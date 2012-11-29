package com.nantian.npbs.business.dao;

import java.util.List;

import com.nantian.npbs.business.model.TbBiMenu;

public interface MenuDao {
	
	/**
	 * 查询所有菜单字符串
	 */
	public abstract List getMenus(String sql);

	/**
	 * 查询所有菜单目录
	 */
	public abstract List<TbBiMenu> getAllMenus();
	
	/**
	 * 查询商户所属缴费菜单
	 * @param compCode 商户号
	 */
	public abstract List<TbBiMenu> getBusinessMenus(String shopCode);

}