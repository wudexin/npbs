package com.nantian.npbs.business.model;

/**
 * TbSmPsamCompanyRef entity. @author MyEclipse Persistence Tools
 */

public class TbSmPsamCompanyRef implements java.io.Serializable {

	// Fields

	private String cardNo;
	private String companyCode;
	private String cardHealth;

	// Constructors

	/** default constructor */
	public TbSmPsamCompanyRef() {
	}

	/** minimal constructor */
	public TbSmPsamCompanyRef(String cardNo, String companyCode) {
		this.cardNo = cardNo;
		this.companyCode = companyCode;
	}

	/** full constructor */
	public TbSmPsamCompanyRef(String cardNo, String companyCode,
			String cardHealth) {
		this.cardNo = cardNo;
		this.companyCode = companyCode;
		this.cardHealth = cardHealth;
	}

	// Property accessors

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCardHealth() {
		return this.cardHealth;
	}

	public void setCardHealth(String cardHealth) {
		this.cardHealth = cardHealth;
	}

}