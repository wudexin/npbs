package com.nantian.npbs.business.model;

/**
 * TbSmMessageOper entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessageOper implements java.io.Serializable {

	// Fields

	private TbSmMessageOperId id;
	private Boolean state;

	// Constructors

	/** default constructor */
	public TbSmMessageOper() {
	}

	/** minimal constructor */
	public TbSmMessageOper(TbSmMessageOperId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmMessageOper(TbSmMessageOperId id, Boolean state) {
		this.id = id;
		this.state = state;
	}

	// Property accessors

	public TbSmMessageOperId getId() {
		return this.id;
	}

	public void setId(TbSmMessageOperId id) {
		this.id = id;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

}