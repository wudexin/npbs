package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 交易取消
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket000012Helper extends TUXSTRINGPacketxxx012Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
	}
	
	@Override
	public String[] hasFields() {
		return null;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

	
}
