package com.nantian.npbs.business.model;

import java.util.HashSet;
import java.util.Set;

/**
 * TbSmBus entity. @author MyEclipse Persistence Tools
 */

public class TbSmBus implements java.io.Serializable {

	// Fields

	private String busId;
	private String busName;
	private Set tbSmBusGroups = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSmBus() {
	}

	/** minimal constructor */
	public TbSmBus(String busId) {
		this.busId = busId;
	}

	/** full constructor */
	public TbSmBus(String busId, String busName, Set tbSmBusGroups) {
		this.busId = busId;
		this.busName = busName;
		this.tbSmBusGroups = tbSmBusGroups;
	}

	// Property accessors

	public String getBusId() {
		return this.busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getBusName() {
		return this.busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public Set getTbSmBusGroups() {
		return this.tbSmBusGroups;
	}

	public void setTbSmBusGroups(Set tbSmBusGroups) {
		this.tbSmBusGroups = tbSmBusGroups;
	}

}