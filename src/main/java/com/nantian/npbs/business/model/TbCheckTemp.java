package com.nantian.npbs.business.model;

/**
 * TbCheckTemp entity. @author MyEclipse Persistence Tools
 */

public class TbCheckTemp implements java.io.Serializable {

	// Fields

	private TbCheckTempId id;

	// Constructors

	/** default constructor */
	public TbCheckTemp() {
	}

	/** full constructor */
	public TbCheckTemp(TbCheckTempId id) {
		this.id = id;
	}

	// Property accessors

	public TbCheckTempId getId() {
		return this.id;
	}

	public void setId(TbCheckTempId id) {
		this.id = id;
	}

}