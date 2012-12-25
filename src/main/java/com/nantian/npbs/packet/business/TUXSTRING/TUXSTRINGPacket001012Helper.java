package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 移动交易取消
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket001012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 平台流水号
		PacketUtils.addFieldValue(fieldValues, "D13_10_TRAN_SEQNO", bm.getSysJournalSeqno()); 
		
		// 电话号码
		PacketUtils.addFieldValue(fieldValues, "D13_10_PHONE", bm.getUserCode());

		// 原缴费金额
		PacketUtils.addFieldValue(fieldValues, "TRAN_AMT", String.valueOf(bm.getAmount()));

		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate());
		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// 用户名称
		String userName = (String) fieldValues.get("D13_10_NAME");
		if (userName == null) throw new PacketOperationException();
		bm.setUserName(userName); //返回pos
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_10_NAME"   						// 用户名称
				};
		
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}
