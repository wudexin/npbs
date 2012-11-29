package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 申请对账文件
 * 
 * @author jxw
 * 
 */
@Component
public class TUXSTRINGPacketxxx806Helper implements IPacketTUXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// 对账文件日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_BM_DATE", bm.getBatDate());
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// 用户名称
		String fileName = (String) fieldValues.get("H_RECV_FILE");
		if (fileName == null) throw new PacketOperationException();
		bm.setBatFileName(fileName); //返回
	}

	@Override
	public String[] hasFields() {
		String[] fields = { "H_RECV_FILE" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			TUXSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}
