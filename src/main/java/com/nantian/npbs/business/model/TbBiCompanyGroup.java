package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbBiCompanyGroup entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyGroup implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private String groupCode;
	private String groupName;
	private String unitcode;
	private String contact;
	private String phone;
	private String remark;

	// Constructors

	/** default constructor */
	public TbBiCompanyGroup() {
	}

	/** minimal constructor */
	public TbBiCompanyGroup(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiCompanyGroup(BigDecimal id, String groupCode, String groupName,
			String unitcode, String contact, String phone, String remark) {
		this.id = id;
		this.groupCode = groupCode;
		this.groupName = groupName;
		this.unitcode = unitcode;
		this.contact = contact;
		this.phone = phone;
		this.remark = remark;
	}

	// Property accessors

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}