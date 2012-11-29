/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

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
public class TUXSTRINGFieldsConfig extends FieldsConfig {
	public static String HEADER_SPLITER = "0";
	public static String FIELD_SPLITER = "|";

	private Map<String, Field> fieldsMap = new HashMap<String, Field>();

	public TUXSTRINGFieldsConfig() {
		setFieldsMap(fieldsMap);
	}

	@Override
	protected void initFieldConfig() {
		Field f = null;

		// 用户编号
		f = new Field("D13_13_HESB_YHBH", VariableType.ASCII,
				LengthType.VARIABLE, 16, "UserCode");
		addFieldConfig(f.getName(), f);

		// 用户名称
		f = new Field("D13_13_HESB_YHMC", VariableType.ASCII,
				LengthType.VARIABLE, 60, "CustomerName");
		addFieldConfig(f.getName(), f);

		// 地区码
		f = new Field("D13_13_HESB_DQM", VariableType.ASCII,
				LengthType.VARIABLE, 16, "AreaCode");
		addFieldConfig(f.getName(), f);

		// 电卡状态
		f = new Field("D13_13_HESB_DKZT", VariableType.ASCII,
				LengthType.VARIABLE, 2, "CardStatus");
		addFieldConfig(f.getName(), f);

		// 用电地址
		f = new Field("D13_13_HESB_YDDZ", VariableType.ASCII,
				LengthType.VARIABLE, 80, "Address");
		addFieldConfig(f.getName(), f);

		// IC卡查询-----------------------------------------------

		//核算单位编号
		f = new Field("D13_13_HESB_HSDWBH",VariableType.ASCII, LengthType.VARIABLE, 16, "hesuanCode");
		addFieldConfig(f.getName(), f);
		
		//低保户标志
		f = new Field("D13_13_HESB_DBHBZ",VariableType.ASCII, LengthType.VARIABLE, 2, "dbhflag");
		addFieldConfig(f.getName(), f);
		
		// 低保户剩余金额
		f = new Field("D13_13_HESB_DBHSYJE", VariableType.ASCII,LengthType.VARIABLE, 6, "dibaofei");
		addFieldConfig(f.getName(), f);

		//换表欠电量
		f = new Field("D13_13_HESB_HBQDL",VariableType.ASCII, LengthType.VARIABLE, 16, "hbqdl");
		addFieldConfig(f.getName(), f);
		
		//调整电量
		f = new Field("D13_13_HESB_TZDL",VariableType.ASCII, LengthType.VARIABLE, 16, "tzdl");
		addFieldConfig(f.getName(), f);
		
		// 欠费金额
		f = new Field("D13_13_HESB_QFJE", VariableType.ASCII,
				LengthType.VARIABLE, 16, "qianfeiJE");
		addFieldConfig(f.getName(), f);

		// 上次余额
		f = new Field("D13_13_HESB_SCYE", VariableType.ASCII,LengthType.VARIABLE, 16, "lastBalance");
		addFieldConfig(f.getName(), f);

		//扣减金额
		f = new Field("D13_13_HESB_KJJE",VariableType.ASCII, LengthType.VARIABLE, 16, "kjje");
		addFieldConfig(f.getName(), f);
		
		// 电价名称
		f = new Field("D13_13_HESB_DJMC", VariableType.ASCII,
				LengthType.VARIABLE, 30, "elecName");
		addFieldConfig(f.getName(), f);

		// 电价
		f = new Field("D13_13_HESB_DJ", VariableType.ASCII,
				LengthType.VARIABLE, 16, "elecprice");
		addFieldConfig(f.getName(), f);

		//购电权限
		f = new Field("D13_13_HESB_GDQX",VariableType.ASCII, LengthType.VARIABLE, 2, "gdqx");
		addFieldConfig(f.getName(), f);

		//上次尖抄见电量
		f = new Field("D13_13_HESB_SCJCJDL",VariableType.ASCII, LengthType.VARIABLE, 16, "scjcjdl");
		addFieldConfig(f.getName(), f);

		//上次峰抄见电量
		f = new Field("D13_13_HESB_SCFCJDL",VariableType.ASCII, LengthType.VARIABLE, 16, "scfcjdl");
		addFieldConfig(f.getName(), f);

		//上次平抄见电量
		f = new Field("D13_13_HESB_SCPCJDL",VariableType.ASCII, LengthType.VARIABLE, 16, "scpcjdl");
		addFieldConfig(f.getName(), f);

		//上次谷抄见电量
		f = new Field("D13_13_HESB_SCGCJDL",VariableType.ASCII, LengthType.VARIABLE, 16, "scgcjdl");
		addFieldConfig(f.getName(), f);
		
		//电价数量
		f = new Field("RECORD_REC", VariableType.ASCII, LengthType.FIXED, 1, 4, AlignType.RIGHTSPACE, "scgcjdl");
		addFieldConfig(f.getName(), f);

		// 外部认证数据
		f = new Field("D13_13_HESB_WBRZS",VariableType.ASCII, LengthType.VARIABLE, 16, "wbrzdata");
		addFieldConfig(f.getName(), f);

		//电表识别号
		f = new Field("D13_13_HESB_DBSBH",VariableType.ASCII, LengthType.VARIABLE, 20, "cardNo");
		addFieldConfig(f.getName(), f);

		// 卡序列号
		f = new Field("D13_13_HESB_YHKFSYZ", VariableType.ASCII,
				LengthType.FIXED, 0, 16, AlignType.RIGHTSPACE, "cardSerno");
		addFieldConfig(f.getName(), f);

		// 随机数
		f = new Field("D13_13_HESB_SJS",VariableType.ASCII, LengthType.VARIABLE, 16, "randomNum");
		addFieldConfig(f.getName(), f);
		
		// 购电方式
		f = new Field("D13_13_HESB_GDFS", VariableType.ASCII, LengthType.FIXED,
				0, 1, AlignType.RIGHTSPACE, "buyElecMode");
		addFieldConfig(f.getName(), f);

		// 购电值
		f = new Field("D13_13_HESB_GDZ",VariableType.ASCII, LengthType.VARIABLE, 16, "curElectric");
		addFieldConfig(f.getName(), f);

		// 购电次数
		f = new Field("D13_13_HESB_GDCS",VariableType.ASCII, LengthType.VARIABLE, 10, "buyElecNum");
		addFieldConfig(f.getName(), f);

		// 电卡总购电字
		f = new Field("D13_13_HESB_DKZGDZ",VariableType.ASCII, LengthType.VARIABLE, 10, "buyElecTotal");
		addFieldConfig(f.getName(), f);

		// 剩余电字
		f = new Field("D13_13_HESB_SYDZ",VariableType.ASCII, LengthType.VARIABLE, 10, "remainElec");
		addFieldConfig(f.getName(), f);

		// 过零电字
		f = new Field("D13_13_HESB_GLDZ",VariableType.ASCII, LengthType.VARIABLE, 10, "zeroElec");
		addFieldConfig(f.getName(), f);

		// 总用电字
		f = new Field("D13_13_HESB_ZYDZ",VariableType.ASCII, LengthType.VARIABLE, 10, "allUseElec");
		addFieldConfig(f.getName(), f);

		// 电卡类型
		f = new Field("D13_13_HESB_DKLX",VariableType.ASCII, LengthType.VARIABLE, 2, "ElecType");
		addFieldConfig(f.getName(), f);

		// 尖用电字数
		f = new Field("D13_13_HESB_JYDZS",VariableType.ASCII, LengthType.VARIABLE, 10, "jianydzs");
		addFieldConfig(f.getName(), f);

		// 峰用电字数
		f = new Field("D13_13_HESB_FYDZS",VariableType.ASCII, LengthType.VARIABLE, 10, "fengydzs");
		addFieldConfig(f.getName(), f);

		// 谷用电字数
		f = new Field("D13_13_HESB_GYDZS",VariableType.ASCII, LengthType.VARIABLE, 10, "guydzs");
		addFieldConfig(f.getName(), f);

		// 平用电字数
		f = new Field("D13_13_HESB_PYDZS",VariableType.ASCII, LengthType.VARIABLE, 10, "pingydzs");
		addFieldConfig(f.getName(), f);

		// 回写时间
		f = new Field("D13_13_HESB_HXSJ",VariableType.ASCII, LengthType.VARIABLE, 8, "writebtime");
		addFieldConfig(f.getName(), f);

		// 外部认证数据
		f = new Field("D13_13_HESB_WBRZSJ",VariableType.ASCII, LengthType.VARIABLE, 16, "wbrzdata");
		addFieldConfig(f.getName(), f);

		// 原交易流水号
		f = new Field("D13_13_HESB_YJYLSH",VariableType.ASCII, LengthType.VARIABLE, 32, "origSysJournalSeqno");
		addFieldConfig(f.getName(), f);
		
		// 交易状态
		f = new Field("D13_13_HESB_JYZT", VariableType.ASCII, LengthType.FIXED,
				0, 8, AlignType.RIGHTSPACE, "writeICStatus");
		addFieldConfig(f.getName(), f);

		// 写卡数据包
		f = new Field("D13_13_HESB_XKSJB", VariableType.ASCII,
				LengthType.FIXED, 0, 200, AlignType.RIGHTSPACE, "writeData");
		addFieldConfig(f.getName(), f);
		
		// 本次购电量
		f = new Field("D13_13_HESB_BCGDL", VariableType.ASCII,LengthType.VARIABLE, 16, "bcgdl");
		addFieldConfig(f.getName(), f);
		
		// 本次余额
		f = new Field("D13_13_HESB_BCYE", VariableType.ASCII,LengthType.VARIABLE, 16, "HESB_BCYE");
		addFieldConfig(f.getName(), f);
		
		// 省标阶梯标志
		f = new Field("D13_13_HESB_JTBZ", VariableType.ASCII,LengthType.VARIABLE, 2, "HESB_JTBZ");
		addFieldConfig(f.getName(), f);
		
		// 省标本年一档用电量
		f = new Field("D13_13_HESB_BNYDYDL", VariableType.ASCII,LengthType.VARIABLE, 16, "HESB_BNYDYDL");
		addFieldConfig(f.getName(), f);
		
		//省标本年二档用电量
		f = new Field("D13_13_HESB_BNEDYDL", VariableType.ASCII,LengthType.VARIABLE, 16, "HESB_BNEDYDL");
		addFieldConfig(f.getName(), f);
		
		// 省标本年三档用电量
		f = new Field("D13_13_HESB_BNSDYDL", VariableType.ASCII,LengthType.VARIABLE, 16, "HESB_BNSDYDL");
		addFieldConfig(f.getName(), f);
		
		// 省标第N档剩余电量
		f = new Field("D13_13_HESB_DNDSYDL", VariableType.ASCII,LengthType.VARIABLE, 24, "HESB_DNDSYDL");
		addFieldConfig(f.getName(), f);
		

		// 局号
		// f = new Field("H_BRCH_NO", VariableType.ASCII, LengthType.FIXED, 0,
		// 7,
		// AlignType.RIGHTSPACE, "branchNo");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_BRCH_NO", VariableType.ASCII, LengthType.VARIABLE, 0,
				4, 0, 7, "branchNo");
		addFieldConfig(f.getName(), f);

		// 柜员号
		// f = new Field("H_OPER_NO", VariableType.ASCII, LengthType.FIXED, 0,
		// 2,
		// AlignType.RIGHTSPACE, "operNo");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_OPER_NO", VariableType.ASCII, LengthType.VARIABLE, 0,
				4, 0, 4, "operNo");
		addFieldConfig(f.getName(), f);

		// 交易流水号
		// f = new Field("H_SEQ_NO", VariableType.ASCII, LengthType.FIXED, 0,
		// 10,
		// AlignType.RIGHTSPACE, "journalSeqno");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_SEQ_NO", VariableType.ASCII, LengthType.VARIABLE, 0,
				4, 0, 10, "journalSeqno");
		addFieldConfig(f.getName(), f);

		// 本地IP地址
		// f = new Field("H_IP_ADDR", VariableType.ASCII, LengthType.FIXED, 0,
		// 10,
		// AlignType.RIGHTSPACE, "localIpAddr");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_IP_ADDR", VariableType.ASCII, LengthType.VARIABLE, 0,
				4, 0, 10, "localIpAddr");
		addFieldConfig(f.getName(), f);

		// 终端号
		// f = new Field("H_TTY", VariableType.ASCII, LengthType.FIXED, 0, 15,
		// AlignType.RIGHTSPACE, "terminalNo");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_TTY", VariableType.ASCII, LengthType.VARIABLE, 0, 4,
				0, 15, "terminalNo");
		addFieldConfig(f.getName(), f);

		// 授权柜员号
		// f = new Field("H_AUTH_OPER_NO", VariableType.ASCII, LengthType.FIXED,
		// 0, 2, AlignType.RIGHTSPACE, "authOperNo");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_AUTH_OPER_NO", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 4, "authOperNo");
		addFieldConfig(f.getName(), f);

		// 渠道流水号
		// f = new Field("H_CHANNEL_TRACE", VariableType.ASCII,
		// LengthType.FIXED,
		// 0, 12, AlignType.RIGHTSPACE, "channelTrace");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_CHANNEL_TRACE", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 12, "channelTrace");
		addFieldConfig(f.getName(), f);

		// 渠道标识
		// f = new Field("H_CHANNEL_NO", VariableType.ASCII, LengthType.FIXED,
		// 0,
		// 2, AlignType.RIGHTSPACE, "channelNo");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_CHANNEL_NO", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 4, "channelNo");
		addFieldConfig(f.getName(), f);

		// 网点机构号
		// f = new Field("H_BRCH_NO_NEW", VariableType.ASCII, LengthType.FIXED,
		// 0,
		// 8, AlignType.RIGHTSPACE, "branchNoNew");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_BRCH_NO_NEW", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 8, "branchNoNew");
		addFieldConfig(f.getName(), f);

		// 柜员号
		// f = new Field("H_OPER_NO_NEW", VariableType.ASCII, LengthType.FIXED,
		// 0,
		// 4, AlignType.RIGHTSPACE, "operNoNew");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_OPER_NO_NEW", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 4, "operNoNew");
		addFieldConfig(f.getName(), f);

		// 上送文件个数
		// f = new Field("H_SFILE_NUM", VariableType.ASCII, LengthType.FIXED, 0,
		// 4, AlignType.RIGHTSPACE, "sendFileNum");
		// addFieldConfig(f.getName(), f);
		f = new Field("H_SFILE_NUM", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 4, "sendFileNum");
		addFieldConfig(f.getName(), f);

		// 不带路径上送文件名
		f = new Field("H_SEND_FILE", VariableType.ASCII, LengthType.FIXED, 0,
				60, AlignType.RIGHTSPACE, "sendFileName");
		addFieldConfig(f.getName(), f);

		// 主机返回处理代码
		f = new Field("HOST_RET_ERR", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 4, "retErr");
		addFieldConfig(f.getName(), f);

		// 主机返回信息
		f = new Field("HOST_RET_MSG", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 60, "retMsg");
		addFieldConfig(f.getName(), f);

		// 系统日期
		f = new Field("H_SYS_DATE", VariableType.ASCII, LengthType.VARIABLE, 0,
				4, 0, 10, "sysDate");
		addFieldConfig(f.getName(), f);

		// 交易流水号
		// f = new Field("H_SEQ_NO", VariableType.ASCII, LengthType.FIXED, 0,
		// 10, AlignType.RIGHTSPACE, "sendFileName");
		// addFieldConfig(f.getName(), f);

		// 下送文件个数
		f = new Field("H_RFILE_NUM", VariableType.ASCII, LengthType.FIXED, 1,
				4, AlignType.RIGHTSPACE, "rFileNum");
		addFieldConfig(f.getName(), f);

		// 不带路径下送文件名
		f = new Field("H_RECV_FILE", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 60, "rFileName");
		addFieldConfig(f.getName(), f);

		// 交易数据结束标志
		f = new Field("H_REND_FLAG", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 4, "rendFlag");
		addFieldConfig(f.getName(), f);

		// 张家口水费
		// 欠费月数数
		f = new Field("D13_13_ZJKW_REC_NUM", VariableType.ASCII,
				LengthType.FIXED, 1, 4, AlignType.RIGHTSPACE,
				"D13_13_ZJKW_REC_NUM");
		addFieldConfig(f.getName(), f);

		// 用户名称
		f = new Field("D13_13_ZJKW_USER_NAME", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 25, "D13_13_ZJKW_USER_NAME");
		addFieldConfig(f.getName(), f);

		// 用户地址
		f = new Field("D13_13_ZJKW_USER_ADDR", VariableType.ASCII,
				LengthType.VARIABLE, 0, 0, 0, 40, "D13_13_ZJKW_USER_ADDR");
		addFieldConfig(f.getName(), f);

		// 应缴金额
		f = new Field("D13_13_ZJKW_SUM_FEE", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 16, "D13_13_ZJKW_SUM_FEE");
		addFieldConfig(f.getName(), f);

		// 缴费金额
		f = new Field("D13_13_ZJKW_PAY_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 16, "D13_13_ZJKW_PAY_AMT");
		addFieldConfig(f.getName(), f);

		// 凭证号码
		f = new Field("D13_13_ZJKW_CERT_NO", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 20, "D13_13_ZJKW_CERT_NO");
		addFieldConfig(f.getName(), f);

		// 凭证类型
		f = new Field("B05_VOUC_KIND", VariableType.ASCII, LengthType.VARIABLE,
				0, 4, 0, 20, "B05_VOUC_KIND");
		addFieldConfig(f.getName(), f);

		// 凭证终止序号
		f = new Field("B01_BILL_LSTNO", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 20, "B01_BILL_LSTNO");
		addFieldConfig(f.getName(), f);

		// 平来流水
		f = new Field("D13_13_ZJKW_SEQ_NO", VariableType.ASCII,
				LengthType.FIXED, 0, 20, AlignType.RIGHTSPACE,
				"D13_13_ZJKW_SEQ_NO");
		addFieldConfig(f.getName(), f);

		// 欠费总额
		f = new Field("D13_13_ZJKW_SUM_NUM", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_ZJKW_SUM_NUM");
		addFieldConfig(f.getName(), f);

		// 上次抄表时间
		f = new Field("D13_13_ZJKW_LAST_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_ZJKW_LAST_DATE");
		addFieldConfig(f.getName(), f);

		// 本次抄表时间
		f = new Field("D13_13_ZJKW_CURR_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_ZJKW_CURR_DATE");
		addFieldConfig(f.getName(), f);

		// 水费
		f = new Field("D13_13_ZJKW_AMT_1", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_ZJKW_AMT_1");
		addFieldConfig(f.getName(), f);

		// 污水费
		f = new Field("D13_13_ZJKW_AMT_2", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 9, "D13_13_ZJKW_AMT_2");
		addFieldConfig(f.getName(), f);

		// 水资源费(源水费)
		f = new Field("D13_13_ZJKW_AMT_3", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 9, "D13_13_ZJKW_AMT_3");
		addFieldConfig(f.getName(), f);

		// 上次读数
		f = new Field("D13_13_ZJKW_LAST_BAL", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 12, "D13_13_ZJKW_LAST_BAL");
		addFieldConfig(f.getName(), f);

		// 本次读数
		f = new Field("D13_13_ZJKW_CURR_BAL", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 8, "D13_13_ZJKW_CURR_BAL");
		addFieldConfig(f.getName(), f);

		// 水价1
		f = new Field("D13_13_ZJKW_PRICE_1", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 6, "D13_13_ZJKW_PRICE_1");
		addFieldConfig(f.getName(), f);

		// 水价2
		f = new Field("D13_13_ZJKW_PRICE_2", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 6, "D13_13_ZJKW_PRICE_2");
		addFieldConfig(f.getName(), f);

		// 水价3
		f = new Field("D13_13_ZJKW_PRICE_3", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 12, "D13_13_ZJKW_PRICE_3");
		addFieldConfig(f.getName(), f);

		// 本次抄时间
		f = new Field("D13_13_ZJKW_CURR_DATA", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_ZJKW_CURR_DATA");
		addFieldConfig(f.getName(), f);

		
		
		// 现金代缴电力缴费-------------------------------------------------------------------------------
		// 查询条件
		f = new Field("D13_13_HEE_QUERY", VariableType.ASCII,
				LengthType.VARIABLE, 1, "query");
		addFieldConfig(f.getName(), f);

		// 用户编号
		f = new Field("D13_13_HEE_CODE", VariableType.ASCII,
				LengthType.VARIABLE, 10, "code");
		addFieldConfig(f.getName(), f);

		// 电费年月
		f = new Field("D13_13_HEE_MONTH", VariableType.ASCII,
				LengthType.VARIABLE, 6, "month");
		addFieldConfig(f.getName(), f);

		// 用户名称
		f = new Field("D13_13_HEE_NAME", VariableType.ASCII,
				LengthType.VARIABLE, 40, "username");
		addFieldConfig(f.getName(), f);

		// 用电地址
		f = new Field("D13_13_HEE_ADDR", VariableType.ASCII,
				LengthType.VARIABLE, 80, "address");
		addFieldConfig(f.getName(), f);

		// 合计金额
		f = new Field("D13_13_HEE_TOTAL_BILL", VariableType.ASCII,
				LengthType.VARIABLE, 19, "totalBill");
		addFieldConfig(f.getName(), f);
		
		// 本次余额
		f = new Field("D13_13_HEE_THIS_BALANCE", VariableType.ASCII,
				LengthType.VARIABLE, 19, "thisBalance");
		addFieldConfig(f.getName(), f);
		
		// 起止示数
		f = new Field("D13_13_HEE_SE_NUM", VariableType.ASCII,
				LengthType.VARIABLE, 31, "SENumber");
		addFieldConfig(f.getName(), f);
		
		f = new Field("D13_13_HEE_JTDJXX", VariableType.ASCII,
				LengthType.VARIABLE, 684, "阶梯电价信息");
		addFieldConfig(f.getName(),f);
		// 合计电费
		f = new Field("D13_13_HEE_TOTAL_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 19, "totalAmt");
		addFieldConfig(f.getName(), f);

		// 合计违约金
		f = new Field("D13_13_HEE_PEN_BILL", VariableType.ASCII,
				LengthType.VARIABLE, 19, "penBill");
		addFieldConfig(f.getName(), f);

		// 合计预收
		f = new Field("D13_13_HEE_PR_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 19, "preAmt");
		addFieldConfig(f.getName(), f);

		// 金额总笔数
		f = new Field("D13_13_HEE_AMT_NUM", VariableType.ASCII,
				LengthType.VARIABLE, 4, "amtNum");
		addFieldConfig(f.getName(), f);

		// 电费明细数据
		f = new Field("D13_13_HEE_REG", VariableType.ASCII,
				LengthType.VARIABLE, 1024, "reg");
		addFieldConfig(f.getName(), f);

		// 缴费金额
		f = new Field("D13_13_HEE_PAY_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 1024, "payAmt");
		addFieldConfig(f.getName(), f);

		// 原交易流水号
		f = new Field("OLD_SEQ_NO", VariableType.ASCII,
				LengthType.VARIABLE, 9, "seqno");
		addFieldConfig(f.getName(), f);
		
		//---------------------------------------------------------------------------------------------------------------------------
		
		
		
		// 新奥燃气现金代收
		// IC卡号
		f = new Field("D13_13_XAG_USER_NO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "iccardno");
		addFieldConfig(f.getName(), f);

		// 用户名称
		f = new Field("D13_13_XAG_USER_NAME", VariableType.ASCII,
				LengthType.VARIABLE, 60, "username");
		addFieldConfig(f.getName(), f);

		// 用电地址
		f = new Field("D13_13_XAG_USER_ADDR", VariableType.ASCII,
				LengthType.VARIABLE, 100, "user_addr");
		addFieldConfig(f.getName(), f);

		// 账户余额
		f = new Field("D13_13_XAG_LAST_BAL", VariableType.ASCII,
				LengthType.VARIABLE, 12, "last_bal");
		addFieldConfig(f.getName(), f);

		// 发卡次数
		f = new Field("D13_13_XAG_CARD_NUM", VariableType.ASCII,
				LengthType.VARIABLE, 4, "card_num");
		addFieldConfig(f.getName(), f);

		// 充值金额
		f = new Field("D13_13_XAG_PAY_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 12, "pay_amt");
		addFieldConfig(f.getName(), f);

		// 接入渠道日期
		f = new Field("D13_13_CHANNEL_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 8, "username");
		addFieldConfig(f.getName(), f);

		// 最新余额
		f = new Field("D13_13_XAG_CURR_BAL", VariableType.ASCII,
				LengthType.VARIABLE, 10, "cur_bal");
		addFieldConfig(f.getName(), f);

		// 平台流水号	
		f = new Field("D13_13_XAG_SEQ_NO",VariableType.ASCII,
				LengthType.FIXED,0,20,AlignType.NONE,"seq_no");
		addFieldConfig(f.getName(), f);

		// 原交易流水号
		f = new Field("D13_13_XAG_OLD_SEQ_NO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "old_seq_no");
		addFieldConfig(f.getName(), f);

		// 保定水费
		// 用户编号
		f = new Field("D13_13_BDW_USERNO", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 8, "D13_13_BDW_USERNO");
		addFieldConfig(f.getName(), f);
		// 卡片编号
		f = new Field("D13_13_BDW_CARDNO", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 8, "D13_13_BDW_CARDNO");
		addFieldConfig(f.getName(), f);
		// 用户名称
		f = new Field("D13_13_BDW_USERNAME", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 60, "D13_13_BDW_USERNAME");
		addFieldConfig(f.getName(), f);
		// 总欠费金额
		f = new Field("D13_13_BDW_TOTALAMT", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_BDW_TOTALAMT");
		addFieldConfig(f.getName(), f);
		// 应收金额
		f = new Field("D13_13_BDW_OUGHTAMT", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 10, "D13_13_BDW_OUGHTAMT");
		addFieldConfig(f.getName(), f);
		// 第三方流水号
		f = new Field("D13_13_BDW_SEQNO3", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 32, "D13_13_BDW_SEQNO3");
		addFieldConfig(f.getName(), f);
		// 发票张数
		f = new Field("D13_13_BDW_TICKETSUM", VariableType.ASCII,
				LengthType.VARIABLE, 0, 4, 0, 2, "D13_13_BDW_TICKETSUM");
		addFieldConfig(f.getName(), f);


		
		// 华电国标IC卡
		//用户编号
		f = new Field("D13_13_HBGB_CUSTOMERNO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "customerNo");
		addFieldConfig(f.getName(), f);
		//电表编号
		f = new Field("D13_13_HBGB_METERNO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "meterNo");
		addFieldConfig(f.getName(), f);
		//用户名称
		f = new Field("D13_13_HBGB_CUSTOMERNAME", VariableType.ASCII,
				LengthType.VARIABLE, 255, "customerName");
		addFieldConfig(f.getName(), f);
		//用户地址
		f = new Field("D13_13_HBGB_CUSTOMERADDRESS", VariableType.ASCII,
				LengthType.VARIABLE, 255, "customerAdd");
		addFieldConfig(f.getName(), f);
		//账户余额
		f = new Field("D13_13_HBGB_USERACCOUNT", VariableType.ASCII,
				LengthType.VARIABLE, 10, "customerAcc");
		addFieldConfig(f.getName(), f);
		//返回购电次数
		f = new Field("D13_13_HBGB_REBUYTIMES", VariableType.ASCII,
				LengthType.VARIABLE, 10, "rebuyTimes");
		addFieldConfig(f.getName(), f);
		
		//卡序列号
		f = new Field("D13_13_HBGB_CARDSEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 16, "cardSeq");
		addFieldConfig(f.getName(), f);
		//卡分散数据
		f = new Field("D13_13_HBGB_CARDMSG", VariableType.ASCII,
				LengthType.VARIABLE, 256, "cardMessage");
		addFieldConfig(f.getName(), f);
		//随机数
		f = new Field("D13_13_HBGB_ROMNO", VariableType.ASCII,
				LengthType.VARIABLE, 16, "randomNumber");
		addFieldConfig(f.getName(), f);
		//参数信息文件
		f = new Field("D13_13_HBGB_PARATYPE", VariableType.ASCII,
				LengthType.VARIABLE, 90, "paraType");
		addFieldConfig(f.getName(), f);
		
		//认证数据1
		f = new Field("D13_13_HBGB_AUTHDATA1", VariableType.ASCII,
				LengthType.VARIABLE, 16, "authData1");
		addFieldConfig(f.getName(), f);
		//认证数据2
		f = new Field("D13_13_HBGB_AUTHDATA2", VariableType.ASCII,
				LengthType.VARIABLE, 16, "authData2");
		addFieldConfig(f.getName(), f);
		//认证数据3
		f = new Field("D13_13_HBGB_AUTHDATA3", VariableType.ASCII,
				LengthType.VARIABLE, 16, "authData3");
		addFieldConfig(f.getName(), f);
		//写卡数据
		f = new Field("D13_13_HBGB_WRITEPARAM", VariableType.ASCII,
				LengthType.VARIABLE, 2048, "cardWritenData");
		addFieldConfig(f.getName(), f);
		//电力返回流水
		f = new Field("D13_13_HBGB_ELECSEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 36, "electricSeq");
		addFieldConfig(f.getName(), f);
		//平台流水
		f = new Field("D13_13_HBGB_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 18, "terminalSeq");
		addFieldConfig(f.getName(), f);
		//便民日期
		f = new Field("D13_13_HBGB_BM_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 8, "PbSerial");
		addFieldConfig(f.getName(), f);
		//便民流水
		f = new Field("D13_13_HBGB_BM_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 30, "BMTradeDate");
		addFieldConfig(f.getName(), f);
		
		
		// 华电现金
		//用户编号
		f = new Field("D13_13_HBE_CUSTOMERNO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "customerNo");
		addFieldConfig(f.getName(), f);
		//用户名称
		f = new Field("D13_13_HBE_CUSTOMERNAME", VariableType.ASCII,
				LengthType.VARIABLE, 255, "customerName");
		addFieldConfig(f.getName(), f);
		//用户地址
		f = new Field("D13_13_HBE_CUSTOMERADDRESS", VariableType.ASCII,
				LengthType.VARIABLE, 255, "customerAdd");
		addFieldConfig(f.getName(), f);
		//账户余额
		f = new Field("D13_13_HBE_USERACCOUNT", VariableType.ASCII,
				LengthType.VARIABLE, 18, "userAccount");
		addFieldConfig(f.getName(), f);
		//供电单位编号
		f = new Field("D13_13_HBE_ORGNO", VariableType.ASCII,
				LengthType.VARIABLE, 16, "orgNumber");
		addFieldConfig(f.getName(), f);
		//记录条数
		f = new Field("D13_13_HBE_RECORDNUM", VariableType.ASCII,
				LengthType.VARIABLE, 2, "recordNo");
		addFieldConfig(f.getName(), f);
		//记录明细
		f = new Field("D13_13_HBE_RECORD", VariableType.ASCII,
				LengthType.VARIABLE, 1024, "record");
		addFieldConfig(f.getName(), f);
				
		//阶梯欠费
		f = new Field("D13_13_HBE_LEV_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 18, "levAmt");
		addFieldConfig(f.getName(), f);
		
		//缴费金额
		f = new Field("D13_13_HBE_ BUYACCOUNT", VariableType.ASCII,
				LengthType.VARIABLE, 16, "buyAccount");
		addFieldConfig(f.getName(), f);
		//平台流水
		f = new Field("D13_13_HBE_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 18, "seqNumber");
		addFieldConfig(f.getName(), f);
		
		// 便民日期
		f = new Field("D13_13_HBE_BM_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 8, "BMDate");
		addFieldConfig(f.getName(), f);
		// 便民流水
		f = new Field("D13_13_HBE_BM_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 30, "BMPbSerial");
		addFieldConfig(f.getName(), f);
		
		// 本次余额
		f = new Field("D13_13_HBE_THIS_BALANCE", VariableType.ASCII,
				LengthType.VARIABLE, 19, "thisBlalance");
		addFieldConfig(f.getName(), f);
		// 起止度数
		f = new Field("D13_13_HBE_SE_NUM", VariableType.ASCII,
				LengthType.VARIABLE, 31, "start_end_No");
		addFieldConfig(f.getName(), f);
		
		// 年阶梯累计电量
		f = new Field("D13_13_HBE_LEV_INCPQ", VariableType.ASCII,
				LengthType.VARIABLE, 18, "lev_incpq");
		addFieldConfig(f.getName(), f);
		
		
		
		
		
		//---------------------------------------------- 电信 start---------------------------------------------------------//
		
		// 查询上送--电话号码
		f = new Field("D13_11_PHONE_NO", VariableType.ASCII, LengthType.VARIABLE, 20, "phoneNo");
		addFieldConfig(f.getName(), f);

		// 查询上送--欠费金额
		f = new Field("D13_11_FEE", VariableType.ASCII, LengthType.VARIABLE, 10, "fee");
		addFieldConfig(f.getName(), f);

		// 查询上送--本期应缴
		f = new Field("D13_11_AMT3", VariableType.ASCII, LengthType.VARIABLE, 10, "amt3");
		addFieldConfig(f.getName(), f);

		// 查询上送--银行代码
		f = new Field("D13_11_BANK_NO", VariableType.ASCII, LengthType.VARIABLE, 2, "bankNo");
		addFieldConfig(f.getName(), f);

		// 查询上送--服务类型
		f = new Field("D13_11_SERV_TYPE", VariableType.ASCII, LengthType.VARIABLE, 1, "serviceType");
		addFieldConfig(f.getName(), f);
		
		// 查询返回--用户姓名
		f = new Field("D13_11_CUSTOM_NAME", VariableType.ASCII, LengthType.VARIABLE, 20, "customerName");
		addFieldConfig(f.getName(), f);
		
		
		// 缴费上送--缴费金额
		f = new Field("D13_11_AMOUNT1", VariableType.ASCII, LengthType.VARIABLE, 12, "amount");
		addFieldConfig(f.getName(), f);

		// 缴费上送--电信凭证号码
		f = new Field("D13_11_CERT_NO", VariableType.ASCII, LengthType.VARIABLE, 20, "certNo");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--平台流水号
		f = new Field("D13_11_TRAN_SEQNO", VariableType.ASCII, LengthType.VARIABLE, 20, "tranSeqNo");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--欠费起始截止年月
		f = new Field("D13_11_MONTH", VariableType.ASCII, LengthType.VARIABLE, 25, "month");
		addFieldConfig(f.getName(), f);

		// 缴费返回--计费合计
		f = new Field("D13_11_AMT4", VariableType.ASCII, LengthType.VARIABLE, 10, "amt4");
		addFieldConfig(f.getName(), f);

		// 缴费返回--上期结存
		f = new Field("D13_11_AMT1", VariableType.ASCII, LengthType.VARIABLE, 10, "amt1");
		addFieldConfig(f.getName(), f);

		// 缴费返回--未出帐费用
		f = new Field("D13_11_AMT2", VariableType.ASCII, LengthType.VARIABLE, 10, "amt2");
		addFieldConfig(f.getName(), f);

		// 缴费返回--本期结存
		f = new Field("D13_11_AMT5", VariableType.ASCII, LengthType.VARIABLE, 10, "amt2");
		addFieldConfig(f.getName(), f);

		// 缴费返回--当前结余
		f = new Field("D13_11_AMT6", VariableType.ASCII, LengthType.VARIABLE, 10, "amt6");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细1
		f = new Field("D13_11_MX1", VariableType.ASCII, LengthType.VARIABLE, 50, "mx1");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细2
		f = new Field("D13_11_MX2", VariableType.ASCII, LengthType.VARIABLE, 50, "mx2");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细3
		f = new Field("D13_11_MX3", VariableType.ASCII, LengthType.VARIABLE, 50, "mx3");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细4
		f = new Field("D13_11_MX4", VariableType.ASCII, LengthType.VARIABLE, 50, "mx4");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细5
		f = new Field("D13_11_MX5", VariableType.ASCII, LengthType.VARIABLE, 50, "mx5");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细6
		f = new Field("D13_11_MX6", VariableType.ASCII, LengthType.VARIABLE, 50, "mx6");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细7
		f = new Field("D13_11_MX7", VariableType.ASCII, LengthType.VARIABLE, 50, "mx7");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细8
		f = new Field("D13_11_MX8", VariableType.ASCII, LengthType.VARIABLE, 50, "mx8");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细9
		f = new Field("D13_11_MX9", VariableType.ASCII, LengthType.VARIABLE, 50, "mx9");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细10
		f = new Field("D13_11_MX10", VariableType.ASCII, LengthType.VARIABLE, 50, "mx10");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细11
		f = new Field("D13_11_MX11", VariableType.ASCII, LengthType.VARIABLE, 50, "mx11");
		addFieldConfig(f.getName(), f);

		// 取消上送--交易金额
		f = new Field("TRAN_AMT", VariableType.ASCII, LengthType.VARIABLE, 12, "tranAmt");
		addFieldConfig(f.getName(), f);

		//--------------------------------------------电信----------------------------------------------------------------------------
		
		//---------------------------------------------移动----------------------------------------------------------------------------
		
		// 查询上送--用户手机号
		f = new Field("D13_10_PHONE", VariableType.ASCII, LengthType.VARIABLE, 12, "phone");
		addFieldConfig(f.getName(), f);

		// 查询上送--查询标志,1—	号码;2—	帐号
		f = new Field("D13_10_PAY_TYPE", VariableType.ASCII, LengthType.VARIABLE, 1, "payType");
		addFieldConfig(f.getName(), f);
		

		// 查询返回--用户名称
		f = new Field("D13_10_NAME", VariableType.ASCII, LengthType.VARIABLE, 60, "userName");
		addFieldConfig(f.getName(), f);

		// 查询返回--当月话费
		f = new Field("D13_10_AMT", VariableType.ASCII, LengthType.VARIABLE, 12, "amt");
		addFieldConfig(f.getName(), f);

		// 查询返回--应收金额
		f = new Field("D13_10_FEE", VariableType.ASCII, LengthType.VARIABLE, 12, "fee");
		addFieldConfig(f.getName(), f);

		
		// 缴费上送--缴费金额
		f = new Field("D13_10_AMT1", VariableType.ASCII, LengthType.VARIABLE, 12, "amt1");
		addFieldConfig(f.getName(), f);

		// 缴费上送--话费欠费月份
		f = new Field("D13_10_CERT_NO", VariableType.ASCII, LengthType.VARIABLE, 20, "certNo");
		addFieldConfig(f.getName(), f);
		
		
		// 缴费返回--发票张数
		f = new Field("D13_10_NUM", VariableType.ASCII, LengthType.VARIABLE, 2, "num");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印时间
		f = new Field("D13_10_PRT_TIME", VariableType.ASCII, LengthType.VARIABLE, 6, "prtTime");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--客户品牌
		f = new Field("D13_10_CUSTBRAND", VariableType.ASCII, LengthType.VARIABLE, 20, "custBrand");
		addFieldConfig(f.getName(), f);

		// 缴费返回--平台流水号
		f = new Field("D13_10_TRAN_SEQNO", VariableType.ASCII, LengthType.VARIABLE, 10, "tranSeqNo");
		addFieldConfig(f.getName(), f);

		// 缴费返回-- 移动流水号
		f = new Field("D13_10_CMSEQNO", VariableType.ASCII, LengthType.VARIABLE, 20, "cmSeqNo");
		addFieldConfig(f.getName(), f);

		// 缴费返回--收款合计(发票用)
		f = new Field("D13_10_TOTAL_AMT", VariableType.ASCII, LengthType.VARIABLE, 12, "totalAmt");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印交易日期
		f = new Field("D13_10_PRT_DATA", VariableType.ASCII, LengthType.VARIABLE, 8, "prtData");
		addFieldConfig(f.getName(), f);

		// 缴费返回--打印明细1
		f = new Field("D13_10_PRT_MX1", VariableType.ASCII, LengthType.VARIABLE, 100, "mx1");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细2
		f = new Field("D13_10_PRT_MX2", VariableType.ASCII, LengthType.VARIABLE, 100, "mx2");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细3
		f = new Field("D13_10_PRT_MX3", VariableType.ASCII, LengthType.VARIABLE, 100, "mx3");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细4
		f = new Field("D13_10_PRT_MX4", VariableType.ASCII, LengthType.VARIABLE, 100, "mx4");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细5
		f = new Field("D13_10_PRT_MX5", VariableType.ASCII, LengthType.VARIABLE, 100, "mx5");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细6
		f = new Field("D13_10_PRT_MX6", VariableType.ASCII, LengthType.VARIABLE, 100, "mx6");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细7
		f = new Field("D13_10_PRT_MX7", VariableType.ASCII, LengthType.VARIABLE, 100, "mx7");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细8
		f = new Field("D13_10_PRT_MX8", VariableType.ASCII, LengthType.VARIABLE, 100, "mx8");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细9
		f = new Field("D13_10_PRT_MX9", VariableType.ASCII, LengthType.VARIABLE, 100, "mx9");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细10
		f = new Field("D13_10_PRT_MX10", VariableType.ASCII, LengthType.VARIABLE, 100, "mx10");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细11
		f = new Field("D13_10_PRT_MX11", VariableType.ASCII, LengthType.VARIABLE, 100, "mx11");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细12
		f = new Field("D13_10_PRT_MX12", VariableType.ASCII, LengthType.VARIABLE, 100, "mx12");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细13
		f = new Field("D13_10_PRT_MX13", VariableType.ASCII, LengthType.VARIABLE, 100, "mx13");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细14
		f = new Field("D13_10_PRT_MX14", VariableType.ASCII, LengthType.VARIABLE, 100, "mx14");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细15
		f = new Field("D13_10_PRT_MX15", VariableType.ASCII, LengthType.VARIABLE, 100, "mx15");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细16
		f = new Field("D13_10_PRT_MX16", VariableType.ASCII, LengthType.VARIABLE, 100, "mx16");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印明细17
		f = new Field("D13_10_PRT_MX17", VariableType.ASCII, LengthType.VARIABLE, 100, "mx17");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--计费期间
		f = new Field("D13_10_PRT_TRANDATA", VariableType.ASCII, LengthType.VARIABLE, 23, "prtTranData");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--备注1
		f = new Field("D13_10_PRT_REMARK1", VariableType.ASCII, LengthType.VARIABLE, 100, "remark1");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--备注2
		f = new Field("D13_10_PRT_REMARK2", VariableType.ASCII, LengthType.VARIABLE, 100, "remark2");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--备注3
		f = new Field("D13_10_PRT_REMARK3", VariableType.ASCII, LengthType.VARIABLE, 100, "remark3");
		addFieldConfig(f.getName(), f);
		
		
		// 缴费返回--
		f = new Field("D13_10_PRT_TOTALCONT", VariableType.ASCII, LengthType.VARIABLE, 200, "prtTotalCont");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--打印银行名称
		f = new Field("D13_10_PRT_BANKNAME", VariableType.ASCII, LengthType.VARIABLE, 60, "bankName");
		addFieldConfig(f.getName(), f);

		// 取消上送--原缴费金额
		f = new Field("TRAN_AMT", VariableType.ASCII, LengthType.VARIABLE, 12, "tranAmt");
		addFieldConfig(f.getName(), f);
		
		
		// 接入渠道日期
		f = new Field("CHANNEL_DATE", VariableType.ASCII,
				LengthType.FIXED, 0, 8, AlignType.RIGHTSPACE, "tranDate");
		addFieldConfig(f.getName(), f);

		// 接入渠道流水号
		f = new Field("CHANNEL_SEQNO", VariableType.ASCII,
				LengthType.FIXED, 0, 14, AlignType.RIGHTSPACE, "pbSeqno");
		addFieldConfig(f.getName(), f);
		
		//---------------------------------------------- 河电国标卡---------------------------------------------------------//
		
		// 缴费上送--购电权限
		f = new Field("D13_13_HEGB_OPP", VariableType.ASCII, LengthType.VARIABLE, 2, "HEGB_OPP");
		addFieldConfig(f.getName(), f);
		
		// 缴费上送--用户地址
		f = new Field("D13_13_HEGB_ADD", VariableType.ASCII, LengthType.VARIABLE, 80, "HEGB_ADD");
		addFieldConfig(f.getName(), f);

		// 缴费上送--用户编号
		f = new Field("D13_13_HEGB_NO", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_NO");
		addFieldConfig(f.getName(), f);
		
		// 查询返回--用户名称
		f = new Field("D13_13_HEGB_NAME", VariableType.ASCII, LengthType.VARIABLE, 60, "HEGB_NO");
		addFieldConfig(f.getName(), f);
		
		// 缴费上送--用户IC卡序列号
		f = new Field("D13_13_HEGB_ICNO", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_ICNO");
		addFieldConfig(f.getName(), f);

		// 缴费上送--电表识别号
		f = new Field("D13_13_HEGB_ID", VariableType.ASCII, LengthType.VARIABLE, 12, "HEGB_ID");
		addFieldConfig(f.getName(), f);

		// 缴费上送--用户卡卡片信息
		f = new Field("D13_13_HEGB_INFO", VariableType.ASCII, LengthType.VARIABLE, 256, "HEGB_INFO");
		addFieldConfig(f.getName(), f);

		// 缴费上送--电表出厂编号
		f = new Field("D13_13_HEGB_GWNO", VariableType.ASCII, LengthType.VARIABLE, 12, "HEGB_GWNO");
		addFieldConfig(f.getName(), f);

		// 缴费上送--随机数
		f = new Field("D13_13_HEGB_SJ", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_SJ");
		addFieldConfig(f.getName(), f);

		// 缴费上送--购电次数
		f = new Field("D13_13_HEGB_NUM", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_NUM");
		addFieldConfig(f.getName(), f);

		// 缴费上送--欠费金额
		f = new Field("D13_13_HEGB_AMT", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_AMT");
		addFieldConfig(f.getName(), f);

		// 缴费上送--电卡类型
		f = new Field("D13_13_HEGB_TYPE", VariableType.ASCII, LengthType.VARIABLE, 2, "HEGB_TYPE");
		addFieldConfig(f.getName(), f);

		// 缴费上送--购电值(金额)
		f = new Field("D13_13_HEGB_AMT2", VariableType.ASCII, LengthType.VARIABLE, 12, "HEGB_AMT2");
		addFieldConfig(f.getName(), f);

		// 缴费上送--电卡状态标志位
		f = new Field("D13_13_HEGB_UN", VariableType.ASCII, LengthType.VARIABLE, 2, "HEGB_UN");
		addFieldConfig(f.getName(), f);

		// 缴费上送--剩余金额
		f = new Field("D13_13_HEGB_FEE", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_FEE");
		addFieldConfig(f.getName(), f);

		// 缴费上送--预收金额
		f = new Field("D13_13_HEGB_PAY", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_PAY");
		addFieldConfig(f.getName(), f);

		// 缴费上送--核算单位编号
		f = new Field("D13_13_HEGB_CODE", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_CODE");
		addFieldConfig(f.getName(), f);

		// 缴费上送--低保户标志
		f = new Field("D13_13_HEGB_DBCODE", VariableType.ASCII, LengthType.VARIABLE, 2, "HEGB_DBCODE");
		addFieldConfig(f.getName(), f);

		// 缴费上送--低保户剩余金额
		f = new Field("D13_13_HEGB_DBPAY", VariableType.ASCII, LengthType.VARIABLE, 6, "HEGB_DBPAY");
		addFieldConfig(f.getName(), f);

		// 缴费上送--调整金额
		f = new Field("D13_13_HEGB_TZFEE", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_TZFEE");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--流水号
		f = new Field("D13_13_HEGB_SEQNO", VariableType.ASCII, LengthType.VARIABLE, 10, "HEGB_SEQNO");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--钱包文件的Mac值
		f = new Field("D13_13_HEGB_MAC1", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_MAC1");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--返写区文件的Mac值
		f = new Field("D13_13_HEGB_MAC2", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_MAC2");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--参数信息文件
		f = new Field("D13_13_HEGB_MAC3", VariableType.ASCII, LengthType.VARIABLE, 90, "HEGB_MAC3");
		addFieldConfig(f.getName(), f);

		// 缴费返回--参数信息文件Mac值
		f = new Field("D13_13_HEGB_MAC4", VariableType.ASCII, LengthType.VARIABLE, 8, "HEGB_MAC4");
		addFieldConfig(f.getName(), f);

		// 缴费返回--写卡数据
		f = new Field("D13_13_HEGB_PAG", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_PAG");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--国标是否阶梯
		f = new Field("D13_13_HEGB_JT", VariableType.ASCII, LengthType.VARIABLE, 2, "HEGB_JT");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回--国标本年一档用电量
		f = new Field("D13_13_HEGB_STAGE1", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_STAGE1");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回---国标本年二档用电量
		f = new Field("D13_13_HEGB_STAGE2", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_STAGE2");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回---国标本年三档用电量
		f = new Field("D13_13_HEGB_STAGE3", VariableType.ASCII, LengthType.VARIABLE, 16, "HEGB_STAGE3");
		addFieldConfig(f.getName(), f);
		
		// 缴费返回---国标第N档剩余电量
		f = new Field("D13_13_HEGB_STAGE4", VariableType.ASCII, LengthType.VARIABLE, 24, "HEGB_STAGE4");
		addFieldConfig(f.getName(), f);
		
		
		// 撤销返回--冲正新流水号
		f = new Field("D13_13_HEGB_NEWSEQ", VariableType.ASCII, LengthType.VARIABLE, 10, "HEGB_NEWSEQ");
		addFieldConfig(f.getName(), f);
		
		//---------------------------------------------- 末笔查询---------------------------------------------------------//
		//pos查询末笔状态
		f = new Field("DEAL_TRADE_STATE", VariableType.ASCII,LengthType.VARIABLE, 2,"deal_trade_state");
		addFieldConfig(f.getName(), f);
		
		//pos末笔交易日期
		f = new Field("ORAG_DEAL_DATE", VariableType.ASCII,LengthType.VARIABLE, 8,"orig_deal_date");
		addFieldConfig(f.getName(), f);
		
		//pos末笔交易时间
		f = new Field("ORIG_DEAL_TIME", VariableType.ASCII,LengthType.VARIABLE, 6,"orig_deal_time");
		addFieldConfig(f.getName(), f);
		
		//末笔pb交易流水
		f = new Field("ORIG_SYS_JOURNAL_SEQNO", VariableType.ASCII,LengthType.VARIABLE, 30, "orig_sys_journal_seqno");
		addFieldConfig(f.getName(), f);
		
		//---------邯郸燃气----------------
		//用户编号
		f = new Field("D13_13_HDG_USERCODE", VariableType.ASCII, LengthType.VARIABLE, 20, "user_code");
		addFieldConfig(f.getName(), f);
		
		//用户名称
		f = new Field("D13_13_HDG_USERNAME", VariableType.ASCII, LengthType.VARIABLE, 80, "user_name");
		addFieldConfig(f.getName(), f);
		
		//用户类型
		f = new Field("D13_13_HDG_TYPENO", VariableType.ASCII, LengthType.VARIABLE, 1, "user_type");
		addFieldConfig(f.getName(), f);
		
		//用户地址
		f = new Field("D13_13_HDG_USERADDR", VariableType.ASCII, LengthType.VARIABLE, 100, "user_addr");
		addFieldConfig(f.getName(), f);
		
		//上次结余
		f = new Field("D13_13_HDG_LASTAMT", VariableType.ASCII, LengthType.VARIABLE, 10, "last_amt");
		addFieldConfig(f.getName(), f);
		
		//记录总数
		f = new Field("D13_13_HDG_TOTALREC", VariableType.ASCII, LengthType.VARIABLE, 4, "total_rec");
		addFieldConfig(f.getName(), f);
		
		//第一笔缴费ID
		f = new Field("D13_13_HDG_CHARGEID_1", VariableType.ASCII, LengthType.VARIABLE, 20, "id_len20");
		addFieldConfig(f.getName(), f);
		
		//第一笔缴费金额
		f = new Field("D13_13_HDG_OUGHTAMT_1", VariableType.ASCII, LengthType.VARIABLE, 10, "first_amt");
		addFieldConfig(f.getName(), f);
		
		//应交合计
		f = new Field("D13_13_HDG_TOTALOUGHT", VariableType.ASCII, LengthType.VARIABLE, 10, "total_amt");
		addFieldConfig(f.getName(), f);
		
		// 重复记录数
		f = new Field("B13_RECORD_REC1", VariableType.ASCII,
				LengthType.FIXED, 1, 4, AlignType.RIGHTSPACE,
				"record_rec");
		addFieldConfig(f.getName(), f);
		//收费ID
		f = new Field("D13_13_HDG_CHARGEID", VariableType.ASCII, LengthType.VARIABLE, 20, "收费ID");
		addFieldConfig(f.getName(), f);
		//抄表期间
		f = new Field("D13_13_HDG_MONTH", VariableType.ASCII, LengthType.VARIABLE, 10, "抄表期间");
		addFieldConfig(f.getName(), f);
		//气量
		f = new Field("D13_13_HDG_GASCOUNT", VariableType.ASCII, LengthType.VARIABLE, 5, "气量");
		addFieldConfig(f.getName(), f);
		//气费合计
		f = new Field("D13_13_HDG_TOTALAMT", VariableType.ASCII, LengthType.VARIABLE, 10, "气费合计");
		addFieldConfig(f.getName(), f);
		//滞纳金
		f = new Field("D13_13_HDG_LATEAMT", VariableType.ASCII, LengthType.VARIABLE, 10, "滞纳金");
		addFieldConfig(f.getName(), f);
		//实缴金额
		f = new Field("D13_13_HDG_PAYAMT", VariableType.ASCII, LengthType.VARIABLE, 10, "实缴金额");
		addFieldConfig(f.getName(), f);
		//应缴金额
		f = new Field("D13_13_HDG_OUGHTAMT", VariableType.ASCII, LengthType.VARIABLE, 10, "应缴金额");
		addFieldConfig(f.getName(), f);
		
		//凭证号码
		f = new Field("D13_13_HDG_CERTNO", VariableType.ASCII, LengthType.VARIABLE, 20, "凭证号码");
		addFieldConfig(f.getName(), f);
		
		//本次结余
		f = new Field("D13_13_HDG_THISAMT", VariableType.ASCII, LengthType.VARIABLE, 10, "this_amt");
		addFieldConfig(f.getName(), f);
		
		//抄表期间
		f = new Field("D13_13_HDG_MONTH", VariableType.ASCII, LengthType.VARIABLE, 10, "hd_month");
		addFieldConfig(f.getName(), f);
		
		//----------------------------------有线电视-----------------------------------------
		//客户编号
		f = new Field("D13_16_CUSTOMERNO", VariableType.ASCII, LengthType.VARIABLE, 20, "客户编码");
		addFieldConfig(f.getName(), f);
		
		//账户编码
		f = new Field("D13_16_ACCNO",VariableType.ASCII,LengthType.VARIABLE, 20,"账户编码");
		addFieldConfig(f.getName(), f);
		
		//业务类型
		f = new Field("D13_16_SERVICETYPE",VariableType.ASCII,LengthType.VARIABLE, 2,"业务类型");
		addFieldConfig(f.getName(), f);
		
		//服务号码
		f = new Field("D13_16_SUBSCRIBERNO",VariableType.ASCII,LengthType.VARIABLE, 20,"服务号码");
		addFieldConfig(f.getName(), f);
		
		//客户名称
		f = new Field("D13_16_CUSTOMERNAME",VariableType.ASCII,LengthType.VARIABLE, 50,"客户名称");
		addFieldConfig(f.getName(), f);
		
		//余额账本编码
		f = new Field("D13_16_ACCBOOKNO",VariableType.ASCII,LengthType.VARIABLE, 20,"余额账本编码");
		addFieldConfig(f.getName(), f);
		
		//当前余额
		f = new Field("D13_16_CURAMT",VariableType.ASCII,LengthType.VARIABLE, 10,"当前余额");
		addFieldConfig(f.getName(), f);
		
		//实缴费用
		f = new Field("D13_16_RECFEE",VariableType.ASCII,LengthType.VARIABLE, 10,"实缴费用");
		addFieldConfig(f.getName(), f);
		
		//凭证号码
		f = new Field("D13_16_CERT_HEAD", VariableType.ASCII, LengthType.VARIABLE, 20, "凭证号码");
		addFieldConfig(f.getName(), f);
		
		//凭证号码
		f = new Field("D13_16_CERT_NO", VariableType.ASCII, LengthType.VARIABLE, 20, "凭证号码");
		addFieldConfig(f.getName(), f);
		
		//便民日期
		f = new Field("D13_16_BM_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 8, "PbSerial");
		addFieldConfig(f.getName(), f);
		
		//便民流水
		f = new Field("D13_16_BM_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 30, "BMTradeDate");
		addFieldConfig(f.getName(), f);
		
		//平台流水
		f = new Field("D13_16_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 18, "terminalSeq");
		addFieldConfig(f.getName(), f);
				
	
		
		
		//----------------------------------新奥燃气IC卡-----------------------------------
		
		//IC卡号
		f = new Field("D13_13_XAIC_ID", VariableType.ASCII, LengthType.VARIABLE, 20, "新奥IC卡号");
		addFieldConfig(f.getName(), f);
		
		//新奥备注信息
		f = new Field("D13_13_XAIC_BZ", VariableType.ASCII, LengthType.VARIABLE, 2, "新奥备注信息");
		addFieldConfig(f.getName(), f);
		
		//发卡次数
		f = new Field("D13_13_XAIC_NO", VariableType.ASCII, LengthType.VARIABLE, 2, "发卡次数");
		addFieldConfig(f.getName(), f);
		
		//客户预购气量
		f = new Field("D13_13_XAIC_BUY", VariableType.ASCII, LengthType.VARIABLE, 10, "客户预购气量");
		addFieldConfig(f.getName(), f);
		
		//加密串
		f = new Field("D13_13_XAIC_IFO", VariableType.ASCII, LengthType.VARIABLE, 128, "加密串");
		addFieldConfig(f.getName(),f);
		
		//用户姓名
		f = new Field("D13_13_XAIC_NAME", VariableType.ASCII, LengthType.VARIABLE, 60, "用户姓名");
		addFieldConfig(f.getName(), f);
		
		//用户地址
		f = new Field("D13_13_XAIC_ADD", VariableType.ASCII, LengthType.VARIABLE, 100, "用户地址");
		addFieldConfig(f.getName(), f);
		
		//最大购气量
		f = new Field("D13_13_XAIC_MAXGAS", VariableType.ASCII, LengthType.VARIABLE, 6, "最大购气量");
		addFieldConfig(f.getName(), f);
		
		//购气单价
		f = new Field("D13_13_XAIC_AMT1", VariableType.ASCII, LengthType.VARIABLE, 4, "购气单价");
		addFieldConfig(f.getName(), f);
		
		//帐户余额
		f = new Field("D13_13_XAIC_AMT", VariableType.ASCII, LengthType.VARIABLE, 10, "帐户余额");
		addFieldConfig(f.getName(), f);
		
		//购气金额
		f = new Field("D13_13_XAIC_COST", VariableType.ASCII, LengthType.VARIABLE, 10, "购气金额");
		addFieldConfig(f.getName(), f);
		
		//缴费金额
		f = new Field("D13_13_XAIC_PAY", VariableType.ASCII, LengthType.VARIABLE, 10, "缴费金额");
		addFieldConfig(f.getName(), f);
		
		//购气量
		f = new Field("D13_13_XAIC_GAS", VariableType.ASCII, LengthType.VARIABLE, 5, "购气量");
		addFieldConfig(f.getName(),f);
		
		//用户姓名
		f = new Field("D13_13_XAIC_NAME", VariableType.ASCII, LengthType.VARIABLE, 60, "用户姓名");
		addFieldConfig(f.getName(), f);
		
		//用户地址
		f = new Field("D13_13_XAIC_ADD", VariableType.ASCII, LengthType.VARIABLE, 100, "用户地址");
		addFieldConfig(f.getName(), f);
		
		//最大购气量
		f = new Field("D13_13_XAIC_MAXGAS", VariableType.ASCII, LengthType.VARIABLE, 6, "最大购气量");
		addFieldConfig(f.getName(), f);
		
		//便民日期
		f = new Field("D13_13_XAIC_BM_DATE", VariableType.ASCII,	LengthType.VARIABLE, 8, "便民日期");
		addFieldConfig(f.getName(), f);
		
		//便民流水
		f = new Field("D13_13_XAIC_BM_SEQNO", VariableType.ASCII,LengthType.VARIABLE, 14, "便民流水");
		addFieldConfig(f.getName(), f);
				
		//电商流水
		f = new Field("D13_13_XAIC_SEQNO", VariableType.ASCII,LengthType.VARIABLE, 20, "电商流水");
		addFieldConfig(f.getName(), f);		
		
		
		//add by fengyafang 张家口燃气
		
		//用户编号
		f = new Field("D13_13_ZJKG_USER_NO", VariableType.ASCII,
				LengthType.VARIABLE, 13, "用户编号");
		addFieldConfig(f.getName(), f);
		//收费月份上送‘000000’即可
		f = new Field("D13_13_ZJKG_FEE_MON", VariableType.ASCII,
				LengthType.VARIABLE, 6, "收费月份");
		addFieldConfig(f.getName(), f);
		
		
		// 用户名称
		f = new Field("D13_13_ZJKG_USER_NAME", VariableType.ASCII,
				LengthType.VARIABLE, 25, "用户名称");
		addFieldConfig(f.getName(), f);
		// 用户地址
		f = new Field("D13_13_ZJKG_USER_ADDR", VariableType.ASCII,
				LengthType.VARIABLE, 40, "用户地址");
		addFieldConfig(f.getName(), f);
		// 应缴金额
		f = new Field("D13_13_ZJKG_SUM_FEE", VariableType.ASCII,
				LengthType.VARIABLE, 16, "应缴金额");
		addFieldConfig(f.getName(), f);
		// 欠费月数
		f = new Field("D13_13_ZJKG_REC_NUM", VariableType.ASCII, LengthType.FIXED,1, 4,AlignType.RIGHTSPACE, "欠费月数");//FIXEDVARIABLE
		addFieldConfig(f.getName(), f); 
		
		 // 第三方流水号
		f = new Field("D13_13_ZJKG_SEQ_NO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "第三方流水号");
		addFieldConfig(f.getName(), f); 
			// 缴费金额
		f = new Field("D13_13_ZJKG_SUM_NUM", VariableType.ASCII,
				LengthType.VARIABLE, 10, "缴费金额");
		addFieldConfig(f.getName(), f); 
			// 上次余额
		f = new Field("D13_13_ZJKG_LAST_BAL", VariableType.ASCII,
				LengthType.VARIABLE, 12, "上次余额");
		addFieldConfig(f.getName(), f); 
			// 本次结存
		f = new Field("D13_13_ZJKG_CURR_BAL", VariableType.ASCII,
				LengthType.VARIABLE, 8, "本次结存");
		addFieldConfig(f.getName(), f); 
		
		// 实缴金额
		f = new Field("D13_13_ZJKG_PAY_AMT", VariableType.ASCII,
				LengthType.VARIABLE, 12, "实缴金额");
		addFieldConfig(f.getName(), f); 
				// 发票号码
		f = new Field("D13_13_ZJKG_CERT_NO", VariableType.ASCII,
				LengthType.VARIABLE, 20, "发票号码");
		addFieldConfig(f.getName(), f); 
				// 发票类型
		f = new Field("B05_VOUC_KIND", VariableType.ASCII,
				LengthType.VARIABLE, 4, "发票类型");
		addFieldConfig(f.getName(), f); 
 		// 接入渠道日期
		f = new Field("D13_13_ZJKG_CHANNEL_DATE", VariableType.ASCII,
				LengthType.VARIABLE, 8, "接入渠道日期");
		addFieldConfig(f.getName(), f); 
		// 接入渠道流水
		f = new Field("D13_13_ZJKG_CHANNEL_SEQNO", VariableType.ASCII,
				LengthType.VARIABLE, 14, "接入渠道流水");
		addFieldConfig(f.getName(), f); 
		//起码
		f = new Field("D13_13_ZJKG_STAR_DATA", VariableType.ASCII,
				LengthType.VARIABLE, 8, "起码");
		addFieldConfig(f.getName(), f); 
		//止码
		f = new Field("D13_13_ZJKG_END_DATA", VariableType.ASCII,
				LengthType.VARIABLE, 8, "止码");
		addFieldConfig(f.getName(), f); 
		//农电
		f=new Field("D13_13_HEND_CONS_NO",VariableType.ASCII,LengthType.VARIABLE,16,"客户编号");//客户编号
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CARD_NO",VariableType.ASCII,LengthType.VARIABLE,16,"电卡编号");//电卡编号
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PURP_FLAG",VariableType.ASCII,LengthType.VARIABLE,1,"业务标志位");//业务标志位
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CARD_INFO",VariableType.ASCII,LengthType.VARIABLE,48,"卡内信息");//卡内信息
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_IDDATA",VariableType.ASCII,LengthType.VARIABLE,256,"卡片信息");//卡片信息
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_READ_INFO",VariableType.ASCII,LengthType.VARIABLE,500,"读卡字符串");//读卡字符串
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_BM_SEQNO",VariableType.ASCII,LengthType.VARIABLE,14,"便民服务站流水");//便民服务站流水
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_BM_DATE",VariableType.ASCII,LengthType.VARIABLE,8,"便民服务站日期");//便民服务站日期
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_METER_FLAG",VariableType.ASCII,LengthType.VARIABLE,1,"电能表标识");//电能表标识
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_KEY_INFO",VariableType.ASCII,LengthType.VARIABLE,256,"密钥信息");//密钥信息
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CHECK_ID",VariableType.ASCII,LengthType.VARIABLE,24,"对账批次");//对账批次 
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CONS_NAME",VariableType.ASCII,LengthType.VARIABLE,16,"用户名称");//用户名称
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CONS_ADDR",VariableType.ASCII,LengthType.VARIABLE,60,"用电地址");//用电地址
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CONS_STATUS",VariableType.ASCII,LengthType.VARIABLE,1,"用户状态");//用户状态
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PAY_ORGNO",VariableType.ASCII,LengthType.VARIABLE,9,"核算单位");//核算单位
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_ORG_NO",VariableType.ASCII,LengthType.VARIABLE,16,"供电单位");//供电单位
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_METER_ID",VariableType.ASCII,LengthType.VARIABLE,16,"电能表编号");//电能表编号
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_METER_FAC",VariableType.ASCII,LengthType.VARIABLE,40,"电能表厂家名称");//电能表厂家名称
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_METER_MODEL",VariableType.ASCII,LengthType.VARIABLE,8,"电能表型号");//电能表型号
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_METER_TS",VariableType.ASCII,LengthType.VARIABLE,2,"电能表类别");//电能表类别
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_CHARGE_CLASS",VariableType.ASCII,LengthType.VARIABLE,2,"预付费类别");//预付费类别
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_FACTOR_VALUE",VariableType.ASCII,LengthType.VARIABLE,10,"综合倍率");//综合倍率
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PURP_PRICE",VariableType.ASCII,LengthType.VARIABLE,16,"购电电价");//购电电价
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PURP_MAX",VariableType.ASCII,LengthType.VARIABLE,16,"最大购电值");//最大购电值
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PURP_MIN",VariableType.ASCII,LengthType.VARIABLE,16,"最小购电值");//最小购电值
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_OWN_AMT",VariableType.ASCII,LengthType.VARIABLE,16,"欠费金额");//欠费金额
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PRE_AMT",VariableType.ASCII,LengthType.VARIABLE,16,"用户余额");//用户余额
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_PURP_TIMES",VariableType.ASCII,LengthType.VARIABLE,8,"用户上次购电次数");//用户上次购电次数
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_IF_METER",VariableType.ASCII,LengthType.VARIABLE,1,"是否插表（0未插表1已插表）");//是否插表（0未插表1已插表）
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_IF_PURP",VariableType.ASCII,LengthType.VARIABLE,1,"是否允许购电（0否1是）");//是否允许购电（0否1是）
		addFieldConfig(f.getName(),f);
		f=new Field("D13_13_HEND_NOALLOW_MSG",VariableType.ASCII,LengthType.VARIABLE,40,"不允许购电原因说明");//不允许购电原因说明 
		addFieldConfig(f.getName(),f); 
		f=new Field("D13_13_HEND_SERIAL_NUMBER",VariableType.ASCII,LengthType.VARIABLE,32,"外部售电系统售电流水号");//外部售电系统售电流水号
		addFieldConfig(f.getName(),f);
		//购电金额
		f=new Field("D13_13_HEND_PURP_VALUE",VariableType.ASCII,LengthType.VARIABLE,16,"购电金额"); 
		addFieldConfig(f.getName(),f);
		// 写卡金额20120820
		f=new Field("D13_13_HEND_WRITE_VALUE",VariableType.ASCII,LengthType.VARIABLE,16,"写卡金额"); 
		addFieldConfig(f.getName(),f);
		//D13_13_HEND_WRITE_INFO写卡字符串
		f=new Field("D13_13_HEND_WRITE_INFO",VariableType.ASCII,LengthType.VARIABLE,500,"写卡字符串"); 
		addFieldConfig(f.getName(),f);
		//D13_13_HEND_SEQNO 原交易流水号
		f=new Field("D13_13_HEND_SEQNO",VariableType.ASCII,LengthType.VARIABLE,10,"原交易流水号"); 
		addFieldConfig(f.getName(),f);
		//D13_13_HEND_PURP_AMT 冲正金额 16
		f=new Field("D13_13_HEND_PURP_AMT",VariableType.ASCII,LengthType.VARIABLE,16,"冲正金额"); 
		addFieldConfig(f.getName(),f);
		//add by fengyafang 20120830 业务类别
		f=new Field("D13_13_HEND_BUSI_TYPE",VariableType.ASCII,LengthType.VARIABLE,8,"业务类别");//02-售电，03-冲正，06-补写卡
		addFieldConfig(f.getName(),f);
		//费控方式
		f=new Field("D13_13_HEND_OCS_MODE",VariableType.ASCII,LengthType.VARIABLE,2,"费控方式"); 
		addFieldConfig(f.getName(),f);
		//预置值
		f=new Field("D13_13_HEND_PRESET_VALUE",VariableType.ASCII,LengthType.VARIABLE,16,"预置值"); 
		addFieldConfig(f.getName(),f);
		 	
		//add by mengqingwei 20121030 start 
		 //阶梯差价
		f=new Field("D13_13_HEND_LADDER_DIFF",VariableType.ASCII,LengthType.VARIABLE,16,"阶梯差价"); 
		addFieldConfig(f.getName(),f);
		
		 //本年累计用电量
		f=new Field("D13_13_HEND_ANNUAL_VALUE",VariableType.ASCII,LengthType.VARIABLE,16,"本年累计用电量"); 
		addFieldConfig(f.getName(),f);
		 //本档阶梯剩余电量
		f=new Field("D13_13_HEND_LADDER_SURPLUS",VariableType.ASCII,LengthType.VARIABLE,36,"本档阶梯剩余电量"); 
		addFieldConfig(f.getName(),f);
		//add by mengqingwei 20121030 end 
		
		//联通解包
		
		f=new Field("D13_12_UNC_PHONENUM",VariableType.ASCII,LengthType.VARIABLE,20,"用户号码");//用户号码
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_USERNAME",VariableType.ASCII,LengthType.VARIABLE,40,"用户姓名");//用户姓名
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_ACCNO",VariableType.ASCII,LengthType.VARIABLE,20,"局编账号");//局编账号
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_AREANO",VariableType.ASCII,LengthType.VARIABLE,10,"地市代码");//地市代码
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_SMONTH",VariableType.ASCII,LengthType.VARIABLE,6,"欠费起始年月");//欠费起始年月
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_SUMAMT",VariableType.ASCII,LengthType.VARIABLE,11,"往月欠费总额");//往月欠费总额
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_LASTAMT",VariableType.ASCII,LengthType.VARIABLE,11,"上次余额");//上次余额
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_THISAMT",VariableType.ASCII,LengthType.VARIABLE,11,"未出账话费");//未出账话费
		addFieldConfig(f.getName(),f);
		f=new Field("D13_12_UNC_LATEAMT",VariableType.ASCII,LengthType.VARIABLE,11,"滞纳金总额");//滞纳金总额
		addFieldConfig(f.getName(),f);
				f=new Field("D13_12_UNC_OUGHTAMT",VariableType.ASCII,LengthType.VARIABLE,11,"本期应缴");//本期应缴
		addFieldConfig(f.getName(),f);
		 
		//号码类型
		f = new Field("D13_12_UNC_PHONETYPE", VariableType.ASCII, LengthType.VARIABLE, 1, "号码类型");
		addFieldConfig(f.getName(), f);
		
	 
		
		//缴纳金额
		f = new Field("D13_12_UNC_PAYAMT", VariableType.ASCII, LengthType.VARIABLE, 11, "缴纳金额");
		addFieldConfig(f.getName(), f);
		
		//凭证号码
		f = new Field("D13_12_UNC_CERTNO", VariableType.ASCII, LengthType.VARIABLE, 20, "凭证号码");
		addFieldConfig(f.getName(), f);
		//缴费日期
		f = new Field("D13_12_UNC_CHANNEL_DATE", VariableType.ASCII, LengthType.VARIABLE, 8, "接入渠道日期");
		addFieldConfig(f.getName(), f);
		
		//流水号
		f = new Field("D13_12_UNC_CHANNEL_SEQNO", VariableType.ASCII, LengthType.VARIABLE, 20, "接入渠道流水号");
		addFieldConfig(f.getName(), f);
		
		
		//add by fengyafang 20120813 国标卡隔日补写卡日期
		
		f = new Field("D13_13_HEGB_DZ_DATE", VariableType.ASCII, LengthType.VARIABLE, 8, "补写卡日期");
		addFieldConfig(f.getName(), f);
		
	}

	private static FieldsConfig instance = null;

	public static FieldsConfig getInstance() {
		if (instance != null)
			return instance;
		instance = new TUXSTRINGFieldsConfig();
		return instance;
	}

	protected void addFieldConfig(String name, Field f) {
		fieldsMap.put(name, f);
	}
}
