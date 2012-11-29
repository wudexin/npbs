package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.XAGasCashData;

/**
 * 新奥燃气现金代收 缴费
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket008002Helper extends TUXSTRINGPacketxxx002Helper {
	
	//打包发电商平台
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		XAGasCashData cashData = (XAGasCashData)bm.getCustomData();
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_USER_NO", cashData.getUserCode());
		// 发卡次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_CARD_NUM", cashData.getFkcs());
		// 充值金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_PAY_AMT", String.valueOf(cashData.getAmount()));
		// 接入渠道日期
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE", cashData.getCurPBDate());
		// 接入渠道流水号
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO", cashData.getCurPBSerial());
		
	}
	
	//解包到便民
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		XAGasCashData cashData = new XAGasCashData();
		
		// 最新余额
		String curAmount = (String) fieldValues.get("D13_13_XAG_CURR_BAL");
		if (curAmount == null) throw new PacketOperationException();
		cashData.setAmount(curAmount);
		
		// 平台流水号
		String sysSerial = (String) fieldValues.get("D13_13_XAG_SEQ_NO");
		if (sysSerial == null) throw new PacketOperationException();
		cashData.setCurSysSerial(sysSerial);
		
		bm.setCustomData(cashData);

	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_XAG_CURR_BAL",		// 最新余额
				"D13_13_XAG_SEQ_NO"			// 平台流水号
				};
		return fields;
	}
}
