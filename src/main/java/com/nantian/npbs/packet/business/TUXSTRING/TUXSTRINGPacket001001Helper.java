package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 移动手机查询
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket001001Helper extends TUXSTRINGPacketxxx001Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// TODO Auto-generated method stub
		
		// 电话号码
		PacketUtils.addFieldValue(fieldValues, "D13_10_PHONE", bm.getUserCode());
		
		// 交费标志
		//2012.1.5 0:26分，与张彦彬和王哲确认输入为1，李辉凯，杨朝霞
		PacketUtils.addFieldValue(fieldValues, "D13_10_PAY_TYPE", "1");  //0号码;1帐号

		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		MobileData mobileData = new MobileData();

		// 用户名称
		String username = (String) fieldValues.get("D13_10_NAME");
		if (username == null) throw new PacketOperationException();
		mobileData.setUserName(username);
		bm.setUserName(username); //返回pos
		
		// 当月话费
		String amt = (String) fieldValues.get("D13_10_AMT");
		if (amt == null) throw new PacketOperationException();
		mobileData.setAmt(amt);
		
		// 应收金额
		String fee = (String) fieldValues.get("D13_10_FEE");
		if (fee == null) throw new PacketOperationException();
		mobileData.setFee(fee);
		
		bm.setCustomData(mobileData);

	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_10_NAME",   		// 用户名称
				"D13_10_AMT",     			// 当月话费
				"D13_10_FEE"    			// 应收金额
				};
		return fields;
	}
	
}
