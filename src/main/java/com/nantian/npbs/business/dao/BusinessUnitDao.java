package com.nantian.npbs.business.dao;

public interface BusinessUnitDao {

	/**
	 * 增加进程
	 * @param busiCode  业务编码
	 * @param unitcode  机构编码
	 */
	public abstract boolean addProcess(String busiCode, String unitcode)
			throws Exception;

	/**
	 * 释放机构进程数
	 * @param busiCode  业务编码
	 * @param unitcode  机构编码
	 */
	public abstract boolean releaseProcess(String busiCode, String unitcode)
			throws Exception;

}