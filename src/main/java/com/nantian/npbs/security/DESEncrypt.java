package com.nantian.npbs.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.security.model.TbBiEncryption;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * 3DES/ECB 双倍密钥加解密算法
 * 
 * @author HuangBo 2011-09-01
 */
public class DESEncrypt {
	private static final Logger logger = LoggerFactory
			.getLogger(DESEncrypt.class);
	/** 指定主密钥 */
	private static final byte[] MAINKEY = { (byte) 0x43, 0x43, 0x0B, 0x76,
			0x5D, 0x43, (byte) 0xDF, (byte) 0x8F, (byte) 0x9E, 0x15,
			(byte) 0x8C, (byte) 0xBF, 0x58, (byte) 0xE5, 0x07, (byte) 0xD6,
			(byte) 0x43, 0x43, 0x0B, 0x76, 0x5D, 0x43, (byte) 0xDF, (byte) 0x8F };
	/** 指定KEK密钥 7A518C15E50B2F9E E031CDE09D738573 */
	private static final byte[] KEK = { (byte) 0x7A, 0x51, (byte) 0x8C, 0x15,
			(byte) 0xE5, (byte) 0x0B, (byte) 0x2F, (byte) 0x9E, (byte) 0xE0,
			0x31, (byte) 0xCD, (byte) 0xE0, (byte) 0x9D, (byte) 0x73,
			(byte) 0x85, (byte) 0x73, (byte) 0x7A, 0x51, (byte) 0x8C, 0x15,
			(byte) 0xE5, (byte) 0x0B, (byte) 0x2F, (byte) 0x9E };

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
			KeyGenerator kg = KeyGenerator.getInstance("DES");
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
	 * 生成双倍长随机密钥
	 * 
	 * @return byte[]
	 */
	public static byte[] generateKey2() {
		byte[] b = new byte[16];
		byte[] b1 = generateKey();
		byte[] b2 = generateKey();

		for (int i = 0; i < 16; i++) {
			if (i < 8)
				b[i] = b1[i];
			else
				b[i] = b2[i - 8];
		}

		return b;
	}

	/**
	 * 三倍长密钥转换成双倍长密钥
	 * 
	 * @param b1
	 * @return
	 */
	public static byte[] generateKey2(byte[] b1) {
		if (b1.length != 24)
			return null;
		byte[] b = new byte[16];
		for (int i = 0; i < 16; i++)
			b[i] = b1[i];
		return b;
	}

	/**
	 * 生成三倍长随机密钥
	 * 
	 * @return byte[]
	 */
	public static byte[] generateKey3() {
		byte[] b = new byte[24];
		byte[] b1 = generateKey2();

		for (int i = 0; i < 24; i++) {
			if (i < 16)
				b[i] = b1[i];
			else
				b[i] = b1[i - 16];
		}

		return b;
	}

	/**
	 * 双倍长密钥转换成三倍长密钥
	 * 
	 * @param b1
	 * @return byte[]
	 */
	public static byte[] generateKey3(byte[] b1) {
		if (b1.length != 16)
			return null;
		byte[] b = new byte[24];
		for (int i = 0; i < 24; i++) {
			if (i < 16)
				b[i] = b1[i];
			else
				b[i] = b1[i - 16];
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
		String algorithm = "DES";
		try {
			if (keybyte.length == 8) {
				algorithm = "DES";
			} else if (keybyte.length == 16) {
				keybyte = generateKey3(keybyte);
				algorithm = "DESede";
			} else if (keybyte.length == 24) {
				algorithm = "DESede";
			} else {
				throw new Exception("密钥应为64或128或192位!");
			}

			if (source.length % 8 != 0) {
				throw new Exception("要加密的数据长度应为8位的整倍数!");
			}
			SecretKey key = new SecretKeySpec(keybyte, algorithm);
			/** 得到Cipher对象来实现对源数据的DES加密 */
			Cipher cipher = Cipher.getInstance(algorithm + "/ECB/NoPadding");
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
		String algorithm = "DES";
		try {
			if (keybyte.length == 8) {
				algorithm = "DES";
			} else if (keybyte.length == 16) {
				keybyte = generateKey3(keybyte);
				algorithm = "DESede";
			} else if (keybyte.length == 24) {
				algorithm = "DESede";
			} else {
				throw new Exception("密钥应为64或128或192位!");
			}

			if (cryptograph.length % 8 != 0) {
				throw new Exception("要解密的数据长度应为8位的整倍数!");
			}

			SecretKey key = new SecretKeySpec(keybyte, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm + "/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			b = cipher.doFinal(cryptograph);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return b;
	}

	/**
	 * 获得KEK
	 * 
	 * @return String
	 */
	public static String getKEK() {
		byte[] kek = DESEncrypt.encrypt(MAINKEY, KEK);
		return ConvertUtils.bytes2HexStr(kek);
	}

	/**
	 * 生成KEK
	 * 
	 * @return String
	 */
	public static String generateKEK() {
		byte[] kek = generateKey3();
		kek = encrypt(MAINKEY, KEK);
		return ConvertUtils.bytes2HexStr(kek);
	}

	public static void main(String[] args) throws Exception {
		  String source = "04FB4975BE6828916EA43654FDD27DF404FB4975BE682891";// 要加密的字符串
		  // 生成密钥
		  byte[] keybyte = generateKey2();
		// // 转换成十六进制字符串
		 System.out.println(ConvertUtils.bytes2HexStr(keybyte));
		  System.out.println(ConvertUtils.bytes2HexStr(encrypt(MAINKEY,keybyte)));
		  // 使用主密钥将生成密钥再加密
		  keybyte = generateKey3(keybyte);
		  System.out.println(ConvertUtils.bytes2HexStr(keybyte));
		  System.out.println(ConvertUtils.bytes2HexStr(encrypt(MAINKEY,keybyte)));
		  
		  
		  
/*		  // 生成的密文
 	   byte[] cryptograph = encrypt(keybyte, source.getBytes());
	 	  BASE64Encoder encoder = new BASE64Encoder();
		  System.out.println(encoder.encode(cryptograph));
		 //  解密密文
		  System.out.println(new String(decrypt(keybyte, cryptograph)));
*/
		  byte[] pinKey = DESEncrypt.decrypt(KEK,
					ConvertUtils.hexStr2Bytes("04FB4975BE6828916EA43654FDD27DF404FB4975BE682891"));
		  byte[] pinblock = PinBlock.process("88888888", "05008895");
			byte[] pin = encrypt(pinKey, pinblock);
			logger.info("jiami::{}" , ConvertUtils.bytes2HexStr(pin)); 
		  
		//	logger.info("checkPin, kek: [{}]pin key: [{}]",getKEK(),ConvertUtils.bytes2HexStr(pinKey).substring(0, 3));
			
		 // PinBlock.getMD5("88888888", "05008895");
			
			
			
/*		String accno = "05000001";
		String pwd = "123456";
		byte[] pinblock = PinBlock.process(pwd, accno);
		EncryptionService es = new EncryptionService();

		TbBiEncryption encryption = es.getEncryption(accno);
		if (encryption == null) {
			logger.warn("终端:" + accno + "尚未生成主密钥！");

		} else if (encryption.getNewPinKey() == null
				|| encryption.getNewPinKey().equals("")) {
			logger.warn("终端:" + accno + "尚未生成PIN密钥！");
		} else {
			byte[] pinKey = DESEncrypt.decrypt(KEK,
					ConvertUtils.hexStr2Bytes(encryption.getNewPinKey()));
			logger.info("checkPin, kek: [{}]pin key: [{}]",getKEK(),ConvertUtils.bytes2HexStr(pinKey).substring(0, 3));
			byte[] pin = encrypt(pinKey, pinblock);
			logger.info("密钥加密后:{}" , ConvertUtils.bytes2HexStr(pin));
		}*/
	}
}
