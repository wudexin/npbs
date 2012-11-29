package com.nantian.npbs.business.model;

/**
 * TbBiSallaryPackageFormula entity. @author MyEclipse Persistence Tools
 */

public class TbBiSallaryPackageFormula implements java.io.Serializable {

	// Fields

	private TbBiSallaryPackageFormulaId id;

	// Constructors

	/** default constructor */
	public TbBiSallaryPackageFormula() {
	}

	/** full constructor */
	public TbBiSallaryPackageFormula(TbBiSallaryPackageFormulaId id) {
		this.id = id;
	}

	// Property accessors

	public TbBiSallaryPackageFormulaId getId() {
		return this.id;
	}

	public void setId(TbBiSallaryPackageFormulaId id) {
		this.id = id;
	}

}