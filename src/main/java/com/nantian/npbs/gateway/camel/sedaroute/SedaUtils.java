/**
 * 
 */
package com.nantian.npbs.gateway.camel.sedaroute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.PacketUtils;

/**
 * @author TsaiYee
 * 
 */
public class SedaUtils {
	private static Logger logger = LoggerFactory.getLogger(SedaUtils.class);
	private static Map<CamelContext, ProducerTemplate> map = new HashMap<CamelContext, ProducerTemplate>();

	public static void send2Seda(Exchange exchange, String sedaName,
			boolean isSync) throws Exception {
		CamelContext context = exchange.getContext();
		ProducerTemplate template = null;
		synchronized (map) {
			template = map.get(context);
			if (template == null) {
				template = context.createProducerTemplate();
				map.put(context, template);
			}
		}

		if (isSync) {
			Date now = new Date();
			long timeout = PacketUtils.getControlMessage(exchange).getTimeout()
					.getTime()
					- now.getTime();
			logger.info("is sync, timeout(milliseconds): {}", timeout);

			Exchange out = template.send(sedaName, exchange);

			if (new Date().getTime() - now.getTime() > timeout)
				logger.warn("调用超时！");
		} else {
			Future<Exchange> response = template.asyncSend(sedaName, exchange);
			logger.info("async send to {} OK!", sedaName);
		}
	}
}
