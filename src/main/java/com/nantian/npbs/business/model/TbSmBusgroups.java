package com.nantian.npbs.business.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * TbSmBusgroups entity. @author MyEclipse Persistence Tools
 */

public class TbSmBusgroups implements java.io.Serializable {

	// Fields

	private BigDecimal busGroupId;
	private String busGroupDis;
	private String busGroupName;
	private String moditime;
	private String modiOperCode;
	private Set tbSmBusGroups = new HashSet(0);
	private Set tbSmOpBuses = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSmBusgroups() {
	}

	/** minimal constructor */
	public TbSmBusgroups(BigDecimal busGroupId) {
		this.busGroupId = busGroupId;
	}

	/** full constructor */
	public TbSmBusgroups(BigDecimal busGroupId, String busGroupDis,
			String busGroupName, String moditime, String modiOperCode,
			Set tbSmBusGroups, Set tbSmOpBuses) {
		this.busGroupId = busGroupId;
		this.busGroupDis = busGroupDis;
		this.busGroupName = busGroupName;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
		this.tbSmBusGroups = tbSmBusGroups;
		this.tbSmOpBuses = tbSmOpBuses;
	}

	// Property accessors

	public BigDecimal getBusGroupId() {
		return this.busGroupId;
	}

	public void setBusGroupId(BigDecimal busGroupId) {
		this.busGroupId = busGroupId;
	}

	public String getBusGroupDis() {
		return this.busGroupDis;
	}

	public void setBusGroupDis(String busGroupDis) {
		this.busGroupDis = busGroupDis;
	}

	public String getBusGroupName() {
		return this.busGroupName;
	}

	public void setBusGroupName(String busGroupName) {
		this.busGroupName = busGroupName;
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

	public Set getTbSmBusGroups() {
		return this.tbSmBusGroups;
	}

	public void setTbSmBusGroups(Set tbSmBusGroups) {
		this.tbSmBusGroups = tbSmBusGroups;
	}

	public Set getTbSmOpBuses() {
		return this.tbSmOpBuses;
	}

	public void setTbSmOpBuses(Set tbSmOpBuses) {
		this.tbSmOpBuses = tbSmOpBuses;
	}

}