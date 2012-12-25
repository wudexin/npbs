package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 现金代收有线电视
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket009002Helper extends TUXSTRINGPacketxxx002Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket009002Helper.class);

	//打包发送电子商务平台
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		TVCashData cashData = (TVCashData)bm.getCustomData();
		
		if(null == cashData) {
			logger.error("打包需要对象为空~！~");
			return;
		}
		
		//用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_16_CUSTOMERNO", cashData.getCustomerNo());
		
		//账户编码
		PacketUtils.addFieldValue(fieldValues, "D13_16_ACCNO", cashData.getAccNo());
		
		//业务类型
		PacketUtils.addFieldValue(fieldValues, "D13_16_SERVICETYPE", cashData.getServiceType());
		
		//服务号码
		PacketUtils.addFieldValue(fieldValues, "D13_16_SUBSCRIBERNO", cashData.getSubscriberNo());
		
		//当前余额
		PacketUtils.addFieldValue(fieldValues, "D13_16_CURAMT", cashData.getCurAmt());
		
		//实缴费用
		PacketUtils.addFieldValue(fieldValues, "D13_16_RECFEE", cashData.getRecFee());
		
		//余额账本编码
		PacketUtils.addFieldValue(fieldValues, "D13_16_ACCBOOKNO", cashData.getAccBookNo());
		
		//凭证代码
		PacketUtils.addFieldValue(fieldValues, "D13_16_CERT_HEAD", "");
		
		//凭证号码
		PacketUtils.addFieldValue(fieldValues, "D13_16_CERT_NO", "");
		
		//便民日期
		PacketUtils.addFieldValue(fieldValues, "D13_16_BM_DATE", cashData.getCurPBDate());
		
		//便民流水
		PacketUtils.addFieldValue(fieldValues, "D13_16_BM_SEQNO", cashData.getCurPBSerial());
	}

//	解包到便民
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
				
		String curSysSerial = (String)fieldValues.get("D13_16_SEQNO");
		if(curSysSerial == null) throw new PacketOperationException();
		bm.setSysJournalSeqno(curSysSerial);
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_16_SEQNO"
		};
		
		return fields;
	}
}
