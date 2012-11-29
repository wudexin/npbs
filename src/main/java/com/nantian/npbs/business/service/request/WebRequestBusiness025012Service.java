package com.nantian.npbs.business.service.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeId;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
@Scope("prototype")
@Component(value="WebRequestBusiness025012Service")
public class WebRequestBusiness025012Service extends WebRequestBusinessService {
	private static Logger logger = LoggerFactory
	.getLogger(WebRequestBusiness025012Service.class);
	
	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("WebRequestBusiness025012Service webexecute begin");
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
		String sql="select * from tb_bi_trade t  where t.system_serial='"+modelSvcReq.getWeb_serial()+"' and t.trade_date='"+modelSvcReq.getWeb_date()+"' ";
		logger.info("查询sql"+sql);
		//查找流水
		List list = tradeDao.findInfoList(sql);
		if(list.isEmpty()){
			modelSvcAns.setMessage("查无此流水");
			modelSvcAns.setStatus("01");
			return;
		}else {
			//有该笔流水的情况 。判断该流水的的交易状态，如果为取消，则不能ukg
			
		}
		if (companyCode2 == null) {// 辅商户为空的情况,直接冲回商户1
			// 先记流水
			setTrade(trade, tradeId, modelSvcReq, modelSvcReq.getCompany_code());
			if (tradeDao.addTrade(trade)) {// 流水成功后记备付金明细。
				tbBiPrepayInfoId.setPbSerial(number);
				tbBiPrepayInfoId.setTradeDate(new SimpleDateFormat("yyyyMMdd")
						.format(new Date()));
				setPrepayInfo(tbBiPrepayInfo, tbBiPrepayInfoId, modelSvcReq,
						modelSvcReq.getCompany_code());
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
				modelSvcAns.setMessage("增加流水失败，冲正失败！");
				modelSvcAns.setStatus("01");
				return;
			}
		
		} else {// 商户号2有值的情况 。则把商户2的值扣除，再增加到商户1里。
				 
			setTrade(trade, tradeId, modelSvcReq, modelSvcReq.getCompany_code_sec());
			if (tradeDao.addTrade(trade)) {
				// 增加流水成功后扣款
				String string = withdrawalsPrepay(companyCode, Double
						.parseDouble(modelSvcReq.getAmount()));
				if (!string.equals("000000")) {
					if (string.equals("000001")) {
						modelSvcAns.setMessage("交易失败！");
						modelSvcAns.setStatus("01");
					} else if (string.equals("000002")) {
						modelSvcAns.setMessage("提取备付金账户出错！");
						modelSvcAns.setStatus("01");
					} else if (string.equals("000003")) {
						modelSvcAns.setMessage("备付金余额不足！");
						modelSvcAns.setStatus("01");
					}
					//在这里更成失败状态 
					return;
				} else {
					// 扣款商户2成功后，登录 备付金明细
					setPrepayInfo(tbBiPrepayInfo, tbBiPrepayInfoId,
							modelSvcReq, modelSvcReq.getCompany_code_sec());
					tbBiPrepayInfo.setFlag("2");
					baseHibernateDao.save(tbBiPrepayInfo);
					//增加金额到商户1
					TbBiTrade  trade2 = new TbBiTrade();
					TbBiTradeId  tradeId2 = new TbBiTradeId();
					String number2 = baseHibernateDao.getNumber();
					tradeId2.setPbSerial(number2);
					tradeId2.setTradeDate(baseHibernateDao.getSystemDate());

					TbBiPrepayInfo  tbBiPrepayInfo2 = new TbBiPrepayInfo();
					TbBiPrepayInfoId  tbBiPrepayInfoId2 = new TbBiPrepayInfoId();
					tbBiPrepayInfoId2.setPbSerial(number2);
					tbBiPrepayInfoId2.setTradeDate(baseHibernateDao.getSystemDate());
					
					setTrade(trade2, tradeId2, modelSvcReq, modelSvcReq.getCompany_code_sec());
					if (tradeDao.addTrade(trade2)) {
						
						setPrepayInfo(tbBiPrepayInfo2, tbBiPrepayInfoId2,
								modelSvcReq, modelSvcReq.getCompany_code_sec());	
						 
						if(addPrepayInfo(tbBiPrepayInfo2) ){
							//转入商户2
						String depositPrepay = depositPrepay(modelSvcReq.getCompany_code_sec(),Double.parseDouble(modelSvcReq.getAmount()));
						if (!string.equals("000000")) {
							if (string.equals("000001")) {
								modelSvcAns.setMessage("修改备付金账户余额出错");
								modelSvcAns.setStatus("01");
							} else if (string.equals("000002")) {
								modelSvcAns.setMessage("取该商户备付金账户时出错!");
								modelSvcAns.setStatus("01");
							} 
							//在这里更成失败状态，把钱转回到商户里，
							return;
						}   
						
						}
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
				return;

			}
		}
		
		
		logger.info("WebRequestBusiness025012Service webexecute end");
		}
}
