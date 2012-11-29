package com.nantian.npbs.business.model;

/**
 * TbBaStaticurl entity. @author MyEclipse Persistence Tools
 */

public class TbBaStaticurl implements java.io.Serializable {

	// Fields

	private String id;
	private String sourceUrl;
	private String targetPath;
	private String targetName;
	private String urlType;
	private String frequency;
	private String modiDate;
	private String fabuTime;

	// Constructors

	/** default constructor */
	public TbBaStaticurl() {
	}

	/** minimal constructor */
	public TbBaStaticurl(String id) {
		this.id = id;
	}

	/** full constructor */
	public TbBaStaticurl(String id, String sourceUrl, String targetPath,
			String targetName, String urlType, String frequency,
			String modiDate, String fabuTime) {
		this.id = id;
		this.sourceUrl = sourceUrl;
		this.targetPath = targetPath;
		this.targetName = targetName;
		this.urlType = urlType;
		this.frequency = frequency;
		this.modiDate = modiDate;
		this.fabuTime = fabuTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getTargetPath() {
		return this.targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getTargetName() {
		return this.targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getUrlType() {
		return this.urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getFabuTime() {
		return this.fabuTime;
	}

	public void setFabuTime(String fabuTime) {
		this.fabuTime = fabuTime;
	}

}