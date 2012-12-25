package com.nantian.npbs.business.service.request;

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
public class RequestBusiness906ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness906ServiceTest.class);

	@Resource
	private RequestBusiness906Service requestBusiness906Service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDealBusiness() {
		logger.info("参数管理测试开始：");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		requestBusiness906Service.dealBusiness(cm, bm);
		assertNotNull(bm.getCustomData());
		logger.info("55位元字符串：{}" , bm.getCustomData().toString());
		logger.info("参数管理测试结束！");
	}

}
