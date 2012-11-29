/**
 * 
 */
package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * @author TsaiYee
 *
 */
public interface IPacketSPLITSTRING {
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException;
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException;
	public String[] hasFields();
	public String[] addFields(String[] fields, int count, SPLITSTRINGFieldsConfig fieldsConfig);
}
