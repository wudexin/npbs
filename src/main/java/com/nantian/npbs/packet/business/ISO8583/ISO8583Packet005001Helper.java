package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.HDCashData;

/**
 * 邯郸燃气查询
 * @author jxw
 *
 */
@Component
public class ISO8583Packet005001Helper extends ISO8583Packetxxx001Helper {
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet005001Helper.class);
	@Override
	protected String packField44(BusinessMessage bm) {
		
		logger.info("开始打包44位元");
		if(!GlobalConst.RESPONSECODE_SUCCESS.equals(bm.getResponseCode())){
			return bm.getResponseMsg();
		}
		HDCashData hdCashData = (HDCashData)bm.getCustomData();
		if(null == hdCashData){
			return "查询信息为空！";
		}
		StringBuffer str = new StringBuffer();
		str.append("用户编号：").append(bm.getUserCode()).append("\n");
		str.append("用户名称:").append(hdCashData.getUserName()).append("\n");
		str.append("第一笔应缴金额:").append(hdCashData.getOughtAmt_1()).append("\n");

		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(str.toString());
		//add by yafang 2012-03-21 end    

		return str.toString();
	}
}
