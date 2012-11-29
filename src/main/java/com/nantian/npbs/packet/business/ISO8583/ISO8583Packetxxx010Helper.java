package com.nantian.npbs.packet.business.ISO8583;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 末笔交易查询	000010
 * @author jxw
 *
 */

public class ISO8583Packetxxx010Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx010Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());
		
		//field 11, system trace number (pos journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		
		//field 32, user code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);
		
		//field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//field 49, currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);

		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//field 61, orig data, for queryStartDate data queryEndDate
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		logger.info("末笔交易查询，61域为pos流水号：[{}]",bm.getOldPbSeqno());
		bm.setOrigPosJournalSeqno(bm.getOldPbSeqno());
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
		
	}

	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		//field 11, pos journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//field 12, local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//field 13, local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		if("010".equals(bm.getBusinessType().trim())) {
			bm.setPbSeqno(bm.getOrigPosJournalSeqno());
		}
		//field 37, pb journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//field 39, response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//field 41, terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//field 42, shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		
		//field 44, additional response data
		ISO8583PacketUtils.packField(fieldValues,44,bm);
		
		//field 49, currency code
		ISO8583PacketUtils.packField(fieldValues,49,bm);
		
		//field 61, old pb journal no
		if(!"010".equals(bm.getBusinessType().trim())) {
			bm.setOrigPosJournalSeqno(bm.getOldPbSeqno());
		}		
		ISO8583PacketUtils.packField(fieldValues,61,bm);
		
		//field 60, tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//field 64, mac
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}

}
