package com.nantian.npbs.business.model;

/**
 * TbBiBusinessUnitId entity. @author MyEclipse Persistence Tools
 */

public class TbBiBusinessUnitId implements java.io.Serializable {

	// Fields

	private String busiCode;
	private String unitcode;

	// Constructors

	/** default constructor */
	public TbBiBusinessUnitId() {
	}

	/** full constructor */
	public TbBiBusinessUnitId(String busiCode, String unitcode) {
		this.busiCode = busiCode;
		this.unitcode = unitcode;
	}

	// Property accessors

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiBusinessUnitId))
			return false;
		TbBiBusinessUnitId castOther = (TbBiBusinessUnitId) other;

		return ((this.getBusiCode() == castOther.getBusiCode()) || (this
				.getBusiCode() != null
				&& castOther.getBusiCode() != null && this.getBusiCode()
				.equals(castOther.getBusiCode())))
				&& ((this.getUnitcode() == castOther.getUnitcode()) || (this
						.getUnitcode() != null
						&& castOther.getUnitcode() != null && this
						.getUnitcode().equals(castOther.getUnitcode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBusiCode() == null ? 0 : this.getBusiCode().hashCode());
		result = 37 * result
				+ (getUnitcode() == null ? 0 : this.getUnitcode().hashCode());
		return result;
	}

}