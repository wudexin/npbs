package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmUnitMessagenote entity. @author MyEclipse Persistence Tools
 */

public class TbSmUnitMessagenote implements java.io.Serializable {

	// Fields

	private BigDecimal msgId;
	private BigDecimal msgCreateId;
	private String accpUnitcode;
	private String content;
	private String sendOperCode;
	private String sendUnitcode;
	private String msgState;
	private String sendDate;
	private String title;

	// Constructors

	/** default constructor */
	public TbSmUnitMessagenote() {
	}

	/** minimal constructor */
	public TbSmUnitMessagenote(BigDecimal msgId, BigDecimal msgCreateId,
			String sendUnitcode) {
		this.msgId = msgId;
		this.msgCreateId = msgCreateId;
		this.sendUnitcode = sendUnitcode;
	}

	/** full constructor */
	public TbSmUnitMessagenote(BigDecimal msgId, BigDecimal msgCreateId,
			String accpUnitcode, String content, String sendOperCode,
			String sendUnitcode, String msgState, String sendDate, String title) {
		this.msgId = msgId;
		this.msgCreateId = msgCreateId;
		this.accpUnitcode = accpUnitcode;
		this.content = content;
		this.sendOperCode = sendOperCode;
		this.sendUnitcode = sendUnitcode;
		this.msgState = msgState;
		this.sendDate = sendDate;
		this.title = title;
	}

	// Property accessors

	public BigDecimal getMsgId() {
		return this.msgId;
	}

	public void setMsgId(BigDecimal msgId) {
		this.msgId = msgId;
	}

	public BigDecimal getMsgCreateId() {
		return this.msgCreateId;
	}

	public void setMsgCreateId(BigDecimal msgCreateId) {
		this.msgCreateId = msgCreateId;
	}

	public String getAccpUnitcode() {
		return this.accpUnitcode;
	}

	public void setAccpUnitcode(String accpUnitcode) {
		this.accpUnitcode = accpUnitcode;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendOperCode() {
		return this.sendOperCode;
	}

	public void setSendOperCode(String sendOperCode) {
		this.sendOperCode = sendOperCode;
	}

	public String getSendUnitcode() {
		return this.sendUnitcode;
	}

	public void setSendUnitcode(String sendUnitcode) {
		this.sendUnitcode = sendUnitcode;
	}

	public String getMsgState() {
		return this.msgState;
	}

	public void setMsgState(String msgState) {
		this.msgState = msgState;
	}

	public String getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}