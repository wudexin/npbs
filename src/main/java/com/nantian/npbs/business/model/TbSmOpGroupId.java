package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmOpGroupId entity. @author MyEclipse Persistence Tools
 */

public class TbSmOpGroupId implements java.io.Serializable {

	// Fields

	private String operCode;
	private BigDecimal groupId;

	// Constructors

	/** default constructor */
	public TbSmOpGroupId() {
	}

	/** full constructor */
	public TbSmOpGroupId(String operCode, BigDecimal groupId) {
		this.operCode = operCode;
		this.groupId = groupId;
	}

	// Property accessors

	public String getOperCode() {
		return this.operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmOpGroupId))
			return false;
		TbSmOpGroupId castOther = (TbSmOpGroupId) other;

		return ((this.getOperCode() == castOther.getOperCode()) || (this
				.getOperCode() != null
				&& castOther.getOperCode() != null && this.getOperCode()
				.equals(castOther.getOperCode())))
				&& ((this.getGroupId() == castOther.getGroupId()) || (this
						.getGroupId() != null
						&& castOther.getGroupId() != null && this.getGroupId()
						.equals(castOther.getGroupId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOperCode() == null ? 0 : this.getOperCode().hashCode());
		result = 37 * result
				+ (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		return result;
	}

}