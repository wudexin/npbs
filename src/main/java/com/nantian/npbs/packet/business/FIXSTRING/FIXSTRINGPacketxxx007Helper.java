package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 终端查询交易量
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx007Helper implements IPacketFIXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 交易码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_CODE", bm.getTranCode());
		
		//PSMA卡号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PSAM_CARDNO", bm.getPSAMCardNo());

		//EPOS流水号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_EPOS_SEQNO", bm.getPosJournalNo());
		
		//交易时间
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_TIME", bm.getTranTime());
		
		//交易日期
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_DATE", bm.getTranDate());
		
		//PB流水号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PB_SEQNO", bm.getPbSeqno());
		
		//应答码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_RESPONSE_CODE", bm.getResponseCode());
		
		//终端编号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TERMINAL_ID", bm.getTerminalId());
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", bm.getAdditionalTip());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//业务交易码
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
		
		//终端编号
		String terminalId = (String) fieldValues.get("D_TERMINAL_ID");
		if (terminalId == null) throw new PacketOperationException();
		bm.setTerminalId(terminalId);
		
		//查询数据
		String queryData = (String) fieldValues.get("D_QUERY_DATA");
		if (queryData == null) throw new PacketOperationException();
		bm.setQueryData(queryData);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//交易日期时间段
		String dealDateTime = (String) fieldValues.get("D_DEAL_DATE_TIME");
		if (dealDateTime == null) throw new PacketOperationException();
		bm.setDealDateTime(dealDateTime);
	}

	@Override
	public String[] hasFields() {
		String[] fields = {"D_TRAN_CODE", "D_PSAM_CARDNO",  "D_EPOS_SEQNO", "D_TERMINAL_ID", "D_QUERY_DATA", "D_CURRENCY_CODE", "D__DEAL_DATE_TIME"};
		return fields;
	}

}
