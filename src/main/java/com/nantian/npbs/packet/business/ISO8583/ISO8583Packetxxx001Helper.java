package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 缴费业务查询
 * @author jxw
 *
 */
public class ISO8583Packetxxx001Helper implements IPacketISO8583{
	private static final Logger logger = LoggerFactory.getLogger(ISO8583Packetxxx001Helper.class);

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		
		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 11, system trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);

		//Field 32, User code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);
		
		//Field 41, TerminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (for POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 49, currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);
		
		//Field 55, custom data, for IC data
		unpackField55(fieldValues[55], bm);

		// Field 60 Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}
	
	// unpack for Field 55
	protected void unpackField55(Object Field, BusinessMessage bm) {
	}
	
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		int fieldNo = 0;
		
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues, 11, bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues, 12, bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues, 13, bm);
		
		//Field 15, Pay type
		ISO8583PacketUtils.packField(fieldValues, 15, bm);
		
		//Field 25, FeeType
		ISO8583PacketUtils.packField(fieldValues, 25, bm);
		
		//Field 32, User code
		ISO8583PacketUtils.packField(fieldValues, 32, bm);
		
		//Field 37, PB journal no
		ISO8583PacketUtils.packField(fieldValues, 37, bm);
	
		//Field 38, User name
		ISO8583PacketUtils.packField(fieldValues, 38, bm);
		
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues, 39, bm);
		
		//Field 41, Terminal id
		ISO8583PacketUtils.packField(fieldValues, 41, bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues, 42, bm);
		
		//Field 44, Additional response data
//		fieldNo = 44;
		packField44(bm);		
		ISO8583PacketUtils.packField(fieldValues, 44, bm);
		
		//Field 49, currency code
		ISO8583PacketUtils.packField(fieldValues, 49, bm);
		
		//Field 55, custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 58, additional tip
		ISO8583PacketUtils.packField(fieldValues, 58, bm);
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);

		//Field 64, MAC
		ISO8583PacketUtils.packField(fieldValues, 64, bm);
	}
	
	// complete pack Field 44
	protected String packField44(BusinessMessage bm) 
		throws PacketOperationException {
		return null;
	}
	
	// complete pack Field 55
	protected String packField55(BusinessMessage bm) 
		throws PacketOperationException {
		return null;
	}
}
