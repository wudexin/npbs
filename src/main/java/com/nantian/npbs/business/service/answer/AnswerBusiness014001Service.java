package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HuaElecCash;

@Scope("prototype")
@Component
public class AnswerBusiness014001Service extends AnswerBusiness001Service {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness014001Service.class);

	/**
	 * 保存现金缴费临时表数据
	 */
	protected void setTempValue(ControlMessage cm, BusinessMessage bm) {

		HuaElecCash cash = (HuaElecCash) bm.getCustomData();
		if (null == cash) {
			logger.info("查询信息为空！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询信息为空！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询信息为空！");
			logger.error("查询信息为空！");
			return;
		}
		TempData ct = new TempData();
		ct.setPbSeqno(bm.getPbSeqno());
		String values = cash.getAccBalance() + "|"	// 账户余额
				+ cash.getOrgNo() + "|"	 			// 供电单位编号
				+ cash.getRecordNo() + "|"			// 记录条数
				+ cash.getRecord()+ "|"				// 记录明细
				+ cash.getUserName();				// 用户名称	
		ct.setTempValue(values);
		ct.setTradeDate(bm.getTranDate());
		logger.info("本交易流水[{}] ,数据:[{}],交易日期:[{}]", new Object[]{bm.getPbSeqno(),ct.getTempValue(),ct.getTradeDate()});
		try {
			baseHibernateDao.save(ct);
		} catch (Exception e) {
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("保存现金缴费临时信息失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("保存现金缴费临时信息失败！");
			logger.error("保存现金缴费临时信息失败！ insert into TB_BI_CASH_TEMP err", e);
			return;
		}

	}
}
