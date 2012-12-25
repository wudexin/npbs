package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * POS应用程序更新
 * @author MDB
 * 
 */
public class ISO8583Packetxxx907Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx907Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {

		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (For POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 55, Customer data
		ISO8583PacketUtils.unpackField(fieldValues,55,bm,true);
		
		//Field 60, Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		int fieldNo = 55;
		
		//Field 55：程序包
		fieldValues[fieldNo] = (byte[]) bm.getCustomData();
		FieldUtils.assertFieldNull(55, fieldValues[fieldNo]);
	}
}
