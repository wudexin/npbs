package com.nantian.npbs.packet.business.WEB;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * EPOS参数下载
 * @author jxw
 *
 */
@Component
public class WEBSTRINGPacketxxx906Helper implements IPacketWEBSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		/*//交易码
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_CODE", bm.getTranCode());
		
		//PSMA卡号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_PSAM_CARDNO", bm.getPSAMCardNo());
		
		//EPOS流水号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_EPOS_SEQNO", bm.getPosJournalNo());
		
		//交易时间
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_TIME", bm.getLocalTime());
		
		//交易日期
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_DATE", bm.getTranDate());
		
		//PB流水号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_PB_SEQNO", bm.getPbSeqno());
		
		//应答码
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_RESPONSE_CODE", bm.getResponseCode());
		
		//终端编号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TERMINAL_ID", bm.getTerminalId());
		
		//应用信息域
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_APP_INFO_FIELD", bm.getAppInfoField());*/
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_web_55", bm.getCustomData().toString());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//交易码
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
		
		//应用信息域
		String appInfoField = (String) fieldValues.get("D_APP_INFO_FIELD");
		if (appInfoField == null) throw new PacketOperationException();
		bm.setAppInfoField(appInfoField);
	}

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_TRAN_CODE", 
				"D_PSAM_CARDNO",
				"D_EPOS_SEQNO", 
				"D_TERMINAL_ID", 
				"D_APP_INFO_FIELD"};
		return fields;
	}
}
