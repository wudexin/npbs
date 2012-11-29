/**
 * 
 */
package com.nantian.npbs.test.packet.ISO8583;

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
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 缴费业务查询 --
 * 交易名称:		备付金明细查询	
 * 交易码:		000005
 * @author victor
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class Packet000005Test extends Assert {

	@Resource
	IPacket packetHelperISO8583;

	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}	
	
	@Test
	public void test8583Pay() {
		// 备付金明细查询	000005
		
		/*00676000038009602200000857020000
		20000100c08019110120001030323831
		39313430363531323334353637382020
		20202020202020202020303532313031
		39342020202020202020202020203135
		36000005001132303131303832383030
		312dddd89cca21af63*/
		
		String hexString="00676000038009602200000857020000"+
		"20000100c08019110120001030323831"+
		"39313430363531323334353637382020"+
		"20202020202020202020303532313031"+
		"39342020202020202020202020203135"+
		"36000005001132303131303832383030"+
		"312dddd89cca21af63";
				
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
			packetHelperISO8583.unpackObject(map);
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cm = (ControlMessage)map.get(DATA_TYPE.CONTROLOBJECT);
		bm = (BusinessMessage)map.get(DATA_TYPE.BUSINESSOBJECT);
		System.out.println("msgtype: " + cm.getPacketHeader().getMessageType());
		assertEquals("0200", cm.getPacketHeader().getMessageType());
		assertEquals(new Double(0.00), new Double(bm.getAmount()));
		assertEquals("000005", bm.getTranCode());
		
		cm.setResultCode("000000");
		bm.setLocalDate("20110830");
		bm.setLocalTime("123143");
		bm.setPbSeqno("12405");
		
		Map<DATA_TYPE, Object> message = new HashMap<DATA_TYPE, Object>();
		message.put(DATA_TYPE.CONTROLOBJECT, cm);
		message.put(DATA_TYPE.BUSINESSOBJECT, bm);
		
		//pack
		try {
			packetHelperISO8583.packBuffer(message);
			String resultHexString = ConvertUtils.bytes2HexStr((byte[])PacketUtils.getOrigAnsPacket(message));
			System.out.println(resultHexString);
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
