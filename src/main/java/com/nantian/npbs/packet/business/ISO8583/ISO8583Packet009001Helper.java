package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 有线电视查询
 * @author wzd
 *
 */
@Component
public class ISO8583Packet009001Helper extends ISO8583Packetxxx001Helper {
	
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet009001Helper.class);

	@Override
	protected String packField44(BusinessMessage bm) {
		
		StringBuffer str = new StringBuffer();
		logger.info("打包44域内容");
		
		TVCashData cashData = (TVCashData) bm.getCustomData();
		
		if(null != cashData) {
			str.append("客户名称：").append(cashData.getCustomerName()).append("\n");
			str.append("账户编码：").append(cashData.getAccNo()).append("\n");
			str.append("账户余额：").append(cashData.getCurAmt()).append("\n");
		}else if(!"".equals(bm.getAdditionalTip()) && null != bm.getAdditionalTip()) {
			str.append(bm.getAdditionalTip()).append("\n");      
		}else if(!"".equals(bm.getResponseMsg()) && null != bm.getResponseMsg()) {
			str.append(bm.getResponseMsg()).append("\n");      //若调用出现异常请求时，打包给pos提示信息
		}
			
		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(str.toString());
		//add by yafang 2012-03-21 end  
		return bm.getResponseMsg();
		
	}

}
