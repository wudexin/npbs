package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

@Component
public class TUXSTRINGPacketxxx010Helper implements IPacketTUXSTRING {

	@Override
	public String[] addFields(String[] fields, int count,
			TUXSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub

	}
}
