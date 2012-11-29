package com.nantian.npbs.business.model;

/**
 * TbSmOpRole entity. @author MyEclipse Persistence Tools
 */

public class TbSmOpRole implements java.io.Serializable {

	// Fields

	private TbSmOpRoleId id;
	private String moditime;
	private String modiOperCode;

	// Constructors

	/** default constructor */
	public TbSmOpRole() {
	}

	/** minimal constructor */
	public TbSmOpRole(TbSmOpRoleId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmOpRole(TbSmOpRoleId id, String moditime, String modiOperCode) {
		this.id = id;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
	}

	// Property accessors

	public TbSmOpRoleId getId() {
		return this.id;
	}

	public void setId(TbSmOpRoleId id) {
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