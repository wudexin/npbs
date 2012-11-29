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

import com.nantian.npbs.business.service.request.WebRequestBusinessService;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.core.service.WebRequestBusinessFactory;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
@Component(value="webServicesAnswerProcessor")
public class WebServicesAnswerProcessor extends BaseProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(WebServicesAnswerProcessor.class);

	@Override
	@Profiled(tag = "webServicesAnswerProcessor")
	public void process(Exchange exchange) throws Exception {

		logger.info("start webServicesAnswerProcessor process ....");
		logger.info(" "); 
		Map<DATA_TYPE, Object> message1 = exchange.getIn().getBody(Map.class);
		ModelSvcAns modelSvcAns = (ModelSvcAns)message1.get("modelSvcAns");
		ModelSvcReq modelSvcReq =(ModelSvcReq)message1.get("modelSvcReq");
		WebRequestBusinessService  service=WebRequestBusinessFactory.create(modelSvcReq.getBusi_code());
		service.webexecute(modelSvcReq,modelSvcAns);
		modelSvcAns.setInstatus("00");
		Map<Object, Object> m = new HashMap();
		m.put("modelSvcReq", modelSvcReq);
		m.put("modelSvcAns", modelSvcAns);
        exchange.getOut().setBody(m);
        logger.info("end webServicesAnswerProcessor process ....");
		return; 
	}
 
}
