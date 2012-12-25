package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.HuaElecICCard;

/**
 * 华电IC卡写卡取消
 * @author MDB
 *
 */
@Component
public class ISO8583Packet013024Helper extends ISO8583Packetxxx024Helper {
	
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet010001Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws 
			PacketOperationException {
		
		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 2, Card number
		//ISO8583PacketUtils.unpackField(fieldValues,2,bm,true);
		
		//Field 4, Trade amount
		ISO8583PacketUtils.unpackField(fieldValues,4,bm,true);
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 32, User code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (For POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 49, Currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);
		
		//Field 55, IC卡应用数据域
		this.unpackField55(fieldValues[55],bm);
		
		//Field 60, Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//Field 61, 原始信息域
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	};
	
	@Override
	protected void unpackField55(Object field, BusinessMessage bm){
		logger.debug("013024开始解包：55域");

		HuaElecICCard sc = new HuaElecICCard();
		//20:华电IC卡_用户号
		//1:电表识别号
		//3:卡序列号
		//21:华电IC卡_卡分散数据
		//2:随机数
		//22:华电IC卡_参数信息文件
		//6:返回购电次数
		
		int hasFields[] = {20,1,3,21,2,22,6};
		try {
			Object[] v = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, (String) field);
			
			// 华电IC卡_用户号
			sc.setUserCode(v[20].toString());
			// 电表识别号
			sc.setAmmeterCode(v[1].toString());
			// 卡序列号
			sc.setCardSeqNo(v[3].toString());
			// 华电IC卡_卡分散数据
			sc.setCardMsg(v[21].toString());
			// 随机数
			sc.setRomNo(v[2].toString());
			// 华电IC卡_参数信息文件
			sc.setParaType(v[22].toString());
			// 返回购电次数
			sc.setBuyElecNum(v[6].toString());

			logger.info("用户号[{}]电表号[{}]卡序号[{}]卡分散[{}]随机数[{}]参数文件[{}]余额[{}]购电次数[{}]",
						new Object[]{v[20],v[1],v[3],v[21],v[2],v[22],v[6]});
			
		} catch (Exception e) {
			bm.setResponseMsg("55域解包错误!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			logger.error("013024解包55域错误!"+e);
		}
		bm.setCustomData(sc);
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		logger.info("由BM开始组包, chanleType[{}]" , cm.getChanelType());
		
		int fieldNo = 0;
		
		//Field 2, Card number
		//ISO8583PacketUtils.packField(fieldValues, 2, bm);
		
		//Field 4, Trade amount
		ISO8583PacketUtils.packField(fieldValues, 4, bm);
		
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues, 11, bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues, 12, bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues, 13, bm);
		
		//Field 32, User code
		ISO8583PacketUtils.packField(fieldValues, 32, bm);
		
		//Field 37, PB journal number
		ISO8583PacketUtils.packField(fieldValues, 37, bm);
		
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues, 39, bm);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.packField(fieldValues, 41, bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues, 42, bm);
		
		//Field 44, 提示信息
		fieldValues[44] = this.packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 49, Currency code
		ISO8583PacketUtils.packField(fieldValues, 49, bm);
		
		//Field 55, IC卡应用数据
		fieldValues[55] = this.packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);
		
		//Field 64, MAC
		ISO8583PacketUtils.packField(fieldValues, 64, bm);
	};
	
	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
		logger.info("ISO-013024打包44域");
		
		// 拼接错误信息
		if (bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)) {
			return bm.getResponseMsg();
		}
		
		HuaElecICCard huaIC = null;
		try {
			huaIC = (HuaElecICCard)bm.getCustomData();
		} catch (ClassCastException e) {
			logger.error("bm.getCustomData()类型转换错误!");
			throw new PacketOperationException("bm.getCustomData()类型转换错误!");
		} catch (Exception e){
			logger.error("bm.getCustomData()强制转换抛出未知异常!");
			throw new PacketOperationException("bm.getCustomData()强制转换抛出未知异常!");
		}
		
		StringBuffer str = new StringBuffer();
		str.append("撤销成功!\n写卡金额:").append(huaIC.getAccountBalance()).append("\n");
		
		return str.toString();
	}
	
	@Override
	protected String packField55(BusinessMessage bm)
		throws PacketOperationException{
		logger.debug("ISO-013024打包55域");
		
		// 拼接错误信息
		if (bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)) {
			return bm.getResponseMsg();
		}
		
		HuaElecICCard huaIC = null;
		try {
			huaIC = (HuaElecICCard)bm.getCustomData();
		} catch (ClassCastException e) {
			logger.error("bm.getCustomData()类型转换错误!");
			throw new PacketOperationException("bm.getCustomData()类型转换错误!");
		} catch (Exception e){
			logger.error("bm.getCustomData()强制转换抛出未知异常!");
			throw new PacketOperationException("bm.getCustomData()强制转换抛出未知异常!");
		}
		
		StringBuffer temp =  new StringBuffer();
		try {
			//8字节返回购电次数
			temp.append(FieldUtils.leftAddZero4FixedLengthString(huaIC.getBuyElecNum(), 8));
			//10字节账户余额
			temp.append(FieldUtils.leftAddZero4FixedLengthString(huaIC.getAccountBalance(), 10));
			//16字节外部认证数据1
			temp.append(huaIC.getAuthdata1());
			//16字节外部认证数据2
			temp.append(huaIC.getAuthdata2());
			//16字节外部认证数据3
			temp.append(huaIC.getAuthdata3());
			//不定长写卡数据（小于400字节）
			temp.append(huaIC.getWriteParam());
		} catch (Exception e) {
			logger.error("处理电力写卡数据异常!");
			throw new PacketOperationException("处理电力写卡数据异常!");
		}
		return temp.toString();
	}
}
