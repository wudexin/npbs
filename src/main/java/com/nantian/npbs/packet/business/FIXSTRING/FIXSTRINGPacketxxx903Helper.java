package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * EPOS签到
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx903Helper implements IPacketFIXSTRING {

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {};
		return fields;
	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
		
		//密钥信息
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "H_WORK_KEY", bm.getWorkKeys());
	}


}
