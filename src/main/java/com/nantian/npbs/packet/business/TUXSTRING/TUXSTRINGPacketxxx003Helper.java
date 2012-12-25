package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 *  请求写卡数据
 * @author jxw
 *
 */
@Component
public class TUXSTRINGPacketxxx003Helper  implements IPacketTUXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		String fieldName = "";
		String value = "";
		ElectricityICCardData cardData = (ElectricityICCardData)bm.getCustomData();
		
		//用户编号
		fieldName = "D13_13_HESB_YHBH";
		value = cardData.getUserCode().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
		
		//购电值
		fieldName = "D13_13_HESB_GDZ";
		value = cardData.getCurElectric().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
		
		//原交易流水号
		fieldName = "D13_13_HESB_YJYLSH";
		value = bm.getOrigSysJournalSeqno().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
	
		//电卡类型
		fieldName = "D13_13_HESB_DKLX";
		value = cardData.getElecType().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
		
		//卡序列号
		fieldName = "D13_13_HESB_YHKFSYZ";
		value = cardData.getCardNo().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
		
		//随机数
		fieldName = "D13_13_HESB_SJS";
		value = cardData.getRandomNum().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
		
		//接入渠道日期
		fieldName = "CHANNEL_DATE";
		value = bm.getTranDate().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
		
		//接入渠道流水号
		fieldName = "CHANNEL_SEQNO";
		value = bm.getPbSeqno().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		ElectricityICCardData customData = new ElectricityICCardData();
		
		//写卡数据包
		String writeData = (String) fieldValues.get("D13_13_HESB_XKSJB");
		if (writeData == null) throw new PacketOperationException();
		customData.setWriteData(writeData);
		bm.setCustomData(customData);
	}

	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		String[] fields = {"D13_13_HESB_XKSJB"};
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
