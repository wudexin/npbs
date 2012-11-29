package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 *  用户交易明细查询
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx006Helper implements IPacketFIXSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		
		//查询数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_QUERY_DATA", bm.getQueryData());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//原交易日期
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ORIG_DEAL_DATE", bm.getOrigDealDate());
		
	}

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_QUERY_DATA", 
				"D_CURRENCY_CODE", 
				"D_SYS_ORIGSEQNO"};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//查询数据
		String queryData = (String) fieldValues.get("D_QUERY_DATA");
		if (queryData == null) throw new PacketOperationException();
		bm.setQueryData(queryData);	
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//原系统流水号
		String sysJournalSeqno = (String) fieldValues.get("D_SYS_ORIGSEQNO");
		if (sysJournalSeqno == null) throw new PacketOperationException();
		bm.setSysJournalSeqno(sysJournalSeqno);
	}

}
