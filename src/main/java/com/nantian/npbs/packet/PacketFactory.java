/**
 * PacketFactory.java
 *
 * Created by TsaiYee  at Nov 11, 2010 5:35:15 PM
 */
package com.nantian.npbs.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
/**
 * @author TsaiYee
 *
 */
public class PacketFactory {
	
	private static Logger logger = LoggerFactory.getLogger(PacketFactory.class);
	
	public static IPacket factory(CHANEL_TYPE chanelType) {
		try {
			return (IPacket) SpringContextHolder.getBean(PacketConfig.getPacketHelperBean(chanelType));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取packetHelper Bean出现错误！",e);
		}
		
		return null;
	}
}