package com.nantian.npbs.business.model;

/**
 * TbReSalaryId entity. @author MyEclipse Persistence Tools
 */

public class TbReSalaryId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String salaryDate;

	// Constructors

	/** default constructor */
	public TbReSalaryId() {
	}

	/** full constructor */
	public TbReSalaryId(String companyCode, String salaryDate) {
		this.companyCode = companyCode;
		this.salaryDate = salaryDate;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSalaryDate() {
		return this.salaryDate;
	}

	public void setSalaryDate(String salaryDate) {
		this.salaryDate = salaryDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbReSalaryId))
			return false;
		TbReSalaryId castOther = (TbReSalaryId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getSalaryDate() == castOther.getSalaryDate()) || (this
						.getSalaryDate() != null
						&& castOther.getSalaryDate() != null && this
						.getSalaryDate().equals(castOther.getSalaryDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37
				* result
				+ (getSalaryDate() == null ? 0 : this.getSalaryDate()
						.hashCode());
		return result;
	}

}