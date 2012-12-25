package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 电信查询
 * @author qiaoxl
 *
 */
@Component
public class ISO8583Packet003001Helper extends ISO8583Packetxxx001Helper {

	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet003001Helper.class);
	
	//TODO: complete pack field 44
	@Override
	protected String packField44(BusinessMessage bm) {
		
		logger.info("开始打包44位元");
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return "查询失败!";
		}
		Telecommunications teleData = (Telecommunications) bm.getCustomData();
		if(null == teleData){
			return "查询信息为空！";
		}
		StringBuffer str = new StringBuffer();
		str.append("用户号码:").append(bm.getUserCode()).append("\n");
		str.append("用户姓名:").append(teleData.getUserName()).append("\n");
		//str.append("本期应缴:").append(teleData.getAmt3()).append("\n");
		str.append("帐户余额:").append(teleData.getAmt3()).append("\n");
		
		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(str.toString());
		//add by yafang 2012-03-21 end    

		return str.toString();
		
	}
	
}
