package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 华电IC卡现金撤销（调用电商华电现金接口）
 * @author MDB
 *
 */
@Component
public class ISO8583Packet013012Helper extends ISO8583Packetxxx012Helper {
	
	private static final Logger logger = 
		LoggerFactory.getLogger(ISO8583Packet013012Helper.class);
	
	@Override
	protected String packField44(ControlMessage cm, BusinessMessage bm) {
		
		//是否系统出错
		if(!GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())){
			return bm.getResponseMsg();
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("交易流水："+bm.getSysJournalSeqno()+"取消成功!\n");
		stringBuffer.append("退回金额："+bm.getAmount()+"\n");
		logger.info(stringBuffer.toString());
		return stringBuffer.toString();
	}
	
}
