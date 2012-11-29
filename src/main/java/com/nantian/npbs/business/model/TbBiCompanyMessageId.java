package com.nantian.npbs.business.model;

/**
 * TbBiCompanyMessageId entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyMessageId implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String message;
	private String issend;

	// Constructors

	/** default constructor */
	public TbBiCompanyMessageId() {
	}

	/** minimal constructor */
	public TbBiCompanyMessageId(String companyCode) {
		this.companyCode = companyCode;
	}

	/** full constructor */
	public TbBiCompanyMessageId(String companyCode, String message,
			String issend, String remark) {
		this.companyCode = companyCode;
		this.message = message;
		this.issend = issend;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIssend() {
		return this.issend;
	}

	public void setIssend(String issend) {
		this.issend = issend;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiCompanyMessageId))
			return false;
		TbBiCompanyMessageId castOther = (TbBiCompanyMessageId) other;

		return ((this.getCompanyCode() == castOther.getCompanyCode()) || (this
				.getCompanyCode() != null
				&& castOther.getCompanyCode() != null && this.getCompanyCode()
				.equals(castOther.getCompanyCode())))
				&& ((this.getMessage() == castOther.getMessage()) || (this
						.getMessage() != null
						&& castOther.getMessage() != null && this.getMessage()
						.equals(castOther.getMessage())))
				&& ((this.getIssend() == castOther.getIssend()) || (this
						.getIssend() != null
						&& castOther.getIssend() != null && this.getIssend()
						.equals(castOther.getIssend())));
	}

	public int hashCode() {
		int result = 17;
		result = 37
				* result
				+ (getCompanyCode() == null ? 0 : this.getCompanyCode()
						.hashCode());
		result = 37 * result
				+ (getMessage() == null ? 0 : this.getMessage().hashCode());
		result = 37 * result
				+ (getIssend() == null ? 0 : this.getIssend().hashCode());
		result = 37 * result ;
		return result;
	}

}