package com.nantian.npbs.services.webservices.models;
import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

public class WebSvcAns {
	private String strTest;
	private String WebAnsStr;// web端传出串
	private String status;// 返回状态
	private String message;// 返回消息
	private String busi_code;// 交易码
	private String totalStatus;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBusi_code() {
		return busi_code;
	}

	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}

	public String getTotalStatus() {
		return totalStatus;
	}

	public void setTotalStatus(String totalStatus) {
		this.totalStatus = totalStatus;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWebAnsStr() {
		return WebAnsStr;
	}

	public void setWebAnsStr(String webAnsStr) {
		WebAnsStr = webAnsStr;
	}

	public Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getStrTest() {
		return strTest;
	}

	public void setStrTest(String strTest) {
		this.strTest = strTest;
	}
	

}
