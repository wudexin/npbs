package com.nantian.npbs.business.dao;

import java.util.List;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiCompanyMessage;
import com.nantian.npbs.core.orm.GenericDao;

public interface CompanyDao extends GenericDao<TbBiCompany, String> {

	/**
	 * 检查商户与业务是否绑定
	 * 
	 * @param shopCode
	 * @param tranCode
	 * @return
	 */
	public abstract boolean checkShopBindBusiness(String shopCode,
			String tranCode) throws Exception;

	/**
	 * 查询授权额度表是否存在信息
	 * 
	 * @param shopCode
	 *            商户号
	 * @param busiCode
	 *            业务号
	 * @param customerNo
	 *            客户账号
	 * @param amount
	 *            金额 授权日期为当天系统日期
	 * @return
	 */
	public abstract boolean checkCompanyAuthorize(String shopCode,
			String busiCode, String customerNo, double amount, String date)	
			throws Exception;

	/**
	 * 根据商户code查找所有该商户消息
	 * 
	 * @param shopCode
	 * @return
	 */
	public abstract List<TbBiCompanyMessage> getCompanyMessageByShopCode(
			String shopCode);

	/**
	 * 修改商户信息
	 * 
	 * @param bm
	 * @return
	 */
	public boolean updateCompanyInfo(TbBiCompany tbBiCompany);

	/**
	 * 修改商户签到签退状态
	 * 
	 * @param tbBiCompany
	 * @param flag
	 *            0为签退，1为签到
	 * @return
	 */
	public boolean updateComCheckStat(TbBiCompany tbBiCompany, String flag);
	
	/**
	 * 修改商户单更新标志
	 * 
	 * @param tbBiCompany
	 * @param flag
	 *            0为无需更新，1为需要更新
	 * @return
	 */
	public boolean updateMenuFlag(TbBiCompany tbBiCompany, String flag);
	
	/*----------add 20111021 by wzd------ start----*/
	/**
	 * 查询商户消息下载信息
	 * @param shopCode -->商户号
	 * @return 商户下载信息内容list
	 */
	public List getCompanyDownLoadMessageByShopCode(String shopCode, final String isSend);	
	
	
	/**
	 * 检测是否有商户消息下载内容
	 *@param shopCode -->商户号
	 *@return 是否有下载内容:有-->true,否则为false 
	 */
	public boolean isDownloadMessage(String shopCode);

	/**
	 *更新TbBiCompanyMessage表的商户消息下载标志位为sendFlag
	 * @param sendFlag 是否下载标志位
	 * @return  是否更新成功
	 */
	
	public boolean updateTbBiCompanyMessage(String shopCode, String sendFlag);
	
	//2012年5月18日8:53:56添加解决系统跑出的JDBCExceptionReporter
	public boolean deleteTbBiCompanyMessage(String shopCode,String msg, String sendFlag);
	/*----------add 20111021 by wzd------ end ----*/

}