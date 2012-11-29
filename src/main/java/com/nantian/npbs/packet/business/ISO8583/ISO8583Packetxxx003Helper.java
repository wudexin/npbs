package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 写卡交易
 * @author jxw
 * 
 */
//由于华电国标卡、河电省标卡、河电国标卡流程不一致
public class ISO8583Packetxxx003Helper implements IPacketISO8583{
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
	}
	
	//TODO: unpack for Field 55
	protected void unpackField55(Object Field, BusinessMessage bm) 
			throws PacketOperationException{
	}
	
	//TODO: unpack for Field 61
	protected void unpackField61(Object Field, BusinessMessage bm)
			throws PacketOperationException{
	}
	
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
	}
	
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException{
		return null;
	}
	
	protected String packField55(BusinessMessage bm) 
			throws PacketOperationException{
		return null;
	}
	
}
