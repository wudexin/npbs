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

/**
 * 用户交易明细查询
 * @author 
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness007Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness007Service.class);
	
	@Resource
	private TradeDao tradeDao;
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		//用户交易明细查询业务处理
		logger.info("用户交易明细查询开始：");
		if (!getUserTradeByPbSerial(cm, bm)) {
			logger.error("根据pb流水号查询交易明细失败！");
			return;
		}
		logger.info("根据pb流水号查询交易明细成功！");

	}
	
	/**
	 * 根据pb流水号查询交易明细
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean getUserTradeByPbSerial(ControlMessage cm,BusinessMessage bm){
		try {
			//
			String queryString = "from TbBiTrade t where t.id.pbSerial = "
					+ bm.getOldPbSeqno();
			System.out.println(bm.getOldPbSeqno());
			ArrayList<Object> tbBiTradeList = (ArrayList) tradeDao
					.findTradeList(queryString);// 取得对应交易明细
			if(null == tbBiTradeList||tbBiTradeList.size()<1){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("根据pb流水号查询交易明细失败,无此记录！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("根据pb流水号查询交易明细失败,无此记录！");
				logger.error("根据pb流水号查询交易明细失败,无此记录！,查询表：TB_BI_TRADE,pb流水号: " + bm.getPbSeqno());
				return false;
			}
			// 设置查询结果到bm
			bm.setJournalList(tbBiTradeList);

		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("根据pb流水号查询交易明细失败!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("根据pb流水号查询交易明细失败!");
			logger.error("根据pb流水号查询交易明细失败,查询表：TB_BI_TRADE,pb流水号: " + bm.getPbSeqno());
			return false;
		}
		return true;
	}


}
