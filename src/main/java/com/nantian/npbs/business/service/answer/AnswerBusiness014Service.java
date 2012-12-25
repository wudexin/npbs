package com.nantian.npbs.business.service.answer;

import java.util.ArrayList;

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
public class AnswerBusiness014Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness014Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	private TradeDao tradeDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		// 终端查询交易明细（指定业务种类）
		logger.info("终端查询交易明细（指定业务种类）！");
		if(!getBusinessInfoByBC(cm,bm)){
			logger.error("终端查询交易明细（指定业务种类）失败！");
			return;
		}
		logger.info("终端查询交易明细（指定业务种类）成功");
	}

	/**
	 * 根据业务种类和起始日期查询交易明细
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean getBusinessInfoByBC(ControlMessage cm, BusinessMessage bm) {
		String busiCode = bm.getBusinessType();// 取得业务种类
		String queryStartDate = bm.getQueryStartDate();// 查询起始日期
		String queryEndDate = bm.getQueryEndDate();// 查询结束日期

		try {
			String queryString = "from TbBiTrade t where t.busiCode = " + busiCode
					+ " and t.id.tradeDate between " + queryStartDate + " and "
					+ queryEndDate;
			ArrayList<Object> tbBiTradeList = (ArrayList) tradeDao
					.findTradeList(queryString);// 取得交易流水列表

			bm.setJournalList(tbBiTradeList);// 设置交易明细
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("终端查询交易明细（指定业务种类）出错,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("终端查询交易明细（指定业务种类）出错,请拨打客服电话咨询!");
			logger.error("终端查询交易明细（指定业务种类）错,查询表：TB_BI_TRADE,商户号: "
					+ bm.getShopCode());
			return false;
		}
		return true;
	}


}
