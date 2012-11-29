package com.nantian.npbs.packet.internal;

/**
 * 华电现金
 * @author MDB
 *
 */
public class HuaElecCash {

	/*
	 * 用户编号
	 * */
	private String userCode;
	/*
	 *  用户名称
	 * */
	private String userName;
	/*
	 * 用户地址 
	 * */
	private String address;
	/*
	 * 供电单位编号
	 * */
	private String orgNo;
	/*
	 * 记录条数
	 * */
	private String recordNo;
	/*
	 * 记录明细
	 * */
	private String record;
	/*
	 * 缴费金额
	 * */
	private String amount;
	/*
	 * 账户余额
	 * */
	private Double accBalance;
	/*
	 * 电商平台流水
	 * */
	private String sysSerial;
	
	
	//add Start MDB 2012年1月12日 17:47:33
	//本次余额
	private String thisBalance;
	
	//起止示数
	private String seNum;
	
	//是否显示“该用户为预交、多月...”
	private Boolean isShowDetail;
	
	//add start wzd 2012年5月23日8:38:44
	//阶梯欠费
	private String levAmt;
	
	//年阶梯累计电量
	private String levIncpq;
	
	//add end wzd 2012年5月23日8:39:01
	public String getLevAmt() {
		return levAmt;
	}
	public String getLevIncpq() {
		return levIncpq;
	}
	
	public void setLevIncpq(String levIncpq) {
		this.levIncpq = levIncpq;
	}
	public void setLevAmt(String levAmt) {
		this.levAmt = levAmt;
	}
	
	
	/**本次余额*/
	public String getThisBalance() {
		return thisBalance;
	}
	/**本次余额*/
	public void setThisBalance(String thisBalance) {
		this.thisBalance = thisBalance;
	}
	
	/**起止示数*/
	public String getSeNum() {
		return seNum;
	}
	/**起止示数*/
	public void setSeNum(String seNum) {
		this.seNum = seNum;
	}
	
	/**小票信息是否显示“该用户为预交、多月...”
	 * @return True显示 False不显示*/
	public Boolean getIsShowDetail() {
		return isShowDetail;
	}
	/**小票信息是否显示“该用户为预交、多月...”
	 * @param isShowDetail True显示 False不显示*/
	public void setIsShowDetail(Boolean isShowDetail) {
		this.isShowDetail = isShowDetail;
	}
//add End MDB 2012年1月12日 17:47:33
	
	public Double getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(Double accBalance) {
		this.accBalance = accBalance;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setSysSerial(String sysSerial) {
		this.sysSerial = sysSerial;
	}
	public String getSysSerial() {
		return sysSerial;
	}
	
}
