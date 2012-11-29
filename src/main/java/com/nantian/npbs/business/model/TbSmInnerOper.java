package com.nantian.npbs.business.model;

import java.math.BigDecimal;

/**
 * TbSmInnerOper entity. @author MyEclipse Persistence Tools
 */

public class TbSmInnerOper implements java.io.Serializable {

	// Fields

	private String operCode;
	private String operPwd;
	private String operName;
	private String operState;
	private BigDecimal pwdPeriod;
	private String onUserDate;
	private String sex;
	private String grade;
	private String pwdModitime;
	private String lastModifydate;
	private String modiOperCode;
	private String identityCode;
	private String operLlock;
	private Byte pwcount;
	private String insiUnitcode;

	// Constructors

	/** default constructor */
	public TbSmInnerOper() {
	}

	/** minimal constructor */
	public TbSmInnerOper(String operCode) {
		this.operCode = operCode;
	}

	/** full constructor */
	public TbSmInnerOper(String operCode, String operPwd, String operName,
			String operState, BigDecimal pwdPeriod, String onUserDate,
			String sex, String grade, String pwdModitime,
			String lastModifydate, String modiOperCode, String identityCode,
			String operLlock, Byte pwcount, String insiUnitcode) {
		this.operCode = operCode;
		this.operPwd = operPwd;
		this.operName = operName;
		this.operState = operState;
		this.pwdPeriod = pwdPeriod;
		this.onUserDate = onUserDate;
		this.sex = sex;
		this.grade = grade;
		this.pwdModitime = pwdModitime;
		this.lastModifydate = lastModifydate;
		this.modiOperCode = modiOperCode;
		this.identityCode = identityCode;
		this.operLlock = operLlock;
		this.pwcount = pwcount;
		this.insiUnitcode = insiUnitcode;
	}

	// Property accessors

	public String getOperCode() {
		return this.operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperPwd() {
		return this.operPwd;
	}

	public void setOperPwd(String operPwd) {
		this.operPwd = operPwd;
	}

	public String getOperName() {
		return this.operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperState() {
		return this.operState;
	}

	public void setOperState(String operState) {
		this.operState = operState;
	}

	public BigDecimal getPwdPeriod() {
		return this.pwdPeriod;
	}

	public void setPwdPeriod(BigDecimal pwdPeriod) {
		this.pwdPeriod = pwdPeriod;
	}

	public String getOnUserDate() {
		return this.onUserDate;
	}

	public void setOnUserDate(String onUserDate) {
		this.onUserDate = onUserDate;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPwdModitime() {
		return this.pwdModitime;
	}

	public void setPwdModitime(String pwdModitime) {
		this.pwdModitime = pwdModitime;
	}

	public String getLastModifydate() {
		return this.lastModifydate;
	}

	public void setLastModifydate(String lastModifydate) {
		this.lastModifydate = lastModifydate;
	}

	public String getModiOperCode() {
		return this.modiOperCode;
	}

	public void setModiOperCode(String modiOperCode) {
		this.modiOperCode = modiOperCode;
	}

	public String getIdentityCode() {
		return this.identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getOperLlock() {
		return this.operLlock;
	}

	public void setOperLlock(String operLlock) {
		this.operLlock = operLlock;
	}

	public Byte getPwcount() {
		return this.pwcount;
	}

	public void setPwcount(Byte pwcount) {
		this.pwcount = pwcount;
	}

	public String getInsiUnitcode() {
		return this.insiUnitcode;
	}

	public void setInsiUnitcode(String insiUnitcode) {
		this.insiUnitcode = insiUnitcode;
	}

}