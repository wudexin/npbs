package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbBiSallaryPackageFormulaId entity. @author MyEclipse Persistence Tools
 */

public class TbBiSallaryPackageFormulaId implements java.io.Serializable {

	// Fields

	private BigDecimal packageId;
	private BigDecimal formulaId;
	private String busiCode;

	// Constructors

	/** default constructor */
	public TbBiSallaryPackageFormulaId() {
	}

	/** minimal constructor */
	public TbBiSallaryPackageFormulaId(BigDecimal packageId,
			BigDecimal formulaId) {
		this.packageId = packageId;
		this.formulaId = formulaId;
	}

	/** full constructor */
	public TbBiSallaryPackageFormulaId(BigDecimal packageId,
			BigDecimal formulaId, String busiCode) {
		this.packageId = packageId;
		this.formulaId = formulaId;
		this.busiCode = busiCode;
	}

	// Property accessors

	public BigDecimal getPackageId() {
		return this.packageId;
	}

	public void setPackageId(BigDecimal packageId) {
		this.packageId = packageId;
	}

	public BigDecimal getFormulaId() {
		return this.formulaId;
	}

	public void setFormulaId(BigDecimal formulaId) {
		this.formulaId = formulaId;
	}

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiSallaryPackageFormulaId))
			return false;
		TbBiSallaryPackageFormulaId castOther = (TbBiSallaryPackageFormulaId) other;

		return ((this.getPackageId() == castOther.getPackageId()) || (this
				.getPackageId() != null
				&& castOther.getPackageId() != null && this.getPackageId()
				.equals(castOther.getPackageId())))
				&& ((this.getFormulaId() == castOther.getFormulaId()) || (this
						.getFormulaId() != null
						&& castOther.getFormulaId() != null && this
						.getFormulaId().equals(castOther.getFormulaId())))
				&& ((this.getBusiCode() == castOther.getBusiCode()) || (this
						.getBusiCode() != null
						&& castOther.getBusiCode() != null && this
						.getBusiCode().equals(castOther.getBusiCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPackageId() == null ? 0 : this.getPackageId().hashCode());
		result = 37 * result
				+ (getFormulaId() == null ? 0 : this.getFormulaId().hashCode());
		result = 37 * result
				+ (getBusiCode() == null ? 0 : this.getBusiCode().hashCode());
		return result;
	}

}