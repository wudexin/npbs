package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 张家口水费取消
 * @author jxw
 *
 */
@Component
public class TUXSTRINGPacket006012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_NO", bm.getUserCode()); 
		
		// 原交易流水号（电子商务平台）
		PacketUtils.addFieldValue(fieldValues, "D13_10_TRAN_SEQNO", bm.getOrigSysJournalSeqno()); 
		
		//原缴费金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_AMT2", String.valueOf(bm.getAmount())); 
		
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
