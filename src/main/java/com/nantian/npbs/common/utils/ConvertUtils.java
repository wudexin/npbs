/**
 * 
 */
package com.nantian.npbs.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.perf4j.aop.Profiled;

/**
 * @author TsaiYee
 * 
 */
public class ConvertUtils {
	private ConvertUtils() {

	}

	public final static char[] BCD_2_ASC = "0123456789abcdef".toCharArray();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 测试十六进制字符转换为字节
		char achar = 'A';
		System.out.println("字符" + achar + "的byte值为"
				+ ConvertUtils.hexChar2byte(achar));

	}

	/**
	 * 将字符转换为字节，将十六进制字符串转为字节数组时用
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param achar
	 * @return
	 */
	@Profiled
	private static byte hexChar2byte(char achar) {
		byte b = (byte) "0123456789ABCDEF"
				.indexOf(Character.toUpperCase(achar));
		return b;
	}

	@Profiled
	public static byte char2byte(char ch) {
		int temp = (int) ch;
		byte b = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
		return b;
	}

	@Profiled
	public static int byte2int(byte[] bytes) {
		int r = 0;
		for (int i = 0; i < bytes.length; i++) {
			r <<= 8;
			r |= (bytes[i] & 0x000000ff);
		}

		return r;
	}

	/**
	 * 将16进制字符串转换成字节数组
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param hexStr
	 * @return
	 */
	public static final byte[] hexStr2BytesOld(String hexStr) {
		int len = (hexStr.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hexStr.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (hexChar2byte(achar[pos]) << 4 | hexChar2byte(achar[pos + 1]));
		}
		return result;
	}

	@Profiled
	public static final byte[] hexStr2Bytes(String hexStr) {
		try {
			return Hex.decodeHex(hexStr.toCharArray());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Profiled
	public static final String bytes2HexStr(byte[] bArray) {
		return Hex.encodeHexString(bArray);
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param bArray
	 * @return
	 */
	@Profiled
	public static final String bytes2HexStrOld(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}


	/**
	 * 将字节数组转换为对象
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Profiled
	public static final Object bytes2Object(byte[] bytes) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(in);
		Object o = oi.readObject();
		oi.close();
		return o;
	}

	/**
	 * 将可序列化对象转换成字节数组
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	@Profiled
	public static final byte[] object2Bytes(Serializable s) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream ot = new ObjectOutputStream(out);
		ot.writeObject(s);
		ot.flush();
		ot.close();
		return out.toByteArray();
	}

	/**
	 * 将可序列化对象转换成16进制字符串
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	@Profiled
	public static final String object2HexStr(Serializable s) throws IOException {
		return bytes2HexStr(object2Bytes(s));
	}

	/**
	 * 将16进制字符串转换成对象
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param hexStr
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws DecoderException
	 */
	@Profiled
	public static final Object hexStr2Object(String hexStr) throws IOException,
			ClassNotFoundException, DecoderException {
		return bytes2Object(ConvertUtils.hexStr2Bytes(hexStr));
	}

	/**
	 * BCD码转为10进制串(阿拉伯数字串)
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param bytes
	 * @return
	 */
	@Profiled
	public static String bcd2DigitStr(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/**
	 * 10进制串转为BCD码
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param asc
	 * @return
	 */
	@Profiled
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/**
	 * BCD码转ASC码
	 * 
	 * @author hfh, 2007-10-25
	 * 
	 * @param bytes
	 * @return
	 */
	@Profiled
	public static String bcd2Asc(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			int h = ((bytes[i] & 0xf0) >>> 4);
			int l = (bytes[i] & 0x0f);
			temp.append(BCD_2_ASC[h]).append(BCD_2_ASC[l]);
		}
		return temp.toString();
	}

	/**
	 * 将字符串转换为布尔值
	 * 
	 * @author hfh, 2007-10-26
	 * 
	 * @param value
	 * @return
	 */
	@Profiled
	public static boolean str2boolean(String value) {
		return value.equalsIgnoreCase("true") || value.equals("1");
	}

	/**
	 * 将byte[] 转换为 BitSet
	 * 
	 * @author TsaiYee
	 * 
	 * @param bytes
	 * @return BitSet
	 */
	@Profiled
	public static BitSet byteArray2BitSet(byte[] bytes) {
		BitSet bits = new BitSet();
		int i, j, k;
		bits.clear();
		for (i = k = 0; i < bytes.length; i++)
			for (j = 0x80; j != 0; j >>>= 1, k++)
				if ((bytes[i] & j) != 0)
					bits.set(k);
		return bits;
	}

	/**
	 * 将BitSet 转换为 byte[]
	 * 
	 * @author TsaiYee
	 * 
	 * @param bits
	 * @return byte[]
	 */
	@Profiled
	public static byte[] bitset2byteArray(BitSet bits) {
		int i;
		int byteLen = bits.length() / 8;
		if (bits.length() % 8 != 0) {
			byteLen += 1;
		}
		byte[] bytes = new byte[byteLen];
		java.util.Arrays.fill(bytes, (byte) 0);
		for (i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1))
			bytes[i >>> 3] |= 0x80 >>> (i & 7);

		return bytes;
	}

	/**
	 * 将byte转换成字符串
	 * 
	 * @param b
	 * @return String
	 */
	@Profiled
	public static String byteToString(byte b) {
		byte high, low;
		byte maskHigh = (byte) 0xf0;
		byte maskLow = 0x0f;

		high = (byte) ((b & maskHigh) >> 4);
		low = (byte) (b & maskLow);

		StringBuffer buf = new StringBuffer();
		buf.append(findHex(high));
		buf.append(findHex(low));

		return buf.toString();
	}

	/**
	 * 查找十六进制字符
	 * 
	 * @param b
	 * @return char
	 */
	@Profiled
	private static char findHex(byte b) {
		int t = new Byte(b).intValue();
		t = t < 0 ? t + 16 : t;

		if ((0 <= t) && (t <= 9)) {
			return (char) (t + '0');
		}

		return (char) (t - 10 + 'A');
	}

	/**
	 * 字符串转为16进制
	 * 
	 * @param str
	 * @return
	 */
	@Profiled
	public String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf0) >> 4));
			sb.append("0123456789ABCDEF".charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 * 
	 * @param bytes
	 * @return
	 */
	@Profiled
	public String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write(("0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF"
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
}
