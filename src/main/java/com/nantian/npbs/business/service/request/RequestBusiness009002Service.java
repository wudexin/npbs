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
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 有线电视缴费
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness009002Service extends RequestBusiness002Service {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness009002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		
			if(null == bm.getOldPbSeqno() || "".equals(bm.getOldPbSeqno())) {
				logger.info("查询流水值为空！");
				bm.setResponseCode(GlobalConst.RESULTCODE_FAILURE);
				bm.setResponseMsg("查询流水为空！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询流水未取到!");
				
			}
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
			String customerNo = savedStr[0];//用户编号
			String accNo = savedStr[1];// 账户编码
			String serviceType = savedStr[2];// 业务类型
			String subscriberNo = savedStr[3];// 服务号码
			String curAmt = savedStr[4];// 当前余额
			String accBookNo = savedStr[5];// 余额账本编码
			String customerName = savedStr[6]; //用户名称
			
			
			bm.setUserName(customerName);   //设置用户名称，用于打印
			
			
			TVCashData tvCashData = new TVCashData();
			// 用户编号
			tvCashData.setCustomerNo(customerNo);
			// 账户编码
			tvCashData.setAccNo(accNo);
			// 业务类型
			tvCashData.setServiceType(serviceType);
			// 服务号码
			tvCashData.setSubscriberNo(subscriberNo);
			// 当前余额
			tvCashData.setCurAmt(curAmt);
			
			//实缴费用
			tvCashData.setRecFee(String.valueOf(bm.getAmount()));
			
			// 余额账本编码
			tvCashData.setAccBookNo(accBookNo);		
			
			// 接入渠道日期
			tvCashData.setCurPBDate(bm.getTranDate());
			
			// 接入渠道流水号
			tvCashData.setCurPBSerial(bm.getPbSeqno());

			bm.setCustomData(tvCashData);
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
