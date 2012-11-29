package com.nantian.npbs.business.model;

/**
 * TbBiPubinfo entity. @author MyEclipse Persistence Tools
 */

public class TbBiPubinfo implements java.io.Serializable {

	// Fields

	private String id;
	private String companyCode;
	private String companyName;
	private String address;
	private String contactnum;
	private String contact;
	private String mobile;
	private String messageType;
	private String content;
	private String deal;
	private String isdeal;
	private String createTime;
	private String remark;
	private String sendUnitcode;
	private String dealUnitcode;

	// Constructors

	/** default constructor */
	public TbBiPubinfo() {
	}

	/** minimal constructor */
	public TbBiPubinfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TbBiPubinfo(String id, String companyCode, String companyName,
			String address, String contactnum, String contact, String mobile,
			String messageType, String content, String deal, String isdeal,
			String createTime, String remark, String sendUnitcode,
			String dealUnitcode) {
		this.id = id;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.address = address;
		this.contactnum = contactnum;
		this.contact = contact;
		this.mobile = mobile;
		this.messageType = messageType;
		this.content = content;
		this.deal = deal;
		this.isdeal = isdeal;
		this.createTime = createTime;
		this.remark = remark;
		this.sendUnitcode = sendUnitcode;
		this.dealUnitcode = dealUnitcode;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactnum() {
		return this.contactnum;
	}

	public void setContactnum(String contactnum) {
		this.contactnum = contactnum;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeal() {
		return this.deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getIsdeal() {
		return this.isdeal;
	}

	public void setIsdeal(String isdeal) {
		this.isdeal = isdeal;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSendUnitcode() {
		return this.sendUnitcode;
	}

	public void setSendUnitcode(String sendUnitcode) {
		this.sendUnitcode = sendUnitcode;
	}

	public String getDealUnitcode() {
		return this.dealUnitcode;
	}

	public void setDealUnitcode(String dealUnitcode) {
		this.dealUnitcode = dealUnitcode;
	}

}