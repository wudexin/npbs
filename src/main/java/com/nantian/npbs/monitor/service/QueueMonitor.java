package com.nantian.npbs.monitor.service;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint; 
import org.apache.camel.component.seda.SedaEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-9-28 下午11:29:07
 * 
 */
public class QueueMonitor {
	private static final Logger logger = LoggerFactory
			.getLogger(QueueMonitor.class);

	@Resource
	private CamelContext camelContext;

	public void monitor() {
		String queueSizeInfo = null;
		String uri = "seda:" + SEDA_TYPE.REQUEST + CHANEL_TYPE.POS;
		SedaEndpoint sep = (SedaEndpoint) camelContext.getEndpoint(uri);
		queueSizeInfo = (uri + " queue size:" + sep.getQueue().size())+"|";

		uri = "seda:" + SEDA_TYPE.REQUEST + CHANEL_TYPE.EPOS;
		sep = (SedaEndpoint) camelContext.getEndpoint(uri);
		queueSizeInfo += (uri + " queue size:"+sep.getQueue().size())+"|";

		uri = SedaRouteUtils
				.getServiceDefaultBusinessRoute(SEDA_TYPE.SERVICEREQUEST);
		sep = (SedaEndpoint) camelContext.getEndpoint(uri);
		queueSizeInfo += (uri + " queue size:"+sep.getQueue().size())+"|";
		
	 
		
		for (CHANEL_TYPE c : CHANEL_TYPE.values()) {
			uri = SedaRouteUtils.getChanelBusinessRoute(c,
					SEDA_TYPE.SERVICEANSWER);
			sep = (SedaEndpoint) camelContext.getEndpoint(uri);
			queueSizeInfo += (uri + " queue size:"+sep.getQueue().size())+"|";
		}
		 //add by fengyafang 20120914
		uri =  "seda:" + SEDA_TYPE.REQUEST+ "WEBSERVICES";
		sep = (SedaEndpoint) camelContext.getEndpoint(uri);
		queueSizeInfo += (uri + " queue size:"+sep.getQueue().size())+"|";
		//logger.info(queueSizeInfo);
	}
}
