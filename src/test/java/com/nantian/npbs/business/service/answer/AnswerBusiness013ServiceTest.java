/**
 * 
 */
package com.nantian.npbs.business.service.answer;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * @author Administrator
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class AnswerBusiness013ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness013ServiceTest.class);

	@Resource
	private AnswerBusiness013Service answerBusiness013Service;

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
	 * Test method for
	 * {@link com.nantian.npbs.business.service.answer.AnswerBusiness010Service#dealBusiness(com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)}
	 * .
	 */
	@Test
	public void testDealBusiness() {
		logger.info("test Business013Service begin!");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		bm.setShopCode("05001022"); // 商户
		answerBusiness013Service.dealBusiness(cm, bm);

		assertNotNull(bm.getJournalList());
		ArrayList<TbBiTrade> tblist = (ArrayList)bm.getJournalList();
		for (TbBiTrade tb : tblist) {
//			assertEquals(tb.getId().getPbSerial(), "20110902293723");
			logger.info("末笔pos流水：{}",tb.getId().getPbSerial());
			assertEquals(tb.getPosSerial(), "900904");
			assertEquals(tb.getCustomername(), "薛峰");
		}

	}

}
