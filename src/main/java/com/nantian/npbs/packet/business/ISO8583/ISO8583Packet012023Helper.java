package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;

/**
 * 河电智能电卡补写卡交易
 * @author jxw
 *
 */
@Component
public class ISO8583Packet012023Helper extends ISO8583Packetxxx023Helper{

	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet012023Helper.class);
	
	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电国标卡IC卡查询--解包55位元读卡信息解包--开始");
		
		// 使用HeGBElecICCard存储解包后的内容，然后将对象放入bm的customData
		HeGBElecICCard icData = new HeGBElecICCard();
		ISO8583FieldICCardUtil.unpackHBGBCardFieldICCard(field,icData);
		bm.setCustomData(icData);
		logger.info("河电国标卡IC卡查询--解包55位元读卡信息解包--结束");
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		logger.info("由BM开始组包, chanleType[{}]" , cm.getChanelType());
		
		int fieldNo = 0;
		
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		//Field 37, PB journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);

		//Field 44, Additional response data
		fieldValues[44] = this.packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 55, Custom data
		fieldValues[55] = this.packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);
		
	 
	}

	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}else {
			return "";
		}
	}

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		logger.info("开始解包到BusinessMessage, chanleType:{}",cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (For POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
				
		//子类重写
		//Field 55, CustomData (For ICData)
		this.unpackField55(fieldValues[55], bm);
		
		// Field 60 Tran Code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//Field 61, 原始信息域
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河电国标卡IC卡补写卡--打包55位元--开始");
		HeGBElecICCard customData = (HeGBElecICCard) bm.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {49,50,51,52,53};
		
		Object[] values = new Object[60];
		
		// 钱包文件的Mac值
		values[49] = null == customData.getWalletMac1() ? "":customData.getWalletMac1();
		
		// 返写区文件的Mac值
		values[50] =  null == customData.getWalletMac2() ? "":customData.getWalletMac2();

		// 参数信息文件
		values[51] =  null == customData.getWalletMac3() ? "":customData.getWalletMac3();

		// 参数信息文件Mac值
		values[52] =  null == customData.getWalletMac4() ? "":customData.getWalletMac4();

		// 写卡数据
		values[53] =  null == customData.getWalletPacket() ? "":customData.getWalletPacket();

		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("打包55位元出错",e);
		}
		logger.info("河电国标卡IC卡补写卡--打包55位元--结束");
		return buffer;
	}
}
