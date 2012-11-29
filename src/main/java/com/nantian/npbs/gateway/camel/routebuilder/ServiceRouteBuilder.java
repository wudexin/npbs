/**
 * 
 */
package com.nantian.npbs.gateway.camel.routebuilder;

import javax.annotation.Resource;

import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;

/**
 * @author TsaiYee
 * 
 */
public class ServiceRouteBuilder extends SpringRouteBuilder {

	@Resource
	Processor serviceProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		// 建立独立service队列
		for (String tranCode : DynamicConst.SERVICESEDALIST) {
			String sedaQueue = SedaRouteUtils.getServiceBusinessRoute(tranCode,
					SEDA_TYPE.SERVICEREQUEST);
			String sedaEndpoint = sedaQueue
					+ "?size="
					+ GlobalConst.CHANNEL_QUEUE_LENGTH
					+ "&concurrentConsumers="
					+ GlobalConst.CHANNEL_QUEUE_CONCURRENT_CONSUMERS;

			from(sedaEndpoint).routeId(sedaQueue).process(serviceProcessor);
		}

		// 建立默认service seda route
		String sedaQueue = SedaRouteUtils
				.getServiceDefaultBusinessRoute(SEDA_TYPE.SERVICEREQUEST);
		String sedaEndpoint = sedaQueue
				+ "?size="
				+ GlobalConst.SERVICE_QUEUE_LENGTH
				+ "&concurrentConsumers="
				+ GlobalConst.SERVICE_QUEUE_CONCURRENT_CONSUMERS;

		from(sedaEndpoint).routeId(sedaQueue).process(serviceProcessor);

	}

}
