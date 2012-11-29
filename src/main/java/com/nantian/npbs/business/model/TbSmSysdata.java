package com.nantian.npbs.business.model;

/**
 * TbSmSysdata entity. @author MyEclipse Persistence Tools
 */

public class TbSmSysdata implements java.io.Serializable {

	// Fields

	private TbSmSysdataId id;

	private String systemStatus;
	
	/** default constructor */
	public TbSmSysdata() {
	}

	/** full constructor */
	public TbSmSysdata(TbSmSysdataId id) {
		this.id = id;
	}

	// Property accessors

	public TbSmSysdataId getId() {
		return this.id;
	}

	public void setId(TbSmSysdataId id) {
		this.id = id;
	}

	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

}