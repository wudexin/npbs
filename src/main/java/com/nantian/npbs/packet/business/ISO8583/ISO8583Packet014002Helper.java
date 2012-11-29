package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 华电现金缴费
 * @author MDB
 *
 */
@Component
public class ISO8583Packet014002Helper extends ISO8583Packetxxx002Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet014002Helper.class);
	
	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
		
		// 拼接错误信息
		if (!bm.getResponseCode().equals(GlobalConst.TRADE_STATUS_SUCCESS)) {
			return bm.getResponseMsg();
		}
		
		HuaElecCash cash = null;
		try {
			cash = (HuaElecCash)bm.getCustomData();
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
		alertMsg.append("本次余额:"+cash.getThisBalance()+"\n");
		
		logger.info("是否显示起止示数提示信息:[{}]",cash.getIsShowDetail());
		
		/**
		 * 添加对cash.getIsShowDetail()为null的判断---如果和电商超时时此值为null；
		 * 排除此处抛出异常pos端不锁屏，用户重新缴费，日终对照又补扣商户备付金情况发生
		 */
		//add by wzd  start 2012年5月8日15:38:53
		if(null == cash.getIsShowDetail()) {
			cash.setIsShowDetail(false);
		}
		//add by wzd end 2012年5月8日15:39:10
		alertMsg.append("年阶梯累计电量:").append(cash.getLevIncpq()).append("\n");
		
		if(true == cash.getIsShowDetail()){
			alertMsg.append("起止示数：\n");
			alertMsg.append("--------------------------------");
			alertMsg.append("您为预交、多月或一户多表缴费,请到电力营业厅打印用电详单！\n");
		}else{
			alertMsg.append("起止示数:"+cash.getSeNum()+"\n");
		}
		
		// 拼接低额提醒信息
		String lowBalanceStr = super.packField44(bm);
		if (null != lowBalanceStr && !"".equals(lowBalanceStr)) {
			if(cash.getIsShowDetail() == false){
				alertMsg.append("--------------------------------\n");
			}
			alertMsg.append(lowBalanceStr).toString();
		}
		
		bm.setResponseMsg(alertMsg.toString());
		
		return bm.getResponseMsg();
	}
	
	
}