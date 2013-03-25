package com.nantian.npbs.business.service.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 写卡
 * @author 
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness003Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness003Service.class);

	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		// 检查商户状态
		if (!checkShopState(cm, bm)) {
			return false;
		}
		// 检查商户签到
		if (!checkSignState(cm, bm)) {
			return false;
		}
		// 检查商户是否有该业务
		if (!checkShopBindBusiness(cm, bm)) {
			return false;
		}
		// 检查强制下载信息
		checkForceDownLoadFlag(cm, bm);

		logger.info("公共校验成功!");
		return true;
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		//直接发送电子商务平台
		logger.info("写卡交易请求处理开始！无业务逻辑。");
	
		//检查交易码是否为空
		if(this.checkNullExceptionString(bm.getTranCode())) {
			this.setResultMsg(cm, bm, 
					GlobalConst.RESPONSECODE_FAILURE, "交易码为空！",
					GlobalConst.RESULTCODE_FAILURE, "交易码为空！", 
					this.getClass().getName()+"交易码为空！");
			return;
		}	
		
		// 如果是华电IC卡则登记流水，检查上送61域流水有效性，检查是否重复写卡
		if ("013003".equals(bm.getTranCode()) == true) {
			
			//检查写卡上送的61域流水是否有效
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			if(oriTrade==null){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("终端上送缴费流水异常!系统不存在该笔流水!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("终端上送缴费流水异常!系统不存在该笔流水!");
				logger.info("终端上送缴费流水异常!系统不存在该笔流水!交易时间[{}],PB流水[{}]",bm.getTranDate(),bm.getOldPbSeqno());
				return;
			}
			if(!GlobalConst.TRADE_TYPE_JF.equals(oriTrade.getTradeType())){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("终端上送缴费流水异常!该笔流水不是缴费流水!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("终端上送缴费流水异常!该笔流水不是缴费流水!");
				logger.info("终端上送缴费流水异常!该笔流水不是缴费流水!交易时间[{}],PB流水[{}]",bm.getTranDate(),bm.getOldPbSeqno());
				return;
			}
			
			//设置登记流水并修改流水状态
			bm.setSeqnoFlag("1");
		}else if("010003".equals(bm.getTranCode())){          	// 如果是河电省标卡则登记流水
			
			//检查交易时间及员缴费流水是否为空
			if(this.checkNullExceptionString(bm.getTranDate()) 
					|| this.checkNullExceptionString(bm.getOldPbSeqno())) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "系统数据异常！",
						GlobalConst.RESULTCODE_FAILURE, "系统数据异常！",
						this.getClass().getName()+"交易日期或原流水异常！");
				return;
			}
			
			//提取对应该笔缴费交易流水信息
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			if(oriTrade==null){
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "系统中不存在该缴费交易!", 
						GlobalConst.RESULTCODE_FAILURE,"系统中不存在该缴费交易!",
						"系统中不存在该缴费交易,交易时间："+bm.getTranDate()+",流水号："+bm.getOldPbSeqno());
				return;
			}
			
			//判断原交易是否为缴费交易
			if(!GlobalConst.TRADE_TYPE_JF.equals(oriTrade.getTradeType())) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "终端上传交易非缴费交易!", 
						GlobalConst.RESULTCODE_FAILURE,"终端上传交易非缴费交易!",
						"终端上传交易非缴费交易!交易时间："+bm.getTranDate()+",流水号："+bm.getOldPbSeqno());
				return;
			}
			
			//判断原交易缴费交易是否为成功
			if(!GlobalConst.TRADE_STATUS_SUCCESS.equals(oriTrade.getStatus())) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "终端缴费流水状态不正常!", 
						GlobalConst.RESULTCODE_FAILURE,"终端缴费流水状态不正常!!",
						"终端缴费流水状态不正常!交易时间："+bm.getTranDate()+",流水号："+bm.getOldPbSeqno());
				return;
			}
			
			// 设置原始电子商务平台流水
			bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
		
			// 获取缴费流水的一些信息 金额等
			// 设置对象数据
			bm.setAmount(oriTrade.getAmount());
			bm.setSalary(oriTrade.getSalary());
			bm.setTax(oriTrade.getTax());
			bm.setDepreciation(oriTrade.getDepreciation());
			bm.setOther(oriTrade.getOther());
			

			//设置登记该笔写卡流水
			bm.setSeqnoFlag("1");
		
			/**
			 * 检查是否重复写卡不登记或更新流水对照
			 */
			StringBuffer sql = new StringBuffer();
			List list = null;
			sql.append(" select count(*) from tb_bi_trade_contrast tc " +
						"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
						"and tc.trade_type='"+GlobalConst.TRADE_TYPE_XK+"'");
			try {
				list = tradeDao.queryBySQL(sql.toString());
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("写卡失败!系统错误:检测重复写卡错误!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("写卡失败!系统错误:检测重复写卡错误!");
				logger.error("写卡失败!系统错误:检测重复写卡错误!SQL[{}]",sql.toString());
				e.printStackTrace();				
			}
			if(null != list && list.size() == 1){
				Integer number = Integer.valueOf(list.get(0).toString().trim());
				
				if(number == 0){          //首次写卡新增缴费、写卡关联
					TbBiTradeContrast tc = new TbBiTradeContrast();
					TbBiTradeContrastId id = new TbBiTradeContrastId();
					id.setPbSerial(bm.getPbSeqno());//本交易（写卡）流水
					id.setOriPbSerial(bm.getOldPbSeqno());//POS上送现金缴费流水
					id.setTradeDate(bm.getTranDate());
					tc.setId(id);
					tc.setTradeType(GlobalConst.TRADE_TYPE_XK);
					
					logger.info("写卡流水对照：类型[{}],交易流水（写卡流水）[{}],原交易流水（缴费流水）[{}]",
							new Object[]{tc.getTradeType(), 
								tc.getId().getPbSerial(), 
								tc.getId().getOriPbSerial()});
					
					baseHibernateDao.save(tc);					
				} else if(number == 1){           //非首次写卡，更新缴费、写卡关联
					StringBuffer update = new StringBuffer();
					update.append("update tb_bi_trade_contrast tc set tc.pb_serial='"+bm.getPbSeqno()+"' " +
							"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
							"and tc.trade_type='"+GlobalConst.TRADE_TYPE_XK+"'");
					try {
						if(tradeDao.updateTradeContrast(update.toString())){							
						}else{
							bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
							bm.setResponseMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
							cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
							cm.setResultMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
						}
					} catch (Exception e) {
						e.printStackTrace();
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
						logger.error("写卡失败!系统错误:更新缴费、写卡关联异常!!SQL[{}]",update.toString());
					}
				}else{    //其它视为异常!
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("写卡失败!系统错误:出现多笔缴费写卡关联!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("写卡失败!系统错误:出现多笔缴费写卡关联!");				
				}
			}			
			
		}else {
			bm.setSeqnoFlag("0");
		}
		
	}
	
	/**
	 * 返回交易类型 
	 * 03-写卡
	 * @return
	 */
	@Override
	protected String tradeType() {
		return "03";
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.service.request.RequestBusinessService#needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		//bm.setSeqnoFlag("0");
		return;
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
}
