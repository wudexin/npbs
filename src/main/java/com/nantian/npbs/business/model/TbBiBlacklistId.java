package com.nantian.npbs.business.model;

/**
 * TbBiBlacklistId entity. @author MyEclipse Persistence Tools
 */

public class TbBiBlacklistId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String accno;
	private String startDate;

	// Constructors

	/** default constructor */
	public TbBiBlacklistId() {
	}

	/** full constructor */
	public TbBiBlacklistId(String companyCode, String accno, String startDate) {
		this.companyCode = companyCode;
		this.accno = accno;
		this.startDate = startDate;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiBlacklistId))
			return false;
		TbBiBlacklistId castOther = (TbBiBlacklistId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getAccno() == castOther.getAccno()) || (this
						.getAccno() != null
						&& castOther.getAccno() != null && this.getAccno()
						.equals(castOther.getAccno())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37 * result
				+ (getAccno() == null ? 0 : this.getAccno().hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		return result;
	}

}