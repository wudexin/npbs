package com.nantian.npbs.business.model;

/**
 * TbBiBusiness entity. @author MyEclipse Persistence Tools
 */

public class TbBiBusiness implements java.io.Serializable {

	// Fields

	private String busiCode;
	private String busiName;
	private String systemCode;
	private Double defaultMax;
	private Double defaultMin;
	private Integer processmax;
	private Integer processnow;
	private String remark;

	// Constructors

	/** default constructor */
	public TbBiBusiness() {
	}

	/** minimal constructor */
	public TbBiBusiness(String busiCode) {
		this.busiCode = busiCode;
	}

	/** full constructor */
	public TbBiBusiness(String busiCode, String busiName, String systemCode,
			Double defaultMax, Double defaultMin, Integer processmax,
			Integer processnow, String remark) {
		this.busiCode = busiCode;
		this.busiName = busiName;
		this.systemCode = systemCode;
		this.defaultMax = defaultMax;
		this.defaultMin = defaultMin;
		this.processmax = processmax;
		this.processnow = processnow;
		this.remark = remark;
	}

	// Property accessors

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getBusiName() {
		return this.busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Double getDefaultMax() {
		return this.defaultMax;
	}

	public void setDefaultMax(Double defaultMax) {
		this.defaultMax = defaultMax;
	}

	public Double getDefaultMin() {
		return this.defaultMin;
	}

	public void setDefaultMin(Double defaultMin) {
		this.defaultMin = defaultMin;
	}

	public Integer getProcessmax() {
		return this.processmax;
	}

	public void setProcessmax(Integer processmax) {
		this.processmax = processmax;
	}

	public Integer getProcessnow() {
		return this.processnow;
	}

	public void setProcessnow(Integer processnow) {
		this.processnow = processnow;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}