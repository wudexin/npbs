package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金续费
 * 
 * @author HuBo
 * 
 */
@Scope("prototype")
@Component
public class AnswerBusiness802Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness802Service.class);

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金续费Answer无业务逻辑!");
	}

}
