package com.nantian.npbs.business.model;

/**
 * TbSmParaitemId entity. @author MyEclipse Persistence Tools
 */

public class TbSmParaitemId implements java.io.Serializable {

	// Fields

	private String paraCode;
	private String paraValuename;

	// Constructors

	/** default constructor */
	public TbSmParaitemId() {
	}

	/** full constructor */
	public TbSmParaitemId(String paraCode, String paraValuename) {
		this.paraCode = paraCode;
		this.paraValuename = paraValuename;
	}

	// Property accessors

	public String getParaCode() {
		return this.paraCode;
	}

	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}

	public String getParaValuename() {
		return this.paraValuename;
	}

	public void setParaValuename(String paraValuename) {
		this.paraValuename = paraValuename;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmParaitemId))
			return false;
		TbSmParaitemId castOther = (TbSmParaitemId) other;

		return ((this.getParaCode() == castOther.getParaCode()) || (this
				.getParaCode() != null
				&& castOther.getParaCode() != null && this.getParaCode()
				.equals(castOther.getParaCode())))
				&& ((this.getParaValuename() == castOther.getParaValuename()) || (this
						.getParaValuename() != null
						&& castOther.getParaValuename() != null && this
						.getParaValuename()
						.equals(castOther.getParaValuename())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getParaCode() == null ? 0 : this.getParaCode().hashCode());
		result = 37
				* result
				+ (getParaValuename() == null ? 0 : this.getParaValuename()
						.hashCode());
		return result;
	}

}