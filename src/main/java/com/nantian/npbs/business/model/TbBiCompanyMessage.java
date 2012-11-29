package com.nantian.npbs.business.model;

/**
 * TbBiCompanyMessage entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyMessage implements java.io.Serializable {

	// Fields

	private TbBiCompanyMessageId id;

	private String remark;

	// Constructors

	/** default constructor */
	public TbBiCompanyMessage() {
	}

	/** full constructor */
	public TbBiCompanyMessage(TbBiCompanyMessageId id ,String remark) {
		this.id = id;
		this.remark = remark;
	}

	// Property accessors

	public TbBiCompanyMessageId getId() {
		return this.id;
	}

	public void setId(TbBiCompanyMessageId id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}