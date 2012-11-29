package com.nantian.npbs.services.webservices.sendqueues;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Scope("prototype")
@Component(value = "pkgSendModel")
public class PkgSendModel {
	private static final Logger logger = LoggerFactory
			.getLogger(PkgSendModel.class);
	CamelContext camelContext = (CamelContext) SpringContextHolder
			.getBean("camelContext");
	ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
	ConsumerTemplate createConsumerTemplate = camelContext
			.createConsumerTemplate();
	Endpoint endpoint = camelContext.getEndpoint("seda:REQUESTWEBSERVICES");
	Exchange exchange = endpoint.createExchange();

	public boolean sendToQueue(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		logger.info("发送队列begin");
		boolean flag = true;
		try {
			if (null != endpoint) {
				exchange.getIn().setBody(modelSvcReq);
				producerTemplate.asyncSend(endpoint, exchange);
			} else {
				logger.error("队列名信息错误，无法发送至当前队列！");
				flag = false;
			}
		} catch (CamelExecutionException e) {
			flag = false;
			logger.error("发送错误队列信息失败！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}