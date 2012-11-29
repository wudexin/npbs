package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 用户交易流水列表查询
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx005Helper implements IPacketFIXSTRING {

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_PREPAY_PWD", 
				"D_CURRENCY_CODE", 
				"D_ORIG_DEAL_DATE"};
		
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//备付金密码
		String prePayPwd = (String) fieldValues.get("D_PREPAY_PWD");
		if (prePayPwd == null) throw new PacketOperationException();
		bm.setShopPINData((ConvertUtils.str2Bcd(prePayPwd)));
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//原交易日期
		String origDealDate = (String) fieldValues.get("D_ADDITIONAL_TIP");
		if (origDealDate == null) throw new PacketOperationException();
		bm.setOrigDealDate(origDealDate);
	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
		
	}

}
