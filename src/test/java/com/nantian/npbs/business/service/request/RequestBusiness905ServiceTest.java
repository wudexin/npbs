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

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RequestBusiness905ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness905ServiceTest.class);

	@Resource
	private RequestBusiness905Service requestBusiness905Service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDealBusiness() {
		
		logger.info("Junit菜单更新测试开始：");
		
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		requestBusiness905Service.dealBusiness(cm, bm);
		
		logger.info("Junit菜单更新测试结束！");
	}

}
