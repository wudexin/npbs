package com.nantian.npbs.business.dao;

import com.nantian.npbs.business.model.TbSmProgram;

public interface ProgramDao {
	
	/**
	 * 查询商户所属缴费菜单
	 * @param compCode 商户号
	 */
	public abstract TbSmProgram getProgrameByFilepath(String filepath);
	
}