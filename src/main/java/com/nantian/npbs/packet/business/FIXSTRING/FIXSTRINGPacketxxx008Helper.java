package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * EPOS流水号查询交易流水
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx008Helper implements IPacketFIXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 交易码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_CODE", bm.getTranCode());
		
		//PSMA卡号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PSAM_CARDNO", bm.getPSAMCardNo());
		
		//交易时间
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_TIME", bm.getTranTime());
		
		//交易日期
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_DATE", bm.getTranDate());
		
		//系统流水号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SYS_SEQNO", bm.getSysJournalSeqno());
		
		//应答码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_RESPONSE_CODE", bm.getResponseCode());
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//EPOS原交易流水
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SYS_ORIGSEQNO", bm.getOrigSysJournalSeqno());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 交易码
		String tranCode = (String) fieldValues.get("D_TRAN_CODE");
		if (tranCode == null) throw new PacketOperationException();
		bm.setTranCode(tranCode);
		
		//PSMA卡号
		String pSAMCardNo = (String) fieldValues.get("D_PSAM_CARDNO");
		if (pSAMCardNo == null) throw new PacketOperationException();
		bm.setPSAMCardNo(pSAMCardNo);
		
		//EPOS流水号
		String ePOSSeqNo = (String) fieldValues.get("D_EPOS_SEQNO");
		if (ePOSSeqNo == null) throw new PacketOperationException();
		bm.setPosJournalNo(ePOSSeqNo);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//EPOS原交易流水
		String origSysJournalSeqno = (String) fieldValues.get("D_SYS_ORIGSEQNO");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setOrigSysJournalSeqno(origSysJournalSeqno);
	}

	@Override
	public String[] hasFields() {
		String[] fields = {"D_TRAN_CODE", "D_PSAM_CARDNO", "D_EPOS_SEQNO", "D_CURRENCY_CODE", "D_SYS_ORIGSEQNO"};
		return fields;
	}

}
