package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.WaterCashData;

/**
 * 现金代收保定水费查询
 * @author hubo
 *
 */
@Component
public class ISO8583Packet016001Helper extends ISO8583Packetxxx001Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet016001Helper.class);
	
	
	// complete pack field 44
	@Override
	protected String packField44(BusinessMessage bm) {
		
		logger.info("开始打包44位元");
//		ElectricityCashData cashData = (ElectricityCashData) bm.getCustomData();
//		StringBuffer str = new StringBuffer();
//		str.append("用户编号:").append(bm.getUserCode()).append("\n");
//		str.append("用户名称:").append(cashData.getUsername()).append("\n");
//		str.append("欠费金额:").append(cashData.getTotalBill()).append("\n");
//		return str.toString();
		WaterCashData wcd = (WaterCashData)bm.getCustomData();
		if(null == wcd){
			return "查询信息为空！";
		}
		StringBuffer str = new StringBuffer();
		str.append("用户编号：").append(bm.getUserCode()).append("\n");
		str.append("用户名称:").append(wcd.getUsername()).append("\n");
		str.append("欠费金额:").append(wcd.getTotalAmt()).append("\n");
		str.append("应收金额:").append(wcd.getOughtAmt()).append("\n");
		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(str.toString());
		//add by yafang 2012-03-21 end 
		return str.toString();
	}
}
