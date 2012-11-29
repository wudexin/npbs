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
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.business.ISO8583.ISO8583PacketHelper;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * @author TsaiYee
 *
 */
public class Packet010002Test extends Assert {
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}	
	
	@Test
	public void test8583Pay() {
		//ic pay
		/*String hexString="02a96000038277602200000901020050"+
		"20008114c09211196221881210017188"+
		"56100000000200009009040000103035"+
		"34363137323335370104996221881210"+
		"017188561d1561560000000000000003"+
		"000000214141449121d000000000000d"+
		"000000000000d00000007090600060d1"+
		"a6b7e520202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202030"+
		"35303031303232202020202020202020"+
		"202020313536999eae8722341ffd0448"+
		"30303030303030303030373637393137"+
		"31383730303030303030303030303736"+
		"37393137313837304339334131463642"+
		"38323933304538434433363043383339"+
		"31443835464631323030303030303030"+
		"30303030303030303030303330303030"+
		"31313533303030303033383430303030"+
		"30303030303030303438353630313030"+
		"30303030303030303030303030303030"+
		"30303030303030303030303030303030"+
		"30303030202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202030353436"+
		"31373233353720202020202020202020"+
		"d1a6b7e5202020202020202020202020"+
		"20202020202020202020202020202020"+
		"20202020202020202020202020202020"+
		"202020202020202020202020bad3b1b1"+
		"caa1caafbcd2d7afcad0d0c2bbaac7f8"+
		"b1b1b6febbb7c3f7d6e9b9abd4b0b1b1"+
		"b2e0a3acbaecd0c7d0a1c7f8cef7b2e0"+
		"3323c2a531b5a5d4aa33303220202020"+
		"202020202020202020202020b3c7d5f2"+
		"bed3c3f1b2bbc2fa314b562020202020"+
		"20202020202020202020353220202020"+
		"20202020202020202020302020202020"+
		"34342020202020202020202020202020"+
		"010002b43755519fa390b4";*/

		String hexString="02a96000038069602200000737020050"+
							"20008114c09211196221881210016996"+
							"78200000000208004049950000103032"+
							"38343335383835310104996221881210"+
							"016996782d1561560000000000000003"+
							"000000214141449121d000000000000d"+
							"000000000000d00000004550600060ca"+
							"afc7ef20202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202030"+
							"35323230313138202020202020202020"+
							"2020203135366c3feb7d55e91c1f0448"+
							"30303030303030303030373637393435"+
							"30353032303030303030303030303736"+
							"37393435303530323132333435344345"+
							"39374246324330463739463138434236"+
							"39393142424245433030303030303030"+
							"30303030303030303030303230303030"+
							"30363932303030303030303030303030"+
							"30303230303030303436333330313030"+
							"30303030303030303030303030303030"+
							"30303030303030303030303030303030"+
							"30303030202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202030323834"+
							"33353838353120202020202020202020"+
							"caafc7ef202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202020"+
							"202020202020202020202020b3c7b9d8"+
							"d5f2cbfec9cf20202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202020"+
							"20202020202020202020202020202020"+
							"202020202020202020202020c5a9b4e5"+
							"b5bdbba7bed3c3f12020202020202020"+
							"20202020202020202020353220202020"+
							"20202020202020202020302020202020"+
							"31362020202020202020202020202020"+
							"010002c40fcf7adfcd3d85";
		
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
		assertEquals(new Double(208.00), new Double(bm.getAmount()));
		assertEquals("0", bm.getFeeType());
		assertEquals("010002", bm.getTranCode());
		assertEquals("0284358851", bm.getUserCode());
		assertEquals("156",bm.getCurrencyCode());
		
		cm.setResultCode("000000");
		bm.setLocalDate("20110823");
		bm.setLocalTime("123143");
		bm.setPbSeqno("12405");
		
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
