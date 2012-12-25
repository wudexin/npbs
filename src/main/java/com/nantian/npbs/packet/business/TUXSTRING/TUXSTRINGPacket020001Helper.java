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
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.ZJKRQ;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 张家口燃气现金查询
 * 
 * @author fyf
 * 
 */
@Component
public class TUXSTRINGPacket020001Helper extends TUXSTRINGPacketxxx001Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket020001Helper.class);

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		ZJKRQ cardData = (ZJKRQ) bm.getCustomData();
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_USER_NO", bm
				.getUserCode());
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_FEE_MON", "000000");
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ZJKRQ cash = new ZJKRQ();

		// 用户名称
		String USER_NAME = (String) fieldValues.get("D13_13_ZJKG_USER_NAME");
		if (USER_NAME == null)
			throw new PacketOperationException();
		cash.setUSER_NAME(USER_NAME);
		bm.setUserName(USER_NAME);

		// 用户地址
		String USER_ADDR = (String) fieldValues.get("D13_13_ZJKG_USER_ADDR");
		if (USER_ADDR == null)
			throw new PacketOperationException();
		cash.setUSER_ADDR(USER_ADDR);

		// 应缴金额
		String SUM_FEE = (String) fieldValues.get("D13_13_ZJKG_SUM_FEE");
		if (SUM_FEE == null)
			throw new PacketOperationException();
		cash.setSUM_FEE(SUM_FEE);
		//01欠款 00 预缴
		if(Double.parseDouble(SUM_FEE)>0){  //返回pos
			bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
		}else{
			bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
		}

		// 欠费月数
		String REC_NUM = (String) fieldValues.get("D13_13_ZJKG_REC_NUM");
		if (REC_NUM == null)
			throw new PacketOperationException();
		int count = Integer.parseInt(REC_NUM);
		cash.setREC_NUM(REC_NUM);
		
		 
	//	String STAR_DATA = (String) fieldValues.get("D13_13_ZJKG_STAR_DATA");
	//	if (STAR_DATA == null)
	//		throw new PacketOperationException();
	//	cash.setSTAR_DATA(STAR_DATA);
		bm.setCustomData(cash);
	
		  
		
	}

	public String[] addFields(String[] fields, int count,
			TUXSTRINGFieldsConfig fieldsConfig) {
		String[] addFields = new String[count * 2];
		String fieldName = null;
		Field f = null;
		int j = 0;
		for (int i = 0; i < count; i++) {
			fieldName = "D13_13_ZJKG_STAR_DATA" + i;
			addFields[j] = "D13_13_ZJKG_STAR_DATA" + i;
			f = new Field("D13_13_ZJKG_STAR_DATA", VariableType.ASCII,
					LengthType.VARIABLE, 8, "起码");
		 
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			fieldName = "D13_13_ZJKG_END_DATA" + i;
			addFields[j] = "D13_13_ZJKG_END_DATA" + i;
			f = new Field("D13_13_ZJKG_END_DATA", VariableType.ASCII,
					LengthType.VARIABLE, 8, "止码");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
		}
		
		String[] fieldsHelp = new String[fields.length + addFields.length];
		System.arraycopy(fields, 0, fieldsHelp, 0, fields.length);
		System.arraycopy(addFields, 0, fieldsHelp, fields.length, addFields.length);
		return addFields;
		}
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "D13_13_ZJKG_USER_NAME",// 用户名称
				"D13_13_ZJKG_USER_ADDR",// 用户地址
				"D13_13_ZJKG_SUM_FEE",// 应缴金额
				"D13_13_ZJKG_REC_NUM", // 欠费月数
				"D13_13_ZJKG_STAR_DATA",//起码
				"D13_13_ZJKG_END_DATA"	//止码x
		};
		return fields;
	}

}
