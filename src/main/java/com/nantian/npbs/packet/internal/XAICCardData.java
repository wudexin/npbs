package com.nantian.npbs.packet.internal;
/**
 * 新奥燃气数据model
 * @author wzd
 *
 */
public class XAICCardData {
	
	//IC卡号 --20B
	private  String  XAIC_Id;
	
	//备注信息--2B
	private String  XAIC_Bz;
	
	//发卡次数--2B
	private String XAIC_No;
	
	//预购气量
	private String XAIC_Buy;		
	
	//加密串 --128B
	private String XAIC_Ifo;
	
	//客户姓名
	private String XAIC_Name;
	
	//用户地址
	private String XAIC_Add;
	
	//本次最大购气量
	private String XAIC_MaxGas;
	
	//购气单价
	private String XAIC_Amt1;
		
	//账户余额
	private String XAIC_Amt;
	
	//购气金额
	private String XAIC_Cost;
	
	//备注1
	private String XAIC_Bz1;
	
	//备注2
	private String XAIC_Bz2;
	
	private String IC_BM_JT;
	
	public String getXAIC_Bz1() {
		return XAIC_Bz1;
	}

	public void setXAIC_Bz1(String xAICBz1) {
		XAIC_Bz1 = xAICBz1;
	}

	public String getXAIC_Bz2() {
		return XAIC_Bz2;
	}

	public void setXAIC_Bz2(String xAICBz2) {
		XAIC_Bz2 = xAICBz2;
	}

	public String getXAIC_Id() {
		return XAIC_Id;
	}

	public void setXAIC_Id(String xAICId) {
		XAIC_Id = xAICId;
	}

	public String getXAIC_Bz() {
		return XAIC_Bz;
	}

	public void setXAIC_Bz(String xAICBz) {
		XAIC_Bz = xAICBz;
	}

	public String getXAIC_No() {
		return XAIC_No;
	}

	public void setXAIC_No(String xAICNo) {
		XAIC_No = xAICNo;
	}

	public String getXAIC_Ifo() {
		return XAIC_Ifo;
	}

	public void setXAIC_Ifo(String xAICIfo) {
		XAIC_Ifo = xAICIfo;
	}

	public String getXAIC_Name() {
		return XAIC_Name;
	}

	public void setXAIC_Name(String xAICName) {
		XAIC_Name = xAICName;
	}

	public String getXAIC_Add() {
		return XAIC_Add;
	}

	public void setXAIC_Add(String xAICAdd) {
		XAIC_Add = xAICAdd;
	}

	public String getXAIC_MaxGas() {
		return XAIC_MaxGas;
	}

	public void setXAIC_MaxGas(String xAICMaxGas) {
		XAIC_MaxGas = xAICMaxGas;
	}

	public String getXAIC_Amt1() {
		return XAIC_Amt1;
	}

	public void setXAIC_Amt1(String xAICAmt1) {
		XAIC_Amt1 = xAICAmt1;
	}

	public String getXAIC_Amt() {
		return XAIC_Amt;
	}

	public void setXAIC_Amt(String xAICAmt) {
		XAIC_Amt = xAICAmt;
	}	
	
	public String getXAIC_Cost() {
		return XAIC_Cost;
	}

	public void setXAIC_Cost(String xAICCost) {
		XAIC_Cost = xAICCost;
	}

	public String getXAIC_Buy() {
		return XAIC_Buy;
	}

	public void setXAIC_Buy(String xAICBuy) {
		XAIC_Buy = xAICBuy;
	}

	public String getIC_BM_JT() {
		return IC_BM_JT;
	}

	public void setIC_BM_JT(String iCBMJT) {
		IC_BM_JT = iCBMJT;
	}


}
