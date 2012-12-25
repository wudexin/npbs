package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 现金代收有线电视
 * 
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket009012Helper extends TUXSTRINGPacketxxx012Helper {

//	打包发送至电子商务平台
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//原交易流水号
		PacketUtils.addFieldValue(fieldValues, "D13_16_SEQNO", bm.getSysJournalSeqno());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "D13_16_BM_DATE",  bm.getTranDate());

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "D13_16_BM_SEQNO",  bm.getPbSeqno());
	}


	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		TVCashData cashData = new TVCashData();
		
		//平台撤销流水
		String curSysSerial = (String)fieldValues.get("D13_16_SEQNO");
		if(curSysSerial == null) throw new PacketOperationException();
		
		cashData.setCurSysSerial(curSysSerial);
		
		bm.setCustomData(cashData);
		
	}
	

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_16_SEQNO"
		};
		return fields;
	}
}
