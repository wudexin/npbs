package com.nantian.npbs.packet.business.ISO8583;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 补写写卡撤销补写卡
 * @author MDB
 *
 */
@Component
public class ISO8583Packetxxx025Helper implements IPacketISO8583 {
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,	BusinessMessage bm) 
			throws PacketOperationException {
	}
	
	protected void unpackField37(Object Field,BusinessMessage bm){}
	
	protected void unpackField61(Object Field,BusinessMessage bm){
	}
	
	protected void unpackField55(Object Field,BusinessMessage bm) 
			throws PacketOperationException{
		return;
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
	}
	
	protected String packField55(BusinessMessage bm) 
			throws PacketOperationException{
		return (String)bm.getCustomData();
	}
	
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
		return null;
	}
}
