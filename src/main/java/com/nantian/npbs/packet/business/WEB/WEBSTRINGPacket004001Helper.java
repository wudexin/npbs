package com.nantian.npbs.packet.business.WEB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.UnitcomCashData;

/**
 * 缴费查询，联通固话
 * 
 * @author fengyafang 
 * 
 */
@Component
public class WEBSTRINGPacket004001Helper extends WEBSTRINGPacketxxx001Helper {

	private static Logger logger = LoggerFactory
			.getLogger(WEBSTRINGPacket004001Helper.class);

	@Override
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm) {
		if (!GlobalConst.RESPONSECODE_SUCCESS.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}
		if (null == bm.getCustomData()) {
			return "查询异常,返回数据为空!";
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
