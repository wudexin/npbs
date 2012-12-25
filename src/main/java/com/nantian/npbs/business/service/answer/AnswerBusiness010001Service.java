package com.nantian.npbs.business.service.answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

@Scope("prototype")
@Component
public class AnswerBusiness010001Service extends  AnswerBusiness001Service{

private static Logger logger = LoggerFactory.getLogger(AnswerBusiness010001Service.class);
	
	/**
	 * 保存现金缴费临时表数据
	 */
	protected void setTempValue(ControlMessage cm,BusinessMessage bm){
		
		ElectricityICCardData icCardData = (ElectricityICCardData) bm.getCustomData();
		TempData ct = new TempData();
			ct.setPbSeqno(bm.getPbSeqno());
			System.out.println(bm.getPbSeqno());
			ct.setTradeDate(bm.getTranDate());
			
			ct.setTempValue(String.valueOf(icCardData.getBuckleAmt()));  //保存扣减金额以备缴费使用
			try {
				baseHibernateDao.save(ct);
			} catch (Exception e) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("保存IC卡缴费临时信息失败！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("保存IC卡缴费临时信息失败！");
				logger.error("保存IC卡缴费临时信息失败！insert into TB_BI_CASH_TEMP err", e);
			}
		
	}
	
}
