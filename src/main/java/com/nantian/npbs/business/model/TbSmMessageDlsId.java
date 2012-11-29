package com.nantian.npbs.business.model;

/**
 * TbSmMessageDlsId entity. @author MyEclipse Persistence Tools
 */

public class TbSmMessageDlsId implements java.io.Serializable {

	// Fields

	private Long dlsid;
	private Long id;

	// Constructors

	/** default constructor */
	public TbSmMessageDlsId() {
	}

	/** full constructor */
	public TbSmMessageDlsId(Long dlsid, Long id) {
		this.dlsid = dlsid;
		this.id = id;
	}

	// Property accessors

	public Long getDlsid() {
		return this.dlsid;
	}

	public void setDlsid(Long dlsid) {
		this.dlsid = dlsid;
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
		if (!(other instanceof TbSmMessageDlsId))
			return false;
		TbSmMessageDlsId castOther = (TbSmMessageDlsId) other;

		return ((this.getDlsid() == castOther.getDlsid()) || (this.getDlsid() != null
				&& castOther.getDlsid() != null && this.getDlsid().equals(
				castOther.getDlsid())))
				&& ((this.getId() == castOther.getId()) || (this.getId() != null
						&& castOther.getId() != null && this.getId().equals(
						castOther.getId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDlsid() == null ? 0 : this.getDlsid().hashCode());
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		return result;
	}

}