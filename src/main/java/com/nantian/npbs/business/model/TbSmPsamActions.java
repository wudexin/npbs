package com.nantian.npbs.business.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TbSmPsamActions entity. @author MyEclipse Persistence Tools
 */

public class TbSmPsamActions implements java.io.Serializable {

	// Fields

	private String id;
	private String groupNo;
	private String startNo;
	private String endNo;
	private BigDecimal totalNo;
	private String currentOrg;
	private String outOrg;
	private String actionType;
	private String isIncept;
	private String upType;
	private Date startDate;
	private String explainInfo;

	// Constructors

	/** default constructor */
	public TbSmPsamActions() {
	}

	/** minimal constructor */
	public TbSmPsamActions(String id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmPsamActions(String id, String groupNo, String startNo,
			String endNo, BigDecimal totalNo, String currentOrg, String outOrg,
			String actionType, String isIncept, String upType, Date startDate,
			String explainInfo) {
		this.id = id;
		this.groupNo = groupNo;
		this.startNo = startNo;
		this.endNo = endNo;
		this.totalNo = totalNo;
		this.currentOrg = currentOrg;
		this.outOrg = outOrg;
		this.actionType = actionType;
		this.isIncept = isIncept;
		this.upType = upType;
		this.startDate = startDate;
		this.explainInfo = explainInfo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getStartNo() {
		return this.startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public String getEndNo() {
		return this.endNo;
	}

	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	public BigDecimal getTotalNo() {
		return this.totalNo;
	}

	public void setTotalNo(BigDecimal totalNo) {
		this.totalNo = totalNo;
	}

	public String getCurrentOrg() {
		return this.currentOrg;
	}

	public void setCurrentOrg(String currentOrg) {
		this.currentOrg = currentOrg;
	}

	public String getOutOrg() {
		return this.outOrg;
	}

	public void setOutOrg(String outOrg) {
		this.outOrg = outOrg;
	}

	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getIsIncept() {
		return this.isIncept;
	}

	public void setIsIncept(String isIncept) {
		this.isIncept = isIncept;
	}

	public String getUpType() {
		return this.upType;
	}

	public void setUpType(String upType) {
		this.upType = upType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getExplainInfo() {
		return this.explainInfo;
	}

	public void setExplainInfo(String explainInfo) {
		this.explainInfo = explainInfo;
	}

}