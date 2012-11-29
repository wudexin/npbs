package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 农电缴费
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet018002Helper extends ISO8583Packetxxx002Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet018002Helper.class);

	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--开始");
		HeNDElecICCard customData = new HeNDElecICCard();
			logger.info("河北农电IC卡----55域信息解包到卡信息实体----开始");
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

		logger.info("河北农电IC卡缴费--打包55位元--开始");
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		} 
	 
		logger.info("河北农电IC卡缴费--打包55位元--结束");
	 
		return customData.getWRITE_INFO();
	}

	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		
		return super.packField44(bm);
	}
	

}
