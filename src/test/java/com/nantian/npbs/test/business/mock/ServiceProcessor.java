/**
 * 
 */
package com.nantian.npbs.test.business.mock;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.SynchronizationAdapter;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.ProcessManager;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.core.service.AnswerBusinessFactory;
import com.nantian.npbs.core.service.IAnswerBusinessService;
import com.nantian.npbs.gateway.camel.processor.BaseProcessor;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.gateway.wtc.CallWTCSvc;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketFactory;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * @author TsaiYee
 * 
 */
@Component(value = "serviceProcessor")
public class ServiceProcessor extends BaseProcessor implements Processor {
	private static Logger logger = LoggerFactory
			.getLogger(ServiceProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	@Profiled(tag = "serviceProcessor")
	public void process(Exchange exchange) throws Exception {

		ControlMessage cm = PacketUtils.getControlMessage(exchange);

		// call wtc service
		try {
			// pack packet for third service
			processExchange(exchange);
			Object requestPkg = PacketUtils.getServiceReqPacket(PacketUtils
					.getMessageMap(exchange));
			logger.error("不调用WTC，直接返回");
			// 由于无论Service调用是否出错，均需要到AnswerProcessor中进行处理，因此不做错误处理，只赋值响应码
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("调用Service:" + cm.getTranCode() + "失败!");
		} catch (java.lang.NoClassDefFoundError r) { // 默认为weblogic相关class未找到
			Thread.sleep((long) (Math.random() * 20)); // 利用这个错误起到测试桩的作用，在tomcat下进行压力测试

			logger.error("调用WTC Service失败:" + r.getMessage(), r);
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("调用Service:" + cm.getTranCode() + "失败!");
		}

		ansProcess(exchange);
//		try {
//			// distribute to service answer base chanel type
//			distributeRoute(exchange);
//		} catch (Exception e) {
//			logger.error("distribute route error:" + e.getMessage(), e);
//			// 原因同上
//			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
//			cm.setResultMsg("distribute tran " + cm.getTranCode() + " fail!");
//		}

	}

	// 处理报文成渠道指定格式
	private void processExchange(Exchange exchange)
			throws PacketOperationException {
		Map<DATA_TYPE, Object> message = PacketUtils.getMessageMap(exchange);
		PacketFactory.factory(getChanelType()).packBuffer(message);
	}

	private void distributeRoute(Exchange exchange) throws Exception {
		String sedaService = SedaRouteUtils.getChanelBusinessRoute(exchange,
				SEDA_TYPE.SERVICEANSWER);

		SedaUtils.send2Seda(exchange, sedaService, getControlMessage(exchange)
				.isSynchronous());
	}

	private CHANEL_TYPE getChanelType() {
		return CHANEL_TYPE.ELEBUSISERIVCE;
	}

	
	public void ansProcess(Exchange exchange) throws Exception {

		logger.info("start answer process ....");
		logger.info("resultCode:[{}], resultMsg:{}" ,getControlMessage(exchange).getResultCode() 
				,getControlMessage(exchange).getResultMsg());
		
		ControlMessage cm = getControlMessage(exchange);
		BusinessMessage bm = getBusinessMessage(exchange);
		
		try{
			//business service
			
			IAnswerBusinessService answerService = AnswerBusinessFactory.create(bm);
			answerService.execute(cm, bm);
			
			//释放申请的进程资源
			if (cm.isLockProcess()) {
				ProcessManager.getProcessManager().unlockProcess(cm, bm);
			}
			
			logger.info("answer Service finished! resultCode:[{}], resultMsg:{}" ,cm.getResultCode() 
					,cm.getResultMsg());
		} catch (Exception e) {
			logger.error("answer service exception", e);
			processBusinessError(exchange, cm.isSynchronous());
			return;
		}

		
		//pack response packet
		processResponse(exchange);
		
		logger.info("processResponse finished!");
		
		//synchronization return
		if(cm.isSynchronous()) {
			exchange.addOnCompletion(new SynchronizationAdapter(){
				@Override
				public void onDone(Exchange response) {
					//TODO:
				}
			});
			
			logger.info("is synchronous! direct return!");
			
			return;
		}
		
		//asynchronization response to answer seda queue 
		String sedaAnswer = SedaRouteUtils.getAsyncAnswerRoute();
		SedaUtils.send2Seda(exchange, sedaAnswer, cm.isSynchronous());
		logger.info("is asynchronous, send to {} OK!",sedaAnswer);
		
	}
	
	public void processResponse(Exchange exchange) throws PacketOperationException {
		Map<DATA_TYPE, Object> message = getControlMessage(exchange).getTermialPacketHelper()
		.packBuffer(PacketUtils.getMessageMap(exchange));
		PacketUtils.packAnswerPacket(exchange, message);
	}
}
