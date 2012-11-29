package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 对账文件申请
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class AnswerBusiness806Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness806Service.class);

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("对账文件申请Answer无业务逻辑!");
	}

}
