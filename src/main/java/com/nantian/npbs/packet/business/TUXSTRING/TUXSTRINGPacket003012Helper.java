package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.internal.TelePhoneNumUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 电信取消
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket003012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		Telecommunications teleData = new Telecommunications();
		TelePhoneNumUtils.getBankNoAndServiceType(teleData,bm.getUserCode());
		
		// 电话号码
		PacketUtils.addFieldValue(fieldValues, "D13_11_PHONE_NO", bm.getUserCode());

		// 平台流水号
		PacketUtils.addFieldValue(fieldValues, "D13_11_TRAN_SEQNO", bm.getSysJournalSeqno());

		// 银行代码
		PacketUtils.addFieldValue(fieldValues, "D13_11_BANK_NO", teleData.getBankNo());

		// 服务类型
		PacketUtils.addFieldValue(fieldValues, "D13_11_SERV_TYPE", teleData.getServiceType());

		// 交易金额
		PacketUtils.addFieldValue(fieldValues, "TRAN_AMT", String.valueOf(bm.getAmount()));
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate());
		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// 用户姓名
		String userName = (String) fieldValues.get("D13_10_NAME");
		if (userName == null) throw new PacketOperationException();
		bm.setUserName(userName); //返回pos
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_10_NAME"   						// 发票张数
				};
		
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}
