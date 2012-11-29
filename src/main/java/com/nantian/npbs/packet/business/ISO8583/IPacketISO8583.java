/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * @author TsaiYee
 *
 */
public interface IPacketISO8583 {
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException;
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException;
}
