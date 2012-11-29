package com.nantian.npbs.business.dao;

import java.util.List;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeBusi;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.packet.BusinessMessage;

public interface TradeDao {

	/**
	 * 设置流水对象 流水号，系统日期（清算日期，数据取自系统时间表）流水状态
	 */
	public abstract void setTrade(BusinessMessage bm, TbBiTrade trade);

	/**
	 * 预计流水信息 设置bm 流水号，系统日期（清算日期，数据取自系统时间表）流水状态
	 */
	public abstract boolean addTrade(TbBiTrade trade);

	/**
	 * 查询单笔流水信息
	 * 
	 */
	public abstract TbBiTrade findTrade(BusinessMessage bm);
	
	/**
	 * 通过SQL查询
	 */
	public List queryBySQL(String sql);

	/**
	 * 更新交易流水记录
	 * 
	 * @param updateSql
	 */
	public abstract boolean updateTrade(String updateSql);

	
	/**
	 * 查询pos最大流水号
	 * @param date
	 * @param shopCode
	 */
	public abstract String selectMaxPosSeq(final String date,final String shopCode);
	
	/**
	 * 查询单笔流水记录
	 * 
	 * @param radeDate
	 *            交易时间
	 * @param pbSerial
	 */
	public abstract TbBiTrade getTradeById(String tradeDate, String pbSerial);

	/**
	 * 修改流水状态
	 * 
	 * @param radeDate
	 *            交易时间
	 * @param pbSerial
	 *            交易流水号
	 * @param status
	 *            状态
	 * @param systemSerial
	 *            第三方流水
	 */
	public abstract boolean updateTradeStatus(String tradeDate,
			String pbSerial, String status, String systemSerial);

	/**
	 * 更新交易流水
	 * 
	 * @param tradeDate
	 * @param pbSerial
	 * @param status
	 * @param systemSerial
	 * @param cancelStatus
	 * @return
	 */
	public boolean updateTradeStatus(final String tradeDate,
			final String pbSerial, final String status,
			final String systemSerial, final String cancelStatus);

	/**
	 * 流水列表查询
	 */
	public abstract List<TbBiTrade> findTradeList(String queryString);

	/**
	 * 设置流水对照表信息
	 */
	public abstract void setTradeContrast(BusinessMessage bm,
			TbBiTradeContrast tradeContrast);

	/**
	 * 插入流水对照表
	 */
	public abstract boolean addTradeContrast(TbBiTradeContrast tradeContrast);

	/**
	 * 查询流水对照表
	 */
	List<TbBiTradeContrast> findTradeContrastList(String sql);

	/**
	 * 查询数据信息
	 * 
	 * @param sql
	 * @return
	 */
	public abstract List findInfoList(String sql);
	

	/**
	 * 单流水对照记录表查询
	 * @param tradeDate     交易日期
	 * @param origPbSerial  原缴费交易流水
	 * @param tradeType     交易类型 
	 * @return              对照记录实体
	 */
	
	public TbBiTradeContrast findUniqueTradeContrast(final String tradeDate,
			final String origPbSerial,final String tradeType);
	
	
	/**
	 * 修改流水状态
	 * 
	 * @param ra            交易时间
	 * @param pbSerial      交易流水号
	 * @param status        状态	
	 */
	public abstract boolean updateTradeStatus(final String tradeDate,
			final String pbSerial, final String status);

	/**
	 * 添加备付金修改记录表
	 * 
	 * @param tbBiTradeBusi
	 * @return
	 * @throws Exception
	 */
	public abstract boolean addTbBiTradeBusi(TbBiTradeBusi tbBiTradeBusi);

	
	/**
	 * 修改流水关联表
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	boolean updateTradeContrast(String sql);
	
	 /**
	  * 
	  * @param tradeDate
	  * @param pbSerial
	  * @param webDate
	  * @param webSerial
	  * @return
	  */
	public abstract TbBiTrade getTradeByDateSerial(String tradeDate, String pbSerial,String webDate,String webSerial);
}