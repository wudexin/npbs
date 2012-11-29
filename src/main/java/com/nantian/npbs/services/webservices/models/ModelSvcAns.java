package com.nantian.npbs.services.webservices.models;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

public class ModelSvcAns  implements  Synchronization{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态
	 private String  message;//返回消息
	private String busi_code;//交易码
	 private String acc_balance;//当前余额
	 private String amount;//扣减金额
	 private String CREDIT_AMT;//信用额度
	 private String company_code;//扣减的商户号
	 private String pb_serial;//便民流水
	 private String trade_date;//便民日期
	 private String inmessage;//内部消息
	 private String instatus;//内部状态
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public void setBusi_code(String busiCode) {
		busi_code = busiCode;
	}
	public String getAcc_balance() {
		return acc_balance;
	}
	public void setAcc_balance(String accBalance) {
		acc_balance = accBalance;
	}
	public String getCREDIT_AMT() {
		return CREDIT_AMT;
	}
	public void setCREDIT_AMT(String cREDITAMT) {
		CREDIT_AMT = cREDITAMT;
	}
	@Override
	public void onComplete(Exchange arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFailure(Exchange arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		 
		return "状态："+this.getStatus()+"消息"+this.getMessage()+"商户号"+this.getBusi_code()+"余额"+this.getAcc_balance()+"信用额度"+this.getCREDIT_AMT();
	}
	 public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getCompany_code() {
			return company_code;
		}
		public void setCompany_code(String companyCode) {
			company_code = companyCode;
		}
		public String getPb_serial() {
			return pb_serial;
		}
		public void setPb_serial(String pbSerial) {
			pb_serial = pbSerial;
		}
		public String getTrade_date() {
			return trade_date;
		}
		public void setTrade_date(String tradeDate) {
			trade_date = tradeDate;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getInmessage() {
			return inmessage;
		}
		public String getInstatus() {
			return instatus;
		}
		public void setInmessage(String inmessage) {
			this.inmessage = inmessage;
		}
		public void setInstatus(String instatus) {
			this.instatus = instatus;
		}
	
	
}
