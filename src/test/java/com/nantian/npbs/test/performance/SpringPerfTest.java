package com.nantian.npbs.test.performance;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-9-28 下午11:29:07
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class SpringPerfTest {
	private static final Logger logger = LoggerFactory
			.getLogger(SpringPerfTest.class);

	@Resource
	private CamelContext camelContext;

	public ProducerTemplate template = null;

	@Test
	public void run() throws Exception {
		try {
			template = camelContext.createProducerTemplate();

			for (int i = 0; i < 200; i++) {
				logger.info("sending {}", i);
				
				template.sendBody("seda:testO", i);
				template.sendBody("seda:testP", i);
				template.sendBody("seda:testQ", i);
				template.sendBody("seda:testR", i);
			}
			Thread.sleep(5000 * 1000);
		} finally {
		}
	}

}
