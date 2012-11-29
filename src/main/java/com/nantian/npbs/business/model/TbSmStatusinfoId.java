package com.nantian.npbs.business.model;

/**
 * TbSmStatusinfoId entity. @author MyEclipse Persistence Tools
 */

public class TbSmStatusinfoId implements java.io.Serializable {

	// Fields

	private String status;
	private String statusType;
	private String statusName;

	// Constructors

	/** default constructor */
	public TbSmStatusinfoId() {
	}

	/** full constructor */
	public TbSmStatusinfoId(String status, String statusType, String statusName) {
		this.status = status;
		this.statusType = statusType;
		this.statusName = statusName;
	}

	// Property accessors

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusType() {
		return this.statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmStatusinfoId))
			return false;
		TbSmStatusinfoId castOther = (TbSmStatusinfoId) other;

		return ((this.getStatus() == castOther.getStatus()) || (this
				.getStatus() != null
				&& castOther.getStatus() != null && this.getStatus().equals(
				castOther.getStatus())))
				&& ((this.getStatusType() == castOther.getStatusType()) || (this
						.getStatusType() != null
						&& castOther.getStatusType() != null && this
						.getStatusType().equals(castOther.getStatusType())))
				&& ((this.getStatusName() == castOther.getStatusName()) || (this
						.getStatusName() != null
						&& castOther.getStatusName() != null && this
						.getStatusName().equals(castOther.getStatusName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37
				* result
				+ (getStatusType() == null ? 0 : this.getStatusType()
						.hashCode());
		result = 37
				* result
				+ (getStatusName() == null ? 0 : this.getStatusName()
						.hashCode());
		return result;
	}

}