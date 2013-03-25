package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 河电IC卡联动取消交易
 * @author qiaoxiaolei
 * 
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness027Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness027Service.class);
	
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {

		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}
		if (!checkSignState(cm, bm)) { // 检查商户签到
			return false;
		}
		// 检查备付金
		if (!checkReserve(cm, bm)) {
			return false;
		}
		logger.info("检查备付金成功!");
		
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("查询原交易流水！交易日期[{}];原交易流水号[{}]" , 
				new Object[]{bm.getTranDate(),bm.getOldPbSeqno()});
		
		TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
		if(oriTrade==null){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水号输入有误,请重新输入!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("原流水号输入有误,请重新输入!");
			logger.error("取消交易失败！用户录入流水号[{}],用户号[{}],缴费金额[{}]" ,
					new Object[]{bm.getOldPbSeqno(), bm.getUserCode(), bm.getAmount()});
			return;
		}		
		
		
		// 原交易流水商户号
		logger.info("原交易流水商户号:[{}]" , oriTrade.getCompanyCode());
		if(!bm.getShopCode().equals(oriTrade.getCompanyCode())){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户号不符！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户号不符！");
			logger.error("商户号不符，原交易流水商户号:[{}]" , oriTrade.getCompanyCode());
			return;
		}
		
		
		//检查原交易状态、用户编号、缴费金额
		logger.info("原交易状态:[{}];原交易用户编号:[{}];原交易缴费金额:[{}];" ,
				new Object[]{oriTrade.getStatus(),oriTrade.getCustomerno(),oriTrade.getAmount()});
		
		if(!checkOldTradeState(oriTrade,cm,bm)||
				!checkOldTradeUserCode(oriTrade,cm,bm)||
				!checkOldTradeAmount(oriTrade,cm,bm)){
			return;
		}
		
		// 设置电子商务平台流水
		bm.setSysJournalSeqno(oriTrade.getSystemSerial()); 
		bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
		// 设置取消原交易类型
		cm.setCancelBusinessType(oriTrade.getBusiCode());
		
		//更新原交易状态
		logger.info("预修改原交易状态为:[{}];" , GlobalConst.TRADE_CANCEL_ING);
		boolean suc = tradeDao.updateTradeStatus(oriTrade.getId().getTradeDate(), oriTrade
				.getId().getPbSerial(),GlobalConst.TRADE_CANCEL_ING);
		if (suc != true) {
			logger.info("修改流水状态失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("流水状态更新失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("流水状态更新失败！");
			return;
		}
		logger.info("流水状态更新成功！");
		

		// 设置一些交易信息
		bm.setBusinessType(oriTrade.getBusiCode());
		bm.setUserCode(oriTrade.getCustomerno());
		bm.setUserName(oriTrade.getCustomername());
		bm.setAmount(oriTrade.getAmount()); 		//交易金额
		bm.setSalary(oriTrade.getSalary());  		//酬金金额
		bm.setCancelFlag(GlobalConst.TRADE_CANCEL_FLAG_NO);
		bm.setTax(oriTrade.getTax());
		bm.setDepreciation(oriTrade.getDepreciation());
		bm.setOther(oriTrade.getOther());
		bm.setPayType(oriTrade.getPayType());
		bm.setPrePayAccno(oriTrade.getAccno());
		
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		cm.setResultMsg("");
	}

	/**
	 * 检查原交易状态
	 */
	protected boolean checkOldTradeState(TbBiTrade oriTrade,ControlMessage cm,BusinessMessage  bm){
		if(oriTrade.getStatus().trim().equals(GlobalConst.TRADE_STATUS_SUCCESS)){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("取消交易失败,该状态不能取消!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("取消交易失败,该状态不能取消,请拨打客服电话咨询!");
			logger.error("取消交易失败,原状态不能取消!用户录入平台流水号[{}],用户号[{}],缴费金额[{}] | 原流水状态为[{}]",
					new Object[]{ bm.getOldPbSeqno(),bm.getUserCode(), bm.getAmount(), oriTrade.getStatus()});
			return false;
		}
	}
	
	/**
	 * 检查原交易用户名称
	 */
	protected boolean checkOldTradeUserCode(TbBiTrade oriTrade,ControlMessage cm, BusinessMessage bm){
		if(oriTrade.getCustomerno().trim().equals(bm.getUserCode().trim() )){
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

	/**
	 * 返回交易类型 
	 * 02-取消交易
	 * @return
	 */
	@Override
	protected String tradeType(){
		return "02";
	}

	@Override
	public boolean needLockProcess() {
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("1");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag("1");
	}
	
	/**
	 * 检查备付金 设置bm 备付金账户、备付金余额、剩余信用额度
	 */
	protected boolean checkReserve(ControlMessage cm, BusinessMessage bm) {
		TbBiCompany shop = bm.getShop();
		// 检查是否支付方式是否为备付金，非备付金方式不需要检查备付金
//		if (!bm.payTypeIsPrePay()) {
//			logger.info("商户:{} 使用非备付金方式, 支付方式: {}", bm.getShopCode(),
//					bm.getPayType());
//			return true;
//		}
		Object temp = baseHibernateDao.get(TbBiPrepay.class, shop.getResaccno());
		if(temp != null){
			TbBiPrepay tbBiPrepay = (TbBiPrepay) temp;
			if(!(GlobalConst.PREPAY_STATE_ZC).equals(tbBiPrepay.getState())){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("您的备付金账户暂停或已注销,不能进行缴费交易!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("您的备付金账户暂停或已注销,不能进行缴费交易!");
				logger.error("该备付金账户暂停或注销,商户号[{}],备付金账号[{}]",
						bm.getShopCode(),shop.getResaccno());
				return false;
			}
			
			return true;
			
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("您的备付金账户不存在,,请拨打客服电话咨询!!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("您的备付金账户暂停或已注销,,请拨打客服电话咨询!!");
			logger.error("该备付金账户不存在,商户号[{}],备付金账号[{}]",
					bm.getShopCode(),shop.getResaccno());
			return false;
		}
	}
}
