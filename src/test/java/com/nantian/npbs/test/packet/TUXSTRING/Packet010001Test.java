/**
 * 
 */
package com.nantian.npbs.test.packet.TUXSTRING;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.business.ISO8583.ISO8583PacketHelper;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 缴费业务查询 --
 * 交易名称:		河电IC卡查询	
 * 交易码:		010001
 * @author victor
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class Packet010001Test extends Assert {

	@Resource
	ISO8583PacketHelper packetHelper;
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}	
	
	@Test
	public void test8583Pay() {
		//河电IC卡查询	010001
		
		/*012a6000038099602200000737020000
		20000100c08211404994001637394631
		38434236393931424242454347505253
		3a202020202000002020202020202020
		30353232303131382020202020202020
		20202020313536020030303030303030
		30303037363739343530353032313233
		34353443453937424632433046373946
		31384342363939314242424543303030
		30303030303030303030303030303030
		32303030303036393230303030303030
		30303030303030323030303030343633
		33303130303030303030303030303030
		30303030303030303030303030303030
		30303030303030303020202020202020
		20202020202020202020202020202020
		20202020202020202020202020202020
		20202020202020202020202020202020
		20010001968cc5fdac45432d*/

		String hexString="012a6000038099602200000737020000"+
		"20000100c08211404994001637394631"+
		"38434236393931424242454347505253"+
		"3a202020202000002020202020202020"+
		"30353232303131382020202020202020"+
		"20202020313536020030303030303030"+
		"30303037363739343530353032313233"+
		"34353443453937424632433046373946"+
		"31384342363939314242424543303030"+
		"30303030303030303030303030303030"+
		"32303030303036393230303030303030"+
		"30303030303030323030303030343633"+
		"33303130303030303030303030303030"+
		"30303030303030303030303030303030"+
		"30303030303030303020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20010001968cc5fdac45432d";

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
		assertEquals("010001", bm.getTranCode());
		
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
