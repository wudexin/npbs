package com.nantian.npbs.packet.internal;

/**
 * 新奥燃气现金代收
 * 
 * @author MDB
 */
public class XAGasCashData {

	private String userName;			// 用户名称
	
	private String userCode;			// 用户编号
	
	private String userAdd;				// 用电地址
	
	private String fkcs;				// 发卡次数
	
	private String accBalance;			// 账户余额
	
	private String amount;				// 充值金额
	
	private String newBalance;			// 最新余额
	

	private String curPBDate;			// 当前交易流水日期
	
	private String prePBDate;			// 原交易流水日期
	
	private String curPBSerial;			// 当前交易流水号
	
	private String prePBSerial;			// 原交易流水号
	
	private String curSysSerial;		// 当前交易电商平台流水号
	
	private String preSysSerial;		// 原交易电商平台流水号
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserAdd() {
		return userAdd;
	}

	public void setUserAdd(String userAdd) {
		this.userAdd = userAdd;
	}

	public String getFkcs() {
		return fkcs;
	}

	public void setFkcs(String fkcs) {
		this.fkcs = fkcs;
	}

	public String getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(String accBalance) {
		this.accBalance = accBalance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(String newBalance) {
		this.newBalance = newBalance;
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

	public String getPreSysSerial() {
		return preSysSerial;
	}

	public void setPreSysSerial(String preSysSerial) {
		this.preSysSerial = preSysSerial;
	}

	public String getCurSysSerial() {
		return curSysSerial;
	}

	public void setCurSysSerial(String curSysSerial) {
		this.curSysSerial = curSysSerial;
	}
	
}
