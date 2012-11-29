package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmOpRoleId entity. @author MyEclipse Persistence Tools
 */

public class TbSmOpRoleId implements java.io.Serializable {

	// Fields

	private String operCode;
	private BigDecimal indentityId;

	// Constructors

	/** default constructor */
	public TbSmOpRoleId() {
	}

	/** full constructor */
	public TbSmOpRoleId(String operCode, BigDecimal indentityId) {
		this.operCode = operCode;
		this.indentityId = indentityId;
	}

	// Property accessors

	public String getOperCode() {
		return this.operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public BigDecimal getIndentityId() {
		return this.indentityId;
	}

	public void setIndentityId(BigDecimal indentityId) {
		this.indentityId = indentityId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmOpRoleId))
			return false;
		TbSmOpRoleId castOther = (TbSmOpRoleId) other;

		return ((this.getOperCode() == castOther.getOperCode()) || (this
				.getOperCode() != null
				&& castOther.getOperCode() != null && this.getOperCode()
				.equals(castOther.getOperCode())))
				&& ((this.getIndentityId() == castOther.getIndentityId()) || (this
						.getIndentityId() != null
						&& castOther.getIndentityId() != null && this
						.getIndentityId().equals(castOther.getIndentityId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOperCode() == null ? 0 : this.getOperCode().hashCode());
		result = 37
				* result
				+ (getIndentityId() == null ? 0 : this.getIndentityId()
						.hashCode());
		return result;
	}

}