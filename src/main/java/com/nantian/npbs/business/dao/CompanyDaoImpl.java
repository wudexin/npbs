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
import com.nantian.npbs.core.orm.impl.GenericHibernateDao;

/**
 * Company Dao
 * 
 * @author wangwei
 * 
 */
@Transactional
@Repository("companyDao")
public class CompanyDaoImpl extends GenericHibernateDao<TbBiCompany, String>
		implements CompanyDao {

	final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.ShopDao#checkShopBindBusiness(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public boolean checkShopBindBusiness(final String shopCode,
			final String tranCode) throws Exception {

		Integer count = 0;
		final String hql = "select count(*) from TbBiCompanyBusiness b where b.companyCode = ? and b.busiCode = ? ";

		String num = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, shopCode);
						query.setString(1, tranCode);
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

	/*----------add 20111021 by wzd------ start----*/
	/**
	 * 查询商户消息下载信息
	 * @param shopCode -->商户号
	 * @return 商户下载信息内容list
	 */
	@Override
	public List getCompanyDownLoadMessageByShopCode(final String shopCode, final String isSend) {
				
		final String sql = "select m.message from Tb_Bi_Company_Message m where m.company_code = ? and m.issend = ?";
		List listResult = getHibernateTemplate().execute(
				new HibernateCallback<List>() {
					public List doInHibernate(Session session) 
						throws HibernateException, SQLException{
						Query query = session.createSQLQuery(sql);
						query.setString(0,shopCode);
						query.setString(1,isSend);
						logger.info("sql:[{}]", query.getQueryString());
						return query.list();					
					}
				}
		);
		return listResult;
	}

	/**
	 * 检测是否有商户消息下载内容
	 *@param shopCode -->商户号
	 *@return 是否有下载内容:有-->true,否则为false 
	 */
	@Override
	public boolean isDownloadMessage(String shopCode) {
		List<Object> list = this.getCompanyDownLoadMessageByShopCode(shopCode, "0");
		if(list != null && list.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 *更新TbBiCompanyMessage表的商户消息下载标志位为sendFlag
	 * @param sendFlag 是否下载标志位
	 * @return  是否更新成功
	 */
	@Override
	public boolean updateTbBiCompanyMessage(final String shopCode,final String sendFlag) {
		logger.info("修改下载状态！" );
		final String sql = "update Tb_Bi_Company_Message m set m.issend = ?"
				+ " where m.company_code = ? ";

		int row = getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						query.setString(0,sendFlag);
						query.setString(1, shopCode);
						logger.info("sql:[{}]", query.getQueryString());
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
	
	/**
	 * 删除商户消息
	 * shopCode-----商户号
	 * msg ------商户消息
	 * sendFlag ----是否已发送：0-未发送；1-已发送
	 */
	@Override
	public boolean deleteTbBiCompanyMessage(final String shopCode,final String msg,final String sendFlag) {
		logger.info("删除下载过得消息！" );
		final String sql = "delete Tb_Bi_Company_Message"
				+ " where company_code = ? and issend = ? ";
		try {
			getHibernateTemplate().execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session.createSQLQuery(sql);
							query.setString(0, shopCode);
							query.setString(1, "1");
							logger.info("sql:[{}]", query.getQueryString());
							int row = query.executeUpdate();
							return row;
						}
					});
			return true;
		}catch(Exception e) {
			logger.info("系统内部出错了!");
			return false;
		}
		

		
	}
	/*----------add 20111021 by wzd------ end----*/
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.ShopDao#checkCompanyAuthorize(java.lang
	 * .String, java.lang.String, java.lang.String, double)
	 */
	@Override
	public boolean checkCompanyAuthorize(final String shopCode,
			final String busiCode, final String customerNo,
			final double amount, final String date) throws Exception {

		Integer count = null;
		// String sql =
		// "select count(*) from TB_BI_COMPANY_AUTHORIZE where COMPANY_CODE = ? "
		// +
		// " and BUSI_CODE = ? and CUSTOMERNO = ? and AMOUNTMAX = ? and AUTH_DATE =(select s.system_date from TB_SM_SYSDATA s) ";
		if (date == null || date.equals("")) {
			return false;
		}
		final String hql = "select count(*) from TbBiCompanyAuthorize where id.companyCode  = ? "
				+ " and id.busiCode = ? and id.customerno = ? and amountmax = ? and authDate = ? ";

		String num = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, shopCode);
						query.setString(1, busiCode);
						query.setString(2, customerNo);
						query.setDouble(3, amount);
						query.setString(4, date);
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
	 * com.nantian.npbs.business.dao.ShopDao#getCompanyMessageByShopCode(java
	 * .lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TbBiCompanyMessage> getCompanyMessageByShopCode(String shopCode) {
		List<TbBiCompanyMessage> shopMsgs = null; // 商户消息
		String hql = "from TbBiCompanyMessage m where m.id.companyCode = '"
				+ shopCode + "' and m.id.issend='0' ";
		shopMsgs = find(hql);
		return shopMsgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.CompanyDao#updateCompanyInfo(com.nantian
	 * .npbs.packet.BusinessMessage)
	 */
	@Override
	public boolean updateCompanyInfo(TbBiCompany tbBiCompany) {
		// 修改商户信息
		try {
			update(tbBiCompany);
			return true;
		} catch (Exception e) {
			logger.error("更新错误!", e);
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.dao.CompanyDao#updateComCheckStat(com.nantian.npbs.business.model.TbBiCompany, java.lang.String)
	 */
	@Override
	public boolean updateComCheckStat(TbBiCompany tbBiCompany,String flag) {
		// 修改商户签到状态
		tbBiCompany.setCheckstat(flag);// 修改商户状态为签到
		logger.info("商户状态：{}" ,tbBiCompany.getCheckstat());
		try {
			updateCompanyInfo(tbBiCompany);
		} catch (Exception e) {
			logger.error("更新商户签到状态出错!" + e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateMenuFlag(TbBiCompany tbBiCompany, String flag) {
		//修改商户单更新标志
		tbBiCompany.setMenuFlag(flag);// 修改商户单更新标志
		logger.info("商户状态：{}", tbBiCompany.getMenuFlag());
		try {
			updateCompanyInfo(tbBiCompany);
		} catch (Exception e) {
			logger.error("更新商户单更新标志" + e);
			return false;
		}
		return true;
	}
}
