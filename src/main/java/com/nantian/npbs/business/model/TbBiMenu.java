package com.nantian.npbs.business.model;

/**
 * TbBiMenu entity. @author MyEclipse Persistence Tools
 */

public class TbBiMenu implements java.io.Serializable {

	private String menuCode;//菜单编号
	private String upMenucode;//上级菜单编号
	private String grade;//本菜单等级
	private String menuName;//本菜单等级
	private String isBusiness;//目录是否为具体业务
	private String busiCode;//所属业务ID
	private String inputType;//输入方式
	private String inputLen;//输入位数
	private String strType;//符类型字
	private String remark;//备注
	private Byte bytelen;//长度
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getUpMenucode() {
		return upMenucode;
	}
	public void setUpMenucode(String upMenucode) {
		this.upMenucode = upMenucode;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getIsBusiness() {
		return isBusiness;
	}
	public void setIsBusiness(String isBusiness) {
		this.isBusiness = isBusiness;
	}
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getInputLen() {
		return inputLen;
	}
	public void setInputLen(String inputLen) {
		this.inputLen = inputLen;
	}
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Byte getBytelen() {
		return bytelen;
	}
	public void setBytelen(Byte bytelen) {
		this.bytelen = bytelen;
	}
}