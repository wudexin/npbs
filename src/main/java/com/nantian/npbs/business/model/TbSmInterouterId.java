package com.nantian.npbs.business.model;

/**
 * TbSmInterouterId entity. @author MyEclipse Persistence Tools
 */

public class TbSmInterouterId implements java.io.Serializable {

	// Fields

	private String insiUnitcode;
	private String syscode;

	// Constructors

	/** default constructor */
	public TbSmInterouterId() {
	}

	/** full constructor */
	public TbSmInterouterId(String insiUnitcode, String syscode) {
		this.insiUnitcode = insiUnitcode;
		this.syscode = syscode;
	}

	// Property accessors

	public String getInsiUnitcode() {
		return this.insiUnitcode;
	}

	public void setInsiUnitcode(String insiUnitcode) {
		this.insiUnitcode = insiUnitcode;
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
		if (!(other instanceof TbSmInterouterId))
			return false;
		TbSmInterouterId castOther = (TbSmInterouterId) other;

		return ((this.getInsiUnitcode() == castOther.getInsiUnitcode()) || (this
				.getInsiUnitcode() != null
				&& castOther.getInsiUnitcode() != null && this
				.getInsiUnitcode().equals(castOther.getInsiUnitcode())))
				&& ((this.getSyscode() == castOther.getSyscode()) || (this
						.getSyscode() != null
						&& castOther.getSyscode() != null && this.getSyscode()
						.equals(castOther.getSyscode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getInsiUnitcode() == null ? 0 : this.getInsiUnitcode()
						.hashCode());
		result = 37 * result
				+ (getSyscode() == null ? 0 : this.getSyscode().hashCode());
		return result;
	}

}