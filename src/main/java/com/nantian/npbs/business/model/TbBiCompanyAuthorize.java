package com.nantian.npbs.business.model;

/**
 * TbBiCompanyAuthorize entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompanyAuthorize implements java.io.Serializable {

	// Fields

	private TbBiCompanyAuthorizeId id;
	private Double amountmax;
	private String authDate;
	private String remark;
	private String channelCode;

	// Constructors

	/** default constructor */
	public TbBiCompanyAuthorize() {
	}

	/** minimal constructor */
	public TbBiCompanyAuthorize(TbBiCompanyAuthorizeId id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiCompanyAuthorize(TbBiCompanyAuthorizeId id, Double amountmax,
			String authDate, String remark, String channelCode) {
		this.id = id;
		this.amountmax = amountmax;
		this.authDate = authDate;
		this.remark = remark;
		this.channelCode = channelCode;
	}

	// Property accessors

	public TbBiCompanyAuthorizeId getId() {
		return this.id;
	}

	public void setId(TbBiCompanyAuthorizeId id) {
		this.id = id;
	}

	public Double getAmountmax() {
		return this.amountmax;
	}

	public void setAmountmax(Double amountmax) {
		this.amountmax = amountmax;
	}

	public String getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

}