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
public class RequestBusiness025Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness025Service.class);
	
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
		logger.info("025补写卡交易请求处理开始！");
		
		//华电国标卡补写卡
		if ("013025".equals(bm.getTranCode()) ) {
			
			//检查缴费流水
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			if(oriTrade==null){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,请重新输入!");
				logger.error("取消交易失败！用户录入流水号[{}]" , bm.getOldPbSeqno());
				return;
			}
			if(!GlobalConst.TRADE_TYPE_JF.equals(oriTrade.getTradeType())){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,该流水不是缴费流水!请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,该流水不是缴费流水!请重新输入!");
				logger.info("取消交易失败!用户录入流水号[{}]!该笔流水不是缴费流水!交易时间[{}],PB流水[{}]",bm.getTranDate(),bm.getOldPbSeqno());
				return;
			}
			
			if(!checkOldTradeUserCode(oriTrade,cm,bm) || !checkOldTradeAmount(oriTrade,cm,bm)){
				return;
			}
			
			// 取消授权标志  0-未授权；1-已授权
			logger.info("取消授权标志:[{}]" , oriTrade.getCancelflag());
			if(oriTrade.getCancelflag().equals(GlobalConst.TRADE_CANCEL_FLAG_NO)){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("该取消交易没有授权,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("该取消交易没有授权,请拨打客服电话咨询!");
				logger.error("该取消交易没有授权!用户输入流水号[{}] | 原流水状态[{}]",
						new Object[]{bm.getOldPbSeqno() , oriTrade.getStatus(),});
				return;
			}
			
			TbBiTrade t = tradeDao.findTrade(bm);
			//logger.info("tradeDao.findTrade(bm)查询："+t.getId().getPbSerial());
			//bm.setOldPbSeqno(" ");
			StringBuffer sb = new StringBuffer();
			sb.append("select t.system_serial from tb_bi_trade t where t.pb_serial = " +
					"(select tc.pb_serial from tb_bi_trade_contrast tc " +
					"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
					"and tc.trade_type='"+GlobalConst.TRADE_TYPE_QXXK+"' )");
			List list = tradeDao.queryBySQL(sb.toString());
			if(null != list && list.size() == 1){
				bm.setOldPbSeqno(list.get(0).toString().trim());
			}else{
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("系统无上笔写卡流水，请到电力营业厅处理!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("系统无上笔写卡流水，请到电力营业厅处理!");
			}
		}else{
			return;
		}
	}
	
	/**
	 * 检查原交易用户号码
	 */
	protected boolean checkOldTradeUserCode(TbBiTrade oriTrade,ControlMessage cm, BusinessMessage bm){
		if(oriTrade.getCustomerno().equals(bm.getUserCode() )){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("用户编号输入有误!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("用户编号输入有误!");
			logger.error("取消交易失败,用户编号输入有误!流水状态为:" + oriTrade.getStatus() + "用户号:" + bm.getUserCode() 
					+  ",平台流水号:" + bm.getOldPbSeqno() + ",缴费金额:" + bm.getAmount()  );
			return false;
		}
	}
	
	/**
	 * 检查原交易缴费金额
	 */
	protected boolean checkOldTradeAmount(TbBiTrade oriTrade, ControlMessage cm, BusinessMessage bm){
		if(oriTrade.getAmount().equals(bm.getAmount() )){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费金额输入有误!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费金额输入有误!");
			logger.error("取消交易失败,缴费金额输入有误!流水状态为[{}]" + oriTrade.getStatus() + "用户号[{}]" + bm.getUserCode() 
					+  ",平台流水号[{}]" + bm.getOldPbSeqno() + ",缴费金额[{}]" + bm.getAmount()  );
			return false;
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
}
