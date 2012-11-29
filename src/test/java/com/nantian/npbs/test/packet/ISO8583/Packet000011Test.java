/**
 * 
 */
package com.nantian.npbs.test.packet.ISO8583;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.business.ISO8583.ISO8583PacketHelper;
import com.nantian.npbs.common.utils.ConvertUtils;

/**
 * 缴费业务查询 --
 * 交易名称:		商户信息下载	
 * 交易码:		000011
 * @author victor
 *
 */
public class Packet000011Test extends Assert {
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}	
	
	@Test
	public void test8583Pay() {
		// 商户信息下载	000011
		
		String hexString="004860000882c6602200007830080000"+
		"00000000c00011202020202020202020"+
		"20202020202020202020203035303331"+
		"31323520202020202020202020202000"+
		"0010ec41d71e7db3ec22";
		
		IPacket packetHelper = new ISO8583PacketHelper();
		
		//unpack
		ControlMessage cm = new ControlMessage();
		cm.setChanelType(CHANEL_TYPE.POS);
		BusinessMessage bm = new BusinessMessage();
		byte[] origPacket = ConvertUtils.hexStr2Bytes(hexString);
		Map<DATA_TYPE, Object> map = new HashMap<DATA_TYPE, Object>();
		map.put(DATA_TYPE.ORIGREQPACKET, origPacket);
		map.put(DATA_TYPE.CONTROLOBJECT, cm);
		map.put(DATA_TYPE.BUSINESSOBJECT, bm);
		try {
			packetHelper.unpackObject(map);
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cm = (ControlMessage)map.get(DATA_TYPE.CONTROLOBJECT);
		bm = (BusinessMessage)map.get(DATA_TYPE.BUSINESSOBJECT);
		System.out.println("msgtype: " + cm.getPacketHeader().getMessageType());
		assertEquals("0800", cm.getPacketHeader().getMessageType());
		assertEquals("000010", bm.getTranCode());
		
		cm.setResultCode("000000");
		bm.setLocalDate("20110830");
		bm.setLocalTime("123143");
		bm.setPbSeqno("12405");
		
		Map<DATA_TYPE, Object> message = new HashMap<DATA_TYPE, Object>();
		message.put(DATA_TYPE.CONTROLOBJECT, cm);
		message.put(DATA_TYPE.BUSINESSOBJECT, bm);
		
		//pack
		try {
			packetHelper.packBuffer(message);
			String resultHexString = ConvertUtils.bytes2HexStr((byte[])PacketUtils.getOrigAnsPacket(message));
			System.out.println(resultHexString);
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
