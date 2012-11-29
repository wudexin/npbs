package com.nantian.npbs.security;

import org.apache.commons.codec.digest.DigestUtils;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.internal.FieldUtils;

public class PinBlock {
	/**
	 * getPinBlock 标准ANSI X9.8 Format（带主帐号信息）的PIN BLOCK计算 PIN BLOCK 格式等于 PIN
	 * 按位异或 主帐号;主帐号低于10位其实是无效的。目前使用备付金帐号做主帐号
	 * 
	 * @param pin
	 *            String
	 * @param accno
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] process(String pin, String accno) throws Exception {
		byte arrAccno[] = getHAccno(accno);
		byte arrPin[] = getHPin(pin);
		byte arrRet[] = new byte[8];
		// PIN BLOCK 格式等于 PIN 按位异或 主帐号;
		for (int i = 0; i < 8; i++) {
			arrRet[i] = (byte) (arrPin[i] ^ arrAccno[i]);
		}
		// System.out.println("PinBlock："+ConvertUtils.bytes2HexStr(arrRet));
		return arrRet;
	}

	public static String getMD5Pin(String pin, String accno) {
		return DigestUtils.md5Hex(pin + accno);
	}

	public static String getMD5(String pinBlock, String accno) throws Exception {
		return DigestUtils.md5Hex(getPin(pinBlock, accno) + accno);
	}

	public static String getMD5(byte[] pinBlock, String accno) throws Exception {
		return DigestUtils.md5Hex(getPin(pinBlock, accno) + accno);
	}

	/**
	 * getPin PIN BLOCK密文，ACCNO明文
	 * 
	 * @param pinBlock
	 * @param accno
	 * @return
	 * @throws Exception
	 */
	private static String getPin(String pinBlock, String accno)
			throws Exception {
		byte[] pinblock = ConvertUtils.hexStr2Bytes(pinBlock);
		byte[] arrAccno = getHAccno(accno);
		byte[] arrPin = new byte[8];
		// PIN 等于PINBLOCK 按位异或主帐号
		for (int i = 0; i < 8; i++) {
			arrPin[i] = (byte) (pinblock[i] ^ arrAccno[i]);
		}
		return ConvertUtils.bytes2HexStr(arrPin).substring(2, 8);
	}

	/**
	 * getPin PIN BLOCK密文，ACCNO明文
	 * 
	 * @param pinBlock
	 * @param accno
	 * @return
	 * @throws Exception
	 */
	private static String getPin(byte[] pinBlock, String accno)
			throws Exception {
		byte[] arrAccno = getHAccno(accno);
		byte[] arrPin = new byte[8];
		// PIN 等于PINBLOCK 按位异或主帐号
		for (int i = 0; i < 8; i++) {
			arrPin[i] = (byte) (pinBlock[i] ^ arrAccno[i]);
		}
		return ConvertUtils.bytes2HexStr(arrPin).substring(2, 8);
	}
	public static void main(String[] args) throws Exception {
		byte[] pinBlock= ConvertUtils.str2Bcd("0000000000060000");
		//c088f3a5b5cd22c6
		String str = getPin(pinBlock,"999999999");
		System.out.println(str);
		
		byte[]bt = process("888888","999999999");
		System.out.println(ConvertUtils.bytes2HexStr(bt));
	}

	/**
	 * getHPin 对密码进行转换 PIN格式 BYTE 1 PIN的长度 BYTE 2 – BYTE 3/4/5/6/7
	 * 4--12个PIN(每个PIN占4个BIT) BYTE 4/5/6/7/8 – BYTE 8 FILLER “F” (每个“F“占4个BIT)
	 * 
	 * @param pin
	 *            String
	 * @return byte[]
	 */
	private static byte[] getHPin(String pin) {
		byte arrPin[] = pin.getBytes();
		byte encode[] = new byte[8];
		encode[0] = (byte) 0x06;
		encode[1] = (byte) uniteBytes(arrPin[0], arrPin[1]);
		encode[2] = (byte) uniteBytes(arrPin[2], arrPin[3]);
		encode[3] = (byte) uniteBytes(arrPin[4], arrPin[5]);
		encode[4] = (byte) 0xFF;
		encode[5] = (byte) 0xFF;
		encode[6] = (byte) 0xFF;
		encode[7] = (byte) 0xFF;
		// System.out.println("encoded pin："+ConvertUtils.bytes2HexStr(encode));
		return encode;
	}

	/**
	 * getHAccno 对帐号进行转换 BYTE 1 — BYTE 2 0X0000 BYTE 3 — BYTE 8 12个主帐号
	 * 取主帐号的右12位（不包括最右边的校验位），不足12位左补“0X00”。
	 * 
	 * @param accno
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	private static byte[] getHAccno(String accno) throws Exception {
		// 取出主帐号；

		accno = FieldUtils.leftAddZero4FixedLengthString(accno, 13);
		int len = accno.length();
		accno = accno.substring(len - 13, len - 1);

		byte arrAccno[] = accno.getBytes();
		byte encode[] = new byte[8];
		encode[0] = (byte) 0x00;
		encode[1] = (byte) 0x00;
		encode[2] = (byte) uniteBytes(arrAccno[0], arrAccno[1]);
		encode[3] = (byte) uniteBytes(arrAccno[2], arrAccno[3]);
		encode[4] = (byte) uniteBytes(arrAccno[4], arrAccno[5]);
		encode[5] = (byte) uniteBytes(arrAccno[6], arrAccno[7]);
		encode[6] = (byte) uniteBytes(arrAccno[8], arrAccno[9]);
		encode[7] = (byte) uniteBytes(arrAccno[10], arrAccno[11]);
		// System.out.println("encoded accno："+ConvertUtils.bytes2HexStr(encode));
		return encode;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
//	public static void main(String[] args) throws Exception {
//		String pin = "123456";
//		String accno = "1234567890";
//		byte b[] = process(pin, accno);
//		String pinblock = ConvertUtils.bytes2HexStr(b);
//		System.out.println("PIN:" + pin);
//		System.out.println("PinBlock:" + pinblock);
//		System.out.println("deconde PIN:" + getPin(pinblock, accno));
//		System.out.println("MD5 PINACCNO:" + getMD5(pinblock, accno));
//		
//		
//		System.out.println(DigestUtils.md5Hex("888888"+"0500000101"));
//	}

}
