/**
 * 
 */
package com.nantian.npbs.packet.internal;

/**
 *	 IC卡数据
 * @author TsaiYee
 *
 */
public class ICCardData {
	
	//IC卡号,卡识别号(20位）
	private String cardNo; 
	
	 //客户名称
	private String customerName;
	
	
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
}
