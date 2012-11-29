package com.nantian.npbs.business.model;

/**
 * TbSmMessageOperId entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessageOperId implements java.io.Serializable {

	// Fields

	private Long id;
	private String operCode;

	// Constructors

	/** default constructor */
	public TbSmMessageOperId() {
	}

	/** full constructor */
	public TbSmMessageOperId(Long id, String operCode) {
		this.id = id;
		this.operCode = operCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperCode() {
		return this.operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmMessageOperId))
			return false;
		TbSmMessageOperId castOther = (TbSmMessageOperId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getOperCode() == castOther.getOperCode()) || (this
						.getOperCode() != null
						&& castOther.getOperCode() != null && this
						.getOperCode().equals(castOther.getOperCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getOperCode() == null ? 0 : this.getOperCode().hashCode());
		return result;
	}

}