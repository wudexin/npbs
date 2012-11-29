package com.nantian.npbs.packet.internal;

public class ZJKRQ {

	private String USER_NO;// 用户编号
	private String FEE_MON;// 收费月份上送‘000000’即可
	private String USER_NAME;// 用户名称
	private String USER_ADDR;// 用户地址
	private String SUM_FEE;// 应缴金额
	private String REC_NUM;// 欠费月数
	private String PAY_AMT;// 实缴金额
	private String STAR_DATA;//起码
	private String END_DATA;//止码
	
	private String  SEQ_NO;   // 第三方流水号
	private String  SUM_NUM; //  缴费金额    
	private String  LAST_BAL; //  上次余额    
	private String  CURR_BAL; //   本次结存   
	 
	public String getSEQ_NO() {
		return SEQ_NO;
	}
	public void setSEQ_NO(String sEQNO) {
		SEQ_NO = sEQNO;
	}
	public String getSUM_NUM() {
		return SUM_NUM;
	}
	public void setSUM_NUM(String sUMNUM) {
		SUM_NUM = sUMNUM;
	}
	public String getLAST_BAL() {
		return LAST_BAL;
	}
	public void setLAST_BAL(String lASTBAL) {
		LAST_BAL = lASTBAL;
	}
	public String getCURR_BAL() {
		return CURR_BAL;
	}
	public void setCURR_BAL(String cURRBAL) {
		CURR_BAL = cURRBAL;
	}
	public String getUSER_NO() {
		return USER_NO;
	}
	public void setUSER_NO(String uSERNO) {
		USER_NO = uSERNO;
	}
	public String getFEE_MON() {
		return FEE_MON;
	}
	public void setFEE_MON(String fEEMON) {
		FEE_MON = fEEMON;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSERNAME) {
		USER_NAME = uSERNAME;
	}
	public String getUSER_ADDR() {
		return USER_ADDR;
	}
	public void setUSER_ADDR(String uSERADDR) {
		USER_ADDR = uSERADDR;
	}
	public String getSUM_FEE() {
		return SUM_FEE;
	}
	public void setSUM_FEE(String sUMFEE) {
		SUM_FEE = sUMFEE;
	}
	public String getREC_NUM() {
		return REC_NUM;
	}
	public void setREC_NUM(String rECNUM) {
		REC_NUM = rECNUM;
	}
	public String getPAY_AMT() {
		return PAY_AMT;
	}
	public void setPAY_AMT(String pAYAMT) {
		PAY_AMT = pAYAMT;
	}
	public String getSTAR_DATA() {
		return STAR_DATA;
	}
	public void setSTAR_DATA(String sTARDATA) {
		STAR_DATA = sTARDATA;
	}
	public String getEND_DATA() {
		return END_DATA;
	}
	public void setEND_DATA(String eNDDATA) {
		END_DATA = eNDDATA;
	}
}
