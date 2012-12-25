package com.nantian.npbs.packet.business.FIXSTRING;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 缴费业务查询：电信手机
 * @author MDB
 *
 */
@Component
public class FIXSTRINGPacket003001Helper extends FIXSTRINGPacketxxx001Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(FIXSTRINGPacket003001Helper.class);
	
	@Override
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm) {
		
		if(!GlobalConst.RESPONSECODE_SUCCESS.equals(bm.getResponseCode())){
			return bm.getResponseMsg();
		}
		if( null == bm.getCustomData()){
			return "查询异常,返回数据为空!";
		}

		Telecommunications teleData;
		try {
			teleData = (Telecommunications) bm.getCustomData();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常, bm.getCustomData() 程序处理错误");
			return "查询异常,便民程序处理错误!";
		}
		
		StringBuffer str = new StringBuffer();
		str.append("用户号码:").append(bm.getUserCode()).append("\n");
		str.append("用户姓名:").append(teleData.getUserName()).append("\n");
		str.append("欠费金额:").append(teleData.getFee()).append("\n");
		
		return str.toString();
	}
}
