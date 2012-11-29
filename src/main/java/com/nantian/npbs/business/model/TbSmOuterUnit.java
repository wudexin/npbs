package com.nantian.npbs.business.model;

/**
 * TbSmOuterUnit entity. @author MyEclipse Persistence Tools
 */

public class TbSmOuterUnit implements java.io.Serializable {

	// Fields

	private TbSmOuterUnitId id;
	private String outUnitname;
	private String mapcode;

	// Constructors

	/** default constructor */
	public TbSmOuterUnit() {
	}

	/** minimal constructor */
	public TbSmOuterUnit(TbSmOuterUnitId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmOuterUnit(TbSmOuterUnitId id, String outUnitname, String mapcode) {
		this.id = id;
		this.outUnitname = outUnitname;
		this.mapcode = mapcode;
	}

	// Property accessors

	public TbSmOuterUnitId getId() {
		return this.id;
	}

	public void setId(TbSmOuterUnitId id) {
		this.id = id;
	}

	public String getOutUnitname() {
		return this.outUnitname;
	}

	public void setOutUnitname(String outUnitname) {
		this.outUnitname = outUnitname;
	}

	public String getMapcode() {
		return this.mapcode;
	}

	public void setMapcode(String mapcode) {
		this.mapcode = mapcode;
	}

}