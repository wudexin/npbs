package com.nantian.npbs.business.model;

/**
 * TbBiTrade entity. @author MyEclipse Persistence Tools
 */

public class TbBiTrade implements java.io.Serializable {

	// Fields

	private TbBiTradeId id;
	private String companyCode;
	private String busiCode;
	private String systemCode;
	/**交易状态
	 * 00——交易成功，01——交易失败，02——原交易不存在，03——交易被取消，04-交易被冲正，05-交易由失败状态改为成功状态，06-写卡失败,
	 * 99——初始状态，交易未确定
	 */

	private String status;
	/**
	 * 交易类型 POS业务缴费：01-缴费交易；02-取消交易；03-写卡；04-冲正交易05-写卡成功确认，06-写卡失败确认 07-查询，08-管理
	 * 电子商务平台续费：10-正常，11-撤销，12-退费
	 */
	private String tradeType;

	private String posSerial;
	private String systemDate;
	private String systemSerial;
	private String customerno;
	private String customername;
	private Double amount;
	private Double salary;
	private String cancelflag;
	private Double tax;
	private Double depreciation;
	private Double other;
	private String remark;
	private String localDate;
	private String tradeTime;
	private String payType;
	private String accno;

	// Constructors

	/** default constructor */
	public TbBiTrade() {
	}

	/** minimal constructor */
	public TbBiTrade(TbBiTradeId id, String tradeType) {
		this.id = id;
		this.tradeType = tradeType;
	}

	/** full constructor */
	public TbBiTrade(TbBiTradeId id, String companyCode, String busiCode,
			String systemCode, String status, String tradeType,
			String posSerial, String systemDate, String systemSerial,
			String customerno, String customername, Double amount,
			Double salary, String cancelflag, Double tax, Double depreciation,
			Double other, String remark, String localDate, String tradeTime,
			String payType, String accno) {
		this.id = id;
		this.companyCode = companyCode;
		this.busiCode = busiCode;
		this.systemCode = systemCode;
		this.status = status;
		this.tradeType = tradeType;
		this.posSerial = posSerial;
		this.systemDate = systemDate;
		this.systemSerial = systemSerial;
		this.customerno = customerno;
		this.customername = customername;
		this.amount = amount;
		this.salary = salary;
		this.cancelflag = cancelflag;
		this.tax = tax;
		this.depreciation = depreciation;
		this.other = other;
		this.remark = remark;
		this.localDate = localDate;
		this.tradeTime = tradeTime;
		this.payType = payType;
		this.accno = accno;
	}

	// Property accessors

	public TbBiTradeId getId() {
		return this.id;
	}

	public void setId(TbBiTradeId id) {
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

	public String getSystemDate() {
		return this.systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
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

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getDepreciation() {
		return this.depreciation;
	}

	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

	public Double getOther() {
		return this.other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocalDate() {
		return this.localDate;
	}

	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}

	public String getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

}