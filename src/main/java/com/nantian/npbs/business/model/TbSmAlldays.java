package com.nantian.npbs.business.model;

/**
 * TbSmAlldays entity. @author MyEclipse Persistence Tools
 */

public class TbSmAlldays implements java.io.Serializable {

	// Fields

	private TbSmAlldaysId id;

	// Constructors

	/** default constructor */
	public TbSmAlldays() {
	}

	/** full constructor */
	public TbSmAlldays(TbSmAlldaysId id) {
		this.id = id;
	}

	// Property accessors

	public TbSmAlldaysId getId() {
		return this.id;
	}

	public void setId(TbSmAlldaysId id) {
		this.id = id;
	}

}