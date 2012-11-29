/**
 * 
 */
package com.nantian.npbs.gateway.camel.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.common.utils.TimeUtils;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.core.service.RequestBusinessFactory;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketFactory;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * @author TsaiYee
 * 
 */
public abstract class RequestProcessor extends BaseProcessor implements
		Processor {

	private static Logger logger = LoggerFactory
			.getLogger(RequestProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(final Exchange exchange) throws Exception {
		logger.info("start requestProcessor[--------------------------------------交易开始--------------------------------------]");

		Map<DATA_TYPE, Object> message = new HashMap<DATA_TYPE, Object>();

		// 保存原输入报文体，用于出错处理的需要
		Message originalIn = new DefaultMessage();
		originalIn.setBody(exchange.getIn().getBody());

		// 报文处理出错的错误包均在报文处理中完成，业务处理出错的错误包在Processor中处理
		try {
			initPacket(exchange, message);
			logger.info("init packet finished!");

			// unpack packets
			processExchange(exchange, message);
			logger.info("unpack packet finished!");

		} catch (Exception e) {
			logger.error("处理报文失败", e);
			exchange.setIn(originalIn);
			processGeneralError(exchange);
			if (isSynchronous()) {
				logger.info("[RequestProcessor---------------------- 交易返回 --------------------------------------]");
				return;
			} else {
				String sedaAnswer = SedaRouteUtils.getAsyncAnswerRoute();
				logger.info("is Asynchronous, error packet send to seda: "
						+ sedaAnswer);
				SedaUtils.send2Seda(exchange, sedaAnswer, isSynchronous());
				logger.info("[RequestProcessor---------------- 向返回队列发送错误包完成 --------------------------------------]");
				return;
			}
		}

		try {
			ControlMessage cm = getControlMessage(exchange);
			BusinessMessage bm = getBusinessMessage(exchange);

			// request business service (pre)
			IRequestBusinessService requestService = RequestBusinessFactory
					.create(bm);

			requestService.execute(cm, bm);
			String resultCode = cm.getResultCode();
			logger.info("request service finished, resultCode[{}]", resultCode);

			if (!GlobalConst.RESULTCODE_SUCCESS.equals(resultCode)) {
				processBusinessError(exchange, isSynchronous());
				return;
			}
			// distribute seda route
			distributeRequestRoute(exchange);

			if ((exchange.isFailed()) || (exchange.getException() != null)) {
				printExchange(logger, exchange);
				//如果队列已满，直接返回失败
				if("IllegalStateException".equals(exchange.getException().getClass().getSimpleName()) && "Queue full".equals(exchange.getException().getMessage())){
					if(checkBusi(bm)){
						cm.setResultCode(GlobalConst.RESULTCODE_BUSY);
					}
				}
				throw new Exception(exchange.getException());
			}

			logger.info("[RequestProcessor--------------------正常，交易结束--------------------------------------]");
		} catch (Exception e) {
			logger.error("交易请求处理失败！", e);
			processBusinessError(exchange, isSynchronous());
			return;
		}

	}

	protected void distributeRequestRoute(Exchange exchange) throws Exception {
		// BusinessMessage bm = PacketUtils.getBusinessMessage(exchange);

		ControlMessage cm = getControlMessage(exchange);
		BusinessMessage bm = getBusinessMessage(exchange);

		String tranCode = bm.getTranCode();

		// 判断是否发送第三方
		String sedaService = null;
		if ("1".equals(cm.getServiceCallFlag()) == true) {
			sedaService = SedaRouteUtils.getServiceBusinessRoute(tranCode,
					SEDA_TYPE.SERVICEREQUEST);
					//TODO:在RouteBuilder中加了超时时间后测试，没有起作用；在这里加后起作用了。
			SedaUtils.send2Seda(exchange, sedaService+ "?timeout=53000", isSynchronous());
		} else {
			sedaService = SedaRouteUtils.getChanelBusinessRoute(exchange,
					SEDA_TYPE.SERVICEANSWER);

			SedaUtils.send2Seda(exchange, sedaService,
					getControlMessage(exchange).isSynchronous());
		}
	}

	protected void initPacket(Exchange exchange, Map<DATA_TYPE, Object> message) {
		Object origReqPacket = getOrigPacket(exchange);
		message.put(DATA_TYPE.ORIGREQPACKET, origReqPacket);

		ControlMessage controlMessage = new ControlMessage();
		controlMessage.setSynchronous(isSynchronous());
		controlMessage.setChanelType(getChanelType());
		// 初始化不发送第三方
		controlMessage.setServiceCallFlag("0");
		controlMessage.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		controlMessage.setTimeout(TimeUtils
				.getTimeOut(GlobalConst.TIME_OUT_INTERVAL));
		controlMessage.setTimeOutInterval(GlobalConst.TIME_OUT_INTERVAL);

		logger.info(" 超时间隔: {}  超时时间:{}", controlMessage.getTimeOutInterval(),
				controlMessage.getTimeout());
		message.put(DATA_TYPE.CONTROLOBJECT, controlMessage);

		BusinessMessage businessMessage = new BusinessMessage();
		businessMessage.setChanelType(getChanelType());
		businessMessage.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
		message.put(DATA_TYPE.BUSINESSOBJECT, businessMessage);

		exchange.getIn().setBody(message);

		// 初始化报文处理对象
	}

	private void processExchange(Exchange exchange,
			Map<DATA_TYPE, Object> message) throws PacketOperationException {
		ControlMessage cm = PacketUtils.getControlMessage(message);
		BusinessMessage bm = PacketUtils.getBusinessMessage(message);

		IPacket packetHelper = PacketFactory.factory(getChanelType());
		getControlMessage(exchange).setTermialPacketHelper(packetHelper);
		try {
			packetHelper.unpackObject(message);
		} catch (Exception e) {
			logger.error("解包失败!", e);
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg(e.getMessage());
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg(e.getMessage());
			packetHelper.packErrorBuffer(message);
			PacketUtils.packAnswerPacket(exchange, message);
			throw new PacketOperationException(e);
		}
	}

	public abstract CHANEL_TYPE getChanelType();

	public abstract Object getOrigPacket(Exchange exchange);

	public abstract boolean isSynchronous();

	public static void printExchange(Logger LOG, Exchange exchange) {
		if ((exchange.isFailed()) || (exchange.getException() != null)) {
			LOG.warn("exchange isfailed: {}, Exception is {}",
					exchange.isFailed(), exchange.getException());
		}

		LOG.info("header: " + exchange.getIn().getHeaders());

		if (exchange.getIn().getBody().getClass() == byte.class)
			LOG.info("in body: "
					+ new String((byte[]) exchange.getIn().getBody()));
		else if (exchange.getIn().getBody().getClass() == BigEndianHeapChannelBuffer.class) {
			BigEndianHeapChannelBuffer buffer = (BigEndianHeapChannelBuffer) exchange
					.getIn().getBody();

			LOG.info("in body: " + new String(buffer.array()));
		} else
			LOG.info("in body: " + exchange.getIn().getBody().toString());

		if (exchange.getOut() != null)
			LOG.info("out: " + exchange.getOut());
	}
	
	/**
	 * 检查是否进行队列控制，需要直接返回错误信息
	 * 
	 * @param bm
	 * @return
	 */
	private boolean checkBusi(BusinessMessage bm){
		boolean retFlag = false;
		
		for (String businessType : DynamicConst.SERVICESEDALIST) {
			if(businessType.equals(bm.getBusinessType())){
				retFlag = true;
				break;
			}
		}
		return retFlag;
	}

}
