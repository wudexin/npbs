package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 补写卡
 * @author jxw,qxl
 *
 */
public class ISO8583Packetxxx004Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx004Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());
		
		//field 11, system trace number (pos journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 55, Custom data, for IC data
		unpackField55(fieldValues[55], bm);
		
		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);

		//Field 61, 原始信息域
		unpackField61(fieldValues[61],bm);
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}

	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {

		int fieldNo = 0;
		
		//field 11, pos journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//field 12, local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//field 13, local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		//field 37, pb journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//field 39, response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//field 41, terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//field 42, shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		
		//field 44, shop code
		ISO8583PacketUtils.packField(fieldValues,44,bm);
		
		//Field 55, Custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//field 60, tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//field 64, mac
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}
	
	protected void unpackField55(Object field, BusinessMessage bm) {
		
	}
	//TODO: unpack for Field 61
	protected void unpackField61(Object Field, BusinessMessage bm){
	}
	
	protected String packField55(BusinessMessage bm) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
