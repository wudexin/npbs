package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbBiSallaryPackage entity. @author MyEclipse Persistence Tools
 */

public class TbBiSallaryPackage implements java.io.Serializable {

	// Fields

	private BigDecimal packageId;
	private String unitcode;
	private String packageName;
	private String channelCode;
	private Double depreciation;
	private Double tax;
	private Double other;

	// Constructors

	/** default constructor */
	public TbBiSallaryPackage() {
	}

	/** minimal constructor */
	public TbBiSallaryPackage(BigDecimal packageId, String unitcode,
			String channelCode) {
		this.packageId = packageId;
		this.unitcode = unitcode;
		this.channelCode = channelCode;
	}

	/** full constructor */
	public TbBiSallaryPackage(BigDecimal packageId, String unitcode,
			String packageName, String channelCode, Double depreciation,
			Double tax, Double other) {
		this.packageId = packageId;
		this.unitcode = unitcode;
		this.packageName = packageName;
		this.channelCode = channelCode;
		this.depreciation = depreciation;
		this.tax = tax;
		this.other = other;
	}

	// Property accessors

	public BigDecimal getPackageId() {
		return this.packageId;
	}

	public void setPackageId(BigDecimal packageId) {
		this.packageId = packageId;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Double getDepreciation() {
		return this.depreciation;
	}

	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getOther() {
		return this.other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

}