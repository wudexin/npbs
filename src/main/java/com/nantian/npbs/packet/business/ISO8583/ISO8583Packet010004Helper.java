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
 * 河电IC卡缴费购电补写卡
 * @author jxw,qxl
 *
 */
@Component
public class ISO8583Packet010004Helper extends ISO8583Packetxxx004Helper{
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet010004Helper.class);

	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());
		
		//field 11, system trace number (pos journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 55, Custom data, for IC data
		unpackField55(fieldValues[55], bm);
		
		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);

		//Field 61, 原始信息域
		unpackField61(fieldValues[61],bm);
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}

	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {

		int fieldNo = 0;
		
		//field 11, pos journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//field 12, local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//field 13, local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		//field 37, pb journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//field 39, response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//field 41, terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//field 42, shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		

		//子类重写
		//Field 44, Additional response data
		fieldNo = 44;
		fieldValues[fieldNo] = packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
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

		logger.info("河电省标卡IC卡缴费--开始打包55位元--开始");
		if(bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)){
			return null;
		}
		ElectricityICCardData customData = (ElectricityICCardData) bm.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {29,44}; //

		Object[] values = new Object[45];

		// 外部认证数据
		values[29] = null == customData.getOutAuthData() ? "":customData.getOutAuthData();
		
		// 原交易流水号
		values[44] = null == bm.getOldElecSeqNo()? "":bm.getOldElecSeqNo();
		
		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("河电省标卡IC卡缴费--开始打包55位元出错",e);
		}

		logger.info("河电省标卡IC卡缴费--开始打包55位元--结束");
		return buffer;
	}
	
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电IC卡补写卡--解包55位元--开始");
		
		//使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
		ElectricityICCardData icData = new ElectricityICCardData();
		
		//组织需要解包的字段，序号参见ElectricField55Config
		int hasFields[] = {1};
		
		String buffer = (String)field;
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(hasFields, buffer);

			// 读卡信息
			ISO8583FieldICCardUtil.unpackHBSBCardFieldICCard(values[1],icData);

		} catch (Exception e) {
			logger.error("河电IC卡补写卡--解包55位元出错",e);
		}
		
		bm.setCustomData(icData);
		logger.info("河电IC卡补写卡--解包55位元--结束");
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
	 * 
	 */
	protected String packField44(BusinessMessage bm) {
		logger.info("开始打包44位元");
		
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return bm.getResponseMsg();
		}
		
		return "";
		
	}
	

}
