package com.nantian.npbs.business.service.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Scope("prototype")
@Component(value = "WebRequestBusiness025012Service")
public class WebRequestBusiness025012Service extends WebRequestBusinessService {
	private static Logger logger = LoggerFactory
			.getLogger(WebRequestBusiness025012Service.class);

	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("WebRequestBusiness025012Service webexecute begin");
		TbBiTrade trade = new TbBiTrade();
		TbBiTradeId tradeId = new TbBiTradeId();
		tradeId.setPbSerial(modelSvcReq.getPb_serial());
		tradeId.setTradeDate(modelSvcReq.getTrade_date());
		String companyCode = modelSvcReq.getCompany_code_fir();// 主商户1
		String companyCode2 = modelSvcReq.getCompany_code_sec();// 辅商户2

		logger.info("---------------------检查冲正流水开始 冲正商户 号：[{}]冲正金额[{}]-------------------------",
						companyCode, modelSvcReq.getAmount());
		TbBiTrade oriTrade = tradeDao.getTradeByDateSerial(modelSvcReq.getOld_trade_date(),
				modelSvcReq.getOld_pb_serial(),modelSvcReq.getOld_web_date(),
				modelSvcReq.getOld_web_serial());
		if (oriTrade == null) {
			modelSvcAns.setMessage("查无此流水");
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
			return;
		} else {
			if(!checkOldTradeState(oriTrade, modelSvcReq, modelSvcAns)){
				return;
			}
			if(!checkOldTradeAmount(oriTrade, modelSvcReq, modelSvcAns)){
				return;
			}
			if(!checkOldTradeAmount(oriTrade, modelSvcReq, modelSvcAns)){
				return;
			}
		}
		modelSvcReq.setOriTrade(oriTrade);
		logger.info("-----------------检查冲正流水结束 -------------------------");
		if (modelSvcReq.getSystem_code().equals("22")) {// 辅商户为空的情况,直接冲回商户1
			logger.info("-----------------------只有一个商户号，直接冲正  开始-------------------------");
			setTrade(trade, tradeId, modelSvcReq, modelSvcReq
					.getCompany_code_fir());
			modelSvcReq.setTbBiTrade(trade);
			modelSvcReq.setTradeId(tradeId);
	}
		else{
			modelSvcAns.setMessage("暂不支持的交易类型");
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
		}	
		logger.info("WebRequestBusiness025012Service webexecute end");
	}

	@Override
	public void setTradeFlag(ModelSvcReq modelSvcReq) {
		// TODO Auto-generated method stub
		modelSvcReq.setSeqnoFlag("1");
		
	}
	
	/**
	 * 检查原交易状态
	 */
	protected boolean checkOldTradeState(TbBiTrade oriTrade,ModelSvcReq modelSvcReq,ModelSvcAns modelSvcAns){
		if(oriTrade.getStatus().trim().equals(GlobalConst.TRADE_STATUS_SUCCESS)){
			return true;
		}else{
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
			modelSvcAns.setMessage("取消交易失败,该状态不能取消,请拨打客服电话咨询!");
			logger.error("取消交易失败,原状态不能取消!用户录入平台流水号[{}],商户号[{}],缴费金额[{}] | 原流水状态为[{}]",
					new Object[]{ modelSvcReq.getOld_pb_serial(),modelSvcReq.getCompany_code_fir(),modelSvcReq.getAmount(), oriTrade.getStatus()});
			return false;
		}
	}
	
	 
	
	/**
	 * 检查原交易缴费金额
	 */
	protected boolean checkOldTradeAmount(TbBiTrade oriTrade,ModelSvcReq modelSvcReq,ModelSvcAns modelSvcAns){
		if(oriTrade.getAmount()==Double.parseDouble(modelSvcReq.getAmount())) 
				return true; 
		  else{
			
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
			modelSvcAns.setMessage("取消交易失败,缴费金额输入有误!");
			logger.error("取消交易失败,缴费金额输入有误!用户录入平台流水号[{}],商户号[{}],缴费金额[{}] | 原缴费金额为[{}]",
					new Object[]{ modelSvcReq.getOld_pb_serial(),modelSvcReq.getCompany_code_fir(),modelSvcReq.getAmount(), oriTrade.getAmount()});
			return false;
		}
	}
	
	/**
	 * 检查原交易商户号是否相符
	 */
	protected boolean checkOldTradeCompany(TbBiTrade oriTrade,ModelSvcReq modelSvcReq,ModelSvcAns modelSvcAns){
		if(oriTrade.getCompanyCode().equals(modelSvcReq.getCompany_code_fir())) 
				return true; 
		  else{
			
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
			modelSvcAns.setMessage("取消交易失败,商户号不符!");
			logger.error("取消交易失败,缴费金额输入有误!用户录入平台流水号[{}],商户号[{}],缴费金额[{}] | 原缴费金额为[{}]",
					new Object[]{ modelSvcReq.getOld_pb_serial(),modelSvcReq.getCompany_code_fir(),modelSvcReq.getAmount(), oriTrade.getAmount()});
			return false;
		}
	}

	/**
	 * 返回交易类型 
	 * 02-取消交易
	 * @return
	 */
	protected String tradeType(){
		return "02";
	}
	
	
}
