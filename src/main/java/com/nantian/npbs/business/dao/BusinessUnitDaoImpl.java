package com.nantian.npbs.business.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nantian.npbs.core.orm.impl.BaseHibernateDao;

/**
 * 业务对应机构
 * @author 7tianle
 *
 */
@Repository(value="businessUnitDao")
public class BusinessUnitDaoImpl extends BaseHibernateDao implements BusinessUnitDao {

	final Logger logger = LoggerFactory.getLogger(BusinessUnitDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.dao.BusinessUnitDao#addProcess(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addProcess(final String busiCode,final String unitcode) throws Exception{
//		String sql = "UPDATE TB_BI_BUSINESS_UNIT SET PROCESSNOW = PROCESSNOW + 1 WHERE BUSI_CODE = '" + busiCode + "' AND UNITCODE = '" + unitcode+ "'";
//		excuteSQL(sql);
		
		final String hql = "update TbBiBusinessUnit set processnow = processnow + 1 "
				+ " where id.busiCode = ? and id.unitcode = ?";
		int row = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, busiCode);
				query.setString(1, unitcode);
				int row = query.executeUpdate();
				return row;
			}
		});

		if(row == 1){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.dao.BusinessUnitDao#releaseProcess(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean releaseProcess(final String busiCode,final String unitcode)throws Exception{
		//String sql = "UPDATE TB_BI_BUSINESS_UNIT SET PROCESSNOW = PROCESSNOW - 1 WHERE BUSI_CODE = '" + busiCode + "' AND UNITCODE = '" + unitcode+ "'";
	
		final String hql = "update TbBiBusinessUnit set processnow = processnow - 1 "
				+ " where id.busiCode = ? and id.unitcode = ?";
		int row = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, busiCode);
				query.setString(1, unitcode);
				int row = query.executeUpdate();
				return row;
			}
		});

		if(row == 1){
			return true;
		}else{
			return false;
		}
	}

}
