package com.nantian.npbs.business.model;

/**
 * TbSmBusGroup entity. @author MyEclipse Persistence Tools
 */

public class TbSmBusGroup implements java.io.Serializable {

	// Fields

	private String busId;
	private TbSmBusgroups tbSmBusgroups;
	private TbSmBus tbSmBus;
	private String moditime;
	private String modiOperCode;

	// Constructors

	/** default constructor */
	public TbSmBusGroup() {
	}

	/** minimal constructor */
	public TbSmBusGroup(String busId, TbSmBus tbSmBus) {
		this.busId = busId;
		this.tbSmBus = tbSmBus;
	}

	/** full constructor */
	public TbSmBusGroup(String busId, TbSmBusgroups tbSmBusgroups,
			TbSmBus tbSmBus, String moditime, String modiOperCode) {
		this.busId = busId;
		this.tbSmBusgroups = tbSmBusgroups;
		this.tbSmBus = tbSmBus;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
	}

	// Property accessors

	public String getBusId() {
		return this.busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public TbSmBusgroups getTbSmBusgroups() {
		return this.tbSmBusgroups;
	}

	public void setTbSmBusgroups(TbSmBusgroups tbSmBusgroups) {
		this.tbSmBusgroups = tbSmBusgroups;
	}

	public TbSmBus getTbSmBus() {
		return this.tbSmBus;
	}

	public void setTbSmBus(TbSmBus tbSmBus) {
		this.tbSmBus = tbSmBus;
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