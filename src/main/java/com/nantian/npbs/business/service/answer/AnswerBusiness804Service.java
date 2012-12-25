package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金明细查询
 * 
 * @author HuBo
 * 
 */
@Scope("prototype")
@Component
public class AnswerBusiness804Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness804Service.class);

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金明细查询Answer无业务逻辑!");
	}

}
