package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.WaterCashData;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 联通手机
 * 
 * @author hubo
 * 
 */
@Component
public class TUXSTRINGPacket002002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONENUM", bm.getUserCode());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONETYPE", "G");
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
