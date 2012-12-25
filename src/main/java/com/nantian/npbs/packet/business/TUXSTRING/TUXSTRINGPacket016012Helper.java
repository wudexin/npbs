package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 保定水费取消
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket016012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// 电子商务平台原流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_SEQNO", bm.getOrigSysJournalSeqno());
		
		//用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_BDW_USERNO", bm.getUserCode());
		
		// 便民日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_BM_DATE", bm.getTranDate());
		
		// 便民流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_BM_SEQNO", bm.getPbSeqno());
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
				"D13_10_NAME"    //用户名称
		};
		return fields;
	}
}
