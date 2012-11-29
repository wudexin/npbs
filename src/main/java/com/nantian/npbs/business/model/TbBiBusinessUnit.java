package com.nantian.npbs.business.model;

/**
 * TbBiBusinessUnit entity. @author MyEclipse Persistence Tools
 */

public class TbBiBusinessUnit implements java.io.Serializable {

	// Fields

	private TbBiBusinessUnitId id;
	private Integer porcessmax;
	private Integer processnow;
	private Double feemix;
	private String remark;

	// Constructors

	/** default constructor */
	public TbBiBusinessUnit() {
	}

	/** minimal constructor */
	public TbBiBusinessUnit(TbBiBusinessUnitId id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiBusinessUnit(TbBiBusinessUnitId id, Integer porcessmax,
			Integer processnow, Double feemix, String remark) {
		this.id = id;
		this.porcessmax = porcessmax;
		this.processnow = processnow;
		this.feemix = feemix;
		this.remark = remark;
	}

	// Property accessors

	public TbBiBusinessUnitId getId() {
		return this.id;
	}

	public void setId(TbBiBusinessUnitId id) {
		this.id = id;
	}

	public Integer getPorcessmax() {
		return this.porcessmax;
	}

	public void setPorcessmax(Integer porcessmax) {
		this.porcessmax = porcessmax;
	}

	public Integer getProcessnow() {
		return this.processnow;
	}

	public void setProcessnow(Integer processnow) {
		this.processnow = processnow;
	}

	public Double getFeemix() {
		return this.feemix;
	}

	public void setFeemix(Double feemix) {
		this.feemix = feemix;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}