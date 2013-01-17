package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 * 新奥燃气（IC）
 * @author fengyafang
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness011001Service extends  AnswerBusiness001Service{

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness011001Service.class);
	
	/**
	 * 保存现金缴费临时表数据
	 */
	@Override
	protected void setTempValue(ControlMessage cm,BusinessMessage bm){
		
		XAICCardData lt = (XAICCardData) bm.getCustomData();
		TempData ct = new TempData();
		ct.setPbSeqno(bm.getPbSeqno());
		ct.setTempValue(lt.getSAPCODE());

		ct.setTradeDate(bm.getTranDate());
		
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
