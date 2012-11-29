package com.nantian.npbs.business.model;

/**
 * TbSmStatusinfo entity. @author MyEclipse Persistence Tools
 */

public class TbSmStatusinfo implements java.io.Serializable {

	// Fields

	private TbSmStatusinfoId id;

	// Constructors

	/** default constructor */
	public TbSmStatusinfo() {
	}

	/** full constructor */
	public TbSmStatusinfo(TbSmStatusinfoId id) {
		this.id = id;
	}

	// Property accessors

	public TbSmStatusinfoId getId() {
		return this.id;
	}

	public void setId(TbSmStatusinfoId id) {
		this.id = id;
	}

}