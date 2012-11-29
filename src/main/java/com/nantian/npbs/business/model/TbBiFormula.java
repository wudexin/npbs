package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbBiFormula entity. @author MyEclipse Persistence Tools
 */

public class TbBiFormula implements java.io.Serializable {

	// Fields

	private BigDecimal formulaId;
	private String busiCode;
	private String channelCode;
	private String unitcode;
	private String title;

	// Constructors

	/** default constructor */
	public TbBiFormula() {
	}

	/** minimal constructor */
	public TbBiFormula(BigDecimal formulaId, String busiCode) {
		this.formulaId = formulaId;
		this.busiCode = busiCode;
	}

	/** full constructor */
	public TbBiFormula(BigDecimal formulaId, String busiCode,
			String channelCode, String unitcode, String title) {
		this.formulaId = formulaId;
		this.busiCode = busiCode;
		this.channelCode = channelCode;
		this.unitcode = unitcode;
		this.title = title;
	}

	// Property accessors

	public BigDecimal getFormulaId() {
		return this.formulaId;
	}

	public void setFormulaId(BigDecimal formulaId) {
		this.formulaId = formulaId;
	}

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}