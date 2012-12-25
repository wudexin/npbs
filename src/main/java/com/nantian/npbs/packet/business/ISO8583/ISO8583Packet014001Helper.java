package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 华电现金查询
 * @author MDB
 *
 */
@Component
public class ISO8583Packet014001Helper extends ISO8583Packetxxx001Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet014001Helper.class);
	
	@Override
	protected String packField44(BusinessMessage bm) throws PacketOperationException {
		
		// 拼接错误信息
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return bm.getResponseMsg();
		}
		
		HuaElecCash cash = null;
		try {
			cash = (HuaElecCash)bm.getCustomData();
		} catch (ClassCastException e) {
			logger.error("bm.getCustomData()类型转换错误");
			throw new PacketOperationException("bm.getCustomData()类型转换错误!");
		} catch (Exception e){
			logger.error("bm.getCustomData()强制转换抛出未知异常!");
			throw new PacketOperationException("bm.getCustomData()强制转换抛出未知异常!");
		}
		if(null == cash){
			logger.error("bm.getCustomData()为空值!");
			throw new PacketOperationException("bm.getCustomData()为空值!");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("用户编号:").append(cash.getUserCode()).append("\n");
		sb.append("用户名称:").append(cash.getUserName()).append("\n");
		sb.append("账户余额:").append(
				new DecimalFormat("0.00")
				.format(-cash.getAccBalance())).append("\n");
		sb.append("阶梯欠费:").append(cash.getLevAmt()).append("\n");
		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(sb.toString());
		//add by yafang 2012-03-21 end 
		return sb.toString();
	}
	
}
