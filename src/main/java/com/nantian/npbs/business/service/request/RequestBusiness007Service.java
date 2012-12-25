package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 用户交易明细查询
 * @author 
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness007Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness007Service.class);
	
	//TODO: 
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		// TODO Auto-generated method stub
		return false;
	}

	//TODO: 
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		// TODO Auto-generated method stub

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
