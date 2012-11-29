package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.internal.TelePhoneNumUtils;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 电信查询
 * 
 * @author 7tianle
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness003001Service extends RequestBusiness001Service{
	private static Logger logger = LoggerFactory
	.getLogger(RequestBusiness003001Service.class);
	/**
	 * 检查电话号码
	 * @param bm
	 * @return
	 */
	@Override
	protected boolean checkPhoneNum(ControlMessage cm,BusinessMessage bm){
		Telecommunications teleData = new Telecommunications();
		if(!TelePhoneNumUtils.getBankNoAndServiceType(teleData,bm.getUserCode())){
			logger.error("电话号码长度不符!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("电话号码长度不符!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("电话号码长度不符!");
			
			return false;
		}
		bm.setCustomData(teleData);
		return true;
	}
	
}
