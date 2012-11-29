package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * ProcessinfoId entity. @author MyEclipse Persistence Tools
 */

public class ProcessinfoId implements java.io.Serializable {

	// Fields

	private BigDecimal pid;
	private String pname;
	private String businesstype;
	private String businessname;
	private String pstatus;

	// Constructors

	/** default constructor */
	public ProcessinfoId() {
	}

	/** full constructor */
	public ProcessinfoId(BigDecimal pid, String pname, String businesstype,
			String businessname, String pstatus) {
		this.pid = pid;
		this.pname = pname;
		this.businesstype = businesstype;
		this.businessname = businessname;
		this.pstatus = pstatus;
	}

	// Property accessors

	public BigDecimal getPid() {
		return this.pid;
	}

	public void setPid(BigDecimal pid) {
		this.pid = pid;
	}

	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getBusinesstype() {
		return this.businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getBusinessname() {
		return this.businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}

	public String getPstatus() {
		return this.pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProcessinfoId))
			return false;
		ProcessinfoId castOther = (ProcessinfoId) other;

		return ((this.getPid() == castOther.getPid()) || (this.getPid() != null
				&& castOther.getPid() != null && this.getPid().equals(
				castOther.getPid())))
				&& ((this.getPname() == castOther.getPname()) || (this
						.getPname() != null
						&& castOther.getPname() != null && this.getPname()
						.equals(castOther.getPname())))
				&& ((this.getBusinesstype() == castOther.getBusinesstype()) || (this
						.getBusinesstype() != null
						&& castOther.getBusinesstype() != null && this
						.getBusinesstype().equals(castOther.getBusinesstype())))
				&& ((this.getBusinessname() == castOther.getBusinessname()) || (this
						.getBusinessname() != null
						&& castOther.getBusinessname() != null && this
						.getBusinessname().equals(castOther.getBusinessname())))
				&& ((this.getPstatus() == castOther.getPstatus()) || (this
						.getPstatus() != null
						&& castOther.getPstatus() != null && this.getPstatus()
						.equals(castOther.getPstatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPid() == null ? 0 : this.getPid().hashCode());
		result = 37 * result
				+ (getPname() == null ? 0 : this.getPname().hashCode());
		result = 37
				* result
				+ (getBusinesstype() == null ? 0 : this.getBusinesstype()
						.hashCode());
		result = 37
				* result
				+ (getBusinessname() == null ? 0 : this.getBusinessname()
						.hashCode());
		result = 37 * result
				+ (getPstatus() == null ? 0 : this.getPstatus().hashCode());
		return result;
	}

}