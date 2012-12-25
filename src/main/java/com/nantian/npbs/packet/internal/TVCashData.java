package com.nantian.npbs.packet.internal;
/**
 *有线电视收费
 * 
 * @author wzd
 *
 */

public class TVCashData {

	private String customerNo;  //客户编号
	
	private String  accNo;      //账户编号
	
	private String serviceType; //业务类型
	
	private String  subscriberNo;//服务号码
	
	private String customerName; //客户名称
	
	private String accBookNo;    //余额账本编码
	
	private String curAmt;      //当前余额
	
	private String  recFee;     //实缴费用
	
	private String certNo;     //凭证编码
	
		
	private String curPBDate;  //当前交易流水日期
	
	private String prePBDate;  //原交易流水日期
	
	private String curPBSerial; //当前便民交易流水号
	
	private String prePBSerial; //原便民交易流水号
	
	private String curSysSerial; //当前电子商务平台交易流水号

	private String preSysSerial; //原电子商务平台交易流水号

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccBookNo() {
		return accBookNo;
	}

	public void setAccBookNo(String accBookNo) {
		this.accBookNo = accBookNo;
	}

	public String getCurAmt() {
		return curAmt;
	}

	public void setCurAmt(String curAmt) {
		this.curAmt = curAmt;
	}

	public String getRecFee() {
		return recFee;
	}

	public void setRecFee(String recFee) {
		this.recFee = recFee;
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
