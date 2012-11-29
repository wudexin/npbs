package com.nantian.npbs.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.business.service.request.WebRequestBusinessService;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;

/**
 * 请求服务工厂类
 * 
 * @author 创建方法：create 请求服务的命名规则：requestBusiness + 6位交易码 + Service
 * 
 */
public class WebRequestBusinessFactory {

	private static Logger logger = LoggerFactory
			.getLogger(WebRequestBusinessFactory.class);

	public static WebRequestBusinessService create(String busi_code) {
	 
			return SpringContextHolder.getBean("WebRequestBusiness"
					+ busi_code + "Service");

	}

}
