package com.nantian.npbs.packet.business.WEB;

import java.util.Map;

import org.springframework.stereotype.Component;


import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 交易取消
 * @author MDB
 *
 */
@Component
public class WEBSTRINGPacketxxx021Helper implements IPacketWEBSTRING {

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_PREPAY_PWD"
				,"D_PREPAY_PWD_NEW"
				};
		return fields;
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//原备付金密码
		String oldPWD = (String) fieldValues.get("D_PREPAY_PWD");
		if (oldPWD == null) throw new PacketOperationException();
		bm.setShopPINData(ConvertUtils.str2Bcd(oldPWD));
		
		//新备付金密码
		String newPWD = (String) fieldValues.get("D_PREPAY_PWD_NEW");
		if (newPWD == null) throw new PacketOperationException();
		bm.setCustomerPINData(ConvertUtils.str2Bcd(newPWD));
		
	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//附加响应数据
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
	}
	
}
