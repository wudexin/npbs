package com.nantian.npbs.business.service.answer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HDCashData;

@Scope("prototype")
@Component
public class AnswerBusiness005001Service extends AnswerBusiness001Service {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness005001Service.class);

	/**
	 * 保存现金缴费临时表数据
	 */
	protected void setTempValue(ControlMessage cm, BusinessMessage bm) {

		HDCashData hdCashData = (HDCashData) bm.getCustomData();
		if (null == hdCashData) {
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
		String values = bm.getUserCode() + "|"// 用户编号
				+ hdCashData.getUserType() + "|"// 用户类型
				+ hdCashData.getLastAmt()  + "|"// 上次结余
				+ hdCashData.getTotalRec()  + "|"// 记录总数
				+ hdCashData.getChargeId_1()  + "|"// 第一笔缴费ID
				+ hdCashData.getOughtAmt_1();// 第一笔应缴金额

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
