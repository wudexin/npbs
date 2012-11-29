package com.nantian.npbs.business.model;

/**
 * TbBiMenuId entity. @author MyEclipse Persistence Tools
 */

public class TbBiMenuId implements java.io.Serializable {

	// Fields

	private String menuCode;
	private String upMenucode;
	private String grade;
	private String menuName;
	private String isBusiness;
	private String busiCode;
	private String inputType;
	private String inputLen;
	private String strType;
	private String remark;
	private Byte bytelen;

	// Constructors

	/** default constructor */
	public TbBiMenuId() {
	}

	/** minimal constructor */
	public TbBiMenuId(String menuCode) {
		this.menuCode = menuCode;
	}

	/** full constructor */
	public TbBiMenuId(String menuCode, String upMenucode, String grade,
			String menuName, String isBusiness, String busiCode,
			String inputType, String inputLen, String strType, String remark,
			Byte bytelen) {
		this.menuCode = menuCode;
		this.upMenucode = upMenucode;
		this.grade = grade;
		this.menuName = menuName;
		this.isBusiness = isBusiness;
		this.busiCode = busiCode;
		this.inputType = inputType;
		this.inputLen = inputLen;
		this.strType = strType;
		this.remark = remark;
		this.bytelen = bytelen;
	}

	// Property accessors

	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getUpMenucode() {
		return this.upMenucode;
	}

	public void setUpMenucode(String upMenucode) {
		this.upMenucode = upMenucode;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getIsBusiness() {
		return this.isBusiness;
	}

	public void setIsBusiness(String isBusiness) {
		this.isBusiness = isBusiness;
	}

	public String getBusiCode() {
		return this.busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getInputType() {
		return this.inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getInputLen() {
		return this.inputLen;
	}

	public void setInputLen(String inputLen) {
		this.inputLen = inputLen;
	}

	public String getStrType() {
		return this.strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getBytelen() {
		return this.bytelen;
	}

	public void setBytelen(Byte bytelen) {
		this.bytelen = bytelen;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiMenuId))
			return false;
		TbBiMenuId castOther = (TbBiMenuId) other;

		return ((this.getMenuCode() == castOther.getMenuCode()) || (this
				.getMenuCode() != null
				&& castOther.getMenuCode() != null && this.getMenuCode()
				.equals(castOther.getMenuCode())))
				&& ((this.getUpMenucode() == castOther.getUpMenucode()) || (this
						.getUpMenucode() != null
						&& castOther.getUpMenucode() != null && this
						.getUpMenucode().equals(castOther.getUpMenucode())))
				&& ((this.getGrade() == castOther.getGrade()) || (this
						.getGrade() != null
						&& castOther.getGrade() != null && this.getGrade()
						.equals(castOther.getGrade())))
				&& ((this.getMenuName() == castOther.getMenuName()) || (this
						.getMenuName() != null
						&& castOther.getMenuName() != null && this
						.getMenuName().equals(castOther.getMenuName())))
				&& ((this.getIsBusiness() == castOther.getIsBusiness()) || (this
						.getIsBusiness() != null
						&& castOther.getIsBusiness() != null && this
						.getIsBusiness().equals(castOther.getIsBusiness())))
				&& ((this.getBusiCode() == castOther.getBusiCode()) || (this
						.getBusiCode() != null
						&& castOther.getBusiCode() != null && this
						.getBusiCode().equals(castOther.getBusiCode())))
				&& ((this.getInputType() == castOther.getInputType()) || (this
						.getInputType() != null
						&& castOther.getInputType() != null && this
						.getInputType().equals(castOther.getInputType())))
				&& ((this.getInputLen() == castOther.getInputLen()) || (this
						.getInputLen() != null
						&& castOther.getInputLen() != null && this
						.getInputLen().equals(castOther.getInputLen())))
				&& ((this.getStrType() == castOther.getStrType()) || (this
						.getStrType() != null
						&& castOther.getStrType() != null && this.getStrType()
						.equals(castOther.getStrType())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
				&& ((this.getBytelen() == castOther.getBytelen()) || (this
						.getBytelen() != null
						&& castOther.getBytelen() != null && this.getBytelen()
						.equals(castOther.getBytelen())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuCode() == null ? 0 : this.getMenuCode().hashCode());
		result = 37
				* result
				+ (getUpMenucode() == null ? 0 : this.getUpMenucode()
						.hashCode());
		result = 37 * result
				+ (getGrade() == null ? 0 : this.getGrade().hashCode());
		result = 37 * result
				+ (getMenuName() == null ? 0 : this.getMenuName().hashCode());
		result = 37
				* result
				+ (getIsBusiness() == null ? 0 : this.getIsBusiness()
						.hashCode());
		result = 37 * result
				+ (getBusiCode() == null ? 0 : this.getBusiCode().hashCode());
		result = 37 * result
				+ (getInputType() == null ? 0 : this.getInputType().hashCode());
		result = 37 * result
				+ (getInputLen() == null ? 0 : this.getInputLen().hashCode());
		result = 37 * result
				+ (getStrType() == null ? 0 : this.getStrType().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37 * result
				+ (getBytelen() == null ? 0 : this.getBytelen().hashCode());
		return result;
	}

}