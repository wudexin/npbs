package com.nantian.npbs.business.model;

public class TbNdPara implements java.io.Serializable {

	private String id;// 商户号
	private String NDCOMPANY_NAME;// 商户名
	private String NDUNITCODE;// 机构编码
	private String NDFACTORY;// 终端厂商
	private String ND_PARA_VALUENAME;// 参数ID
	private String ND_PARA_VALUE;// 轮寻ID顺序
 
	public String getNDCOMPANY_NAME() {
		return NDCOMPANY_NAME;
	}
	public String getNDUNITCODE() {
		return NDUNITCODE;
	}
	public String getNDFACTORY() {
		return NDFACTORY;
	}
	public String getND_PARA_VALUENAME() {
		return ND_PARA_VALUENAME;
	}
	public String getND_PARA_VALUE() {
		return ND_PARA_VALUE;
	}
	public String getNDREMARK() {
		return NDREMARK;
	}
 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setNDCOMPANY_NAME(String nDCOMPANY_NAME) {
		NDCOMPANY_NAME = nDCOMPANY_NAME;
	}
	public void setNDUNITCODE(String nDUNITCODE) {
		NDUNITCODE = nDUNITCODE;
	}
	public void setNDFACTORY(String nDFACTORY) {
		NDFACTORY = nDFACTORY;
	}
	public void setND_PARA_VALUENAME(String nD_PARA_VALUENAME) {
		ND_PARA_VALUENAME = nD_PARA_VALUENAME;
	}
	public void setND_PARA_VALUE(String nD_PARA_VALUE) {
		ND_PARA_VALUE = nD_PARA_VALUE;
	}
	public void setNDREMARK(String nDREMARK) {
		NDREMARK = nDREMARK;
	}
	private String NDREMARK;// 备注
}
