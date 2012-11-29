package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.XAGasCashData;

/**
 * 现金代收新奥燃气查询
 * 
 * @author MDB
 */
@Component
public class ISO8583Packet008001Helper extends ISO8583Packetxxx001Helper {
	
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet008001Helper.class);
	
	@Override
	protected String packField44(BusinessMessage bm) {

		logger.info("开始打包44位元");
		
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}
			
		XAGasCashData cashData = (XAGasCashData) bm.getCustomData();
		
		StringBuffer str = new StringBuffer();
		str.append("用户名称:").append(cashData.getUserName()).append("\n");
		str.append("用户地址:").append(cashData.getUserAdd()).append("\n");
		str.append("账户余额:").append(cashData.getAccBalance()).append("\n");
		
//		logger.info("查询显示："+str.toString());
		bm.setResponseMsg(str.toString());
		return bm.getResponseMsg();
	}
	
}
