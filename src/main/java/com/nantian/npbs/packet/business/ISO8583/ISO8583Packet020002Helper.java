package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;
import com.nantian.npbs.packet.internal.ZJKRQ;

/**
 * 张家口燃气缴费
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet020002Helper extends ISO8583Packetxxx002Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet020002Helper.class);

	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
		
		// 拼接错误信息
		if (!bm.getResponseCode().equals(GlobalConst.TRADE_STATUS_SUCCESS)) {
			return bm.getResponseMsg();
		}
		
		ZJKRQ cash = null;
		try {
			cash = (ZJKRQ)bm.getCustomData();
		} catch (ClassCastException e) {
			logger.error("bm.getCustomData()类型转换错误!");
			throw new PacketOperationException("bm.getCustomData()类型转换错误!");
		} catch (Exception e){
			logger.error("bm.getCustomData()强制转换抛出未知异常!");
			throw new PacketOperationException("bm.getCustomData()强制转换抛出未知异常!");
		}
		if(null == cash){
			logger.error("bm.getCustomData()为空值!");
			throw new PacketOperationException("bm.getCustomData()为空值!");
		}
		StringBuffer alertMsg = new StringBuffer();
		alertMsg.append("上次余额:"+cash.getLAST_BAL()+"\n");
		alertMsg.append("本次结存:"+cash.getCURR_BAL()+"\n");
		bm.setResponseMsg(alertMsg.toString());
		
		return bm.getResponseMsg();
	}
}
