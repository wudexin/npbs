package com.nantian.npbs.business.service.answer;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness009Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness009Service.class);
	
	// 交易流水Dao
	@Resource
	public TradeDao tradeDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		// 根据pos流水号查询交易流水
		if (!getTradeInfoByPosJournalNo(cm, bm)) {
			logger.error("根据pos流水号查询交易流水失败！" );
			return;
		}
		logger.info("根据pos流水号查询交易流水成功！");
	}
	
	/**
	 * 根据pos流水号查询交易流水
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean getTradeInfoByPosJournalNo(ControlMessage cm,
			BusinessMessage bm) {
		String origPosJournalSeqno = bm.getOrigPosJournalSeqno();// 获取pos流水号
		/* 根据pos流水号查询流水表TB_BI_TRADE */
		String hql = "from TbBiTrade tbt where tbt.posSerial = " + origPosJournalSeqno;
//		String hql = "from TbBiTrade tbt where tbt.posSerial = " + origPosJournalSeqno+" and tbt.companyCode = '"+bm.getShopCode()+"'";
		try {
			ArrayList<Object> tbList = (ArrayList) tradeDao
					.findTradeList(hql);
			bm.setJournalList(tbList);
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("根据pos流水号查询交易流水失败!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("根据pos流水号查询交易流水失败!");
			logger.error("根据pos流水号查询交易流水失败,查询表：TB_BI_TRADE");
			return false;
		}
		return true;
	}
}
