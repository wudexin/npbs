package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 交易流水列表查询
 * @author 
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness006Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness006Service.class);
	 
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		
		return true;
	}
 
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		// 无业务处理
		logger.info("交易流水列表查询无request业务处理！");
	}
	
	//TODO: 
	protected String tradeType(){
		return "07";
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.service.request.RequestBusinessService#needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");
	}
	

}
