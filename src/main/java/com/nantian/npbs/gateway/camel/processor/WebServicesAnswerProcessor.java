package com.nantian.npbs.gateway.camel.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.answer.WebAnswerBusinessService;
import com.nantian.npbs.business.service.request.WebRequestBusinessService;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.core.service.WebAnswerBusinessFactory;
import com.nantian.npbs.core.service.WebRequestBusinessFactory;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Component(value = "webServicesAnswerProcessor")
public class WebServicesAnswerProcessor extends BaseProcessor implements
		Processor {
	private static Logger logger = LoggerFactory
			.getLogger(WebServicesAnswerProcessor.class);

	@Override
	@Profiled(tag = "webServicesAnswerProcessor")
	public void process(Exchange exchange) throws Exception {

		logger.info("start webServicesAnswerProcessor process ....");

		ModelSvcReq modelSvcReq = getModelSvcReq(exchange);
		ModelSvcAns modelSvcAns = getModelSvcAns(exchange);
		WebAnswerBusinessService service = WebAnswerBusinessFactory
				.create(modelSvcReq.getBusi_code());
		service.execute(modelSvcReq, modelSvcAns);

		
		modelSvcAns.setStrTest(modelSvcReq.getWeb_date()+""+modelSvcReq.getWeb_serial());
		exchange.getOut().setBody(modelSvcAns);
		logger.info("end webServicesAnswerProcessor process ....响应码[{}],响应信息[{}]",modelSvcAns.getStatus(), modelSvcAns.getMessage());
		return;
	}

	private ModelSvcReq getModelSvcReq(Exchange exchange) {
		return (ModelSvcReq) PacketUtils.getMessageMap(exchange).get(
				DATA_TYPE.WEBREQ);

	}

	private ModelSvcAns getModelSvcAns(Exchange exchange) {
		return (ModelSvcAns) PacketUtils.getMessageMap(exchange).get(
				DATA_TYPE.WEBANS);

	}
}
