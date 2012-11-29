package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbBiCompany entity. @author MyEclipse Persistence Tools
 */

public class TbBiCompany implements java.io.Serializable {

	// Fields

	private String companyCode;
	private String groupCode;
	private String companyName;
	private String unitcode;
	private String state;
	private String address;
	private String contact;
	private String contactnum;
	private String cardno;
	private String phone;
	private String creatDate;
	private BigDecimal packageId;
	private String channelCode;
	private String fundType;
	private String terminalid;
	private String busiman;
	private String techman;
	private String remark;
	private String menuFlag;
	private String programFlag;
	private String paraFlag;
	private String filepath;
	private String factory;
	private String acountname;
	private String auid;
	private String isadd;
	private String checkstat;
	private String location;
	private String payType;
	private String resaccno;

	// Constructors

	/** default constructor */
	public TbBiCompany() {
	}

	/** minimal constructor */
	public TbBiCompany(String companyCode) {
		this.companyCode = companyCode;
	}

	/** full constructor */
	public TbBiCompany(String companyCode, String groupCode,
			String companyName, String unitcode, String state, String address,
			String contact, String contactnum, String cardno, String phone,
			String creatDate, BigDecimal packageId, String channelCode,
			String fundType, String terminalid, String busiman, String techman,
			String remark, String menuFlag, String programFlag,
			String paraFlag, String filepath, String factory,
			String acountname, String auid, String isadd, String checkstat,
			String location, String payType, String resaccno) {
		this.companyCode = companyCode;
		this.groupCode = groupCode;
		this.companyName = companyName;
		this.unitcode = unitcode;
		this.state = state;
		this.address = address;
		this.contact = contact;
		this.contactnum = contactnum;
		this.cardno = cardno;
		this.phone = phone;
		this.creatDate = creatDate;
		this.packageId = packageId;
		this.channelCode = channelCode;
		this.fundType = fundType;
		this.terminalid = terminalid;
		this.busiman = busiman;
		this.techman = techman;
		this.remark = remark;
		this.menuFlag = menuFlag;
		this.programFlag = programFlag;
		this.paraFlag = paraFlag;
		this.filepath = filepath;
		this.factory = factory;
		this.acountname = acountname;
		this.auid = auid;
		this.isadd = isadd;
		this.checkstat = checkstat;
		this.location = location;
		this.payType = payType;
		this.resaccno = resaccno;
	}

	// Property accessors

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactnum() {
		return this.contactnum;
	}

	public void setContactnum(String contactnum) {
		this.contactnum = contactnum;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public BigDecimal getPackageId() {
		return this.packageId;
	}

	public void setPackageId(BigDecimal packageId) {
		this.packageId = packageId;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getFundType() {
		return this.fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getTerminalid() {
		return this.terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	public String getBusiman() {
		return this.busiman;
	}

	public void setBusiman(String busiman) {
		this.busiman = busiman;
	}

	public String getTechman() {
		return this.techman;
	}

	public void setTechman(String techman) {
		this.techman = techman;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMenuFlag() {
		return this.menuFlag;
	}

	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}

	public String getProgramFlag() {
		return this.programFlag;
	}

	public void setProgramFlag(String programFlag) {
		this.programFlag = programFlag;
	}

	public String getParaFlag() {
		return this.paraFlag;
	}

	public void setParaFlag(String paraFlag) {
		this.paraFlag = paraFlag;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getAcountname() {
		return this.acountname;
	}

	public void setAcountname(String acountname) {
		this.acountname = acountname;
	}

	public String getAuid() {
		return this.auid;
	}

	public void setAuid(String auid) {
		this.auid = auid;
	}

	public String getIsadd() {
		return this.isadd;
	}

	public void setIsadd(String isadd) {
		this.isadd = isadd;
	}

	public String getCheckstat() {
		return this.checkstat;
	}

	public void setCheckstat(String checkstat) {
		this.checkstat = checkstat;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getResaccno() {
		return this.resaccno;
	}

	public void setResaccno(String resaccno) {
		this.resaccno = resaccno;
	}

}