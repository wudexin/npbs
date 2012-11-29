package com.nantian.npbs.business.service.answer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.business.dao.BusinessUnitDao;
import com.nantian.npbs.business.dao.CompanyDao;
import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.dao.ProgramDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiBusinessUnit;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.core.service.IAnswerBusinessService;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

public abstract class WebAnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(WebAnswerBusinessService.class);

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

	@Resource
	protected BusinessUnitDao businessUnitDao;

	protected TbBiBusinessUnit businessUnit; // 业务对应机构

	public void execute(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {

		logger.info("开始执行响应处理");
		// 由于流水是一个公共处理，不论失败成功均需要处理
		// 先修改流水，再进行账务处理避免非同一事务可能导致业务处理成功流水修改失败
		if ("1".equals(modelSvcReq.getSeqnoFlag())) {
			if (!modelSvcAns.getStatus().equals(GlobalConst.RESULTCODE_FAILURE )
					&&!modelSvcAns.getStatus().equals("01")
			&&!modelSvcAns.getStatus().equals(GlobalConst.TRADE_STATUS_CARD_ORIG )		
			) {
				editTradeState(modelSvcReq, modelSvcAns);
			}  
		}
		// 失败不需要进行业务处理
		if (!GlobalConst.RESULTCODE_SUCCESS.equals(modelSvcAns.getStatus())) {
			return;
		}
		dealBusiness(modelSvcReq, modelSvcAns);

		logger.info("WebAnswerBusinessService响应完成 -----响应码[{}],响应信息[{}]",
				modelSvcAns.getStatus(), modelSvcAns.getMessage());
	}

	/**
	 * 修改流水表状态
	 */
	protected boolean editTradeState(ModelSvcReq modelSvcReq,
			ModelSvcAns modelSvcAns) {
		logger.info("修改流水状态");
		logger.info("交易日期：[{}],交易流水：[{}]。", modelSvcReq.getTrade_date(),
				modelSvcReq.getPb_serial());
		// 查询原流水信息
		TbBiTrade trade = (TbBiTrade) baseHibernateDao.get(TbBiTrade.class,
				modelSvcReq.getTradeId());
		if (trade == null) {
			logger.info("查询流水表出错！");
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
			modelSvcAns.setMessage("查询流水表出错");
			return false;
		}
		// 流水状态根据第业务流程返回修改
		logger.info("日期：[{}],流水号：[{}],原流水状态：[{}],交易流程控制状态：[{}],交易流程信息：[{}]",
				new Object[] { trade.getId().getTradeDate(),
						trade.getId().getPbSerial(), trade.getStatus(),
						modelSvcAns.getStatus(), modelSvcAns.getMessage() });

		trade.setStatus("00");

		// 更新流水表
		// boolean suc = dao.updateTrade(trade);
		String sql = "update TbBiTrade set status = '" + trade.getStatus()
				+ "' where id.tradeDate = '" + trade.getId().getTradeDate()
				+ "' and id.pbSerial = '" + trade.getId().getPbSerial() + "' ";
		boolean suc = tradeDao.updateTrade(sql);
		if (suc != true) {
			logger.info("修改流水状态失败！");
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
			modelSvcAns.setMessage("修改流水状态失败,交易失败");
			return false;
		}
		modelSvcAns.setPb_serial(modelSvcReq.getTradeId().getPbSerial());
		modelSvcAns.setCompany_code_fir(trade.getCompanyCode());
		modelSvcAns.setAmount(trade.getAmount().toString());
		modelSvcAns.setTrade_date(trade.getId().getTradeDate());
		modelSvcAns.setStatus(GlobalConst.RESULTCODE_SUCCESS);
		logger.info("流水状态更新成功！");
		return true;

	}

	public abstract void dealBusiness(ModelSvcReq modelSvcReq,
			ModelSvcAns modelSvcAns);

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
		tbBiPrepayInfo.setCustomername("web端交易");// 
		tbBiPrepayInfo.setSummary("");
		tbBiPrepayInfo.setRemark("");
		tbBiPrepayInfo.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
	}

	/**
	 * 扣备付金并登记备付金明细
	 * 
	 * @param ControlMessage
	 * @param BusinessMessage
	 * 
	 */
	public boolean payPrepay(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("备付金处理开始！");

		String accNo = modelSvcReq.getCompany_code_fir();
		Double amt = Double.parseDouble(modelSvcReq.getAmount());
		String retCode = GlobalConst.RESULTCODE_SUCCESS;

		logger.info(" accNo=[{}];amt=[{}]", new Object[] { accNo, amt });

		retCode = withdrawalsPrepay(accNo, amt, modelSvcReq,modelSvcAns);
		if (GlobalConst.RESULTCODE_SUCCESS.equals(retCode) != true) {
			String retMsg = null;
			switch (Integer.parseInt(retCode)) {
			case 000001:
				retMsg = "修改备付金余额出错!";
				break;
			case 000002:
				retMsg = "取该商户备付金账户时出错!";
				break;
			case 000003:
				retMsg = "备付金余额不足!";
				break;
			default:
				retMsg = "取款失败！";
				break;
			}
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
			modelSvcAns.setMessage((retMsg));
			modelSvcAns.setAcc_balance_fir(modelSvcReq.getAmount());
			
			logger.error(retMsg);
			return false;
		}

		// 查询备付金当前余额
		logger.info("商户号: [{}];备付金账号: [{}];"
				+ modelSvcReq.getCompany_code_fir(), modelSvcReq
				.getCompany_code_fir());
		TbBiPrepay tbBiPrepay = null;
		try {
			Object obj1 = baseHibernateDao.get(TbBiPrepay.class, modelSvcReq
					.getCompany_code_fir());
			if (obj1 != null) {
				tbBiPrepay = (TbBiPrepay) obj1;
				// 设置
				modelSvcAns.setTbBiPrepay(tbBiPrepay);
				modelSvcAns.setAcc_balance_fir(tbBiPrepay.getAccBalance()
						.toString());// 备付金余额
			} else {
				modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
				modelSvcAns.setMessage(("备付金查询出错!"));
				logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"
						+ modelSvcReq.getCompany_code_fir(), modelSvcReq
						.getCompany_code_fir());
				return false;
			}
		} catch (Exception e) {
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
			modelSvcAns.setMessage(("查询您的备付金账户出错,请联系管理员!"));
			logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"
					+ modelSvcReq.getCompany_code_fir(), modelSvcReq
					.getCompany_code_fir());
			return false;
		}
		// 设置备付金明细--在服务端调用好呢还是在这里调用
		// 存取款操作应该联动登记明细，但是这里取不到流水号和商户名称等信息
		TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
		TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
		tbPrepayId.setPbSerial(modelSvcReq.getPb_serial());
		tbPrepayId.setTradeDate(modelSvcReq.getTrade_date());
		setPrepayInfo(prepayInfo, tbPrepayId, modelSvcReq, modelSvcReq
				.getCompany_code_fir());
		prepayInfo.setFlag("2");// 1-存款，2-取款
		prepayInfo.setAmount(amt);
		if (DoubleUtils.sub(tbBiPrepay.getAccBalance(), 0) > 0.0) {
			prepayInfo.setBal(tbBiPrepay.getAccBalance());
		} else {
			prepayInfo.setBal(-tbBiPrepay.getUseCreamt());
		}
		prepayInfo.setSummary("取款");
		prepayInfo.setRemark("");

		// 登记备付金明细
		boolean suc = false;
		suc = addPrepayInfo(prepayInfo);
		if (suc == false) {
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
			modelSvcAns.setMessage(("备付金明细插入失败！"));
			logger.error("备付金明细插入失败！");
			return false;
		}
		modelSvcAns.setMessage("交易成功");
		modelSvcAns.setStatus(GlobalConst.RESULTCODE_SUCCESS);
		return true;
	}

	/**
	 * 取款
	 * 
	 * @param 账号
	 * @param amt
	 * @return 错误码 "000000" --->扣款成功 "000001" --->扣款失败 "000002" --->提取备付金账户出错
	 *         "000003" --->备付金余额不足
	 */
	public String withdrawalsPrepay(String accNo, Double amt,
			ModelSvcReq modelSvcReq,ModelSvcAns modelSvcAns) {
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

		// if (tbPrepay.getCreditAmt() == 0.00 || tbPrepay.getCreditAmt() ==
		// null) {
		// retCode = "000003";
		// // ("备付金余额不足!");
		// logger.error("备付金余额不足!");
		// return retCode;
		// }

		if (DoubleUtils.sub(DoubleUtils.sum(balance, surCreAmt), amt) < 0) {
			retCode = "000003";
			// ("备付金余额不足!");
			logger.error("备付金余额不足!");
			return retCode;
		}
		if (DoubleUtils.sub(balance, amt) >= 0) {
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
				arrearsDate = modelSvcReq.getTrade_date();
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
		modelSvcAns.setAcc_balance_fir(balance.toString());
		modelSvcAns.setAmount(amt.toString());
		logger.info("备付金余额修改成功！");
		return retCode;
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
		return tbp;
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

	 /*
	 * 备付金余额查询
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private Double getPrebalanceByShopAccount(ModelSvcReq modelSvcReq,
			ModelSvcAns modelSvcAns) {
		String shopAccount = modelSvcReq.getCompany_code_fir();// 商户号
		String accNo = null;// 备付金帐号
		TbBiPrepay tbBiprepay = null;// 备付金
		Double preBalance = 0.00;// 备付金余额
		try {
			accNo = prepayDao.searchPreAccnoBySA(shopAccount);
			tbBiprepay = (TbBiPrepay) baseHibernateDao.get(TbBiPrepay.class,
					accNo);
			preBalance = tbBiprepay.getAccBalance();// 设置备付金余额
		} catch (Exception e) {
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
			modelSvcAns.setMessage(("查询备付金余额出错,请拨打客服电话咨询!"));

			logger.error("查询备付金余额出错,查询表：TB_BI_PREPAY,商户号: "
					+ modelSvcReq.getCompany_code_fir() + "备付金账号: " + accNo);
		}
		return preBalance;
	}

	/**
	 * 备付金取款取消，登记明细
	 * 
	 * @param 账号
	 * @param amt
	 * @return boolean
	 */
	public boolean cancelToUpdatePrepay(String accNo, Double amt,
			ModelSvcAns modelSvcAns) {
		logger.info("取消交易中恢复备付金!");

		Double balance = 0.00;// 余额
		Double useCreAmt = 0.00;// 已使用信用额度
		Double surCreAmt = 0.00;// 剩余信用额度

		// 取备付金账户信息
		TbBiPrepay tbPrepay = null;
		try {
			tbPrepay = getPrepay(accNo);
		} catch (Exception e) {
			logger.error("取该商户备付金账户时出错!accNo=[" + accNo + "];", e);
			return false;
		}

		balance = tbPrepay.getAccBalance();
		useCreAmt = tbPrepay.getUseCreamt();
		surCreAmt = tbPrepay.getSurCreamt();

		logger
				.info(
						"回退备付金账户前：交易金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt });

		if (DoubleUtils.sub(amt, useCreAmt) > 0) {
			tbPrepay.setUseCreamt(0.00);
			tbPrepay.setSurCreamt(tbPrepay.getCreditAmt());
			tbPrepay.setAccBalance(DoubleUtils.sum(balance, DoubleUtils.sub(
					amt, useCreAmt)));
		} else {
			tbPrepay.setUseCreamt(DoubleUtils.sub(useCreAmt, amt));
			tbPrepay.setSurCreamt(DoubleUtils.sum(surCreAmt, amt));
		}

		try {
			baseHibernateDao.update(tbPrepay);
		} catch (Exception e) {
			logger.error("修改备付金账户余额出错!", e);
			return false;
		}

		try {
			tbPrepay = getPrepay(accNo);
		} catch (Exception e) {
			logger.error("取该商户备付金账户时出错!accNo=[" + accNo + "];", e);
			return false;
		}
		logger
				.info(
						"回退备付金账户后：金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, tbPrepay.getAccBalance(),
								useCreAmt, surCreAmt });

		logger.info("回退备付金账户成功！");
		modelSvcAns.setAcc_balance_fir(tbPrepay.getAccBalance().toString());

		return true;
	}
}
