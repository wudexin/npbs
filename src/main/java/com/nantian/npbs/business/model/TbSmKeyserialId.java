package com.nantian.npbs.business.model;

/**
 * TbSmKeyserialId entity. @author MyEclipse Persistence Tools
 */

public class TbSmKeyserialId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String keytype;
	private String serialno;

	// Constructors

	/** default constructor */
	public TbSmKeyserialId() {
	}

	/** full constructor */
	public TbSmKeyserialId(String companyCode, String keytype, String serialno) {
		this.companyCode = companyCode;
		this.keytype = keytype;
		this.serialno = serialno;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getKeytype() {
		return this.keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmKeyserialId))
			return false;
		TbSmKeyserialId castOther = (TbSmKeyserialId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getKeytype() == castOther.getKeytype()) || (this
						.getKeytype() != null
						&& castOther.getKeytype() != null && this.getKeytype()
						.equals(castOther.getKeytype())))
				&& ((this.getSerialno() == castOther.getSerialno()) || (this
						.getSerialno() != null
						&& castOther.getSerialno() != null && this
						.getSerialno().equals(castOther.getSerialno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37 * result
				+ (getKeytype() == null ? 0 : this.getKeytype().hashCode());
		result = 37 * result
				+ (getSerialno() == null ? 0 : this.getSerialno().hashCode());
		return result;
	}

}