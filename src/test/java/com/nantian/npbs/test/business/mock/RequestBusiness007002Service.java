package com.nantian.npbs.test.business.mock;

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

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness007002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {

		try {
			TempData cashTemp = new TempData();
			cashTemp.setPbSeqno(bm.getOldPbSeqno());
			cashTemp.setTempValue("10.0|100|dddddd");

			// 时间，不能为空，日终使用
			cashTemp.setTradeDate(bm.getTranDate());
			
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
