package com.nantian.npbs.business.service.internal;

import com.nantian.npbs.packet.internal.Telecommunications;

public class TelePhoneNumUtils {

	/**
	 * 判断电信号码输入是否符合,并设置银行代码、银服务类型
	 * @param bm
	 * @return
	 */
	public static boolean getBankNoAndServiceType(Telecommunications teleData,String phoneNo){
		
			if(phoneNo.length()==11){
				teleData.setBankNo("6C");
				teleData.setServiceType("2");
				return true;
			} else if ( phoneNo.length() <= 8 ){  
				teleData.setBankNo("6P");
				teleData.setServiceType("6");
				return true;
			 } else{
			      	return false;   
			} 
			
	}

}
