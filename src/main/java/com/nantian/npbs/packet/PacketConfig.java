/**
 * PacketConfig.java
 *
 * Created by TsaiYee , Nov 18, 2010
 */
package com.nantian.npbs.packet;

import java.util.Hashtable;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.PACKET_TYPE;

/**
 * @author TsaiYee
 * 
 */
public final class PacketConfig {

	private static Hashtable<CHANEL_TYPE, PACKET_TYPE> supportPacketType = new Hashtable<CHANEL_TYPE, PACKET_TYPE>();
	private static boolean supportPacketTypeIsInit = false;
	
	public static String getPacketHelperBean(CHANEL_TYPE channelType) {
		checkInit();

		String packetClassName = getPacketType(channelType) + "PacketHelper";

		return packetClassName;
	}

	private static void checkInit() {
		if (!supportPacketTypeIsInit) {
			initSupportedPacketType();
			supportPacketTypeIsInit = true;
		}
	}

	public static String getPacketType(CHANEL_TYPE channelType) {
		checkInit();
		return supportPacketType.get(channelType).name();
	}

	private static void initSupportedPacketType() {
		supportPacketType.put(CHANEL_TYPE.ELEBUSISERIVCE, PACKET_TYPE.TUXSTRING);
		supportPacketType.put(CHANEL_TYPE.POS, PACKET_TYPE.ISO8583);
		supportPacketType.put(CHANEL_TYPE.EPOS, PACKET_TYPE.FIXSTRING);
	//	supportPacketType.put(CHANEL_TYPE.WEB, PACKET_TYPE.FIXSTRING);
		supportPacketType.put(CHANEL_TYPE.ELEBUSIREQUEST, PACKET_TYPE.SPLITSTRING);
	}
}
