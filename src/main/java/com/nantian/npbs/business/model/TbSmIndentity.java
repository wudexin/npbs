package com.nantian.npbs.business.model;

/**
 * TbSmIndentity entity. @author MyEclipse Persistence Tools
 */

public class TbSmIndentity implements java.io.Serializable {

	// Fields

	private String indentityCode;
	private String indentityName;

	// Constructors

	/** default constructor */
	public TbSmIndentity() {
	}

	/** minimal constructor */
	public TbSmIndentity(String indentityCode) {
		this.indentityCode = indentityCode;
	}

	/** full constructor */
	public TbSmIndentity(String indentityCode, String indentityName) {
		this.indentityCode = indentityCode;
		this.indentityName = indentityName;
	}

	// Property accessors

	public String getIndentityCode() {
		return this.indentityCode;
	}

	public void setIndentityCode(String indentityCode) {
		this.indentityCode = indentityCode;
	}

	public String getIndentityName() {
		return this.indentityName;
	}

	public void setIndentityName(String indentityName) {
		this.indentityName = indentityName;
	}

}