package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 现金新联通缴费
 * 
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket004012Helper extends TUXSTRINGPacketxxx012Helper {

	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		return super.hasFields();
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//add by fengyafang 20120816 联通取消
		//原交易流水号
		PacketUtils.addFieldValue(fieldValues, "OLD_SEQ_NO", bm.getSysJournalSeqno());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CHANNEL_DATE",  bm.getTranDate());

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_CHANNEL_SEQNO",  bm.getPbSeqno());
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		super.unpack(fieldValues, cm, bm);
	}


}
