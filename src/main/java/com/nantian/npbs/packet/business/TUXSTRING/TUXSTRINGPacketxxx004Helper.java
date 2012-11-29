package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 交易确认
 * @author jxw
 *
 */
@Component
public class TUXSTRINGPacketxxx004Helper  implements IPacketTUXSTRING {

	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		String[] fields = null;
		return fields;
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		String fieldName = "";
		String value = "";
		
		//原交易流水号
		fieldName = "D13_13_HESB_YJYLSH";
		value = bm.getOrigSysJournalSeqno().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
	
		//交易状态
		fieldName = "D13_13_HESB_JYZT";
		value = bm.getWriteICStatus().trim();
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		ElectricityICCardData customData = new ElectricityICCardData();
		
		bm.setCustomData(customData);
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

}
