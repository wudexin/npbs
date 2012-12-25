package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 邯郸燃气取消
 * @author jxw
 *
 */
@Component
public class TUXSTRINGPacket005012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 原交易流水号（电子商务平台）
		PacketUtils.addFieldValue(fieldValues, "D13_10_TRAN_SEQNO", bm.getOrigSysJournalSeqno()); 
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate());
		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// 无
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
					// 无
				};
		
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}
