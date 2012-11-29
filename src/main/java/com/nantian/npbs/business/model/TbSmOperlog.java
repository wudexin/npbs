package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmOperlog entity. @author MyEclipse Persistence Tools
 */

public class TbSmOperlog implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private String dealTime;
	private String noteCode;
	private String noteType;
	private String operateCode;
	private String promptCode;
	private String operCode;
	private String loginip;

	// Constructors

	/** default constructor */
	public TbSmOperlog() {
	}

	/** minimal constructor */
	public TbSmOperlog(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmOperlog(BigDecimal id, String dealTime, String noteCode,
			String noteType, String operateCode, String promptCode,
			String operCode, String loginip) {
		this.id = id;
		this.dealTime = dealTime;
		this.noteCode = noteCode;
		this.noteType = noteType;
		this.operateCode = operateCode;
		this.promptCode = promptCode;
		this.operCode = operCode;
		this.loginip = loginip;
	}

	// Property accessors

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getNoteCode() {
		return this.noteCode;
	}

	public void setNoteCode(String noteCode) {
		this.noteCode = noteCode;
	}

	public String getNoteType() {
		return this.noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getOperateCode() {
		return this.operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getPromptCode() {
		return this.promptCode;
	}

	public void setPromptCode(String promptCode) {
		this.promptCode = promptCode;
	}

	public String getOperCode() {
		return this.operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

}