package com.nantian.npbs.business.service.answer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.WaterCashData;

@Scope("prototype")
@Component
public class AnswerBusiness016001Service extends AnswerBusiness001Service {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness016001Service.class);

	/**
	 * 保存现金缴费临时表数据
	 */
	protected void setTempValue(ControlMessage cm, BusinessMessage bm) {

		WaterCashData waterCashData = (WaterCashData) bm.getCustomData();
		if (null == waterCashData) {
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询信息为空！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询信息为空！");
			logger.warn("查询信息为空！");
			return;
		}
		TempData ct = new TempData();
		ct.setPbSeqno(bm.getPbSeqno());
		String values = waterCashData.getOughtAmt() + "|"// 应收金额
				+ waterCashData.getUserNo() + "|"// 用户编号
				+ waterCashData.getUsername() + "|"// 用户名称
				+ waterCashData.getSeqno3() + "|"// 第三方流水
				+ waterCashData.getTicketSum();// 发票张数

		ct.setTradeDate(bm.getTranDate());
		
		logger.info(values);
		ct.setTempValue(values);
		try {
			baseHibernateDao.save(ct);
		} catch (Exception e) {
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("保存现金缴费临时信息失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("保存现金缴费临时信息失败！");
			logger.error("保存现金缴费临时信息失败！insert into TB_BI_CASH_TEMP err", e);
		}

	}
}
