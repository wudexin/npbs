package com.nantian.npbs.core.orm.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbSmSysdata;

@Component(value = "baseHibernateDao")
public class BaseHibernateDao extends GenericHibernateDao<Object, Serializable> {

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public String getSystemDate() {
		String date = "";
		String hql = "select id.systemDate from TbSmSysdata where 1=1 ";
		try {
			@SuppressWarnings("unchecked")
			List<String> list = find(hql);
			for (String str : list) {
				date = str;
			}
		} catch (Exception e) {
			logger.error("select id.systemDate from TbSmSysdata where 1=1 ", e);
		}
		return date;

	}
	
	/**
	 * 获取系统日期状态表
	 * 
	 * @return
	 */
	public TbSmSysdata getSysData() {
		TbSmSysdata sysData = null;
		String hql = " from TbSmSysdata where 1=1 ";
		try {
			@SuppressWarnings("unchecked")
			List<TbSmSysdata> list = find(hql);
			for (TbSmSysdata system : list) {
				sysData = system;
			}
		} catch (Exception e) {
			logger.error("select id.systemDate from TbSmSysdata where 1=1 ", e);
		}
		return sysData;

	}

	/**
	 * 生成流水号
	 * 原：
	 * CREATE SEQUENCE PBCHNLNO MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START  WITH 1 CACHE 100 ORDER CYCLE ;
	 * 
	 * 新：
	 * CREATE SEQUENCE  PBJOURNO  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  CYCLE ;
	 * 
	 * @return
	 */
	public String getNumber() {
		String num = null;
//		final String sql = "select (select s.system_date from TB_SM_SYSDATA s) || substr('000000' || "
//				+ "PBJOURNO.nextval,length('000000' || PBJOURNO.nextval) -5,6) from dual";
		
		final String sql = "select substr((select s.system_date from TB_SM_SYSDATA s), 3) || " +
				"substr('00000000' || PBJOURNO.nextval,length('00000000' || PBJOURNO.nextval) -7,8) " +
				"from dual";

		num = getHibernateTemplate().execute(new HibernateCallback<String>() {
			public String doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.uniqueResult().toString();
			}
		});

		return num;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object get(Class claz, Serializable id) {
		return getHibernateTemplate().get(claz, id);
	}

	public void excuteSQL(final String sql) {
		try {
			getHibernateTemplate().execute(new HibernateCallback<Object>() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createSQLQuery(sql);
					return query.executeUpdate();
				}
			});

		} catch (Exception e) {
			logger.error("执行SQL错误：" + sql, e);
		}
	}
}
