package com.nantian.npbs.business.service.answer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness008Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness008Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	public TradeDao tradeDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("终端查询交易量业务处理开始！");

		// 校验查询日期是否合法
		if (!checkDate(cm, bm)) {
			logger.error("输入日期有误，请修改后重试！" );
			return;
		}

		// 终端查询交易量
		if (!getBusinessInfos(cm, bm)) {
			logger.error("");
		}
	}

	/**
	 * 终端查询交易量
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean getBusinessInfos(ControlMessage cm, BusinessMessage bm) {
		ArrayList<Object> tranInfoList = new ArrayList();

		String shopAccount = bm.getShopCode();// 商户号
		String queryStartDate = bm.getQueryStartDate();// 查询起始日期
		String queryEndDate = bm.getQueryEndDate();// 查询结束日期	
	
		
//		shopAccount = "05001022";//测试用号
		String accNo = null;// 备付金帐号
		// 查询备付金帐号
		try {
			accNo = prepayDao.searchPreAccnoBySA(shopAccount);
		} catch (Exception e) {
			bm.setResponseCode("99");
			bm.setResponseMsg("查询备付金帐号出错,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金帐号出错,请联系管理员!");
			logger
					.error("查询备付金帐号出错,查询表：TB_BI_PREPAY ,商户号: "
							+ bm.getShopCode());
			return false;
		}

		// 按商户号查询统计信息
		String sql = "select COMPANY_CODE,BUSI_CODE,count(PB_SERIAL),sum(AMOUNT) from TB_BI_TRADE where COMPANY_CODE = '"
				+ shopAccount
				+ "' and trade_date >= '"
				+ queryStartDate
				+ "' and trade_date <= '"
				+ queryEndDate
				+ "' and trade_time >='" +queryStartDate.trim()+
				"000000'  and trade_time <='" +queryEndDate.trim()+
				"235959' and STATUS = '00' GROUP BY COMPANY_CODE, BUSI_CODE ORDER BY BUSI_CODE";
		try {
			List<Object[]> o = tradeDao.findInfoList(sql);
			if (null != o && o.size() > 0) {
				for (Object[] obj : o) {
					String companyCode = (String) obj[0];// 商户号
					String busiCode = (String) obj[1];// 业务类型
					BigDecimal pbSerialCount = (BigDecimal) obj[2];// 交易笔数
					BigDecimal amountSum = (BigDecimal) obj[3];// 交易总金额
					logger.info("商户号[{}]业务类型[{}]交易笔数[{}]交易总金额[{}]" ,new Object[]{companyCode ,busiCode
							, pbSerialCount ,amountSum});
					// //根据业务类型查询业务名称
					// try{
					// String busiName =
					// tradeDao.findNameByBusiType(busiCode);//业务名称
					// logger.info("业务名称["+busiName+"]");
					// } catch(Exception e){
					// bm.setResponseCode("99");
					// bm.setResponseMsg("根据业务类型查询业务名称错!");
					// cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					// cm.setResultMsg("根据业务类型查询业务名称错!");
					// logger.error("根据业务类型查询业务名称错！ubsiCode=[" + busiCode + "]"
					// );
					// return false;
					// }					
				}
				tranInfoList.add(o);
			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("按商户号查询统计无信息,请重新输入查询条件!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("按商户号查询统计无信息,请重新输入查询条件!");
				logger.error("按商户号查询统计无信息！sql=[" + sql + "]"
						);
				return false;
			}

		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("按商户号查询统计信息出错,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("按商户号查询统计信息出错,请联系管理员!");
			logger.error("按商户号查询统计信息出错！sql=[" + sql + "]"
					);
			e.printStackTrace();
			return false;
		}

		/*
		// 查询备付金统计信息
		String sqlForPrepay = "select accno,count(pb_serial),sum(amount) from tb_bi_prepay_info where accno = "
				+ accNo
				+ " and status = '00' and trade_date >= "
				+ queryStartDate
				+ " and trade_date <= "
				+ queryEndDate
				+ " group by accno";
		try {
			List<Object[]> o = tradeDao.findInfoList(sqlForPrepay);
			if (null != o && o.size() > 0) {
				String accno = (String) o.get(0)[0];
				BigDecimal prepayPbCount = (BigDecimal) o.get(0)[1];// 备付金交易笔数
				BigDecimal prepayAmountSum = (BigDecimal) o.get(0)[2];// 备付金交易总金额
				logger.info("备付金帐号[{}]备付金交易笔数[{}]备付金交易总金额[{}]" ,new Object[]{accNo , prepayPbCount
						, prepayAmountSum });
				tranInfoList.add(o);
			} else {
				logger.error("无备付金统计信息！sqlForPrepay=[" + sqlForPrepay + "]"
						);
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金统计信息出错,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金统计信息出错,请联系管理员!");
			logger.error("查询备付金统计信息出错！sqlForPrepay=[" + sqlForPrepay + "]"
					);
			e.printStackTrace();
			return false;
		}
		*/
		bm.setJournalList(tranInfoList);
//		shopAccount = "05000001";
		return true;
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean checkDate(ControlMessage cm, BusinessMessage bm) {

		long lTime1 = Long.parseLong(bm.getQueryStartDate());
		long lTime2 = Long.parseLong(bm.getQueryEndDate());
		if (lTime2 < lTime1) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("输入日期有误，请修改后重试!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("输入日期有误，请修改后重试!");
			logger.info("输入日期有误，请修改后重试!");
			return false;
		}
		return true;
	}



}
