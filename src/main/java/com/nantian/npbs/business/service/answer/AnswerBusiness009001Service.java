package com.nantian.npbs.business.service.answer;

/**
 * 有线电视  
 * wzd
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HDCashData;
import com.nantian.npbs.packet.internal.TVCashData;

@Scope("prototype")
@Component
public class AnswerBusiness009001Service extends AnswerBusiness001Service {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness009001Service.class);

	/**
	 * 保存现金缴费临时表数据
	 */
	protected void setTempValue(ControlMessage cm, BusinessMessage bm) {

		TVCashData tvCashData = (TVCashData) bm.getCustomData();
		if (null == tvCashData) {
			logger.info("查询信息为空！{}" , getClass().getName());
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
				+ tvCashData.getAccNo() + "|"// 账户编码
				+ tvCashData.getServiceType()  + "|"// 业务类型
				+ tvCashData.getSubscriberNo()  + "|"// 服务号码
				+ tvCashData.getCurAmt()  + "|"// 当前余额
				+ tvCashData.getAccBookNo()+"|"//余额账本编码
				+ tvCashData.getCustomerName(); //用户姓名

		ct.setTradeDate(bm.getTranDate());
		
		logger.info(values);
		ct.setTempValue(values);
		try {
			baseHibernateDao.save(ct);
		} catch (Exception e) {
			logger.info("保存现金缴费临时信息失败！{}" , getClass().getName());
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("保存现金缴费临时信息失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("保存现金缴费临时信息失败！");
			logger.error("insert into TB_BI_CASH_TEMP err", e);
		}

	}
}
