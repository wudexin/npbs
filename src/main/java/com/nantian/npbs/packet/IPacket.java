/**
 * IPacketIn.java
 *
 * Created by TsaiYee , Nov 17, 2010
 */
package com.nantian.npbs.packet;

import java.util.Map;


import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
/**
 * @author TsaiYee
 *
 */
public interface IPacket {
	public Map<DATA_TYPE, Object> packBuffer(Map<DATA_TYPE, Object> message) throws PacketOperationException;
	public Map<DATA_TYPE, Object> unpackObject(Map<DATA_TYPE, Object> message) throws PacketOperationException;
	public Map<DATA_TYPE, Object> packErrorBuffer(Map<DATA_TYPE, Object> message);
}