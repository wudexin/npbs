package com.nantian.npbs.business.model;

/**
 * TbBiCheck entity. @author MyEclipse Persistence Tools
 */

public class TbBiCheck implements java.io.Serializable {

	// Fields

	private String pbSerial;
	private String radeDate;
	private String checkDate;
	private String companyCode;
	private String busiCode;
	private String systemCode;
	private String status;
	private String tradeType;
	private String posSerial;
	private String systemSerial;
	private String systemStatus;
	private String customerno;
	private String customername;
	private Double amount;
	private Double salary;
	private String cancelflag;
	private String remark;
	private String adjusted;

	// Constructors

	/** default constructor */
	public TbBiCheck() {
	}

	/** minimal constructor */
	public TbBiCheck(String pbSerial) {
		this.pbSerial = pbSerial;
	}

	/** full constructor */
	public TbBiCheck(String pbSerial, String radeDate, String checkDate,
			String companyCode, String busiCode, String systemCode,
			String status, String tradeType, String posSerial,
			String systemSerial, String systemStatus, String customerno,
			String customername, Double amount, Double salary,
			String cancelflag, String remark, String adjusted) {
		this.pbSerial = pbSerial;
		this.radeDate = radeDate;
		this.checkDate = checkDate;
		this.companyCode = companyCode;
		this.busiCode = busiCode;
		this.systemCode = systemCode;
		this.status = status;
		this.tradeType = tradeType;
		this.posSerial = posSerial;
		this.systemSerial = systemSerial;
		this.systemStatus = systemStatus;
		this.customerno = customerno;
		this.customername = customername;
		this.amount = amount;
		this.salary = salary;
		this.cancelflag = cancelflag;
		this.remark = remark;
		this.adjusted = adjusted;
	}

	// Property accessors

	public String getPbSerial() {
		return this.pbSerial;
	}

	public void setPbSerial(String pbSerial) {
		this.pbSerial = pbSerial;
	}

	public String getRadeDate() {
		return this.radeDate;
	}

	public void setRadeDate(String radeDate) {
		this.radeDate = radeDate;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPosSerial() {
		return this.posSerial;
	}

	public void setPosSerial(String posSerial) {
		this.posSerial = posSerial;
	}

	public String getSystemSerial() {
		return this.systemSerial;
	}

	public void setSystemSerial(String systemSerial) {
		this.systemSerial = systemSerial;
	}

	public String getSystemStatus() {
		return this.systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

	public String getCustomerno() {
		return this.customerno;
	}

	public void setCustomerno(String customerno) {
		this.customerno = customerno;
	}

	public String getCustomername() {
		return this.customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getCancelflag() {
		return this.cancelflag;
	}

	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdjusted() {
		return this.adjusted;
	}

	public void setAdjusted(String adjusted) {
		this.adjusted = adjusted;
	}

}