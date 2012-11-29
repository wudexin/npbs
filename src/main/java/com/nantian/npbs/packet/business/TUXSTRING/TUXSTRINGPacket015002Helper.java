package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.UnitcomCashData;

/**
 * 现金代收新联通缴费宽带
 * 
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket015002Helper extends TUXSTRINGPacketxxx002Helper {
	
	Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket015002Helper.class);

	

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONENUM", bm.getUserCode());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONETYPE", "D");
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_OUGHTAMT", bm.getCustomData().toString());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PAYAMT", String.valueOf(bm.getAmount()));
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CERTNO", "");
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CHANNEL_DATE", bm.getTranDate());
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CHANNEL_SEQNO", bm.getPbSeqno());
	}

	//解包到便民服务站
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
	
	}
	
	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		return super.hasFields();
	}

}
