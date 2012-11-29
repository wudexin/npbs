package com.nantian.npbs.business.model;

import java.util.HashSet;
import java.util.Set;

/**
 * TbSmOperpower entity. @author MyEclipse Persistence Tools
 */

public class TbSmOperpower implements java.io.Serializable {

	// Fields

	private String operateCode;
	private String depict;
	private String powerName;
	private String state;
	private Set tbSmRolePowers = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSmOperpower() {
	}

	/** minimal constructor */
	public TbSmOperpower(String operateCode) {
		this.operateCode = operateCode;
	}

	/** full constructor */
	public TbSmOperpower(String operateCode, String depict, String powerName,
			String state, Set tbSmRolePowers) {
		this.operateCode = operateCode;
		this.depict = depict;
		this.powerName = powerName;
		this.state = state;
		this.tbSmRolePowers = tbSmRolePowers;
	}

	// Property accessors

	public String getOperateCode() {
		return this.operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getDepict() {
		return this.depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}

	public String getPowerName() {
		return this.powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set getTbSmRolePowers() {
		return this.tbSmRolePowers;
	}

	public void setTbSmRolePowers(Set tbSmRolePowers) {
		this.tbSmRolePowers = tbSmRolePowers;
	}

}