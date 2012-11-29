package com.nantian.npbs.services.webservices.models;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;

public class ModelSvcAns   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strTest;
	private String status;// 返回状态
	private String message;// 返回消息
	private String busi_code;// 交易码
	private String company_code_fir;// 扣减的商户号
	private String acc_balance_fir;// 当前余额
	private String amount;// 扣减金额
	private String company_code_sec;// 扣减的商户号
	private String acc_balance_sec;// 当前余额
	private String credit_amt;// 信用额度
	private String pb_serial;// 便民流水
	private String trade_date;// 便民日期
	
	private String totalStatus;
	 
	
	public String getTotalStatus() {
		return totalStatus;
	}
	public void setTotalStatus(String totalStatus) {
		this.totalStatus = totalStatus;
	}
	//流水信息及备付金信息
	private TbBiPrepay tbBiPrepay;//备付金信息
	private TbBiTrade tbBiTrade;//流水
	private TbBiTradeId tradeId;//
	private TbBiPrepayInfo tbBiPrepayInfo;
	private TbBiPrepayInfoId tbBiPrepayInfoId;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public String getCompany_code_fir() {
		return company_code_fir;
	}
	public String getAcc_balance_fir() {
		return acc_balance_fir;
	}
	 
	public String getCompany_code_sec() {
		return company_code_sec;
	}
	public String getAcc_balance_sec() {
		return acc_balance_sec;
	}
	 
	public String getCredit_amt() {
		return credit_amt;
	}
	public String getPb_serial() {
		return pb_serial;
	}
	public String getTrade_date() {
		return trade_date;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setBusi_code(String busiCode) {
		busi_code = busiCode;
	}
	public void setCompany_code_fir(String companyCodeFir) {
		company_code_fir = companyCodeFir;
	}
	public void setAcc_balance_fir(String accBalanceFir) {
		acc_balance_fir = accBalanceFir;
	}
 
	public void setCompany_code_sec(String companyCodeSec) {
		company_code_sec = companyCodeSec;
	}
	public void setAcc_balance_sec(String accBalanceSec) {
		acc_balance_sec = accBalanceSec;
	}
	 
	public void setCredit_amt(String creditAmt) {
		credit_amt = creditAmt;
	}
	public void setPb_serial(String pbSerial) {
		pb_serial = pbSerial;
	}
	public void setTrade_date(String tradeDate) {
		trade_date = tradeDate;
	}
 
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public TbBiPrepay getTbBiPrepay() {
		return tbBiPrepay;
	}
	public void setTbBiPrepay(TbBiPrepay tbBiPrepay) {
		this.tbBiPrepay = tbBiPrepay;
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


	public String getStrTest() {
		return strTest;
	}

	public void setStrTest(String strTest) {
		this.strTest = strTest;
	}
}
