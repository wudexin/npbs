package com.nantian.npbs.packet.business.TUXSTRING;

/**
 * TUXEDO报文头数据对象封装
 * @author jxw
 *
 */
public class TUXMessageHead {
	private String branchNo;		//局号
	private String operNo;			//柜员号
	private String journalSeqno;	//交易流水号
	private String localIpAddr;		//本地ip地址
	private String terminalNo;		//终端号
	private String authOperNo;		//授权柜员号
	private String channelTrace;	//渠道流水号
	private String channelNo;		//渠道标识
	private String branchNoNew;		//网点机构号
	private String operNoNew;		//柜员号
	private int sendFileNum;		//上传文件个数
	private String sendFileName;	//不带路径上送文件名
	
	private String retErr;			//主机返回处理代码
	private String retMsg;			//主机返回信息
	private String sysDate;			//系统日期
	private int rFileNum;			//下送文件个数
	private String[] rFileName;		//不带路径下送文件名
	private String rendFlag;		//不带路径下送文件名
	
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getJournalSeqno() {
		return journalSeqno;
	}
	public void setJournalSeqno(String journalSeqno) {
		this.journalSeqno = journalSeqno;
	}
	public String getLocalIpAddr() {
		return localIpAddr;
	}
	public void setLocalIpAddr(String localIpAddr) {
		this.localIpAddr = localIpAddr;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getAuthOperNo() {
		return authOperNo;
	}
	public void setAuthOperNo(String authOperNo) {
		this.authOperNo = authOperNo;
	}
	public String getChannelTrace() {
		return channelTrace;
	}
	public void setChannelTrace(String channelTrace) {
		this.channelTrace = channelTrace;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getBranchNoNew() {
		return branchNoNew;
	}
	public void setBranchNoNew(String branchNoNew) {
		this.branchNoNew = branchNoNew;
	}
	public String getOperNoNew() {
		return operNoNew;
	}
	public void setOperNoNew(String operNoNew) {
		this.operNoNew = operNoNew;
	}
	public int getSendFileNum() {
		return sendFileNum;
	}
	public void setSendFileNum(int sendFileNum) {
		this.sendFileNum = sendFileNum;
	}
	public String getSendFileName() {
		return sendFileName;
	}
	public void setSendFileName(String sendFileName) {
		this.sendFileName = sendFileName;
	}
	public String getRetErr() {
		return retErr;
	}
	public void setRetErr(String retErr) {
		this.retErr = retErr;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getSysDate() {
		return sysDate;
	}
	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}
	public int getrFileNum() {
		return rFileNum;
	}
	public void setrFileNum(int rFileNum) {
		this.rFileNum = rFileNum;
	}
	public String getRendFlag() {
		return rendFlag;
	}
	public void setRendFlag(String rendFlag) {
		this.rendFlag = rendFlag;
	}
	public String[] getrFileName() {
		return rFileName;
	}
	public void setrFileName(String[] rFileName) {
		this.rFileName = rFileName;
	}
	
	
}
