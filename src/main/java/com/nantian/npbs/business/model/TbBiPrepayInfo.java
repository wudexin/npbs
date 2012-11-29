package com.nantian.npbs.business.model;

/**
 * TbBiPrepayInfo entity. @author MyEclipse Persistence Tools
 */

public class TbBiPrepayInfo implements java.io.Serializable {

	// Fields

	private TbBiPrepayInfoId id;
	private String accno;
	private String companyCode;
	private String busiCode;
	private String systemCode;
	private String status;
	private String posSerial;
	private String systemSerial;
	private String customerno;
	private String customername;
	private String flag;
	

	private Double amount;
	private Double bal;
	private String summary;
	private String remark;
	private String tradeTime;

	// Constructors

	/** default constructor */
	public TbBiPrepayInfo() {
	}

	/** minimal constructor */
	public TbBiPrepayInfo(TbBiPrepayInfoId id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiPrepayInfo(TbBiPrepayInfoId id, String companyCode,
			String busiCode, String systemCode, String posSerial,
			String systemSerial, String customerno, String customername,
			String flag, Double amount, Double bal, String summary,
			String remark, String tradeTime) {
		this.id = id;
		this.companyCode = companyCode;
		this.busiCode = busiCode;
		this.systemCode = systemCode;
		this.posSerial = posSerial;
		this.systemSerial = systemSerial;
		this.customerno = customerno;
		this.customername = customername;
		this.flag = flag;
		this.amount = amount;
		this.bal = bal;
		this.summary = summary;
		this.remark = remark;
		this.tradeTime = tradeTime;
	}

	// Property accessors

	public TbBiPrepayInfoId getId() {
		return this.id;
	}

	public void setId(TbBiPrepayInfoId id) {
		this.id = id;
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

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBal() {
		return this.bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}