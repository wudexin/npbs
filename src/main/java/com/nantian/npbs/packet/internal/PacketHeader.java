/**
 * 
 */
package com.nantian.npbs.packet.internal;

import java.util.BitSet;

/**
 * @author TsaiYee
 *
 */
public class PacketHeader {
	private String TPDU; //for 网控器
	private String applicationType; //应用类别定义
	private String reserveString; //保留使用
	private char terminalState; //终端状态
	private char handleType;  //处理要求
	private String terminalVersion; //终端软件版本号
	private String messageType;
	private BitSet bitmap; //for ISO8583
	private int headerLength; 
	private int mabLength;
	private int packetLength;
	
	private String tpId;
	private String distAddr;
	private String sourceAdd;
	
	
	
	public String getTpId() {
		return tpId;
	}
	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
	public String getDistAddr() {
		return distAddr;
	}
	public void setDistAddr(String distAddr) {
		this.distAddr = distAddr;
	}
	public String getSourceAdd() {
		return sourceAdd;
	}
	public void setSourceAdd(String sourceAdd) {
		this.sourceAdd = sourceAdd;
	}
	/**
	 * @return the headerLength
	 */
	public int getHeaderLength() {
		return headerLength;
	}
	/**
	 * @param headerLength the headerLength to set
	 */
	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @return the bitmap
	 */
	public BitSet getBitmap() {
		return bitmap;
	}
	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(BitSet bitmap) {
		this.bitmap = bitmap;
	}
	/**
	 * @return the tPDU
	 */
	public String getTPDU() {
		return TPDU;
	}
	/**
	 * @param tPDU the tPDU to set
	 */
	public void setTPDU(String tPDU) {
		TPDU = tPDU;
	}
	/**
	 * @return the applicationType
	 */
	public String getApplicationType() {
		return applicationType;
	}
	/**
	 * @param applicationType the applicationType to set
	 */
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	/**
	 * @return the reserveString
	 */
	public String getReserveString() {
		return reserveString;
	}
	/**
	 * @param reserveString the reserveString to set
	 */
	public void setReserveString(String reserveString) {
		this.reserveString = reserveString;
	}
	/**
	 * @return the terminalState
	 */
	public char getTerminalState() {
		return terminalState;
	}
	/**
	 * @param terminalState the terminalState to set
	 */
	public void setTerminalState(char terminalState) {
		this.terminalState = terminalState;
	}
	/**
	 * @return the handleType
	 */
	public char getHandleType() {
		return handleType;
	}
	/**
	 * @param handleType the handleType to set
	 */
	public void setHandleType(char handleType) {
		this.handleType = handleType;
	}
	/**
	 * @return the terminalVersion
	 */
	public String getTerminalVersion() {
		return terminalVersion;
	}
	/**
	 * @param terminalVersion the terminalVersion to set
	 */
	public void setTerminalVersion(String terminalVersion) {
		this.terminalVersion = terminalVersion;
	}
	/**
	 * @param mabLength the mabLength to set
	 */
	public void setMabLength(int mabLength) {
		this.mabLength = mabLength;
	}
	/**
	 * @return the mabLength
	 */
	public int getMabLength() {
		return mabLength;
	}
	/**
	 * @param packetLength the packetLength to set
	 */
	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}
	/**
	 * @return the packetLength
	 */
	public int getPacketLength() {
		return packetLength;
	}
	
}
