package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 农电缴费
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet018002Helper extends ISO8583Packetxxx002Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet018002Helper.class);

	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--开始");
		HeNDElecICCard customData = new HeNDElecICCard();
			logger.info("河北农电IC卡----55域信息解包到卡信息实体----开始");
			 String buffer = (String)field;
				customData.setCONS_NO(buffer.toString());// 客户编号
		bm.setUserCode(customData.getCONS_NO().trim());//用户编号
		bm.setCustomData(customData);
		logger.info("河北农电IC卡缴费--解包55位元读卡信息解包--结束"); 
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河北农电IC卡缴费--打包55位元--开始");
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		} 
	 
		logger.info("河北农电IC卡缴费--打包55位元--结束");
	 
		return customData.getWRITE_INFO();
	}

	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		//add by mengqingwei 20121030 start
		logger.info("开始打包44位元");
		HeNDElecICCard cashData = (HeNDElecICCard) bm.getCustomData();
		if(null == cashData){
			bm.setResponseMsg("系统错误,请联系管理员!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			return bm.getResponseMsg();
		}
		StringBuffer str = new StringBuffer();
		if(!("".equals(cashData.getLADDER_DIFF())||cashData.getLADDER_DIFF()==null))
		str.append("阶梯差价:").append(cashData.getLADDER_DIFF()).append("\n");
		if(!("".equals(cashData.getANNUAL_VALUE())||cashData.getANNUAL_VALUE()==null))
		str.append("本年累计用电量:").append(cashData.getANNUAL_VALUE()).append("\n");
		if(!("".equals(cashData.getLADDER_SURPLUS())||cashData.getLADDER_SURPLUS()==null))
		str.append(cashData.getLADDER_SURPLUS());
		if(str.toString().length()>1){
		 bm.setResponseMsg(str.toString());
		 return str.toString();
		}else{
			return bm.getResponseMsg();
		}
		
		//add by mengqingwei 20121030 end
	}
	

}
