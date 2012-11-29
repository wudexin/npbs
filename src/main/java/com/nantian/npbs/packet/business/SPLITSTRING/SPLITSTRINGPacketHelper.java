/**
 * 
 */
package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
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
public class SPLITSTRINGPacketHelper implements IPacket {

	private static Logger logger = LoggerFactory
			.getLogger(SPLITSTRINGPacketHelper.class);

	protected Map<String, Object> fieldValues = new LinkedHashMap<String, Object>();
	protected FieldsConfig fieldsConfig = SPLITSTRINGFieldsConfig.getInstance();
	private int offset = 0;

	@Resource
	private SPLITSTRINGPacketHeaderHelper headerHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.packet.IPacket#packBuffer(java.util.Map)
	 */
	@Override
	@Profiled
	public Map<DATA_TYPE, Object> packBuffer(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {

		logger.info("开始进行SPLITSTRING打包....");
		offset = 0;

		ControlMessage cm = PacketUtils.getControlMessage(message);
		BusinessMessage bm = PacketUtils.getBusinessMessage(message);
		String tranCode = cm.getTranCode();

		// pack body
		IPacketSPLITSTRING packetHelper;
		String body = null;
		try {
			fieldValues.clear();
			// packetHelper = (IPacketSPLITSTRING) Class.forName(
			// getPacketClassName(tranCode)).newInstance();

			packetHelper = (IPacketSPLITSTRING) SpringContextHolder
					.getBean(getPacketBeanName(cm.getTranCode()));

			packetHelper.pack(fieldValues, cm, bm);
			body = packSPLITSTRING(packetHelper);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}
		logger.info("交易{}打包包体完成." , tranCode);
		// pack header
		String header = headerHelper.pack(cm, bm);
		logger.info("交易{}打包包头完成." ,tranCode);
		String packet = header + body;

		PacketUtils.setOrigAnsPacket(message, packet);

		logger.info("交易{}SPLITSTRING报文打包完成...." ,tranCode);
		logger.info("报文:[{}]" ,packet );

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.packet.IPacket#unpackObject(java.util.Map)
	 */
	@Override
	@Profiled(tag = "unpackObject-SPLITSTRING")
	public Map<DATA_TYPE, Object> unpackObject(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {
		logger.info("开始进行SPLITSTRING解包....");
		offset = 0;

		ControlMessage cm = PacketUtils.getControlMessage(message);
		BusinessMessage bm = PacketUtils.getBusinessMessage(message);
		bm.setSystemChanelCode(GlobalConst.eleChanelCode);

		String buffer = PacketUtils.getOrigReqPacket(message).toString();

		logger.info("需要解包的报文内容:[{}]" ,buffer );

		// pack header
		// SPLITSTRINGPacketHeaderHelper headerHelper = new
		// SPLITSTRINGPacketHeaderHelper();

		String[] headerFields = headerHelper.hasFields();
		fieldValues.clear();
		unpackSPLITSTRING(headerFields, buffer, headerHelper);
		headerHelper.unpack(fieldValues, cm, bm);
		String tranCode = ((SPLITSTRINGMessageHead) bm.getSplitsHeadData()).getTranCode();
		cm.setTranCode(tranCode);
		bm.setTranCode(tranCode);

		logger.info("交易{}解包包头完成." ,tranCode);

		if (buffer.indexOf("|", offset) < 0) {
			logger.info("无报文体！");
			return message;
		}

		// pack body
		IPacketSPLITSTRING packetHelper;
		try {
			// packetHelper = (IPacketSPLITSTRING) Class.forName(
			// getPacketClassName(tranCode)).newInstance();

			packetHelper = (IPacketSPLITSTRING) SpringContextHolder
					.getBean(getPacketBeanName(cm.getTranCode()));

			String[] fields = packetHelper.hasFields();
			fieldValues.clear();
			unpackSPLITSTRING(fields, buffer, packetHelper);
			packetHelper.unpack(fieldValues, cm, bm);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}
		logger.info("交易{}解包包体完成.",tranCode);

		logger.info("交易{}SPLITSTRING报文解包完成....",tranCode);

		return message;
	}

	private String packSPLITSTRING(IPacketSPLITSTRING packetHelper) {
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
										+ value
												.getBytes(DynamicConst.PACKETCHARSET).length
										+ "]");
					// length
					try{
						FieldUtils.setAsciiField(buffer, value, maxAsciiLength);
					}catch(Exception e){
						logger.info("packSPLITSTRING field: {} error!", fieldName, e);
					}

					FieldUtils.setFixedLengthAsciiFieldSpliter(buffer,
							SPLITSTRINGFieldsConfig.FIELD_SPLITER);
					logger.info("packSPLITSTRING field:{}, value: [{}]",fieldName
							,value);
				}

				// 处理定长字段
				if (lengthType.equals(LengthType.FIXED)) {
					//处理循环字段添加到SPLITSTRINGFieldsConfig
					if (fieldsConfig.getFieldBcdLength(fieldName) == 1
							&& Integer.parseInt(value) > 0) {
					packetHelper.addFields(null, Integer.parseInt(value),
							(SPLITSTRINGFieldsConfig) fieldsConfig);
					}
					FieldUtils.setFixedLengthAsciiField(fieldName, buffer,
							length, value, fieldsConfig);
					FieldUtils.setFixedLengthAsciiFieldSpliter(buffer,
							SPLITSTRINGFieldsConfig.FIELD_SPLITER);
					logger.info("packSPLITSTRING field:{}, value: [{}]" ,fieldName
							,value);
				}

			} catch (Exception e) {
				logger.info("packSPLITSTRING field: {} error!", fieldName, e);
			}
		}

		return buffer.toString();
	}

	//
	// public static void main(String[] args) throws Exception {
	//
	// // String buffer
	// //
	// ="000000||20111031|130048525|00001|史玉海|胜利南路26号|440.10|0000";//,DynamicConst.PACKETCHARSET);
	// // // byte[] =
	// // String value = FieldUtils.getAsciiField(buffer, 33, 7);
	// //
	// // System.out.println(value);
	//
	// // String buffer = "000000||20110825|0|00001|";
	// // String buffer =
	// // "000000||20110825|130000060|00011313155_05300016_ZG01|1|";
	// String buffer =
	// "000000||20111031|130048525|00001|史玉海|胜利南路26号|440.10|000220110603|990|1100|110|297.00|0|20110703|1100|1243|53|143.10|0|";
	// logger.info("需要解包的报文内容:[" + buffer + "]");
	// SPLITSTRINGPacketHelper pack = new SPLITSTRINGPacketHelper();
	// ControlMessage cm = new ControlMessage();
	// BusinessMessage bm = new BusinessMessage();
	//
	// // pack header
	// SPLITSTRINGPacketHeaderHelper headerHelper = new
	// SPLITSTRINGPacketHeaderHelper();
	// String[] headerFields = headerHelper.hasFields();
	// pack.fieldValues.clear();
	// pack.unpackSPLITSTRING(headerFields, buffer, headerHelper);
	// headerHelper.unpack(pack.fieldValues, cm, bm);
	//
	// IPacketSPLITSTRING packetHelper;
	// packetHelper = new SPLITSTRINGPacket006001Helper();
	// String[] fields = packetHelper.hasFields();
	// pack.fieldValues.clear();
	// pack.unpackSPLITSTRING(fields, buffer, packetHelper);
	// packetHelper.unpack(pack.fieldValues, cm, bm);
	//
	// logger.info("交易解包包头完成.");
	// }

	private void unpackSPLITSTRING(String[] fields, String buffer,
			IPacketSPLITSTRING packtHelper) {
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
					value = getEJBedoFixedLengthAsciiField(fieldName, buffer,
							offset, length, fieldsConfig);
					fieldValues.put(fieldName, value);
					offset += length;
					if (fieldsConfig.getFieldBcdLength(fieldName) == 1
							&& Integer.parseInt(value) > 0) {
						fields = packtHelper.addFields(fields, Integer
								.parseInt(value),
								(SPLITSTRINGFieldsConfig) fieldsConfig);
					}
					break;
				case VARIABLE:
					int endIndex = buffer.indexOf('|', offset);
					length = endIndex - offset;
					value = getEJBedoFixedLengthAsciiField(fieldName, buffer,
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

	private String getEJBedoFixedLengthAsciiField(String fieldName,
			String buffer, int offset, int length, FieldsConfig fieldsConfig)
			throws Exception {
		String value = null;
		value = buffer.substring(offset, offset + length);
		logger.info("field[{}], offset start[{}]offset end[{}][{}]" ,new Object[] {fieldName ,(offset)
				,(offset + length) ,fieldsConfig.getDescription(fieldName)});

		logger.info("field[{}]fixedASCIILength[{}][{}]:[{}]" 
				,new Object[]{ fieldName ,length , fieldsConfig.getDescription(fieldName) , value });
		if (value == null) {
			throw new Exception("取值错误!");
		}
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

	private static String getPacketBeanName(String tranCode) {

		// String BASE_PACKAGE =
		// "com.nantian.npbs.packet.business.SPLITSTRING.";

		return "SPLITSTRINGPacket" + tranCode + "Helper";
	}

}
