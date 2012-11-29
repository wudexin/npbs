package com.nantian.npbs.packet.internal;

/**
 * 华电IC卡
 * @author MDB
 *
 */
public class HuaElecICCard {

	// 用户编号
	private String userCode;
	
	// 用户名称
	private String userName;

	// 电表编号
	private String ammeterCode;
	
	// 用户地址
	private String address;
	
	// 账户余额
	private String accountBalance;
	
	// 购电次数
	private String buyElecNum;

	// 卡序列号
	private String cardSeqNo;
	
	// 卡分散数据
	private String cardMsg;
	
	// 随机数
	private String romNo;
	
	// 参数信息文件
	private String  paraType;
	
	
	// 认证数据一
	private String  authdata1;

	// 认证数据二
	private String  authdata2;
	
	// 认证数据三
	private String  authdata3;
	
	// 写卡数据
	private String writeParam;

	// 电力返回流水
	private String elecSeqNo ;
	
	// 平台流水
	private String seqNo;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAmmeterCode() {
		return ammeterCode;
	}

	public void setAmmeterCode(String ammeterCode) {
		this.ammeterCode = ammeterCode;
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

	public String getBuyElecNum() {
		return buyElecNum;
	}

	public void setBuyElecNum(String buyElecNum) {
		this.buyElecNum = buyElecNum;
	}

	public String getCardSeqNo() {
		return cardSeqNo;
	}

	public void setCardSeqNo(String cardSeqNo) {
		this.cardSeqNo = cardSeqNo;
	}

	public String getCardMsg() {
		return cardMsg;
	}

	public void setCardMsg(String cardMsg) {
		this.cardMsg = cardMsg;
	}

	public String getRomNo() {
		return romNo;
	}

	public void setRomNo(String romNo) {
		this.romNo = romNo;
	}

	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}

	public String getAuthdata1() {
		return authdata1;
	}

	public void setAuthdata1(String authdata1) {
		this.authdata1 = authdata1;
	}

	public String getAuthdata2() {
		return authdata2;
	}

	public void setAuthdata2(String authdata2) {
		this.authdata2 = authdata2;
	}

	public String getAuthdata3() {
		return authdata3;
	}

	public void setAuthdata3(String authdata3) {
		this.authdata3 = authdata3;
	}

	public String getWriteParam() {
		return writeParam;
	}

	public void setWriteParam(String writeParam) {
		this.writeParam = writeParam;
	}

	public String getElecSeqNo() {
		return elecSeqNo;
	}

	public void setElecSeqNo(String elecSeqNo) {
		this.elecSeqNo = elecSeqNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

}
