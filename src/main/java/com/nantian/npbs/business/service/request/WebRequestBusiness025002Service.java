package com.nantian.npbs.business.service.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import weblogic.servlet.jsp.AddToMapException;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Scope("prototype")
@Component(value = "WebRequestBusiness025002Service")
public class WebRequestBusiness025002Service extends WebRequestBusinessService {
	private static Logger logger = LoggerFactory
			.getLogger(WebRequestBusiness025002Service.class);

	@SuppressWarnings("null")
	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("WebRequestBusiness025002Service webexecute begin");
		/**
		 * 如商户2有值，则扣商户1的值，存入商户2， 转帐：商户1值扣，存入商户2，交易类型需传28. 默认扣商户1的值，与支付金额相等。
		 * 
		 * 如子商户为空，则默认扣第一个商户的金额，如果不够则交易不成功。 商户1值不够扣，则扣信用额度。交易类型为21
		 */

		TbBiTrade trade = new TbBiTrade();
		TbBiTradeId tradeId = new TbBiTradeId();
		String number = baseHibernateDao.getNumber();
		tradeId.setPbSerial(number);
		tradeId.setTradeDate(baseHibernateDao.getSystemDate());

		TbBiPrepayInfo tbBiPrepayInfo = new TbBiPrepayInfo();
		TbBiPrepayInfoId tbBiPrepayInfoId = new TbBiPrepayInfoId();
		tbBiPrepayInfoId.setPbSerial(number);
		tbBiPrepayInfoId.setTradeDate(baseHibernateDao.getSystemDate());

		String companyCode = modelSvcReq.getCompany_code();// 主商户1
		String companyCode2 = modelSvcReq.getCompany_code_sec();// 辅商户2
		if (companyCode2 == null) {// 辅商户为空的情况,直接扣商户1的钱
			// 先记流水
			setTrade(trade, tradeId, modelSvcReq, modelSvcReq.getCompany_code());
			if (tradeDao.addTrade(trade)) {// 流水成功后记备付金明细。
				tbBiPrepayInfoId.setPbSerial(number);
				tbBiPrepayInfoId.setTradeDate(new SimpleDateFormat("yyyyMMdd")
						.format(new Date()));
				setPrepayInfo(tbBiPrepayInfo, tbBiPrepayInfoId, modelSvcReq,
						modelSvcReq.getCompany_code());
				TbBiPrepay  prepay=null;
				try {
					 prepay = commonPrepay.getPrepay(companyCode);
					 modelSvcReq.setTbBiPrepay(prepay); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tbBiPrepayInfo.setBal(modelSvcReq.getTbBiPrepay().getAccBalance()
						- Double.parseDouble(modelSvcReq.getAmount()));
				baseHibernateDao.save(tbBiPrepayInfo);
				String string = withdrawalsPrepay(companyCode, Double
						.parseDouble(modelSvcReq.getAmount()));
				if (!string.equals("000000")) {
					if (string.equals("000001")) {
						modelSvcAns.setMessage("交易失败");
						modelSvcAns.setStatus("01");
					} else if (string.equals("000002")) {
						modelSvcAns.setMessage("提取备付金账户出错!");
						modelSvcAns.setStatus("01");
					} else if (string.equals("000003")) {
						modelSvcAns.setMessage("备付金余额不足!");
						modelSvcAns.setStatus("01");
					}
					return;
				}
			} else {// 流水失败则不扣款，直接返回
				modelSvcAns.setMessage("增加流水失败，不扣备付金");
				modelSvcAns.setStatus("01");
				return;
			}

		} else {// 商户号2有值的情况 。则为转帐
			// 先记商户1的流水跟然后扣款 登记 备付金明细
			setTrade(trade, tradeId, modelSvcReq, modelSvcReq.getCompany_code());
			if (tradeDao.addTrade(trade)) {
				// 增加流水成功后开始扣款
				String string = withdrawalsPrepay(companyCode, Double
						.parseDouble(modelSvcReq.getAmount()));
				if (!string.equals("000000")) {
					if (string.equals("000001")) {
						modelSvcAns.setMessage("交易失败");
						modelSvcAns.setStatus("01");
					} else if (string.equals("000002")) {
						modelSvcAns.setMessage("提取备付金账户出错!");
						modelSvcAns.setStatus("01");
					} else if (string.equals("000003")) {
						modelSvcAns.setMessage("备付金余额不足!");
						modelSvcAns.setStatus("01");
					}
					
					//在这里更成失败状态
					tradeDao.updateTrade("update tb_bi_trade tt set tt.status='01' where tt.pb_serial='"+trade.getId().getPbSerial()+"' and tt.trade_date='"+trade.getId().getTradeDate()+"'");
					return;
				} else {
					// 扣款商户1成功后，登录 备付金明细
					setPrepayInfo(tbBiPrepayInfo, tbBiPrepayInfoId,
							modelSvcReq, modelSvcReq.getCompany_code());
					TbBiPrepay  prepay=null;
					try {
						 prepay = commonPrepay.getPrepay(companyCode);
						 modelSvcReq.setTbBiPrepay(prepay); 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tbBiPrepayInfo.setBal(modelSvcReq.getTbBiPrepay().getAccBalance()
							- Double.parseDouble(modelSvcReq.getAmount()));
					baseHibernateDao.save(tbBiPrepayInfo);
					List find = baseHibernateDao.find("select count(*) from tb_bi_trade tt where tt.pb_serial='"+tbBiPrepayInfo.getId().getPbSerial()+"' and  tt.trade_date='"+tbBiPrepayInfo.getId().getTradeDate()+"'");
					if(find.isEmpty()){
						modelSvcAns.setMessage("增加明细失败，转帐失败");
						modelSvcAns.setStatus("01");
						tradeDao.updateTrade("update tb_bi_trade tt set tt.status='01' where tt.pb_serial='"+trade.getId().getPbSerial()+"' and tt.trade_date='"+trade.getId().getTradeDate()+"'");
						tradeDao.updateTrade("update tb_bi_trade tt set tt.status='01' where tt.pb_serial='"+tbBiPrepayInfo.getId().getPbSerial()+"' and tt.trade_date='"+tbBiPrepayInfo.getId().getTradeDate()+"'");
					return;
					}
					// 增加金额到商户2
					TbBiTrade trade2 = new TbBiTrade();
					TbBiTradeId tradeId2 = new TbBiTradeId();
					String number2 = baseHibernateDao.getNumber();
					tradeId2.setPbSerial(number2);
					tradeId2.setTradeDate(baseHibernateDao.getSystemDate());

					TbBiPrepayInfo tbBiPrepayInfo2 = new TbBiPrepayInfo();
					TbBiPrepayInfoId tbBiPrepayInfoId2 = new TbBiPrepayInfoId();
					tbBiPrepayInfoId2.setPbSerial(number2);
					tbBiPrepayInfoId2.setTradeDate(baseHibernateDao
							.getSystemDate());

					setTrade(trade2, tradeId2, modelSvcReq, modelSvcReq
							.getCompany_code_sec());
					if (tradeDao.addTrade(trade2)) {
						setPrepayInfo(tbBiPrepayInfo2, tbBiPrepayInfoId2,
								modelSvcReq, modelSvcReq.getCompany_code_sec());
						TbBiPrepay  prepay1=null;
						try {
							 prepay1 = commonPrepay.getPrepay(companyCode);
							 modelSvcReq.setTbBiPrepay(prepay1); 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tbBiPrepayInfo.setBal(modelSvcReq.getTbBiPrepay().getAccBalance()
								+ Double.parseDouble(modelSvcReq.getAmount()));
						if (addPrepayInfo(tbBiPrepayInfo2)) {
							// 转入商户2
							String depositPrepay = depositPrepay(modelSvcReq
									.getCompany_code_sec(), Double
									.parseDouble(modelSvcReq.getAmount()));
							if (!string.equals("000000")) {
								if (string.equals("000001")) {
									modelSvcAns.setMessage("修改备付金账户余额出错");
									modelSvcAns.setStatus("01");
								} else if (string.equals("000002")) {
									modelSvcAns.setMessage("取该商户备付金账户时出错!");
									modelSvcAns.setStatus("01");
								}
								// 要回退。。
								return;
							}
						}
					}
				}
			}else {// 流水失败则不扣款，直接返回
				modelSvcAns.setMessage("增加流水失败，不扣备付金");
				modelSvcAns.setStatus("01");
				return;
			}
		}
		modelSvcAns.setCompany_code(companyCode);
		// 取备付金账户信息
		TbBiPrepay tbPrepay = null;
		try {
			tbPrepay = getPrepay(companyCode);
		} catch (Exception e) {
		}
		modelSvcAns.setAmount(modelSvcReq.getAmount());
		modelSvcAns.setAcc_balance(tbPrepay.getAccBalance().toString());
		modelSvcAns.setTrade_date(tradeId.getTradeDate());
		modelSvcAns.setPb_serial(tradeId.getPbSerial());
		modelSvcAns.setMessage("交易成功");
		modelSvcAns.setStatus("00");
		logger.info("WebRequestBusiness025002Service webexecute end");
		return;
	}

}
