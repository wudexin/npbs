package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.HuaElecICCard;

/**
 * 华电IC卡卡表信息查询
 * @author MDB
 *
 */
@Component
public class ISO8583Packet013022Helper extends ISO8583Packetxxx022Helper {
	
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet010001Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,	BusinessMessage bm) 
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
		
		//Field 60, Trade code
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
	}
	
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {
		logger.info("开始解包55位元读卡信息解包");
		
		HuaElecICCard sc = new HuaElecICCard();
		int hasFields[] = {1,20};
		try {
			Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, (String) field);
			// 电表编号
			sc.setUserCode(values[1].toString());
			// HuaIC_用户编号
			sc.setAmmeterCode(values[20].toString());
			logger.info("电表编号[{}],用户编号[{}]",values[1],values[20] );
		} catch (Exception e) {
			logger.error("55域解包错误"+e);
		}
		bm.setCustomData(sc);
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		logger.info("ISO8583 xxx023开始打包：ChanleType:" + cm.getChanelType());
		
		int fieldNo = 0;
		//10, 11, 12, 36, 38, 40, 41, 54, 63
		//对fieldValues中对应bitmap的赋值，解包结束后不打包原包数据
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues, 11, bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues, 12, bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues, 13, bm);
		
		//Field 37, PB journal number
		ISO8583PacketUtils.packField(fieldValues, 37, bm);
		
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues, 39, bm);
		
		//Field 41, Terminal ID
		ISO8583PacketUtils.packField(fieldValues, 41, bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues, 42, bm);	

		//子类重写
		//Field 44, Additional response data
		fieldValues[44] = this.packField44(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
		//子类重写
		//Field 55, Custom data
		fieldValues[55] = this.packField55(bm);
		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		
	}
	
	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
		logger.info("开始打包44位元");
		if(!"00".equals(bm.getResponseCode()))
			return bm.getResponseMsg();
		return null;
		/*HuaElecICCard sc = (HuaElecICCard) bm.getCustomData();
		StringBuffer str = new StringBuffer();
		str.append("用户名称:").append(sc.getUserName()).append("\n");
		str.append("用户地址:").append(sc.getAddress()).append("\n");
		str.append("账户余额:").append(sc.getAccountBalance()).append("\n");
		return str.toString();*/
	}
	
	@Override
	protected String packField55(BusinessMessage bm) 
			throws PacketOperationException{
		logger.info("开始打包55位元");
		if(!"00".equals(bm.getResponseCode()))
			return "";
		HuaElecICCard sc = (HuaElecICCard)bm.getCustomData();
		StringBuffer temp = new StringBuffer();
		try {
			temp.append(FieldUtils.leftAddZero4FixedLengthString(sc.getBuyElecNum(), 8));
			temp.append(FieldUtils.leftAddZero4FixedLengthString(sc.getAccountBalance(), 10));
			logger.info("013022-pack-55域[{}]",temp.toString());
		} catch (Exception e) {
			logger.error("013001 ISO Pack55转换错误"+e);
			throw new PacketOperationException();
		}
		return temp.toString();
	}
}
