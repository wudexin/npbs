package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 缴费菜单更新
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx905Helper implements IPacketFIXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//交易码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_CODE", bm.getTranCode());
		
		//PSMA卡号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PSAM_CARDNO", bm.getPSAMCardNo());
		
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
		
		//应用信息域
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_APP_INFO_FIELD", bm.getAppInfoField());
		
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
		String[] fields = {"D_TRAN_CODE", "D_PSAM_CARDNO", "D_TERMINAL_ID", "D_APP_INFO_FIELD"};
		return fields;
	}
}
