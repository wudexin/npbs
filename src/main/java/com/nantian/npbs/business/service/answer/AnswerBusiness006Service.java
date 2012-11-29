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
public class AnswerBusiness006Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness006Service.class);

	@Resource
	private TradeDao tradeDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("开始业务处理");

		// 用户流水列表查询
		logger.info("根据用户号查询流水列表开始：");
		if (!getUserTradeByCustomerNo(cm, bm)) {
			logger.error("根据用户号查询流水列表失败！");
			return;
		}
		logger.info("根据用户号查询流水列表成功！");
	}

	/**
	 * 根据pb流水号查询交易明细
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean getUserTradeByCustomerNo(ControlMessage cm,
			BusinessMessage bm) {
		String origLocalDate = bm.getOrigLocalDate();// 原交易日期
		String userCode = bm.getUserCode();// 用户号
		String packageNum = bm.getPackageNum();// 第packageNum次发包，packageNum为3位001为第一次
		int num = Integer.parseInt(packageNum);
		System.out.println(origLocalDate);
		System.out.println(num);
		try {
			String numsql = "select * from tb_bi_trade t where t.customerno = '"
					+ userCode
					+ "' and t.trade_Date = '"
					+ origLocalDate
					+ "' and t.company_code = '"
					+ bm.getShopCode()
					+ "' order by t.trade_Time desc";
			ArrayList<Object> numList = (ArrayList) tradeDao
					.findInfoList(numsql);// 取得对应交易明细
			if (null == numList || numList.size() < 1) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("根据用户号查询流水列表失败,无此记录！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("根据用户号查询流水列表失败,无此记录！");
				logger.error("根据用户号查询流水列表失败,无此记录！,查询表：TB_BI_TRADE,用户号: "
						+ userCode);
				return false;
			}
			int sum = numList.size();
			System.out.println(sum);
			// 是否有后续数据
			if (num * 12 < sum) {
				// 有后续数据
				bm.setPackageFlag("1");
			} else {
				// 无后续数据
				bm.setPackageFlag("0");
			}
			String queryString = "select * from (select a.*,rownum rn from (select * from tb_bi_trade t where t.customerno = '"
					+ userCode
					+ "' and t.trade_Date = '"
					+ origLocalDate
					+ "' and t.company_code = '"
					+ bm.getShopCode()
					+ "' order by t.trade_Time desc ) a where rownum <= "
					+ num
					* 12 + " ) where rn >" + (num - 1) * 12;
			System.out.println(queryString);
			System.out.println(bm.getOrigPbSeqno());
			ArrayList<Object> tbBiTradeList = (ArrayList) tradeDao
					.findInfoList(queryString);// 取得对应交易明细
			if (null == tbBiTradeList || tbBiTradeList.size() < 1) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("根据用户号查询流水列表失败,无此记录！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("根据用户号查询流水列表失败,无此记录！");
				logger.error("根据用户号查询流水列表失败,无此记录！,查询表：TB_BI_TRADE,用户号: "
						+ userCode);
				return false;
			}
			// 设置查询结果到bm
			bm.setJournalList(tbBiTradeList);

		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("根据用户号查询流水列表失败!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("根据用户号查询流水列表失败!");
			logger.error("根据用户号查询流水列表失败,查询表：TB_BI_TRADE,用户号: " + userCode);
			return false;
		}
		return true;
	}

}
