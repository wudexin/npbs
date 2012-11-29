/**
 * 
 */
package com.nantian.npbs.business.gateway;

import org.apache.camel.Exchange;
import org.apache.camel.component.netty.NettyConstants;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.gateway.camel.processor.RequestProcessor;

/**
 * @author TsaiYee
 * 
 */
@Component(value = "posRequestProcessor")
public class POSRequestProcessor extends RequestProcessor {

	private static Logger logger = LoggerFactory
			.getLogger(POSRequestProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.gateway.camel.processor.RequestProcessor#getChanelType()
	 */
	@Override
	public CHANEL_TYPE getChanelType() {
		return CHANEL_TYPE.POS;
	}

	@Override
	@Profiled(tag = "posRequestProcessor")
	public void process(final Exchange exchange) throws Exception {
		BigEndianHeapChannelBuffer buffer = (BigEndianHeapChannelBuffer) exchange
				.getIn().getBody();

		if (buffer.capacity() >= 1024 || buffer.capacity() <= 48) {
			logger.error("收到数据包过长/短，判断情况异常，直接返回，并关闭Socket连接！数据包长度： {}", buffer.capacity());
			processGeneralError(exchange);
			exchange.getOut().setHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, true);
			return;
		}
		super.process(exchange);
		logger.info("POS 请求处理完毕，返回客户端！");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.gateway.camel.processor.RequestProcessor#getOrigPacket
	 * (org.apache.camel.Exchange)
	 */
	@Override
	public Object getOrigPacket(Exchange exchange) {
		// 对于ISO8583报文，netty使用length-decoder进行解码，解码后的类型为BigEndianHeapChannelBuffer
		// 由processor转换为byte[]
		BigEndianHeapChannelBuffer buffer = (BigEndianHeapChannelBuffer) exchange
				.getIn().getBody();

		return buffer.array();

	}

	@Override
	public boolean isSynchronous() {
		return true;
	}

}
