package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.answer.AnswerBusiness010Service;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 交易取消
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx012Helper implements IPacketFIXSTRING {
	
	private static Logger logger = LoggerFactory
	.getLogger(FIXSTRINGPacketxxx012Helper.class);
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_PREPAY_PWD"
				,"D_AMOUNT"
				,"D_USER_CODE"
				,"D_CURRENCY_CODE"
				,"D_CUSTOM_FIELD"
				,"D_SYS_ORIGSEQNO"
				};
		return fields;
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//备付金密码
		String prePayPwd = (String) fieldValues.get("D_PREPAY_PWD");
		if (prePayPwd == null) throw new PacketOperationException();
		bm.setShopPINData((ConvertUtils.str2Bcd(prePayPwd)));
		
		//交易金额
		String amount = (String) fieldValues.get("D_AMOUNT");
		if (amount == null) throw new PacketOperationException();
		bm.setAmount(Integer.parseInt((String)amount) / 100.00);
		logger.info("取消交易金额[{}]",bm.getAmount());
		
		//用户号
		String userCode = (String) fieldValues.get("D_USER_CODE");
		if (userCode == null) throw new PacketOperationException();
		bm.setUserCode(userCode);
		
		//货币代码
		String currCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currCode);
		
		//自定义域(业务交易码)
		String busiCode = (String) fieldValues.get("D_CUSTOM_FIELD");
		if (busiCode == null) throw new PacketOperationException();
		
		//原系统水流号
		String oriSerial = (String) fieldValues.get("D_SYS_ORIGSEQNO");
		if (oriSerial == null) throw new PacketOperationException();
		bm.setOldPbSeqno(oriSerial);
		
	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//交易金额
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_AMOUNT", Double.toString(bm.getAmount()));
		
		//用户号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_CODE", bm.getUserCode());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//自定义域
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CUSTOM_FIELD", "000012");
		
		//原系统水流号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SYS_ORIGSEQNO", bm.getOldPbSeqno());
		
		//商户号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_CODE", bm.getShop().getCompanyCode());
		
		//商户名称
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_NAME", bm.getShop().getCompanyName());
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
		
	}
	
}
