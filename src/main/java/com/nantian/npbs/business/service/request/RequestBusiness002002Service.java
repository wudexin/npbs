package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.business.service.internal.TelePhoneNumUtils;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 业务查询
 * 
 * @author fengyafang 
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness002002Service extends RequestBusiness002Service{
	private static Logger logger = LoggerFactory
	.getLogger(RequestBusiness002002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		 
		TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,
				bm.getOldPbSeqno());
		if (cashTemp == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费失败,现金查询时没有保存电费明细数据等重要信息!");
			logger.info("现金查询时,电力电费明细数据等信息没有保存,商户号:{} " , bm.getShopCode());
			return false;
		}
		String temp = cashTemp.getTempValue(); 
		bm.setCustomData(temp);
		return true;
	}
 
	
}
