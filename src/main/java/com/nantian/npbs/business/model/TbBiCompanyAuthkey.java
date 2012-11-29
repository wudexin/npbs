package com.nantian.npbs.business.model;

/**
 * TbBiCompanyAuthkey entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyAuthkey implements java.io.Serializable {

	// Fields

	private TbBiCompanyAuthkeyId id;

	// Constructors

	/** default constructor */
	public TbBiCompanyAuthkey() {
	}

	/** full constructor */
	public TbBiCompanyAuthkey(TbBiCompanyAuthkeyId id) {
		this.id = id;
	}

	// Property accessors

	public TbBiCompanyAuthkeyId getId() {
		return this.id;
	}

	public void setId(TbBiCompanyAuthkeyId id) {
		this.id = id;
	}

}