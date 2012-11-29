package com.nantian.npbs.business.model;

/**
 * TbSmMessageDls entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessageDls implements java.io.Serializable {

	// Fields

	private TbSmMessageDlsId id;
	private Boolean state;

	// Constructors

	/** default constructor */
	public TbSmMessageDls() {
	}

	/** minimal constructor */
	public TbSmMessageDls(TbSmMessageDlsId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmMessageDls(TbSmMessageDlsId id, Boolean state) {
		this.id = id;
		this.state = state;
	}

	// Property accessors

	public TbSmMessageDlsId getId() {
		return this.id;
	}

	public void setId(TbSmMessageDlsId id) {
		this.id = id;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

}