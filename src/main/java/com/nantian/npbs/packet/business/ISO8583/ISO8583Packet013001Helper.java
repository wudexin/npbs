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
 * 华电IC卡卡表查询（调用电商华电现金接口）
 * @author MDB
 *
 */
@Component
public class ISO8583Packet013001Helper extends ISO8583Packetxxx001Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet013001Helper.class);
	
	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
		
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
		sb.append("用户名称:").append(cash.getUserName()).append("\n");
		sb.append("账户余额:").append(
				new DecimalFormat("0.00").format(-cash.getAccBalance())).append("\n");
		/**
		 * 2012年5月3日根据业务杨朝霞女士要求添加地址显示
		 */
		sb.append("用户地址:").append(cash.getAddress()).append("\n");
		//add by fengyafang 20120706start
		sb.append("阶梯欠费:").append(cash.getLevAmt()).append("\n");
		//add by fengyafang 20120706 end
		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(sb.toString());
		//add by yafang 2012-03-21 end  
		return sb.toString();
		
		/*HuaElecCash cash = null;
		try {
			cash = (HuaElecCash)bm.getCustomData();
		} catch (ClassCastException e) {
			bm.setResponseMsg("系统错误，查询异常!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			logger.error("bm.getCustomData()类型转换错误");
			return bm.getResponseMsg();
		} catch (Exception e){
			bm.setResponseMsg("系统错误，查询异常!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			logger.error("bm.getCustomData()强制转换抛出未知异常!");
			return bm.getResponseMsg();
		}
		if(null == cash){
			bm.setResponseMsg("系统错误，查询异常!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			logger.error("bm.getCustomData()为null!");
			return bm.getResponseMsg();
		}
		
		StringBuffer sb = new StringBuffer();
		//sb.append("用户编号:").append(cash.getUserCode()).append("\n");
		sb.append("用户名称:").append(cash.getUserName()).append("\n");
		sb.append("账户余额:").append(
				new DecimalFormat("0.00").format(-cash.getAccBalance())).append("\n");
		
		return sb.toString();*/
	}
	
}
