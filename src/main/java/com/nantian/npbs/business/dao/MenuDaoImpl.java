package com.nantian.npbs.business.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nantian.npbs.business.model.TbBiMenu;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;

/**
 * 缴费业务菜单
 * 
 * @author MDB
 * 
 */
@Repository(value = "menuDao")
public class MenuDaoImpl extends BaseHibernateDao implements MenuDao {

	private static Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

	public void excuteSQL(final String sql) {
		try {

		} catch (Exception e) {
			logger.error("执行SQL错误：" + sql, e);
		}
	}
	
	/**
	 * 查询缴费菜单
	 * @param sql SQL语句
	 */
	@SuppressWarnings("unchecked")
	public List getMenus(final String sql)  {
		return (List)getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbBiMenu> getAllMenus() {
		List<TbBiMenu> menuList = new LinkedList<TbBiMenu>();
		StringBuffer sql = new StringBuffer();
		sql.append(" from TbBiMenu m where m.isBusiness = '0'");
		
		try {
			menuList = find(sql.toString());
			logger.info("sql:[{}];",sql);
		} catch (Exception e) {
			logger.error("查询所有菜单目录 =[" + sql + "]", e);
		} finally{
			sql = null;
		}
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbBiMenu> getBusinessMenus(String shopCode) {
		List<TbBiMenu> busiMenuList = new LinkedList<TbBiMenu>();

		StringBuffer hql = new StringBuffer();
		hql.append("select m from TbBiMenu m ,TbBiCompanyBusiness cb");
		hql.append(" where cb.busiCode = m.busiCode");
		hql.append(" and cb.companyCode = '"+shopCode+"'");
		hql.append(" and m.isBusiness = '1'");
		
		try {
			busiMenuList = find(hql.toString());
			logger.info("sql:[{}]" ,hql);
		} catch (Exception e) {
			logger.error("查询所有菜单目录 =[" + hql + "]", e);
		} finally{
			hql = null;
		}
		return busiMenuList;
	}

	
}
