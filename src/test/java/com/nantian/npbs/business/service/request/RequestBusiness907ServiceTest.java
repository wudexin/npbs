package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

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
import com.nantian.npbs.packet.internal.PacketHeader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RequestBusiness907ServiceTest {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness907ServiceTest.class);
	
	// Company DAO
	@Resource
	private CompanyDao companyDao;

	@Resource
	private RequestBusiness907Service requestBusiness907Service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDealBusiness() {
		logger.info("Junit应用程序测试开始：");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		
		//55域(首次下载)
		bm.setCustomData("0000");
		//907Service会根据商户表文件路径查询程序
		bm.setShop(companyDao.get("05000002"));
		
		//bm.getShop().setFilepath("D:/20111027091042");
		PacketHeader ph = new PacketHeader();
		cm.setPacketHeader(ph);
		cm.getPacketHeader().setTerminalVersion("000000");
		requestBusiness907Service.dealBusiness(cm, bm);
		
		logger.info("Junit应用程序测试结束！");
	}

}
