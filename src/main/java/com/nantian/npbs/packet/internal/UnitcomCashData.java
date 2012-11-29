package com.nantian.npbs.packet.internal;

/**
 * 现金代收新联通
 * 
 * @author wzd
 *
 */

public class UnitcomCashData {
	
	//用户号码
	private String phoneNum;
	
	//号码类型：
	private String phoneType;
	
	//用户姓名
	private String userName;
	
	//局扁账号
	private String accno;
	
	//地市代码
	private String areano;
	
	//欠费起始年月
	private String sMonth;
	
	//往月欠费总额
	private String sumAmt;
	
	//上次余额
	private String lastAmt;
	
	//未出账话费
	private String thisAmt;
	
	//滞纳金总额
	private String lateAmt;
	
	//本期应缴
	private String oughtAmt;
	
	//缴费金额
	private String payAmt;
	
	//凭证号码
	private String certNo;
	
	//当前交易流水日期
	private String curPBDate;  
	
	//原交易流水日期
	private String prePBDate;  
	
	//当前便民交易流水号
	private String curPBSerial; 
	
	//原便民交易流水号
	private String prePBSerial; 
	
	//当前电子商务平台交易流水号
	private String curSysSerial; 

	//原电子商务平台交易流水号
	private String preSysSerial;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getAreano() {
		return areano;
	}

	public void setAreano(String areano) {
		this.areano = areano;
	}

	public String getsMonth() {
		return sMonth;
	}

	public void setsMonth(String sMonth) {
		this.sMonth = sMonth;
	}

	public String getSumAmt() {
		return sumAmt;
	}

	public void setSumAmt(String sumAmt) {
		this.sumAmt = sumAmt;
	}

	public String getLastAmt() {
		return lastAmt;
	}

	public void setLastAmt(String lastAmt) {
		this.lastAmt = lastAmt;
	}

	public String getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(String thisAmt) {
		this.thisAmt = thisAmt;
	}

	public String getLateAmt() {
		return lateAmt;
	}

	public void setLateAmt(String lateAmt) {
		this.lateAmt = lateAmt;
	}

	public String getOughtAmt() {
		return oughtAmt;
	}

	public void setOughtAmt(String oughtAmt) {
		this.oughtAmt = oughtAmt;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCurPBDate() {
		return curPBDate;
	}

	public void setCurPBDate(String curPBDate) {
		this.curPBDate = curPBDate;
	}

	public String getPrePBDate() {
		return prePBDate;
	}

	public void setPrePBDate(String prePBDate) {
		this.prePBDate = prePBDate;
	}

	public String getCurPBSerial() {
		return curPBSerial;
	}

	public void setCurPBSerial(String curPBSerial) {
		this.curPBSerial = curPBSerial;
	}

	public String getPrePBSerial() {
		return prePBSerial;
	}

	public void setPrePBSerial(String prePBSerial) {
		this.prePBSerial = prePBSerial;
	}

	public String getCurSysSerial() {
		return curSysSerial;
	}

	public void setCurSysSerial(String curSysSerial) {
		this.curSysSerial = curSysSerial;
	}

	public String getPreSysSerial() {
		return preSysSerial;
	}

	public void setPreSysSerial(String preSysSerial) {
		this.preSysSerial = preSysSerial;
	}

}
