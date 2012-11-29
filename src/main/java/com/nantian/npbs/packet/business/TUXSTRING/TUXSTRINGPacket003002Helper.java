package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 电信缴费
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket003002Helper  extends TUXSTRINGPacketxxx002Helper{

	/**
	 * 
	 */
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// TODO Auto-generated method stub
		Telecommunications teleData = (Telecommunications) bm.getCustomData();
		
		// 电话号码
		PacketUtils.addFieldValue(fieldValues, "D13_11_PHONE_NO", bm.getUserCode());

		// 缴费金额
		PacketUtils.addFieldValue(fieldValues, "D13_11_AMOUNT1", String.valueOf(bm.getAmount()));

		// 电信凭证号码
		PacketUtils.addFieldValue(fieldValues, "D13_11_CERT_NO", "");

		// 银行代码
		PacketUtils.addFieldValue(fieldValues, "D13_11_BANK_NO", teleData.getBankNo());

		// 银服务类型
		PacketUtils.addFieldValue(fieldValues, "D13_11_SERV_TYPE", teleData.getServiceType());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate()); 		//D13_13_HEE_CHANNEL_DATE

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());   // D13_13_HEE_CHANNEL_SEQNO
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		
		Telecommunications teleData = new Telecommunications();

		// 用户姓名
		String username = (String) fieldValues.get("D13_11_CUSTOM_NAME");
		if (username == null) throw new PacketOperationException();
		teleData.setUserName(username);
		bm.setUserName(username); //返回pos
		
		// 电话号码
		String phoneNo = (String) fieldValues.get("D13_11_PHONE_NO");
		if (phoneNo == null) throw new PacketOperationException();
		teleData.setPhoneNo(phoneNo);

		// 平台流水号
		String tranSeqNo = (String) fieldValues.get("D13_11_TRAN_SEQNO");
		if (tranSeqNo == null) throw new PacketOperationException();
		teleData.setTranSeqNo(tranSeqNo);

		// 缴费金额
		String amount = (String) fieldValues.get("D13_11_AMOUNT1");
		if (amount == null) throw new PacketOperationException();
		teleData.setAmount(amount);
		
		// 本期结存
		String amt5 = (String) fieldValues.get("D13_11_AMT5");
		if (amt5 == null) throw new PacketOperationException();
		teleData.setAmt5(amt5);
		
		// 当前结余
		String amt6 = (String) fieldValues.get("D13_11_AMT6");
		if (amt6 == null) throw new PacketOperationException();
		teleData.setAmt6(amt6);

		// 欠费金额
		String fee = (String) fieldValues.get("D13_11_FEE");
		if (fee == null) throw new PacketOperationException();
		teleData.setFee(fee);
		
		bm.setCustomData(teleData);
		
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_11_PHONE_NO",   					// 电话号码
				"D13_11_CUSTOM_NAME",     			// 用户姓名
				"D13_11_TRAN_SEQNO",     			// 平台流水号
				"D13_11_MONTH",     					// 欠费起始截止年月
				"D13_11_AMT4",     						// 计费合计
				"D13_11_AMOUNT1",     				// 缴费金额
				"D13_11_AMT1",     				// 上期结存
				"D13_11_AMT2",     			// 未出帐费用
				"D13_11_AMT5",     			// 本期结存
				"D13_11_AMT6",     			// 当前结余
				"D13_11_FEE",     			// 欠费金额
				"D13_11_MX1",     			// 打印明细1
				"D13_11_MX2",     			// 打印明细2
				"D13_11_MX3",     			// 打印明细3
				"D13_11_MX4",     			// 打印明细4
				"D13_11_MX5",     			// 打印明细5
				"D13_11_MX6",     			// 打印明细6
				"D13_11_MX7",     			// 打印明细7
				"D13_11_MX8",     			// 打印明细8
				"D13_11_MX9",     			// 打印明细9
				"D13_11_MX10",     		// 打印明细10
				"D13_11_MX11"    			// 打印明细11
				};
		
		return fields;
	}
}
