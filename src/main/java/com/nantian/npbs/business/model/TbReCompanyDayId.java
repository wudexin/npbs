package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbReCompanyDayId entity. @author MyEclipse Persistence Tools
 */

public class TbReCompanyDayId implements java.io.Serializable {

	// Fields

	private String reportDate;
	private String reportType;
	private BigDecimal reportId;

	// Constructors

	/** default constructor */
	public TbReCompanyDayId() {
	}

	/** full constructor */
	public TbReCompanyDayId(String reportDate, String reportType,
			BigDecimal reportId) {
		this.reportDate = reportDate;
		this.reportType = reportType;
		this.reportId = reportId;
	}

	// Property accessors

	public String getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public BigDecimal getReportId() {
		return this.reportId;
	}

	public void setReportId(BigDecimal reportId) {
		this.reportId = reportId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbReCompanyDayId))
			return false;
		TbReCompanyDayId castOther = (TbReCompanyDayId) other;

		return ((this.getReportDate() == castOther.getReportDate()) || (this
				.getReportDate() != null
				&& castOther.getReportDate() != null && this.getReportDate()
				.equals(castOther.getReportDate())))
				&& ((this.getReportType() == castOther.getReportType()) || (this
						.getReportType() != null
						&& castOther.getReportType() != null && this
						.getReportType().equals(castOther.getReportType())))
				&& ((this.getReportId() == castOther.getReportId()) || (this
						.getReportId() != null
						&& castOther.getReportId() != null && this
						.getReportId().equals(castOther.getReportId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getReportDate() == null ? 0 : this.getReportDate()
						.hashCode());
		result = 37
				* result
				+ (getReportType() == null ? 0 : this.getReportType()
						.hashCode());
		result = 37 * result
				+ (getReportId() == null ? 0 : this.getReportId().hashCode());
		return result;
	}

}