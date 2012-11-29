package com.nantian.npbs.gateway.camel.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultMessage;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.request.WebRequestBusinessService;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.common.utils.TimeUtils;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.core.service.WebRequestBusinessFactory;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Component(value = "webServicesRequestProcessor")
public class WebServicesRequestProcessor extends BaseProcessor implements
		Processor {
	private static Logger logger = LoggerFactory
			.getLogger(WebServicesRequestProcessor.class);

	@Override
	@Profiled(tag = "webServicesRequestProcessor")
	public void process(Exchange exchange) throws Exception {

		logger
				.info("start webServicesRequestProcessor process[--------------------------------------交易开始--------------------------------------]");

		Map<DATA_TYPE, Object> message = new HashMap<DATA_TYPE, Object>();
		// 保存原输入报文体，用于出错处理的需要
		Message originalIn = new DefaultMessage();
		originalIn.setBody(exchange.getIn().getBody());
		logger.info("init packet begin!");
		initModelSvcReq(exchange, message);
		logger.info("init packet finished!");
		ModelSvcReq modelSvcReq = getModelSvcReq(exchange);
		ModelSvcAns modelSvcAns = getModelSvcAns(exchange);

		WebRequestBusinessService service = WebRequestBusinessFactory
				.create(modelSvcReq.getBusi_code());
		service.webexecute(modelSvcReq, modelSvcAns);

	 
		logger
				.info("end webServicesRequestProcessor process [--------------------------------------交易结束--------------------------------------]");
		distributeRoute(exchange); 
	}

	private void distributeRoute(Exchange exchange) throws Exception {
		send2Seda(exchange, "seda:" + SEDA_TYPE.ASYNCANSWER + "WEBSERVICES",
				isSynchronous());
	}

	public boolean isSynchronous() {
		return true;
	}

	private void initModelSvcReq(Exchange exchange,
			Map<DATA_TYPE, Object> message) {
		ModelSvcReq modelSvcReq =(ModelSvcReq)  exchange.getIn().getBody();
		ModelSvcAns modelSvcAns = new ModelSvcAns(); 
		modelSvcAns.setStatus("000000");
		modelSvcReq.setSeqnoFlag("0");
		modelSvcReq.setTimeout(TimeUtils
				.getTimeOut(GlobalConst.TIME_OUT_INTERVAL));
		modelSvcReq.setTimeOutInterval(GlobalConst.TIME_OUT_INTERVAL);
		message.put(DATA_TYPE.WEBREQ, modelSvcReq);
		message.put(DATA_TYPE.WEBANS, modelSvcAns);
		exchange.getIn().setBody(message);
	}

	private static ModelSvcReq getModelSvcReq(Exchange exchange) {
		return (ModelSvcReq) PacketUtils.getMessageMap(exchange).get(
				DATA_TYPE.WEBREQ);

	}

	private ModelSvcAns getModelSvcAns(Exchange exchange) {
		return (ModelSvcAns) PacketUtils.getMessageMap(exchange).get(
				DATA_TYPE.WEBANS);

	}
	public static void send2Seda(Exchange exchange, String sedaName,
			boolean isSync) throws Exception {
		CamelContext context = exchange.getContext();
		ProducerTemplate template = context.createProducerTemplate();
		if (isSync) {
			Date now = new Date();
			long timeout = getModelSvcReq(exchange).getTimeout().getTime()
					- now.getTime();
			logger.info("is sync, timeout(milliseconds): {}", timeout);

			Exchange send = template.send(sedaName, exchange);

			if (new Date().getTime() - now.getTime() > timeout)
				logger.warn("调用超时！");

		} else {
			Future<Exchange> response = template.asyncSend(sedaName, exchange);
			logger.info("async send to {} OK!", sedaName);
		}

	}
}
