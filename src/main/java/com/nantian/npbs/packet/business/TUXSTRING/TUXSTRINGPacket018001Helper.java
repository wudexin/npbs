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
 * 河北农电IC卡查询
 * 
 * @author fyf
 * 
 */
@Component
public class TUXSTRINGPacket018001Helper extends TUXSTRINGPacketxxx001Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket018001Helper.class);

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		/*|||10.22.17.6|pts_2|ZG01|05008889|02|06600408|PG01|0000
		//017513
		//|017513
		|
		|
		|
		|017513^02^XT_003^LK^01^^19^24^1^1^^^0^945^945^24^945^0^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		|12120800245826
		|20121208
		|02|
		*/
		HeNDElecICCard cardData = (HeNDElecICCard) bm.getCustomData();

		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CONS_NO", cardData
				.getCONS_NO());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CARD_NO", cardData
				.getCARD_NO());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_PURP_FLAG",
				cardData.getPURP_FLAG());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_CARD_INFO",
				cardData.getCARD_INFO());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_IDDATA", cardData
				.getIDDATA());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_READ_INFO",
				cardData.getREAD_INFO());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BM_SEQNO", bm
				.getPbSeqno());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BM_DATE", bm
				.getTranDate());
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BUSI_TYPE", cardData.getBUSI_TYPE());//

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HeNDElecICCard cash = new HeNDElecICCard();

		//000000||20121213|0|00001| | |0|0556967000|017513|王杰|河北省保定市雄县双堂乡大魏庄村单元116|0|1340624|134062408|245200382|河南新天|XT_003|02|02|1|0.52|0.0|0.0|0.0| |23|0|1| |||03|0|

		// 电能表标识
		String METER_FLAG = (String) fieldValues.get("D13_13_HEND_METER_FLAG");
		if (METER_FLAG == null)
			throw new PacketOperationException();
		cash.setMETER_FLAG(METER_FLAG);

		// 密钥信息
		String KEY_INFO = (String) fieldValues.get("D13_13_HEND_KEY_INFO");
		if (KEY_INFO == null)
			throw new PacketOperationException();
		cash.setKEY_INFO(KEY_INFO);

		// 对账批次
		String CHECK_ID = (String) fieldValues.get("D13_13_HEND_CHECK_ID");
		if (CHECK_ID == null)
			throw new PacketOperationException();
		cash.setCHECK_ID(CHECK_ID);
		// 客户编号
		String CONS_NO = (String) fieldValues.get("D13_13_HEND_CONS_NO");
		if (CONS_NO == null)
			throw new PacketOperationException();
		logger.info("终端上送编号[{}],电商返回编号[{}]", bm.getUserCode(), CONS_NO);
		cash.setCONS_NO(CONS_NO);
		bm.setUserCode(CONS_NO);
		// 电卡编号
		String CARD_NO = (String) fieldValues.get("D13_13_HEND_CARD_NO");
		if (CARD_NO == null)
			throw new PacketOperationException();
		cash.setCARD_NO(CARD_NO);

		// 用户名称
		String CONS_NAME = (String) fieldValues.get("D13_13_HEND_CONS_NAME");
		if (CONS_NAME == null)
			throw new PacketOperationException();
		cash.setCONS_NAME(CONS_NAME);
		bm.setUserName(CONS_NAME);

		// 用电地址
		String CONS_ADDR = (String) fieldValues.get("D13_13_HEND_CONS_ADDR");
		if (CONS_ADDR == null)
			throw new PacketOperationException();
		cash.setCONS_ADDR(CONS_ADDR);

		// 用户状态
		String CONS_STATUS = (String) fieldValues
				.get("D13_13_HEND_CONS_STATUS");
		if (CONS_STATUS == null)
			throw new PacketOperationException();
		cash.setCONS_STATUS(CONS_STATUS);
		// 核算单位
		String PAY_ORGNO = (String) fieldValues.get("D13_13_HEND_PAY_ORGNO");
		if (PAY_ORGNO == null)
			throw new PacketOperationException();
		cash.setPAY_ORGNO(PAY_ORGNO);

		// 供电单位
		String ORG_NO = (String) fieldValues.get("D13_13_HEND_ORG_NO");
		if (ORG_NO == null)
			throw new PacketOperationException();
		cash.setORG_NO(ORG_NO);

		// 电能表编号
		String METER_ID = (String) fieldValues.get("D13_13_HEND_METER_ID");
		if (METER_ID == null)
			throw new PacketOperationException();
		cash.setMETER_ID(METER_ID);

		// 电能表厂家名称
		String METER_FAC = (String) fieldValues.get("D13_13_HEND_METER_FAC");
		if (METER_FAC == null)
			throw new PacketOperationException();
		cash.setMETER_FAC(METER_FAC);
		// 电能表型号
		String METER_MODEL = (String) fieldValues
				.get("D13_13_HEND_METER_MODEL");
		if (METER_MODEL == null)
			throw new PacketOperationException();
		cash.setMETER_MODEL(METER_MODEL);
		// 电能表类别
		String METER_TS = (String) fieldValues.get("D13_13_HEND_METER_TS");
		if (METER_TS == null)
			throw new PacketOperationException();
		cash.setMETER_TS(METER_TS);
		// 预付费类别
		String CHARGE_CLASS = (String) fieldValues
				.get("D13_13_HEND_CHARGE_CLASS");
		if (CHARGE_CLASS == null)
			throw new PacketOperationException();
		cash.setCHARGE_CLASS(CHARGE_CLASS);
		// 综合倍率
		String FACTOR_VALUE = (String) fieldValues
				.get("D13_13_HEND_FACTOR_VALUE");
		if (FACTOR_VALUE == null)
			throw new PacketOperationException();
		cash.setFACTOR_VALUE(FACTOR_VALUE);
		// 购电电价
		String PURP_PRICE = (String) fieldValues.get("D13_13_HEND_PURP_PRICE");
		if (PURP_PRICE == null)
			throw new PacketOperationException();
		cash.setPURP_PRICE(PURP_PRICE);
		// 最大购电值
		String PURP_MAX = (String) fieldValues.get("D13_13_HEND_PURP_MAX");
		if (PURP_MAX == null)
			throw new PacketOperationException();
		cash.setPURP_MAX(PURP_MAX);

		// 最小购电值
		String PURP_MIN = (String) fieldValues.get("D13_13_HEND_PURP_MIN");
		if (PURP_MIN == null)
			throw new PacketOperationException();
		cash.setPURP_MIN(PURP_MIN);
		// 欠费金额
		String OWN_AMT = (String) fieldValues.get("D13_13_HEND_OWN_AMT");
		if (OWN_AMT == null)
			throw new PacketOperationException();
		cash.setOWN_AMT(OWN_AMT);
		if(Double.parseDouble(OWN_AMT)>0){  //返回pos
			bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
		}else{
			bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
		}
		// 用户余额
		String PRE_AMT = (String) fieldValues.get("D13_13_HEND_PRE_AMT");
		if (PRE_AMT == null)
			throw new PacketOperationException();
		cash.setPRE_AMT(PRE_AMT);
		// 用户上次购电次数
		String PURP_TIMES = (String) fieldValues.get("D13_13_HEND_PURP_TIMES");
		if (PURP_TIMES == null)
			throw new PacketOperationException();
		cash.setPURP_TIMES(PURP_TIMES);

		// 是否插表（0 未插表1 已插表）
		String IF_METER = (String) fieldValues.get("D13_13_HEND_IF_METER");
		if (IF_METER == null)
			throw new PacketOperationException();
		cash.setIF_METER(IF_METER);

		// 是否允许购电（0 否 1 是）
		String IF_PURP = (String) fieldValues.get("D13_13_HEND_IF_PURP");
		if (IF_PURP == null)
			throw new PacketOperationException();
		cash.setIF_PURP(IF_PURP);
		// 不允许购电原因说明
		String NOALLOW_MSG = (String) fieldValues
				.get("D13_13_HEND_NOALLOW_MSG");
		cash.setNOALLOW_MSG(NOALLOW_MSG);
		 
		// 卡内信息
		String CARD_INFO = (String) fieldValues.get("D13_13_HEND_CARD_INFO");
		if (CARD_INFO == null)
			throw new PacketOperationException();
		cash.setCARD_INFO(CARD_INFO);

		// 卡片信息
		String IDDATA = (String) fieldValues.get("D13_13_HEND_IDDATA");
		if (IDDATA == null)
			throw new PacketOperationException();
		cash.setIDDATA(IDDATA);
		
		//费控方式
		String OCS_MODE = (String) fieldValues.get("D13_13_HEND_OCS_MODE");
		if (OCS_MODE == null)
			throw new PacketOperationException();
		cash.setOCS_MODE(OCS_MODE);
		
		//预置值
		String PRESET_VALUE = (String) fieldValues.get("D13_13_HEND_PRESET_VALUE");
		if (PRESET_VALUE == null)
			throw new PacketOperationException();
		cash.setPRESET_VALUE(PRESET_VALUE);
		
		bm.setCustomData(cash);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "D13_13_HEND_METER_FLAG", // 电能表标识
				"D13_13_HEND_KEY_INFO", // 密钥信息
				"D13_13_HEND_CHECK_ID", // 对账批次
				"D13_13_HEND_CONS_NO", // 客户编号
				"D13_13_HEND_CARD_NO", // 电卡编号
				"D13_13_HEND_CONS_NAME", // 用户名称
				"D13_13_HEND_CONS_ADDR", // 用电地址
				"D13_13_HEND_CONS_STATUS", // 用户状态
				"D13_13_HEND_PAY_ORGNO", // 核算单位
				"D13_13_HEND_ORG_NO", // 供电单位
				"D13_13_HEND_METER_ID", // 电能表编号
				"D13_13_HEND_METER_FAC", // 电能表厂家名称
				"D13_13_HEND_METER_MODEL", // 电能表型号
				"D13_13_HEND_METER_TS", // 电能表类别
				"D13_13_HEND_CHARGE_CLASS", // 预付费类别
				"D13_13_HEND_FACTOR_VALUE", // 综合倍率
				"D13_13_HEND_PURP_PRICE", // 购电电价
				"D13_13_HEND_PURP_MAX", // 最大购电值
				"D13_13_HEND_PURP_MIN", // 最小购电值
				"D13_13_HEND_OWN_AMT", // 欠费金额
				"D13_13_HEND_PRE_AMT", // 用户余额
				"D13_13_HEND_PURP_TIMES", // 用户上次购电次数
				"D13_13_HEND_IF_METER", // 是否插表（0 未插表1 已插表）
				"D13_13_HEND_IF_PURP", // 是否允许购电（0 否 1 是）
				"D13_13_HEND_NOALLOW_MSG", // 不允许购电原因说明
				"D13_13_HEND_CARD_INFO", // 卡内信息
				"D13_13_HEND_IDDATA", // 卡片信息
				"D13_13_HEND_OCS_MODE",//费控方式
				"D13_13_HEND_PRESET_VALUE"	//预置值
		};
		return fields;
	}

}
