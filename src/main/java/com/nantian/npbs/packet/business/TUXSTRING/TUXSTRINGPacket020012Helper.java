package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ZJKRQ;

/**
 * 河北燃气缴费取消
 * 
 * @author fyf
 * 
 */
@Component
public class TUXSTRINGPacket020012Helper extends TUXSTRINGPacketxxx012Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket020012Helper.class);

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		ZJKRQ cardData = (ZJKRQ) bm.getCustomData();
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_USER_NO",bm.getUserCode());//用户编号                      
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_SEQ_NO",bm.getOrigSysJournalSeqno());//原交易流水号                   
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_PAY_AMT",String.valueOf(bm.getAmount()));//原缴费金额                      
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_CHANNEL_DATE",bm.getTranDate());//接入渠道日期                  
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_CHANNEL_SEQNO",bm.getPbSeqno());//接入渠道流水号 
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ZJKRQ cash = new ZJKRQ();

		//  用户名称
		String USER_NAME  = (String) fieldValues.get("D13_13_ZJKG_USER_NAME");
		if (USER_NAME == null)
			throw new PacketOperationException();
		 cash.setUSER_NAME(USER_NAME);
		 
		 
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "D13_13_ZJKG_USER_NAME",   //20  用户名称
		};
		return fields;
	}

}
