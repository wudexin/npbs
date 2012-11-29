/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * @author TsaiYee
 *
 */
public class ISO8583Packetxxx002Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx002Helper.class);

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());
		
		//绿卡卡号不作处理
		/*//Field 2, main account
		ISO8583PacketUtils.unpackField(fieldValues,2,bm,true);*/
	
		//Field 4, Tran amount
		ISO8583PacketUtils.unpackField(fieldValues,4,bm,true);
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 15, Pay type
		ISO8583PacketUtils.unpackField(fieldValues,15,bm,false);
		bm.setPayType(bm.getFlagField().substring(0, 1));
		
		//Field 25, Debts or save flag
		ISO8583PacketUtils.unpackField(fieldValues,25,bm,true);
		
		//Field 32, User code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);
		
		//Field 37, PB Serial
		ISO8583PacketUtils.unpackField(fieldValues,37,bm,true);
		bm.setOldPbSeqno(bm.getPbSeqno());
		
		//Field 38, User name
		ISO8583PacketUtils.unpackField(fieldValues,38,bm,true);
		
		//Field 41, TerminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (for POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 49, Currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);
		
		//Field 52, Pin Data
		ISO8583PacketUtils.unpackField(fieldValues,52,bm,true);
		
		//Field 55, Custom data, for IC data
		unpackField55(fieldValues[55], bm);

		// Field 60 Tran Code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}
	
	// unpack for Field 55
	protected void unpackField55(Object Field, BusinessMessage bm) {
		
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		int fieldNo = 0;
		//Field  2, Main account
		ISO8583PacketUtils.packField(fieldValues,2,bm);
		
		//Field  4, Tran amount
		ISO8583PacketUtils.packField(fieldValues,4,bm);
		
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		//Field 15, Pay type
		ISO8583PacketUtils.packField(fieldValues,15,bm);
		
		//Feild 32, User code
		ISO8583PacketUtils.packField(fieldValues,32,bm);
		
		//Field 37, PB journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//Field 38, User name
		ISO8583PacketUtils.packField(fieldValues,38,bm);
		
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//Field 41, Terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		
		//Field 44, Additional response data
		packField44(bm);
		ISO8583PacketUtils.packField(fieldValues,44,bm);
		
		//Field 49, Currency code
		ISO8583PacketUtils.packField(fieldValues,49,bm);
		
		//Field 55, Custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 58, Additional tip
		ISO8583PacketUtils.packField(fieldValues,58,bm);
		
		//Field 60, Tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//Field 64, MAC
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}
	
	/**
	 * 拼接低额提醒
	 */
	protected String packField44(BusinessMessage bm) 
		throws PacketOperationException{
		StringBuffer stringBuffer = new StringBuffer();
		//是否需要低额提醒
		logger.info("开始打包44位元");
		// 是否系统出错
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return bm.getResponseMsg();
		}
		else if(null != bm.getAdditionalTip() && !"".equals(bm.getAdditionalTip())){
			//需要进行低额提醒
			//获取提醒信息
			stringBuffer.append("\n");
			stringBuffer.append("\n");
			stringBuffer.append("\n");
			stringBuffer.append("          提示商户!!!        \n");
			stringBuffer.append("--------------------------------\n");
			stringBuffer.append(bm.getAdditionalTip());
		}
		logger.info("低额提醒信息：" + stringBuffer.toString());
		bm.setResponseMsg(stringBuffer.toString());
		return stringBuffer.toString();
	}

	/**
	 * IC卡交易写卡数据
	 */
	protected String packField55(BusinessMessage bm) 
		throws PacketOperationException {
		return null;
	}
	
}
