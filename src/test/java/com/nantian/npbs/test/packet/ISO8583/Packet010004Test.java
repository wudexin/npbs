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
import com.nantian.npbs.packet.internal.ElectricityICCardData;
import com.nantian.npbs.common.utils.ConvertUtils;

/**
 * 缴费业务查询 --
 * 交易名称:		河电IC卡写卡确认	
 * 交易码:		010004
 * @author victor
 *
 */
public class Packet010004Test extends Assert {
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}	
	
	@Test
	public void test8583Pay() {
		// 河电IC卡写卡确认	010004
		
/*		005f60000380f7602200000737080000
		20000000c00211404997202020202020
		20202020202020202020202020203035
		32323031313820202020202020202020
		20200018323031313038323930333832
		3030303130310100042052fe00c965e1
		e3*/
		
		String hexString="005f60000380f7602200000737080000"+
		"20000000c00211404997202020202020"+
		"20202020202020202020202020203035"+
		"32323031313820202020202020202020"+
		"20200018323031313038323930333832"+
		"3030303130310100042052fe00c965e1"+
		"e3s";
		
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
		assertEquals(new Double(0.00), new Double(bm.getAmount()));
		assertEquals("010004", bm.getTranCode());
		
		cm.setResultCode("000000");
		bm.setLocalDate("20110830");
		bm.setLocalTime("123143");
		bm.setPbSeqno("12405");
		
		//增加对55位元测试
		ElectricityICCardData customData = new ElectricityICCardData();
		customData.setCurRecv("10");
		customData.setCurElectric("100");
		customData.setCurBalance("11");
		customData.setWriteElectric("200");
		customData.setOutAuthData("");
		customData.setRemark1("1234567890123456".getBytes());
		customData.setRemark2("12345678901234561234567890123456".getBytes());
		bm.setCustomData(customData);
		
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
