package com.nantian.npbs.business.model;

/**
 * TbBiPrepay entity. @author MyEclipse Persistence Tools
 */

public class TbBiPrepay implements java.io.Serializable {

	// Fields

	private String accno;
	private Double accBalance;
	private String accpwd;
	private String creditLvl;
	private Double creditAmt;
	private Double useCreamt;
	private Double surCreamt;
	private String arrearsDate;
	private String state;
	private String remark;
	private String pwdFlag;

	// Constructors

	/** default constructor */
	public TbBiPrepay() {
	}

	/** minimal constructor */
	public TbBiPrepay(String accno) {
		this.accno = accno;
	}

	/** full constructor */
	public TbBiPrepay(String accno, Double accBalance, String accpwd,
			String creditLvl, Double creditAmt, Double useCreamt,
			Double surCreamt, String arrearsDate, String state, String remark, String pwdFlag) {
		this.accno = accno;
		this.accBalance = accBalance;
		this.accpwd = accpwd;
		this.creditLvl = creditLvl;
		this.creditAmt = creditAmt;
		this.useCreamt = useCreamt;
		this.surCreamt = surCreamt;
		this.arrearsDate = arrearsDate;
		this.state = state;
		this.remark = remark;
		this.pwdFlag = pwdFlag;
	}

	// Property accessors

	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public Double getAccBalance() {
		return this.accBalance;
	}

	public void setAccBalance(Double accBalance) {
		this.accBalance = accBalance;
	}

	public String getAccpwd() {
		return this.accpwd;
	}

	public void setAccpwd(String accpwd) {
		this.accpwd = accpwd;
	}

	public String getCreditLvl() {
		return this.creditLvl;
	}

	public void setCreditLvl(String creditLvl) {
		this.creditLvl = creditLvl;
	}

	public Double getCreditAmt() {
		return this.creditAmt;
	}

	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}

	public Double getUseCreamt() {
		return this.useCreamt;
	}

	public void setUseCreamt(Double useCreamt) {
		this.useCreamt = useCreamt;
	}

	public Double getSurCreamt() {
		return this.surCreamt;
	}

	public void setSurCreamt(Double surCreamt) {
		this.surCreamt = surCreamt;
	}

	public String getArrearsDate() {
		return this.arrearsDate;
	}

	public void setArrearsDate(String arrearsDate) {
		this.arrearsDate = arrearsDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPwdFlag() {
		return this.pwdFlag;
	}

	public void setPwdFlag(String pwdFlag) {
		this.pwdFlag = pwdFlag;
	}

}