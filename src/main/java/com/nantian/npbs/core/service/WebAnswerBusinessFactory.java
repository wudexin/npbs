package com.nantian.npbs.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.business.service.answer.WebAnswerBusinessService;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;

/**
 * 请求服务工厂类
 * 
 * @author 创建方法：create 请求服务的命名规则：answerBusiness + 6位交易码 + Service
 * 
 */
public class WebAnswerBusinessFactory {

	private static Logger logger = LoggerFactory
			.getLogger(WebAnswerBusinessFactory.class);

	public static WebAnswerBusinessService create(String busi_code) {
	 
			return SpringContextHolder.getBean("WebAnswerBusiness"
					+ busi_code + "Service");

	}

}
