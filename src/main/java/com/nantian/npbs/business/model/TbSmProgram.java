package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmProgram entity. @author MyEclipse Persistence Tools
 */

public class TbSmProgram implements java.io.Serializable {

	// Fields
	private BigDecimal id;
	private String channelCode;
	private String createTime;
	private String fileDesc;
	private String remark;
	private String filepath;
	private String isForce;
	private String md5;
	private String factory;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
}