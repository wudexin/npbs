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
public class RequestBusiness016002Service extends RequestBusiness002Service {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness016002Service.class);

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
			Double sumFee = Double.parseDouble(savedStr[0]);// 应缴金额
			String userCode = savedStr[1];// 用户编号
			String userName = savedStr[2];// 用户名称
			String seqno3 = savedStr[3];// 第三方流水
			String ticketSum =savedStr[4]; // 发票张数

			// 缴费金额必须大于应缴金额
			logger.info("应缴金额:[{}],实际缴费金额:[{}]",sumFee,bm.getAmount());
			if (sumFee > bm.getAmount()) {
				logger.info("缴费金额不应小于应缴金额!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("缴费金额小于应缴金额!");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("缴费金额小于应缴金额!");
				return false;
			}
			WaterCashData waterCashData = new WaterCashData();
			//实际缴费
			waterCashData.setPayAmt(String.valueOf(bm.getAmount()));
			
			//以下从tb_bi_cash_temp表中根据pb流水读取
			// 用户编号
			waterCashData.setUserNo(userCode);
			// 用户名称
			waterCashData.setUsername(userName);
			bm.setUserName(userName);
			// 应收总额
			waterCashData.setSumFee(String.valueOf(sumFee));
			// 第三方流水
			waterCashData.setSeqno3(seqno3);
			// 发票张数
			waterCashData.setTicketSum(ticketSum);

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
