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
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
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

@Component(value = "webServicesProcessor")
public class WebServicesProcessor extends BaseProcessor implements Processor {
	private static Logger logger = LoggerFactory
			.getLogger(WebServicesProcessor.class);

	@Override
	@Profiled(tag = "webServicesProcessor")
	public void process(Exchange exchange) throws Exception {

		logger
				.info("start webServicesProcessor process[--------------------------------------交易开始--------------------------------------]");
		// 保存原输入报文体，用于出错处理的需要
		Message originalIn = new DefaultMessage();
		originalIn.setBody(exchange.getIn().getBody());
		Map<DATA_TYPE, Object> message = (Map<DATA_TYPE, Object>) exchange
				.getIn().getBody();
		ModelSvcAns modelSvcAns = (ModelSvcAns) message.get("modelSvcAns");
		ModelSvcReq modelSvcReq = (ModelSvcReq) message.get("modelSvcReq");
		logger.info("init packet finished!");
		if (modelSvcReq == null) {
			modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_FAILURE);
			modelSvcAns.setMessage("请求数据为空，请重新交易！");
			modelSvcAns.setInstatus(GlobalConst.TRADE_STATUS_SUCCESS);
			return;
		}

		WebRequestBusinessService service = WebRequestBusinessFactory
				.create(modelSvcReq.getBusi_code());
		service.webexecute(modelSvcReq, modelSvcAns);
		if (modelSvcAns.getStatus().equals(GlobalConst.TRADE_STATUS_SUCCESS)) {
			logger.info("webServicesProcessor  webexecute finished, resultStatus[{}] "
							+ modelSvcAns.getStatus());
		modelSvcAns.setInstatus(GlobalConst.TRADE_STATUS_SUCCESS);	
		}
		logger.info("end webServicesProcessor process [--------------------------------------交易结束--------------------------------------]");
		return;
	}
}
