package com.nantian.npbs.business.model;

/**
 * TbBiCheckId entity. @author MyEclipse Persistence Tools
 */

public class TbBiCheckId implements java.io.Serializable {

	// Fields

	private String radeDate;
	private String pbSerial;

	// Constructors

	/** default constructor */
	public TbBiCheckId() {
	}

	/** full constructor */
	public TbBiCheckId(String radeDate, String pbSerial) {
		this.radeDate = radeDate;
		this.pbSerial = pbSerial;
	}

	// Property accessors

	public String getRadeDate() {
		return this.radeDate;
	}

	public void setRadeDate(String radeDate) {
		this.radeDate = radeDate;
	}

	public String getPbSerial() {
		return this.pbSerial;
	}

	public void setPbSerial(String pbSerial) {
		this.pbSerial = pbSerial;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiCheckId))
			return false;
		TbBiCheckId castOther = (TbBiCheckId) other;

		return ((this.getRadeDate() == castOther.getRadeDate()) || (this
				.getRadeDate() != null
				&& castOther.getRadeDate() != null && this.getRadeDate()
				.equals(castOther.getRadeDate())))
				&& ((this.getPbSerial() == castOther.getPbSerial()) || (this
						.getPbSerial() != null
						&& castOther.getPbSerial() != null && this
						.getPbSerial().equals(castOther.getPbSerial())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRadeDate() == null ? 0 : this.getRadeDate().hashCode());
		result = 37 * result
				+ (getPbSerial() == null ? 0 : this.getPbSerial().hashCode());
		return result;
	}

}