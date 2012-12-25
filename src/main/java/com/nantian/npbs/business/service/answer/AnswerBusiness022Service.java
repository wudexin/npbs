package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 申请补写卡数据
 * @author
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness022Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness022Service.class);
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		// TODO 是否与申请写卡数据一样处理备付金？
		logger.info("申请写卡数据（补写）响应处理开始！无业务逻辑。");

	}



}
