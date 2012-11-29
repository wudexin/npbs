/**
 * 
 */
package com.nantian.npbs.gateway.camel.routebuilder;

import javax.annotation.Resource;
import org.apache.camel.Endpoint;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.SpringRouteBuilder;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.tibco.tibjms.TibjmsConnectionFactory;

/**
 * @author TsaiYee
 * 
 */
public class WebServicesRequestRouteBuilder extends SpringRouteBuilder {

	static {
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
	}

	private static Logger logger = LoggerFactory
			.getLogger(WebServicesRequestRouteBuilder.class);

	@Resource
	Processor webServicesRequestProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
	 
		String webSedaRequestQueue = "seda:" + SEDA_TYPE.REQUEST+"WEBSERVICES";
		String webSedaRequestEndpoint = webSedaRequestQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS ;
		logger.info("webSedaRequest=[{}]", webSedaRequestEndpoint);
		
		from(webSedaRequestQueue).process(webServicesRequestProcessor); 
		logger.info("启动 WebServicesRouteBuilder 完成!");
	}

}
