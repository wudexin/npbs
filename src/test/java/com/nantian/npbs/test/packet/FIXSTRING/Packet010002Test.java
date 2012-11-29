/**
 * 
 */
package com.nantian.npbs.test.packet.FIXSTRING;

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
import com.nantian.npbs.packet.business.FIXSTRING.FIXSTRINGPacketHelper;

/**
 * EPOS缴费测试
 * @author VICTOR
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

		/*String hexString="02a96000038069602200000737020050"+
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
							"010002c40fcf7adfcd3d85";*/
		
		IPacket packetHelper = new FIXSTRINGPacketHelper();
		
		//unpack
		ControlMessage cm = new ControlMessage();
		cm.setChanelType(CHANEL_TYPE.EPOS);
		BusinessMessage bm = new BusinessMessage();
		
		cm.setResultCode("000000");
		bm.setLocalDate("20110823");
		bm.setLocalTime("123143");
		bm.setPbSeqno("12405");
		cm.setTranCode("010002");
		
		Map<DATA_TYPE, Object> message = new HashMap<DATA_TYPE, Object>();
		message.put(DATA_TYPE.CONTROLOBJECT, cm);
		message.put(DATA_TYPE.BUSINESSOBJECT, bm);
		
		//pack
		try {
			packetHelper.packBuffer(message);
		} catch (PacketOperationException e) {
			e.printStackTrace();
		}
		
	}
}
