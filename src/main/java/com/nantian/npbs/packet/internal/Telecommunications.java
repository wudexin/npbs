package com.nantian.npbs.packet.internal;

/**
 * 电信
 * @author qiaoxl
 *
 */
public class Telecommunications {

	// 用户姓名
	private String userName;
	
	// 欠费金额
	private String fee;
	
	// 本期应缴
	private String amt3;
	
	// 电话号码
	private String phoneNo;
	
	// 平台流水
	private String tranSeqNo;
	
	// 缴费金额
	private String amount;

	//本期结存
	private String amt5;
	
	//当前结余
	private String amt6;
	
	// 银行代码
	private String bankNo;
	
	// 银服务类型
	private String serviceType;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getAmt3() {
		return amt3;
	}

	public void setAmt3(String amt3) {
		this.amt3 = amt3;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getTranSeqNo() {
		return tranSeqNo;
	}

	public void setTranSeqNo(String tranSeqNo) {
		this.tranSeqNo = tranSeqNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmt5() {
		return amt5;
	}

	public void setAmt5(String amt5) {
		this.amt5 = amt5;
	}

	public String getAmt6() {
		return amt6;
	}

	public void setAmt6(String amt6) {
		this.amt6 = amt6;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
