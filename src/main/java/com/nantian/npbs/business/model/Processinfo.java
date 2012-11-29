package com.nantian.npbs.business.model;

/**
 * Processinfo entity. @author MyEclipse Persistence Tools
 */

public class Processinfo implements java.io.Serializable {

	// Fields

	private ProcessinfoId id;

	// Constructors

	/** default constructor */
	public Processinfo() {
	}

	/** full constructor */
	public Processinfo(ProcessinfoId id) {
		this.id = id;
	}

	// Property accessors

	public ProcessinfoId getId() {
		return this.id;
	}

	public void setId(ProcessinfoId id) {
		this.id = id;
	}

}