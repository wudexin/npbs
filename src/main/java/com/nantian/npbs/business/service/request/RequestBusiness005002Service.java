package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HDCashData;

/**
 * 邯郸燃气缴费
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness005002Service extends RequestBusiness002Service {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness005002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		try {
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

			String str = cashTemp.getTempValue();
			String[] savedStr = str.split("\\|");
			String userCode = savedStr[0];//用户编号
			String userType = savedStr[1];// 用户类型
			String lastAmt = savedStr[2];// 上次结余
			String totalRec = savedStr[3];// 记录总数
			String chargeId_1 = savedStr[4];// 第一笔缴费ID
			String oughtAmt_1 = savedStr[5];// 第一笔应缴金额

			// 缴费金额不小于第一笔应缴金额
			if (Double.parseDouble(oughtAmt_1) > bm.getAmount()) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("缴费金额不应小于第一笔应缴金额!");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("缴费金额不应小于第一笔应缴金额!");
				return false;
			}
			HDCashData hdCashData = new HDCashData();
			// 用户编号
			hdCashData.setUserCode(userCode);
			// 用户类型
			hdCashData.setUserType(userType);
			// 上次结余
			hdCashData.setLastAmt(lastAmt);
			// 记录总数
			hdCashData.setTotalRec(totalRec);
			// 第一笔缴费ID
			hdCashData.setChargeId_1(chargeId_1);
			// 第一笔应缴金额
			hdCashData.setOughtAmt_1(oughtAmt_1);

			bm.setCustomData(hdCashData);
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费不正常,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费不正常,请拨打客服电话咨询!");
			logger.error("缴费不正常!请查询表:CashTemp.商户号" + bm.getShopCode());
		}
		return true;
	}
}
