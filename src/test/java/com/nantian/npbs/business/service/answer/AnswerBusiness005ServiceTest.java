/**
 * 
 */
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

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class AnswerBusiness005ServiceTest {
	
	private static Logger logger = LoggerFactory
	.getLogger(AnswerBusiness005ServiceTest.class);
	
	@Resource
	private AnswerBusiness005Service answerBusiness005Service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.nantian.npbs.business.service.answer.AnswerBusiness005Service#dealBusiness(com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)}.
	 */
	@Test
	public void testDealBusiness() {
		logger.info("test AnswerBusiness005Service begin:");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		bm.setTranDate("20110830");
		bm.setPbSeqno("20110830293423");
		bm.setShopCode("05001022"); // 商户
		answerBusiness005Service.dealBusiness(cm, bm);
		logger.info("备付金帐号：{}",bm.getPrePayAccno());
		logger.info("备付金名称：{}",bm.getPrePayName());
		logger.info("余额：{}",bm.getPreBalance());
		logger.info("剩余信用额度：{}",bm.getPrePaySurCreamt());
		assertEquals("0500102201", bm.getPrePayAccno());
		assertEquals("薛峰", bm.getPrePayName());
		assertNotNull(bm.getPreBalance());
		assertNotNull(bm.getPrePaySurCreamt());
	}

}
