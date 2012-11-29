package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbBiFormulaDetailId entity. @author MyEclipse Persistence Tools
 */

public class TbBiFormulaDetailId implements java.io.Serializable {

	// Fields

	private BigDecimal formulaId;
	private Short formulaSerial;

	// Constructors

	/** default constructor */
	public TbBiFormulaDetailId() {
	}

	/** full constructor */
	public TbBiFormulaDetailId(BigDecimal formulaId, Short formulaSerial) {
		this.formulaId = formulaId;
		this.formulaSerial = formulaSerial;
	}

	// Property accessors

	public BigDecimal getFormulaId() {
		return this.formulaId;
	}

	public void setFormulaId(BigDecimal formulaId) {
		this.formulaId = formulaId;
	}

	public Short getFormulaSerial() {
		return this.formulaSerial;
	}

	public void setFormulaSerial(Short formulaSerial) {
		this.formulaSerial = formulaSerial;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiFormulaDetailId))
			return false;
		TbBiFormulaDetailId castOther = (TbBiFormulaDetailId) other;

		return ((this.getFormulaId() == castOther.getFormulaId()) || (this
				.getFormulaId() != null
				&& castOther.getFormulaId() != null && this.getFormulaId()
				.equals(castOther.getFormulaId())))
				&& ((this.getFormulaSerial() == castOther.getFormulaSerial()) || (this
						.getFormulaSerial() != null
						&& castOther.getFormulaSerial() != null && this
						.getFormulaSerial()
						.equals(castOther.getFormulaSerial())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFormulaId() == null ? 0 : this.getFormulaId().hashCode());
		result = 37
				* result
				+ (getFormulaSerial() == null ? 0 : this.getFormulaSerial()
						.hashCode());
		return result;
	}

}