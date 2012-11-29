package com.nantian.npbs.business.model;

/**
 * TbSmPsamCradInfo entity. @author MyEclipse Persistence Tools
 */

public class TbSmPsamCradInfo implements java.io.Serializable {

	// Fields

	private String cardNo;
	private String groupNo;
	private String currentOrg;
	private String bindStatus;
	private String isValid;
	private String onWay;

	// Constructors

	/** default constructor */
	public TbSmPsamCradInfo() {
	}

	/** minimal constructor */
	public TbSmPsamCradInfo(String cardNo) {
		this.cardNo = cardNo;
	}

	/** full constructor */
	public TbSmPsamCradInfo(String cardNo, String groupNo, String currentOrg,
			String bindStatus, String isValid, String onWay) {
		this.cardNo = cardNo;
		this.groupNo = groupNo;
		this.currentOrg = currentOrg;
		this.bindStatus = bindStatus;
		this.isValid = isValid;
		this.onWay = onWay;
	}

	// Property accessors

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getCurrentOrg() {
		return this.currentOrg;
	}

	public void setCurrentOrg(String currentOrg) {
		this.currentOrg = currentOrg;
	}

	public String getBindStatus() {
		return this.bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getOnWay() {
		return this.onWay;
	}

	public void setOnWay(String onWay) {
		this.onWay = onWay;
	}

}