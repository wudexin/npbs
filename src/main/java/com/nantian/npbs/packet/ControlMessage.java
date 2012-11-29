/**
 * 
 */
package com.nantian.npbs.packet;

import java.util.Date;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.packet.business.ISO8583.IPacketISO8583;
import com.nantian.npbs.packet.internal.PacketHeader;

/**
 * @author TsaiYee
 *
 */
public class ControlMessage {
	
	private String tranCode; //交易码
	private String cancelBusinessType;  // 取消交易，取消原交易流水交易码前三位
	private CHANEL_TYPE chanelType; //渠道类型
	private boolean isSynchronous; //是否同步
	private boolean isLockProcess = false; //是否已经锁定进程资源
	PacketHeader requestPacketHeader;//报文头解包类
	IPacketISO8583 tranPacketHelper; //8583解包类
	private boolean enablePack = false;//报文是否合法
	private String resultCode; //处理结果码，6位，内部使用，将根据此结果码进行流程控制等
	private String resultMsg;  //处理结果描述，内部使用，将记录到日志中，返回到请求端的响应信息将根据resultCode将resultMsg复制过去
	private String serviceCallFlag; //是否发送第三方标志 0-不发送，1-发送
	private String serviceResultCode; //第三方服务返回的结果码，将第三方的转换为系统内部的
	private String serviceResultMsg; //第三方服务返回的结果描述，将第三方的转换为系统内部的
	private Date timeout; //超时时间，由RequestProcessor初始化时根据当前时间和超时间隔（秒）计算而得
	private int timeOutInterval; //超时间隔（秒），在全局变量中进行初始化
	private IPacket servicePacketHelper = null; //第三方服务报文处理对象, 由ServiceProcessor负责初始化
	private IPacket termialPacketHelper = null; //接入渠道报文处理对象, 由RequestProcessor负责初始化
	
	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}
	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	/**
	 * @return the packetHeader
	 */
	public PacketHeader getPacketHeader() {
		return requestPacketHeader;
	}
	/**
	 * @param packetHeader the packetHeader to set
	 */
	public void setPacketHeader(PacketHeader packetHeader) {
		this.requestPacketHeader = packetHeader;
	}
	/**
	 * @return the tranCode
	 */
	public String getTranCode() {
		return tranCode;
	}
	/**
	 * @param tranCode the tranCode to set
	 */
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	/**
	 * @return the chanelType
	 */
	public CHANEL_TYPE getChanelType() {
		return chanelType;
	}
	/**
	 * @param chanelType the chanelType to set
	 */
	public void setChanelType(CHANEL_TYPE chanelType) {
		this.chanelType = chanelType;
	}
	/**
	 * @return the isSynchronous
	 */
	public boolean isSynchronous() {
		return isSynchronous;
	}
	/**
	 * @param isSynchronous the isSynchronous to set
	 */
	public void setSynchronous(boolean isSynchronous) {
		this.isSynchronous = isSynchronous;
	}
	/**
	 * @param isLockProcess the isLockProcess to set
	 */
	public void setLockProcess(boolean isLockProcess) {
		this.isLockProcess = isLockProcess;
	}
	/**
	 * @return the isLockProcess
	 */
	public boolean isLockProcess() {
		return isLockProcess;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}
	/**
	 * @return the timeout
	 */
	public Date getTimeout() {
		return timeout;
	}
	/**
	 * @param timeOutInterval the timeOutInterval to set
	 */
	public void setTimeOutInterval(int timeOutInterval) {
		this.timeOutInterval = timeOutInterval;
	}
	/**
	 * @return the timeOutInterval
	 */
	public int getTimeOutInterval() {
		return timeOutInterval;
	}
	
	/**
	 * @param serviceCallFlag the serviceCallFlag to set
	 */
	public void setServiceCallFlag(String serviceCallFlag) {
		this.serviceCallFlag = serviceCallFlag;
	}
	/**
	 * @return the serviceResultCode
	 */
	public String getServiceCallFlag() {
		return serviceCallFlag;
	}
	
	/**
	 * @param serviceResultCode the serviceResultCode to set
	 */
	public void setServiceResultCode(String serviceResultCode) {
		this.serviceResultCode = serviceResultCode;
	}
	/**
	 * @return the serviceResultCode
	 */
	public String getServiceResultCode() {
		return serviceResultCode;
	}
	/**
	 * @param serviceResultMsg the serviceResultMsg to set
	 */
	public void setServiceResultMsg(String serviceResultMsg) {
		this.serviceResultMsg = serviceResultMsg;
	}
	/**
	 * @return the serviceResultMsg
	 */
	public String getServiceResultMsg() {
		return serviceResultMsg;
	}
	/**
	 * @param servicePacketHelper the servicePacketHelper to set
	 */
	public void setServicePacketHelper(IPacket servicePacketHelper) {
		this.servicePacketHelper = servicePacketHelper;
	}
	/**
	 * @return the servicePacketHelper
	 */
	public IPacket getServicePacketHelper() {
		return servicePacketHelper;
	}
	/**
	 * @param termialPacketHelper the termialPacketHelper to set
	 */
	public void setTermialPacketHelper(IPacket termialPacketHelper) {
		this.termialPacketHelper = termialPacketHelper;
	}
	/**
	 * @return the termialPacketHelper
	 */
	public IPacket getTermialPacketHelper() {
		return termialPacketHelper;
	}
	public String getCancelBusinessType() {
		return cancelBusinessType;
	}
	public void setCancelBusinessType(String cancelBusinessType) {
		this.cancelBusinessType = cancelBusinessType;
	}
	public PacketHeader getRequestPacketHeader() {
		return requestPacketHeader;
	}
	public void setRequestPacketHeader(PacketHeader requestPacketHeader) {
		this.requestPacketHeader = requestPacketHeader;
	}
	public IPacketISO8583 getTranPacketHelper() {
		return tranPacketHelper;
	}
	public void setTranPacketHelper(IPacketISO8583 tranPacketHelper) {
		this.tranPacketHelper = tranPacketHelper;
	}
	public boolean isEnablePack() {
		return enablePack;
	}
	public void setEnablePack(boolean enablePack) {
		this.enablePack = enablePack;
	}
	
	
}
