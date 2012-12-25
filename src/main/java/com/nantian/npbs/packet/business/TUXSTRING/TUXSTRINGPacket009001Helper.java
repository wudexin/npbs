package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.TVCashData;
import com.nantian.npbs.packet.internal.XAGasCashData;

/**
 * 现金代收有线电视
 * 
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket009001Helper extends TUXSTRINGPacketxxx001Helper {
	
	Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket009001Helper.class);

	//打包发送至电子商务平台
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
	
		
		//客户编码
		PacketUtils.addFieldValue(fieldValues, "D13_16_CUSTOMERNO",bm.getUserCode());		
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		TVCashData cashData = new TVCashData();
		
		//用户编号
		String customerNo = (String)fieldValues.get("D13_16_CUSTOMERNO");
		if(customerNo == null) throw new PacketOperationException();
		cashData.setCustomerNo(customerNo);
		
		//账户编码
		String accNo = (String)fieldValues.get("D13_16_ACCNO");
		if(accNo == null) throw new PacketOperationException();
		cashData.setAccNo(accNo);
		
		//业务类型
		String serviceType = (String)fieldValues.get("D13_16_SERVICETYPE");
		if(serviceType == null) throw new PacketOperationException();
		cashData.setServiceType(serviceType);
		
		//服务号码
		String subscriberNo = (String)fieldValues.get("D13_16_SUBSCRIBERNO");
		if(subscriberNo == null) throw new PacketOperationException();
		cashData.setSubscriberNo(subscriberNo);
		
		//客户名称
		String customerName = (String)fieldValues.get("D13_16_CUSTOMERNAME");
		if(customerName == null) throw new PacketOperationException();
		bm.setUserName(customerName);
		cashData.setCustomerName(customerName);
		
		//余额账本编码
		String accBookNo = (String)fieldValues.get("D13_16_ACCBOOKNO");
		if(accBookNo == null) throw new PacketOperationException();
		cashData.setAccBookNo(accBookNo);
		
		//当前余额
		String curAmt = (String)fieldValues.get("D13_16_CURAMT");
		if(curAmt == null) throw new PacketOperationException();
		cashData.setCurAmt(curAmt);		
		
		bm.setCustomData(cashData);
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_16_CUSTOMERNO",            //用户编号  
				"D13_16_ACCNO",                 //账户编码
				"D13_16_SERVICETYPE",           //业务类型
				"D13_16_SUBSCRIBERNO",          //服务号码
				"D13_16_CUSTOMERNAME",          //客户名称
				"D13_16_ACCBOOKNO",             //余额账本编码
				"D13_16_CURAMT"                 //当前余额				 
		};
		
		return fields;
	}
	
	
	
	
}
