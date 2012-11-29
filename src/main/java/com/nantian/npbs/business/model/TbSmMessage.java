package com.nantian.npbs.business.model;

import java.util.Date;

/**
 * TbSmMessage entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessage implements java.io.Serializable {

	// Fields

	private Long id;
	private String title;
	private String author;
	private Date createDate;
	private Boolean state;
	private Boolean authorType;
	private String context;

	// Constructors

	/** default constructor */
	public TbSmMessage() {
	}

	/** minimal constructor */
	public TbSmMessage(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmMessage(Long id, String title, String author, Date createDate,
			Boolean state, Boolean authorType, String context) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.createDate = createDate;
		this.state = state;
		this.authorType = authorType;
		this.context = context;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Boolean getAuthorType() {
		return this.authorType;
	}

	public void setAuthorType(Boolean authorType) {
		this.authorType = authorType;
	}

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}