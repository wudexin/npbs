package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.business.dao.CompanyDao;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RequestBusiness011ServiceTest {
	private Logger logger = LoggerFactory
	.getLogger(RequestBusiness011ServiceTest.class);
	
	ControlMessage cm = new ControlMessage();
	BusinessMessage bm = new BusinessMessage();
	
	@Resource
	private RequestBusiness011Service requestBusiness011Service;
	
	@Resource
	private CompanyDao companyDao;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("start jnnit!");
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("end junit!");
		
	}
	
	@Test 
	public void testDealBusiness() {
		
		bm.setShopCode("07500001");
		bm.setTranCode("011");
		cm.setResultCode("000000");		
		
		
		requestBusiness011Service.execute(cm, bm);
		logger.info("业务处理完毕!");
		assertEquals("所有商户消息。。。。",bm.getCustomData());
	}
	
	
	

}
