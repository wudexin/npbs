package com.nantian.npbs.business.model;

/**
 * TbSmInnerUnit entity. @author MyEclipse Persistence Tools
 */

public class TbSmInnerUnit implements java.io.Serializable {

	// Fields

	private String insiUnitcode;
	private String insiUnitname;
	private String state;
	private String upperUnitcode;
	private String prinName;
	private String linkTel;
	private String insiUnitaddr;
	private String post;
	private String onUseDate;
	private String nowGrade;
	private String mapcode;
	private String remark;
	private String insiUnitId;
	private String lastModifydate;
	private String modiOpercode;

	// Constructors

	/** default constructor */
	public TbSmInnerUnit() {
	}

	/** minimal constructor */
	public TbSmInnerUnit(String insiUnitcode, String nowGrade) {
		this.insiUnitcode = insiUnitcode;
		this.nowGrade = nowGrade;
	}

	/** full constructor */
	public TbSmInnerUnit(String insiUnitcode, String insiUnitname,
			String state, String upperUnitcode, String prinName,
			String linkTel, String insiUnitaddr, String post, String onUseDate,
			String nowGrade, String mapcode, String remark, String insiUnitId,
			String lastModifydate, String modiOpercode) {
		this.insiUnitcode = insiUnitcode;
		this.insiUnitname = insiUnitname;
		this.state = state;
		this.upperUnitcode = upperUnitcode;
		this.prinName = prinName;
		this.linkTel = linkTel;
		this.insiUnitaddr = insiUnitaddr;
		this.post = post;
		this.onUseDate = onUseDate;
		this.nowGrade = nowGrade;
		this.mapcode = mapcode;
		this.remark = remark;
		this.insiUnitId = insiUnitId;
		this.lastModifydate = lastModifydate;
		this.modiOpercode = modiOpercode;
	}

	// Property accessors

	public String getInsiUnitcode() {
		return this.insiUnitcode;
	}

	public void setInsiUnitcode(String insiUnitcode) {
		this.insiUnitcode = insiUnitcode;
	}

	public String getInsiUnitname() {
		return this.insiUnitname;
	}

	public void setInsiUnitname(String insiUnitname) {
		this.insiUnitname = insiUnitname;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUpperUnitcode() {
		return this.upperUnitcode;
	}

	public void setUpperUnitcode(String upperUnitcode) {
		this.upperUnitcode = upperUnitcode;
	}

	public String getPrinName() {
		return this.prinName;
	}

	public void setPrinName(String prinName) {
		this.prinName = prinName;
	}

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getInsiUnitaddr() {
		return this.insiUnitaddr;
	}

	public void setInsiUnitaddr(String insiUnitaddr) {
		this.insiUnitaddr = insiUnitaddr;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getOnUseDate() {
		return this.onUseDate;
	}

	public void setOnUseDate(String onUseDate) {
		this.onUseDate = onUseDate;
	}

	public String getNowGrade() {
		return this.nowGrade;
	}

	public void setNowGrade(String nowGrade) {
		this.nowGrade = nowGrade;
	}

	public String getMapcode() {
		return this.mapcode;
	}

	public void setMapcode(String mapcode) {
		this.mapcode = mapcode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInsiUnitId() {
		return this.insiUnitId;
	}

	public void setInsiUnitId(String insiUnitId) {
		this.insiUnitId = insiUnitId;
	}

	public String getLastModifydate() {
		return this.lastModifydate;
	}

	public void setLastModifydate(String lastModifydate) {
		this.lastModifydate = lastModifydate;
	}

	public String getModiOpercode() {
		return this.modiOpercode;
	}

	public void setModiOpercode(String modiOpercode) {
		this.modiOpercode = modiOpercode;
	}

}