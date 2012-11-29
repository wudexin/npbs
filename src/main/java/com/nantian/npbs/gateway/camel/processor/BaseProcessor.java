/**
 * 
 */
package com.nantian.npbs.gateway.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketUtils;

/**
 * @author TsaiYee
 * 
 */
public abstract class BaseProcessor {
	private static Logger logger = LoggerFactory.getLogger(BaseProcessor.class);

	protected BusinessMessage getBusinessMessage(Exchange exchange) {
		return PacketUtils.getBusinessMessage(exchange);
	}

	protected ControlMessage getControlMessage(Exchange exchange) {
		return PacketUtils.getControlMessage(exchange);
	}

	protected void processBusinessError(Exchange exchange, boolean isSync) {
		logger.info("start process error.....");

		// 程序可以处理的异常，将exception设置为空
		exchange.setException(null);

		try {
			ControlMessage cm = PacketUtils.getControlMessage(exchange);
			BusinessMessage bm = PacketUtils.getBusinessMessage(exchange);
			if (cm.getResultCode() == null)
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);

			String resultCode = cm.getResultCode();

			//answer队列超时，或者电商返回成功时，返回异常。//业务中控制
			if (resultCode.equals(GlobalConst.RESULTCODE_SUCCESS) ){//|| GlobalConst.RESULTCODE_SUCCESS.equals(cm.getServiceResultCode())) {
				// set result for error
				logger.info("resultCode is {}, set error code!", resultCode);
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				
				bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
				bm.setResponseMsg("交易超时或异常，请进行末笔查询或重发。");

				PacketUtils.setControlMessage(exchange, cm);
				logger.info("set result code ok!");
			}
			
			//特定队列已满时，直接返回失败
			if(GlobalConst.RESULTCODE_BUSY.equals(cm.getResultCode())){

				// set result for error
				logger.info("resultCode is {}, set error code!", resultCode);
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				//add by fengyafang 20120816
				//对于此种情况的需要返回99锁屏做末笔。因为有可能是末笔上来的队列满的问题。不能返回失败。
				//bm.setResponseCode(GlobalConst.TRADE_STATUS_FAILURE);
				bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
				bm.setResponseMsg("业务繁忙，请稍后重试。");

				PacketUtils.setControlMessage(exchange, cm);
				logger.info("set result code ok!");
			}
			logger.info("resultCode:[{}]", cm.getResultCode());

			// direct answer
			// pack packet
			logger.info("start pack answer packet");
			cm.getTermialPacketHelper().packBuffer(
					PacketUtils.getMessageMap(exchange));
			PacketUtils.packAnswerPacket(exchange,
					PacketUtils.getMessageMap(exchange));
			logger.info("pack answer packet OK, answer packet is: {}", exchange);
			
			if (isSync) {
				logger.info("[---------------------------------- 异常处理完成 ---------------------------------------]");
				return;
			} else {
				String sedaAnswer = SedaRouteUtils.getAsyncAnswerRoute();
				SedaUtils.send2Seda(exchange, sedaAnswer, isSync);
				logger.info("[------------------- 异常处理完成，发送至结果队列 -------------------]");
			}
		} catch (Exception e) {
			logger.error("[------------------- 异常处理出现错误！ -------------------]",
					e);
			processGeneralError(exchange);
		}
	}
	
	protected void processGeneralError(Exchange exchange) {
		String errorCode = "000000000000";
		Message out = new DefaultMessage();
		if (exchange.getIn().getBody().getClass() == BigEndianHeapChannelBuffer.class) {
			byte[] answerBuffer = errorCode.getBytes();
			BigEndianHeapChannelBuffer buf = new BigEndianHeapChannelBuffer(
					answerBuffer);
			out.setBody(buf);
		} else
			out.setBody(errorCode);

		exchange.setOut(out);
		exchange.setException(null);
	}
}
