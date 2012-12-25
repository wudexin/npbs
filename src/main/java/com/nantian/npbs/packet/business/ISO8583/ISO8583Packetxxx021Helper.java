package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 备付金密码修改
 * @author jxw
 *
 */
public class ISO8583Packetxxx021Helper implements IPacketISO8583 {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx021Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//从fieldValues中取得上传报文中对应bitmap的值
		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (For POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 52, Old pin data	
		ISO8583PacketUtils.unpackField(fieldValues,52,bm,true);
		
		//Field 53, New pin data	
		ISO8583PacketUtils.unpackField(fieldValues,53,bm,true);
		
		//Field 60, Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
	
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		//对fieldValues中对应bitmap的赋值，解包结束后不打包原包数据
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues, 11, bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues, 12, bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues, 13, bm);
		
		//Field 37, PB journal number
		ISO8583PacketUtils.packField(fieldValues, 37, bm);
	
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues, 39, bm);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.packField(fieldValues, 41, bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues, 42, bm);
		
		//Field 44, Additional response data
		ISO8583PacketUtils.packField(fieldValues, 44, bm);
	
		//Field 52, Old personal data
		ISO8583PacketUtils.packField(fieldValues, 52, bm);
		
		//Field 53, Now personal data
		ISO8583PacketUtils.packField(fieldValues, 54, bm);
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);
		
		//Field 64 MAC
		ISO8583PacketUtils.packField(fieldValues, 64, bm);
		
	}
}
