package com.nantian.npbs.common.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

public class NtStringUtils {

	public static String getFilePath(String fileName) {
		if (!(fileName == null)) {
			try {
				String filename = new File(fileName).getName();
				String filePath = fileName.substring(0, fileName.length() - filename.length());
				return filePath;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static String formatFileName(String filePath, String fileName) {
		char cLast = '/';
		char cLast1 = '\\';
		char cSep = '/';
		String fullFileName = null;
		int sPos = 0;
		if (filePath != null) {
			sPos = filePath.lastIndexOf(cLast);
			if (sPos == -1) {
				sPos = filePath.lastIndexOf(cLast1);
				if (sPos != -1) {
					cSep = cLast1;
				}
			} else {
				cSep = cLast;
			}
			if (sPos != -1) {
				if (sPos < (filePath.length() - 1)) {
					fullFileName = filePath + cSep + fileName;
				} else {
					fullFileName = filePath + fileName;
				}
			}
		} else {
			fullFileName = fileName;
		}
		return fullFileName;
	}

	public static String mergePath(String path1, String path2) {
		String fullFileName = "";
		String firstHalf = "";
		String secondHalf = "";

		// 去掉尾部/
		if (path1.charAt(path1.length() - 1) == '/') {
			firstHalf = path1.substring(0, path1.length() - 1);
		} else {
			firstHalf = path1;
		}

		// 去掉尾部/，去掉头部/
		if (path2.charAt(path2.length() - 1) == '/') {
			secondHalf = path2.substring(0, path2.length() - 1);
		} else {
			secondHalf = path2;
		}
		if (path2.charAt(0) == '/') {
			secondHalf = secondHalf.substring(1);
		}

		fullFileName = firstHalf + "/" + secondHalf;
		return fullFileName;
	}

	public static String formatString(String str, String strFrom, String strTo) {
		String eResult = str.replaceFirst(strFrom, strTo);
		return eResult;
	}

	public static String quotedStr(String str) {
		return new StringBuffer("'").append(str).append("'").toString();
	}

	public static String parenthesizedStr(String str) {
		return new StringBuffer("(").append(str).append(")").toString();
	}

	public static StringBuffer parenthesizedStrBuffer(StringBuffer sb) {
		sb.insert(0, '(').append(')');
		return sb;
	}

	/**
	 * 字符串是否存在字符串数组中
	 * 
	 * @param str
	 * @param strarray
	 * @return
	 */
	public static boolean exist(String str, String[] strarray) {
		boolean exist = false;
		int i = 0;
		while (i < strarray.length) {
			if (str.equals(strarray[i])) {
				exist = true;
				break;
			}
			i++;
		}
		return exist;
	}

	public static boolean contains(List stringList, String stringToFind) {
		if (stringList == null)
			return false;
		if (stringList.size() == 0)
			return false;
		for (int i = 0; i < stringList.size(); i++) {
			if (stringList.get(i).toString().equals(stringToFind))
				return true;
		}
		return false;
	}

	public static String getSqlConditionFromValueList(List valueList, String fieldName) {
		StringBuffer result = new StringBuffer();
		if (valueList.size() > 0) {
			for (int i = 0; i < valueList.size() - 1; i++)
				result.append(fieldName).append(" = ").append(NtStringUtils.quotedStr(valueList.get(i).toString())).append(
						" or ");
			result.append(fieldName).append(" = ").append(
					NtStringUtils.quotedStr(valueList.get(valueList.size() - 1).toString()));
		}
		return result.toString();
	}

	public static String getUuid() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

	public static String getDocPathFromDocId(String docId) {
		return docId.substring(0, 2) + "/" + docId.substring(2, 4) + "/" + docId;
	}
}
