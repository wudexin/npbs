/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldsConfig;

/**
 * @author TsaiYee
 *
 */
@Component
public  class TUXSTRINGPacketxxx001Helper implements IPacketTUXSTRING {

	protected FieldsConfig fieldsConfig = TUXSTRINGFieldsConfig.getInstance();
	protected String spliter = TUXSTRINGFieldsConfig.FIELD_SPLITER;

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
	
	@Override
	public String[] hasFields() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}
}
