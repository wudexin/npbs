package com.nantian.npbs.gateway.camel.sedaroute;

import org.apache.camel.Exchange;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketUtils;

public class SedaRouteUtils {

	public static String getServiceDefaultBusinessRoute(SEDA_TYPE type) {
		return "seda:" + "000000" + type;
	}

	public static String getServiceBusinessRoute(String tranCode, SEDA_TYPE type) {

		String sedaCode = null;
		// 如果需要独立队列，则使用 seda + tranCode + type的模式, 如果不需要，则使用默认队列
		for (String c : DynamicConst.SERVICESEDALIST) {
			if (c.length() == 3) {
				if (tranCode.startsWith(c))
					sedaCode = c;
			} else if (c.equals(tranCode))
				sedaCode = tranCode;

			if (sedaCode != null)
				return "seda:" + sedaCode + type;
		}

		return getServiceDefaultBusinessRoute(type);
	}

	public static String getChanelBusinessRoute(Exchange exchange,
			SEDA_TYPE type) {
		BusinessMessage bm = PacketUtils.getBusinessMessage(exchange);

		return getChanelBusinessRoute(bm.getChanelType(), type);
	}

	public static String getChanelBusinessRoute(CHANEL_TYPE chnl_type,
			SEDA_TYPE seda_type) {

		return "seda:" + chnl_type + seda_type;
	}

	public static String getAsyncAnswerRoute() {
		return "seda:" + SEDA_TYPE.ASYNCANSWER;
	}
}
