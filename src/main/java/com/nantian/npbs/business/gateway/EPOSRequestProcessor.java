/**
 * 
 */
package com.nantian.npbs.business.gateway;

import org.apache.camel.Exchange;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.gateway.camel.processor.RequestProcessor;

/**
 * @author TsaiYee
 *
 */
@Component(value="eposRequestProcessor")
public class EPOSRequestProcessor extends RequestProcessor {

	/* (non-Javadoc)
	 * @see com.nantian.npbs.gateway.camel.processor.RequestProcessor#getChanelType()
	 */
	@Override
	public CHANEL_TYPE getChanelType() {
		return CHANEL_TYPE.EPOS;
	}

	@Override
	@Profiled(tag = "eposRequestProcessor")
	public void process(final Exchange exchange) throws Exception {
			super.process(exchange);
	}
	
	/* (non-Javadoc)
	 * @see com.nantian.npbs.gateway.camel.processor.RequestProcessor#getOrigPacket(org.apache.camel.Exchange)
	 */
	@Override
	public Object getOrigPacket(Exchange exchange) {
		//对于FIXSTRING报文，netty使用string-decoder进行解码，解码后的类型为String
		String  buffer = (String) exchange.getIn().getBody();
		return buffer.toString();
	}

	@Override
	public boolean isSynchronous() {
		return true;
	}

}
