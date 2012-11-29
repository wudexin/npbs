/**
 * 
 */
package com.nantian.npbs.business.service.answer;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * @author Administrator
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class AnswerBusiness008ServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness008ServiceTest.class);

	@Resource
	private AnswerBusiness008Service answerBusiness008Service;

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
		logger.info("test Business008Service begin!");
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();

		bm.setShopCode("05001022"); // 商户
		bm.setQueryStartDate("20110101");
		bm.setQueryEndDate("20111231");
		answerBusiness008Service.dealBusiness(cm, bm);
		ArrayList<Object> res = bm.getJournalList();

		assertNotNull(res);

		ArrayList<Object[]> tranRes = (ArrayList) res.get(0);// 交易统计
		ArrayList<Object[]> preRes = (ArrayList) res.get(1);// 备付金统计

		assertNotNull(tranRes);
		assertNotNull(preRes);
		
		for (Object[] o : tranRes) {
			String companyCode = (String) o[0];// 商户号
			String busiCode = (String) o[1];// 业务类型
			BigDecimal pbSerialCount = (BigDecimal) o[2];// 交易笔数
			BigDecimal amountSum = (BigDecimal) o[3];// 交易总金额
			logger.info("商户号[{}]业务类型[{}]交易笔数[{}]交易总金额[{}]"
					,new Object[] {companyCode,busiCode, pbSerialCount, amountSum });
		}
		for (Object[] o : preRes) {
			String accNo = (String) o[0];// 帐户号
			BigDecimal prepayPbCount = (BigDecimal) o[1];// 备付金交易笔数
			BigDecimal prepayAmountSum = (BigDecimal) o[2];// 备付金交易总金额
			logger.info("备付金帐号[{}]备付金交易笔数[{}]备付金交易总金额[{}]" 
					,new Object[] {accNo ,prepayPbCount,prepayAmountSum});
		}
	}
}
