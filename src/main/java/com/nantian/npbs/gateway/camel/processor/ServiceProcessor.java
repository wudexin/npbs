/**
 * 
 */
package com.nantian.npbs.gateway.camel.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import weblogic.wtc.jatmi.TypedString;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.GlobalConst.SEDA_TYPE;
import com.nantian.npbs.gateway.camel.sedaroute.SedaRouteUtils;
import com.nantian.npbs.gateway.camel.sedaroute.SedaUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketFactory;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.business.TUXSTRING.TUXMessageHead;
import com.nantian.npbs.gateway.wtc.CallWTCSvc;

/**
 * @author TsaiYee
 * 
 */
@Component(value = "serviceProcessor")
public class ServiceProcessor extends BaseProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(ServiceProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	@Profiled(tag = "serviceProcessor")
	public void process(Exchange exchange) throws Exception {

		logger.info("开始进行service处理...");
		ControlMessage cm = PacketUtils.getControlMessage(exchange);
		BusinessMessage bm = PacketUtils.getBusinessMessage(exchange);

		// call wtc service
		try {
			// pack packet for third service
			processExchange(exchange);
			Object requestPkg = PacketUtils.getServiceReqPacket(PacketUtils
					.getMessageMap(exchange));
	       CallWTCSvc callSvc = new CallWTCSvc();
	       	Object responsePkg = callSvc.callSvc(requestPkg, getServiceCode(cm,bm));
			
	    	//	测试用
	    	//  TypedString responsePkg = new TypedString(TuxStringTest.backPacket(cm));
			//responsePkg = null;
			PacketUtils.setServiceAnsPacket(
					PacketUtils.getMessageMap(exchange), responsePkg);
			logger.info("收到Service应答报文:{} " , responsePkg);
			
			// unpack wtc packet
			processResponse(exchange);
			TUXMessageHead msgHeadData = (TUXMessageHead) bm.getMsgHeadData();
			cm.setServiceResultCode(msgHeadData.getRetErr());
			cm.setServiceResultMsg(msgHeadData.getRetMsg());
			bm.setMidPlatformDate(msgHeadData.getSysDate());
			bm.setSysJournalSeqno(msgHeadData.getJournalSeqno());
 
			if("000000".equals(cm.getServiceResultCode()) != true){
				logger.info("调用Service返回错误，retcode=[{}],retmsg=[{}];" , cm.getServiceResultCode() ,cm.getServiceResultMsg());
				if("000010".equals(bm.getTranCode())){//末笔交易查询
					bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
					cm.setResultCode("999999");
				}else{
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				}
				if(cm.getServiceResultMsg() == null || "".equals(cm.getServiceResultMsg()))
					bm.setResponseMsg("系统错误：电子商务平台返回错误！");
				else
					bm.setResponseMsg("电子商务返回：["+cm.getServiceResultMsg()+"]");
				cm.setResultMsg("电子商务平台返回错误:[" + cm.getServiceResultCode() + "][" +cm.getServiceResultMsg()+"]");
			}
		} catch (Exception e) {
			logger.error("调用WTC Service失败:" + e.getMessage(), e);
			// 由于无论Service调用是否出错，均需要到AnswerProcessor中进行处理，因此不做错误处理，只赋值响应码
			cm.setResultCode("999999");
			cm.setResultMsg("调用Service:" + cm.getTranCode() + "失败!");
			bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
			bm.setResponseMsg("系统错误：电子商务平台服务调用失败！");
		} catch (java.lang.NoClassDefFoundError r) { // 默认为weblogic相关class未找到
			Thread.sleep((long) (Math.random() * 20)); // 利用这个错误起到测试桩的作用，在tomcat下进行压力测试

			logger.error("调用WTC Service失败:" + r.getMessage(), r);
			cm.setResultCode("999999");
			cm.setResultMsg("调用Service:" + cm.getTranCode() + "失败!");
			bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
			bm.setResponseMsg("系统错误：电子商务平台服务调用失败！");
		}

		
		try {
			// distribute to service answer base chanel type
			distributeRoute(exchange);
		} catch (Exception e) {
			logger.error("distribute route error:" + e.getMessage(), e);
			// 原因同上
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("distribute tran " + cm.getTranCode() + " fail!");
		}

	}

	// 处理报文成渠道指定格式
	private void processExchange(Exchange exchange)
			throws PacketOperationException {
		Map<DATA_TYPE, Object> message = PacketUtils.getMessageMap(exchange);
		PacketFactory.factory(getChanelType()).packBuffer(message);
	}

	// 报文解包成公共报文
	private void processResponse(Exchange exchange)
			throws PacketOperationException {
		Map<DATA_TYPE, Object> message = PacketUtils.getMessageMap(exchange);
		PacketFactory.factory(getChanelType()).unpackObject(message);
	}

	private void distributeRoute(Exchange exchange) throws Exception {
		String sedaService = SedaRouteUtils.getChanelBusinessRoute(exchange,SEDA_TYPE.SERVICEANSWER);

		SedaUtils.send2Seda(exchange, sedaService, getControlMessage(exchange).isSynchronous());
	}

	private CHANEL_TYPE getChanelType() {
		return CHANEL_TYPE.ELEBUSISERIVCE;
	}

	/**
	 * 处理交易码
	 * @param cm
	 * @param bm
	 * @return
	 */
	private String getServiceCode(ControlMessage cm,BusinessMessage bm){
		String serviceCode = null;
		if(cm.getTranCode().equals("000012")){  //当业务类型为取消交易时
			serviceCode = cm.getCancelBusinessType() + "012";
		}else{
			serviceCode = cm.getTranCode();
		}
		return serviceCode;
	}
}
