package com.nantian.npbs.packet.internal;

public class HeNDElecICCard {
	// 客户编号
	public String CONS_NO;
	// 电卡编号
	public String CARD_NO;
	// 业务标识位
	public String PURP_FLAG;
	// 卡内信息
	public String CARD_INFO;
	// 卡片信息
	public String IDDATA;
	// 读卡字符串
	public String READ_INFO;
	// 电能表标识
	public String METER_FLAG;
	// 密钥信息
	public String KEY_INFO;
	// 对账批次
	public String CHECK_ID;
	// 用户名称
	public String CONS_NAME;
	// 用电地址
	public String CONS_ADDR;
	// 电能表编号
	public String METER_ID;
	// 是否允许购电（0 否 1 是）
	public String IF_PURP;

	// 用户状态
	public String CONS_STATUS;
	// 核算单位
	public String PAY_ORGNO;
	// 供电单位
	public String ORG_NO;
	// 电能表厂家名称
	public String METER_FAC;
	// 电能表型号
	public String METER_MODEL;
	// 电能表类别
	public String METER_TS;
	// 预付费类别
	public String CHARGE_CLASS;
	// 综合倍率
	public String FACTOR_VALUE;
	// 购电电价
	public String PURP_PRICE;
	// 最大购电值
	public String PURP_MAX;
	// 最小购电值
	public String PURP_MIN;
	// 欠费金额
	public String OWN_AMT;
	// 用户余额
	public String PRE_AMT;
	// 用户上次购电次数
	public String PURP_TIMES;
	public String getBUSI_TYPE() {
		return BUSI_TYPE;
	}

	public void setBUSI_TYPE(String bUSITYPE) {
		BUSI_TYPE = bUSITYPE;
	}

	// 购电金额
	public String PURP_VALUE;
	//业务类别
	public String BUSI_TYPE;
	public String getWRITE_VALUE() {
		return WRITE_VALUE;
	}

	public void setWRITE_VALUE(String wRITEVALUE) {
		WRITE_VALUE = wRITEVALUE;
	}

	public String getWRITE_INFO() {
		return WRITE_INFO;
	}

	public void setWRITE_INFO(String wRITEINFO) {
		WRITE_INFO = wRITEINFO;
	}

	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}

	public void setSERIAL_NUMBER(String sERIALNUMBER) {
		SERIAL_NUMBER = sERIALNUMBER;
	}

	// 写卡金额
	public String WRITE_VALUE;
	// 写卡字符串
	public String WRITE_INFO;
	// 外部售电系统售电流水号
	public String SERIAL_NUMBER;
	
	
	private String OCS_MODE;//费控方式
	private String PRESET_VALUE;//预置值
	

	public String getPURP_VALUE() {
		return PURP_VALUE;
	}

	public void setPURP_VALUE(String pURPVALUE) {
		PURP_VALUE = pURPVALUE;
	}

	public String getCONS_STATUS() {
		return CONS_STATUS;
	}

	public void setCONS_STATUS(String cONSSTATUS) {
		CONS_STATUS = cONSSTATUS;
	}

	public String getPAY_ORGNO() {
		return PAY_ORGNO;
	}

	public void setPAY_ORGNO(String pAYORGNO) {
		PAY_ORGNO = pAYORGNO;
	}

	public String getORG_NO() {
		return ORG_NO;
	}

	public void setORG_NO(String oRGNO) {
		ORG_NO = oRGNO;
	}

	public String getMETER_FAC() {
		return METER_FAC;
	}

	public void setMETER_FAC(String mETERFAC) {
		METER_FAC = mETERFAC;
	}

	public String getMETER_MODEL() {
		return METER_MODEL;
	}

	public void setMETER_MODEL(String mETERMODEL) {
		METER_MODEL = mETERMODEL;
	}

	public String getMETER_TS() {
		return METER_TS;
	}

	public void setMETER_TS(String mETERTS) {
		METER_TS = mETERTS;
	}

	public String getCHARGE_CLASS() {
		return CHARGE_CLASS;
	}

	public void setCHARGE_CLASS(String cHARGECLASS) {
		CHARGE_CLASS = cHARGECLASS;
	}

	public String getFACTOR_VALUE() {
		return FACTOR_VALUE;
	}

	public void setFACTOR_VALUE(String fACTORVALUE) {
		FACTOR_VALUE = fACTORVALUE;
	}

	public String getPURP_PRICE() {
		return PURP_PRICE;
	}

	public void setPURP_PRICE(String pURPPRICE) {
		PURP_PRICE = pURPPRICE;
	}

	public String getPURP_MAX() {
		return PURP_MAX;
	}

	public void setPURP_MAX(String pURPMAX) {
		PURP_MAX = pURPMAX;
	}

	public String getPURP_MIN() {
		return PURP_MIN;
	}

	public void setPURP_MIN(String pURPMIN) {
		PURP_MIN = pURPMIN;
	}

	public String getOWN_AMT() {
		return OWN_AMT;
	}

	public void setOWN_AMT(String oWNAMT) {
		OWN_AMT = oWNAMT;
	}

	public String getPRE_AMT() {
		return PRE_AMT;
	}

	public void setPRE_AMT(String pREAMT) {
		PRE_AMT = pREAMT;
	}

	public String getPURP_TIMES() {
		return PURP_TIMES;
	}

	public void setPURP_TIMES(String pURPTIMES) {
		PURP_TIMES = pURPTIMES;
	}

	public String getIF_METER() {
		return IF_METER;
	}

	public void setIF_METER(String iFMETER) {
		IF_METER = iFMETER;
	}

	public String getNOALLOW_MSG() {
		return NOALLOW_MSG;
	}

	public void setNOALLOW_MSG(String nOALLOWMSG) {
		NOALLOW_MSG = nOALLOWMSG;
	}

	// 是否插表（0 未插表1 已插表）
	public String IF_METER;
	// 不允许购电原因说明
	public String NOALLOW_MSG;

	public String getCONS_NO() {
		return CONS_NO;
	}

	public void setCONS_NO(String cONSNO) {
		CONS_NO = cONSNO;
	}

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARDNO) {
		CARD_NO = cARDNO;
	}

	public String getPURP_FLAG() {
		return PURP_FLAG;
	}

	public void setPURP_FLAG(String pURPFLAG) {
		PURP_FLAG = pURPFLAG;
	}

	public String getCARD_INFO() {
		return CARD_INFO;
	}

	public void setCARD_INFO(String cARDINFO) {
		CARD_INFO = cARDINFO;
	}

	public String getIDDATA() {
		return IDDATA;
	}

	public void setIDDATA(String iDDATA) {
		IDDATA = iDDATA;
	}

	public String getREAD_INFO() {
		return READ_INFO;
	}

	public void setREAD_INFO(String rEADINFO) {
		READ_INFO = rEADINFO;
	}

	public String getMETER_FLAG() {
		return METER_FLAG;
	}

	public void setMETER_FLAG(String mETERFLAG) {
		METER_FLAG = mETERFLAG;
	}

	public String getKEY_INFO() {
		return KEY_INFO;
	}

	public void setKEY_INFO(String kEYINFO) {
		KEY_INFO = kEYINFO;
	}

	public String getCHECK_ID() {
		return CHECK_ID;
	}

	public void setCHECK_ID(String cHECKID) {
		CHECK_ID = cHECKID;
	}

	public String getCONS_NAME() {
		return CONS_NAME;
	}

	public void setCONS_NAME(String cONSNAME) {
		CONS_NAME = cONSNAME;
	}

	public String getCONS_ADDR() {
		return CONS_ADDR;
	}

	public void setCONS_ADDR(String cONSADDR) {
		CONS_ADDR = cONSADDR;
	}

	public String getMETER_ID() {
		return METER_ID;
	}

	public void setMETER_ID(String mETERID) {
		METER_ID = mETERID;
	}

	public String getIF_PURP() {
		return IF_PURP;
	}

	public void setIF_PURP(String iFPURP) {
		IF_PURP = iFPURP;
	}

	public String getOCS_MODE() {
		return OCS_MODE;
	}

	public String getPRESET_VALUE() {
		return PRESET_VALUE;
	}

	public void setOCS_MODE(String oCSMODE) {
		OCS_MODE = oCSMODE;
	}

	public void setPRESET_VALUE(String pRESETVALUE) {
		PRESET_VALUE = pRESETVALUE;
	}

}
