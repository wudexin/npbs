package com.nantian.npbs.business.model;

/**
 * TbSmMessageSjId entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessageSjId implements java.io.Serializable {

	// Fields

	private Long sjid;
	private Long id;

	// Constructors

	/** default constructor */
	public TbSmMessageSjId() {
	}

	/** full constructor */
	public TbSmMessageSjId(Long sjid, Long id) {
		this.sjid = sjid;
		this.id = id;
	}

	// Property accessors

	public Long getSjid() {
		return this.sjid;
	}

	public void setSjid(Long sjid) {
		this.sjid = sjid;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmMessageSjId))
			return false;
		TbSmMessageSjId castOther = (TbSmMessageSjId) other;

		return ((this.getSjid() == castOther.getSjid()) || (this.getSjid() != null
				&& castOther.getSjid() != null && this.getSjid().equals(
				castOther.getSjid())))
				&& ((this.getId() == castOther.getId()) || (this.getId() != null
						&& castOther.getId() != null && this.getId().equals(
						castOther.getId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSjid() == null ? 0 : this.getSjid().hashCode());
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		return result;
	}

}