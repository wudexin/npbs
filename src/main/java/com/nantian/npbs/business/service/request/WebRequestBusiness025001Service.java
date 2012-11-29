package com.nantian.npbs.business.service.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
@Scope("prototype")
@Component(value="WebRequestBusiness025001Service")
public class WebRequestBusiness025001Service extends WebRequestBusinessService {
	private static Logger logger = LoggerFactory
	.getLogger(WebRequestBusiness025001Service.class);
	
	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("WebRequestBusiness025001Service webexecute begin");
		String companyCode = modelSvcReq.getCompany_code_fir();
		try {
			TbBiPrepay tbBiPrepay = commonPrepay.getPrepay(companyCode);
			if(tbBiPrepay.equals(null)){
				modelSvcAns.setMessage("查无此商户"); 
				modelSvcAns.setStatus( GlobalConst.TRADE_STATUS_CARD_ORIG); 	 
			}else{
			modelSvcAns.setAcc_balance_fir(tbBiPrepay.getAccBalance().toString());
			modelSvcAns.setBusi_code("025001");
			modelSvcAns.setCredit_amt(tbBiPrepay.getCreditAmt().toString()); 
			modelSvcAns.setCompany_code_fir(tbBiPrepay.getAccno());
			modelSvcAns.setMessage("查询成功");
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_SUCCESS);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("WebRequestBusiness025001Service webexecute end");
		}

	@Override
	public void setTradeFlag(ModelSvcReq modelSvcReq) {
		modelSvcReq.setSeqnoFlag("0");
	}
}
