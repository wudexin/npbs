package com.nantian.npbs.packet.internal;

/**
 * 移动话费
 * @author qiaoxl
 *
 */
public class MobileData {

	// 用户名称
	private String userName;
	
	// 电话号码
	private String phone;
	
	// 平台流水号
	private String tranSeqNo;
	
	// 移动流水号
	private String cmSeqNo;
	
	// 收款合计
	private String totalAmt;
	
	// 当月话费
	private String amt;
	
	// 应收金额
	private String fee;
	
	// 欠费月份
	private String certNo;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTranSeqNo() {
		return tranSeqNo;
	}

	public void setTranSeqNo(String tranSeqNo) {
		this.tranSeqNo = tranSeqNo;
	}

	public String getCmSeqNo() {
		return cmSeqNo;
	}

	public void setCmSeqNo(String cmSeqNo) {
		this.cmSeqNo = cmSeqNo;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	
}
