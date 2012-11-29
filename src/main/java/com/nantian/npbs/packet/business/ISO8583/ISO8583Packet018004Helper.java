package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 农电缴费补写卡
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet018004Helper extends ISO8583Packetxxx004Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet018004Helper.class);
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());
		
		//field 11, system trace number (pos journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		//field 32.用户号
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);
		//field 37, 放原交易流水。取到后存临时变量里
		ISO8583PacketUtils.unpackField(fieldValues,37,bm,true);
		bm.setNdzhuanyong(fieldValues[37].toString());
		//field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 55, Custom data, for IC data
		unpackField55(fieldValues[55], bm);
		
		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);

		// field 61 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}

	
	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {
		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--开始");
		HeNDElecICCard customData = new HeNDElecICCard();
		String buffer = (String)field;
		customData.setCONS_NO(buffer.toString());// 客户编号
		bm.setCustomData(customData);
		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--结束"); 
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();
		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		return customData.getWRITE_INFO();
	}
 
}
