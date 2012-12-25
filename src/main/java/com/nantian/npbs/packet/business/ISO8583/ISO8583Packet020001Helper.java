package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;
import com.nantian.npbs.packet.internal.MobileData;
import com.nantian.npbs.packet.internal.ZJKRQ;

/**
 * 张家口燃气查询
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet020001Helper extends ISO8583Packetxxx001Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet020001Helper.class);

	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {

		logger.info("开始打包44位元");
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return bm.getResponseMsg();
		}
		ZJKRQ zjkrq = (ZJKRQ) bm.getCustomData();
		if(null == zjkrq){
			return "查询信息为空!";
		}
		StringBuffer str = new StringBuffer();
		str.append("用户号码:").append(bm.getUserCode()).append("\n");
		str.append("用户姓名:").append(zjkrq.getUSER_NAME()).append("\n");
		str.append("欠费金额:").append(zjkrq.getSUM_FEE()).append("\n");
		if(Integer.parseInt(zjkrq.getREC_NUM())==1){
			str.append("起码:").append(zjkrq.getSTAR_DATA()).append("\n");
			str.append("止码:").append(zjkrq.getEND_DATA()).append("\n");
		}else{}
	
		bm.setResponseMsg(str.toString());
		return str.toString();
	}

	 
}
