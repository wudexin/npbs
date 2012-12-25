package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.ElectricityCashData;

/**
 * 现金电力缴费
 * 
 * @author qiaoxl
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness007002Service extends RequestBusiness002Service {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness007002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {

			try {
				logger.info("本交易流水[{}],查询流水号:{} ", bm.getPbSeqno(),bm.getOldPbSeqno());
				TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,bm.getOldPbSeqno());
				if (cashTemp == null) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("缴费失败,007001查询时没有保存电费明细数据等重要信息!");
					logger.info("007001查询时,电力电费明细数据等信息没有保存,商户号:{} ", bm.getShopCode());
					return false;
				}
	
				String str = cashTemp.getTempValue();
				Double totalBill = Double.parseDouble(str.split("\\|")[0]);
				String amtNum = str.split("\\|")[1];
				String reg = str.split("\\|")[2];
	
				// 缴费金额必须大于应缴金额
				if (totalBill > bm.getAmount()) {
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("缴费金额小于应缴金额!");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("缴费金额小于应缴金额!");
					return false;
				}
				ElectricityCashData cashData = new ElectricityCashData();
	
				// 缴费金额
				cashData.setPayAmt(bm.getAmount());
				// 电费明细数据
				cashData.setReg(reg);
				// 用户编号
				cashData.setCode(bm.getUserCode());
				// 用户名称
				cashData.setUsername(bm.getUserName());
				// 金额总笔数
				cashData.setAmtNum(amtNum);
				// 合计金额
				cashData.setTotalBill(totalBill.toString());
	
				bm.setCustomData(cashData);
			} catch (Exception e) {
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
