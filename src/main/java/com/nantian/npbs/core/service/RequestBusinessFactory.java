package com.nantian.npbs.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.packet.BusinessMessage;

/**
 * 请求服务工厂类
 * 
 * @author qiaoxiaolei 创建方法：create 请求服务的命名规则：requestBusiness + 3位交易码 + Service
 * 
 */
public class RequestBusinessFactory {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusinessFactory.class);

	public static IRequestBusinessService create(BusinessMessage bm) {

		if (bm.getTranCode().equals("003001")
				|| bm.getTranCode().equals("003002")
				|| bm.getTranCode().equals("005002")
				|| bm.getTranCode().equals("006002")
				|| bm.getTranCode().equals("007002")
				|| bm.getTranCode().equals("008002")
				|| bm.getTranCode().equals("009002")
				|| bm.getTranCode().equals("010002")
				|| bm.getTranCode().equals("013002")
				|| bm.getTranCode().equals("014002")
				|| bm.getTranCode().equals("016002")
				||bm.getTranCode().equals("020002")
				//add by fengyafang 20120730 联通
				||bm.getTranCode().equals("002002")
				||bm.getTranCode().equals("004002")
				||bm.getTranCode().equals("015002")
				//add by fengyafang  农电
				||bm.getTranCode().equals("018002")
		) {
			return SpringContextHolder.getBean("requestBusiness"
					+ bm.getTranCode() + "Service");
		} 

		return SpringContextHolder.getBean("requestBusiness"
				+ bm.getTranCode().substring(3) + "Service");

	}

}
