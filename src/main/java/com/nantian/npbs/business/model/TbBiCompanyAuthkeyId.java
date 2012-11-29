package com.nantian.npbs.business.model;

/**
 * TbBiCompanyAuthkeyId entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyAuthkeyId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String state;
	private String authDate;
	private String remark;

	// Constructors

	/** default constructor */
	public TbBiCompanyAuthkeyId() {
	}

	/** minimal constructor */
	public TbBiCompanyAuthkeyId(String companyCode) {
		this.companyCode = companyCode;
	}

	/** full constructor */
	public TbBiCompanyAuthkeyId(String companyCode, String state,
			String authDate, String remark) {
		this.companyCode = companyCode;
		this.state = state;
		this.authDate = authDate;
		this.remark = remark;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiCompanyAuthkeyId))
			return false;
		TbBiCompanyAuthkeyId castOther = (TbBiCompanyAuthkeyId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getState() == castOther.getState()) || (this
						.getState() != null
						&& castOther.getState() != null && this.getState()
						.equals(castOther.getState())))
				&& ((this.getAuthDate() == castOther.getAuthDate()) || (this
						.getAuthDate() != null
						&& castOther.getAuthDate() != null && this
						.getAuthDate().equals(castOther.getAuthDate())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37 * result
				+ (getState() == null ? 0 : this.getState().hashCode());
		result = 37 * result
				+ (getAuthDate() == null ? 0 : this.getAuthDate().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		return result;
	}

}