/**
 * 
 */
package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.HashMap;
import java.util.Map;

import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;
import com.nantian.npbs.packet.internal.FieldsConfig;

/**
 * @author TsaiYee
 * 
 */
public class SPLITSTRINGFieldsConfig extends FieldsConfig {
	public static String HEADER_SPLITER = "0";
	public static String FIELD_SPLITER = "|";

	private Map<String, Field> fieldsMap = new HashMap<String, Field>();

	public SPLITSTRINGFieldsConfig() {
		setFieldsMap(fieldsMap);
	}

	@Override
	protected void initFieldConfig() {
		Field f = null;

		/* 报文头 */
		f = new Field("tranCode", VariableType.ASCII, LengthType.VARIABLE, 6,
				"tranCode");// 交易码
		addFieldConfig(f.getName(), f);
		f = new Field("tranDate", VariableType.ASCII, LengthType.VARIABLE, 8,
				"tranDate");// 交易日期
		addFieldConfig(f.getName(), f);
		f = new Field("thirdSerial", VariableType.ASCII, LengthType.VARIABLE,
				40, "thirdSerial");// 电子商务平台流水号
		addFieldConfig(f.getName(), f);
		f = new Field("channelCode", VariableType.ASCII, LengthType.VARIABLE,
				2, "channelCode");// 渠道号
		addFieldConfig(f.getName(), f);
		f = new Field("operCode", VariableType.ASCII, LengthType.VARIABLE, 4,
				"operCode");// 柜员号
		addFieldConfig(f.getName(), f);
		f = new Field("unitCode", VariableType.ASCII, LengthType.VARIABLE, 9,
				"unitCode");// 机构号
		addFieldConfig(f.getName(), f);
		f = new Field("retCode", VariableType.ASCII, LengthType.VARIABLE, 6,
				"retCode");// 响应码
		addFieldConfig(f.getName(), f);
		f = new Field("retMsg", VariableType.ASCII, LengthType.VARIABLE, 60,
				"retMsg");// 响应信息
		addFieldConfig(f.getName(), f);
		f = new Field("thirdDate", VariableType.ASCII, LengthType.VARIABLE, 8,
				"thirdDate");// 电子商务平台交易日期
		addFieldConfig(f.getName(), f);
		f = new Field("pbDate", VariableType.ASCII, LengthType.VARIABLE, 8,
				"pbDate");// 便民服务站交易日期
		addFieldConfig(f.getName(), f);
		f = new Field("pbSerial", VariableType.ASCII, LengthType.VARIABLE, 40,
				"pbSerial");// 便民服务站流水号
		addFieldConfig(f.getName(), f);

		/* 备付金查询上送报文 */
		f = new Field("shopCode", VariableType.ASCII, LengthType.VARIABLE, 20,
				"shopCode");// 商户号
		addFieldConfig(f.getName(), f);

		/* 备付金查询返回报文 */
		// 商户号已存在使用shopCode
		f = new Field("companyName", VariableType.ASCII, LengthType.VARIABLE,
				80, "companyName");// 商户名称
		addFieldConfig(f.getName(), f);
		// 机构编码已存在使用unitCode
		addFieldConfig(f.getName(), f);
		f = new Field("state", VariableType.ASCII, LengthType.VARIABLE, 1,
				"state");// 状态
		addFieldConfig(f.getName(), f);
		f = new Field("address", VariableType.ASCII, LengthType.VARIABLE, 100,
				"address");// 商户地址
		addFieldConfig(f.getName(), f);
		f = new Field("contact", VariableType.ASCII, LengthType.VARIABLE, 20,
				"contact");// 联系人
		addFieldConfig(f.getName(), f);
		f = new Field("contactNum", VariableType.ASCII, LengthType.VARIABLE,
				20, "contactNum");// 联系电话
		addFieldConfig(f.getName(), f);
		f = new Field("creatDate", VariableType.ASCII, LengthType.VARIABLE, 9,
				"creatDate");// 创建日期
		addFieldConfig(f.getName(), f);
		f = new Field("remark", VariableType.ASCII, LengthType.VARIABLE, 200,
				"remark");// 备注
		addFieldConfig(f.getName(), f);
		f = new Field("auid", VariableType.ASCII, LengthType.VARIABLE, 36,
				"auid");// 身份证号
		addFieldConfig(f.getName(), f);
		f = new Field("payType", VariableType.ASCII, LengthType.VARIABLE, 3,
				"payType");// 资金归集方式
		addFieldConfig(f.getName(), f);
		f = new Field("resAccNo", VariableType.ASCII, LengthType.VARIABLE, 20,
				"resAccNo");// 备付金帐号
		addFieldConfig(f.getName(), f);
		f = new Field("accBalance", VariableType.ASCII, LengthType.VARIABLE,
				16, "accBalance");// 备付金余额
		addFieldConfig(f.getName(), f);
		f = new Field("creditAmt", VariableType.ASCII, LengthType.VARIABLE, 16,
				"creditAmt");// 信用额度
		addFieldConfig(f.getName(), f);
		f = new Field("useCreAmt", VariableType.ASCII, LengthType.VARIABLE, 16,
				"useCreAmt");// 已用信用额度
		addFieldConfig(f.getName(), f);
		f = new Field("surCreAmt", VariableType.ASCII, LengthType.VARIABLE, 16,
				"surCreAmt");// 剩余信用额度
		addFieldConfig(f.getName(), f);
		f = new Field("arrearsDate", VariableType.ASCII, LengthType.VARIABLE,
				8, "arrearsDate");// 欠费日期
		addFieldConfig(f.getName(), f);

		/* 备付金续费上送报文 */
		// 商户号已存在使用shopCode
		f = new Field("amount", VariableType.ASCII, LengthType.VARIABLE, 16,
				"amount");// 交易金额
		addFieldConfig(f.getName(), f);

		/* 备付金续费返回报文 */
		// 商户号已存在使用shopCode
		// 商户名已存在使用companyName
		// 商户地址已存在使用address
		// 帐户余额已存在使用accBalance
		// 信用额度已存在使用creditAmt
		// 已使用额度已存在使用useCreAmt
		// 充值金额已存在使用amount

		/* 续费撤销上送报文 */
		f = new Field("oldPbSeqno", VariableType.ASCII, LengthType.VARIABLE,
				40, "oldPbSeqno");// 原流水号
		addFieldConfig(f.getName(), f);

		/* 续费撤销返回报文 */
		// 撤销金额已存在使用amount
		// 商户号已存在使用shopCode
		// 商户名已存在使用companyName
		// 帐户余额已存在使用accBalance
		// 信用额度已存在使用creditAmt
		// 已使用额度已存在使用useCreAmt

		/* 备付金账户交易明细查询上送报文 */
		f = new Field("queryType", VariableType.ASCII, LengthType.VARIABLE, 2,
				"queryType");// 查询类型
		addFieldConfig(f.getName(), f);
		// 商户号已存在使用shopCode
		f = new Field("queryStartDate", VariableType.ASCII,
				LengthType.VARIABLE, 14, "queryStartDate");// 查询开始时间
		addFieldConfig(f.getName(), f);
		f = new Field("queryEndDate", VariableType.ASCII, LengthType.VARIABLE,
				14, "queryEndDate");// 查询结束时间
		addFieldConfig(f.getName(), f);
		
		/*f = new Field("numPerPage", VariableType.ASCII, LengthType.VARIABLE, 3,
				"numPerPage");// 每页显示笔数
		addFieldConfig(f.getName(), f);*/
		
		f = new Field("packageNum", VariableType.ASCII, LengthType.VARIABLE, 3,
				"packageNum");// 第几页
		addFieldConfig(f.getName(), f);

		/* 备付金账户交易明细查询返回报文 */
		// 第几页已存在使用packageNum
		// f = new Field("tranTime", VariableType.ASCII, LengthType.VARIABLE,
		// 14,
		// "tranTime");// 交易时间
		// addFieldConfig(f.getName(), f);
		// f = new Field("tranType", VariableType.ASCII, LengthType.VARIABLE, 2,
		// "tranType");// 交易类型
		// addFieldConfig(f.getName(), f);
		// // 交易金额已存在使用amount
		// f = new Field("busiCode", VariableType.ASCII, LengthType.VARIABLE, 3,
		// "busiCode");// 业务类型
		// addFieldConfig(f.getName(), f);
		// f = new Field("customerNo", VariableType.ASCII, LengthType.VARIABLE,
		// 40, "customerNo");// 用户号码
		// addFieldConfig(f.getName(), f);
		// f = new Field("customerName", VariableType.ASCII,
		// LengthType.VARIABLE,
		// 40, "customerName");// 用户名称
		// addFieldConfig(f.getName(), f);
		// // 备注已存在使用remark
		f = new Field("numActual", VariableType.ASCII, LengthType.FIXED, 1, 3,
				AlignType.LEFTZERO, "numActual");// 实际返回笔数
		addFieldConfig(f.getName(), f);
		f = new Field("packageFlag", VariableType.ASCII, LengthType.VARIABLE,
				1, "packageFlag");// 是否有后续数据
		addFieldConfig(f.getName(), f);

		/** 账户密码重置上送报文 */
		// 请求标识已存在，使用queryType
		// 商户号已存在，使用shopCode
		// 商户负责人姓名已存在，使用contact
		// 负责人身份证号码已存在，使用auid
		f = new Field("agent", VariableType.ASCII, LengthType.VARIABLE, 6,
				"agent");// 代办人姓名
		addFieldConfig(f.getName(), f);
		f = new Field("agentauid", VariableType.ASCII, LengthType.VARIABLE, 20,
				"agentauid");// 代办人身份证号码
		addFieldConfig(f.getName(), f);
		// f = new Field("accPwd", VariableType.ASCII, LengthType.VARIABLE, 6,
		// "accPwd");// 新密码
		// addFieldConfig(f.getName(), f);

		/** 账户密码重置返回报文 */
		// 商户号已存在，使用shopCode
		// 商户负责人姓名已存在，使用contact
		// 负责人身份证号码已存在，使用auid
		// 代办人姓名已存在，使用agent
		// 代办人身份证号码已存在，使用agentauid

		//末笔交易查询
		f = new Field("oldTradeDate", VariableType.ASCII, LengthType.VARIABLE, 8,
				"oldTradeDate");// 原交易日期
		addFieldConfig(f.getName(), f);
		f = new Field("oldPosSeqno", VariableType.ASCII, LengthType.VARIABLE, 20,
				"oldPosSeqno");// 原pos流水
		addFieldConfig(f.getName(), f);
	}

	private static FieldsConfig instance = null;

	public static FieldsConfig getInstance() {
		if (instance != null)
			return instance;
		instance = new SPLITSTRINGFieldsConfig();
		return instance;
	}

	protected void addFieldConfig(String name, Field f) {
		fieldsMap.put(name, f);
	}
}
