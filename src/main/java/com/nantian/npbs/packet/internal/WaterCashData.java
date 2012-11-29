package com.nantian.npbs.packet.internal;

/**
 * 水费
 * 
 * @author hubo
 * 
 */
public class WaterCashData {

	private String userNo;			// 用户编号

	private String username;		// 用户名称

	private String cardNo;			//卡片编号

	private String totalAmt;		//欠费总金额
	
	private String oughtAmt;		//应收金额
	
	private String seqno3;			//第三方流水号
	
	private String ticketSum;		//发票张数
	
	private String payAmt;			//实际缴费
	
	private String channelDate;		//接入渠道日期
	
	private String channelSeqno;	//接入渠道流水号
	
	private String oldSeqNo;		//原交易流水号
	
	private String payDate;			//对账日期
	
	private String feeMon;			//收费月份一般为000000代表全部欠费
	
	private String userAddr;		//用户地址
	
	private String sumFee;			//应缴金额（应收总额）
	
	private String recNum;			//欠费月数数（见文档）
	
	private String[] copyDate;		//抄表日期
	
	private String[] startData;		//起码
	
	private String[] endData;			//止码
	
	private String[] usedNum;			//使用吨数
	
	private String[] monFee;			//本月欠费
	
	private String[] feeFlag;			//缴费标识
	
	private String certNo;			//凭证号码
	
	private String voucKind;		//凭证类型
	
	private String sumNum;			//欠费总额
	
	private String lastDate;		//上次抄表时间
	
	private String currDate;		//本次抄表时间
	
	private String amt1;			//水费
	
	private String amt2;			//污水费
	
	private String amt3;			//水资源费(源水费)
	
	private String lastBal;			//上次读数
	
	private String currBal;			//本次读数
	
	private String price1;			//水价1
	
	private String price2;			//水价2
	
	private String price3;			//水价3
	
	private String currData;		//本次抄时间
	
	private String oldPayAmt;		//原缴费金额
	
	private String billLstno;		//凭证终止序号
	
	private String seqno;			//凭证流水

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getOughtAmt() {
		return oughtAmt;
	}

	public void setOughtAmt(String oughtAmt) {
		this.oughtAmt = oughtAmt;
	}

	public String getSeqno3() {
		return seqno3;
	}

	public void setSeqno3(String seqno3) {
		this.seqno3 = seqno3;
	}

	public String getTicketSum() {
		return ticketSum;
	}

	public void setTicketSum(String ticketSum) {
		this.ticketSum = ticketSum;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getChannelDate() {
		return channelDate;
	}

	public void setChannelDate(String channelDate) {
		this.channelDate = channelDate;
	}

	public String getChannelSeqno() {
		return channelSeqno;
	}

	public void setChannelSeqno(String channelSeqno) {
		this.channelSeqno = channelSeqno;
	}

	public String getOldSeqNo() {
		return oldSeqNo;
	}

	public void setOldSeqNo(String oldSeqNo) {
		this.oldSeqNo = oldSeqNo;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getFeeMon() {
		return feeMon;
	}

	public void setFeeMon(String feeMon) {
		this.feeMon = feeMon;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getSumFee() {
		return sumFee;
	}

	public void setSumFee(String sumFee) {
		this.sumFee = sumFee;
	}

	public String getRecNum() {
		return recNum;
	}

	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}

	public String[] getCopyDate() {
		return copyDate;
	}

	public void setCopyDate(String[] copyDate) {
		this.copyDate = copyDate;
	}

	public String[] getStartData() {
		return startData;
	}

	public void setStartData(String[] startData) {
		this.startData = startData;
	}

	public String[] getEndData() {
		return endData;
	}

	public void setEndData(String[] endData) {
		this.endData = endData;
	}

	public String[] getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(String[] usedNum) {
		this.usedNum = usedNum;
	}

	public String[] getMonFee() {
		return monFee;
	}

	public void setMonFee(String[] monFee) {
		this.monFee = monFee;
	}

	public String[] getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String[] feeFlag) {
		this.feeFlag = feeFlag;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getVoucKind() {
		return voucKind;
	}

	public void setVoucKind(String voucKind) {
		this.voucKind = voucKind;
	}

	public String getSumNum() {
		return sumNum;
	}

	public void setSumNum(String sumNum) {
		this.sumNum = sumNum;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public String getAmt1() {
		return amt1;
	}

	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}

	public String getAmt2() {
		return amt2;
	}

	public void setAmt2(String amt2) {
		this.amt2 = amt2;
	}

	public String getAmt3() {
		return amt3;
	}

	public void setAmt3(String amt3) {
		this.amt3 = amt3;
	}

	public String getLastBal() {
		return lastBal;
	}

	public void setLastBal(String lastBal) {
		this.lastBal = lastBal;
	}

	public String getCurrBal() {
		return currBal;
	}

	public void setCurrBal(String currBal) {
		this.currBal = currBal;
	}

	public String getPrice1() {
		return price1;
	}

	public void setPrice1(String price1) {
		this.price1 = price1;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getPrice3() {
		return price3;
	}

	public void setPrice3(String price3) {
		this.price3 = price3;
	}

	public String getCurrData() {
		return currData;
	}

	public void setCurrData(String currData) {
		this.currData = currData;
	}

	public String getOldPayAmt() {
		return oldPayAmt;
	}

	public void setOldPayAmt(String oldPayAmt) {
		this.oldPayAmt = oldPayAmt;
	}

	public String getBillLstno() {
		return billLstno;
	}

	public void setBillLstno(String billLstno) {
		this.billLstno = billLstno;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	
}
