package com.nantian.npbs.services.webservices.sendqueues;

import java.util.HashMap;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
import com.nantian.npbs.services.webservices.utils.SendMsgHelper;

@Scope("prototype")
@Component(value = "pkgSendModel")
public class PkgSendModel {
	private static final Logger logger = LoggerFactory
			.getLogger(PkgSendModel.class);

	public boolean sendToQueue(ModelSvcReq modelSvcReq,
			  ModelSvcAns modelSvcAns) {
		logger.info("发送队列begin");
		CamelContext camelContext = (CamelContext) SpringContextHolder.getBean("camelContext");
		ProducerTemplate producerTemplate = camelContext
				.createProducerTemplate();
		Endpoint endpoint = camelContext.getEndpoint("seda:REQUESTWEBSERVICES");
		boolean flag = true;
		try {
			if (null != endpoint) {
				Map<String, Object> headers = new HashMap<String, Object>();
				SendMsgHelper.packPkgHeader(modelSvcReq, headers);
				Map<Object, Object> m = new HashMap();
				m.put("modelSvcReq", modelSvcReq);
				m.put("modelSvcAns", modelSvcAns);
				producerTemplate.start();
				producerTemplate.sendBodyAndHeaders(endpoint, m, headers);
				producerTemplate.stop();
				logger.info("发送成功！");
				return true;
			} else {
				logger.error("队列名信息错误，无法发送至当前队列！");
				flag = false;
			}

		} catch (CamelExecutionException e) {
			flag = false;
			logger.error("发送错误队列信息失败！");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

}