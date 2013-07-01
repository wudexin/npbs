package com.nantian.npbs.packet.business.WEB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 缴费业务查询：移动手机
 * @author MDB
 *
 */
@Component
public class WEBSTRINGPacket001001Helper extends WEBSTRINGPacketxxx001Helper {
	
	private static Logger logger = LoggerFactory
			.getLogger(WEBSTRINGPacket001001Helper.class);
	
	@Override
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm) {
		
		if(!GlobalConst.RESPONSECODE_SUCCESS.equals(bm.getResponseCode())){
			return bm.getResponseMsg();
		}
		if( null == bm.getCustomData()){
			return "查询异常,返回数据为空!";
		}

		MobileData mobileData;
		try {
			mobileData = (MobileData) bm.getCustomData();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常, bm.getCustomData() 程序处理错误");
			return "查询异常,便民程序处理错误!";
		}
		 
		StringBuffer str = new StringBuffer();
		str.append("用户号码:").append(bm.getUserCode()).append("\n");
		str.append("用户姓名:").append(mobileData.getUserName()).append("\n");
		str.append("欠费金额:").append(DoubleUtils
				.sum(Double.parseDouble(mobileData.getFee()),
						Double.parseDouble(mobileData.getAmt()))).append("\n");
		
		return str.toString();
	}
}
