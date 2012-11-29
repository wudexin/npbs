package com.nantian.npbs.business.dao;

import java.math.BigDecimal;
import java.util.List;

import com.nantian.npbs.business.model.TbBiFormulaDetail;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;

public interface PrepayDao {

	/**
	 * 增加备付金
	 */
	public abstract void addPrepay();

	/**
	 * 根据商户号查询备付金帐号
	 */
	public abstract String searchPreAccnoBySA(String shopAccount);

	/**
	 * 重置密码
	 */
	public boolean resetPSW(String accNo) throws Exception;

	/**
	 * 0取款，1存款
	 * 
	 * @param flag
	 */
	public abstract void dealPrepay(boolean flag);

	/**
	 * 获取酬金公式详细
	 * 
	 * @param shId
	 * @param ywId
	 * @param amount
	 * @throws Exception
	 */
	public abstract TbBiFormulaDetail getTbBiFormulaDetail(
			BigDecimal formulaId, double amount) throws Exception;

	/**
	 * 获取商户的酬金套餐id
	 * 
	 * @param shopCode
	 * @return
	 */
	public abstract BigDecimal getShopPackageId(String shopCode)
			throws Exception;

	/**
	 * 获取id
	 * 
	 * @param packageid
	 * @param busiCode
	 * @return
	 */
	public abstract BigDecimal getFormulaId(BigDecimal packageid,
			String busiCode) throws Exception;

	/**
	 * 酬金套餐校验(商户是否存在此套餐)
	 * 
	 * @param formulaId
	 * @param busiCode
	 * @return
	 */
	public abstract boolean checkRemunerationByShopBusiCode(
			BigDecimal formulaId, String busiCode) throws Exception;

	/**
	 * 获取备付金参数
	 * 
	 * @param accno
	 * @return
	 * @throws Exception
	 */
	public abstract TbBiPrepayLowamount getTbBiPrepayLowamount(String accno)
			throws Exception;

	/**
	 * 修改备付金参数表
	 * 
	 * @param tbBiPrepayLowamount
	 * @return
	 * @throws Exception
	 */
	public abstract boolean updateTbBiPrepayLowamount(
			TbBiPrepayLowamount tbBiPrepayLowamount) throws Exception;

	/**
	 * 获取备付金明细列表
	 * 
	 * @param sql
	 * @return
	 */
	public abstract List findPrepayInfoList(String sql);

	/**
	 * 修改备付金明细流水状态
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
	 * 取得备付金明细列表
	 * 
	 * @param sql
	 * @return
	 */
	public abstract List<TbBiPrepayInfo> getPrepayInfoList(String hql);

	/**
	 * 获取条数
	 * 
	 * @param hql
	 * @return
	 */
	public abstract int getCountPrepayList(String hql) throws Exception;

	/**
	 * 获取备付金
	 * 
	 * @param accNo
	 * @return
	 * @throws Exception
	 */
	public abstract TbBiPrepay getPrepay(String accNo) throws Exception;
	
	
	public TbBiPrepay getWithLock(String accno);
	
	public void updateWithLock(TbBiPrepay entity);

}