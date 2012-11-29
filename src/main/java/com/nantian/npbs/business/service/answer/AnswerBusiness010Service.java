package com.nantian.npbs.business.service.answer;

import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness010Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness010Service.class);
	
	@Resource
	private CommonPrepay commonPrepay;
	
	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {


		if ("0".equals(cm.getServiceCallFlag())) { // 判断是否调用了电子商务平台,0为不调用,直接返回，可能是无本地流水返回02,异常返回99
			logger.info("便民服务站直接返回，不发送电子商务平台。");
			return;
		}

		if (!GlobalConst.RESULTCODE_SUCCESS.equals(cm.getServiceResultCode())) { // 和电子商务平台交互应答失败,返回原交易记录状态99
			logger.info("查询电子商务平台失败!");
			return;
		}

		/*
		 * 电子商务平台状态 1:成功 2: 原交易无效--失败 3: 原交易已取消—本交易为缴费交易，已经取消成功的
		 * 4:该交易为取消交易—本交易为取消交易，并且成功的 5:原交易不存在
		 */
		/*
		 * 便民服务站状态 00——交易成功， 01——交易失败， 02——原交易不存在， 03——交易被取消， 04-交易被冲正，
		 * 05-交易由失败状态改为成功状态， 06-写卡失败, 99——初始状态
		 */
//		bm.setPbSeqno(bm.getOldPbSeqno());
//		TbBiTrade trade = tradeDao.findTrade(bm); // 原交易流水
//		if(trade == null){
//			logger.info("原交易流水不存在!");
//			return;
//		}
//		// 流水状态根据第三方返回修改
//		logger.info("日期：[{}],流水号：[{}],原流水状态：[{}],末笔查询返回状态：[{}]。{}",
//				new Object[] { trade.getId().getTradeDate(),
//						trade.getId().getPbSerial(), trade.getStatus(),
//						bm.getPbState(), getClass().getSimpleName() });
		switch (bm.getPbState().charAt(0)) {
		case '1'://1:成功
			//修改流水流水状态和电子商务平台日期、流水
			if(!editTradeState(cm,bm)){
				bm.setPbState("99");
				return ;
			}
			updatePrepay(cm,bm); //进行缴费账务处理
			break;
		case '2'://2-原交易无效--失败,但经过沟通了解到对方失败不登记流水
			bm.setPbState("01");
			bm.setResponseMsg("电商返回[2:原交易无效]");
			//河电省标卡末笔交易查询返回失败时，同步更新原缴费交易流水状态为失败，这里bm.getOrigPosJournalSeqno中存储的为缴费交易流水
			if("010".equals(bm.getBusinessType().trim())) {
				tradeDao.updateTradeStatus(bm.getTranDate(), bm.getOrigPosJournalSeqno()
						, GlobalConst.TRADE_STATUS_FAILURE);
			}			
			break;
		case '3'://3: 原交易已取消—本交易为缴费交易，已经取消成功的
			bm.setPbState("99");
			bm.setResponseMsg("电商返回[3:原交易已取消]");
			break;
		case '4'://4:该交易为取消交易—本交易为取消交易，并且成功的
			//修改流水流水状态和电子商务平台日期、流水
			if(!editTradeState(cm,bm)){
				bm.setPbState("99");
				return ;
			}
			cancelPrepay(cm,bm);//进行取消账务处理
			break;
		case '5':// 5:原交易不存在
			bm.setPbState("02");
			bm.setResponseMsg("电商返回[5:原交易不存在]");
			break;
		}
		bm.setResponseCode(bm.getPbState());

		logger.info("末笔查询交易处理完毕！");
	}
	
	/**
	 * 扣备付金，登记明细
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean updatePrepay(ControlMessage cm,BusinessMessage bm){
		//删除授权表
		deleteAuthorizeAmount(cm, bm);
		//处理备付金，登记明细
		bm.setPrePayAccno(bm.getShop().getResaccno());
		if(!commonPrepay.payPrepay(cm, bm)){
			return false;
		}
		return true;
	}
	
	/**
	 * 取消备付金，登记明细
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean cancelPrepay(ControlMessage cm,BusinessMessage bm){
		logger.info("查询原流水与取消流水对照！" );
		TbBiTradeContrast tradeContrast = null;
		String getTradeSql = " from TbBiTradeContrast where id.tradeDate = '"
				+ bm.getTranDate() + "' and id.pbSerial = '"
				+ bm.getOldPbSeqno() + "' and tradeType = '02'";
		List<TbBiTradeContrast> tradeConList = tradeDao.findTradeContrastList(getTradeSql);
		if (tradeConList.size() > 1) {
			logger.info("查询原流水对照失败！" );
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询原流水对照失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询原流水对照失败！");
			return false;
		}
		tradeContrast = tradeConList.get(0);
		bm.setPbSeqno(bm.getOldPbSeqno());
		if(!"012".equals(bm.getBusinessType())) {
			bm.setOldPbSeqno(tradeContrast.getId().getOriPbSerial());
		}
		
		
		TbBiTrade trade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
		
		// 如果交易成功，备付金处理，加一条备付金流水
		if(GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())){
			//回退备付金账户
			logger.info("取消交易!回退备付金账户!备付金帐号{};金额{}" , bm.getPrePayAccno(),bm.getAmount());
			if(!commonPrepay.cancelToUpdatePrepay(bm.getShop().getResaccno(),bm.getAmount())){
				logger.info("取消交易失败!回退备付金账户金额失败!失败原因:pb流水{};交易日期{}" ,  bm.getPbSeqno() ,bm.getTranDate());
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
				cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
				return false;
			}
			
			//查询备付金当前余额
			logger.error("商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getShop().getResaccno());
			TbBiPrepay tbBiPrepay = null;
			try {
				Object obj1 = baseHibernateDao.get(TbBiPrepay.class,bm.getShop().getResaccno());
				if (obj1 != null) {
					tbBiPrepay = (TbBiPrepay) obj1;
					bm.setPrepay(tbBiPrepay);
					// 设置
					bm.setPreBalance(tbBiPrepay.getAccBalance()); // 备付金余额
				} else {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("备付金查询出错!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金查询出错!");
					logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
					return false;
				}
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询您的备付金账户出错,请联系管理员!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询您的备付金账户出错,请联系管理员!");
				logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
				return false;
			}
			
			TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
			commonPrepay.setPrepayInfo(prepayInfo, bm, "2");
			if(!commonPrepay.addPrepayInfo(prepayInfo)){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("备付金明细登记出错,请联系管理员!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金明细登记出错,请联系管理员!");
				logger.error("备付金明细登记出错!商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
				return true;
			}
			
			//更新原缴费交易流水状态
			if(trade==null){
				logger.info("查询原缴费交易流水错误,原交易流水号:{},交易日期:{}" , bm.getOldPbSeqno() , bm.getTranDate());
				return true;
			}
			//更新原交易状态。
			boolean suc = tradeDao.updateTradeStatus(trade.getId().getTradeDate(), trade
					.getId().getPbSerial(), GlobalConst.TRADE_STATUS_CANCEL,trade.getSystemSerial());
			if (suc != true) {
				logger.info("更新原交易状态错误,原交易流水号:{},交易日期:{},原状态为:{}" ,new Object[] {bm.getOldPbSeqno() , bm.getTranDate() , trade.getStatus()});
				return false;
			}
		}else{ //如果交易失败 ，原来流水恢复状态
			
			//String msg = 	cm.getServiceResultMsg();
			logger.info("取消交易失败!失败原因:{}",cm.getServiceResultMsg());
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
			cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
			
			if(trade==null){
				logger.info("查询原交易流水错误,原交易流水号:{},交易日期:{}" , bm.getOldPbSeqno() , bm.getTranDate());
				return true;
			}
			//更新原交易状态。
			boolean suc = tradeDao.updateTradeStatus(trade.getId().getTradeDate(), trade
					.getId().getPbSerial(), GlobalConst.TRADE_STATUS_SUCCESS,trade.getSystemSerial());
			if (suc != true) {
				logger.info("更新原交易状态错误,原交易流水号:{},交易日期:{},原状态为:{}" ,new Object[] {bm.getOldPbSeqno() , bm.getTranDate() , trade.getStatus()});
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * 修改流水表状态
	 */
	protected boolean editTradeState(ControlMessage cm, BusinessMessage bm) {
		logger.info("修改流水状态" );
		bm.setTranDate(bm.getOrigDealDate());//原交易日期
		bm.setPbSeqno(bm.getOldPbSeqno());//原交易流水
		logger.info("交易日期：[{}],交易流水：[{}]。" , bm
				.getTranDate(), bm.getPbSeqno());
		// 查询原流水信息
		TbBiTrade trade = tradeDao.findTrade(bm);
		if (trade == null) {
			logger.info("原流水信息不存在！" );
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水信息不存在！");
			return false;
		}
		if(!"99".equals(trade.getStatus()) && !"02".equals(trade.getStatus())){
			logger.info("交易状态不符:本系统[{}]",trade.getStatus());
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("交易状态不符!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("交易状态不符，请联系管理员处理！");
			return false;
		}
		//正常交易成功
		if("1".equals(bm.getPbState())){
			if(!"01".equals(trade.getTradeType()) && !"03".equals(trade.getTradeType())){//01正常缴费，03写卡（河电写卡记账）
				logger.error("交易类型不一致！本系统为[{}]，电子商务平台为1-正常成功交易。",trade.getTradeType());
				bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
				bm.setResponseMsg("电子商务平台该交易为取消交易！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("电子商务平台该交易为取消交易！");
				return false;
			}else{
				bm.setPbState("00");
			}
		}
		//取消交易成功
		if("4".equals(bm.getPbState())){
			if(!"02".equals(trade.getTradeType())){
				logger.error("交易类型不一致！本系统为[{}]，电子商务平台为4-该交易为取消交易。",trade.getTradeType());
				bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
				bm.setResponseMsg("电子商务平台该交易为取消交易！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("电子商务平台该交易为取消交易！");
				return false;
			}else{
				bm.setPbState("00");
			}
		}
		
		if(DoubleUtils.sub(trade.getAmount(),bm.getAmount()) > 0.00){
			logger.info("交易金额不符:本系统[{}]，电子商务[{}]",trade.getAmount(),bm.getAmount());
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("交易金额不符!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("交易金额不符，联系管理员处理！");
			return false;
		}
		
		//电子商务平台信息
		trade.setStatus(bm.getPbState());
		trade.setSystemDate(bm.getOrigLocalDate());
		trade.setSystemSerial(bm.getOrigSysJournalSeqno());
		bm.setSysJournalSeqno(trade.getSystemSerial());
		
		//登记明细时使用
		bm.setBusinessType(trade.getBusiCode());
		bm.setTranType(trade.getTradeType());//-Add 2012年1月9日 17:39:58 MDB

		// 更新流水表
		String sql = "update TbBiTrade set status = '" + trade.getStatus()
				+ "' ,systemDate= '" + trade.getSystemDate()
				+ "' ,systemSerial= '" + trade.getSystemSerial()
				+ "' where id.tradeDate = '" + trade.getId().getTradeDate()
				+ "' and id.pbSerial = '" + trade.getId().getPbSerial() + "' ";
		boolean suc = tradeDao.updateTrade(sql);
		if (suc != true) {
			logger.info("修改流水状态失败！" );
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("流水状态更新失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("流水状态更新失败！");
			return false;
		}
		logger.info("流水状态更新成功！" );
		return true;

	}

}
