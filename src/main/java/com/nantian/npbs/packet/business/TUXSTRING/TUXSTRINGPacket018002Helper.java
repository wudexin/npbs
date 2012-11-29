package com.nantian.npbs.packet.business.TUXSTRING;

import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;

/**
 * 河北农电IC卡缴费
 * 
 * @author fyf
 * 
 */
@Component
public class TUXSTRINGPacket018002Helper extends TUXSTRINGPacketxxx002Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket018002Helper.class);

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HeNDElecICCard cardData = (HeNDElecICCard) bm.getCustomData();

		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CHECK_ID", cardData
				.getCHECK_ID());// 售电查询出参返回的对账批次
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CONS_NO", cardData
				.getCONS_NO()); // 客户编号
		bm.setUserCode(cardData.getCONS_NO());//由于农电的读卡信息里取不到客户编号，需要打印用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_METER_ID", cardData
				.getMETER_ID()); // 电能表编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_PURP_VALUE",
				String.valueOf(bm.getAmount()) ); // 购电金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_METER_FLAG",
				cardData.getMETER_FLAG()); // 电能表标识
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CARD_INFO",
				cardData.getCARD_INFO()); // 卡内信息
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_IDDATA", cardData
				.getIDDATA()); // 卡片信息
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CONS_NAME",
				cardData.getCONS_NAME()); // 用户名称
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CONS_ADDR",
				cardData.getCONS_ADDR()); // 用电地址
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_PAY_ORGNO",
				cardData.getPAY_ORGNO()); // 核算单位
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_ORG_NO", cardData
				.getORG_NO()); // 供电单位
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CHARGE_CLASS",
				cardData.getCHARGE_CLASS()); // 预付费类别
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_FACTOR_VALUE",
				cardData.getFACTOR_VALUE()); // 综合倍率
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_PURP_PRICE",
				cardData.getPURP_PRICE()); // 购电电价
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BM_SEQNO", bm
				.getPbSeqno());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BM_DATE", bm
				.getTranDate());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CARD_NO", cardData
				.getCARD_NO());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_OCS_MODE", cardData
				.getOCS_MODE());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_PRESET_VALUE", cardData
				.getPRESET_VALUE());
		

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HeNDElecICCard cash = new HeNDElecICCard();
		//购电次数        
		String PURP_TIMES = (String) fieldValues.get("D13_13_HEND_PURP_TIMES");
		if (PURP_TIMES == null)
			throw new PacketOperationException();
		cash.setPURP_TIMES(PURP_TIMES);
		  //写卡金额        
		String WRITE_VALUE = (String) fieldValues.get("D13_13_HEND_WRITE_VALUE");
		if (WRITE_VALUE == null)
			throw new PacketOperationException();
		cash.setWRITE_VALUE(WRITE_VALUE);
		  //用户余额        
		String PRE_AMT = (String) fieldValues.get("D13_13_HEND_PRE_AMT");
		if (PRE_AMT == null)
			throw new PacketOperationException();
		cash.setPRE_AMT(PRE_AMT);
		  //写卡字符串       
		String WRITE_INFO = (String) fieldValues.get("D13_13_HEND_WRITE_INFO");
		if (WRITE_INFO == null)
			throw new PacketOperationException();
		  cash.setWRITE_INFO(WRITE_INFO.replace('^', '|'));
		 
		 //外部售电系统售电流水号       
		String SERIAL_NUMBER = (String) fieldValues.get("D13_13_HEND_SERIAL_NUMBER");
		if (SERIAL_NUMBER == null)
			throw new PacketOperationException();
		cash.setSERIAL_NUMBER(SERIAL_NUMBER);
		//add by mengqingwei 20121030 start 
		 //阶梯差价  
		String LADDER_DIFF = (String) fieldValues.get("D13_13_HEND_LADDER_DIFF");
		if (LADDER_DIFF == null)
			throw new PacketOperationException();
		cash.setLADDER_DIFF(LADDER_DIFF);

		 //本年累计用电量   
		String ANNUAL_VALUE = (String) fieldValues.get("D13_13_HEND_ANNUAL_VALUE");
		if (ANNUAL_VALUE == null)
			throw new PacketOperationException();
		cash.setANNUAL_VALUE(ANNUAL_VALUE);
		 //本档阶梯剩余电量       
		String LADDER_SURPLUS = (String) fieldValues.get("D13_13_HEND_LADDER_SURPLUS");
		if (LADDER_SURPLUS == null)
			throw new PacketOperationException();
		cash.setLADDER_SURPLUS(LADDER_SURPLUS);
		
		//add by mengqingwei 20121030 end

		bm.setCustomData(cash);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "D13_13_HEND_PURP_TIMES", // 购电次数
				"D13_13_HEND_WRITE_VALUE", // 写卡金额
				"D13_13_HEND_PRE_AMT", // 用户余额
				"D13_13_HEND_WRITE_INFO", // 写卡字符串
				"D13_13_HEND_SERIAL_NUMBER" // 外部售电系统售电流水号
				
				//add by mengqingwei 20121030
				,"D13_13_HEND_LADDER_DIFF",	 //阶梯差价      
				"D13_13_HEND_ANNUAL_VALUE", // 本年累计用电量   
				"D13_13_HEND_LADDER_SURPLUS"//本档阶梯剩余电量   

		};
		return fields;
	}

}
