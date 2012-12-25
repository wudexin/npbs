package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.UnitcomCashData;

/**
 * 现金代收新联通缴费查询
 * 
 * @author wzd
 *
 */
@Component
public class ISO8583Packet015001Helper extends ISO8583Packetxxx001Helper {
	
	Logger logger = LoggerFactory.getLogger(ISO8583Packet015001Helper.class);

	@Override
	protected String packField44(BusinessMessage bm) {
		
		logger.info("开始打包联通44位元");
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return bm.getResponseMsg();
		}
		UnitcomCashData lt;
		try {
			lt = (UnitcomCashData) bm.getCustomData();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常, bm.getCustomData() 程序处理错误");
			return "查询异常,便民程序处理错误!";
		}
		if (null == lt) {
			return "查询信息为空!";
		}
		StringBuffer str = new StringBuffer();
		str.append("用户号码:").append(lt.getPhoneNum()).append("\n");
		str.append("用户姓名:").append(lt.getUserName()).append("\n");
		str.append("上次余额:").append(lt.getLastAmt()).append("\n");
		str.append("本期应缴:").append(lt.getOughtAmt()).append("\n");
		bm.setResponseMsg(str.toString());
		return str.toString();
	}

}
