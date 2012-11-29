package com.nantian.npbs.business.model;

/**
 * TbBiFormulaDetail entity. @author MyEclipse Persistence Tools
 */

public class TbBiFormulaDetail implements java.io.Serializable {

	// Fields

	private TbBiFormulaDetailId id;
	private Integer amountBegin;
	private Integer amountEnd;
	private String calculateType;
	private Double calculateNum;

	// Constructors

	/** default constructor */
	public TbBiFormulaDetail() {
	}

	/** minimal constructor */
	public TbBiFormulaDetail(TbBiFormulaDetailId id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiFormulaDetail(TbBiFormulaDetailId id, Integer amountBegin,
			Integer amountEnd, String calculateType, Double calculateNum) {
		this.id = id;
		this.amountBegin = amountBegin;
		this.amountEnd = amountEnd;
		this.calculateType = calculateType;
		this.calculateNum = calculateNum;
	}

	// Property accessors

	public TbBiFormulaDetailId getId() {
		return this.id;
	}

	public void setId(TbBiFormulaDetailId id) {
		this.id = id;
	}

	public Integer getAmountBegin() {
		return this.amountBegin;
	}

	public void setAmountBegin(Integer amountBegin) {
		this.amountBegin = amountBegin;
	}

	public Integer getAmountEnd() {
		return this.amountEnd;
	}

	public void setAmountEnd(Integer amountEnd) {
		this.amountEnd = amountEnd;
	}

	public String getCalculateType() {
		return this.calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}

	public Double getCalculateNum() {
		return this.calculateNum;
	}

	public void setCalculateNum(Double calculateNum) {
		this.calculateNum = calculateNum;
	}

}