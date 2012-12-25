package com.nantian.npbs.business.service.answer;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class AnswerBusiness019ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness019ServiceTest.class);
	@Resource
	private AnswerBusiness019Service answerBusiness019Service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDealBusiness() {
		logger.info("test AnswerReserveBalanceQueryService begin:");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		bm.setShopCode("05001022"); // 商户
		answerBusiness019Service.dealBusiness(cm, bm);
		assertNotNull(bm.getPreBalance());
		logger.info("备付金余额：{}",bm.getPreBalance());
	}

}
