package com.nantian.npbs.business.model;

/**
 * TbBiPrepayLowamount entity. @author MyEclipse Persistence Tools
 */

public class TbBiPrepayLowamount implements java.io.Serializable {

	// Fields

	private String accno;
	private Double remindBalance;
	private Byte remindNum;
	private Byte remainNum;
	private String remark;

	// Constructors

	/** default constructor */
	public TbBiPrepayLowamount() {
	}

	/** minimal constructor */
	public TbBiPrepayLowamount(String accno) {
		this.accno = accno;
	}

	/** full constructor */
	public TbBiPrepayLowamount(String accno, Double remindBalance,
			Byte remindNum, Byte remainNum, String remark) {
		this.accno = accno;
		this.remindBalance = remindBalance;
		this.remindNum = remindNum;
		this.remainNum = remainNum;
		this.remark = remark;
	}

	// Property accessors

	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public Double getRemindBalance() {
		return this.remindBalance;
	}

	public void setRemindBalance(Double remindBalance) {
		this.remindBalance = remindBalance;
	}

	public Byte getRemindNum() {
		return this.remindNum;
	}

	public void setRemindNum(Byte remindNum) {
		this.remindNum = remindNum;
	}

	public Byte getRemainNum() {
		return this.remainNum;
	}

	public void setRemainNum(Byte remainNum) {
		this.remainNum = remainNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}