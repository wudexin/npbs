package com.nantian.npbs.business.model;

/**
 * TbSmOpBus entity. @author MyEclipse Persistence Tools
 */

public class TbSmOpBus implements java.io.Serializable {

	// Fields

	private TbSmOpBusId id;
	private String moditime;
	private String modiOperCode;

	// Constructors

	/** default constructor */
	public TbSmOpBus() {
	}

	/** minimal constructor */
	public TbSmOpBus(TbSmOpBusId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmOpBus(TbSmOpBusId id, String moditime, String modiOperCode) {
		this.id = id;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
	}

	// Property accessors

	public TbSmOpBusId getId() {
		return this.id;
	}

	public void setId(TbSmOpBusId id) {
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