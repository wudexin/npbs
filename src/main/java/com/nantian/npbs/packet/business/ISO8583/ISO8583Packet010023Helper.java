package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityICCardData;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 河电IC卡申请补写卡
 * 
 * @author qxl
 * 
 */
@Component
public class ISO8583Packet010023Helper extends ISO8583Packetxxx023Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet010023Helper.class);

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,	BusinessMessage bm) 
			throws PacketOperationException {
		logger.info("ISO8583 xxx023开始解包：ChanleType:" + cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);

		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (for POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 55, Custom data, for IC data
		unpackField55(fieldValues[55], bm);
		
		//Field 60, Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);

		//Field 61, 原始信息域
		unpackField61(fieldValues[61],bm);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
		
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		logger.info("ISO8583 xxx023开始打包：ChanleType:" + cm.getChanelType());
		
		int fieldNo = 0;
		
		//Field 11, POS serial
		fieldNo = 11;
		fieldValues[fieldNo] = bm.getPosJournalNo();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 12, Local time
		fieldNo = 12;
		fieldValues[fieldNo] = bm.getLocalTime();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 13, Local date
		fieldNo = 13;
		fieldValues[fieldNo] = bm.getLocalDate();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		// Field 37, PB serial
		fieldNo = 37;
		fieldValues[fieldNo] = bm.getPbSeqno();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
			
		//Field 39, Response code
		fieldNo = 39;
		fieldValues[fieldNo] = bm.getResponseCode();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		//Field 41, Terminal ID
		fieldNo = 41;
		fieldValues[fieldNo] = bm.getTerminalId();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 42, Shop code
		fieldNo = 42;
		fieldValues[fieldNo] = bm.getShopCode();
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);		

		//子类重写
		//Field 44, Additional response data
		fieldNo = 44;
		fieldValues[fieldNo] = packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//子类重写
		//Field 55, Custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

		//field 60, tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//field 64, mac
		ISO8583PacketUtils.packField(fieldValues,64,bm);
		
	}
	
	@Override
	protected String packField55(BusinessMessage bm) {
		logger.info("河电IC卡申请补写卡--打包55位元--开始");
		if(bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)){
			return null;
		}
		String data = (String) bm.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = { 39 };
		Object[] values = new Object[40];
		values[39] = null == data ? "" : data;
		String buffer = null;
		try {
			buffer = ElectricField55Utils
					.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("河电IC卡申请补写卡--打包55位元出错", e);
		}
		logger.info("河电IC卡申请补写卡--打包55位元--结束");
		return buffer;
	}

	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电IC卡申请补写卡--解包55位元--开始");

		// 使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
		ElectricityICCardData icData = new ElectricityICCardData();

		// 组织需要解包的字段，序号参见ElectricField55Config
		int hasFields[] = {1,44}; //

		String buffer = (String) field;
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(
					hasFields, buffer);

			// 读卡信息
			ISO8583FieldICCardUtil.unpackHBSBCardFieldICCard(values[1],icData);

			// 原电力交易流水号
			bm.setOldElecSeqNo((String) values[44]);

		} catch (Exception e) {
			logger.error("河电IC卡申请补写卡--解包55位元出错", e);
		}

		bm.setCustomData(icData);
		logger.info("河电IC卡申请补写卡--解包55位元--结束");
	}

	@Override
	protected void unpackField61(Object field,BusinessMessage bm) {
		logger.debug("ISO-013024解包61域");
		
		String buffer = (String) field;
		try {
			bm.setOldPbSeqno(buffer.substring(0,14)); //原中心流水
		} catch (Exception e) {
			logger.error("ISO-013024解包61域原中心流水错误",e);
		}
	}
	
}
