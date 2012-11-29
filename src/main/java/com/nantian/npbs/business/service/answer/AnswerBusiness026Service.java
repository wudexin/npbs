package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness026Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness026Service.class);
	// 流水操作
	@Resource
	public TradeDao dao;
	// 备付金
	@Resource
	public CommonPrepay commonPrepay;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("补写卡交易响应处理开始！无业务逻辑。");
	}

}
