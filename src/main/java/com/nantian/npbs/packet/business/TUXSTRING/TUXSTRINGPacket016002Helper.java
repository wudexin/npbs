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
public class TUXSTRINGPacket016002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		WaterCashData waterCashData = (WaterCashData)bm.getCustomData();
		
		
		
		// 实际交费
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_PAYAMT", waterCashData.getPayAmt());
		
		// 应收金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_BDW_OUGHTAMT", waterCashData.getSumFee());
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_BDW_USERNO",waterCashData.getUserNo());
		
		// 第三方流水号
		PacketUtils.addFieldValue(fieldValues, "D13_13_BDW_SEQNO3", waterCashData.getSeqno3());
		
		// 发票张数
		PacketUtils.addFieldValue(fieldValues, "D13_13_BDW_TICKETSUM", waterCashData.getTicketSum());
		
		// 接入渠道日期
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",bm.getTranDate());
		
		// 接入渠道流水号
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",bm.getPbSeqno());
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {};
		return fields;
	}
	
}
