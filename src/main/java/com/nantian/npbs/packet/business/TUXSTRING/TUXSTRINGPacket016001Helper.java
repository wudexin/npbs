package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.WaterCashData;

/**
 * 现金代收保定水费查询
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket016001Helper extends TUXSTRINGPacketxxx001Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CODE", bm.getUserCode());
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		WaterCashData waterCashData = new WaterCashData();

		// 用户编号
		String userNo = (String) fieldValues.get("D13_13_BDW_USERNO");
		if (userNo == null) throw new PacketOperationException();
		waterCashData.setUserNo(userNo);
		
		// 卡片编号
		String cardNo = (String) fieldValues.get("D13_13_BDW_CARDNO");
		if (cardNo == null) throw new PacketOperationException();
		waterCashData.setCardNo(cardNo);
		
		// 用户名称
		String username = (String) fieldValues.get("D13_13_BDW_USERNAME");
		if (username == null) throw new PacketOperationException();
		waterCashData.setUsername(username);
		bm.setUserName(username);
		
		//总欠费金额
		String totalAmt = (String) fieldValues.get("D13_13_BDW_TOTALAMT");
		if (totalAmt == null) throw new PacketOperationException();
		waterCashData.setTotalAmt(totalAmt);
		
		// 应收金额
		String oughtAmt = (String) fieldValues.get("D13_13_BDW_OUGHTAMT");
		if (oughtAmt == null) throw new PacketOperationException();
		waterCashData.setOughtAmt(oughtAmt);

		// 第三方流水号
		String seqno3 = (String) fieldValues.get("D13_13_BDW_SEQNO3");
		if (seqno3 == null) throw new PacketOperationException();
		waterCashData.setSeqno3(seqno3);

		// 发票张数
		String ticketSum = (String) fieldValues.get("D13_13_BDW_TICKETSUM");
		if (ticketSum == null) throw new PacketOperationException();
		waterCashData.setTicketSum(ticketSum);	
		
		bm.setCustomData(waterCashData);		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_BDW_USERNO",   			// 用户编号
				"D13_13_BDW_CARDNO",     		// 卡片编号
				"D13_13_BDW_USERNAME",          // 用户名称
				"D13_13_BDW_TOTALAMT",         //总欠费金额
				"D13_13_BDW_OUGHTAMT",    	 // 应收金额
				"D13_13_BDW_SEQNO3",     		// // 第三方流水号
				"D13_13_BDW_TICKETSUM"  	 // 发票张数				
				};
		return fields;
	}
	
}
