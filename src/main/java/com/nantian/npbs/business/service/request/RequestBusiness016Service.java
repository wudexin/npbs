package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金预存
 * @author 
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness016Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness016Service.class);
	
	//TODO: 
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		if(!checkShopState(cm,bm)){   // 检查商户状态
			return false;
		}
		if(!checkSignState(cm,bm)){   // 检查商户签到
			return false;
		}
		if(!checkShopBindBusiness(cm,bm)){ // 检查商户是否有该业务
			return false;
		}
		logger.info("公共校验成功!");
		return true;
	}

	//TODO: 
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("备付金预存，无request业务逻辑");

	}
	
	protected String tradeType(){
		return "01";
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.service.request.RequestBusinessService#needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		// TODO Auto-generated method stub
		return true;
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
