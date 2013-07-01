package com.nantian.npbs.business.gateway;

import org.apache.camel.Exchange;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.gateway.camel.processor.RequestProcessor;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * @author TsaiYee
 *
 */
@Component(value="webRequestProcessor")
public class WEBRequestProcessor extends RequestProcessor {

	/* (non-Javadoc)
	 * @see com.nantian.npbs.gateway.camel.processor.RequestProcessor#getChanelType()
	 */
	@Override
	public CHANEL_TYPE getChanelType() {
		return CHANEL_TYPE.WEB;
	}

	@Override
	@Profiled(tag = "webRequestProcessor")
	public void process(final Exchange exchange) throws Exception {
			super.process(exchange);
			ControlMessage cm = getControlMessage(exchange);
			BusinessMessage bm = getBusinessMessage(exchange);
			String resultCode = cm.getResultCode();
			if (!GlobalConst.RESULTCODE_SUCCESS.equals(resultCode)) { 
				String sedaService = SedaRouteUtils.getChanelBusinessRoute(exchange,SEDA_TYPE.SERVICEANSWER);
				SedaUtils.send2Seda(exchange, sedaService, getControlMessage(exchange).isSynchronous());
			}
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
