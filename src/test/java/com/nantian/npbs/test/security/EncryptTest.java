package com.nantian.npbs.test.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.security.DESEncrypt;

/** 
 * 
 * @author 王玮
 * @version 创建时间：2011-10-1 下午6:42:43 
 * 
 */

public class EncryptTest {
	
	private static final Logger logger = LoggerFactory
			.getLogger(EncryptTest.class);
	
	
	/** 指定主密钥 */
	private static final byte[] mainKey = { (byte)0x7A, 0x51, (byte)0x8C, 0x15, (byte)0xE5, (byte)0x0B,
		(byte)0x2F, (byte)0x9E};	
	
	
	@Test
	public void encrypt(){
		byte[] kek =ConvertUtils.hexStr2Bytes("7DC9996017654EFB");
		
		byte[] encryptedKEK = DESEncrypt.encrypt(mainKey, ConvertUtils.hexStr2Bytes("7DC9996017654EFB"));
		logger.info("checkMac, kek: [" + ConvertUtils.bytes2HexStr(encryptedKEK) + "]");

		byte[] macKey =DESEncrypt.encrypt(kek, ConvertUtils.hexStr2Bytes("2A8FB2FF2B5A8E4A"));
		
		logger.info("checkMac, mac key: [" + ConvertUtils.bytes2HexStr(macKey) + "]");

	}
}
