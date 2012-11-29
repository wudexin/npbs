package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.XAGasCashData;

/**
 * 现金代收新奥燃气缴费
 * 
 * @author MDB
 */
@Component
public class ISO8583Packet008002Helper extends ISO8583Packetxxx002Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet008002Helper.class);

	@Override
	protected String packField44(BusinessMessage bm) {
		logger.info("开始打包44位元");
		// 是否系统出错
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return   bm.getResponseMsg();
		}
		XAGasCashData cashData = (XAGasCashData) bm.getCustomData();
		StringBuffer str = new StringBuffer();
		//str.append("最新余额:").append(cashData.getAmount()).append("元\n");		
		
		//拼接备付金低额提醒信息
		try {
			String  lowerMessage = super.packField44(bm);
			if(null != lowerMessage && !"".equals(lowerMessage)) {
				str.append(lowerMessage);
			}
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		bm.setResponseMsg(str.toString());
		
//		logger.info("新奥现金缴费返回报文44位元{}",str.toString());
		
		return bm.getResponseMsg();
	}
}
