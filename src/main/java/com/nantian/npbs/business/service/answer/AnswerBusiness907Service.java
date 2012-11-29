package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * POS应用程序更新
 * @author 7tianle
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness907Service extends AnswerBusinessService {
	
	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness907Service.class);
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		// TODO Auto-generated method stub

	}


}
