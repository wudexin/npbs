/**
 * 
 */
package com.nantian.npbs.gateway.camel.routebuilder;

import javax.annotation.Resource;

import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;

/**
 * @author TsaiYee
 * 
 */
public class ELEBUSIRequestRouteBuilder extends SpringRouteBuilder {

	private static Logger logger = LoggerFactory
			.getLogger(ELEBUSIRequestRouteBuilder.class);

	@Resource
	Processor elebusiRequestProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		final String encoding = System.getProperty("file.encoding");
		logger.info("系统字符集：{} ", encoding);

		logger.info("开始启动 ELEBUSIRequestRouteBuilder....");

		String elebusiRequestQueue = "seda:" + SEDA_TYPE.REQUEST
				+ CHANEL_TYPE.ELEBUSIREQUEST;
		String elebusiRequestSeda = elebusiRequestQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS;
		logger.info("elebusiRequestSeda= {}", elebusiRequestSeda);
		from(elebusiRequestSeda).routeId(elebusiRequestQueue).process(elebusiRequestProcessor);

		logger.info("启动ELEBUSI监听{}.... ",
				GlobalConst.ELEBUSIREQUEST_HOST_ADDRESS);
		from(
				GlobalConst.ELEBUSIREQUEST_HOST_ADDRESS
						+ "?corePoolSize=200&maxPoolSize=400&workerCount=256&allowDefaultCodec=false&connectTimeout=60000&decoder=#string-decoder&encoder=#string-encoder")
				.to(elebusiRequestSeda);

	}
}
