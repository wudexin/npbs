package com.nantian.npbs.business.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiCompanyMessage;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.business.model.TbSmPsamCompanyRef;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.core.orm.impl.GenericHibernateDao;

/**
 * PsamCompanyDao
 * 
 * @author jxw
 * 
 */
@Transactional
@Repository("psamCompanyDao")
public class PsamCompanyDaoImpl  extends BaseHibernateDao implements PsamCompanyDao {

	final Logger logger = LoggerFactory.getLogger(PsamCompanyDaoImpl.class);

	@Override
	public String findPsamCompanyRef(String psamCardNo) {
		
		Object obj = null;
		try {
			obj = get(TbSmPsamCompanyRef.class, psamCardNo);
		} catch (Exception e) {
			logger.error("取PSAM卡、商户关联表出错!", e);
		}
		if (obj != null) {
			TbSmPsamCompanyRef tbSmPsamCompanyRef = (TbSmPsamCompanyRef) obj;
			return tbSmPsamCompanyRef.getCompanyCode();
		} else {
			return null;
		}
	}

	
}
