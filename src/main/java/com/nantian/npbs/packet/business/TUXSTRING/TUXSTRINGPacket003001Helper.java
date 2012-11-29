package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 电信查询
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket003001Helper extends TUXSTRINGPacketxxx001Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// TODO Auto-generated method stub
		Telecommunications teleData = (Telecommunications) bm.getCustomData();
		
		// 电话号码
		PacketUtils.addFieldValue(fieldValues, "D13_11_PHONE_NO", bm.getUserCode());
		
		// 欠费金额
		PacketUtils.addFieldValue(fieldValues, "D13_11_FEE", "");

		// 本期应缴
		PacketUtils.addFieldValue(fieldValues, "D13_11_AMT3", "");
		
		// 银行代码
		PacketUtils.addFieldValue(fieldValues, "D13_11_BANK_NO", teleData.getBankNo());

		// 银服务类型
		PacketUtils.addFieldValue(fieldValues, "D13_11_SERV_TYPE", teleData.getServiceType());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		Telecommunications teleData = new Telecommunications();

		// 用户姓名
		String username = (String) fieldValues.get("D13_11_CUSTOM_NAME");
		if (username == null) throw new PacketOperationException();
		teleData.setUserName(username);
		bm.setUserName(username); //返回pos
		
		// 帐户余额
		String amt3 = (String) fieldValues.get("D13_11_AMT3");
		if (amt3 == null) throw new PacketOperationException();
		teleData.setAmt3(String.valueOf(-Double.parseDouble(amt3)));
		
		// 欠费金额
		String fee = (String) fieldValues.get("D13_11_FEE");
		if (fee == null) throw new PacketOperationException();
		teleData.setFee(fee);
		
		bm.setCustomData(teleData);
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_11_CUSTOM_NAME",   	// 用户姓名
				"D13_11_AMT3",     					// 本期应缴
				"D13_11_FEE"    						// 欠费金额
				};
		return fields;
	}
}
