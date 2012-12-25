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
 * 华电智能电卡补写卡写卡
 * @author MDB
 *
 */
@Component
public class ISO8583Packet013023Helper extends ISO8583Packetxxx023Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet010001Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (For POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//子类重写
		//Field 55, CustomData (For ICData)
		this.unpackField55(fieldValues[55], bm);
		
		//Field 61, 原始信息域
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		
		//Field 64, MAC
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	};
	
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {
		logger.info("013023开始解包：55域");

		HuaElecICCard sc = new HuaElecICCard();
		//20:华电IC卡_用户号
		//1:电表识别号
		//3:卡序列号
		//21:华电IC卡_卡分散数据
		//2:随机数
		//22:华电IC卡_参数信息文件
		//6:返回购电次数
		
		//
		int hasFields[] = {20,1,3,21,2,22,6};
		try {
			Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, (String) field);
			
			// 华电IC卡_用户号
			sc.setUserCode(values[20].toString());
			// 电表识别号
			sc.setAmmeterCode(values[1].toString());
			// 卡序列号
			sc.setCardSeqNo(values[3].toString());
			// 华电IC卡_卡分散数据
			sc.setCardMsg(values[21].toString());
			// 随机数
			sc.setRomNo(values[2].toString());
			// 华电IC卡_参数信息文件
			sc.setParaType(values[22].toString());
			// 返回购电次数
			sc.setBuyElecNum(values[6].toString());
			
		} catch (Exception e) {
			bm.setResponseMsg("013023解包55域错误!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			logger.error("013023解包55域错误!"+e);
			return;
		}
		bm.setCustomData(sc);
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
	}
	
	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException {

		logger.info("013023开始打包：44域");
		
		//终端屏显信息由后台指定，因此交易失败统一处理
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return "补写卡失败，重新补写或到电力营业厅处理！";
		}
		
		HuaElecICCard ic = null;
		try {
			ic = (HuaElecICCard)bm.getCustomData();
		} catch (ClassCastException e) {
			logger.error("013023打包44域错误：bm.getCustomData()类型转换错误!");
			throw new PacketOperationException("bm.getCustomData()类型转换错误!");
		} catch (Exception e){
			logger.error("013023打包44域错误：bm.getCustomData()强制转换抛出未知异常!");
			throw new PacketOperationException("bm.getCustomData()强制转换抛出未知异常!");
		}
		if(null == ic){
			logger.error("013023打包44域错误：bm.getCustomData()为空值!");
			throw new PacketOperationException("bm.getCustomData()为空值!");
		}
		
		StringBuffer str = new StringBuffer();
		str.append("补写卡成功！\n补写金额:").append(ic.getAccountBalance()).append("\n");
		return str.toString();
	}
	
	@Override
	protected String packField55(BusinessMessage bm)
			throws PacketOperationException {
		
		logger.info("013023开始打包：55域");
		
		//终端屏显信息由后台指定，因此交易失败统一处理
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return "补写卡失败，重新补写或到电力营业厅处理！";
		}
		
		HuaElecICCard ic = null;
		try {
			ic = (HuaElecICCard)bm.getCustomData();
		} catch (ClassCastException e) {
			logger.error("013023打包55域错误：bm.getCustomData()类型转换错误!");
			throw new PacketOperationException("bm.getCustomData()类型转换错误!");
		} catch (Exception e){
			logger.error("013023打包55域错误：bm.getCustomData()强制转换抛出未知异常!");
			throw new PacketOperationException("bm.getCustomData()强制转换抛出未知异常!");
		}
		if(null == ic){
			logger.error("013023打包55域错误：bm.getCustomData()为空值!");
			throw new PacketOperationException("bm.getCustomData()为空值!");
		}
		
		StringBuffer temp =  new StringBuffer();
		try {
			//8字节返回购电次数
			temp.append(FieldUtils.leftAddZero4FixedLengthString(ic.getBuyElecNum(), 8));
			//10字节账户余额
			temp.append(FieldUtils.leftAddZero4FixedLengthString(ic.getAccountBalance(), 10));
			//16字节外部认证数据1
			temp.append(ic.getAuthdata1());
			//16字节外部认证数据2
			temp.append(ic.getAuthdata2());
			//16字节外部认证数据3
			temp.append(ic.getAuthdata3());
			//不定长写卡数据（小于400字节）
			temp.append(ic.getWriteParam());
		} catch (Exception e) {
			logger.error("013023打包55域错误"+e);
			throw new PacketOperationException("013023打包55域错误!");
		}
		
		return temp.toString();
	}
	
}
