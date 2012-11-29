package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmRole entity. @author MyEclipse Persistence Tools
 */

public class TbSmRole implements java.io.Serializable {

	// Fields

	private BigDecimal indentityId;
	private String identityGrade;
	private String isIdentityBase;
	private String identityName;
	private String moditime;
	private String modiOperCode;

	// Constructors

	/** default constructor */
	public TbSmRole() {
	}

	/** minimal constructor */
	public TbSmRole(BigDecimal indentityId) {
		this.indentityId = indentityId;
	}

	/** full constructor */
	public TbSmRole(BigDecimal indentityId, String identityGrade,
			String isIdentityBase, String identityName, String moditime,
			String modiOperCode) {
		this.indentityId = indentityId;
		this.identityGrade = identityGrade;
		this.isIdentityBase = isIdentityBase;
		this.identityName = identityName;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
	}

	// Property accessors

	public BigDecimal getIndentityId() {
		return this.indentityId;
	}

	public void setIndentityId(BigDecimal indentityId) {
		this.indentityId = indentityId;
	}

	public String getIdentityGrade() {
		return this.identityGrade;
	}

	public void setIdentityGrade(String identityGrade) {
		this.identityGrade = identityGrade;
	}

	public String getIsIdentityBase() {
		return this.isIdentityBase;
	}

	public void setIsIdentityBase(String isIdentityBase) {
		this.isIdentityBase = isIdentityBase;
	}

	public String getIdentityName() {
		return this.identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
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