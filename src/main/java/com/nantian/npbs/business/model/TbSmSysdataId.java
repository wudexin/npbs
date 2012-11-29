package com.nantian.npbs.business.model;

/**
 * TbSmSysdataId entity. @author MyEclipse Persistence Tools
 */

public class TbSmSysdataId implements java.io.Serializable {

	// Fields

	private String systemDate;
	private String batDate;

	// Constructors

	/** default constructor */
	public TbSmSysdataId() {
	}

	/** minimal constructor */
	public TbSmSysdataId(String systemDate) {
		this.systemDate = systemDate;
	}

	/** full constructor */
	public TbSmSysdataId(String systemDate, String batDate) {
		this.systemDate = systemDate;
		this.batDate = batDate;
	}

	// Property accessors

	public String getSystemDate() {
		return this.systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}

	public String getBatDate() {
		return this.batDate;
	}

	public void setBatDate(String batDate) {
		this.batDate = batDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmSysdataId))
			return false;
		TbSmSysdataId castOther = (TbSmSysdataId) other;

		return ((this.getSystemDate() == castOther.getSystemDate()) || (this
				.getSystemDate() != null
				&& castOther.getSystemDate() != null && this.getSystemDate()
				.equals(castOther.getSystemDate())))
				&& ((this.getBatDate() == castOther.getBatDate()) || (this
						.getBatDate() != null
						&& castOther.getBatDate() != null && this.getBatDate()
						.equals(castOther.getBatDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSystemDate() == null ? 0 : this.getSystemDate()
						.hashCode());
		result = 37 * result
				+ (getBatDate() == null ? 0 : this.getBatDate().hashCode());
		return result;
	}

}