package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HuaElecICCard;

/**
 * 华电IC卡卡表信息查询
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket013022Helper extends TUXSTRINGPacketxxx022Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HuaElecICCard sc = (HuaElecICCard)bm.getCustomData();
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_CUSTOMERNO", sc.getUserCode());
		
		// 电表编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_METERNO", sc.getAmmeterCode());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		
		HuaElecICCard sc = (HuaElecICCard)bm.getCustomData();

		// 用户名称
		String username = (String) fieldValues.get("D13_13_HBGB_CUSTOMERNAME");
		if (username == null) throw new PacketOperationException();
		sc.setUserName(username);
		
		// 用户地址
		String address = (String) fieldValues.get("D13_13_HBGB_CUSTOMERADDRESS");
		if (address == null) throw new PacketOperationException();
		sc.setAddress(address);

		// 账户余额
		String accountBalance = (String) fieldValues.get("D13_13_HBGB_USERACCOUNT");
		if (accountBalance == null) throw new PacketOperationException();
		sc.setAccountBalance(accountBalance);
		
		// 购电次数
		String buyElecNum = (String) fieldValues.get("D13_13_HBGB_REBUYTIMES");
		if (buyElecNum == null) throw new PacketOperationException();
		sc.setBuyElecNum(buyElecNum);
		System.out.println("Tuxdo购电次数"+buyElecNum);
		bm.setCustomData(sc);
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HBGB_CUSTOMERNAME",   		// 用户名称
				"D13_13_HBGB_CUSTOMERADDRESS",     	// 用户地址
				"D13_13_HBGB_USERACCOUNT",    		// 账户余额
				"D13_13_HBGB_REBUYTIMES" 			// 购电次数
		};
		return fields;
	}
	
}
