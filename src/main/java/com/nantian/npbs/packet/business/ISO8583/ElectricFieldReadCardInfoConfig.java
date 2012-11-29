package com.nantian.npbs.packet.business.ISO8583;

import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldsConfig;
import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 解包55位元读卡信息配置类
 * @author jxw 
 *
 */
public class ElectricFieldReadCardInfoConfig extends FieldsConfig {

	private  Field fields[] = new Field[100];
	
	private static FieldsConfig instance = null;
	
	public ElectricFieldReadCardInfoConfig() {
		setFields(fields);
	}
	
	public static FieldsConfig getInstance() {
		if (instance != null)
			return instance;
		instance = new ElectricFieldReadCardInfoConfig();
		return instance;
	}
	
	@Override
	protected  void initFieldConfig() {
		
		// 电表识别号
		int fieldNo = 1;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 20, AlignType.NONE, "电表识别号");
		
		// 随机数
		fieldNo = 2; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.NONE, "随机数");
		
		// 卡序列号
		fieldNo = 3; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.NONE, "卡序列号");
		
		// 读写器句柄
		fieldNo = 4;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 10, AlignType.NONE, "读写器句柄");
		
		// 电卡状态
		fieldNo = 5;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.NONE, "电卡状态");
		
		// 购电次数
		fieldNo = 6;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "购电次数");
		
		// 电卡总购电字
		fieldNo = 7;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "电卡总购电字");
		
		// 剩余电字
		fieldNo = 8;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "剩余电字");
		
		// 过零电字
		fieldNo = 9;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "过零电字");
		
		// 总用电字
		fieldNo = 10;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "总用电字");
		
		// 电卡类型
		fieldNo = 11;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.RIGHTSPACE, "电卡类型");
		
		// 尖用电字数
		fieldNo = 12;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "尖用电字数");
		
		// 峰用电字数
		fieldNo = 13;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "峰用电字数");
		
		// 谷用电字数
		fieldNo = 14;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "谷用电字数");
		
		// 平用电字数
		fieldNo = 15;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "平用电字数");
		
		// 回写时间
		fieldNo = 16;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 6, AlignType.NONE, "回写时间");
		
		
		// 华电IC卡特有数据
		// 华电IC卡_用户号
		fieldNo = 20; 
		fields[fieldNo] =
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 20, AlignType.NONE, "HuaIC_UserCode");
		
		// 华电IC卡_卡分散数据
		fieldNo = 21; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 256, AlignType.NONE, "HuaIC_CardMsg");
		
		// 华电IC卡_参数信息文件
		fieldNo = 22; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 90, AlignType.NONE, "HuaIC_ParasFile");
		
		// 华电IC卡_账户余额
		fieldNo = 23; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 10, AlignType.NONE, "HuaIC_AccBalance");
		
		//============新奥燃气IC卡卡数据=====================
		
		//新奥IC卡  -IC卡号
		fieldNo = 30; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 20, AlignType.NONE, "IC卡号");
		
		//备注信息
		fieldNo = 31; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.NONE, "备注信息");
		
		//发卡次数
		fieldNo = 32; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.NONE, "发卡次数");
		
		//客户预购气量
		fieldNo = 33; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 5, AlignType.NONE, "客户预购气量");
		
		//加密串
		fieldNo = 34; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 128, AlignType.NONE, "加密串");
		
		
		
		//====================河电国标IC卡卡数据==============
		//河电国标IC卡--卡片序列号
		fieldNo = 40; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.NONE, "卡片序列号");

		//河电国标IC卡--卡片信息
		fieldNo = 41; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 256, AlignType.NONE, "卡片信息");

		//河电国标IC卡--卡片状态
		fieldNo = 42; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.NONE, "卡片状态");
		
		//河电国标IC卡--随机数
		fieldNo = 43; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 16, AlignType.NONE, "随机数");
		
		//河电国标IC卡--电表识别号(电卡编号)
		fieldNo = 44; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 12, AlignType.NONE, "电表识别号(电卡编号)");

		//河电国标IC卡--电表出厂编号
		fieldNo = 45; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 12, AlignType.NONE, "电表出厂编号");

		//河电国标IC卡--电卡类型
		fieldNo = 46; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 2, AlignType.NONE, "电卡类型");

		//河电国标IC卡--购电次数
		fieldNo = 47; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "购电次数");

		//河电国标IC卡--剩余金额
		fieldNo = 48; 
		fields[fieldNo] = 
			new Field(fieldNo, VariableType.ASCII, LengthType.FIXED, 0, 8, AlignType.NONE, "剩余金额");
		
		// add by fengyafang 20120428
		// 河北农电IC卡--客户编号
		fieldNo = 49;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "客户编号");
		// 河北农电IC卡--电卡编号
		fieldNo = 50;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "电卡编号");
		// 河北农电IC卡--业务标志位
		fieldNo = 51;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 1, AlignType.NONE, "业务标志位");
		// 河北农电IC卡--读卡字符串
		fieldNo = 54;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 500, AlignType.NONE, "读卡字符串");

		// 用户名称
		fieldNo = 55;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "用户名称");
		// 用电地址
		fieldNo = 56;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 60, AlignType.NONE, "用电地址");
		// 核算单位
		fieldNo = 57;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 6, AlignType.NONE, "核算单位");
		// 供电单位
		fieldNo = 58;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "供电单位");
		// 预付费类别
		fieldNo = 59;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "预付费类别");
		// 综合倍率
		fieldNo = 60;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 10, AlignType.NONE, "综合倍率");
		// 购电电价
		fieldNo = 61;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "购电电价");
		// 对账批次
		fieldNo = 62;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 24, AlignType.NONE, "对账批次"); 
		// 电能表编号
		fieldNo = 64;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "电能表编号");
		// 电能表标识
		fieldNo = 65;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 1, AlignType.NONE, "电能表标识");
		// 是否允许购电
		fieldNo = 66;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 1, AlignType.NONE, "是否允许购电");
		// 不允许购电原因
		fieldNo = 67;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 40, AlignType.NONE, "不允许购电原因");
		// 查询业务代码
		fieldNo = 68;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "查询业务代码");//02-售电，03-冲正，06-补写卡
	 
	}
}
