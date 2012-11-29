package com.nantian.npbs.business.service.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 补写卡写卡
 * @author MDB
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness023Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness023Service.class);
	
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		//进行公共检查
		if(!checkShopState(cm,bm))			// 检查商户状态
			return false;
		if(!checkSignState(cm,bm))			// 检查商户签到
			return false;
		if(!checkShopBindBusiness(cm,bm))	// 检查商户是否有该业务
			return false;
		logger.info("公共校验成功!");
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("023补写卡交易请求处理开始！");
		
		//华电国标卡补写卡
		if ("013023".equals(bm.getTranCode()) ) {
			
			//TbBiTrade t = tradeDao.findTrade(bm);
			
			//根据61域缴费流水查询电子商务写卡流水
			StringBuffer sql = new StringBuffer();
			sql.append("select t.system_serial from tb_bi_trade t where t.pb_serial = " +
						"(select tc.pb_serial from tb_bi_trade_contrast tc " +
						"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
						"and tc.trade_type='"+GlobalConst.TRADE_TYPE_XK+"' )");
			List list = null;
			try {
				list = tradeDao.queryBySQL(sql.toString());
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("系统错误:查询电子商务写卡流水异常!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("系统错误:查询电子商务写卡流水异常!");
				e.printStackTrace();
				logger.error("数据库操作错误[{}]", sql.toString());
				return;
			}
			
			if(null != list && list.size() == 1){
				bm.setOldPbSeqno(list.get(0).toString().trim());
			}else{
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("系统无上笔写卡流水，请到电力营业厅处理!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("系统无上笔写卡流水，请到电力营业厅处理!");
				return;
			}
			//河电国标卡补写卡
		}else if("012023".equals(bm.getTranCode())){    
			
			//检验pos终端上送61域是否有值
			if(this.checkNullExceptionString(bm.getOldPbSeqno()) || this.checkNullExceptionString(bm.getTranDate())) {
				this.setResultMsg(cm, bm, 
					GlobalConst.RESPONSECODE_FAILURE, "上传申请补写流水数据异常！", 
					GlobalConst.RESULTCODE_FAILURE, "上传申请流水数据异常", 
					"上传申请流水值为空！");
			}			
			
			
			//根据61域缴费流水查询电子商务流水
			TbBiTrade tbBiTrade = tradeDao.getTradeById(
					//bm.getTranDate(),
					"20"+bm.getOldPbSeqno().trim().substring(0,6),bm.getOldPbSeqno().trim());
			
			if(null==tbBiTrade) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "上传流水不存在！", 
						GlobalConst.RESULTCODE_FAILURE, "上传流水不存在", 
						"上传申请流水不存在！");
			}else {	
				
				//检查缴费补写是否成功
				if(!this.checkOldTradeState(tbBiTrade, cm, bm)) {
					return;					
				}
				//检查电商流水是否为空
				if(this.checkNullExceptionString(tbBiTrade.getSystemSerial())) {
					this.setResultMsg(cm, bm, 
							GlobalConst.RESPONSECODE_FAILURE, "提取电商流水异常！", 
							GlobalConst.RESULTCODE_FAILURE, "提取电商流水异常！", 
							"提取电商流水异常！");
				}else {
					//设置发送电商流水
					bm.setOrigSysJournalSeqno(tbBiTrade.getSystemSerial().trim());
					//bm.setOrigSysJournalSeqno("130017630");
					//设置发送电商日期20120813补写卡日期电商的日期
					bm.setMidPlatformDate(tbBiTrade.getSystemDate().trim());
				}				
			}			
			
		}else{
			
			return;
		}
	}
	
	protected String tradeType(){
		//交易类型 01-缴费交易；02-取消交易；04-冲正交易；05-写卡成功确认；06-写卡失败确认；07-查询；08-管理
		//无特殊处理，设置为查询
		return "07";
	}

	@Override
	public boolean needLockProcess() {
		//发送第三方，需要进程控制
		return true;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		//不登记流水
		bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag("1");
	}
	
	/**
	 *判断字符串是否为null或空字符串
	 * @param str   需要判断的字符串
	 * @return 为空为true  否则为false
	 */
	public boolean checkNullExceptionString(String str) {
		if(null == str || "".equals(str)) {
			return true;
		}else {
			return false;
		}			
	}
	
	/**
	 * 设置应答码、应答信息、结果码、结果信息、日志信息
	 * @param cm
	 * @param bm
	 * @param resposeCode  应答码
	 * @param resposeMsg   应答信息
	 * @param resultCode   结果码
	 * @param resultMsg    结果信息
	 * @param logMsg       日志信息
	 */
	public  void setResultMsg(ControlMessage cm, BusinessMessage bm,
			String resposeCode,String resposeMsg,
			String resultCode,String resultMsg,String logMsg) {
		bm.setResponseCode(resposeCode);
		bm.setResponseMsg(resposeMsg);
		cm.setResultCode(resultCode);
		cm.setResultMsg(resultMsg);
		logger.info(logMsg);
		
	}
	
	/**
	 * 检查原交易状态
	 */
	protected boolean checkOldTradeState(TbBiTrade oriTrade,ControlMessage cm,BusinessMessage  bm){
		if(oriTrade.getStatus().equals(GlobalConst.TRADE_STATUS_SUCCESS)){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("补写交易失败,该状态不能取消!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("补写交易失败,该状态不能取消,请拨打客服电话咨询!");
			logger.error("补写交易失败,原状态不能取消!用户录入平台流水号[{}],用户号[{}],缴费金额[{}] | 原流水状态为[{}]",
					new Object[]{ bm.getOldPbSeqno(),bm.getUserCode(), bm.getAmount(), oriTrade.getStatus()});
			return false;
		}
	}
	
}
