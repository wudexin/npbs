package com.nantian.npbs.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.utils.ConvertUtils;

/**
 * 3DES/ECB 加解密算法
 * 
 * @author HuangBo 2011-09-01
 */
public class DESedeEncrypt {

	private static final Logger logger = LoggerFactory
			.getLogger(DESEncrypt.class);

	/** 指定加密算法为3DES */
	private static String ALGORITHM = "DESede";
	/** 指定主密钥 */
	private static final byte[] mainKey = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38
        , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66
        , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2};

	/**
	 * 生成随机密钥
	 * 
	 * @return byte[]
	 */
	public static byte[] generateKey() {
		byte[] b = null;
		try {
			/** DES算法要求有一个可信任的随机数源 */
			SecureRandom sr = new SecureRandom();
			/** 为DES算法创建一个KeyGenerator对象 */
			KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
			/** 利用上面的随机数据源初始化这个KeyGenerator对象 */
			kg.init(sr);
			/** 生成密匙 */
			SecretKey key = kg.generateKey();
			b = key.getEncoded();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return b;
	}

	/**
	 * 明文加密成密文
	 * 
	 * @param keybyte
	 * @param source
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] keybyte, byte[] source) {
		byte[] b = null;
		try {
			SecretKey key = new SecretKeySpec(keybyte, ALGORITHM);
			/** 得到Cipher对象来实现对源数据的DES加密 */
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			/** 执行加密操作 */
			b = cipher.doFinal(source);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return b;
	}

	/**
	 * 密文解密成明文
	 * 
	 * @param keybyte
	 * @param cryptograph
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] keybyte, byte[] cryptograph) {
		byte[] b = null;
		try {
			SecretKey key = new SecretKeySpec(keybyte, ALGORITHM);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//DESede/ECB/PKCS7Padding
			cipher.init(Cipher.DECRYPT_MODE, key);
			// BASE64Decoder decoder = new BASE64Decoder();
			b = cipher.doFinal(cryptograph);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return b;
	}

	/**
	 * 生成KEK
	 * 
	 * @return
	 */
	public static String generateKEK() {
		byte[] kek = generateKey();
		kek = encrypt(mainKey, kek);
		return ConvertUtils.bytes2HexStr(kek);
	}

	public static void main(String[] args) throws Exception {
		String source = "abcd1234";// 要加密的字符串
		// 生成密钥
		byte[] keybyte = generateKey();
		// 转换成十六进制字符串
		System.out.println(ConvertUtils.bytes2HexStr(keybyte));
		// 使用主密钥将生成密钥再加密
		byte[] keyekey = encrypt(mainKey, keybyte);
		String keyekeyString = ConvertUtils.bytes2HexStr(keyekey);
		System.out.println(keyekeyString);
		// 生成的密文
		byte[] cryptograph = encrypt(keybyte, source.getBytes());
		// encoder = new BASE64Encoder();
		System.out.println(ConvertUtils.bytes2HexStr(cryptograph));
		// 解密密文
		System.out.println(new String(decrypt(keybyte, cryptograph)));
	}
}
