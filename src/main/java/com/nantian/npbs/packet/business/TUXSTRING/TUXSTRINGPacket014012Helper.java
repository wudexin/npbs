package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 华电现金取消
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket014012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// 商务平台流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_SEQNO", bm.getSysJournalSeqno());
		
		// 便民日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_BM_DATE", bm.getTranDate());
		
		// 便民流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_BM_SEQNO", bm.getPbSeqno());
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 商务平台撤销流水
		String sysSerial = (String) fieldValues.get("D13_13_HBE_SEQNO");
		if (sysSerial == null) throw new PacketOperationException();
		bm.setSysJournalSeqno(sysSerial);
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {"D13_13_HBE_SEQNO"};
		return fields;
	}
}
