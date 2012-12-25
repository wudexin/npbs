package com.nantian.npbs.business.service.request;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.internal.TelePhoneNumUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 业务查询
 * 
 * @author 7tianle
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness003002Service extends RequestBusiness002Service{

	/**
	 * 检查电话号码
	 * @param bm
	 * @return
	 */
	@Override
	protected void checkPhoneNum(BusinessMessage bm){

		Telecommunications teleData = new Telecommunications();
		if(TelePhoneNumUtils.getBankNoAndServiceType(teleData,bm.getUserCode())){
			bm.setCustomData(teleData);
		}
	}
	
}
