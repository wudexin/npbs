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
public class AnswerBusiness007ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness007ServiceTest.class);

	@Resource
	private AnswerBusiness007Service answerBusiness007Service;

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
	 * {@link com.nantian.npbs.business.service.answer.AnswerBusiness007Service#dealBusiness(com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)}
	 * .
	 */
	@Test
	public void testDealBusiness() {
		logger.info("test AnswerBusiness007Service begin:");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
	    bm.setPbSeqno("20110906294213");
	    answerBusiness007Service.dealBusiness(cm, bm);
	    ArrayList<TbBiTrade> tbList = (ArrayList)bm.getJournalList();

	    assertNotNull(tbList);

	    for(TbBiTrade tb : tbList){
	    	logger.info(tb.getAccno());
	    	logger.info(tb.getCustomername());
	    }
	}

}
