package com.nantian.npbs.business.model;

/**
 * TbBiBlacklist entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TbBiBlacklist implements java.io.Serializable {

	// Fields

	private TbBiBlacklistId id;
	private String stat;
	private String endDate;
	private String reason;
	private Byte overdueNum;
	private String remark;

	// Constructors

	/** default constructor */
	public TbBiBlacklist() {
	}

	/** minimal constructor */
	public TbBiBlacklist(TbBiBlacklistId id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiBlacklist(TbBiBlacklistId id,  String stat, String endDate, String reason,
			Byte overdueNum, String remark) {
		this.id = id;
		this.stat = stat;
		this.endDate = endDate;
		this.reason = reason;
		this.overdueNum = overdueNum;
		this.remark = remark;
	}

	// Property accessors

	public TbBiBlacklistId getId() {
		return this.id;
	}

	public void setId(TbBiBlacklistId id) {
		this.id = id;
	}

	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Byte getOverdueNum() {
		return this.overdueNum;
	}

	public void setOverdueNum(Byte overdueNum) {
		this.overdueNum = overdueNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}