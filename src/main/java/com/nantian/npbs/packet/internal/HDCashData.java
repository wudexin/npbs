package com.nantian.npbs.packet.internal;

/**
 * 邯郸燃气
 * 
 * @author jxw
 * 
 */
public class HDCashData {

	private String userCode;			// 用户编号

	private String userName;		// 用户名称
	
	private String userType;		// 用户类型
	
	private String userAddr;		// 用户地址

	private String lastAmt;		//上次结余
	
	private String totalRec;		//记录总数
	
	private String chargeId_1;			//第一笔缴费ID
	
	private String oughtAmt_1;			//第一笔应缴金额
	
	private String totalought;			//应交合计
	
	private String recordRec;			//重复记录数
	
	private String[] chargeId;		//收费ID
	
	private String[] month;		//抄表期间
	
	private String[] gasCount;			//气量
	
	private String[] totalAmt;			//气费合计
	
	private String[] lateAmt;			//滞纳金
	
	private String[] oughtAmt;			//应缴金额
	
	private String thisAmt;			//本次结余
	
	private String hd_month;			//抄表期间

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getLastAmt() {
		return lastAmt;
	}

	public void setLastAmt(String lastAmt) {
		this.lastAmt = lastAmt;
	}

	public String getTotalRec() {
		return totalRec;
	}

	public void setTotalRec(String totalRec) {
		this.totalRec = totalRec;
	}

	public String getChargeId_1() {
		return chargeId_1;
	}

	public void setChargeId_1(String chargeId_1) {
		this.chargeId_1 = chargeId_1;
	}

	public String getOughtAmt_1() {
		return oughtAmt_1;
	}

	public void setOughtAmt_1(String oughtAmt_1) {
		this.oughtAmt_1 = oughtAmt_1;
	}

	public String getTotalought() {
		return totalought;
	}

	public void setTotalought(String totalought) {
		this.totalought = totalought;
	}

	public String getRecordRec() {
		return recordRec;
	}

	public void setRecordRec(String recordRec) {
		this.recordRec = recordRec;
	}

	public String[] getChargeId() {
		return chargeId;
	}

	public void setChargeId(String[] chargeId) {
		this.chargeId = chargeId;
	}

	public String[] getMonth() {
		return month;
	}

	public void setMonth(String[] month) {
		this.month = month;
	}

	public String[] getGasCount() {
		return gasCount;
	}

	public void setGasCount(String[] gasCount) {
		this.gasCount = gasCount;
	}

	public String[] getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String[] totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String[] getLateAmt() {
		return lateAmt;
	}

	public void setLateAmt(String[] lateAmt) {
		this.lateAmt = lateAmt;
	}

	public String[] getOughtAmt() {
		return oughtAmt;
	}

	public void setOughtAmt(String[] oughtAmt) {
		this.oughtAmt = oughtAmt;
	}

	public String getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(String thisAmt) {
		this.thisAmt = thisAmt;
	}

	public String getHd_month() {
		return hd_month;
	}

	public void setHd_month(String hdMonth) {
		hd_month = hdMonth;
	}
	
}
