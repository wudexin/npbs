package com.nantian.npbs.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.packet.BusinessMessage;

/**
 * 应答交易工厂
 * 
 * @author qiaoxiaolei
 * 
 */
public class AnswerBusinessFactory {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusinessFactory.class);

	public static IAnswerBusinessService create(BusinessMessage bm) {

		if ( bm.getTranCode().equals("005001")
				|| bm.getTranCode().equals("006001")
				|| bm.getTranCode().equals("007001")
				|| bm.getTranCode().equals("008001")
				|| bm.getTranCode().equals("009001")
				|| bm.getTranCode().equals("010001")
				|| bm.getTranCode().equals("013001")
				|| bm.getTranCode().equals("014001")
				|| bm.getTranCode().equals("016001")
				|| bm.getTranCode().equals("020001")
				//add by fengyafang 联通
				|| bm.getTranCode().equals("002001")
				|| bm.getTranCode().equals("004001")
				|| bm.getTranCode().equals("015001")
				// add by fengyafang 农电
				|| bm.getTranCode().equals("018001")
				) { 
			return SpringContextHolder.getBean("answerBusiness"
					+ bm.getTranCode() + "Service");
		}
		return SpringContextHolder.getBean("answerBusiness"
				+ bm.getTranCode().substring(3) + "Service");

	}
}
