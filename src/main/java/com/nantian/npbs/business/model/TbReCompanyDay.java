package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbReCompanyDay entity. @author MyEclipse Persistence Tools
 */

public class TbReCompanyDay implements java.io.Serializable {

	// Fields

	private BigDecimal reportId;
	private BigDecimal reportType;
	private String reportDate;
	private String unitcode;
	private String unitname;
	private Integer normalNum;
	private Integer offNum;
	private Integer pauseNum;
	private Integer addNum;
	private String channelCode;

	// Constructors

	/** default constructor */
	public TbReCompanyDay() {
	}

	/** minimal constructor */
	public TbReCompanyDay(BigDecimal reportId, BigDecimal reportType) {
		this.reportId = reportId;
		this.reportType = reportType;
	}

	/** full constructor */
	public TbReCompanyDay(BigDecimal reportId, BigDecimal reportType,
			String reportDate, String unitcode, String unitname,
			Integer normalNum, Integer offNum, Integer pauseNum,
			Integer addNum, String channelCode) {
		this.reportId = reportId;
		this.reportType = reportType;
		this.reportDate = reportDate;
		this.unitcode = unitcode;
		this.unitname = unitname;
		this.normalNum = normalNum;
		this.offNum = offNum;
		this.pauseNum = pauseNum;
		this.addNum = addNum;
		this.channelCode = channelCode;
	}

	// Property accessors

	public BigDecimal getReportId() {
		return this.reportId;
	}

	public void setReportId(BigDecimal reportId) {
		this.reportId = reportId;
	}

	public BigDecimal getReportType() {
		return this.reportType;
	}

	public void setReportType(BigDecimal reportType) {
		this.reportType = reportType;
	}

	public String getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public Integer getNormalNum() {
		return this.normalNum;
	}

	public void setNormalNum(Integer normalNum) {
		this.normalNum = normalNum;
	}

	public Integer getOffNum() {
		return this.offNum;
	}

	public void setOffNum(Integer offNum) {
		this.offNum = offNum;
	}

	public Integer getPauseNum() {
		return this.pauseNum;
	}

	public void setPauseNum(Integer pauseNum) {
		this.pauseNum = pauseNum;
	}

	public Integer getAddNum() {
		return this.addNum;
	}

	public void setAddNum(Integer addNum) {
		this.addNum = addNum;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

}