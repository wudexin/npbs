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
 * 河电IC卡写卡申请
 * @author jxw,qxl
 *
 */
@Component
public class ISO8583Packet010003Helper extends ISO8583Packetxxx003Helper{
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet010003Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);

		//Field 32, User code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);
		
		//Field 38, User name
		ISO8583PacketUtils.unpackField(fieldValues,38,bm,true);
		
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
		int fieldNo = 0;
		
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);

		//Feild 32, User code
		ISO8583PacketUtils.packField(fieldValues,32,bm);
		
		//Field 37, PB journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);

		//Field 38, User name
		ISO8583PacketUtils.packField(fieldValues,38,bm);
		
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		
		//Field 44, Additional response data
		fieldNo = 44;
		fieldValues[fieldNo] = packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 55, Custom data
		fieldNo = 55;
		fieldValues[fieldNo] = packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//Field 64, MAC
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}
	
	
	@Override
	protected String packField55(BusinessMessage bm) {
		logger.info("河电省标卡IC卡申请写卡数据--打包55位元--开始");
		if(bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)){
			return null;
		}
		
		String data = (String)bm.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {39};
		Object[] values = new Object[40];
		values[39] = null == data ? "":data;
		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("河电省标卡IC卡申请写卡数据--打包55位元出错",e);
		}
		logger.info("河电省标卡IC卡申请写卡数据--打包55位元--结束");
		return buffer;
	}
	
	
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电省标卡IC卡申请写卡数据--解包55位元--开始");
		
		//使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
		ElectricityICCardData icData = new ElectricityICCardData();
		
		//组织需要解包的字段，序号参见ElectricField55Config
		int hasFields[] = {1,12,44};
		
		String buffer = (String)field;
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(hasFields, buffer);

			// 读卡信息
			ISO8583FieldICCardUtil.unpackHBSBCardFieldICCard(values[1],icData);

			// 购电值
			icData.setCurElectric((String)values[12]);
			
			// 原电力流水号
			String oldElecSeqNo = (String)values[44];
			bm.setOldElecSeqNo(oldElecSeqNo.trim());
			
		} catch (Exception e) {
			logger.error("河电省标卡IC卡申请写卡数据--解包55位元出错",e);
		}
		
		bm.setCustomData(icData);
		logger.info("河电省标卡IC卡申请写卡数据--解包55位元--结束");
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
	
	/**
	 * //打印小票
	 * 河电IC卡缴费44域：本次购电量:XX度\n本次余额:XX\n交易时间:XX\n"
	 */
	protected String packField44(BusinessMessage bm) {
		logger.info("开始打包44位元");
		
		return bm.getResponseMsg();
	}

}
