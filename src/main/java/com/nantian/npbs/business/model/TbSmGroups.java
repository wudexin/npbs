package com.nantian.npbs.business.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * TbSmGroups entity. @author MyEclipse Persistence Tools
 */

public class TbSmGroups implements java.io.Serializable {

	// Fields

	private BigDecimal groupId;
	private String groupDis;
	private String moditime;
	private String groupName;
	private String modiOperCode;
	private Set tbSmOpBuses = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSmGroups() {
	}

	/** minimal constructor */
	public TbSmGroups(BigDecimal groupId) {
		this.groupId = groupId;
	}

	/** full constructor */
	public TbSmGroups(BigDecimal groupId, String groupDis, String moditime,
			String groupName, String modiOperCode, Set tbSmOpBuses) {
		this.groupId = groupId;
		this.groupDis = groupDis;
		this.moditime = moditime;
		this.groupName = groupName;
		this.modiOperCode = modiOperCode;
		this.tbSmOpBuses = tbSmOpBuses;
	}

	// Property accessors

	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public String getGroupDis() {
		return this.groupDis;
	}

	public void setGroupDis(String groupDis) {
		this.groupDis = groupDis;
	}

	public String getModitime() {
		return this.moditime;
	}

	public void setModitime(String moditime) {
		this.moditime = moditime;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getModiOperCode() {
		return this.modiOperCode;
	}

	public void setModiOperCode(String modiOperCode) {
		this.modiOperCode = modiOperCode;
	}

	public Set getTbSmOpBuses() {
		return this.tbSmOpBuses;
	}

	public void setTbSmOpBuses(Set tbSmOpBuses) {
		this.tbSmOpBuses = tbSmOpBuses;
	}

}