package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HeGBElecICCard;

/**
 * 河电智能电卡取消交易
 * @author qxl
 *
 */
@Component
public class ISO8583Packet012012Helper extends ISO8583Packetxxx012Helper{

	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet012012Helper.class);
	
	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电国标卡IC卡取消--解包55位元读卡信息解包--开始");
		
		// 使用HeGBElecICCard存储解包后的内容，然后将对象放入bm的customData
		HeGBElecICCard icData = new HeGBElecICCard();
		ISO8583FieldICCardUtil.unpackHBGBCardFieldICCard(field,icData);
		bm.setCustomData(icData);
		logger.info("河电国标卡IC卡取消--解包55位元读卡信息解包--结束");
	}
	
	@Override
	protected String packField44(ControlMessage cm, BusinessMessage bm) {
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}else {
			return "";
		}
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河电国标卡IC卡缴费--打包55位元--开始");
		
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}
		
		HeGBElecICCard customData = (HeGBElecICCard) bm.getCustomData();
		
		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {53,49,50};
		
		Object[] values = new Object[60];
		
		// 写卡数据
		values[53] =  null == customData.getWalletPacket() ? "":customData.getWalletPacket();
		
		// 钱包文件的Mac值
		values[49] = null == customData.getWalletMac1() ? "":customData.getWalletMac1();
		
		// 返写区文件的Mac值
		values[50] =  null == customData.getWalletMac2() ? "":customData.getWalletMac2();
		
		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("打包55位元出错",e);
		}
		logger.info("河电国标卡IC卡缴费--打包55位元--结束");
		return buffer;
	}
	
}
