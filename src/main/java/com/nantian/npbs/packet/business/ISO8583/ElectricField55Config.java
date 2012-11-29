/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldsConfig;
import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * @author TsaiYee
 * 
 */
public class ElectricField55Config extends FieldsConfig {

	private Field fields[] = new Field[100];

	private static FieldsConfig instance = null;

	public ElectricField55Config() {
		setFields(fields);
	}

	public static FieldsConfig getInstance() {
		if (instance != null)
			return instance;
		instance = new ElectricField55Config();
		return instance;
	}

	@Override
	protected void initFieldConfig() {
		//=============河电省标卡=============================
		
		// 卡数据
		int fieldNo = 1;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 200, AlignType.NONE, "卡数据");
		
		// 用户编码
		fieldNo = 2;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "用户编码");

		// 用户名称
		fieldNo = 3;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 60, AlignType.RIGHTSPACE, "用户名称");
		
		// 用电地址
		fieldNo = 4;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 80, AlignType.RIGHTSPACE, "用户地址");
	
		// 核算单位编号
		fieldNo = 5;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "核算单位编号");
		
		// 低保户标志
		fieldNo = 6;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "低保户标志");

		// 低保户剩余金额
		fieldNo = 7;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 6, AlignType.LEFTZERO, "低保户剩余金额");

		// 卡号、电表识别号
		fieldNo = 8;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 20, AlignType.NONE, "电表识别号");

		// 卡序列号
		fieldNo = 9;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "卡序列号");
		
		// 随机数
		fieldNo = 10;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "随机数");
		
		// 购电方式
		fieldNo = 11;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 1, AlignType.RIGHTSPACE, "购电方式");
		
		// 购电值
		fieldNo = 12;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "购电值");
		
		// 购电次数
		fieldNo = 13;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "购电次数");
		
		// 电卡总购电字
		fieldNo = 14;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "电卡总购电次数");
		
		// 剩余电字
		fieldNo = 15;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "剩余电字");
		
		// 过零电字
		fieldNo = 16;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "过零电字");
		
		// 总用电字
		fieldNo = 17;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "总用电字");
		
		// 电卡类型
		fieldNo = 18;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.RIGHTSPACE, "电卡类型");
		
		// 尖用电字数
		fieldNo = 19;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "尖用电字数");
		
		// 峰用电字数
		fieldNo = 20;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "峰用电字数");
		
		// 谷用电字数
		fieldNo =21;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "谷用电字数");
		
		// 平用电字数
		fieldNo = 22;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "平用电字数");
		
		// 回写时间
		fieldNo = 23;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 6, AlignType.RIGHTSPACE, "回写时间");

		// 本次余额
		fieldNo = 24;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.LEFTZERO, "本次余额");

		// 交易时间
		fieldNo = 25;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "交易时间");
		
		// 缴费返回pos
		// 电价名称
		fieldNo = 26;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 30, AlignType.RIGHTSPACE, "电价名称");
		
		// 电价
		fieldNo = 27;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.LEFTZERO, "电价");
		
		// 上次余额
		fieldNo = 28;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.LEFTZERO, "上次余额");

		// 外部认证数据
		fieldNo = 29;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "外部认证数据");
		
		// 原交易流水号
		fieldNo = 30;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0,14, AlignType.RIGHTSPACE, "原交易流水号");

		//欠费金额
		fieldNo = 31;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.LEFTZERO, "欠费金额");
		
		// 购电权限
		fieldNo = 32;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "购电权限");
		
		//本次应收
		fieldNo = 33;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.LEFTZERO, "本次应收");
		
		//写卡电量
		fieldNo = 34;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "写卡电量");
		
		//备注1(16Bytes)
		fieldNo = 35;
		fields[fieldNo] = new Field(fieldNo, VariableType.BINARY,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "备注1");
		
		//备注2(32Bytes)
		fieldNo = 36;
		fields[fieldNo] = new Field(fieldNo, VariableType.BINARY,
				LengthType.FIXED, 0, 32, AlignType.RIGHTSPACE, "备注2");
		
		//
		fieldNo = 37;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "writeICStatus");
		
		//
		fieldNo = 38;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "writeICResultType");
		
		// 写卡数据包
		fieldNo = 39;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 200, AlignType.RIGHTSPACE, "写卡数据包");
		
		
		//华电IC卡数据
		//HuaIC_用户编号
		fieldNo = 40;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 20, AlignType.NONE, "HuaIC_userCode");
		
		//HuaIC_卡分散数据
		fieldNo = 41;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 256, AlignType.NONE, "HuaIC_cardMsg");
		
		//参数信息文件
		fieldNo = 42;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 90, AlignType.NONE, "HuaIC_paraFile");
		
		//河电省标卡补写卡标识
		fieldNo = 43;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 1, AlignType.NONE, "HD_bxk_bz");
		

		// 原电力流水号
		fieldNo = 44;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0,32, AlignType.RIGHTSPACE, "elecSeqno");
		
		//---------------------河电国标卡
		// 购电权限
		fieldNo = 45;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0,2, AlignType.NONE, "购电权限");

		//预收金额
		fieldNo = 46;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.LEFTZERO, "预收金额");

		//调整金额
		fieldNo = 47;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.LEFTZERO, "调整金额");
		
		// 国标卡卡数据
		fieldNo = 48;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 332, AlignType.NONE, "国标卡卡数据");

		//钱包文件的Mac值
		fieldNo = 49;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.NONE, "钱包文件的Mac值");
		
		//返写区文件的Mac值
		fieldNo = 50;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.NONE, "返写区文件的Mac值");
		//参数信息文件
		fieldNo = 51;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 90, AlignType.NONE, "参数信息文件");
		//参数信息文件Mac值
		fieldNo = 52;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.NONE, "参数信息文件Mac值");
		//写卡数据
		fieldNo = 53;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.NONE, "写卡数据");
		
		//======================新澳燃气IC卡===========================
		//IC卡号
		fieldNo = 54;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 20, AlignType.LEFTSPACE, "IC卡号");

		//备注信息
		fieldNo = 55;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "备注信息");
		
		//客户姓名
		fieldNo = 56;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 60, AlignType.RIGHTSPACE, "客户姓名");
		
		//本次最大气量
		fieldNo = 57;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 6, AlignType.LEFTZERO, "本次最大气量");
		
		//账户余额
		fieldNo = 58;
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 10, AlignType.LEFTZERO, "账户余额");
		
		//发卡次数
		fieldNo = 59; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 2, AlignType.NONE, "发卡次数");
		
		//客户预购气量
		fieldNo = 60; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 5, AlignType.NONE, "客户预购气量");
		
		//加密串
		fieldNo = 61; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII, 
				LengthType.FIXED, 0, 128, AlignType.NONE, "加密串");

		
		//购气金额
		fieldNo = 64; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 12, AlignType.LEFTZERO, "购气金额");
		
		
		//农电写卡字符串
		fieldNo = 65; 
		fields[fieldNo] = new Field(fieldNo, VariableType.ASCII,
				LengthType.FIXED, 0, 500, AlignType.LEFTZERO, "写卡字符串");
		

		
	}
}
