package com.nantian.npbs.business.service.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.business.dao.CompanyDao;
import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.dao.ProgramDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

public abstract class WebRequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(WebRequestBusinessService.class);

	// 基Dao
	@Resource
	protected BaseHibernateDao baseHibernateDao;

	// Company Dao
	@Resource
	protected CompanyDao companyDao;

	// 交易流水Dao
	@Resource
	protected TradeDao tradeDao;

	@Resource
	protected ProgramDao programDao;
	// 备付金明细Dao
	@Resource
	protected PrepayDao prepayDao;

	@Resource
	protected CommonPrepay commonPrepay;

	public void webexecute(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("webexecute begin");
		// 公共检查
		// 检查备付金帐户是否正常
		if(!checkPrepay(modelSvcReq, modelSvcReq.getCompany_code())){
			modelSvcAns.setMessage("取帐户信息异常");
			modelSvcAns.setStatus("01");
			return;
		}
		if(modelSvcReq.getCompany_code_sec()!=null){
			if(!checkPrepay(modelSvcReq, modelSvcReq.getCompany_code_sec())){
				modelSvcAns.setMessage("取转入帐户 信息异常");
				modelSvcAns.setStatus("01");
				return;	
			}
		}
			
		dealBusiness(modelSvcReq, modelSvcAns);
		logger.info("webexecute end");
	}

	public  void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
	}

	/**
	 * 检查备付金帐户是否正常
	 * @param modelSvcReq
	 * @param modelSvcAns
	 * @return
	 */
	public boolean checkPrepay(ModelSvcReq modelSvcReq, String companyCode) {
		TbBiPrepay prepay=null;
		try {
			 prepay = commonPrepay.getPrepay(companyCode);
			 modelSvcReq.setTbBiPrepay(prepay);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(prepay.equals(null)){
			return false;
		}else
		return true;
	}

	/**
	 * 取款
	 * 
	 * @param 账号
	 * @param amt
	 * @return 错误码   
	 *  "000000" --->扣款成功
	 *  "000001" --->扣款失败
	 *  "000002" --->提取备付金账户出错
	 *  "000003" --->备付金余额不足
	 */
	public String withdrawalsPrepay(String accNo, Double amt) {
		logger.info("备付金余额修改！");
		String retCode = "000000";

		// String accNo = bm.getShopAccount();
		// Double amt = bm.getAmount();
		Double balance = 0.00;// 余额
		Double useCreAmt = 0.00;// 已使用信用额度
		Double surCreAmt = 0.00;// 剩余信用额度
		String arrearsDate = ""; // 欠费日期

		// 取备付金账户信息
		TbBiPrepay tbPrepay = null;
		try {
			tbPrepay = getPrepay(accNo);
		} catch (Exception e) {
			retCode = "000002";
			logger.error("取该商户备付金账户时出错!accNo=[" + accNo + "];", e);
			return retCode;
		}

		balance = tbPrepay.getAccBalance();
		useCreAmt = tbPrepay.getUseCreamt();
		surCreAmt = tbPrepay.getSurCreamt();
		arrearsDate = tbPrepay.getArrearsDate(); // 欠费日期，日终使用

		logger
				.info(
						"扣账前：消费金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });

//		if (tbPrepay.getCreditAmt() == 0.00 || tbPrepay.getCreditAmt() == null) {
//			retCode = "000003";
//			// ("备付金余额不足!");
//			logger.error("备付金余额不足!");
//			return retCode;
//		}

		if (DoubleUtils.sub(DoubleUtils.sum(balance , surCreAmt) , amt) < 0) {
			retCode = "000003";
			// ("备付金余额不足!");
			logger.error("备付金余额不足!");
			return retCode;
		}
		if (DoubleUtils.sub(balance , amt) >= 0) {
			// balance = balance - amt;
			balance = DoubleUtils.sub(balance, amt);
			tbPrepay.setAccBalance(balance);
		} else {
			// useCreAmt = useCreAmt + (amt - balance);
			// surCreAmt = surCreAmt - (amt - balance);
			useCreAmt = DoubleUtils.sum(useCreAmt, DoubleUtils
					.sub(amt, balance));
			surCreAmt = DoubleUtils.sub(surCreAmt, DoubleUtils
					.sub(amt, balance));

			// 当欠费日期为空时，设置欠费日期。不为空时，说明已经欠费，充值时，如果不欠费，则设置空
			if (arrearsDate == null || "".equals(arrearsDate)) {
				arrearsDate =""; //trancode
				tbPrepay.setArrearsDate(arrearsDate);
			}
			tbPrepay.setAccBalance(0.00);
			tbPrepay.setUseCreamt(useCreAmt);
			tbPrepay.setSurCreamt(surCreAmt);
		}

		try {
			baseHibernateDao.update(tbPrepay);
		} catch (Exception e) {
			logger.error("修改备付金账户余额出错！", e);
			retCode = GlobalConst.RESULTCODE_FAILURE;
			return retCode;
		}
		logger
				.info(
						"扣账后：消费金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });

		logger.info("备付金余额修改成功！");
		return retCode;
	}

	/**
	 * 记备付金明细
	 */
	public boolean addPrepayInfo(TbBiPrepayInfo prepayInfo) {
		logger.info("登记备付金明细！");
		boolean suc = false;
		try {
			 baseHibernateDao.save(prepayInfo);
			suc = true;
		} catch (Exception e) {
			suc = false;
			logger.error("登记备付金明细出错！", e);
		}
		logger.info("登记备付金成功！");
		return suc;

	}
	
	/**
	 * 获取备付金
	 * 
	 * @throws Exception
	 * 
	 */
	public TbBiPrepay getPrepay(String accNo) throws Exception {
		TbBiPrepay tbp = null;
		 tbp = (TbBiPrepay) baseHibernateDao.get(TbBiPrepay.class, accNo); 
		//add by fengyafang start
		//tbp=prepayDao.getWithLock(accNo);
		return tbp;
	}
	
	
	
	
	/**
	 * 存款
	 * 
	 * @param 账号
	 * @param amt
	 * @return 错误码
	 */
	public String depositPrepay(String accNo, Double amt) {
		logger.info("备付金预存！");
		String retCode = "000000";

		Double balance = 0.00;// 余额
		Double useCreAmt = 0.00;// 已使用信用额度
		Double surCreAmt = 0.00;// 剩余信用额度
		Double creditAmt = 0.00;

		// 取备付金账户信息
		TbBiPrepay tbPrepay = null;
		try {
			tbPrepay = getPrepay(accNo);
		} catch (Exception e) {
			retCode = "000002";
			logger.error("取该商户备付金账户时出错!accNo=[" + accNo + "];", e);
			return retCode;
		}

		balance = tbPrepay.getAccBalance();
		useCreAmt = tbPrepay.getUseCreamt();
		surCreAmt = tbPrepay.getSurCreamt();
		creditAmt = tbPrepay.getCreditAmt();

		logger
				.info(
						"存款前：预存金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });

		// 充值金额小于欠费，先缴纳欠费
		if (DoubleUtils.sub(useCreAmt , amt) > 0) {
			useCreAmt = DoubleUtils.sub(useCreAmt, amt);
			tbPrepay.setUseCreamt(useCreAmt);
//取消回去时，可用信用额度可能为负数
			surCreAmt = DoubleUtils.sum(surCreAmt, amt);
			tbPrepay.setSurCreamt(surCreAmt);
			

		} else { // 充值金额大于欠费
			balance = DoubleUtils.sub(DoubleUtils.sum(balance, amt), useCreAmt);
			surCreAmt = creditAmt;
			useCreAmt = 0.00;
			// 设置欠费日期为 null
			tbPrepay.setArrearsDate(null);
		}
		
		tbPrepay.setAccBalance(balance);
		tbPrepay.setUseCreamt(useCreAmt);
		tbPrepay.setSurCreamt(surCreAmt);

		try {
			baseHibernateDao.update(tbPrepay);
			//add by fengyafang start
			//prepayDao.updateWithLock(tbPrepay);
		} catch (Exception e) {
			logger.error("修改备付金账户余额出错！", e);
			retCode = GlobalConst.RESULTCODE_FAILURE;
			return retCode;
		}
		logger
				.info(
						"预存后：预存金额金额  amt=[{}]账户余额  balance=[{}]]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });

		logger.info("备付金预存成功！");
		return retCode;
	}
	
	 
	
	public void setPrepayInfo(TbBiPrepayInfo tbBiPrepayInfo,
			TbBiPrepayInfoId tbBiPrepayInfoId, ModelSvcReq modelSvcReq,
			String company_code) {
		tbBiPrepayInfo.setId(tbBiPrepayInfoId);
		tbBiPrepayInfo.setCompanyCode(company_code);
		tbBiPrepayInfo.setAccno(company_code);
		tbBiPrepayInfo.setBusiCode(modelSvcReq.getBusi_code().substring(0, 3));
		tbBiPrepayInfo.setSystemCode(modelSvcReq.getSystem_code());
		tbBiPrepayInfo.setStatus("00");
		tbBiPrepayInfo.setPosSerial(modelSvcReq.getWeb_serial());
		tbBiPrepayInfo.setSystemSerial(modelSvcReq.getWeb_serial());
		tbBiPrepayInfo.setCustomerno(company_code);//
		tbBiPrepayInfo.setCustomername("");//
		tbBiPrepayInfo.setFlag("2");
		tbBiPrepayInfo.setAmount(Double.parseDouble(modelSvcReq.getAmount()));
		tbBiPrepayInfo.setSummary("");
		tbBiPrepayInfo.setRemark("");
		tbBiPrepayInfo.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
	}

	public void setTrade(TbBiTrade trade, TbBiTradeId tradeId,
			ModelSvcReq modelSvcReq, String company_code) {
		trade.setId(tradeId);// 日期 流水
		trade.setCompanyCode(company_code);//
		trade.setBusiCode(modelSvcReq.getBusi_code().substring(0, 3));//
		trade.setSystemCode(modelSvcReq.getSystem_code());
		trade.setStatus("00");//
		trade.setTradeType("01");
		trade.setPosSerial(modelSvcReq.getWeb_serial());
		trade.setSystemDate(modelSvcReq.getWeb_date());
		trade.setSystemSerial(modelSvcReq.getWeb_serial());
		trade.setCustomerno(company_code);//
		trade.setCustomername("web端交易");//
		trade.setCancelflag("0");// 取消标志
		trade.setAccno(company_code);//
		trade.setAmount(Double.parseDouble(modelSvcReq.getAmount()));//
		trade.setSalary(0.0);//
		trade.setCancelflag("0");
		trade.setTax(0.0);
		trade.setDepreciation(0.0);//
		trade.setOther(0.0);
		trade.setRemark("");
		trade.setLocalDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));//
		trade.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));//
		trade.setPayType("");
		trade.setAccno(company_code);
	}
}
