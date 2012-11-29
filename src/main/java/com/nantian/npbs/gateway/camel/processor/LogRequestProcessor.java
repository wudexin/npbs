/**
 * 
 */
package com.nantian.npbs.gateway.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.ConvertUtils;

/**
 * @author TsaiYee
 *
 */
@Component(value="logRequestProcessor")
public class LogRequestProcessor implements Processor {
	
	private static Logger logger = LoggerFactory.getLogger(LogRequestProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Object body = exchange.getIn().getBody();
		logger.info(exchange.getIn().getHeaders().toString());
		if (body.getClass() == byte.class)
			logger.info("Received body: "
					+ new String((byte[]) body));
		else if (body.getClass() == BigEndianHeapChannelBuffer.class)
			logger.info("Received body: "
					+ ConvertUtils.bytes2HexStr(((BigEndianHeapChannelBuffer) body).array()));
		else
			logger.info("Received body: " + body.toString());
	}

}
