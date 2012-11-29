package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmRolePowerId entity. @author MyEclipse Persistence Tools
 */

public class TbSmRolePowerId implements java.io.Serializable {

	// Fields

	private TbSmOperpower tbSmOperpower;
	private BigDecimal indentityId;

	// Constructors

	/** default constructor */
	public TbSmRolePowerId() {
	}

	/** full constructor */
	public TbSmRolePowerId(TbSmOperpower tbSmOperpower, BigDecimal indentityId) {
		this.tbSmOperpower = tbSmOperpower;
		this.indentityId = indentityId;
	}

	// Property accessors

	public TbSmOperpower getTbSmOperpower() {
		return this.tbSmOperpower;
	}

	public void setTbSmOperpower(TbSmOperpower tbSmOperpower) {
		this.tbSmOperpower = tbSmOperpower;
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
		if (!(other instanceof TbSmRolePowerId))
			return false;
		TbSmRolePowerId castOther = (TbSmRolePowerId) other;

		return ((this.getTbSmOperpower() == castOther.getTbSmOperpower()) || (this
				.getTbSmOperpower() != null
				&& castOther.getTbSmOperpower() != null && this
				.getTbSmOperpower().equals(castOther.getTbSmOperpower())))
				&& ((this.getIndentityId() == castOther.getIndentityId()) || (this
						.getIndentityId() != null
						&& castOther.getIndentityId() != null && this
						.getIndentityId().equals(castOther.getIndentityId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTbSmOperpower() == null ? 0 : this.getTbSmOperpower()
						.hashCode());
		result = 37
				* result
				+ (getIndentityId() == null ? 0 : this.getIndentityId()
						.hashCode());
		return result;
	}

}