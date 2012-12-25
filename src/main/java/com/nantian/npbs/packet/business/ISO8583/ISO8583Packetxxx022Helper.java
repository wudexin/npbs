package com.nantian.npbs.packet.business.ISO8583;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 卡查询
 * @author MDB
 *
 */
public class ISO8583Packetxxx022Helper implements IPacketISO8583 {
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,	BusinessMessage bm) 
			throws PacketOperationException {
	}
	
	protected void unpackField55(Object Field,BusinessMessage bm) 
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
