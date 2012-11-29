package com.nantian.npbs.test.performance.pos;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.test.performance.Pos8885SocketClient;
import com.nantian.npbs.test.performance.Pos8886SocketClient;
import com.nantian.npbs.test.performance.Pos8887SocketClient;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-10-1 上午12:57:34
 * 
 */

public class ClientRouteBuilder extends SpringRouteBuilder {
	private static final Logger logger = LoggerFactory
			.getLogger(ClientRouteBuilder.class);

	public void configure() {
		from("seda:testO?waitForTaskToComplete=Never&concurrentConsumers=50")
				.process(new Processor() {
					public void process(Exchange e) {

						try {
							PosDuplexSocketClient.sendMessageToServer(null, false);
						} catch (Exception ex) {
							logger.error("error", ex);
						}

					}
				});
		from("seda:testP?waitForTaskToComplete=Never&concurrentConsumers=50")
				.process(new Processor() {
					public void process(Exchange e) {

						try {
							Pos8885SocketClient.sendMessageToServer();
						} catch (Exception ex) {
							logger.error("error", ex);
						}

					}
				});
		from("seda:testQ?waitForTaskToComplete=Never&concurrentConsumers=50")
				.process(new Processor() {
					public void process(Exchange e) {

						try {
							Pos8886SocketClient.sendMessageToServer();
						} catch (Exception ex) {
							logger.error("error", ex);
						}

					}
				});
		from("seda:testR?waitForTaskToComplete=Never&concurrentConsumers=50")
				.process(new Processor() {
					public void process(Exchange e) {

						try {
							Pos8887SocketClient.sendMessageToServer();
						} catch (Exception ex) {
							logger.error("error", ex);
						}

					}
				});
	}

}
