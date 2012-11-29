package com.nantian.npbs.business.service.request;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 对账文件申请
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness806Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness806Service.class);


	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("对账文件申请Request无业务逻辑!");
	}

	protected String tradeType() {
		return "07";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.nantian.npbs.business.service.request.RequestBusinessService#
	 * needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		 bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag("1");
	}
}