package com.nantian.npbs.gateway.camel.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.SynchronizationAdapter;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.ProcessManager;
import com.nantian.npbs.core.service.AnswerBusinessFactory;
import com.nantian.npbs.core.service.IAnswerBusinessService;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

@Component(value="answerProcessor")
public class AnswerProcessor extends BaseProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(AnswerProcessor.class);

	@Override
	@Profiled(tag = "answerProcessor")
	public void process(Exchange exchange) throws Exception {

		logger.info("start answer process ....");
		logger.info("resultCode:[{}], resultMsg:{}" ,getControlMessage(exchange).getResultCode() 
				,getControlMessage(exchange).getResultMsg());
		
		ControlMessage cm = getControlMessage(exchange);
		BusinessMessage bm = getBusinessMessage(exchange);
		
		try{
			//business service
			//Thread.sleep(60000);
			IAnswerBusinessService answerService = AnswerBusinessFactory.create(bm);
			answerService.execute(cm, bm);
			
			 
			logger.info("answer Service finished! resultCode:[{}], resultMsg:{}" 
					,cm.getResultCode() ,bm.getResponseMsg());
		} catch (Exception e) {
			logger.error("answer service exception", e);
			//add by fengyafang 201302-01
			 
			processBusinessError(exchange, cm.isSynchronous());
			return;
		}
		 
		//释放申请的进程资源 在所有交易完成后进行释放
	 	if (cm.isLockProcess()) {
	 		ProcessManager.getProcessManager().unlockProcess(cm, bm);
	 		cm.setLockProcess(false);
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
			logger.info("[AnswerProcessor--------------------------------------交易结束--------------------------------------]");
			return;
		}
		
		
		
		//asynchronization response to answer seda queue 
		String sedaAnswer = SedaRouteUtils.getAsyncAnswerRoute();
	 	if(cm.getChanelType().equals(CHANEL_TYPE.WEB)){
			sedaAnswer=sedaAnswer+"WEB";
		} 
		SedaUtils.send2Seda(exchange, sedaAnswer, cm.isSynchronous());
		logger.info("is asynchronous, send to {}OK!" , sedaAnswer);
		logger.info("[AnswerProcessor--------------------------------------交易结束--------------------------------------]");
		
	}
	
	public void processResponse(Exchange exchange) throws PacketOperationException {
		Map<DATA_TYPE, Object> message = getControlMessage(exchange).getTermialPacketHelper()
		.packBuffer(PacketUtils.getMessageMap(exchange));
		PacketUtils.packAnswerPacket(exchange, message);
	}
}
