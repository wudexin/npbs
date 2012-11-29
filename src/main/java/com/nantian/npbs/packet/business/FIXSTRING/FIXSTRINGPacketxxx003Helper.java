package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 交易取消
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx003Helper implements IPacketFIXSTRING {

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_PREPAY_PWD",
				"D_AMOUNT", 
				"D_USER_CODE", 
				"D_ADDITIONAL_TIP",
				"D_CURRENCY_CODE",
				"D_CUSTOM_FIELD", 
				"D_SYS_ORIGSEQNO"};
		return fields;
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//备付金密码
		String prePayPwd = (String) fieldValues.get("D_PREPAY_PWD");
		if (prePayPwd == null) throw new PacketOperationException();
		bm.setPrePayPwd(prePayPwd);
		
		//交易金额
		String amount = (String) fieldValues.get("D_AMOUNT");
		if (amount == null) throw new PacketOperationException();
		bm.setAmount(Double.valueOf(amount));
		
		//用户号
		String userCode = (String) fieldValues.get("D_USER_CODE");
		if (userCode == null) throw new PacketOperationException();
		bm.setUserCode(userCode);
		
		//附加响应数据
		String data = (String) fieldValues.get("D_ADDITIONAL_TIP");
		if (data == null) throw new PacketOperationException();
		bm.setCurrencyCode(data);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//自定义域
		String customField = (String) fieldValues.get("D_CUSTOM_FIELD");
		if (customField == null) throw new PacketOperationException();
		bm.setCustomField(customField);
		
		//原系统流水号
		String origSysJournalSeqno = (String) fieldValues.get("D_SYS_ORIGSEQNO");
		if (origSysJournalSeqno == null) throw new PacketOperationException();
		bm.setOrigSysJournalSeqno(origSysJournalSeqno);
	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//备付金密码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PREPAY_PWD", bm.getPrePayPwd());
		
		//交易金额
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_AMOUNT", Double.toString(bm.getAmount()));
		
		//用户号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_CODE", bm.getUserCode());
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//自定义域
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CUSTOM_FIELD", bm.getCustomField());
		
		//原系统水流号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SYS_ORIGSEQNO", bm.getOrigSysJournalSeqno());
	}
	
}
