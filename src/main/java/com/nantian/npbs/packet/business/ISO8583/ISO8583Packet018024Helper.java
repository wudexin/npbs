package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;
import com.nantian.npbs.packet.internal.UnitcomCashData;

/**
 * 农电交易取消
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet018024Helper extends ISO8583Packetxxx024Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet018024Helper.class);

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		logger.info("开始解包到BusinessMessage, chanleType:{}", cm.getChanelType());
		// field 4 交易金额
		ISO8583PacketUtils.unpackField(fieldValues, 4, bm, true);
		// field 11, system trace number (pos journal no)
		ISO8583PacketUtils.unpackField(fieldValues, 11, bm, true);
		// field 32.用户号
		ISO8583PacketUtils.unpackField(fieldValues, 32, bm, true);
		// field 37, 放原交易流水。取到后存临时变量里
		ISO8583PacketUtils.unpackField(fieldValues, 37, bm, true);
		bm.setNdzhuanyong(fieldValues[37].toString());
		// field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues, 41, bm, true);

		// field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues, 42, bm, true);

		// Field 55, Custom data, for IC data
		unpackField55(fieldValues[55], bm);

		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues, 60, bm, true);

		// field 61 tranCode
		ISO8583PacketUtils.unpackField(fieldValues, 61, bm, true);

		// field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues, 64, bm, true);
	}

	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--开始");
		HeNDElecICCard customData = new HeNDElecICCard();
		String buffer = (String) field;
		customData.setCONS_NO(buffer.toString());// 客户编号
		bm.setCustomData(customData);
		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--结束");
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河北农电IC卡缴费--打包55位元--开始");
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();
		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		return customData.getWRITE_INFO();
	}

	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {

		int fieldNo = 0;

		// Field 11, POS serial
		fieldNo = 11;
		fieldValues[fieldNo] = bm.getPosJournalNo();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 12, Local time
		fieldNo = 12;
		fieldValues[fieldNo] = bm.getLocalTime();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 13, Local date
		fieldNo = 13;
		fieldValues[fieldNo] = bm.getLocalDate();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 32 ,UserCode
		fieldNo = 32;
		fieldValues[fieldNo] = bm.getUserCode();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 37, PB serial
		fieldNo = 37;
		fieldValues[fieldNo] = bm.getPbSeqno();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 38 ,User Name
		fieldNo = 38;
		fieldValues[fieldNo] = bm.getUserName();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 39, Response code
		fieldNo = 39;
		fieldValues[fieldNo] = bm.getResponseCode();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 41, Terminal ID
		fieldNo = 41;
		fieldValues[fieldNo] = bm.getTerminalId();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// Field 42, Shop code
		fieldNo = 42;
		fieldValues[fieldNo] = bm.getShopCode();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// 子类重写
		// Field 44, Additional response data
		fieldNo = 44;
		fieldValues[fieldNo] = packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// 子类重写
		// Field 55, Custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		// field 60, tran code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);

		// field 64, mac
		ISO8583PacketUtils.packField(fieldValues, 64, bm);

	}
	@Override
	protected String packField44(BusinessMessage bm) throws PacketOperationException{
	return bm.getResponseMsg();}

}
