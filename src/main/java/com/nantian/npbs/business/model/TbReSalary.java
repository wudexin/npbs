package com.nantian.npbs.business.model;

/**
 * TbReSalary entity. @author MyEclipse Persistence Tools
 */

public class TbReSalary implements java.io.Serializable {

	// Fields

	private TbReSalaryId id;
	private String unitcode;
	private Double salary;
	private String calculateType;
	private Double tax;
	private Double depreciation;
	private Double other;
	private String ispay;
	private String fallreson;
	private String isnormal;

	// Constructors

	/** default constructor */
	public TbReSalary() {
	}

	/** minimal constructor */
	public TbReSalary(TbReSalaryId id, String unitcode) {
		this.id = id;
		this.unitcode = unitcode;
	}

	/** full constructor */
	public TbReSalary(TbReSalaryId id, String unitcode, Double salary,
			String calculateType, Double tax, Double depreciation,
			Double other, String ispay, String fallreson, String isnormal) {
		this.id = id;
		this.unitcode = unitcode;
		this.salary = salary;
		this.calculateType = calculateType;
		this.tax = tax;
		this.depreciation = depreciation;
		this.other = other;
		this.ispay = ispay;
		this.fallreson = fallreson;
		this.isnormal = isnormal;
	}

	// Property accessors

	public TbReSalaryId getId() {
		return this.id;
	}

	public void setId(TbReSalaryId id) {
		this.id = id;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getCalculateType() {
		return this.calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getDepreciation() {
		return this.depreciation;
	}

	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

	public Double getOther() {
		return this.other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

	public String getIspay() {
		return this.ispay;
	}

	public void setIspay(String ispay) {
		this.ispay = ispay;
	}

	public String getFallreson() {
		return this.fallreson;
	}

	public void setFallreson(String fallreson) {
		this.fallreson = fallreson;
	}

	public String getIsnormal() {
		return this.isnormal;
	}

	public void setIsnormal(String isnormal) {
		this.isnormal = isnormal;
	}

}