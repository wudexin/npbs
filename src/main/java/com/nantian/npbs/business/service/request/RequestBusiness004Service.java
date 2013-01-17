package com.nantian.npbs.business.service.request;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HeNDElecICCard;

/**
 * 补写卡
 * 河电省标卡
 * 
 * @author
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness004Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness004Service.class);
	// 流水操作
	@Resource
	public TradeDao dao;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("补写卡交易请求处理开始!");

		/*// 如果是河电省标卡则登记流水
		if ("010002".equals(bm.getTranCode()) == true
				&& "000000".equals(cm.getServiceResultCode()) == true) {
			//设置登记流水并修改流水状态
			bm.setSeqnoFlag("1");
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			// 设置原始电子商务平台流水
			bm.setSysJournalSeqno(oriTrade.getSystemSerial());
			
			// 获取缴费流水的一些信息 金额等
			// 设置对象数据
			bm.setAmount(oriTrade.getAmount());
			bm.setSalary(oriTrade.getSalary());
			bm.setTax(oriTrade.getTax());
			bm.setDepreciation(oriTrade.getDepreciation());
			bm.setOther(oriTrade.getOther());
			bm.setUserCode(oriTrade.getCustomerno());
			bm.setUserName(oriTrade.getCustomername());
			
			
		}*/
		
		//如果是河电省标卡补写卡，则根据终端上送缴费流水确定上送电商写卡流水
		if("010004".equals(bm.getTranCode())) {		
			
			if(this.checkNullExceptionString(bm.getTranDate()) || this.checkNullExceptionString(bm.getOldPbSeqno())) {
			  this.setResultMsg(cm, bm, 
					  GlobalConst.RESPONSECODE_FAILURE, "系统数据异常",
					  GlobalConst.RESULTCODE_FAILURE, "系统数据异常",
					  "系统必须的流水日期，流水号为空！交易日期："+bm.getTranDate()+",流水号："+bm.getOldPbSeqno());
			  return;
			}
			//提取河电省标缴费交易流水信息
			TbBiTrade origAmountTrade = tradeDao.getTradeById(
					"20"+bm.getOldPbSeqno().trim().substring(0,6),
					//bm.getTranDate().trim()
					  bm.getOldPbSeqno().trim());  
			if(null == origAmountTrade) {
				this.setResultMsg(cm, bm, GlobalConst.RESPONSECODE_FAILURE, "终端上送缴费流水异常，系统不存在此流水", 
						GlobalConst.RESULTCODE_FAILURE, "终端上送缴费流水异常，系统不存在此流水", 
						"终端上送流水异常，系统不存在改笔流水！交易日期："+bm.getTranDate()+",交易PB流水号："+bm.getOldPbSeqno());
				return;
			}	
		
			//判断原流水是否可进行补写卡
			if(!this.checkOldTradeState(origAmountTrade, cm, bm)) {
				return;
			}
			
			//设置电子商务平台流水
			bm.setOldElecSeqNo(origAmountTrade.getSystemSerial().trim());
			//设置发送电商日期20120813补写卡日期电商的日期
			bm.setMidPlatformDate(origAmountTrade.getSystemDate().trim());
			
		}else if(bm.getTranCode().equals("018004")){
			TbBiTrade oriTrade = tradeDao.getTradeById("20"+bm.getOldPbSeqno().trim().substring(0, 6), bm.getOldPbSeqno().trim());  
			if(null==oriTrade){
				  this.setResultMsg(cm, bm, 
						  GlobalConst.RESPONSECODE_FAILURE, "系统数据异常",
						  GlobalConst.RESULTCODE_FAILURE, "系统数据异常",
						  "系统必须的流水日期，流水号为空！交易日期："+"20"+bm.getOldPbSeqno().trim().substring(0, 6)+",流水号："+bm.getOldPbSeqno());
				return;
			}
			//农电缴费补写卡
			TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,bm.getNdzhuanyong());
			if(null==cashTemp){
				  this.setResultMsg(cm, bm, 
						  GlobalConst.RESPONSECODE_FAILURE, "系统数据异常",
						  GlobalConst.RESULTCODE_FAILURE, "系统数据异常",
						  "农电临时缴费数据异常");
				return;
			}
			HeNDElecICCard customData=null;
			if (bm.getCustomData() != null) {
				customData = (HeNDElecICCard) bm.getCustomData();
			} else {
				customData = new HeNDElecICCard();
			}
				String[] split = String.valueOf(cashTemp.getTempValue()).split("\\^");
				 customData.setCHECK_ID(split[0]);    
				  customData.setCONS_NO(split[1]);     
				 customData.setMETER_ID(split[2]);    
				 customData.setMETER_FLAG(split[3]);  
				 customData.setCARD_INFO(split[4]);   
				 customData.setIDDATA(split[5]);      
				 customData.setCONS_NAME(split[6]);   
				 customData.setCONS_ADDR(split[7]) ;  
				 customData.setPAY_ORGNO(split[8]);   
				 customData.setORG_NO(split[9]) ;     
				 customData.setCHARGE_CLASS(split[10]);
				 customData.setFACTOR_VALUE(split[11]);
				 customData.setPURP_PRICE(split[12])  ;
				bm.setCustomData(customData); 
				
				
				bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
				 
		}
	}
	
	/**
	 * 返回交易类型 
	 * 13-补写卡交易
	 * @return
	 */
	@Override
	protected String tradeType() {
		return "13";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.nantian.npbs.business.service.request.RequestBusinessService#
	 * needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		return true;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		//在业务代码中设置
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方需根据业务判断，在业务代码中设置
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
	 * 检查原交易是能进行补写卡
	 * @param oriTrade  需要检查交易
	 * @param cm       控制类
	 * @param bm       业务类
	 * @return     是:true   否：false 
	 */
	protected boolean checkOldTradeState(TbBiTrade oriTrade,ControlMessage cm,BusinessMessage  bm){	
		
		
		//判断是否缴费流水
		if(!GlobalConst.TRADE_TYPE_JF.equals(oriTrade.getTradeType())) {
			this.setResultMsg(cm, bm, 
					GlobalConst.RESPONSECODE_FAILURE, "补写卡失败,非缴费流水!",
					GlobalConst.RESULTCODE_FAILURE, "补写卡失败,非缴费流水!",
					"补写卡失败，原交易类型为"+oriTrade.getTradeType());				
			return false;
		}
		
        //判断流水状态是否成功
		if(GlobalConst.TRADE_STATUS_SUCCESS.equals(oriTrade.getStatus().trim())||
				GlobalConst.TRADE_STATUS_NEED_WRITE.equals(oriTrade.getStatus().trim())){
			return true;
		}else{
			//查找是否写卡成功，缴费失败，如果是则可以做补写卡
			String sql="select status from tb_bi_trade t where t.system_date='"+oriTrade.getSystemDate() 
			+"' and t.system_serial='"+oriTrade.getSystemSerial()+"' and t.trade_type='03' and t.trade_date='"+"20"+bm.getOldPbSeqno().trim().substring(0,6)+"'";
			List queryBySQL = tradeDao.queryBySQL(sql);
			//List find = baseHibernateDao.find(sql);
			if(queryBySQL.isEmpty()){
				logger.info("查询写卡流水失败");
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "补写卡失败,查询写卡流水失败!",
						GlobalConst.RESULTCODE_FAILURE, "补写卡失败,查询写卡流水失败!",
						"补写卡失败,查询写卡流水失败");		
				return false;
			}
			if(queryBySQL.get(0).equals("00")){
				logger.info("查询缴费对应写卡流水为成功则修改缴费流水为成功");
				boolean updateTradeStatus = tradeDao.updateTradeStatus("20"+bm.getOldPbSeqno().trim().substring(0,6), oriTrade.getId().getPbSerial(), "00");
				if(updateTradeStatus){
					return true;	
				}else{
					logger.info("更新原缴费流水失败");
					this.setResultMsg(cm, bm, 
							GlobalConst.RESPONSECODE_FAILURE, "补写卡失败,原交易流水更新失败!",
							GlobalConst.RESULTCODE_FAILURE, "补写卡失败,原交易流水更新失败!",
							"补写卡失败,原交易流水更新失败"+oriTrade.getStatus());		
					return false;	
				}
			}else{
			this.setResultMsg(cm, bm, 
					GlobalConst.RESPONSECODE_FAILURE, "补写卡失败,流水状态不正确!",
					GlobalConst.RESULTCODE_FAILURE, "补写卡失败,流水状态不正确!",
					"补写卡失败，原交易状态为"+oriTrade.getStatus());				
			return false;}
		}
		
	}
	
	
}
