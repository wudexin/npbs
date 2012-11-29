package com.nantian.npbs.business.model;

/**
 * TbSmInterouter entity. @author MyEclipse Persistence Tools
 */

public class TbSmInterouter implements java.io.Serializable {

	// Fields

	private TbSmInterouterId id;
	private String outUnitcode;

	// Constructors

	/** default constructor */
	public TbSmInterouter() {
	}

	/** full constructor */
	public TbSmInterouter(TbSmInterouterId id, String outUnitcode) {
		this.id = id;
		this.outUnitcode = outUnitcode;
	}

	// Property accessors

	public TbSmInterouterId getId() {
		return this.id;
	}

	public void setId(TbSmInterouterId id) {
		this.id = id;
	}

	public String getOutUnitcode() {
		return this.outUnitcode;
	}

	public void setOutUnitcode(String outUnitcode) {
		this.outUnitcode = outUnitcode;
	}

}