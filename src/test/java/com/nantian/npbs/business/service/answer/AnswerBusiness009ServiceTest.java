/**
 * 
 */
package com.nantian.npbs.business.service.answer;

import static org.junit.Assert.*;

import java.util.List;

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
public class AnswerBusiness009ServiceTest {
	private static Logger logger = LoggerFactory
	.getLogger(AnswerBusiness009ServiceTest.class);
	
	@Resource
	private AnswerBusiness009Service answerBusiness009Service;

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
	 * Test method for {@link com.nantian.npbs.business.service.answer.AnswerBusiness009Service#dealBusiness(com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)}.
	 */
	@Test
	public void testDealBusiness() {
		logger.info("test AnswerBusiness009Service begin:");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		bm.setPosJournalNo("801226");
		answerBusiness009Service.dealBusiness(cm, bm);
		List<TbBiTrade> list = (List)bm.getJournalList();
		for(TbBiTrade tb : list){
			assertEquals("赵树", tb.getCustomername());
			logger.info(tb.getAccno());
			logger.info(tb.getPosSerial());
			logger.info(tb.getCustomername());
		}
		
	}

}
