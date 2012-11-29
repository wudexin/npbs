/**
 * 
 */
package com.nantian.npbs.packet.internal;

import java.io.UnsupportedEncodingException;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.internal.FieldType.AlignType;

/**
 * @author TsaiYee
 * 
 *         注意，此类函数中所有的长度(length)均指字节(byte)长度，而不是指某个字符集对应的字符串的长度
 */
@Component
public class FieldUtils {
	private static Logger logger = LoggerFactory.getLogger(FieldUtils.class);


	@Profiled
	public static byte[] getFieldByte(byte[] buffer, int offset, int length) {
		byte[] tmp = new byte[length];

		System.arraycopy(buffer, offset, tmp, 0, length);

		return tmp;
	}

	@Profiled
	public static byte[] getFieldByteOld(byte[] buffer, int offset, int length) {
		byte[] tmp = new byte[length];
		for (int i = 0; i < length; i++)
			tmp[i] = buffer[offset + i];

		return tmp;
	}

	@Profiled
	public static String getBcdField(byte[] buf, int offset, int length) {
		return ConvertUtils.bcd2Asc(getFieldByte(buf, offset, length));
	}

	@Profiled
	public static String getAsciiField(byte[] buf, int offset, int length)
			throws Exception {
		return new String(getFieldByte(buf, offset, length),
				DynamicConst.PACKETCHARSET);
	}

	@Profiled
	public static String getAsciiField(String buf, int offset, int length)
			throws Exception {
		byte[] bytes = buf.getBytes(DynamicConst.PACKETCHARSET);
		if (offset + length > bytes.length) {
			logger.error("长度溢出! length of buf: " + bytes.length + " offset: "
					+ offset + " length: " + length);
			return null;
		}
		return getAsciiField(bytes, offset, length);
	}

	

	@Profiled
	public static byte[] setFieldByte(byte[] buffer, int offset, byte[] values) {
		System.arraycopy(values, 0, buffer, offset, values.length);
		return buffer;
	}
	
	@Profiled
	private static byte[] setFieldByteOld(byte[] buffer, int offset, byte[] values) {
		for (int i = 0; i < values.length; i++) {
			buffer[offset + i] = values[i];
		}

		return buffer;
	}

	@Profiled
	public static int setBcdField(byte[] buffer, int offset, String value,
			int length) throws Exception {
		byte[] bytes = ConvertUtils.str2Bcd(value);
		if (bytes.length > length) {
			throw new Exception("set value is too long, expect[" + length
					+ "], actual[" + bytes.length + "]");
		}
		setFieldByte(buffer, offset, bytes);
		return bytes.length;
	}

	@Profiled
	public static int setAsciiField(byte[] buffer, int offset, String value,
			int length) throws Exception {
		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length > length) {
			logger.warn("实际长度 " + bytes.length + "超出规定长度 " + length + " ，自动截取!");
			bytes = getFieldByte(bytes, 0, length);
		}
		setFieldByte(buffer, offset, bytes);
		return bytes.length;
	}

	@Profiled
	public static int setAsciiField(StringBuffer buffer, String value,
			int length) throws Exception {
		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length > length) {
			throw new Exception("set value is too long, expect[" + length
					+ "], actual byte length[" + bytes.length + "]");
		}
		buffer.append(value);
		return bytes.length;
	}

	@Profiled
	public static int setBinaryField(byte[] buffer, int offset, byte[] bytes,
			int length) throws Exception {
		if (bytes.length > length) {
			logger.warn("实际长度 " + bytes.length + "超出规定长度 " + length + " ，自动截取!");
			bytes = getFieldByte(bytes, 0, length);
		}
		setFieldByte(buffer, offset, bytes);
		return length;
	}

	@Profiled
	public static String leftAddZero4FixedLengthString(String value, int length)
			throws Exception {
		char add = '0';
		return leftAddChar4FixedLengthString(value, add, length);
	}

	@Profiled
	public static String leftAddSpace4FixedLengthString(String value, int length)
			throws Exception {
		char add = 32;
		return leftAddChar4FixedLengthString(value, add, length);
	}

	public static String leftAddChar4FixedLengthString(String value, char add,
			int length) throws UnsupportedEncodingException {
		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		int actualLength = bytes.length;
		if (actualLength >= length)
			return value;
		char[] left = new char[length - actualLength];
		for (int i = 0; i < left.length; i++)
			left[i] = add;
		String result = new String(left) + value;
		return result;
	}

	@Profiled
	public static String leftAddChar4FixedLengthStringUseByte(String value,
			char add, int length) throws Exception {
		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length >= length)
			return value;
		byte[] tmpString = new byte[length];
		int actualLength = bytes.length;
		byte addByte = ConvertUtils.char2byte(add);
		for (int i = 0; i < length - actualLength; i++) {
			tmpString[i] = addByte;
		}
		for (int i = 0; i < actualLength; i++) {
			tmpString[i + length - actualLength] = bytes[i];
		}

		value = new String(tmpString, DynamicConst.PACKETCHARSET);
		return value;
	}

	@Profiled
	public static String rightAddSpace4FixedLengthString(String value,
			int length) throws Exception {
		char add = 32;
		return rightAddChar4FixedLengthString(value, length, add);
	}

	@Profiled
	public static String rightAddZero4FixedLengthString(String value, int length)
			throws Exception {
		return rightAddChar4FixedLengthString(value, length, '0');
	}

	@Profiled
	public static String rightAddChar4FixedLengthString(String value,
			int length, char add) throws Exception {
		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length >= length)
			return value;

		byte[] tmpString = new byte[length];
		int actualLength = bytes.length;
		for (int i = 0; i < actualLength; i++) {
			tmpString[i] = bytes[i];
		}

		// int aaa = length-actualLength;
		// for (int i=actualLength; i < length-actualLength; i++) {
		byte addByte = ConvertUtils.char2byte(add);
		for (int i = actualLength; i < length; i++) {
			tmpString[i] = addByte;
		}
		value = new String(tmpString, DynamicConst.PACKETCHARSET);

		return value;
	}

	@Profiled
	public static void assertFieldNull(int fieldNo, Object value) {
		if (value == null)
			logger.debug("fileds[{}] value is null!!!", fieldNo);
	}

	@Profiled
	public static String getFixedLengthBcdField(int fieldNo, byte[] buffer,
			int offset, int length, FieldsConfig fieldsConfig) throws Exception {
		int asciiLength = fieldsConfig.getFieldAsciiLength(fieldNo);
		String value = getBcdField(buffer, offset, length);

		// if length of value is too long, must split
		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length > asciiLength)
			value = getAsciiField(value, 0, asciiLength);

		logger.debug(
				"field[{}], offset start[{}]offset end[{}][{}], fixedBCDLength[{}],fixedASCIILength[{}],value[{}]",
				new Object[] { fieldNo, (offset - length), offset,
						fieldsConfig.getDescription(fieldNo), length, asciiLength, value });

		return value;
	}

	@Profiled
	public static String getFixedLengthAsciiField(int fieldNo, byte[] buffer,
			int offset, int length, FieldsConfig fieldsConfig) throws Exception {
		String value = getAsciiField(buffer, offset, length);
		logger.debug(
				"field[{}], offset start[{}], offset end[{}][{}], fixedASCIILength [{}], value[{}]",
				new Object[] { fieldNo, offset, offset + length,
						fieldsConfig.getDescription(fieldNo), length, value });

		return value;
	}

	@Profiled
	public static String getFixedLengthAsciiField(String fieldName,
			String buffer, int offset, int length, FieldsConfig fieldsConfig)
			throws Exception {
		String value = getAsciiField(buffer, offset, length);
		logger.debug(
				"field[{}], offset start[{}], offset end[{}][{}], fixedASCIILength [{}], value[{}]",
				new Object[] { fieldName, offset, offset + length,
						fieldsConfig.getDescription(fieldName), length, value });

		return value;
	}

	@Profiled
	public static byte[] getFixedLengthBinaryField(int fieldNo, byte[] buffer,
			int offset, int length, FieldsConfig fieldsConfig) throws Exception {

		byte[] bytes = getFieldByte(buffer, offset, length);

		logger.debug(
				"field[{}], offset start[{}], offset end[{}], fixedLength [{}][{}], value[{}]",
				new Object[] { fieldNo, offset - length, offset, length,
						fieldsConfig.getDescription(fieldNo), bytes });

		return bytes;
	}

	@Profiled
	public static byte[] setFixedLengthBcdField(int fieldNo, byte[] buffer,
			int offset, int bcdLength, String value, FieldsConfig fieldsConfig)
			throws Exception {
		// check length
		int asciiLength = fieldsConfig.getFieldAsciiLength(fieldNo);
		value = addFixedLengthAsciiString(fieldNo, value, asciiLength,
				fieldsConfig);

		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length != asciiLength)
			throw new Exception("field[" + fieldNo + "] [" + value
					+ "]length isn't expect fixed[" + asciiLength + "]actual["
					+ bytes.length + "]");

		logger.debug(
				"field[{}],offset [{}][{}], fixedBcdLength[{}], fixedAsciiLength [{}], value[{}]",
				new Object[] { fieldNo, offset,
						fieldsConfig.getDescription(fieldNo), bcdLength,
						asciiLength, value });

		setBcdField(buffer, offset, value, bcdLength);

		return buffer;
	}

	@Profiled
	public static byte[] setFixedLengthAsciiField(int fieldNo, byte[] buffer,
			int offset, int asciiLength, String value, FieldsConfig fieldsConfig)
			throws Exception {
		// check length
		value = addFixedLengthAsciiString(fieldNo, value, asciiLength,
				fieldsConfig);

		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length > asciiLength) {
			logger.warn("实际长度 " + bytes.length + "超出规定长度 " + asciiLength
					+ " ，自动截取!");
			bytes = getFieldByte(bytes, 0, asciiLength);
		}
		if (bytes.length != asciiLength)
			throw new Exception("field[" + fieldNo + "] [" + value
					+ "] length isn't expect fixed[" + asciiLength + "]actual["
					+ bytes.length + "]");

		int length = fieldsConfig.getFieldAsciiLength(fieldNo);

		logger.debug(
				"field[{}],offset [{}][{}], fixedAsciiLength [{}], value[{}]",
				new Object[] { fieldNo, offset,
						fieldsConfig.getDescription(fieldNo), asciiLength,
						value });

		setAsciiField(buffer, offset, value, length);
		return buffer;
	}

	@Profiled
	public static StringBuffer setFixedLengthAsciiField(int fieldNo,
			StringBuffer buffer, int asciiLength, String value,
			FieldsConfig fieldsConfig) throws Exception {
		// check length
		value = addFixedLengthAsciiString(fieldNo, value, asciiLength,
				fieldsConfig);

		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length != asciiLength)
			throw new Exception("field[" + fieldNo + "] [" + value
					+ "] length isn't expect fixed[" + asciiLength + "]actual["
					+ bytes.length + "]");

		logger.debug("field[{}][{}],fixedAsciiLength [{}], value[{}]",
				new Object[] { fieldNo, fieldsConfig.getDescription(fieldNo),
						asciiLength, value });

		buffer = setFixedLengthAsciiField(buffer, asciiLength, value);

		return buffer;
	}

	@Profiled
	public static StringBuffer setFixedLengthAsciiField(String fieldName,
			StringBuffer buffer, int asciiLength, String value,
			FieldsConfig fieldsConfig) throws Exception {
		// check length
		value = addFixedLengthAsciiString(fieldName, value, asciiLength,
				fieldsConfig);

		byte[] bytes = value.getBytes(DynamicConst.PACKETCHARSET);
		if (bytes.length != asciiLength)
			throw new Exception("field[" + fieldName + "] [" + value
					+ "] length isn't expect fixed[" + asciiLength + "]actual["
					+ bytes.length + "]");

		logger.debug(
				"field[{}][{}],fixedAsciiLength [{}], value[{}]",
				new Object[] { fieldName,
						fieldsConfig.getDescription(fieldName), asciiLength,
						value });

		buffer = setFixedLengthAsciiField(buffer, asciiLength, value);

		return buffer;
	}

	@Profiled
	public static StringBuffer setFixedLengthAsciiField(StringBuffer buffer,
			int asciiLength, String value) throws Exception {
		setAsciiField(buffer, value, asciiLength);
		return buffer;
	}

	@Profiled
	public static StringBuffer setFixedLengthAsciiFieldSpliter(
			StringBuffer buffer, String spliter) {
		return buffer.append(spliter);
	}

	@Profiled
	public static byte[] setFixedLengthBinaryField(int fieldNo, byte[] buffer,
			int offset, int length, byte[] value, FieldsConfig fieldsConfig)
			throws Exception {

		return setFixedLengthBinaryField(buffer, offset, length, value);
	}

	@Profiled
	public static byte[] setFixedLengthBinaryField(byte[] buffer, int offset,
			int length, byte[] value) throws Exception {
		setBinaryField(buffer, offset, value, length);

		return buffer;
	}

	@Profiled
	public static String addFixedLengthAsciiString(int fieldNo, String value,
			int length, FieldsConfig fieldsConfig) throws Exception {
		return addFixedLengthAsciiString(
				fieldsConfig.getFieldAlignType(fieldNo), value, length);
	}

	@Profiled
	public static String addFixedLengthAsciiString(String fieldName,
			String value, int length, FieldsConfig fieldsConfig)
			throws Exception {
		return addFixedLengthAsciiString(
				fieldsConfig.getFieldAlignType(fieldName), value, length);
	}

	@Profiled
	public static String addFixedLengthAsciiString(AlignType type,
			String value, int asciiLength) throws Exception {
		switch (type) {
		case LEFTZERO:
			value = leftAddZero4FixedLengthString(value, asciiLength);
			break;
		case LEFTSPACE:
			value = leftAddSpace4FixedLengthString(value, asciiLength);
			break;
		case RIGHTSPACE:
			value = rightAddSpace4FixedLengthString(value, asciiLength);
			break;
		case RIGHTZERO:
			value = rightAddZero4FixedLengthString(value, asciiLength);
			break;
		case NONE:
			break;

		default:
			logger.warn("don't support AlignType: " + type);
		}

		return value;
	}
}
