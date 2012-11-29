package com.nantian.npbs.business.service.request;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.apache.commons.jexl.junit.Asserter;
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
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RequestBusiness903ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness903ServiceTest.class);
	ControlMessage cm = new ControlMessage();
	BusinessMessage bm = new BusinessMessage();

	@Resource
	private RequestBusiness903Service requestBusiness903Service;
	
	@Resource
	private CompanyDao companyDao;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		companyDao.updateComCheckStat(bm.getShop(), "0");
	}

	@Test
	public void testDealBusiness() {
		
		bm.setShopCode("05001022"); // 商户
		bm.setTranCode("903");
		cm.setResultCode("000000");
		
		TbBiCompany tbBiCompany = new TbBiCompany();
		tbBiCompany.setCheckstat("0");
		bm.setShop(tbBiCompany);
		
		requestBusiness903Service.execute(cm, bm);
		assertEquals("1",companyDao.get("05001022").getCheckstat().trim());
	}

}
