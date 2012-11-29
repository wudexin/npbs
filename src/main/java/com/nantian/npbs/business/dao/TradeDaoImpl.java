package com.nantian.npbs.business.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeBusi;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.packet.BusinessMessage;

/**
 * 交易流水
 * 
 * @author 7tianle
 * 
 */
@Repository(value = "tradeDao")
public class TradeDaoImpl extends BaseHibernateDao implements TradeDao {

	private static Logger logger = LoggerFactory.getLogger(TradeDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#addTrade(com.nantian.npbs.packet
	 * .BusinessMessage)
	 */
	@Override
	public void setTrade(BusinessMessage bm, TbBiTrade trade) {
		TbBiTradeId id = new TbBiTradeId();
		trade.setId(id);

		trade.getId().setTradeDate(bm.getTranDate());
		trade.getId().setPbSerial(bm.getPbSeqno());

		if(GlobalConst.eleChanelCode.equals(bm.getSystemChanelCode())){
			trade.setSystemDate(bm.getMidPlatformDate());
			trade.setCustomerno(bm.getShopCode());
			trade.setCustomername(bm.getShopName());
		}else{
			trade.setPosSerial(bm.getPosJournalNo());
			trade.setCustomerno(bm.getUserCode());
			trade.setCustomername(bm.getUserName());
		}
		
		// 设置对象数据
		// trade.setId(id);
		trade.setCompanyCode(bm.getShopCode());
		trade.setBusiCode(bm.getBusinessType());
		trade.setSystemCode(bm.getSystemChanelCode());
		trade.setStatus(bm.getPbState());
		trade.setTradeType(bm.getTranType());
		
		trade.setAmount(bm.getAmount());
		trade.setSalary(bm.getSalary());
		trade.setCancelflag(bm.getCancelFlag());
		trade.setTax(bm.getTax());
		trade.setDepreciation(bm.getDepreciation());
		trade.setOther(bm.getOther());
		trade.setPayType(bm.getShop().getPayType());
		trade.setAccno(bm.getShop().getResaccno());
		trade.setRemark(bm.getRemark());
		trade.setLocalDate(bm.getLocalDate());
		trade.setTradeTime(bm.getLocalDate() + bm.getLocalTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#addTrade(com.nantian.npbs.packet
	 * .BusinessMessage)
	 */
	@Override
	public boolean addTrade(TbBiTrade trade) {
		logger.info("插入流水表!系统日期: {}，流水号 :{}，pos流水号", new Object[]{trade.getId().getTradeDate(),trade.getId().getPbSerial(),trade.getPosSerial()});
		try {
			save(trade);
		} catch (Exception e) {
			logger.error("插入流水表失败!系统日期: " + trade.getId().getTradeDate()
					+ "，流水号 :" + trade.getId().getPbSerial(), e);
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#findTrade(com.nantian.npbs.packet
	 * .BusinessMessage)
	 */
	@Override
	public TbBiTrade findTrade(BusinessMessage bm) {
		return getTradeById(bm.getTranDate(), bm.getPbSeqno());
	}
	
	/**
	 * SQL查询
	 * @param sql SQL语句
	 */
	@SuppressWarnings("unchecked")
	public List queryBySQL(final String sql)  {
		logger.info("SQL - "+sql);
		return (List)getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#updateTrade(com.nantian.npbs.business
	 * .model.TbBiTrade)
	 */
//	@Override
//	public boolean updateTrade(TbBiTrade trade) {
//		try {
//			update(trade);
//			return true;
//		} catch (Exception e) {
//			logger.error("更新错误!", e);
//			return false;
//		}
//
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#getTradeById(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public TbBiTrade getTradeById(String tradeDate, String pbSerial) {
		logger.info("根据日期和流水号取流水表，交易日期[{}],流水号[{}]", new Object[] { tradeDate, pbSerial });
		TbBiTradeId id = new TbBiTradeId(tradeDate, pbSerial);
		Object obj = null;
		try {
			obj = get(TbBiTrade.class, id);

		} catch (Exception e) {
			logger.error("取流水表出错!", e);
		}
		if (obj != null) {
			TbBiTrade trade = (TbBiTrade) obj;
			return trade;
		} else {
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#updateTrade(com.nantian.npbs.business
	 * .model.TbBiTrade)
	 */
	@Override
	public boolean updateTrade(final String sql) {

		logger.info("修改流水状态！[{}]", sql);

		int row = getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(sql);
						int row = query.executeUpdate();						return row;
					}
				});
		if (row == 1) {
			return true;
		} else {
			return false;
		}
	
	}

	@Override
	public boolean updateTradeStatus(final String tradeDate,
			final String pbSerial, final String status,
			final String systemSerial) {
		logger.info("修改流水状态！");
		final String hql = "update TbBiTrade set status = ? ,systemSerial= ? "
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

	@Override
	public boolean updateTradeStatus(final String tradeDate,
			final String pbSerial, final String status,
			final String systemSerial, final String cancelStatus) {
		logger.info("修改流水状态！");
		// final String hql =
		// "update TbBiTrade set status = ? ,systemSerial= ?, cancelflag = ? " +
		// " where id.tradeDate = ? and id.pbSerial = ?  ";
		final String hql = "update TbBiTrade set status = ? , cancelflag = ? "
				+ " where id.tradeDate = ? and id.pbSerial = ?  ";
		int row = getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, status);
						// query.setString(1, systemSerial);
						query.setString(1, cancelStatus);
						query.setString(2, tradeDate);
						query.setString(3, pbSerial);
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
	 * 修改流水状态
	 * 
	 * @param ra            交易时间
	 * @param pbSerial      交易流水号
	 * @param status        状态	
	 */
	@Override
	public boolean updateTradeStatus(final String tradeDate,
			final String pbSerial, final String status){
		logger.info("修改流水状态！{}", getClass().getName());		
		final String hql = "update TbBiTrade set status = ? "
				+ " where id.tradeDate = ? and id.pbSerial = ?  ";
		int row = getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, status);						
						query.setString(1, tradeDate);
						query.setString(2, pbSerial);
						logger.info("sql:[{}];", query.getQueryString(),
								getClass().getName());
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
	public List<TbBiTrade> findTradeList(String queryString) {
		// 查询流水列表
		List<TbBiTrade> tbBiTradeList = null;
		try {
			tbBiTradeList = find(queryString);
		} catch (Exception e) {
			logger.error("queryString=[" + queryString + "]", e);
		}
		return tbBiTradeList;
	}

	@Override
	public List<TbBiTradeContrast> findTradeContrastList(String sql) {

		logger.info("sql:[{}]", sql);
		// 查询流水列表
		List<TbBiTradeContrast> tbBiTradeList = null;
		try {
			tbBiTradeList = find(sql);
		} catch (Exception e) {
			logger.error("queryString=[" + sql + "]", e);
		}
		return tbBiTradeList;
	}
	
	/**
	 * 单流水对照记录表查询
	 * @param tradeDate 交易日期
	 * @param origPbSerial 原缴费交易流水
	 * @param tradeType     交易类型 
	 * @return   对照记录实体
	 */
	
	public TbBiTradeContrast findUniqueTradeContrast(final String tradeDate,final String origPbSerial,final String tradeType) {
		final String  sql = "from TbBiTradeContrast where id.tradeDate = ? and id.oriPbSerial = ? and tradeType = ? ";
		TbBiTradeContrast result = getHibernateTemplate().execute(
				new HibernateCallback<TbBiTradeContrast>() {
					public TbBiTradeContrast doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(sql);
						query.setString(0, tradeDate);
						query.setString(1,origPbSerial);
						query.setString(2, tradeType);
						logger.info("sql:[{}]",query.getQueryString());
						return (TbBiTradeContrast)query.uniqueResult();
					}
				});
		return result;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#findTradeInfo(java.lang.String)
	 */
	@Override
	public List findInfoList(final String sql) {
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
	public boolean addTradeContrast(TbBiTradeContrast tradeContrast) {
		logger.info("插入流水对照表!系统日期: {}，流水号 :{}，原交易流水：{}", new Object[] {
				tradeContrast.getId().getTradeDate(),
				tradeContrast.getId().getPbSerial(),
				tradeContrast.getId().getOriPbSerial() });
		try {
			save(tradeContrast);
		} catch (Exception e) {
			logger.error("插入流水对照表失败!系统日期: "
					+ tradeContrast.getId().getTradeDate() + "，流水号 :"
					+ tradeContrast.getId().getPbSerial() + "，原交易流水："
					+ tradeContrast.getId().getOriPbSerial(), e);
			return false;
		}

		return true;
	}

	@Override
	public void setTradeContrast(BusinessMessage bm,
			TbBiTradeContrast tradeContrast) {
		TbBiTradeContrastId id = new TbBiTradeContrastId();
		tradeContrast.setId(id);

		tradeContrast.getId().setOriPbSerial(bm.getOrigPbSeqno());
		tradeContrast.getId().setPbSerial(bm.getPbSeqno());
		tradeContrast.getId().setTradeDate(bm.getTranDate());

		tradeContrast.setTradeType(bm.getTranType());

	}

	@Override
	public boolean addTbBiTradeBusi(TbBiTradeBusi tbBiTradeBusi) {
		try {
			save(tbBiTradeBusi);
		} catch (Exception e) {
			logger.error("登记备付金密码修改表出错！");
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.business.dao.TradeDao#selectMaxPosSeq(java.lang.String)
	 */
	@Override
	public String selectMaxPosSeq(final String date,final String shopCode) {
		final String sql = "select coalesce(max(pos_serial),'000000') from Tb_Bi_Trade where trade_date = '"+date+"' and company_code = '"+shopCode+"' ";
		String result = getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						return query.uniqueResult().toString();
					}
				});
		return result;
	}
	
	@Override
	public boolean updateTradeContrast(final String sql) {

		logger.info("修改流水关联表！[{}]", sql);

		int row = getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
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

	
	

	// /* (non-Javadoc)
	// * @see
	// com.nantian.npbs.business.dao.TradeDao#findNameByBusiType(java.lang.String)
	// */
	// @Override
	// public String findNameByBusiType(final String type) {
	// final String sql =
	// "select t.BUSI_NAME from TB_BI_BUSINESS t where t.BUSI_CODE = ?";
	// String result = getHibernateTemplate().execute(new
	// HibernateCallback<String>() {
	// public String doInHibernate(Session session)
	// throws HibernateException, SQLException {
	// Query query = session.createSQLQuery(sql);
	// query.setString(0, type);
	// return query.uniqueResult().toString();
	// }
	// });
	// return result;
	// }
	
	
	@Override
	public   TbBiTrade getTradeByDateSerial(String tradeDate, String pbSerial,String webDate,String webSerial){
		logger.info("根据日期和流水号取流水表，原交易日期[{}],原交易流水号[{}],原E站日期[{}],原E站流水号[{}]", new Object[] { tradeDate, pbSerial,webDate ,webSerial});
		TbBiTrade trade1=new TbBiTrade();
		TbBiTradeId id = new TbBiTradeId(tradeDate, pbSerial);
		trade1.setId(id);
		trade1.setSystemDate(webDate);
		trade1.setSystemSerial(webSerial);
		String[] string={"TRADE_DATE","PB_SERIAL","SYSTEM_DATE","SYSTEM_SERIAL"};
		String sql="select tt from TbBiTrade tt where tt.id.tradeDate='"+tradeDate+"' and tt.id.pbSerial='"+pbSerial+"' and tt.systemDate='"+webDate+"' and tt.systemSerial='"+webSerial+"'";
		Object obj = null;
		try {
			
		 List list = getHibernateTemplate().find(sql);
			//obj = get(TbBiTrade.class, id);
			if (list.size() == 1) {
				obj=list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("取流水表出错!", e);
		}
		if (obj != null) {
			TbBiTrade trade = (TbBiTrade) obj;
			return trade;
		} else {
			return null;
		}
	 
	}
}
