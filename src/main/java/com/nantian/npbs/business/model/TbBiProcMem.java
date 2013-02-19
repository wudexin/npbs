package com.nantian.npbs.business.model;

/**
 * TbBiBusiness entity. @author MyEclipse Persistence Tools
 */

public class TbBiProcMem implements java.io.Serializable {

	// Fields

	private String pid;
	private String busi_code;
	private String unitcode;
	private String c1;
	private String c2;
 
	public String getPid() {
		return pid;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public String getUnitcode() {
		return unitcode;
	}
	public String getC1() {
		return c1;
	}
	public String getC2() {
		return c2;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}
	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	public void setC1(String c1) {
		this.c1 = c1;
	}
	public void setC2(String c2) {
		this.c2 = c2;
	}
 
}