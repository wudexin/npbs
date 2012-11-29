package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

/**
 * epos报文
 * @author jxw
 *
 */
public class FIXSTRINGPacketUtils {
	/**
	 * 加域值通用方法
	 * @param fieldValues
	 * @param fieldName
	 * @param value
	 */
	public static void addFieldValue(Map<String, Object> fieldValues, String fieldName, String value) {
		if(null == value) value = "";
		fieldValues.put(fieldName, value);
	}
}
