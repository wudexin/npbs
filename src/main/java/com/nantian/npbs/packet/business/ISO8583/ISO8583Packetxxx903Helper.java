package com.nantian.npbs.packet.business.ISO8583;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * POS签到	000903
 * @author jxw
 *
 */
public class ISO8583Packetxxx903Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx903Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		//从fieldValues中取得上传报文中对应bitmap的值
		logger.info("开始解包到BM, chanleType[{}]" , cm.getChanelType());
		
		//Field 11, System trace number (POS journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//Field 41, TerminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//Field 42, Shop code (for POS)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//Field 60, TranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
	}
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		//对fieldValues中对应bitmap的赋值，解包结束后不打包原包数据
		//Field 11, POS journal no
		ISO8583PacketUtils.packField(fieldValues, 11, bm);
		
		//Field 12, Local time
		ISO8583PacketUtils.packField(fieldValues, 12, bm);
		
		//Field 13, Local date
		ISO8583PacketUtils.packField(fieldValues, 13, bm);
		
		//Field 37, PB journal no
		ISO8583PacketUtils.packField(fieldValues, 37, bm);
		
		//Field 38, User name
		//签到时传商户名，其他交易为用户名称
		bm.setUserName(bm.getShopName()); 
		//默认设置为用户名称,如果设置其他数据则不用该方法，或拷贝到usename
		ISO8583PacketUtils.packField(fieldValues, 38, bm);
	
		//Field 39, Response code
		ISO8583PacketUtils.packField(fieldValues, 39, bm);
		
		//Field 41, Terminal id
		ISO8583PacketUtils.packField(fieldValues, 41, bm);
		
		//Field 42, Shop code
		ISO8583PacketUtils.packField(fieldValues, 42, bm);
		
		//Field 44, Additional response data
		//字符串复制后下传
		if(GlobalConst.RESPONSECODE_SUCCESS.equals(bm.getResponseCode()))
		bm.setResponseMsg(bm.getAdditionalTip());
		// 默认设置响应信息，如果存在打印数据则不用该方法或拷贝到mag中
		ISO8583PacketUtils.packField(fieldValues, 44, bm);
		
		//Field 58, AdditionalTip
		//默认设置是提示信息，目前58域终端没有使用，直接在44域打印和提示
		ISO8583PacketUtils.packField(fieldValues, 58, bm); 
		
		//Field 60, Trade code
		ISO8583PacketUtils.packField(fieldValues, 60, bm);
		
		//Field 61, PBSeqno
		ISO8583PacketUtils.packField(fieldValues, 61, bm);
		
		//Field 62,  Work key
		ISO8583PacketUtils.packField(fieldValues, 62, bm);
		
	}

}
