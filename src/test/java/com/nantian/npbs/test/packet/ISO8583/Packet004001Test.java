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
 * 交易名称:		联通固话查询
 * 交易码:		004001
 * @author victor
 *
 */
public class Packet004001Test extends Assert {
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}	
	
	@Test
	public void test8583Pay() {
		//联通固话查询		004001
		
/*		0058600003827e602200000878020000
		20000100c08011219121000838363033
		30393336202020202020202020202020
		20202020202020203035303033303330
		20202020202020202020202031353600
		40013fb14aadf15a1bc6*/

		String hexString="0058600003827e602200000878020000"+
		"20000100c08011219121000838363033"+
		"30393336202020202020202020202020"+
		"20202020202020203035303033303330"+
		"20202020202020202020202031353600"+		
		"40013fb14aadf15a1bc6";
		
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
		assertEquals("0200", cm.getPacketHeader().getMessageType());
		assertEquals(new Double(0.00), new Double(bm.getAmount()));
		assertEquals("004001", bm.getTranCode());
		
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
