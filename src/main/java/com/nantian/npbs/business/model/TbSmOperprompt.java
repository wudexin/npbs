package com.nantian.npbs.business.model;

/**
 * TbSmOperprompt entity. @author MyEclipse Persistence Tools
 */

public class TbSmOperprompt implements java.io.Serializable {

	// Fields

	private String promptCode;
	private String promptMessage;
	private String moditime;
	private String modiOperCode;
	private String operateCode;

	// Constructors

	/** default constructor */
	public TbSmOperprompt() {
	}

	/** minimal constructor */
	public TbSmOperprompt(String promptCode) {
		this.promptCode = promptCode;
	}

	/** full constructor */
	public TbSmOperprompt(String promptCode, String promptMessage,
			String moditime, String modiOperCode, String operateCode) {
		this.promptCode = promptCode;
		this.promptMessage = promptMessage;
		this.moditime = moditime;
		this.modiOperCode = modiOperCode;
		this.operateCode = operateCode;
	}

	// Property accessors

	public String getPromptCode() {
		return this.promptCode;
	}

	public void setPromptCode(String promptCode) {
		this.promptCode = promptCode;
	}

	public String getPromptMessage() {
		return this.promptMessage;
	}

	public void setPromptMessage(String promptMessage) {
		this.promptMessage = promptMessage;
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

	public String getOperateCode() {
		return this.operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

}