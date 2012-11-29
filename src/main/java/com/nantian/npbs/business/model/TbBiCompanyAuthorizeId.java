package com.nantian.npbs.business.model;

/**
 * TbBiCompanyAuthorizeId entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyAuthorizeId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String busiCode;
	private String customerno;

	// Constructors

	/** default constructor */
	public TbBiCompanyAuthorizeId() {
	}

	/** full constructor */
	public TbBiCompanyAuthorizeId(String companyCode, String busiCode,
			String customerno) {
		this.companyCode = companyCode;
		this.busiCode = busiCode;
		this.customerno = customerno;
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

	public String getCustomerno() {
		return this.customerno;
	}

	public void setCustomerno(String customerno) {
		this.customerno = customerno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiCompanyAuthorizeId))
			return false;
		TbBiCompanyAuthorizeId castOther = (TbBiCompanyAuthorizeId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getBusiCode() == castOther.getBusiCode()) || (this
						.getBusiCode() != null
						&& castOther.getBusiCode() != null && this
						.getBusiCode().equals(castOther.getBusiCode())))
				&& ((this.getCustomerno() == castOther.getCustomerno()) || (this
						.getCustomerno() != null
						&& castOther.getCustomerno() != null && this
						.getCustomerno().equals(castOther.getCustomerno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37 * result
				+ (getBusiCode() == null ? 0 : this.getBusiCode().hashCode());
		result = 37
				* result
				+ (getCustomerno() == null ? 0 : this.getCustomerno()
						.hashCode());
		return result;
	}

}