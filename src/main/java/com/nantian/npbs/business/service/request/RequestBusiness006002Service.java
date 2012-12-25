package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.WaterCashData;

/**
 * 水费缴费
 * 
 * @author hubo
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness006002Service extends RequestBusiness002Service {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness006002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		try {
			TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,
					bm.getOldPbSeqno());
			if (cashTemp == null) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("缴费失败,现金查询时没有保存水费明细数据等重要信息!");
				logger.info("现金查询时,水费明细数据等信息没有保存,商户号:{} " , bm.getShopCode());
				return false;
			}

			String str = cashTemp.getTempValue();
			String[] savedStr = str.split("\\|");
			String userno = savedStr[0];//用户编号
			String userName = savedStr[1];//用户名称
			Double sumFee = Double.parseDouble(savedStr[2]);// 应缴金额
			String recNum =savedStr[3];// 欠费月数

			// 缴费金额必须大于应缴金额
//			if (sumFee > bm.getAmount()) {
//				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
//				cm.setResultMsg("缴费金额小于应缴金额!");
//				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
//				bm.setResponseMsg("缴费金额小于应缴金额!");
//				return false;
//			}
			WaterCashData waterCashData = new WaterCashData();
			// 用户编号
			waterCashData.setUserNo(userno);
			// 用户编号
			waterCashData.setUsername(userName);
			bm.setUserName(userName);
			// 应收总额
			waterCashData.setSumFee(String.valueOf(sumFee));
			// 缴费金额
			waterCashData.setPayAmt(String.valueOf(bm.getAmount()));
			// 水费欠费月数
			waterCashData.setRecNum(recNum);
			// 凭证号码，如没有提供默认值
			waterCashData.setCertNo("");
			// 凭证类型，如没有提供默认值
			waterCashData.setVoucKind("");

			bm.setCustomData(waterCashData);
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
