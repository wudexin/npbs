package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 现金新奥燃气交易取消
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket008012Helper extends TUXSTRINGPacketxxx012Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//用户号
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_USER_NO", bm.getUserCode());
		
		//电商缴费流水号
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_OLD_SEQ_NO", bm.getSysJournalSeqno());
		
		//缴费金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_PAY_AMT", String.valueOf(bm.getAmount()));

		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate());
		
		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//用户姓名
		String userName = (String)fieldValues.get("D13_13_XAG_USER_NAME");
		if(userName == null) throw new PacketOperationException();
		bm.setUserName(userName);
		
		
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_XAG_USER_NAME"				
		};
		
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}
