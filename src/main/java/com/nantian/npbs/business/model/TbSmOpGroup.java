package com.nantian.npbs.business.model;

/**
 * TbSmOpGroup entity. @author MyEclipse Persistence Tools
 */

public class TbSmOpGroup implements java.io.Serializable {

	// Fields

	private TbSmOpGroupId id;
	private String modiOperCode;
	private String moditime;

	// Constructors

	/** default constructor */
	public TbSmOpGroup() {
	}

	/** minimal constructor */
	public TbSmOpGroup(TbSmOpGroupId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmOpGroup(TbSmOpGroupId id, String modiOperCode, String moditime) {
		this.id = id;
		this.modiOperCode = modiOperCode;
		this.moditime = moditime;
	}

	// Property accessors

	public TbSmOpGroupId getId() {
		return this.id;
	}

	public void setId(TbSmOpGroupId id) {
		this.id = id;
	}

	public String getModiOperCode() {
		return this.modiOperCode;
	}

	public void setModiOperCode(String modiOperCode) {
		this.modiOperCode = modiOperCode;
	}

	public String getModitime() {
		return this.moditime;
	}

	public void setModitime(String moditime) {
		this.moditime = moditime;
	}

}