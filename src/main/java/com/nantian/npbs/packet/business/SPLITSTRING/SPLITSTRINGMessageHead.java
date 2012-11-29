package com.nantian.npbs.packet.business.SPLITSTRING;

/**
 * EJB报文头数据对象封装
 * 
 * @author
 * 
 */
public class SPLITSTRINGMessageHead {
	private String tranCode; // 交易码
	private String tranDate; // 交易日期
	private String thirdSerial; // 电子商务平台流水号
	private String channelCode; // 渠道号
	private String operCode; // 柜员号
	private String unitCode; // 机构号

	private String retCode; // 响应码
	private String retMsg; // 响应信息
	private String thirdDate; // 电子商务平台交易日期
	private String pbserial; // 便民服务站交易日期
	private String pbSerial; // 便民服务站流水号

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getThirdSerial() {
		return thirdSerial;
	}

	public void setThirdSerial(String thirdSerial) {
		this.thirdSerial = thirdSerial;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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

	public String getThirdDate() {
		return thirdDate;
	}

	public void setThirdDate(String thirdDate) {
		this.thirdDate = thirdDate;
	}

	public String getPbserial() {
		return pbserial;
	}

	public void setPbserial(String pbserial) {
		this.pbserial = pbserial;
	}

	public String getPbSerial() {
		return pbSerial;
	}

	public void setPbSerial(String pbSerial) {
		this.pbSerial = pbSerial;
	}

}