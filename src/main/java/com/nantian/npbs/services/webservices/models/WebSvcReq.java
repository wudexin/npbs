package com.nantian.npbs.services.webservices.models;

import java.util.Date;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;

public class WebSvcReq {
	private String WebReqStr;// web端传入串
	private String web_date;// 交易日期：（便民E站）
	private String web_serial;// 流水号：（便民E站）
	private String busi_code;// 交易码
	public String getWeb_date() {
		return web_date;
	}

	public void setWeb_date(String web_date) {
		this.web_date = web_date;
	}

	public String getWeb_serial() {
		return web_serial;
	}

	public void setWeb_serial(String web_serial) {
		this.web_serial = web_serial;
	}

	public String getBusi_code() {
		return busi_code;
	}

	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}

	public String getWebReqStr() {
		return WebReqStr;
	}

	public void setWebReqStr(String webReqStr) {
		WebReqStr = webReqStr;
	}


	
}
