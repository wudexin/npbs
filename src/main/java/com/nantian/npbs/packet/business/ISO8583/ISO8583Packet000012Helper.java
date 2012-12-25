package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 交易取消
 * @author jxw
 *
 */
@Component
public class ISO8583Packet000012Helper extends ISO8583Packetxxx012Helper{

	private static final Logger logger = LoggerFactory.getLogger(ISO8583Packet000012Helper.class);
	
	//TODO: complete pack field 44
	@Override
	protected String packField44(ControlMessage cm,BusinessMessage bm) {
		StringBuffer stringBuffer = new StringBuffer();
		//是否系统出错
		if(!GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())){
			stringBuffer.append(bm.getResponseMsg());
			return stringBuffer.toString();
		}		
		//stringBuffer.append("退回金额："+new DecimalFormat("\u00A4###,###.00").format(bm.getAmount())+"元\n");
			
		logger.info(stringBuffer.toString());
		return stringBuffer.toString();
	}

	@Override
	protected String packField55(BusinessMessage bm) {
		if(null != bm.getCustomData()){
			return bm.getCustomData().toString();
		}
		return "";
	}
	
	
	
}
