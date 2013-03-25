package com.nantian.npbs.business.service.internal;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Service
public class CommonPrepay {
	private static Logger logger = LoggerFactory.getLogger(CommonPrepay.class);

	@Resource
	public BaseHibernateDao baseHibernateDao;
	@Resource
	public PrepayDao prepayDao;

	/**
	 * 增加备付金
	 */
	public void addPrepay() {

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
	 * 按照备付金账户提取备付金低额提醒参数实体
	 * @param accNo   备付金参数
	 * @return         备付金实体类
	 * @throws Exception
	 */
	public TbBiPrepayLowamount getTbBiPrepayLowAmount(String accNo) throws Exception {
		TbBiPrepayLowamount tb = null;
		tb = (TbBiPrepayLowamount)baseHibernateDao.get(TbBiPrepayLowamount.class, accNo);
		return tb;
	}

	/**
	 * 扣备付金并登记备付金明细
	 * 
	 * @param ControlMessage
	 * @param BusinessMessage
	 * 
	 */
	public boolean payPrepay(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金处理开始！");
		String flag = cm.getResultCode();
		String accNo = bm.getPrePayAccno();
		Double amt = bm.getAmount();
		String retCode = GlobalConst.RESULTCODE_SUCCESS;

		logger.info("flag=[{}];accNo=[{}];amt=[{}]", new Object[] { flag,
				accNo, amt});

		if (GlobalConst.RESULTCODE_SUCCESS.equals(flag)) {
			retCode = withdrawalsPrepay(accNo, amt, bm);
			if (GlobalConst.RESULTCODE_SUCCESS.equals(retCode) != true) {
				String retMsg = null;
//				switch (Integer.getInteger(retCode)) {
				//add by wzd  start
				switch (Integer.parseInt(retCode)) {
				//add by wzd  end
				case  000001:
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
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg(retMsg);
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg(retMsg);
				logger.error(retMsg);
				return false;
			}

			//查询备付金当前余额
			logger.info("商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
			TbBiPrepay tbBiPrepay = null;
			try {
				 Object obj1 = baseHibernateDao.get(TbBiPrepay.class,bm.getPrePayAccno());
				//add by fengyafang start
				//Object obj1 =getPrepay(bm.getPrePayAccno());
				if (obj1 != null) {
					tbBiPrepay = (TbBiPrepay) obj1;
					bm.setPrepay(tbBiPrepay);
					// 设置
					bm.setPreBalance(tbBiPrepay.getAccBalance()); // 备付金余额
				} else {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("备付金查询出错!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金查询出错!");
					logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
					return false;
				}
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询您的备付金账户出错,请联系管理员!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询您的备付金账户出错,请联系管理员!");
				logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
				return false;
			}
			// 设置备付金明细--在服务端调用好呢还是在这里调用
			// 存取款操作应该联动登记明细，但是这里取不到流水号和商户名称等信息
			TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
			TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
			tbPrepayId.setPbSerial(bm.getPbSeqno());
			tbPrepayId.setTradeDate(bm.getTranDate());
			prepayInfo.setId(tbPrepayId);
			prepayInfo.setAccno(accNo);
			prepayInfo.setCompanyCode(bm.getShopCode());
			prepayInfo.setBusiCode(bm.getBusinessType());
			prepayInfo.setSystemCode(bm.getSystemChanelCode());
			prepayInfo.setStatus("00");//00-正常交易，01-取消，02-冲正
			prepayInfo.setPosSerial(bm.getPosJournalNo());
			prepayInfo.setSystemSerial(bm.getSysJournalSeqno());
			prepayInfo.setCustomerno(bm.getUserCode());
			prepayInfo.setCustomername(bm.getUserName().trim());
			prepayInfo.setFlag("2");//1-存款，2-取款
			prepayInfo.setAmount(amt);
			if(DoubleUtils.sub(tbBiPrepay.getAccBalance(), 0) > 0.0){
				prepayInfo.setBal(tbBiPrepay.getAccBalance());
			}else{
				prepayInfo.setBal(-tbBiPrepay.getUseCreamt());
			}
			prepayInfo.setSummary("取款");
			prepayInfo.setRemark(bm.getRemark());
			prepayInfo.setTradeTime(bm.getLocalDate() + bm.getLocalTime());

			// 登记备付金明细
			boolean suc = false;
			suc = addPrepayInfo(prepayInfo);
			if (suc == false) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金明细插入失败！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("备付金明细插入失败！");
				logger.error("备付金明细插入失败！");
				return false;
			}
		}

		return true;
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
	public String withdrawalsPrepay(String accNo, Double amt, BusinessMessage bm) {
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
				arrearsDate = bm.getTranDate();
				tbPrepay.setArrearsDate(arrearsDate);
			}
			tbPrepay.setAccBalance(0.00);
			tbPrepay.setUseCreamt(useCreAmt);
			tbPrepay.setSurCreamt(surCreAmt);
		}

		try {
			baseHibernateDao.update(tbPrepay);
			//add by fengyafangstart
			//prepayDao.updateWithLock(tbPrepay);
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
	 * 修改备付金账户密码
	 * 
	 * @param newPsw
	 *            新密码
	 * @param accNo
	 *            备付金帐号
	 * @return
	 */
	public boolean updatePSW(String newPsw, String accNo) {
		// 取备付金账户信息
		TbBiPrepay tbPrepay = null;
		try {
			tbPrepay = getPrepay(accNo);
		} catch (Exception e) {
			logger.error("取该商户备付金账户时出错!accNo=[" + accNo + "];", e);
			return false;
		}
		tbPrepay.setAccpwd(newPsw);
		tbPrepay.setPwdFlag("0");
		try {
			baseHibernateDao.update(tbPrepay);
		} catch (Exception e) {
			logger.error("修改备付金密码出错!accNo=[" + accNo + "];", e);
			return false;
		}
		return true;
	}

	/**
	 * 更新备付金明细状态
	 * 
	 * @param pbSerial
	 * @param tradeDate
	 * @param status
	 * @return
	 */
	public boolean updatePrePayInfo(String pbSerial, String tradeDate,
			String status, String sysJournalSeqno) {

		TbBiPrepayInfo prepayInfo = null;
		TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
		tbPrepayId.setPbSerial(pbSerial);
		tbPrepayId.setTradeDate(tradeDate);
		try {
			prepayInfo = getPrepayInfo(tbPrepayId);
			prepayInfo.setStatus(status);
			if(sysJournalSeqno != null && !"".equals(sysJournalSeqno)){
				prepayInfo.setSystemSerial(sysJournalSeqno);
			}
			
			baseHibernateDao.save(prepayInfo);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 获取备付金明细
	 * 
	 * @throws Exception
	 * 
	 */
	public TbBiPrepayInfo getPrepayInfo(TbBiPrepayInfoId id) throws Exception {
		TbBiPrepayInfo info = null;
		info = (TbBiPrepayInfo) baseHibernateDao.get(TbBiPrepayInfo.class, id);
		return info;
	}

	/**
	 * 备付金取款取消，登记明细
	 * 
	 * @param 账号
	 * @param amt
	 * @return boolean
	 */
	public boolean cancelToUpdatePrepay(String accNo, Double amt) {
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

		if (DoubleUtils.sub(amt , useCreAmt) > 0) {
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
		logger
				.info(
						"回退备付金账户后：金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, tbPrepay.getAccBalance(), 	tbPrepay.getUseCreamt(), tbPrepay.getSurCreamt() });

		logger.info("回退备付金账户成功！");
		
		
		return true;
	}

	/**
	 * 备付金存款取消
	 * 
	 * @param accNo
	 *            备付金帐号
	 * @param amount
	 *            取消金额
	 * @return
	 */
	public boolean cancelPrepayIn(String accNo, Double amt, String date) {
		logger.info("备付金存款取消！");
		Double balance = 0.00; // 余额
//		Double creditAmt = 0.00;// 总信用额度
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
//		creditAmt = tbPrepay.getCreditAmt();
		useCreAmt = tbPrepay.getUseCreamt();
		surCreAmt = tbPrepay.getSurCreamt();
		balance = tbPrepay.getAccBalance();
		logger
				.info(
						"回退备付金账户前：金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });
		//取消回去时，可用信用额度可能为负数
		if (useCreAmt > 0) {
			// 已使用信用额度
			// 孟主任：不管信用额度够不够都扣信用额度
			// 虽然孟主任说过，但是考虑到资金风险，先不允许取消，孟主任如果提供书面说明，则放开
			// 孟主任与王辉确认要扣信用额度
//			if (useCreAmt < amt) {
				// 剩余信用额度不够,这时已使用信用额度比总信用额度要大
//				logger.error("剩余信用额度不够!");
//				return false;
//			} 
			surCreAmt = DoubleUtils.sub(surCreAmt, amt);
			useCreAmt = DoubleUtils.sum(useCreAmt, amt);
			
		} else {
			// 未使用信用额度
			// 信用额度和账户余额不够,这时已使用信用额度比总信用额度要大
			//虽然孟主任说过，但是考虑到资金风险，先不允许取消，孟主任如果提供书面说明，则放开
			// 孟主任与王辉确认要扣信用额度，信用额度和账户余额不够,这时已使用信用额度比总信用额度要大
			if (balance < amt) {
				// 账户余额不够，扣光，不够的扣信用额度
				//取消回去时，可用信用额度可能为负数
				surCreAmt = DoubleUtils.sub(DoubleUtils.sum(balance,surCreAmt), amt);
				useCreAmt = DoubleUtils.sub(amt,balance);
				balance = 0.00;
				tbPrepay.setArrearsDate(date);
			} else {// 账户余额够
				balance = DoubleUtils.sub(balance, amt);
			}
			
			
//			if (DoubleUtils.sub(DoubleUtils.sum(balance, surCreAmt), amt) < 0) {
//				// 信用额度和账户余额不够,这时已使用信用额度比总信用额度要大
//				//虽然孟主任说过，但是考虑到资金风险，先不允许取消，孟主任如果提供书面说明，则放开
//				// 孟主任与王辉确认要扣信用额度
//				useCreAmt = DoubleUtils.sub(amt, balance);
//				surCreAmt = DoubleUtils.sub(surCreAmt, useCreAmt);
//				balance = 0.00;
//				
//				tbPrepay.setArrearsDate(null);
////				logger.error("剩余信用额度不够!");
////				return false;
//			} else {//信用额度够用
//				if (balance < amt) {
//					// 账户余额不够，扣光，不够的扣信用额度
//					surCreAmt = DoubleUtils.sub(DoubleUtils.sum(balance,surCreAmt), amt);
//					useCreAmt = DoubleUtils.sub(creditAmt , surCreAmt);
//					balance = 0.00;
//				} else {// 账户余额够
//					balance = DoubleUtils.sub(balance, amt);
//				}
//			}

		}

		tbPrepay.setAccBalance(balance);// 设置备付金余额
		tbPrepay.setSurCreamt(surCreAmt);
		tbPrepay.setUseCreamt(useCreAmt);

		// 修改备付金账户
		try {
			baseHibernateDao.update(tbPrepay);
		} catch (Exception e) {
			logger.error("修改备付金账户余额出错!", e);
			return false;
		}
		logger
				.info(
						"回退备付金账户后：金额  amt=[{}]账户余额  balance=[{}]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });

		logger.info("回退备付金账户成功！");
		return true;
	}
	
	/**
	 * 设置备付金明细数据
	 * 
	  * @param TbBiPrepayInfo
	 *            备付金明细
	 * @param BusinessMessage
	 * @param flag  存取款标志：1-存款，2-取款
	 *           
	 * @return
	 */
	public TbBiPrepayInfo setPrepayInfo(TbBiPrepayInfo prepayInfo,BusinessMessage bm,String flag) {
		TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
		tbPrepayId.setPbSerial(bm.getPbSeqno());
		tbPrepayId.setTradeDate(bm.getTranDate());
		prepayInfo.setId(tbPrepayId);
		prepayInfo.setAccno(bm.getPrePayAccno());
		prepayInfo.setCompanyCode(bm.getShopCode());
		prepayInfo.setBusiCode(bm.getBusinessType());
		prepayInfo.setSystemCode(bm.getSystemChanelCode());
		
		//明细类型 00-正常交易，01-取消，02-冲正 99-不符
		String status = "00";
		if("01".equals(bm.getTranType())){//交易类型 01-缴费交易；02-取消交易；04-冲正交易
			status = "00";
		}else if("02".equals(bm.getTranType())){
			status = "01";
		}else if("04".equals(bm.getTranType())){
			status = "02";
		}else if("03".equals(bm.getTranType()) && "010".equals(bm.getBusinessType())){
			status = "00";
		}else if("09".equals(bm.getTranType()) && "010".equals(bm.getBusinessType())){
			status = "01";
		}else{
			status = "99";
		}
		prepayInfo.setStatus(status);
		prepayInfo.setPosSerial(bm.getPosJournalNo());
		prepayInfo.setSystemSerial(bm.getSysJournalSeqno());
		prepayInfo.setCustomerno(bm.getUserCode());
		prepayInfo.setCustomername(bm.getUserName().trim());
		prepayInfo.setFlag(flag);//1-存款，2-取款
		prepayInfo.setAmount(bm.getAmount());
		if(DoubleUtils.sub(bm.getPreBalance(), 0) > 0.0){
			prepayInfo.setBal(bm.getPreBalance());
		}else{
			prepayInfo.setBal(-bm.getPrepay().getUseCreamt());
		}
		if("1".equals(flag)){
			prepayInfo.setSummary("存款");
		}else{
			prepayInfo.setSummary("取款");
		}
		prepayInfo.setRemark(bm.getRemark());
		prepayInfo.setTradeTime(bm.getLocalDate() + bm.getLocalTime());
		return prepayInfo;
	}

}
