package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
/**
 *末笔交易查询
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket000010Helper extends TUXSTRINGPacketxxx010Helper {

	@Override
	public String[] hasFields() {
		String[] fields = {
				"DEAL_TRADE_STATE", //电子商务平台末笔交易状态
				"ORAG_DEAL_DATE",   //电子商务平台末笔交易日期
				"ORIG_DEAL_TIME",   //电子商务平台末笔交易时间
				"ORIG_SYS_JOURNAL_SEQNO", //电子商务平台末笔交易流水
				"TRAN_AMT"               //电子商务返回金额                
		};
		return fields;
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//交易日期
		PacketUtils.addFieldValue(fieldValues, "ORAG_DEAL_DATE", bm.getOrigDealDate());
		
		//电子商务平台需要的便民服务站需要的原交易流水号
		PacketUtils.addFieldValue(fieldValues, "ORIG_SYS_JOURNAL_SEQNO", bm.getOldPbSeqno()); 	
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 末笔交易状态
		String dealTradeState = (String) fieldValues.get("DEAL_TRADE_STATE");
		if (dealTradeState == null) throw new PacketOperationException();
		bm.setPbState(dealTradeState);
		
		//电子商务平台原交易日期 （交易状态为成功时有效）		
		String origDealDate = (String) fieldValues.get("ORAG_DEAL_DATE");
		if (origDealDate == null) throw new PacketOperationException();
		bm.setOrigLocalDate(origDealDate);
		
		//电子商务平台原交易时间（交易状态为成功时有效）
		String origDealTime = (String) fieldValues.get("ORIG_DEAL_TIME");
		if (origDealTime == null) throw new PacketOperationException();
		bm.setOrigLocalTime(origDealTime);
		
		//电子商务平台原交易流水 （状态为成功时有效）
		String origSysJournalSeqno = (String) fieldValues.get("ORIG_SYS_JOURNAL_SEQNO");
		if (origSysJournalSeqno == null) throw new PacketOperationException();
		bm.setOrigSysJournalSeqno(origSysJournalSeqno);
		
		//电子商务平台返回金额（单位为元）
		String origTranAmt = (String) fieldValues.get("TRAN_AMT");
		if(origTranAmt == null) throw new PacketOperationException();
		if(origTranAmt.trim().equals(""))
			origTranAmt = "0";
		bm.setAmount(Double.valueOf(origTranAmt));
		
		
	}

}
