package com.nantian.npbs.test.performance.pos;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-9-28 下午11:29:07
 * 
 */

public class PosPerfTestServer {
	private static final Logger logger = LoggerFactory
			.getLogger(PosPerfTestServer.class);

	public static CamelContext context = null;

	public static ProducerTemplate template = null;

	public static void main(String args[]) throws Exception {
		context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			public void configure() {
				from(
						"seda:testQ?waitForTaskToComplete=Never&limitConcurrentConsumers=false&concurrentConsumers=5")
						.process(new Processor() {
							public void process(Exchange e) {

								try {
									PosDuplexSocketClient.sendMessageToServer(null, false);
								} catch (Exception ex) {
									logger.error("error", ex);
								}

							}
						});
			}
		});

		try {
			template = context.createProducerTemplate();
			context.start();
			for (int i = 0; i < 100; i++) {
				template.sendBody("seda:testQ", i);
			}
			Thread.sleep(5000 * 1000);
		} finally {
			context.stop();
		}

	}

}
