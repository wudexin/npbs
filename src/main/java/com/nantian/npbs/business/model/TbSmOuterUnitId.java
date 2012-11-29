package com.nantian.npbs.business.model;

/**
 * TbSmOuterUnitId entity. @author MyEclipse Persistence Tools
 */

public class TbSmOuterUnitId implements java.io.Serializable {

	// Fields

	private String outUnitcode;
	private String syscode;

	// Constructors

	/** default constructor */
	public TbSmOuterUnitId() {
	}

	/** full constructor */
	public TbSmOuterUnitId(String outUnitcode, String syscode) {
		this.outUnitcode = outUnitcode;
		this.syscode = syscode;
	}

	// Property accessors

	public String getOutUnitcode() {
		return this.outUnitcode;
	}

	public void setOutUnitcode(String outUnitcode) {
		this.outUnitcode = outUnitcode;
	}

	public String getSyscode() {
		return this.syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmOuterUnitId))
			return false;
		TbSmOuterUnitId castOther = (TbSmOuterUnitId) other;

		return ((this.getOutUnitcode() == castOther.getOutUnitcode()) || (this
				.getOutUnitcode() != null
				&& castOther.getOutUnitcode() != null && this.getOutUnitcode()
				.equals(castOther.getOutUnitcode())))
				&& ((this.getSyscode() == castOther.getSyscode()) || (this
						.getSyscode() != null
						&& castOther.getSyscode() != null && this.getSyscode()
						.equals(castOther.getSyscode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getOutUnitcode() == null ? 0 : this.getOutUnitcode()
						.hashCode());
		result = 37 * result
				+ (getSyscode() == null ? 0 : this.getSyscode().hashCode());
		return result;
	}

}