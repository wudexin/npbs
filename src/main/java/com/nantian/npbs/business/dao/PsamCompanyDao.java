package com.nantian.npbs.business.dao;



public interface PsamCompanyDao {

	/**
	 * 根据psam卡号查询商户号
	 * 
	 * @param psamCardNo
	 * @return shopcode
	 */
	public String findPsamCompanyRef(String psamCardNo) ;

}