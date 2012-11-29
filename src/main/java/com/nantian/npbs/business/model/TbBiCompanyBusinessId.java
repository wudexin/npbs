package com.nantian.npbs.business.model;

/**
 * TbBiCompanyBusinessId entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyBusinessId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String busiCode;

	// Constructors

	/** default constructor */
	public TbBiCompanyBusinessId() {
	}

	/** full constructor */
	public TbBiCompanyBusinessId(String companyCode, String busiCode) {
		this.companyCode = companyCode;
		this.busiCode = busiCode;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiCompanyBusinessId))
			return false;
		TbBiCompanyBusinessId castOther = (TbBiCompanyBusinessId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getBusiCode() == castOther.getBusiCode()) || (this
						.getBusiCode() != null
						&& castOther.getBusiCode() != null && this
						.getBusiCode().equals(castOther.getBusiCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37 * result
				+ (getBusiCode() == null ? 0 : this.getBusiCode().hashCode());
		return result;
	}

}