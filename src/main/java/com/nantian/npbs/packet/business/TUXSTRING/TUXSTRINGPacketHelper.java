/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import weblogic.wtc.jatmi.TypedString;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.FieldsConfig;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * @author TsaiYee
 * 
 */
@Scope("prototype")
@Component
public class TUXSTRINGPacketHelper implements IPacket {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacketHelper.class);

	protected Map<String, Object> fieldValues = new LinkedHashMap<String, Object>();
	protected FieldsConfig fieldsConfig = TUXSTRINGFieldsConfig.getInstance();
	private int offset = 0;

	@Resource
	private TUXSTRINGPacketHeaderHelper headerHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.packet.IPacket#packBuffer(java.util.Map)
	 */
	@Override
	@Profiled
	public Map<DATA_TYPE, Object> packBuffer(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {

		logger.info("开始进行TUXSTRING打包....");
		offset = 0;

		ControlMessage controlMessage = PacketUtils.getControlMessage(message);
		BusinessMessage businessMessage = PacketUtils
				.getBusinessMessage(message);
		String tranCode = controlMessage.getTranCode();

		// pack header
		// TUXSTRINGPacketHeaderHelper headerHelper = new
		// TUXSTRINGPacketHeaderHelper();
		String header = headerHelper.pack(controlMessage, businessMessage);
		// String header = "";
		// headerHelper.pack(fieldValues,controlMessage, businessMessage) ;
		logger.info("交易{}打包包头完成.", tranCode);

		// pack body
		IPacketTUXSTRING packetHelper;
		String body = null;
		try {
			fieldValues.clear();
			// packetHelper = (IPacketTUXSTRING) Class.forName(
			// getPacketClassName(tranCode)).newInstance();

			packetHelper = (IPacketTUXSTRING) SpringContextHolder
					.getBean(getPacketBeanName(controlMessage));

			packetHelper.pack(fieldValues, controlMessage, businessMessage);
			body = packTUXSTRING();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}
		logger.info("交易{}打包包体完成.", tranCode);

		TypedString packet = new TypedString(header + body);
	
		
		PacketUtils.setServiceReqPacket(message, packet);

		logger.info("交易{}TUXSTRING报文打包完成，报文:[{}]", tranCode, packet);

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.packet.IPacket#unpackObject(java.util.Map)
	 */
	@Override
	public Map<DATA_TYPE, Object> unpackObject(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {
		logger.info("开始进行TUXSTRING解包....");
		offset = 0;

		ControlMessage controlMessage = PacketUtils.getControlMessage(message);
		BusinessMessage businessMessage = PacketUtils
				.getBusinessMessage(message);
		String tranCode = controlMessage.getTranCode();
		String buffer = ((TypedString) PacketUtils.getServiceAnsPacket(message))
				.toString();

		logger.info("需要解包的报文内容:[{}]", buffer);

		// pack header
		// TUXSTRINGPacketHeaderHelper headerHelper = new
		// TUXSTRINGPacketHeaderHelper();
		logger.info("开始进行电子商务平台报文头解包-----------------------------------");
		String[] headerFields = headerHelper.hasFields();
		fieldValues.clear();
		unpackTUXSTRING(headerFields, buffer, headerHelper);
		headerHelper.unpack(fieldValues, controlMessage, businessMessage);
		logger.info("交易{}解包包头完成.", tranCode);

		if (buffer.indexOf("|", offset) < 0) {
			logger.info("无报文体！");
			return message;
		}

		// pack body
		IPacketTUXSTRING packetHelper;
		try {

			packetHelper = (IPacketTUXSTRING) SpringContextHolder
					.getBean(getPacketBeanName(controlMessage));
			logger.info("开始进行电子商务平台报文体解包,{}", packetHelper.getClass()
					.getSimpleName());
			String[] fields = packetHelper.hasFields();
			if (fields == null) {
				fields = new String[] { "" };
			}
			fieldValues.clear();
			unpackTUXSTRING(fields, buffer, packetHelper);
			// unpackFieldBody(fields, allFieldsValues[1]);
			packetHelper.unpack(fieldValues, controlMessage, businessMessage);

		} catch (Exception e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}
		logger.info("交易{}报文解包完成....", tranCode);

		return message;
	}

	private String packTUXSTRING() {
		StringBuffer buffer = new StringBuffer();
		int length = 0;
		String value = null;
		LengthType lengthType = null;
		for (String fieldName : fieldValues.keySet()) {
			try {
				if (fieldsConfig.getFieldVariableType(fieldName) != VariableType.ASCII) {
					logger.warn("不支持的变量类型:"
							+ fieldsConfig.getFieldVariableType(fieldName));
					break;
				}

				length = fieldsConfig.getFieldAsciiLength(fieldName);
				value = (String) fieldValues.get(fieldName);
				lengthType = fieldsConfig.getFieldLengthType(fieldName);

				// 处理变长字段
				if (lengthType.equals(LengthType.VARIABLE)) {

					// check length
					int maxAsciiLength = fieldsConfig
							.getFieldMaxAsciiLength(fieldName);
					if (value.length() > maxAsciiLength)
						throw new Exception(
								"field["
										+ fieldName
										+ "] ["
										+ value
										+ "] length is too long, expect["
										+ maxAsciiLength
										+ "]actual["
										+ value.getBytes(DynamicConst.PACKETCHARSET).length
										+ "]");
					// length
					FieldUtils.setAsciiField(buffer, value, maxAsciiLength);

					FieldUtils.setFixedLengthAsciiFieldSpliter(buffer,
							TUXSTRINGFieldsConfig.FIELD_SPLITER);
					logger.info("packTUXSTRING field:{}, value: [{}]",
							fieldName, value);
				}

				// 处理定长字段
				if (lengthType.equals(LengthType.FIXED)) {
					FieldUtils.setFixedLengthAsciiField(fieldName, buffer,
							length, value, fieldsConfig);
					FieldUtils.setFixedLengthAsciiFieldSpliter(buffer,
							TUXSTRINGFieldsConfig.FIELD_SPLITER);
					logger.info("packTUXSTRING field:{}, value: [{}]",
							fieldName, value);
				}

			} catch (Exception e) {
				logger.info("packTUXSTRING field: {} error!", fieldName, e);
			}
		}

		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {

		// String buffer
		// ="000000||20111031|130048525|00001|史玉海|胜利南路26号|440.10|0000";//,DynamicConst.PACKETCHARSET);
		// // byte[] =
		// String value = FieldUtils.getAsciiField(buffer, 33, 7);
		//
		// System.out.println(value);

		// String buffer = "000000||20110825|0|00001|";
		// String buffer =
		// "000000||20110825|130000060|00011313155_05300016_ZG01|1|";
		String buffer = "000000||20111031|130048525|00001|史玉海|胜利南路26号|440.10|000220110603|990|1100|110|297.00|0|20110703|1100|1243|53|143.10|0|";
		logger.info("需要解包的报文内容:[{}]", buffer);
		TUXSTRINGPacketHelper pack = new TUXSTRINGPacketHelper();
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();

		// pack header
		TUXSTRINGPacketHeaderHelper headerHelper = new TUXSTRINGPacketHeaderHelper();
		String[] headerFields = headerHelper.hasFields();
		pack.fieldValues.clear();
		pack.unpackTUXSTRING(headerFields, buffer, headerHelper);
		headerHelper.unpack(pack.fieldValues, cm, bm);

		IPacketTUXSTRING packetHelper;
		packetHelper = new TUXSTRINGPacket006001Helper();
		String[] fields = packetHelper.hasFields();
		pack.fieldValues.clear();
		pack.unpackTUXSTRING(fields, buffer, packetHelper);
		packetHelper.unpack(pack.fieldValues, cm, bm);

		logger.info("交易解包包头完成.");
	}

	private void unpackTUXSTRING(String[] fields, String buffer,
			IPacketTUXSTRING packtHelper) {
		int length = 0;
		String value = null;
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i];
			try {
				if (fieldsConfig.getFieldVariableType(fieldName) != VariableType.ASCII) {
					logger.warn("不支持的变量类型:"
							+ fieldsConfig.getFieldVariableType(fieldName));
					break;
				}

				switch (fieldsConfig.getFieldLengthType(fieldName)) {
				case FIXED:
					length = fieldsConfig.getFieldAsciiLength(fieldName);
					value = getTuxedoFixedLengthAsciiField(fieldName, buffer,
							offset, length, fieldsConfig);
					fieldValues.put(fieldName, value);
					offset += length;
					if (fieldsConfig.getFieldBcdLength(fieldName) == 1
							&& Integer.parseInt(value) > 0) {
						fields = packtHelper.addFields(fields,
								Integer.parseInt(value),
								(TUXSTRINGFieldsConfig) fieldsConfig);
					}
					break;
				case VARIABLE:
					int endIndex = buffer.indexOf('|', offset);
					length = endIndex - offset;
					value = getTuxedoFixedLengthAsciiField(fieldName, buffer,
							offset, length, fieldsConfig);
					fieldValues.put(fieldName, value);
					offset += length;
					offset += 1; // spliter
					break;
				}
			} catch (Exception e) {
				logger.error("unpack field " + fieldName + "error!", e);
			}
		}
	}
/**
 * 按照fieldsCongig 配置从buffer中自offset提取length个字符。
 * @param fieldName
 * @param buffer
 * @param offset
 * @param length
 * @param fieldsConfig
 * @return
 * @throws Exception
 */
	private String getTuxedoFixedLengthAsciiField(String fieldName,
			String buffer, int offset, int length, FieldsConfig fieldsConfig)
			throws Exception {
		String value = null;
		
		int needLength = offset + length;
		if(buffer.length()>= needLength) {
			value = buffer.substring(offset, offset + length);
		}else if(buffer.length()> offset && buffer.length()< needLength) {
			value = buffer.substring(offset, buffer.length()-1);
		}else {
			throw new Exception("提取数据长度不正确！");
		}		

		logger.info("field[{}]fixedASCIILength[{}][{}]:[{}]", new Object[] {
				fieldName, length, fieldsConfig.getDescription(fieldName),
				value });
	
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.packet.IPacket#packErrorBuffer(java.util.Map)
	 */
	@Override
	public Map<DATA_TYPE, Object> packErrorBuffer(Map<DATA_TYPE, Object> message) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getPacketBeanName(ControlMessage cm) {

		String serviceCode = null;
		if (cm.getTranCode().equals("000012")) { // 当业务类型为取消交易时
			// 被取消的业务类型 + 0120
			logger.info("取消交易业务类型:[{}]", cm.getCancelBusinessType());
			serviceCode = cm.getCancelBusinessType() + "012";
		} else {
			serviceCode = cm.getTranCode();
		}

		return "TUXSTRINGPacket" + serviceCode + "Helper";
	}
	
	
}
