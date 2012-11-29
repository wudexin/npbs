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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.business.ISO8583.ISO8583PacketHelper;

/**
 * 缴费业务查询 --
 * 交易名称:		有线电视查询	
 * 交易码:		009001
 * @author victor
 *
 */
public class Packet009001Test extends Assert {
	
	private static final Logger logger = LoggerFactory.getLogger(Packet009001Test.class);

	@Before
	public void setUp() {
		logger.info("before start  test -----");
	}
	
	@After
	public void tearDown() {
		logger.info("finished test--------");
	}	
	
	@Test
	public void test8583Pay() {
		//有线电视查询	009001

		String hexString="005b60000382e8601000000746020000"+
		"20000100c08011001614001131383733"+
		"323732333236314c414e4449424a3120"+
		"20202020202020202020203036313030"+
		"30333720202020202020202020202031"+		
		"3536009001a482161fd254d2ca";
		
		IPacket packetHelper = new ISO8583PacketHelper();
		
		//unpack
		ControlMessage cm = new ControlMessage();
		cm.setChanelType(CHANEL_TYPE.POS);
		BusinessMessage bm = new BusinessMessage();
		byte[] origPacket = ConvertUtils.hexStr2Bytes(hexString);
		logger.info("origPacket:{}", origPacket);
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
		logger.info("msgtype: " + cm.getPacketHeader().getMessageType());
		assertEquals("0200", cm.getPacketHeader().getMessageType());
		assertEquals(new Double(0.00), new Double(bm.getAmount()));
		assertEquals("009001", bm.getTranCode());
		
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
			logger .info(resultHexString);
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
