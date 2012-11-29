package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 华电IC卡现金缴费
 * 
 * @author MDB
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness013002Service extends RequestBusiness002Service {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness013002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		try {
			
			logger.info("本交易流水[{}],查询流水号:[{}] ", bm.getPbSeqno(),bm.getOldPbSeqno());
			TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,bm.getOldPbSeqno());
			
			if (cashTemp == null) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("缴费失败,现金查询时没有保存电费明细数据等重要信息!");
				logger.info("现金查询时,电力电费明细数据等信息没有保存,商户号: {}" , bm.getShopCode());
				return false;
			}
			
			String str = cashTemp.getTempValue();
			String[] temp = str.split("\\|");
			HuaElecCash cash = new HuaElecCash();
			
			cash.setAccBalance(Double.parseDouble(temp[0]));	// 账户余额
			cash.setOrgNo(temp[1]);								// 供电单位编号
			cash.setRecordNo(temp[2]);							// 记录条数
			cash.setRecord(temp[3]);							// 记录明细
			bm.setUserName(temp[4]);							// 用户名称
			
			//缴费金额
			cash.setAmount(String.valueOf(bm.getAmount()));
			bm.setCustomData(cash);	
			
		} catch (Exception e) {
			logger.error("获取临时表数据错误："+e);
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费不正常,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费不正常,请拨打客服电话咨询!");
			logger.error("缴费不正常!请查询表:CashTemp.商户号" + bm.getShopCode());
			return false;
		}
		return true;
	}
}
