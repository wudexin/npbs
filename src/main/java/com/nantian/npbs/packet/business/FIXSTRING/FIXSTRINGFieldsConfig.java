package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.HashMap;
import java.util.Map;

import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;
import com.nantian.npbs.packet.internal.FieldsConfig;

/**
 * epos报文配置类
 * @author jxw
 *
 */
public class FIXSTRINGFieldsConfig extends FieldsConfig {
	public  static String HEADER_SPLITER = "0";
	public  static String FIELD_SPLITER = "|";
	
	private Map<String,Field> fieldsMap = new HashMap<String,Field>();
	
	public FIXSTRINGFieldsConfig() {
		setFieldsMap(fieldsMap);
	}
	
	@Override
	protected  void initFieldConfig() {
		Field  f= null;
		
		//报文长度
		f = new Field("H_PACKET_LENGTH", VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "packetLength");
		addFieldConfig(f.getName(), f);
	
		//应用类别定义
		f = new Field("H_APPLICATION_TYPE", VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.RIGHTSPACE, "applicationType");
		addFieldConfig(f.getName(), f);
		
		//终端状态
		f = new Field("H_TERMINAL_STATE", VariableType.ASCII, LengthType.FIXED, 0, 1, AlignType.RIGHTSPACE, "terminalState");
		addFieldConfig(f.getName(), f);
		
		//处理要求
		f = new Field("H_HANDLE_TYPE", VariableType.ASCII, LengthType.FIXED, 0, 1, AlignType.RIGHTSPACE, "handleType");
		addFieldConfig(f.getName(), f);
		
		//密钥信息
		f = new Field("H_WORK_KEY", VariableType.ASCII, LengthType.FIXED, 0, 80, AlignType.RIGHTSPACE, "workKey");
		addFieldConfig(f.getName(), f);
		
		//业务交易码
		f = new Field("D_TRAN_CODE", VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "tranCode");
		addFieldConfig(f.getName(), f);
		
		//交易码（3位）
		f = new Field("D_BUSI_CODE", VariableType.ASCII, LengthType.FIXED, 0, 3, AlignType.RIGHTSPACE, "busiCode");
		addFieldConfig(f.getName(), f);
		
		//PSMA卡号
		f = new Field("D_PSAM_CARDNO", VariableType.ASCII, LengthType.FIXED, 0, 19, AlignType.RIGHTSPACE, "PSAMCardNo");
		addFieldConfig(f.getName(), f);
		
		//备付金密码
		f = new Field("D_PREPAY_PWD", VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "prePayPwd");
		addFieldConfig(f.getName(), f);
		
		//备付金密码
		f = new Field("D_PREPAY_PWD_NEW", VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "prePayPwdNew");
		addFieldConfig(f.getName(), f);
		
		//交易金额
		f = new Field("D_AMOUNT", VariableType.ASCII, LengthType.FIXED, 0, 12, AlignType.RIGHTSPACE, "amount");
		addFieldConfig(f.getName(), f);
		
		//EPOS流水号
		f = new Field("D_EPOS_SEQNO", VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "EPOSSeqNo");
		addFieldConfig(f.getName(), f);
		
		//EPOS原流水号
		f = new Field("D_EPOS_ORI_SEQNO", VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "OriEPOSSeqNo");
		addFieldConfig(f.getName(), f);
		
		//交易时间
		f = new Field("D_TRAN_TIME", VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "tranTime");
		addFieldConfig(f.getName(), f);
		
		//交易日期
		f = new Field("D_TRAN_DATE", VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "tranDate");
		addFieldConfig(f.getName(), f);
		
		//资金归集方式
		f = new Field("D_PAY_TYPE", VariableType.ASCII, LengthType.FIXED, 0, 1, AlignType.RIGHTSPACE, "payType");
		addFieldConfig(f.getName(), f);
		
		//欠款，预存款标识
		f = new Field("D_FEE_TYPE", VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.RIGHTSPACE, "feeType");
		addFieldConfig(f.getName(), f);
		
		//用户号
		f = new Field("D_USER_CODE", VariableType.ASCII, LengthType.FIXED, 0, 20, AlignType.RIGHTSPACE, "userCode");
		addFieldConfig(f.getName(), f);
		
		//用户名称
		f = new Field("D_USER_NAME", VariableType.ASCII, LengthType.FIXED, 0, 60, AlignType.RIGHTSPACE, "userName");
		addFieldConfig(f.getName(), f);
		
		//PB流水号
		f = new Field("D_PB_SEQNO", VariableType.ASCII, LengthType.FIXED, 0, 14, AlignType.RIGHTSPACE, "pbSeqno");
		addFieldConfig(f.getName(), f);
		
		//系统流水号
		f = new Field("D_SYS_SEQNO", VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "sysJournalSeqno");
		addFieldConfig(f.getName(), f);
		
		//应答码
		f = new Field("D_RESPONSE_CODE", VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.RIGHTSPACE, "responseCode");
		addFieldConfig(f.getName(), f);
		
		//终端编号
		f = new Field("D_TERMINAL_ID", VariableType.ASCII, LengthType.FIXED, 0, 20, AlignType.RIGHTSPACE, "terminalId");
		addFieldConfig(f.getName(), f);
		
		//附加响应数据
		f = new Field("D_ADDITIONAL_TIP", VariableType.ASCII, LengthType.FIXED, 0, 512, AlignType.RIGHTSPACE, "additionalTip");
		addFieldConfig(f.getName(), f);
		
		//货币代码
		f = new Field("D_CURRENCY_CODE", VariableType.ASCII, LengthType.FIXED, 0, 3, AlignType.RIGHTSPACE, "currencyCode");
		addFieldConfig(f.getName(), f);
		
		//自定义域
		f = new Field("D_CUSTOM_FIELD", VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "customField");
		addFieldConfig(f.getName(), f);
		
		//原系统流水号
		f = new Field("D_SYS_ORIGSEQNO", VariableType.ASCII, LengthType.FIXED, 0, 14, AlignType.RIGHTSPACE, "origSysJournalSeqno");
		addFieldConfig(f.getName(), f);
		
		//查询数据
		f = new Field("D_QUERY_DATA", VariableType.ASCII, LengthType.FIXED, 0, 512, AlignType.RIGHTSPACE, "queryData");
		addFieldConfig(f.getName(), f);
		
		//原交易日期
		f = new Field("D_ORIG_DEAL_DATE", VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "origDealDate");
		addFieldConfig(f.getName(), f);
		
		//交易日期时间段
		f = new Field("D_DEAL_DATE_TIME", VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "dealDateTime");
		addFieldConfig(f.getName(), f);
		
		//应用信息域
		f = new Field("D_APP_INFO_FIELD", VariableType.ASCII, LengthType.FIXED, 0, 512, AlignType.RIGHTSPACE, "appInfoField");
		addFieldConfig(f.getName(), f);
		
		//返回数据
		f = new Field("D_RET_DATA", VariableType.ASCII, LengthType.FIXED, 0, 60, AlignType.RIGHTSPACE, "retData");
		addFieldConfig(f.getName(), f);
		
		//PIN工作密钥
		f = new Field("D_PIN_WORK_KEYS", VariableType.ASCII, LengthType.FIXED, 0,18, AlignType.RIGHTSPACE, "pinWorkKeys");
		addFieldConfig(f.getName(), f);
		
		//商户编号
		f = new Field("D_SHOP_CODE", VariableType.ASCII, LengthType.FIXED, 0,20, AlignType.RIGHTSPACE, "shopCode");
		addFieldConfig(f.getName(), f);
		
		//商户名称
		f = new Field("D_SHOP_NAME", VariableType.ASCII, LengthType.FIXED, 0,40, AlignType.RIGHTSPACE, "shopName");
		addFieldConfig(f.getName(), f);
		
		//备付金账号
		f = new Field("D_PREPAY_ACCNO", VariableType.ASCII, LengthType.FIXED, 0,20, AlignType.RIGHTSPACE, "prepayAccno");
		addFieldConfig(f.getName(), f);
		
		//备付金余额
		f = new Field("D_ACC_BALANCE", VariableType.ASCII, LengthType.FIXED, 0, 12, AlignType.RIGHTSPACE, "accBalance");
		addFieldConfig(f.getName(), f);
		
		//电卡现金：账户余额
		f = new Field("D_ELEC_CASH_ACCBAL", VariableType.ASCII, LengthType.FIXED, 0, 19, AlignType.RIGHTSPACE, "HuaCashAccBal");
		addFieldConfig(f.getName(), f);
		
		//电卡现金：起止示数
		f = new Field("D_ELEC_CASH_SENUM", VariableType.ASCII, LengthType.FIXED, 0, 31, AlignType.RIGHTSPACE, "HuaCashStartEndNum");
		addFieldConfig(f.getName(), f);
		
		//电卡现金：阶梯电价信息
		f = new Field("D_ELEC_HEE_JTDJXX", VariableType.ASCII, LengthType.FIXED, 0, 684, AlignType.RIGHTSPACE, "D_ELEC_HEE_JTDJXX");
		addFieldConfig(f.getName(), f);
		
		
		//电卡现金：低额提醒类型及金额，格式参考：[类型]|[金额]
		f = new Field("D_LOW_TIP", VariableType.ASCII, LengthType.FIXED, 0, 18, AlignType.RIGHTSPACE, "HuaCashStartEndNum");
		addFieldConfig(f.getName(), f);
		
		//MAC
		f = new Field("D_EPOS_MAC", VariableType.ASCII, LengthType.FIXED, 0,16, AlignType.RIGHTSPACE, "shopName");
		addFieldConfig(f.getName(), f);
		
		//有线电视：账户余额
		f = new Field("D_TV_CASH_ACCBAL", VariableType.ASCII, LengthType.FIXED, 0, 19, AlignType.RIGHTSPACE, "HuaCashAccBal");
		addFieldConfig(f.getName(), f);
	}
	
	private static FieldsConfig instance = null;
	
	public static FieldsConfig getInstance() {
		if (instance != null)
			return instance;
		instance = new FIXSTRINGFieldsConfig();
		return instance;
	}
	
	private void addFieldConfig(String name, Field f) {
		fieldsMap.put(name, f); 
	}
}
