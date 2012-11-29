package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 交易取消
 * @author
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness012Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness012Service.class);

	@Resource
	private CommonPrepay commonPrepay;
	
	@Override
	public void execute(ControlMessage cm, BusinessMessage bm) {
		
		logger.info("开始执行响应处理");
		// 由于流水是一个公共处理，不论失败成功均需要处理
		if ("1".equals(bm.getSeqnoFlag())) {
			editTradeState(cm, bm);
		}
		
		dealBusiness(cm, bm);
	}
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		
		TbBiTrade trade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
		
		if(trade==null){
			logger.info("查询原交易流水错误,原交易流水号:{},交易日期:{}" , bm.getOldPbSeqno() , bm.getTranDate());
			return;
		}

		if(!"13".equals(trade.getStatus())){
			logger.info("交易状态不正确!pb流水[{}];交易日期[{}],交易状态[{}]" , new Object[]{bm.getPbSeqno() ,bm.getTranDate(),trade.getStatus()});
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
			cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
			return ;
		}
		
		// 如果交易成功，备付金处理，加一条备付金流水
		if(GlobalConst.RESULTCODE_SUCCESS.equals(cm.getServiceResultCode())){
			
			//河电省标卡扣除备付金不在此处理，在申请撤销写卡脚本交易应答里处理
			if(!"010012".equals(bm.getTranCode())) {				
				//回退备付金账户
				logger.info("取消交易!回退备付金账户!备付金帐号{};金额{}" , bm.getPrePayAccno(),bm.getAmount());
				if(!commonPrepay.cancelToUpdatePrepay(bm.getPrePayAccno(),bm.getAmount())){
					logger.info("取消交易失败!回退备付金账户金额失败!失败原因:pb流水{};交易日期{}" ,  bm.getPbSeqno() ,bm.getTranDate());
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
					cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
					return;
				}
				
				//查询备付金当前余额
				logger.info("商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
				TbBiPrepay tbBiPrepay = null;
				try {
					Object obj1 = baseHibernateDao.get(TbBiPrepay.class,bm.getPrePayAccno());
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
						return ;
					}
				} catch (Exception e) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("查询您的备付金账户出错,请联系管理员!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("查询您的备付金账户出错,请联系管理员!");
					logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];",bm.getShopCode(),bm.getPrePayAccno());
					return ;
				}
				
				TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
				commonPrepay.setPrepayInfo(prepayInfo, bm, "2");
				if(!commonPrepay.addPrepayInfo(prepayInfo)){
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("备付金明细登记出错,请联系管理员!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金明细登记出错,请联系管理员!");
					logger.error("备付金明细登记出错!商户号: [{}];备付金账号: [{}];", bm.getShopCode(),bm.getPrePayAccno());
					return;
				}		
			
				logger.info("修改原交易流水状态！");
				//更新原交易状态
				boolean suc = tradeDao.updateTradeStatus(trade.getId().getTradeDate(), trade
						.getId().getPbSerial(), GlobalConst.TRADE_STATUS_CANCEL,trade.getSystemSerial());
				if (suc != true) {
					logger.info("更新原交易状态错误,原交易流水号:{},交易日期:{},原状态为:{}" ,new Object[] {bm.getOldPbSeqno() , bm.getTranDate() , trade.getStatus()});
					return;
				}
			
			}
			
			// 修改备付金明细状态
//			logger.info("取消交易!删除备付金明细!pb流水[{}];交易日期[{}];电子商务流水[{}];修改后状态[{}]" , new Object[]{bm.getPbSeqno(),bm.getTranDate(),bm.getSysJournalSeqno(),"01"});
//			if(!commonPrepay.updatePrePayInfo(bm.getPbSeqno(),bm.getTranDate(),"01",bm.getSysJournalSeqno())){
//				logger.info("取消交易失败!修改备付金明细失败!失败原因:pb流水{};交易日期{}" , bm.getPbSeqno(), bm.getTranDate());
//				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
//				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
//				bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
//				cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
//				return;
//			}
			
		}else{ //如果交易失败 ，原来流水恢复状态
			logger.info("取消交易失败!失败原因:{}",cm.getServiceResultMsg());

			
			//更新原交易状态。
			//add by fengyafang 20121105 如果交易失败恢复原来状态，并修改原标志为授权状态，只有取消成功的才不为授权状态
			boolean suc = tradeDao.updateTradeStatus(trade.getId().getTradeDate(), trade
					.getId().getPbSerial(), GlobalConst.TRADE_STATUS_SUCCESS,trade.getSystemSerial(),GlobalConst.TRADE_CANCEL_FLAG_YES);
			if (suc != true) {
				logger.info("更新原交易状态错误,原交易流水号:{},交易日期:{},原状态为:{},原授权状态为{}" ,new Object[] {bm.getOldPbSeqno() , bm.getTranDate() , trade.getStatus(),GlobalConst.TRADE_CANCEL_FLAG_NO});
				return;
			}
		}
		
	}
	
}
