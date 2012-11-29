package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HDCashData;

/**
 * 邯郸燃气缴费
 * 
 * @author jxw
 * 
 */
@Component
public class ISO8583Packet005002Helper extends ISO8583Packetxxx002Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet005002Helper.class);

	@Override
	protected String packField44(BusinessMessage bm) {

		String lowBalanceStr = null;
		try {
			lowBalanceStr = super.packField44(bm);
		} catch (PacketOperationException e) {
			// TODO 
			e.printStackTrace();
		}
		StringBuffer alertMsg = new StringBuffer();
		// 拼接错误信息
		if (bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)) {
			return bm.getResponseMsg();
		}

		HDCashData hdCashData = (HDCashData) bm.getCustomData();
		if (null == hdCashData) {
			bm.setResponseMsg("系统错误,请联系管理员!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			return bm.getResponseMsg();
		}

		
		alertMsg.append("本次余额:" + hdCashData.getThisAmt() + "\n");
		alertMsg.append("抄表期间:" + hdCashData.getHd_month() + "\n");

		logger.info("本次结余:[{}]抄表期间:[{}]", 
				hdCashData.getThisAmt(), hdCashData.getHd_month());

		// 拼接低额提醒信息
		if (null != lowBalanceStr && !"".equals(lowBalanceStr)) {
			alertMsg.append("--------------------------------\n");
			alertMsg.append(lowBalanceStr).toString();
		}

		bm.setResponseMsg(alertMsg.toString());

		return alertMsg.toString();
	}
}
