package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HDCashData;

/**
 * 邯郸燃气缴费
 * 
 * @author jxw
 * 
 */
@Component
public class TUXSTRINGPacket005002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HDCashData hdCashData = (HDCashData) bm.getCustomData();

		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_USERCODE", hdCashData.getUserCode());// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_TYPENO", hdCashData.getUserType());// 用户类型
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_LASTAMT", hdCashData.getLastAmt());// 上次结余
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_PAYAMT", String.valueOf(bm.getAmount()));// 实交金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_TOTALREC", hdCashData.getTotalRec());// 记录总数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_CHARGEID_1", hdCashData.getChargeId_1());// 第一笔缴费ID
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_OUGHTAMT_1", hdCashData.getOughtAmt_1());// 第一笔应缴金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_CERTNO", "1234");// 凭证号码
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",bm.getTranDate());// 接入渠道日期
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",bm.getPbSeqno());// 接入渠道流水号
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		HDCashData hdCashData = (HDCashData) bm.getCustomData();
		
		// 本次结余
		String thisAmt = (String) fieldValues.get("D13_13_HDG_THISAMT");
		if (thisAmt == null)
			throw new PacketOperationException();
		hdCashData.setThisAmt(thisAmt);
		
		// 抄表期间
		String month = (String) fieldValues.get("D13_13_HDG_MONTH");
		if (month == null)
			throw new PacketOperationException();
		hdCashData.setHd_month(month);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {"D13_13_HDG_THISAMT","D13_13_HDG_MONTH" };
		return fields;
	}

}
