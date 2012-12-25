package com.nantian.nnpbs.services.webservices.client;

import javax.xml.ws.BindingProvider;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.system;

public class Client3_TextCXFAPI {
	private final static Logger logger = LoggerFactory
			.getLogger(Client3_TextCXFAPI.class);

	public static String URL_UNIMNG = "http://10.232.6.210:7001/npbs/services/";

	// 在客户端中设置请求超时的限制（比默认值加倍）
	protected static void replaceClientTimeOut(Object svc) {
		Client client = ClientProxy.getClient(svc);
		HTTPConduit http = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		// 默认值30秒
		httpClientPolicy.setConnectionTimeout(60000);
		httpClientPolicy.setAllowChunking(false);
		// 默认值60秒
		httpClientPolicy.setReceiveTimeout(120000);
		http.setClient(httpClientPolicy);
	}

	@SuppressWarnings("unchecked")
	protected static <T> T createService(Class<T> clazz) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setAddress(URL_UNIMNG + clazz.getSimpleName());
		factory.setServiceClass(clazz);
		T svc = (T) factory.create(); 
		BindingProvider bp = (BindingProvider) svc;
		bp.getRequestContext();
		bp.getRequestContext().put(BindingProvider.SESSION_MAINTAIN_PROPERTY,
				false);

		return svc;
	}

}
