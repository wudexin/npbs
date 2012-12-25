package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 通用交易取消
 * @author MDB
 *
 */
public class ISO8583Packetxxx012Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx012Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {

		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//if payType is green card
		/*if (bm.payTypeIsGreenCard()){
		String account = (String)fieldValues[2];
		if (account == null) throw new PacketOperationException();
		bm.setCustomerAccount(account);*/
	
		//Field  4, Tran amount
		ISO8583PacketUtils.unpackField(fieldValues,4,bm,true);
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 32, User code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);

		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (For POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 49, Currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);
		
		//Field 55, Custom data, For IC data
		unpackField55(fieldValues[55],bm);
		
		//Field 60, Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//Field 61, Old PbSeqno 
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		if(bm.getOldPbSeqno().length()<14)
			throw new PacketOperationException("流水长度错误!");
		bm.setOldPbSeqno(bm.getOldPbSeqno().substring(0,14));
		
	}
	
	//TODO: unpack for Field 55
	protected void unpackField55(Object Field, BusinessMessage bm) {
		
	}

	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		
		int fieldNo = 0;
		
		//Field 2, main account
		/*fieldNo = 2;
		if (bm.payTypeIsGreenCard()) {
			fieldValues[fieldNo] = bm.getCustomerAccount();
			FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		}*/
		
		//Field 4, tran amount
		ISO8583PacketUtils.packField(fieldValues, 4, bm);
		
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues, 11, bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues, 12, bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues, 13, bm);
		
		//Field 32, User code
		ISO8583PacketUtils.packField(fieldValues, 32, bm);
		
		//Field 37, PB journal number
		ISO8583PacketUtils.packField(fieldValues, 37, bm);

		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues, 39, bm);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.packField(fieldValues, 41, bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues, 42, bm);
		
		//Field 44, additional response data
		bm.setResponseMsg(packField44(cm,bm));
		ISO8583PacketUtils.packField(fieldValues, 44, bm);
		
		//Field 49, Currency code
		ISO8583PacketUtils.packField(fieldValues, 49, bm);
		
		//Field 55, Custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 58, Additional tip
		ISO8583PacketUtils.packField(fieldValues, 58, bm);
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);
		
		//Field 64, MAC
		ISO8583PacketUtils.packField(fieldValues, 64, bm);
		
	}
	
	//TODO: complete pack Field 44
	protected String packField44(ControlMessage cm,BusinessMessage bm) {
		return null;
	}
	
	//TODO: complete pack Field 55
	protected String packField55(BusinessMessage bm) {
		return null;
	}
	
}
