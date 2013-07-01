/**
 * 
 */
package com.nantian.npbs.packet.business.WEB;

import java.util.Map;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * WEB报文接口类
 * @author  jxw
 *
 */
public interface IPacketWEBSTRING {
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException;
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException;
	public String[] hasFields();
}
