package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 河电IC卡取消
 * 
 * @author qxl
 * 
 */
@Component
public class ISO8583Packet010012Helper extends ISO8583Packetxxx012Helper {

	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet010012Helper.class);

	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河电IC卡取消--打包55位元--开始");
		if (bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)) {
			return null;
		}
		ElectricityICCardData customData = (ElectricityICCardData) bm
				.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {29,44};

		Object[] values = new Object[45];

		// 外部认证数据
		values[29] = null == customData.getOutAuthData() ? "" : customData
				.getOutAuthData();

		// 原电力交易流水号
		values[44] = null == bm.getOldElecSeqNo() ? "" : bm.getOldElecSeqNo();

		String buffer = null;
		try {
			buffer = ElectricField55Utils
					.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("河电IC卡取消--打包55位元出错", e);
		}

		logger.info("河电IC卡取消--打包55位元--结束");
		return buffer;
	}

	@Override
	protected String packField44(ControlMessage cm, BusinessMessage bm) {
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}else {
			return "";
		}
	}

	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电IC卡取消--解包55位元--开始");

		// 使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
		ElectricityICCardData icData = new ElectricityICCardData();

		// 组织需要解包的字段，序号参见ElectricField55Config
		int hasFields[] = {1};

		String buffer = (String) field;
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(
					hasFields, buffer);

			// 读卡信息
			ISO8583FieldICCardUtil.unpackHBSBCardFieldICCard(values[1],icData);

		} catch (Exception e) {
			logger.error("河电IC卡取消--解包55位元出错", e);
		}

		bm.setCustomData(icData);
		logger.info("河电IC卡取消--解包55位元--结束");
	}

}
