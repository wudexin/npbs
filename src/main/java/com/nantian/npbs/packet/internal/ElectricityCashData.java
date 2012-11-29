package com.nantian.npbs.packet.internal;

import java.util.ArrayList;

/**
 * 现金
 * @author qiaoxl
 *
 */
public class ElectricityCashData {

	private String query;				// 查询条件
	
	private String code;			    	// 用户编号
	
	private String username;			// 用户名称
	
	private String month;			   // 电费年月
	
	private String address;			// 用电地址
	
	private String totalBill;				// 合计金额
	
	private String totalAmt;			// 合计电费
	
	private String penBill;				// 合计违约金
	
	private String preAmt;				// 合计预收
	
	private String amtNum;			// 金额总笔数
	
	private String reg;					// 电费明细数据
	
	private  double payAmt;         // 缴费金额

	//add Start MDB 2012年1月12日 17:47:33
	//本次余额
	private String thisBalance;
	
	//起止示数
	private String seNum;
	
	//是否显示“该用户为预交、多月...”
	private Boolean isShowDetail;
	
	//add by wzd 2012年5月17日11:02:16 ---start
	private String[] jtdjxx;          //阶梯电价信息
	//add by wzd 2012年5月17日11:02:16 ---end	

	
	/**本次余额*/
	public String getThisBalance() {
		return thisBalance;
	}
	/**本次余额*/
	public void setThisBalance(String thisBalance) {
		this.thisBalance = thisBalance;
	}
	
	/**起止示数*/
	public String getSeNum() {
		return seNum;
	}
	/**起止示数*/
	public void setSeNum(String seNum) {
		this.seNum = seNum;
	}
	
	/**小票信息是否显示“该用户为预交、多月...”
	 * @return True显示 False不显示*/
	public Boolean getIsShowDetail() {
		return isShowDetail;
	}
	/**小票信息是否显示“该用户为预交、多月...”
	 * @param isShowDetail True显示 False不显示*/
	public void setIsShowDetail(Boolean isShowDetail) {
		this.isShowDetail = isShowDetail;
	}
//add End MDB 2012年1月12日 17:47:33
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(String totalBill) {
		this.totalBill = totalBill;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getPenBill() {
		return penBill;
	}

	public void setPenBill(String penBill) {
		this.penBill = penBill;
	}

	public String getPreAmt() {
		return preAmt;
	}

	public void setPreAmt(String preAmt) {
		this.preAmt = preAmt;
	}

	public String getAmtNum() {
		return amtNum;
	}

	public void setAmtNum(String amtNum) {
		this.amtNum = amtNum;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(double payAmt) {
		this.payAmt = payAmt;
	}
	public String[] getJtdjxx() {
		return jtdjxx;
	}
	public void setJtdjxx(String[] jtdjxx) {
		this.jtdjxx = jtdjxx;
	}	
	

	

}
