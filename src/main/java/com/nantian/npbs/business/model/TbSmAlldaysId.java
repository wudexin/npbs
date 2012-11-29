package com.nantian.npbs.business.model;

/**
 * TbSmAlldaysId entity. @author MyEclipse Persistence Tools
 */

public class TbSmAlldaysId implements java.io.Serializable {

	// Fields

	private String dayval;
	private String ischeck;

	// Constructors

	/** default constructor */
	public TbSmAlldaysId() {
	}

	/** full constructor */
	public TbSmAlldaysId(String dayval, String ischeck) {
		this.dayval = dayval;
		this.ischeck = ischeck;
	}

	// Property accessors

	public String getDayval() {
		return this.dayval;
	}

	public void setDayval(String dayval) {
		this.dayval = dayval;
	}

	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmAlldaysId))
			return false;
		TbSmAlldaysId castOther = (TbSmAlldaysId) other;

		return ((this.getDayval() == castOther.getDayval()) || (this
				.getDayval() != null
				&& castOther.getDayval() != null && this.getDayval().equals(
				castOther.getDayval())))
				&& ((this.getIscheck() == castOther.getIscheck()) || (this
						.getIscheck() != null
						&& castOther.getIscheck() != null && this.getIscheck()
						.equals(castOther.getIscheck())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDayval() == null ? 0 : this.getDayval().hashCode());
		result = 37 * result
				+ (getIscheck() == null ? 0 : this.getIscheck().hashCode());
		return result;
	}

}