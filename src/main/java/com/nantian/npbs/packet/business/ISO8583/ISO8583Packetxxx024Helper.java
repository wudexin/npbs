package com.nantian.npbs.packet.business.ISO8583;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 华电IC卡写卡取消
 * @author MDB
 *
 */
public class ISO8583Packetxxx024Helper implements IPacketISO8583{

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
	}
	
	protected void unpackField55(Object Field,BusinessMessage bm){
	}
	
	protected void unpackField61(Object Field,BusinessMessage bm){
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
	}
	
	protected String packField44(BusinessMessage bm) 
		throws PacketOperationException{
		return null;
	}
	
	protected String packField55 (BusinessMessage bm)
		throws PacketOperationException{
		return null;
	}
	
}
