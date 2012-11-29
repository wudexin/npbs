/**
 * 
 */
package com.nantian.npbs.packet;

import java.util.Map;

import org.apache.camel.Exchange;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;

import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;

/**
 * @author TsaiYee
 *
 */
public class PacketUtils {
	
	public static Object getOrigReqPacket(Exchange exchange) {
		return getOrigReqPacket(getMessageMap(exchange));
	}
	public static void setOrigReqPacket(Exchange exchange, Object packet) {
		setOrigReqPacket(getMessageMap(exchange), packet);
	}
	
	public static Object getOrigReqPacket(Map<DATA_TYPE, Object> message) {
		return message.get(DATA_TYPE.ORIGREQPACKET);
	}
	public static void setOrigReqPacket(Map<DATA_TYPE, Object> message, Object packet) {
		message.put(DATA_TYPE.ORIGREQPACKET, packet);
	}
	
	public static Object getServiceReqPacket(Map<DATA_TYPE, Object> message) {
		return message.get(DATA_TYPE.SERVICEREQPACET);
	}
	public static void setServiceReqPacket(Map<DATA_TYPE, Object> message, Object packet) {
		message.put(DATA_TYPE.SERVICEREQPACET, packet);
	}
	
	public static Object getServiceAnsPacket(Map<DATA_TYPE, Object> message) {
		return message.get(DATA_TYPE.SERVICEANSPACET);
	}
	public static void setServiceAnsPacket(Map<DATA_TYPE, Object> message, Object packet) {
		message.put(DATA_TYPE.SERVICEANSPACET, packet);
	}
	
	public static BusinessMessage getBusinessMessage(Exchange exchange) {
		return (BusinessMessage)getMessageMap(exchange).get(DATA_TYPE.BUSINESSOBJECT);
	}
	
	public static BusinessMessage getBusinessMessage(Map<DATA_TYPE, Object> message) {
		return (BusinessMessage)message.get(DATA_TYPE.BUSINESSOBJECT);
	}
	
	public static void setBusinessMessage(Exchange exchange, BusinessMessage businessMessage) {
		Map<DATA_TYPE, Object> message = getMessageMap(exchange);
		setBusinessMessage(message, businessMessage);
		exchange.getIn().setBody(message);
	}
	public static void setBusinessMessage(Map<DATA_TYPE, Object> message, BusinessMessage businessMessage) {
		message.put(DATA_TYPE.BUSINESSOBJECT, businessMessage);
	}
	
	public static ControlMessage getControlMessage(Exchange exchange) {
		return (ControlMessage)getMessageMap(exchange).get(DATA_TYPE.CONTROLOBJECT);
	}
	public static ControlMessage getControlMessage(Map<DATA_TYPE, Object> message) {
		return (ControlMessage)message.get(DATA_TYPE.CONTROLOBJECT);
	}
	public static void setControlMessage(Exchange exchange, ControlMessage controlMessage) {
		Map<DATA_TYPE, Object> message = getMessageMap(exchange);
		setControlMessage(message, controlMessage);
		exchange.getIn().setBody(message);
	}
	public static void setControlMessage(Map<DATA_TYPE, Object> message, ControlMessage controlMessage) {
		message.put(DATA_TYPE.CONTROLOBJECT, controlMessage);
	}
	
	public static Map<DATA_TYPE, Object> getMessageMap(Exchange exchange) {
		@SuppressWarnings("unchecked")
		Map<DATA_TYPE, Object> message  = exchange.getIn().getBody(Map.class);
		
		return message;
	}
	/**
	 * @param message
	 * @return
	 */
	public static Object getOrigAnsPacket(Map<DATA_TYPE, Object> message) {
		return message.get(DATA_TYPE.ORIGANSPACKET);
	}
	
	public static void setOrigAnsPacket(Exchange exchange, Object origAnsPacket) {
		Map<DATA_TYPE, Object> message = getMessageMap(exchange);
		setOrigAnsPacket(message, origAnsPacket);
		exchange.getIn().setBody(message);
	}
	
	public static void setOrigAnsPacket(Map<DATA_TYPE, Object> message, Object origAnsPacket) {
		message.put(DATA_TYPE.ORIGANSPACKET, origAnsPacket);
	}
	
	public static void packAnswerPacket(Exchange exchange, Map<DATA_TYPE, Object> message) {
		ControlMessage cm = PacketUtils.getControlMessage(exchange);
		if(CHANEL_TYPE.POS.equals(cm.getChanelType())){
			byte[] answerBuffer = (byte[]) PacketUtils.getOrigAnsPacket(message);
			BigEndianHeapChannelBuffer buf = new BigEndianHeapChannelBuffer(answerBuffer);
			exchange.getOut().setBody(buf);
		}
		if(CHANEL_TYPE.EPOS.equals(cm.getChanelType())){
			String answerBuffer = (String) PacketUtils.getOrigAnsPacket(message);
			exchange.getOut().setBody(answerBuffer);
		}
		if(CHANEL_TYPE.ELEBUSIREQUEST.equals(cm.getChanelType())){
			String answerBuffer = (String) PacketUtils.getOrigAnsPacket(message);
			exchange.getOut().setBody(answerBuffer);
		}
	}
	
	public static void addFieldValue(Map<String, Object> fieldValues, String fieldName, String value) {
		if(null == value) value = "";
		fieldValues.put(fieldName, value.trim());
	}
	
}