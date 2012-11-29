package com.nantian.npbs.test.security;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.zip.CRC32;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.internal.FieldUtils;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws Exception {
//		String src = "8";
//		byte btSrc = 11;
//		String str = (ConvertUtils.byteToString((byte)15));
//		System.out.println(str);
//		System.out.println(ConvertUtils.bytes2HexStr(ConvertUtils.str2Bcd(Integer.toHexString(4))));
//		
//		System.out.println(ConvertUtils.byteToString((byte)Integer.valueOf("16").intValue()));
		
		
		
//crc
//		byte[] lenstr = ConvertUtils.str2Bcd("00211540");
//		String len = new String(lenstr);
//		String src = "000000" + len + "1D23035D0D58821037E52317AC27EDF88   857";
//		byte[] btsrc = src.getBytes(DynamicConst.PACKETCHARSET);
//		CRC32 crc32 = new CRC32();
//		crc32.update(btsrc);
//		long crc = crc32.getValue();
//		System.out.println(crc);
//		String crcstr = Long.toHexString(crc);
//		System.out.println(crcstr);
//		byte[] crcbt = ConvertUtils.str2Bcd(crcstr);
//		System.out.println(FieldUtils.getBcdField(crcbt, 0, 4));
//		String astr = ConvertUtils.bytes2HexStr(crcbt);
//		System.out.println(astr);
//		
//		
//		byte[] b1 = new byte[4];//ConvertUtils.hexStr2Bytes(astr);
////		System.out.println(ConvertUtils.bytes2HexStr(b1));
//		
//		FieldUtils.setBcdField(b1, 0, astr, 4);
//		System.out.println(ConvertUtils.bcd2Asc(b1));
//		
//		String s1 = new String(b1, DynamicConst.PACKETCHARSET);
//		System.out.println(s1);
//		System.out.println(ConvertUtils.bytes2HexStr(ConvertUtils.str2Bcd(s1)));
//		byte[] b2 = s1.getBytes(DynamicConst.PACKETCHARSET);
//		
//		System.out.println(ConvertUtils.bytes2HexStr(b2));
//		
//		
//		System.out.println(ConvertUtils.bytes2HexStr(ConvertUtils.str2Bcd(String.valueOf(175406))));
//		String retstr = new String(crcbt);
//		FieldUtils.setAsciiField(buffer, offset, value, length)
//		System.out.println(retstr);
		
//其他测试
//		int len1 = 15321;
//		String lenstr1 = Integer.toHexString(len1);
//		System.out.println(lenstr1);
//		
//		
//		lenstr = FieldUtils.leftAddZero4FixedLengthString(lenstr, 3*2);
//		System.out.println(lenstr);
		
//		String src = "000000002115401D23035D0D58821037E52317AC27EDF88000857";
		
////		byte[] bt = {0x00,0x21,0x15,0x40};
//		String src = "00211540";
//		byte[] bt = ConvertUtils.str2Bcd(src);
//		System.out.println(ConvertUtils.byteToString(ConvertUtils.str2Bcd(src)));
//		ConvertUtils.bcd2Asc(bt);
		
		
//		System.out.println(ConvertUtils.byteToString((byte)15));

		//读取配置文件
//		ResourceBundle rb = ResourceBundle.getBundle("checkMac");
//		String str = null;
//		try{
//			str = rb.getString("checkMacFlag1");
//		}catch(Exception e){
//			System.out.println("jian");
//			return;
//		}
//		
//		boolean checkMacFlag = Boolean.valueOf(str);
//		System.out.println(checkMacFlag);
		char flag = '0';
		System.out.println(String.valueOf(flag));
	}
	
	
	public static String getVariableLengthBcdAscii(int bcdlen,String src) throws Exception{
		String retstr = null;
		byte[] btSrc = ConvertUtils.str2Bcd(src);
		
		int length = Integer.parseInt(FieldUtils.getBcdField(btSrc, 0, 2));
		
		retstr = FieldUtils.getAsciiField(btSrc, 2, length);
		return retstr;
	}
	
	public static String getFixedLengthBcd(int bcdlen,String src) throws Exception{
		String retstr = null;
		byte[] btSrc = ConvertUtils.str2Bcd(src);
		
		retstr = FieldUtils.getBcdField(btSrc, 0, bcdlen);
		return retstr;
	}

}
