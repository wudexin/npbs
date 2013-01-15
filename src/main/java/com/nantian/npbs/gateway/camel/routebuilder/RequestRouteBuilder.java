/**
 * 
 */
package com.nantian.npbs.gateway.camel.routebuilder;

import javax.annotation.Resource;

import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;

/**
 * @author TsaiYee
 * 
 */
public class RequestRouteBuilder extends SpringRouteBuilder {

	static {
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
	}

	private static Logger logger = LoggerFactory
			.getLogger(RequestRouteBuilder.class);

	@Resource
	Processor posRequestProcessor;

	@Resource
	Processor eposRequestProcessor;
	@Resource
	Processor webRequestProcessor;

	@Resource
	Processor logRequestProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		final String encoding = System.getProperty("file.encoding");
		logger.info("系统字符集：{} ", encoding);

		logger.info("开始启动 RequestRouteBuilder....");

		String posSedaRequestQueue = "seda:" + SEDA_TYPE.REQUEST
				+ CHANEL_TYPE.POS;
		String posSedaRequestEndpoint = posSedaRequestQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS
				+ "&timeout=55000";
		logger.info("posSedaRequest=[{}]", posSedaRequestEndpoint);

		String posNettyConfig = "asyncDuplex=true&corePoolSize=25&workerCount=32&reuseAddress=false&allowDefaultCodec=false&connectTimeout=60000&disconnectOnNoReply=false&noReplyLogLevel=ERROR&decoder=#length-decoder&encoder=#length-encoder";

		String eposSedaRequestQueue = "seda:" + SEDA_TYPE.REQUEST
				+ CHANEL_TYPE.EPOS;
		String eposSedaRequestEndpoint = eposSedaRequestQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS
				+ "&timeout=55000";
		logger.info("eposSedaRequest=[{}]", eposSedaRequestEndpoint);

		String eposNettyConfig = "corePoolSize=50&workerCount=128&reuseAddress=false&allowDefaultCodec=false&connectTimeout=60000&disconnectOnNoReply=false&noReplyLogLevel=ERROR&decoder=#string-decoder&encoder=#string-encoder";

		String posPort = null;
		for (int i = 0; i < GlobalConst.POS_HOST_REQ_ADDRESS.length; i++) {
			posPort = GlobalConst.NETTY_TCP
					+ GlobalConst.POS_HOST_REQ_ADDRESS[i];
			logger.info("启动POS监听 [{}] ....", posPort);
			from(posPort + "?" + posNettyConfig).to(posSedaRequestEndpoint);
		}
		from(posSedaRequestEndpoint).routeId(posSedaRequestQueue).process(
				posRequestProcessor);

		logger.info("启动EPOS监听 [{}] ....", GlobalConst.EPOS_HOST_ADDRESS);

		from(GlobalConst.EPOS_HOST_ADDRESS + "?" + eposNettyConfig).to(
				eposSedaRequestEndpoint);
		from(eposSedaRequestEndpoint).routeId(eposSedaRequestQueue).process(
				eposRequestProcessor);
		logger.info("启动 RequestRouteBuilder 完成!");
		
		String websSedaRequestQueue = "seda:" + SEDA_TYPE.REQUEST
				+ CHANEL_TYPE.WEB;
		String webSedaRequestEndpoint = websSedaRequestQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS
				+ "&timeout=55000";
		logger.info("webbSedaRequestEndpoint=[{}]", webSedaRequestEndpoint);
		from(webSedaRequestEndpoint).process(webRequestProcessor);
	 
	}
}
