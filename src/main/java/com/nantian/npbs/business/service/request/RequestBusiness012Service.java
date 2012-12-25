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
 * 交易取消
 * @author qiaoxiaolei
 * 河北省标卡在交易取消时不记账，在申请到取消写卡数据交易中RequestBusiness024Service中记账
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness012Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness012Service.class);
	
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
		// 检查强制下载信息
		checkForceDownLoadFlag(cm, bm);
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
		
		
		//设置一般信息
		bm.setBusinessType(oriTrade.getBusiCode());		
		bm.setUserName(oriTrade.getCustomername());
		
		
		if(oriTrade.getBusiCode() != null && "000012".equals(bm.getTranCode())&& "013".equals(oriTrade.getBusiCode())){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("华电IC卡业务不可在此进行!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("华电IC卡业务不可在此进行!");
			logger.error("取消交易失败!华电IC卡业务不可在此进行!用户录入流水号[{}],用户号[{}],缴费金额[{}]" ,
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

		// 取消授权标志  0-未授权；1-已授权
		logger.info("取消授权标志:[{}]" , oriTrade.getCancelflag());
		if(oriTrade.getCancelflag().equals(GlobalConst.TRADE_CANCEL_FLAG_NO)){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("该取消交易没有授权,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("该取消交易没有授权,请拨打客服电话咨询!");
			logger.error("该取消交易没有授权!用户输入流水号[{}],用户号[{}],,缴费金额[{}] | 原流水状态[{}]",
					new Object[]{bm.getOldPbSeqno(),bm.getUserCode(), bm.getAmount(),oriTrade.getStatus(),});
			return;
		}		
		/*		*/	
		//检查原交易状态、用户编号、缴费金额
		logger.info("原交易状态:[{}];原交易用户编号:[{}];原交易缴费金额:[{}];" ,
				new Object[]{oriTrade.getStatus(),oriTrade.getCustomerno(),oriTrade.getAmount()});
		
		if(!checkOldTradeState(oriTrade,cm,bm)||
				!checkOldTradeUserCode(oriTrade,cm,bm)||
				!checkOldTradeAmount(oriTrade,cm,bm)){
			return;
		}
		
		
		//登记流水与原流水对照
		logger.info("登记取消流水与原流水对照。交易日期[{}];原流水号[{}];取消交易流水号[{}];" ,
				new Object[]{bm.getTranDate(),bm.getOldPbSeqno(),bm.getPbSeqno()});
		
		TbBiTradeContrast tc = new TbBiTradeContrast();
		TbBiTradeContrastId id = new TbBiTradeContrastId();
		id.setOriPbSerial(bm.getOldPbSeqno());
		id.setPbSerial(bm.getPbSeqno());
		id.setTradeDate(bm.getTranDate());
		tc.setId(id);
		// 01-缴费交易；02-取消交易；03-写卡；04-冲正交易；05-写卡成功确认；06-写卡失败确认
		logger.info("写卡流水对照类型[{}]",bm.getTranType());
		tc.setTradeType(bm.getTranType());
		baseHibernateDao.save(tc);
		
		// 设置电子商务平台流水
		bm.setSysJournalSeqno(oriTrade.getSystemSerial()); 
		bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
		// 设置取消原交易类型
		cm.setCancelBusinessType(oriTrade.getBusiCode());
		
		
		
		//把查询出来的取消流水放到BM里临时存放，以便在取消成功后进行授权的回退
		
		//更新原交易状态,并修改授权标志为未授权
		logger.info("预修改原交易状态为:[{}];预修改原交易授权标志为:[{}];" , GlobalConst.TRADE_CANCEL_ING,GlobalConst.TRADE_CANCEL_FLAG_NO);
		boolean suc = tradeDao.updateTradeStatus(oriTrade.getId().getTradeDate(), oriTrade
				.getId().getPbSerial(),GlobalConst.TRADE_CANCEL_ING,oriTrade.getSystemSerial(),GlobalConst.TRADE_CANCEL_FLAG_NO);
		if (suc != true) {
			logger.info("修改流水状态失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("流水状态更新失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("流水状态更新失败！");
			return;
		}
		logger.info("流水状态更新成功！");
		//设置用户号
		bm.setUserCode(oriTrade.getCustomerno());
		
		// 设置一些交易信息		
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
		return true;
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
