/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;
import com.nantian.npbs.packet.internal.FieldsConfig;

/**
 * @author TsaiYee
 *
 */
@Component
public class ISO8583FieldsConfig extends FieldsConfig{
	
	private Field configs[] = new Field[128];
	
	protected ISO8583FieldsConfig() {
		 setFields(configs);
	}
	
	@Override
	protected void initFieldConfig() {
		
		int fieldNo = 0;
		
		//field 2, main account
		fieldNo = 2;   //bm.getCustomerAccount();卡号、客户帐号
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.VARIABLE, 1, 2, 10, 19, "-2-MainAccount");
		
		//field 4, amount
		fieldNo = 4;   //bm.getAmount();交易金额
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 6, 12, AlignType.LEFTZERO, "-4-TranAmount");
		
		//field 5 - 10, not used
		
		//field 11,  pos journal seqno
		fieldNo = 11;   //bm.getPosJournalNo();pos流水号
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 3, 6, AlignType.LEFTZERO, "-11-SystemTraceNumber(PosJournalNo)");
		
		//field 12, local time, is bcd
		fieldNo = 12;   //bm.getLocalTime();交易时间
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 3, 6, AlignType.NONE, "-12-LocalTime");
		
		//field 13, local date, is bcd
		fieldNo = 13;   //bm.getLocalDate();交易日期
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 4, 8, AlignType.NONE, "-13-LocalDate");
		
		//field 15, pay type, is ascii
		fieldNo = 15;   //bm.getPayType();资金归集方式
		configs[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.RIGHTZERO, "-15-PayType");
		
		//field 25, flag
		fieldNo = 25;   //bm.getFeeType();费用类型： 0 - 预存款  1 - 欠款
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 1, 2, AlignType.NONE, "-25-DebtsOrSave");
		
		//field 32, user code
		fieldNo = 32;   //bm.getUserCode();用户号
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 2, 4, 0, 20, "-32-UserCode");
		
		//field 35, track 2
		fieldNo = 35;   //二磁道信息
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.VARIABLE, 1, 2, 19, 37, "-35-Track2Data");
		
		//field 36, track 3
		fieldNo = 36;   //三磁道信息
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.VARIABLE, 2, 3, 52, 104, "-36-Track3Data");
		
		//field 37, center journal no
		fieldNo = 37;   //bm.getPbSeqno();pb流水号（npbs流水号）
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 7, 14, AlignType.LEFTZERO, "-37-PbJournalNo(CenterJournalNo)");
		
		//field 38, user name
		fieldNo = 38;   //bm.getUserName();用户名称
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 1, 2, 0, 60, "-38-UserName");
		
		//field 39, response code
		fieldNo = 39;   //bm.getResponseCode();相应码
		configs[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.NONE, "-39-ResponseCode");
		
		//field 41, terminal id
		fieldNo = 41;   //bm.getTerminalId();终端号
		configs[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 20,AlignType.RIGHTSPACE, "-41-TerminalId");
		
		//field 42, shop code
		fieldNo = 42;   //bm.getShopCode();商户号
		configs[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 20, AlignType.RIGHTSPACE, "-42-ShopCode");
		
		//field 44, additional response data
		fieldNo = 44;   //packField44(bm);响应信息，打印数据
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 2, 3, 0, 512, "-44-AdditinalRepoonseData");
	
		//field 49, currency code
		fieldNo = 49;   //bm.getCurrencyCode();货币代码
		configs[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 3, AlignType.NONE, "-49-CurrencyCode");
		
		//field 52, pin data
		fieldNo = 52;   //bm.setShopPINData();密码1
		configs[fieldNo] = new Field(fieldNo, VariableType.BINARY, LengthType.FIXED, 8, 8, AlignType.NONE, "-52-PinData");
		
		//field 53, pin data2
		fieldNo = 53;   //bm.setCustomerPINData();密码2
		configs[fieldNo] = new Field(fieldNo, VariableType.BINARY, LengthType.FIXED, 8, 8, AlignType.NONE, "-53-PinData2");
		
		//field 55, custom data
		fieldNo = 55;   //packField55(bm);电卡数据
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 2, 3, 0, 999, "-55-CustomData");
		
		//field 58, additional return data
		fieldNo = 58;   //bm.getAdditionalTip();屏幕提示信息（目前都在44域返回）
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 2, 3, 0, 512, "-58-AdditinalReturnData");
		
		//field 60, tran code
		fieldNo = 60;   //bm.getTranCode();交易码
		configs[fieldNo] = new Field(fieldNo, VariableType.BCD, LengthType.FIXED, 3, 6, AlignType.NONE, "-60-TranCode");
		
		//field 61, orig message
		fieldNo = 61;   //bm.setOldPbSeqno();原pb流水号
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 2, 3, 0, 29, "-61-OrigMessage");
		
		//field 62, Reserved Private
		fieldNo = 62;   //bm.getWorkKeys();工作密钥
		configs[fieldNo] = new Field(fieldNo, VariableType.BCDASCII, LengthType.VARIABLE, 1, 2, 0, 99, "-62-Reserved Private");
		
		//field 64, mac data
		fieldNo = 64;   //bm.getMac();mac值
		configs[fieldNo] = new Field(fieldNo, VariableType.BINARY, LengthType.FIXED, 8, 8, AlignType.NONE, "-64-MacData");

	}
}
