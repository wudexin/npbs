package com.nantian.npbs.business.model;

/**
 * TbSmKeyserial entity. @author MyEclipse Persistence Tools
 */

public class TbSmKeyserial implements java.io.Serializable {

	// Fields

	private TbSmKeyserialId id;

	// Constructors

	/** default constructor */
	public TbSmKeyserial() {
	}

	/** full constructor */
	public TbSmKeyserial(TbSmKeyserialId id) {
		this.id = id;
	}

	// Property accessors

	public TbSmKeyserialId getId() {
		return this.id;
	}

	public void setId(TbSmKeyserialId id) {
		this.id = id;
	}

}