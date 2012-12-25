package com.nantian.npbs.business.service.request;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import weblogic.servlet.jsp.AddToMapException;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Scope("prototype")
@Component(value = "WebRequestBusiness025002Service")
public class WebRequestBusiness025002Service extends WebRequestBusinessService {
	private static Logger logger = LoggerFactory
			.getLogger(WebRequestBusiness025002Service.class);

	@SuppressWarnings("null")
	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("WebRequestBusiness025002Service webexecute begin");
		/**
		 * 如商户2有值，则扣商户1的值，存入商户2， 转帐：商户1值扣，存入商户2，交易类型需传28. 默认扣商户1的值，与支付金额相等。
		 * 
		 * 如子商户为空，则默认扣第一个商户的金额，如果不够则交易不成功。 商户1值不够扣，则扣信用额度。交易类型为21
		 */

		TbBiTrade trade = new TbBiTrade();
		TbBiTradeId tradeId = new TbBiTradeId();
		
		tradeId.setPbSerial(modelSvcReq.getPb_serial());
		tradeId.setTradeDate(modelSvcReq.getTrade_date());
		String companyCode = modelSvcReq.getCompany_code_fir();// 主商户1
		String companyCode2 = modelSvcReq.getCompany_code_sec();// 辅商户2 
		if (modelSvcReq.getSystem_code().equals("21")) { 
			// 设置流水信息
			logger
					.info("-----------------------只有一个商户 登记流水--------------------");
			setTrade(trade, tradeId, modelSvcReq, modelSvcReq
					.getCompany_code_fir());
			modelSvcReq.setTbBiTrade(trade);
			modelSvcReq.setTradeId(tradeId);
		} else if (modelSvcReq.getSystem_code().equals("28")) {// 则为转帐
		
		}
		logger.info("WebRequestBusiness025002Service webexecute end");
		return;
	}

	@Override
	public void setTradeFlag(ModelSvcReq modelSvcReq) {
		// TODO Auto-generated method stub
		modelSvcReq.setSeqnoFlag("1");
	}

}
