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
		logger.info("webrequestexecute begin");
		// 公共检查
	
		// 检查备付金帐户是否正常
		if(!checkPrepay(modelSvcReq, modelSvcReq.getCompany_code_fir())){
			modelSvcAns.setMessage("取帐户信息异常");
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
			return;
		}
	 //检查余额
		if (Double.parseDouble(modelSvcReq.getAmount())
				- (modelSvcReq.getTbBiPrepay().getAccBalance() +modelSvcReq.getTbBiPrepay().getSurCreamt()) > 0) {
			modelSvcAns.setMessage("您的备付金余额不足,请尽快充值");
			modelSvcAns.setStatus(GlobalConst.RESPONSECODE_FAILURE);
			logger.info("备付金余额不足!账户余额:" + modelSvcReq.getTbBiPrepay().getAccBalance()
					+ "信用余额:" +modelSvcReq.getTbBiPrepay().getSurCreamt() + ",缴费金额:" + modelSvcReq.getAmount());
			return ;
		}
		//设置是否登记流水
		setTradeFlag(modelSvcReq);
		modelSvcReq.setPb_serial(baseHibernateDao.getNumber()) ;
		modelSvcReq.setTrade_date(baseHibernateDao.getSystemDate());
		logger.info("交易 流水[{}],交易日期[{}]",modelSvcReq.getPb_serial(),modelSvcReq.getTrade_date());
		dealBusiness(modelSvcReq, modelSvcAns);
		
		if("1".equals(modelSvcReq.getSeqnoFlag())==true){
			//登记流水
			 
				try {
					if(addTrade(modelSvcReq)){
						logger.info("登记流水成功");
						modelSvcAns.setMessage("登记流水成功");
						modelSvcAns.setPb_serial(modelSvcReq.getTradeId().getPbSerial());
						modelSvcAns.setStatus(GlobalConst.RESULTCODE_SUCCESS);
					}else{
						logger.info("登记流水失败，交易失败");
						modelSvcAns.setMessage("登记流水失败，交易失败");
						modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
						return;	
					}
				} catch (Exception e) {
					e.printStackTrace();
					modelSvcAns.setMessage("登记流水失败，交易失败");
					modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
					return;
				} 
		}
		 
		logger.info("webrequestexecute end");
	}

	public abstract  void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) ;
	
	public boolean addTrade(ModelSvcReq modelSvcReq) throws Exception{
		return	 tradeDao.addTrade(modelSvcReq.getTbBiTrade());
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
		if(prepay==null){
			return false;
		}else 	if(!(GlobalConst.PREPAY_STATE_ZC).equals(prepay.getState())){
			return false;
		}else  return true;
		 
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
		} catch (Exception e) {
			logger.error("修改备付金账户余额出错！", e);
			retCode = GlobalConst.RESULTCODE_FAILURE;
			return retCode;
		}
		logger.info(
						"预存后：预存金额金额  amt=[{}]账户余额  balance=[{}]]已使用信用额度  useCreAmt=[{}]剩余信用额度  surCreAmt=[{}]",
						new Object[] { amt, balance, useCreAmt, surCreAmt });

		logger.info("备付金预存成功！");
		return retCode;
	}
	

	public void setTrade(TbBiTrade trade, TbBiTradeId tradeId,
			ModelSvcReq modelSvcReq, String company_code) {
		trade.setId(tradeId);// 日期 流水
		trade.setCompanyCode(company_code);//
		trade.setBusiCode(modelSvcReq.getBusi_code().substring(0, 3));//
		trade.setSystemCode(modelSvcReq.getSystem_code());
		trade.setStatus("99");//
		trade.setTradeType(modelSvcReq.getSystem_code());
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
		trade.setPayType("1");
		trade.setAccno(company_code);
	}
	
	public abstract void setTradeFlag(ModelSvcReq modelSvcReq);
}
