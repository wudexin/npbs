package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.WaterCashData;

/**
 * 现金代收张家口水费（缴费）
 * 
 * @author hubo
 * 
 */
@Component
public class TUXSTRINGPacket006002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		WaterCashData waterCashData = (WaterCashData) bm.getCustomData();

		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CODE", waterCashData.getUserNo());// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKW_SUM_FEE",waterCashData.getSumFee());// 应收总额
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKW_PAY_AMT",waterCashData.getPayAmt());// 缴费金额
		//收费月份一般为000000代表全部欠费
		waterCashData.setFeeMon("000000");// 欠费月数如果木有取默认值000000
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_MONTH",waterCashData.getFeeMon());// 水费欠费月份
		
		if (null == waterCashData.getCertNo()
				|| "".equals(waterCashData.getCertNo())) {
			waterCashData.setCertNo("10092");// 凭证号码如果木有，取默认值
		}
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKW_CERT_NO",waterCashData.getCertNo());// 凭证号码
		
		if (null == waterCashData.getVoucKind()
				|| "".equals(waterCashData.getVoucKind())) {
			waterCashData.setVoucKind("1302");// 凭证类型如果木有，取默认值
		}
		PacketUtils.addFieldValue(fieldValues, "B05_VOUC_KIND", waterCashData.getVoucKind());// 凭号类型
		
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",bm.getTranDate());// 接入渠道日期
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",bm.getPbSeqno());// 接入渠道流水号

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub

		WaterCashData waterCashData = new WaterCashData();

		// 凭证终止序号
		String billLstno = (String) fieldValues.get("B01_BILL_LSTNO");
		if (billLstno == null)
			throw new PacketOperationException();
		waterCashData.setBillLstno(billLstno);
		// 凭证流水
		String seqno = (String) fieldValues.get("D13_13_ZJKW_SEQ_NO");
		if (seqno == null)
			throw new PacketOperationException();
		waterCashData.setBillLstno(seqno);
		// 欠费总额
		String sumNum = (String) fieldValues.get("D13_13_ZJKW_SUM_NUM");
		if (sumNum == null)
			throw new PacketOperationException();
		waterCashData.setSumNum(sumNum);
		// 上次抄表时间
		String lastDate = (String) fieldValues.get("D13_13_ZJKW_LAST_DATE");
		if (lastDate == null)
			throw new PacketOperationException();
		waterCashData.setLastDate(lastDate);
		// 本次抄表时间
		String currDate = (String) fieldValues.get("D13_13_ZJKW_CURR_DATE");
		if (currDate == null)
			throw new PacketOperationException();
		waterCashData.setCurrDate(currDate);
		// 水费
		String amt1 = (String) fieldValues.get("D13_13_ZJKW_AMT_1");
		if (amt1 == null)
			throw new PacketOperationException();
		waterCashData.setAmt1(amt1);
		// 污水费
		String amt2 = (String) fieldValues.get("D13_13_ZJKW_AMT_2");
		if (amt2 == null)
			throw new PacketOperationException();
		waterCashData.setAmt2(amt2);
		// 水资源费(源水费)
		String amt3 = (String) fieldValues.get("D13_13_ZJKW_AMT_3");
		if (amt3 == null)
			throw new PacketOperationException();
		waterCashData.setAmt3(amt3);
		// 上次读数
		String lastBal = (String) fieldValues.get("D13_13_ZJKW_LAST_BAL");
		if (lastBal == null)
			throw new PacketOperationException();
		waterCashData.setLastBal(lastBal);
		// 本次读数
		String currBal = (String) fieldValues.get("D13_13_ZJKW_CURR_BAL");
		if (currBal == null)
			throw new PacketOperationException();
		waterCashData.setCurrBal(currBal);
		// 水价1
		String price1 = (String) fieldValues.get("D13_13_ZJKW_PRICE_1");
		if (price1 == null)
			throw new PacketOperationException();
		waterCashData.setPrice1(price1);
		// 水价2
		String price2 = (String) fieldValues.get("D13_13_ZJKW_PRICE_2");
		if (price2 == null)
			throw new PacketOperationException();
		waterCashData.setPrice2(price2);
		// 水价3
		String price3 = (String) fieldValues.get("D13_13_ZJKW_PRICE_3");
		if (price3 == null)
			throw new PacketOperationException();
		waterCashData.setPrice3(price3);
		// 本次抄时间
		String currData = (String) fieldValues.get("D13_13_ZJKW_CURR_DATA");
		if (currData == null)
			throw new PacketOperationException();
		waterCashData.setCurrData(currData);

		bm.setCustomData(waterCashData);

	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "B01_BILL_LSTNO", "D13_13_ZJKW_SEQ_NO",
				"D13_13_ZJKW_SUM_NUM", "D13_13_ZJKW_LAST_DATE",
				"D13_13_ZJKW_CURR_DATE", "D13_13_ZJKW_AMT_1",
				"D13_13_ZJKW_AMT_2", "D13_13_ZJKW_AMT_3",
				"D13_13_ZJKW_LAST_BAL", "D13_13_ZJKW_CURR_BAL",
				"D13_13_ZJKW_PRICE_1", "D13_13_ZJKW_PRICE_2",
				"D13_13_ZJKW_PRICE_3", "D13_13_ZJKW_CURR_DATA" };
		return fields;
	}

}
