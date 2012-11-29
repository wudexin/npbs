package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 商户信息下载	000011
 * @author jxw
 *
 */
public class ISO8583Packetxxx011Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx011Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 41, TerminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (for POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);

		//Field 60  TranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		//Field 37, PB journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//Field 41, Terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		
		//Field 44, additional response data
//		fieldNo = 44;
//		fields[fieldNo] = packField44(bm);
//		FieldUtils.assertFieldNull(fieldNo, fields[fieldNo]);
		
		//Field 55, Custom data
		ISO8583PacketUtils.packField(fieldValues,55,bm);
		
		//Field 60, Tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//Field 64, MAC
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}
	
}
