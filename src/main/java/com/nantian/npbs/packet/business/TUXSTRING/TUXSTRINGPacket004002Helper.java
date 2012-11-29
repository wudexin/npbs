package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 联通手机
 * 
 * @author hubo
 * 
 */
@Component
public class TUXSTRINGPacket004002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONENUM", bm.getUserCode());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONETYPE", "F");
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_OUGHTAMT", bm.getCustomData().toString());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PAYAMT", String.valueOf(bm.getAmount()));
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CERTNO", "");
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CHANNEL_DATE", bm.getTranDate());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CHANNEL_SEQNO", bm.getPbSeqno());
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		 
	}
	 
}
