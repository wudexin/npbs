package com.nantian.npbs.services.webservices.models;

import com.nantian.npbs.business.model.TbBiPrepay;

public class ModelSvcReq {
	private static final long serialVersionUID = 1L;
	private String busi_code;//交易码
	private String company_code;//商户号
	private String company_code_sec;//辅商户号
	private String web_serial;//流水号：（便民E站）
	private String web_date;//交易日期：（便民E站）
	private String system_code;//交易类型（便民E站）
	private String amount;//交易金额
	private String flag;//1为存款 2为取款
	 private TbBiPrepay tbBiPrepay;//备付金信息
	
	
	public String getCompany_code_sec() {
		return company_code_sec;
	}
	public void setCompany_code_sec(String companyCodeSec) {
		company_code_sec = companyCodeSec;
	}
	public String getWeb_serial() {
		return web_serial;
	}
	public void setWeb_serial(String webSerial) {
		web_serial = webSerial;
	}
	public String getWeb_date() {
		return web_date;
	}
	public void setWeb_date(String webDate) {
		web_date = webDate;
	}
	public String getSystem_code() {
		return system_code;
	}
	public void setSystem_code(String systemCode) {
		system_code = systemCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public void setBusi_code(String busiCode) {
		busi_code = busiCode;
	}
	public String getCompany_code() {
		return company_code;
	}
	public void setCompany_code(String companyCode) {
		company_code = companyCode;
	}
	public TbBiPrepay getTbBiPrepay() {
		return tbBiPrepay;
	}
	public void setTbBiPrepay(TbBiPrepay tbBiPrepay) {
		this.tbBiPrepay = tbBiPrepay;
	}
	 
	
}
