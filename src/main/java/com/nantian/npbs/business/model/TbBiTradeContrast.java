package com.nantian.npbs.business.model;

/**
 * TbBiTradeContrast entity. @author MyEclipse Persistence Tools
 */

public class TbBiTradeContrast implements java.io.Serializable {

	// Fields

	private TbBiTradeContrastId id;
	private String tradeType;

	// Constructors

	/** default constructor */
	public TbBiTradeContrast() {
	}

	/** full constructor */
	public TbBiTradeContrast(TbBiTradeContrastId id, String tradeType) {
		this.id = id;
		this.tradeType = tradeType;
	}

	// Property accessors

	public TbBiTradeContrastId getId() {
		return this.id;
	}

	public void setId(TbBiTradeContrastId id) {
		this.id = id;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}