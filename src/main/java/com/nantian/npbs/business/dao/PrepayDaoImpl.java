package com.nantian.npbs.business.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nantian.npbs.business.model.TbBiFormulaDetail;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;

/**
 * 备付金
 * 
 * @author 7tianle
 * 
 */
@Repository(value = "prepayDao")
public class PrepayDaoImpl extends BaseHibernateDao implements PrepayDao {

	final Logger logger = LoggerFactory.getLogger(PrepayDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.business.dao.PrepayDao#addPrepay()
	 */
	@Override
	public void addPrepay() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.business.dao.PrepayDao#resetPSW()
	 */
	@Override
	public boolean resetPSW(String accNo) throws Exception {
		//重置密码
		String passwd = DigestUtils.md5Hex(GlobalConst.PASSWD_INIT + accNo);
		
		TbBiPrepay tbBiPrepay = getPrepay(accNo);
		tbBiPrepay.setAccpwd(passwd);
		tbBiPrepay.setPwdFlag("1");
		
		update(tbBiPrepay);
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.dao.PrepayDao#getPrepay(java.lang.String)
	 */
	@Override
	public TbBiPrepay getPrepay(String accNo) throws Exception {
		TbBiPrepay tbp = null;
		tbp = (TbBiPrepay) get(TbBiPrepay.class, accNo);
		return tbp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.business.dao.PrepayDao#dealPrepay(boolean)
	 */
	@Override
	public void dealPrepay(boolean flag) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.PrepayDao#getTbBiFormulaDetail(java.math
	 * .BigDecimal, double)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public TbBiFormulaDetail getTbBiFormulaDetail(final BigDecimal formulaId,
			final double amount) throws Exception {

		TbBiFormulaDetail tbBiFormulaDetail = null;
		final String hql = "from TbBiFormulaDetail fd where fd.id.formulaId = ? and fd.amountBegin <= ? and fd.amountEnd > ? ";
		tbBiFormulaDetail = (TbBiFormulaDetail) getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setBigDecimal(0, formulaId);
						query.setDouble(1, amount);
						query.setDouble(2, amount);
						return query.uniqueResult();
					}
				});

		return tbBiFormulaDetail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.PrepayDao#getShopPackageId(java.lang.String
	 * )
	 */
	@Override
	public BigDecimal getShopPackageId(String shopCode) throws Exception {
		// String sql =
		// "select c.PACKAGE_ID from TB_BI_COMPANY c where c.COMPANY_CODE = '" +
		// shopCode + "'";
		final String hql = "select c.packageId from TbBiCompany c where c.companyCode = '"
				+ shopCode + "'";
		String result = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						return query.uniqueResult().toString();
					}
				});
		return BigDecimal.valueOf(Double.parseDouble(result));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.PrepayDao#getFormulaId(java.math.BigDecimal
	 * , java.lang.String)
	 */
	@Override
	public BigDecimal getFormulaId(final BigDecimal packageid,
			final String busiCode) throws Exception {
		// String sql =
		// "select spf.FORMULA_ID from TB_BI_SALLARY_PACKAGE_FORMULA spf where spf.PACKAGE_ID = ? and spf.BUSI_CODE  = ?";
		final String hql = "select spf.id.formulaId from TbBiSallaryPackageFormula spf where spf.id.packageId = ? and spf.id.busiCode  = ?";
		String result = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setBigDecimal(0, packageid);
						query.setString(1, busiCode);
						return query.uniqueResult().toString();
					}
				});
		return BigDecimal.valueOf(Double.parseDouble(result));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.PrepayDao#checkRemunerationByShopBusiCode
	 * (java.math.BigDecimal, java.lang.String)
	 */
	@Override
	public boolean checkRemunerationByShopBusiCode(final BigDecimal formulaId,
			final String busiCode) throws Exception {
		Integer count = null;
		// String sql =
		// "select count(*) from TB_BI_FORMULA f where f.FORMULA_ID = ? and f.BUSI_CODE = ?";
		final String hql = "select count(*) from TbBiFormula f where f.formulaId = ? and f.busiCode = ?";
		String num = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setBigDecimal(0, formulaId);
						query.setString(1, busiCode);
						return query.uniqueResult().toString();
					}
				});
		count = Integer.valueOf(num);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.PrepayDao#searchPreAccnoBySA(java.lang.
	 * String)
	 */
	@Override
	public String searchPreAccnoBySA(final String shopAccount) {
		final String sql = "select t.RESACCNO from TB_BI_COMPANY t where t.COMPANY_CODE = ? and t.PAY_TYPE = '1'";
		String result = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						query.setString(0, shopAccount);
						return query.uniqueResult().toString();
					}
				});
		return result;
	}

	@Override
	public TbBiPrepayLowamount getTbBiPrepayLowamount(String accno)
			throws Exception {
		TbBiPrepayLowamount tbBiPrepayLowamount = null;
		Object obj = null;
		obj = get(TbBiPrepayLowamount.class, accno);
		if (null != obj) {
			tbBiPrepayLowamount = (TbBiPrepayLowamount) obj;
			return tbBiPrepayLowamount;
		} else {
			return null;
		}
	}

	@Override
	public boolean updateTbBiPrepayLowamount(
			TbBiPrepayLowamount tbBiPrepayLowamount) throws Exception {
		try {
			update(tbBiPrepayLowamount);
			return true;
		} catch (Exception e) {
			logger.error("更新错误!", e);
			return false;
		}
	}

	@Override
	public List findPrepayInfoList(final String sql) {
		List result = getHibernateTemplate().execute(
				new HibernateCallback<List>() {
					public List doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						return query.list();
					}
				});
		return result;
	}

	@Override
	public boolean updateTradeStatus(final String tradeDate,
			final String pbSerial, final String status,
			final String systemSerial) {
		logger.info("修改备付金明细流水状态！");
		final String hql = "update TbBiPrepayInfo set status = ? ,systemSerial= ? "
				+ " where id.tradeDate = ? and id.pbSerial = ? ";

		int row = getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, status);
						query.setString(1, systemSerial);
						query.setString(2, tradeDate);
						query.setString(3, pbSerial);
						logger.info("sql:[" + query.getQueryString() + "];");
						int row = query.executeUpdate();
						return row;
					}
				});

		if (row == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<TbBiPrepayInfo> getPrepayInfoList(String hql) {
		logger.info("hql:[" + hql + "];" );
		// 查询流水列表
		List<TbBiPrepayInfo> tbBiPrepayInfo = null;
		try {
			tbBiPrepayInfo = find(hql);
		} catch (Exception e) {
			logger.error("queryString=[" + hql + "]", e);
		}
		return tbBiPrepayInfo;
	}

	@Override
	public int getCountPrepayList(final String sql) {
		String num = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						return query.uniqueResult().toString();
					}
				});
		return Integer.valueOf(num);
	}
	
	// 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	public TbBiPrepay getWithLock(String accno) {
		TbBiPrepay t = (TbBiPrepay) getHibernateTemplate().get(TbBiPrepay.class, accno, LockMode.UPGRADE_NOWAIT);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}
	
	// 更新实体并加锁
	public void updateWithLock(TbBiPrepay entity) {
		getHibernateTemplate().update(entity, LockMode.UPGRADE_NOWAIT);
		this.flush(); // 立即刷新，否则锁不会生效。
	}
	
}
