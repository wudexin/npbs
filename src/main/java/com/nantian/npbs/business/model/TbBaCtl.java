package com.nantian.npbs.business.model;

/**
 * TbBaCtl entity. @author MyEclipse Persistence Tools
 */

public class TbBaCtl implements java.io.Serializable {

	// Fields

	private String id;
	private String batName;
	private String batNo;
	private String batStat;
	private String preBatNo;
	private String batCmd;
	private String batDate;
	private String batInstance;

	// Constructors

	/** default constructor */
	public TbBaCtl() {
	}

	/** minimal constructor */
	public TbBaCtl(String id) {
		this.id = id;
	}

	/** full constructor */
	public TbBaCtl(String id, String batName, String batNo, String batStat,
			String preBatNo, String batCmd, String batDate, String batInstance) {
		this.id = id;
		this.batName = batName;
		this.batNo = batNo;
		this.batStat = batStat;
		this.preBatNo = preBatNo;
		this.batCmd = batCmd;
		this.batDate = batDate;
		this.batInstance = batInstance;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatName() {
		return this.batName;
	}

	public void setBatName(String batName) {
		this.batName = batName;
	}

	public String getBatNo() {
		return this.batNo;
	}

	public void setBatNo(String batNo) {
		this.batNo = batNo;
	}

	public String getBatStat() {
		return this.batStat;
	}

	public void setBatStat(String batStat) {
		this.batStat = batStat;
	}

	public String getPreBatNo() {
		return this.preBatNo;
	}

	public void setPreBatNo(String preBatNo) {
		this.preBatNo = preBatNo;
	}

	public String getBatCmd() {
		return this.batCmd;
	}

	public void setBatCmd(String batCmd) {
		this.batCmd = batCmd;
	}

	public String getBatDate() {
		return this.batDate;
	}

	public void setBatDate(String batDate) {
		this.batDate = batDate;
	}

	public String getBatInstance() {
		return this.batInstance;
	}

	public void setBatInstance(String batInstance) {
		this.batInstance = batInstance;
	}

}