package com.nantian.npbs.business.model;

/**
 * TbSmMessageSj entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessageSj implements java.io.Serializable {

	// Fields

	private TbSmMessageSjId id;
	private Boolean state;

	// Constructors

	/** default constructor */
	public TbSmMessageSj() {
	}

	/** minimal constructor */
	public TbSmMessageSj(TbSmMessageSjId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmMessageSj(TbSmMessageSjId id, Boolean state) {
		this.id = id;
		this.state = state;
	}

	// Property accessors

	public TbSmMessageSjId getId() {
		return this.id;
	}

	public void setId(TbSmMessageSjId id) {
		this.id = id;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

}