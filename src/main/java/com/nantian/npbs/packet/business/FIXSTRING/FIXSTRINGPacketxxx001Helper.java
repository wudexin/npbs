package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 缴费业务查询
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx001Helper implements IPacketFIXSTRING {

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_USER_CODE", 
				"D_CURRENCY_CODE"};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//用户号
		String userCode = (String) fieldValues.get("D_USER_CODE");
		if (userCode == null) throw new PacketOperationException();
		bm.setUserCode(userCode);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//用户号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_CODE", bm.getUserCode());
		
		//用户名称
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_NAME", bm.getUserName());
		
		//商户编号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_CODE", bm.getShop().getCompanyCode());
		
		//商户名称
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_NAME", bm.getShop().getCompanyName());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//交易金额
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_AMOUNT", Double.toString(bm.getAmount()));
		
		//欠款，预存款标识
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_FEE_TYPE", bm.getFeeType());
		
		//资金归集方式
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PAY_TYPE", bm.getShop().getFundType());
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", getAddtionalTip(cm,bm));
		
	}
	
	//子类需要重写
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm){
		return null;
	}

}
