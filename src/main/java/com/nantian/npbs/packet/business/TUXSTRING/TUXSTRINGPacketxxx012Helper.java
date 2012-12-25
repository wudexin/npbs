package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 交易取消
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacketxxx012Helper implements IPacketTUXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		//原交易流水号
		PacketUtils.addFieldValue(fieldValues, "OLD_SEQ_NO", bm.getSysJournalSeqno());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CHANNEL_SEQNO",  bm.getTranDate());

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CHANNEL_SEQNO",  bm.getPbSeqno());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
