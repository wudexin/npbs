package com.nantian.npbs.packet.business.FIXSTRING;


/**
 * FIXSTRING报文头数据对象封装
 * @author jxw
 *
 */
public class FIXMessageHead {
	private int packetLength;		//报文长度
	private String applicationType; //应用类别定义
	private String terminalState; 	//终端状态
	private String handleType;  		//处理要求
	private String tranCode;    //交易码
	private String psamCard;    //PSAM卡号
	private String eposSeqno;    //EPOS流水号
	private String terminalId;    //终端编号
	private String tranTime;    //交易时间
	private String tranDate;    //交易日期
	private String pbSeqno;    //PB流水号
	private String retCode;    //应答码
	private String retMsg;    //返回数据
	
	public int getPacketLength() {
		return packetLength;
	}
	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getTerminalState() {
		return terminalState;
	}
	public void setTerminalState(String terminalState) {
		this.terminalState = terminalState;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getPsamCard() {
		return psamCard;
	}
	public void setPsamCard(String psamCard) {
		this.psamCard = psamCard;
	}
	public String getEposSeqno() {
		return eposSeqno;
	}
	public void setEposSeqno(String eposSeqno) {
		this.eposSeqno = eposSeqno;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getPbSeqno() {
		return pbSeqno;
	}
	public void setPbSeqno(String pbSeqno) {
		this.pbSeqno = pbSeqno;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}
