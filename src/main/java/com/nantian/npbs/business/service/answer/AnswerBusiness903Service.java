package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * POS签到
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class AnswerBusiness903Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness903Service.class);
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("签到交易响应处理开始！无业务处理！");
		

	}



}
