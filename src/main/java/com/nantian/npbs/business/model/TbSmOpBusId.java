package com.nantian.npbs.business.model;

/**
 * TbSmOpBusId entity. @author MyEclipse Persistence Tools
 */

public class TbSmOpBusId implements java.io.Serializable {

	// Fields

	private TbSmBusgroups tbSmBusgroups;
	private TbSmGroups tbSmGroups;

	// Constructors

	/** default constructor */
	public TbSmOpBusId() {
	}

	/** full constructor */
	public TbSmOpBusId(TbSmBusgroups tbSmBusgroups, TbSmGroups tbSmGroups) {
		this.tbSmBusgroups = tbSmBusgroups;
		this.tbSmGroups = tbSmGroups;
	}

	// Property accessors

	public TbSmBusgroups getTbSmBusgroups() {
		return this.tbSmBusgroups;
	}

	public void setTbSmBusgroups(TbSmBusgroups tbSmBusgroups) {
		this.tbSmBusgroups = tbSmBusgroups;
	}

	public TbSmGroups getTbSmGroups() {
		return this.tbSmGroups;
	}

	public void setTbSmGroups(TbSmGroups tbSmGroups) {
		this.tbSmGroups = tbSmGroups;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbSmOpBusId))
			return false;
		TbSmOpBusId castOther = (TbSmOpBusId) other;

		return ((this.getTbSmBusgroups() == castOther.getTbSmBusgroups()) || (this
				.getTbSmBusgroups() != null
				&& castOther.getTbSmBusgroups() != null && this
				.getTbSmBusgroups().equals(castOther.getTbSmBusgroups())))
				&& ((this.getTbSmGroups() == castOther.getTbSmGroups()) || (this
						.getTbSmGroups() != null
						&& castOther.getTbSmGroups() != null && this
						.getTbSmGroups().equals(castOther.getTbSmGroups())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTbSmBusgroups() == null ? 0 : this.getTbSmBusgroups()
						.hashCode());
		result = 37
				* result
				+ (getTbSmGroups() == null ? 0 : this.getTbSmGroups()
						.hashCode());
		return result;
	}

}