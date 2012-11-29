package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 商户消息下载
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx011Helper implements IPacketFIXSTRING {

	@Override
	public String[] hasFields() {
		String[] fields = {};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//应用信息域
		if(null != bm.getCustomData()){
			if(bm.getCustomData().toString().length() > 512){
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_APP_INFO_FIELD", 
						bm.getCustomData().toString().substring(0,512));
			}else{
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_APP_INFO_FIELD", 
						bm.getCustomData().toString());
			}
		}
	}

}
