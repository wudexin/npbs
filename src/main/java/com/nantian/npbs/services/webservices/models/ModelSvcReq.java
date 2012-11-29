package com.nantian.npbs.services.webservices.models;

import java.util.Date;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;

public class ModelSvcReq {
	private static final long serialVersionUID = 1L;

	private String Old_pb_serial;// 原便民流水
	private String Old_web_serial;// 原E站流水
	private String Old_trade_date;//原便民DATE
	private String Old_web_date;//原E站DATE
	private String busi_code;// 交易码
	private String company_code_fir;// 商户号
	private String company_code_sec;// 辅商户号
	private String web_serial;// 流水号：（便民E站）
	private String web_date;// 交易日期：（便民E站）
	private String system_code;// 交易类型（便民E站）
	private String amount;// 交易金额
	private String flag;// 1为存款 2为取款
	private String trade_date;//便民系统日期
	private String pb_serial;//便民系统流水
	
	private Date timeout; //超时时间
	private int timeOutInterval; //超时间隔（秒），在全局变量中进行初始化

	// 校验标志
	private String seqnoFlag; // 是否登记流水0-不登记，1-登记

	// 流水信息及备付金信息
	private TbBiPrepay tbBiPrepay;// 备付金信息
	private TbBiTrade tbBiTrade;// 流水
	private TbBiTradeId tradeId;//
	private TbBiPrepayInfo tbBiPrepayInfo;
	private TbBiPrepayInfoId tbBiPrepayInfoId;
	
	private TbBiTrade oriTrade;// 流水

	public TbBiTrade getOriTrade() {
		return oriTrade;
	}

	public void setOriTrade(TbBiTrade oriTrade) {
		this.oriTrade = oriTrade;
	}

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

	public String getCompany_code_fir() {
		return company_code_fir;
	}

	public void setCompany_code_fir(String companyCode) {
		company_code_fir = companyCode;
	}

	public TbBiPrepay getTbBiPrepay() {
		return tbBiPrepay;
	}

	public void setTbBiPrepay(TbBiPrepay tbBiPrepay) {
		this.tbBiPrepay = tbBiPrepay;
	}

	public String getOld_pb_serial() {
		return Old_pb_serial;
	}

	public String getOld_web_serial() {
		return Old_web_serial;
	}

	public void setOld_pb_serial(String oldPbSerial) {
		Old_pb_serial = oldPbSerial;
	}

	public void setOld_web_serial(String oldWebSerial) {
		Old_web_serial = oldWebSerial;
	}

	public String getSeqnoFlag() {
		return seqnoFlag;
	}

	public void setSeqnoFlag(String seqnoFlag) {
		this.seqnoFlag = seqnoFlag;
	}

	public TbBiTrade getTbBiTrade() {
		return tbBiTrade;
	}

	public void setTbBiTrade(TbBiTrade tbBiTrade) {
		this.tbBiTrade = tbBiTrade;
	}

	public TbBiTradeId getTradeId() {
		return tradeId;
	}

	public void setTradeId(TbBiTradeId tradeId) {
		this.tradeId = tradeId;
	}

	public TbBiPrepayInfo getTbBiPrepayInfo() {
		return tbBiPrepayInfo;
	}

	public void setTbBiPrepayInfo(TbBiPrepayInfo tbBiPrepayInfo) {
		this.tbBiPrepayInfo = tbBiPrepayInfo;
	}

	public TbBiPrepayInfoId getTbBiPrepayInfoId() {
		return tbBiPrepayInfoId;
	}

	public void setTbBiPrepayInfoId(TbBiPrepayInfoId tbBiPrepayInfoId) {
		this.tbBiPrepayInfoId = tbBiPrepayInfoId;
	}

	public Date getTimeout() {
		return timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}

	public int getTimeOutInterval() {
		return timeOutInterval;
	}

	public void setTimeOutInterval(int timeOutInterval) {
		this.timeOutInterval = timeOutInterval;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String tradeDate) {
		trade_date = tradeDate;
	}

	public String getPb_serial() {
		return pb_serial;
	}

	public void setPb_serial(String pbSerial) {
		pb_serial = pbSerial;
	}

	public String getOld_trade_date() {
		return Old_trade_date;
	}

	public void setOld_trade_date(String oldTradeDate) {
		Old_trade_date = oldTradeDate;
	}

	public String getOld_web_date() {
		return Old_web_date;
	}

	public void setOld_web_date(String oldWebDate) {
		Old_web_date = oldWebDate;
	}


}
