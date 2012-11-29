package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmProgramId entity. @author MyEclipse Persistence Tools
 */

public class TbSmProgramId implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public TbSmProgramId() {
	}

	/** minimal constructor */
	public TbSmProgramId(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmProgramId(BigDecimal id, String channelCode, String createTime,
			String fileDesc, String remark, String filepath, String isForce,
			String md5, String factory) {
		this.id = id;
		this.channelCode = channelCode;
		this.createTime = createTime;
		this.fileDesc = fileDesc;
		this.remark = remark;
		this.filepath = filepath;
		this.isForce = isForce;
		this.md5 = md5;
		this.factory = factory;
	}

	// Property accessors

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFileDesc() {
		return this.fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getIsForce() {
		return this.isForce;
	}

	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}

	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmProgramId))
			return false;
		TbSmProgramId castOther = (TbSmProgramId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getChannelCode() == castOther.getChannelCode()) || (this
						.getChannelCode() != null
						&& castOther.getChannelCode() != null && this
						.getChannelCode().equals(castOther.getChannelCode())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getFileDesc() == castOther.getFileDesc()) || (this
						.getFileDesc() != null
						&& castOther.getFileDesc() != null && this
						.getFileDesc().equals(castOther.getFileDesc())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
				&& ((this.getFilepath() == castOther.getFilepath()) || (this
						.getFilepath() != null
						&& castOther.getFilepath() != null && this
						.getFilepath().equals(castOther.getFilepath())))
				&& ((this.getIsForce() == castOther.getIsForce()) || (this
						.getIsForce() != null
						&& castOther.getIsForce() != null && this.getIsForce()
						.equals(castOther.getIsForce())))
				&& ((this.getMd5() == castOther.getMd5()) || (this.getMd5() != null
						&& castOther.getMd5() != null && this.getMd5().equals(
						castOther.getMd5())))
				&& ((this.getFactory() == castOther.getFactory()) || (this
						.getFactory() != null
						&& castOther.getFactory() != null && this.getFactory()
						.equals(castOther.getFactory())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37
				* result
				+ (getChannelCode() == null ? 0 : this.getChannelCode()
						.hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37 * result
				+ (getFileDesc() == null ? 0 : this.getFileDesc().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37 * result
				+ (getFilepath() == null ? 0 : this.getFilepath().hashCode());
		result = 37 * result
				+ (getIsForce() == null ? 0 : this.getIsForce().hashCode());
		result = 37 * result
				+ (getMd5() == null ? 0 : this.getMd5().hashCode());
		result = 37 * result
				+ (getFactory() == null ? 0 : this.getFactory().hashCode());
		return result;
	}

}