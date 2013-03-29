/**
 * 
 */
package com.nantian.npbs.gateway.camel.routebuilder;

import javax.annotation.Resource;

import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;

/**
 * @author TsaiYee
 * 
 */
public class AnswerRouteBuilder extends SpringRouteBuilder {

	@Resource
	Processor answerProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		// 渠道独立Answer队列
		for (CHANEL_TYPE c : CHANEL_TYPE.values()) {
			String sedaQueue = SedaRouteUtils.getChanelBusinessRoute(c,
					SEDA_TYPE.SERVICEANSWER);
			String sedaService = sedaQueue + "?size="
					+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
					+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS;
			from(sedaService).routeId(sedaQueue).process(answerProcessor);
		}

		// 主Answer队列
		String sedaQueue = "seda:" + SEDA_TYPE.ASYNCANSWER;
		String sedaAnswer = sedaQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS;
		from(sedaAnswer).routeId(sedaQueue).to(GlobalConst.POS_HOST_ANS_ADDRESS);
		
		
		//web返回队列
	 	String webSedaAnswerQueue = "seda:" + SEDA_TYPE.ASYNCANSWER+"WEB";
		String webSedaRequestEndpoint = webSedaAnswerQueue + "?size="
				+ GlobalConst.MAIN_QUEUE_LENGTH + "&concurrentConsumers="
				+ GlobalConst.MAIN_QUEUE_CONCURRENT_CONSUMERS;
		
		from(webSedaRequestEndpoint).routeId(webSedaRequestEndpoint).
		to("bean:webcreateResponse?method=returnFromQueue"); 
		

		
	//	 from("seda:WEBSERVICEANSWER").to("bean:webcreateResponse?method=returnFromQueue");
	}

}
