package com.nantian.npbs.gateway.camel.routebuilder;

import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-10-1 上午12:57:34
 * 
 */

public class MonitorRouteBuilder extends SpringRouteBuilder {
	private static final Logger logger = LoggerFactory
			.getLogger(MonitorRouteBuilder.class);

	public void configure() {
		logger.info("启动Monitor timer...");
		from("timer://foo?fixedRate=true&period=10000").routeId("sedaQueueLengthMonitor").to("bean:monitorBean?method=monitor");
	}

}
