package com.nantian.npbs.business.model;

/**
 * TbSmParaitem entity. @author MyEclipse Persistence Tools
 */

public class TbSmParaitem implements java.io.Serializable {

	// Fields

	private TbSmParaitemId id;
	private String paraName;
	private String paraValue;
	private String paraDepict;
	private String paraState;
	private String creatDate;

	// Constructors

	/** default constructor */
	public TbSmParaitem() {
	}

	/** minimal constructor */
	public TbSmParaitem(TbSmParaitemId id) {
		this.id = id;
	}

	/** full constructor */
	public TbSmParaitem(TbSmParaitemId id, String paraName, String paraValue,
			String paraDepict, String paraState, String creatDate) {
		this.id = id;
		this.paraName = paraName;
		this.paraValue = paraValue;
		this.paraDepict = paraDepict;
		this.paraState = paraState;
		this.creatDate = creatDate;
	}

	// Property accessors

	public TbSmParaitemId getId() {
		return this.id;
	}

	public void setId(TbSmParaitemId id) {
		this.id = id;
	}

	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue() {
		return this.paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	public String getParaDepict() {
		return this.paraDepict;
	}

	public void setParaDepict(String paraDepict) {
		this.paraDepict = paraDepict;
	}

	public String getParaState() {
		return this.paraState;
	}

	public void setParaState(String paraState) {
		this.paraState = paraState;
	}

	public String getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

}