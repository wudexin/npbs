package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbReBusiDay entity. @author MyEclipse Persistence Tools
 */

public class TbReBusiDay implements java.io.Serializable {

	// Fields

	private BigDecimal reportId;
	private BigDecimal reportType;
	private String reportDate;
	private String content;
	private String unitcode;
	private String unitname;
	private String channelCode;
	private String channelName;
	private Double amount;
	private Long quantity;
	private String busiCode;
	private String busiName;

	// Constructors

	/** default constructor */
	public TbReBusiDay() {
	}

	/** minimal constructor */
	public TbReBusiDay(BigDecimal reportId, BigDecimal reportType) {
		this.reportId = reportId;
		this.reportType = reportType;
	}

	/** full constructor */
	public TbReBusiDay(BigDecimal reportId, BigDecimal reportType,
			String reportDate, String content, String unitcode,
			String unitname, String channelCode, String channelName,
			Double amount, Long quantity, String busiCode, String busiName) {
		this.reportId = reportId;
		this.reportType = reportType;
		this.reportDate = reportDate;
		this.content = content;
		this.unitcode = unitcode;
		this.unitname = unitname;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.amount = amount;
		this.quantity = quantity;
		this.busiCode = busiCode;
		this.busiName = busiName;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getBusiName() {
		return this.busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

}