package com.nantian.npbs.business.service.answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness013Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness013Service.class);

	@Resource
	private TradeDao tradeDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		// 末笔交易查询业务处理
		if (!getLastSucTranByShopCode(cm, bm)) {
			logger.error("末笔成功交易查询失败！");
			return;
		}
		logger.info("末笔成功交易查询成功！");
	}

	private boolean getLastSucTranByShopCode(ControlMessage cm, BusinessMessage bm) {
		TbBiTrade tbBiTrade = null;
		try {
			//最后一笔成功的缴费交易
			String queryString = "from TbBiTrade t where t.companyCode = "
					+ bm.getShopCode()
					+ " and t.status = '00' and t.tradeType = '01' order by t.id.pbSerial desc";
			ArrayList<TbBiTrade> tbBiTradeList = (ArrayList<TbBiTrade>) tradeDao
					.findTradeList(queryString);// 取得末笔成功流水
			if(null == tbBiTradeList||tbBiTradeList.size()<1){
				bm.setResponseCode("99");
				bm.setResponseMsg("末笔成功交易查询失败,无此记录！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("末笔成功交易查询失败,无此记录！");
				logger.error("末笔成功交易查询失败,无此记录！,查询表：TB_BI_TRADE,商户号: " + bm.getShopCode());
				return false;
			}
			// 设置末笔流水到bm
			ArrayList<Object> tb = new ArrayList<Object>();
			tb.add(tbBiTradeList.get(0));
			bm.setJournalList(tb);

		} catch (Exception e) {
			bm.setResponseCode("99");
			bm.setResponseMsg("末笔成功交易查询失败!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("末笔成功交易查询失败!");
			logger.error("末笔成功交易查询失败,查询表：TB_BI_TRADE,商户号: " + bm.getShopCode());
			return false;
		}
		return true;
	}



}
