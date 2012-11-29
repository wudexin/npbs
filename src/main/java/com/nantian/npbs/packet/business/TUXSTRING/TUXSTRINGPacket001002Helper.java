package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 移动缴费
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket001002Helper extends TUXSTRINGPacketxxx002Helper{

	/**
	 * 
	 */
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// TODO Auto-generated method stub
		// 电话号码
		PacketUtils.addFieldValue(fieldValues, "D13_10_PHONE", bm.getUserCode());

		// 交费标志
		PacketUtils.addFieldValue(fieldValues, "D13_10_PAY_TYPE", "1");  // 1号码；2账号

		// 缴费金额
		PacketUtils.addFieldValue(fieldValues, "D13_10_AMT1", String.valueOf(bm.getAmount()));

		// 话费欠费月份
		PacketUtils.addFieldValue(fieldValues, "D13_10_CERT_NO", "");  // 取查询的信息,取空
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate()); 

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub

		MobileData mobileData = new MobileData();

		// 用户姓名
		String userName = (String) fieldValues.get("D13_10_NAME");
		if (userName == null) throw new PacketOperationException();
		mobileData.setUserName(userName);
		bm.setUserName(userName); //返回pos
		
		// 电话号码
		String phone = (String) fieldValues.get("D13_10_PHONE");
		if (phone == null) throw new PacketOperationException();
		mobileData.setPhone(phone);
		
		// 平台流水号
		String tranSeqNo = (String) fieldValues.get("D13_10_TRAN_SEQNO");
		if (tranSeqNo == null) throw new PacketOperationException();
		mobileData.setTranSeqNo(tranSeqNo);
		
		// 移动流水号
		String cmSeqNo = (String) fieldValues.get("D13_10_CMSEQNO");
		if (cmSeqNo == null) throw new PacketOperationException();
		mobileData.setCmSeqNo(cmSeqNo);
		
		// 收款合计
		String totalAmt = (String) fieldValues.get("D13_10_TOTAL_AMT");
		if (totalAmt == null) throw new PacketOperationException();
		mobileData.setTotalAmt(totalAmt);
		
		bm.setCustomData(mobileData);
		
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_10_NUM",   						// 发票张数
				"D13_10_PRT_TIME",     			// 打印时间
				"D13_10_CUSTBRAND",     		// 客户品牌
				"D13_10_NAME",     					// 用户姓名
				"D13_10_PHONE",     				// 电话号码
				"D13_10_TRAN_SEQNO",     		// 平台流水号
				"D13_10_CMSEQNO",     			// 移动流水号
				"D13_10_TOTAL_AMT",     		// 收款合计(发票用)
				"D13_10_PRT_DATA",     			// 打印交易日期
				"D13_10_PRT_MX1",     				// 打印明细1
				"D13_10_PRT_MX2",     				// 打印明细2
				"D13_10_PRT_MX3",     				// 打印明细3
				"D13_10_PRT_MX4",     				// 打印明细4
				"D13_10_PRT_MX5",     				// 打印明细5
				"D13_10_PRT_MX6",     				// 打印明细6
				"D13_10_PRT_MX7",     				// 打印明细7
				"D13_10_PRT_MX8",     				// 打印明细8
				"D13_10_PRT_MX9",     				// 打印明细9
				"D13_10_PRT_MX10",     			// 打印明细10
				"D13_10_PRT_MX11",     			// 打印明细11
				"D13_10_PRT_MX12",     			// 打印明细12
				"D13_10_PRT_MX13",     			// 打印明细13
				"D13_10_PRT_MX14",     			// 打印明细14
				"D13_10_PRT_MX15",     			// 打印明细15
				"D13_10_PRT_MX16",     			// 打印明细16
				"D13_10_PRT_MX17",     			// 打印明细17
				"D13_10_PRT_TRANDATA",     			// 计费期间
				"D13_10_PRT_REMARK1",     			// 备注1
				"D13_10_PRT_REMARK2",     			// 备注2
				"D13_10_PRT_REMARK3",     			// 备注3
				"D13_10_PRT_TOTALCONT",     		// 
				"D13_10_PRT_BANKNAME"    			// 打印银行名称
				};
		
		return fields;
	}
	
}
