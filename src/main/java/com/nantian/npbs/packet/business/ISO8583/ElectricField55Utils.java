/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.FieldsConfig;

/**
 * @author TsaiYee
 *
 * @param hasFields
 *  需要进行解码的字段，必须按fieldConfig中配置的顺序排列
 */
public class ElectricField55Utils extends FieldUtils {
	private static Logger logger = LoggerFactory.getLogger(ElectricField55Utils.class);
	public static Object[] unpackElectricField55(int hasFields[], String buffer) throws Exception {
		FieldsConfig fieldsConfig = ElectricField55Config.getInstance();
		byte[] bufferBytes = buffer.getBytes(DynamicConst.PACKETCHARSET);
		Object[] result = new Object[100];
		int offset = 0;
		int length = 0;
		for(int i=0; i < hasFields.length; i++) {
			//Field55's subfield is Fixed Length
			switch(fieldsConfig.getFieldVariableType(hasFields[i])) {
			case ASCII:
				length = fieldsConfig.getFieldAsciiLength(hasFields[i]);
				String tmpBuffer = FieldUtils.getFixedLengthAsciiField(hasFields[i], bufferBytes, offset, length, fieldsConfig);
				offset += length;
				result[hasFields[i]] = tmpBuffer;
				logger.info("unpack field55, subfield{}[{}] value: [{}]"
						,new Object[] {hasFields[i] ,fieldsConfig.getDescription(hasFields[i]),tmpBuffer});
				break;
			case BINARY:
				length = fieldsConfig.getFieldAsciiLength(hasFields[i]);
				byte[] tmpBufferBytes = FieldUtils.getFixedLengthBinaryField(hasFields[i], bufferBytes, offset, length, fieldsConfig);
				offset += length;
				result[hasFields[i]] = tmpBufferBytes;
				logger.info("unpack field55, subfield{}[{}] value: [{}]" 
						,new Object[] {hasFields[i],fieldsConfig.getDescription(hasFields[i])
						, ConvertUtils.bytes2HexStr(tmpBufferBytes) });
				break;
			default:
				logger.error("don't supper type:" + fieldsConfig.getFieldVariableType(hasFields[i]));
				break;
			}
		}
	
		return result;
	}
	
	public static String packElectricField55(int hasFields[], Object[] values) {
		FieldsConfig fieldsConfig = ElectricField55Config.getInstance();

		byte[] buffer = new byte[999];
		int offset = 0;
		int length = 0;
		for(int i=0; i < hasFields.length; i++) {
			try {
				//Field55's subfield is Fixed Length
				switch(fieldsConfig.getFieldVariableType(hasFields[i])) {
				case ASCII:
					length = fieldsConfig.getFieldAsciiLength(hasFields[i]);
					buffer = FieldUtils.setFixedLengthAsciiField(hasFields[i], buffer, offset, length, (String)values[hasFields[i]], fieldsConfig);
					offset += length;
					break;
				case BINARY:
					length = fieldsConfig.getFieldAsciiLength(hasFields[i]);
					buffer = FieldUtils.setFixedLengthBinaryField(hasFields[i], buffer, offset, length, (byte[])values[hasFields[i]], fieldsConfig);
					offset += length;
					break;
				default:
					logger.error("don't supper type:" + fieldsConfig.getFieldVariableType(hasFields[i]));
					break;
				}
			} catch (Exception e) {
				logger.error("packElectricField55 " + hasFields[i] +"  error!",e);
				e.printStackTrace();
				return null;
			}
		}

			byte[] result = FieldUtils.getFieldByte(buffer, 0, offset);
			try {
				return new String(result, DynamicConst.PACKETCHARSET);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
	}
}
