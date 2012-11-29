package com.nantian.npbs.business.model;

/**
 * TbSmRolePower entity. @author MyEclipse Persistence Tools
 */

public class TbSmRolePower implements java.io.Serializable {

	// Fields

	private TbSmRolePowerId id;
	private String moditime;
	private String modiOperCode;

	// Constructors

	/** default constructor */
	public TbSmRolePower() {
	}

	/** minimal constructor */
	public TbSmRolePower(TbSmRolePowerId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmRolePower(TbSmRolePowerId id, String moditime,
			String modiOperCode) {
		this.id = id;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
	}

	// Property accessors

	public TbSmRolePowerId getId() {
		return this.id;
	}

	public void setId(TbSmRolePowerId id) {
		this.id = id;
	}

	public String getModitime() {
		return this.moditime;
	}

	public void setModitime(String moditime) {
		this.moditime = moditime;
	}

	public String getModiOperCode() {
		return this.modiOperCode;
	}

	public void setModiOperCode(String modiOperCode) {
		this.modiOperCode = modiOperCode;
	}

}