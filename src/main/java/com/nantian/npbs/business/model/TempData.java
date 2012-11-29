package com.nantian.npbs.business.model;

import java.util.Date;

/**
 * 临时保存数据
 * 查询或者缴费从电子商务平台获取数据后，临时存放位置，
 * 为缴费或者写卡交易上交电子商务平台使用
 * @author qiaoxl
 *
 */
public class TempData implements java.io.Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String pbSeqno;
	
	private String tempValue;
	
	private String tradeDate;

	public String getPbSeqno() {
		return pbSeqno;
	}

	public void setPbSeqno(String pbSeqno) {
		this.pbSeqno = pbSeqno;
	}

	public String getTempValue() {
		return tempValue;
	}

	public void setTempValue(String tempValue) {
		this.tempValue = tempValue;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

}
