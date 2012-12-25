package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 现金代收有线电视
 * @author qiaoxl
 *
 */
@Component
public class ISO8583Packet009002Helper extends ISO8583Packetxxx002Helper{
	Logger logger = LoggerFactory.getLogger(ISO8583Packet009002Helper.class);

	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		//判读是否出错
		 if(bm.getResponseCode() != GlobalConst.RESPONSECODE_SUCCESS) {
			 return bm.getResponseMsg();
		 }
	 	
		 //拼接需要打印的信息
		StringBuffer str = new StringBuffer();		
		if(bm.getCustomData() instanceof TVCashData) {
			TVCashData cashData = (TVCashData)bm.getCustomData();
			if(null != cashData) {
				double curAmt = Double.valueOf(cashData.getCurAmt()).doubleValue() + bm.getAmount();
				str.append("账户余额："+ new DecimalFormat("\u00A4###,###.00").format(curAmt)).append("元\n");
			}
		}else {
			logger.info("系统提取数据出错！");
		}		 
		 
		 //拼接备付金低额提醒信息
		 String strlowMind = super.packField44(bm);
		 
		 if(null != strlowMind && !("".equals(strlowMind))) {
			 str.append(strlowMind);
		 }
		 bm.setResponseMsg(str.toString());
		 logger.info("有线电视返回报文44域：{}",str.toString());
		 
		return str.toString();
	}


}
