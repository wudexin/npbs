/**
 * 
 */
package com.nantian.npbs.security.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.security.DESEncrypt;
import com.nantian.npbs.security.PinBlock;
import com.nantian.npbs.security.dao.EncryptionDao;
import com.nantian.npbs.security.dao.EncryptionTerminatorDao;
import com.nantian.npbs.security.model.TbBiEncryption;
import com.nantian.npbs.security.model.TbBiEncryptterminator;

/**
 * @author HuangBo
 * 
 */
@Component(value = "encryptionService")
public class EncryptionService {
	private static Logger logger = LoggerFactory.getLogger(EncryptionService.class);

	@Resource
	private EncryptionDao encryptionDao;

	@Resource
	private EncryptionTerminatorDao terminatorDao;

	/** 指定主密钥 */
	private static final byte[] MAINKEY = { (byte) 0x43, 0x43, 0x0B, 0x76, 0x5D,0x43, (byte) 0xDF, (byte) 0x8F,
		(byte) 0x9E, 0x15, (byte) 0x8C,	(byte) 0xBF, 0x58, (byte) 0xE5, 0x07, (byte) 0xD6,
		(byte) 0x43, 0x43, 0x0B, 0x76, 0x5D, 0x43, (byte) 0xDF, (byte) 0x8F };
	/** 指定KEK密钥 7A518C15E50B2F9E E031CDE09D738573 */
	private static final byte[] KEK = { (byte) 0x7A, 0x51, (byte) 0x8C,0x15, (byte) 0xE5, (byte) 0x0B, (byte) 0x2F, (byte) 0x9E,
		(byte) 0xE0, 0x31, (byte) 0xCD, (byte) 0xE0, (byte) 0x9D,(byte) 0x73, (byte) 0x85, (byte) 0x73
		, (byte) 0x7A, 0x51,(byte) 0x8C, 0x15, (byte) 0xE5, (byte) 0x0B, (byte) 0x2F,(byte) 0x9E };

	/**
	 * 取得双倍长KEK
	 * 
	 * @return String
	 */
	public String getKey() {
		return ConvertUtils.bytes2HexStr(KEK).substring(0, 32);
	}

	/**
	 * 取得已加密的KEK
	 * 
	 * @return String
	 */
	private String getKEK() {
		byte[] b = new byte[24];
		b = DESEncrypt.encrypt(MAINKEY, KEK);
		return ConvertUtils.bytes2HexStr(b);
	}

	/**
	 * 生成KEK
	 * 
	 * @param terminatorid
	 * @return boolean
	 */
	public boolean generateKEK(String terminatorid) {
		TbBiEncryptterminator terminator = terminatorDao
				.getTbBiEncryptterminatorById(terminatorid);
		TbBiEncryption encryption = null;
		boolean success = false;
		if (terminator == null) {
			encryption = new TbBiEncryption();
			encryption.setKeyID(encryptionDao.getNumber());
			encryption.setKek(getKEK());
			if (encryptionDao.persistEncryption(encryption)) {
				success = terminatorDao
						.persistEncryptTerminator(new TbBiEncryptterminator(
								terminatorid, encryption.getKeyID()));
			}
		} else {
			encryption = encryptionDao.getEncryptionById(terminator.getKeyid());
			if (encryption == null) {
				encryption = new TbBiEncryption();
				encryption.setKeyID(terminator.getKeyid());
				encryption.setKek(getKEK());
				success = encryptionDao.persistEncryption(encryption);
			} else {
				logger.info("终端：{} KEK密钥已经存在!",terminatorid);
			}
		}

		return success;
	}

	/**
	 * 生成所有KEY（KEK,PINKEY,MACKEY）
	 * 
	 * @param terminatorid
	 * @return boolean
	 */
	public boolean generatorAllKey(String terminatorid) {
		TbBiEncryptterminator terminator = terminatorDao
				.getTbBiEncryptterminatorById(terminatorid);
		TbBiEncryption encryption = null;
		boolean success = false;
		byte[] b = new byte[24];
		if (terminator == null) {
			encryption = new TbBiEncryption();
			encryption.setKeyID(encryptionDao.getNumber());
			encryption.setKek(getKEK());
			encryption.setOldPinKey(null);
			b = DESEncrypt.generateKey3();
			encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt
					.encrypt(KEK, b)));
			encryption.setOldMacKey(null);
			b = DESEncrypt.generateKey3();
			encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt
					.encrypt(KEK, b)));
			if (encryptionDao.persistEncryption(encryption))
				success = terminatorDao.persistEncryptTerminator(
						new TbBiEncryptterminator(terminatorid, encryption.getKeyID()));
		} else {
			encryption = encryptionDao.getEncryptionById(terminator.getKeyid());
			if (encryption == null) {
				encryption = new TbBiEncryption();
				encryption.setKeyID(terminator.getKeyid());
				encryption.setKek(getKEK());
				encryption.setOldPinKey(null);
				b = DESEncrypt.generateKey3();
				encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt
						.encrypt(KEK, b)));
				encryption.setOldMacKey(null);
				b = DESEncrypt.generateKey3();
				encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt
						.encrypt(KEK, b)));
			} else {
				b = DESEncrypt.generateKey3();
				encryption.setOldPinKey(encryption.getNewPinKey());
				encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt
						.encrypt(KEK, b)));
				b = DESEncrypt.generateKey3();
				encryption.setOldMacKey(encryption.getNewMacKey());
				encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt
						.encrypt(KEK, b)));
			}
			success = encryptionDao.persistEncryption(encryption);
		}

		return success;
	}

	/**
	 * 取得所有密钥
	 * 
	 * @param terminatorid
	 * @return TbBiEncryption
	 */
	public TbBiEncryption getEncryption(String terminatorid) {
		TbBiEncryptterminator terminator = terminatorDao.getTbBiEncryptterminatorById(terminatorid);
		TbBiEncryption encryption = null;
		boolean success = false;
		byte[] b = new byte[24];
		if (terminator == null) {
			encryption = new TbBiEncryption();
			encryption.setKeyID(encryptionDao.getNumber());
			encryption.setKek(getKEK());
			encryption.setOldPinKey(null);
			b = DESEncrypt.generateKey3();
			encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt
					.encrypt(KEK, b)));
			encryption.setOldMacKey(null);
			b = DESEncrypt.generateKey3();
			encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt
					.encrypt(KEK, b)));
			if (encryptionDao.persistEncryption(encryption))
				success = terminatorDao
						.persistEncryptTerminator(new TbBiEncryptterminator(
								terminatorid, encryption.getKeyID()));
		} else {
			encryption = encryptionDao.getEncryptionById(terminator.getKeyid());
			if (encryption == null) {
				encryption = new TbBiEncryption();
				encryption.setKeyID(terminator.getKeyid());
				encryption.setKek(getKEK());
				encryption.setOldPinKey(null);
				b = DESEncrypt.generateKey3();
				encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt
						.encrypt(KEK, b)));
				encryption.setOldMacKey(null);
				b = DESEncrypt.generateKey3();
				encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt
						.encrypt(KEK, b)));
				success = encryptionDao.persistEncryption(encryption);
			} else {
				success = true;
			}
		}

		if (success)
			return encryption;
		else
			return null;

	}

	/**
	 * 根据终端号取得KEK
	 * 
	 * @param terminatorid
	 * @return String
	 */
	public String getKEK(String terminatorid) {
		TbBiEncryption encryption = getEncryption(terminatorid);
		if (encryption == null) {
			return null;
		} else {
			return getKEK();
		}
	}

	/**
	 * 根据终端号取得PINKey
	 * 
	 * @param terminatorid
	 * @return String
	 */
	public String getPINKey(String terminatorid) {
		TbBiEncryption encryption = getEncryption(terminatorid);
		String key = "";
		if (encryption != null) {
			key = encryption.getNewPinKey();
			if (key != null)
				key = key.substring(0, 32);
		}
		return key;
	}

	/**
	 * 根据终端号取得MACKey
	 * 
	 * @param terminatorid
	 * @return String
	 */
	public String getMACKey(String terminatorid) {
		TbBiEncryption encryption = getEncryption(terminatorid);
		String key = "";
		if (encryption != null) {
			key = encryption.getNewMacKey();
			if (key != null)
				key = key.substring(0, 32);
		}
		return key;
	}

	/**
	 * 根据终端号取得80长度工作密钥（PIN+MAC)
	 * 
	 * @param terminatorid
	 * @return String
	 */
	public String getWorkKey(String terminatorid) {
		TbBiEncryptterminator terminator = terminatorDao
				.getTbBiEncryptterminatorById(terminatorid);
		TbBiEncryption encryption = null;
		boolean success = false;
		byte[] pin = DESEncrypt.generateKey3();
		byte[] mac = DESEncrypt.generateKey3();
		String workkey = "", pinkey = "", mackey = "";
		if (terminator == null) {
			encryption = new TbBiEncryption();
			encryption.setKeyID(encryptionDao.getNumber());
			encryption.setKek(getKEK());
			encryption.setOldPinKey(null);
			encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt
					.encrypt(KEK, pin)));
			encryption.setOldMacKey(null);
			encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt
					.encrypt(KEK, mac)));
			if (encryptionDao.persistEncryption(encryption))
				success = terminatorDao
						.persistEncryptTerminator(new TbBiEncryptterminator(
								terminatorid, encryption.getKeyID()));
		} else {
			encryption = encryptionDao.getEncryptionById(terminator.getKeyid());
			if (encryption == null) {
				encryption = new TbBiEncryption();
				encryption.setKeyID(terminator.getKeyid());
				encryption.setKek(getKEK());
				encryption.setOldPinKey(null);
				encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt.encrypt(KEK, pin)));
				encryption.setOldMacKey(null);
				encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt.encrypt(KEK, mac)));
			} else {
				encryption.setOldPinKey(encryption.getNewPinKey());
				encryption.setNewPinKey(ConvertUtils.bytes2HexStr(DESEncrypt.encrypt(KEK, pin)));
//				logger.info("加密前:"+ConvertUtils.bytes2HexStr(pin));
//				logger.info("加密后:"+encryption.getNewPinKey());
				encryption.setOldMacKey(encryption.getNewMacKey());
				encryption.setNewMacKey(ConvertUtils.bytes2HexStr(DESEncrypt.encrypt(KEK, mac)));
			}
			success = encryptionDao.persistEncryption(encryption);
		}

		if (success) {
			pinkey = encryption.getNewPinKey();
			mackey = encryption.getNewMacKey();
			byte[] temp = { 0, 0, 0, 0, 0, 0, 0, 0 };
			String pincheck = ConvertUtils.bytes2HexStr(DESEncrypt.encrypt(pin, temp)).substring(0, 8);
			workkey = pinkey.substring(0, 32) + pincheck;
			String maccheck = ConvertUtils.bytes2HexStr(DESEncrypt.encrypt(mac, temp)).substring(0, 8);
			workkey += mackey.substring(0, 32) + maccheck;
		}

		return workkey;
	}

	/**
	 * 校验PIN
	 * 
	 * @param pindata
	 * @param accno
	 * @param terminatorid
	 * @param md5pwdaccno
	 * @return boolean
	 */
	public boolean checkPIN(byte[] pindata, String accno, String terminatorid, String md5pwdaccno) {
		logger.info("开始校验终端:{}的PIN!",terminatorid);

		TbBiEncryption encryption = getEncryption(terminatorid);
		if (encryption == null) {
			logger.info("终端:{}尚未生成主密钥！" ,terminatorid );
			return false;
		}
		if (encryption.getNewPinKey() == null
				|| encryption.getNewPinKey().equals("")) {
			logger.info("终端:{}尚未生成PIN密钥！",terminatorid );
			return false;
		}

		byte[] pinKey = DESEncrypt.decrypt(KEK, ConvertUtils
				.hexStr2Bytes(encryption.getNewPinKey()));
//		logger.info(ConvertUtils.bytes2HexStr(KEK));
//		logger.info("解密后pin:"+ConvertUtils.bytes2HexStr(pinKey).substring(0, 32));
//		logger.info("checkPin, kek: [{}]pin key: [{}]",getKEK(),ConvertUtils.bytes2HexStr(pinKey).substring(0, 32));
//		logger.info("pindata:"+ConvertUtils.bytes2HexStr(pindata));
		byte[] pinBlock = DESEncrypt.decrypt(pinKey, pindata);
//		logger.info("pinBlock:"+ConvertUtils.bytes2HexStr(pinBlock));
		String md5pin = null;
		try {
			md5pin = PinBlock.getMD5(pinBlock, accno);
		} catch (Exception e) {
			logger.error("pin错误!" + e );
			return false;
		}
		
		if(md5pin == null){
			logger.info("pin错误!" );
			return false;
		}
		
		return md5pin.equalsIgnoreCase(md5pwdaccno);
	}
	
	/**
	 * 生成MD5pin
	 * 
	 * @param pindata
	 * @param accno
	 * @param terminatorid
	 * @return md5pwdaccno
	 */
	public String getMD5Pin(byte[] pindata, String accno, String terminatorid) {
		logger.info("开始校验终端:{}的PIN!", terminatorid);

		TbBiEncryption encryption = getEncryption(terminatorid);
		if (encryption == null) {
			logger.info("终端:{}尚未生成主密钥！" ,terminatorid);
			return null;
		}
		if (encryption.getNewPinKey() == null
				|| encryption.getNewPinKey().equals("")) {
			logger.info("终端:{}尚未生成PIN密钥！" ,terminatorid);
			return null;
		}

		byte[] pinKey = DESEncrypt.decrypt(KEK, ConvertUtils
				.hexStr2Bytes(encryption.getNewPinKey()));
		logger.info("checkPin, kek: [{}]pin key: [{}]",getKEK() ,ConvertUtils.bytes2HexStr(pinKey).substring(0, 32));
		
		byte[] pinBlock = DESEncrypt.decrypt(pinKey, pindata);
		String md5pin = null;
		try {
			md5pin = PinBlock.getMD5(pinBlock, accno);
		} catch (Exception e) {
			logger.error("pin错误!" + e );
			return null;
		}
		
		if(md5pin == null){
			logger.info("pin错误!" );
			return null;
		}
		
		return md5pin;
	}
	

	/**
	 * 校验MAC
	 * 
	 * @param mac
	 * @param macdata
	 * @param terminatorid
	 * @return boolean
	 */
	public boolean checkMAC(byte[] mac, byte[] macdata, String terminatorid) {
		logger.info("开始校验终端:{}的MAC!" ,terminatorid);
		byte[] macKey = checkTerminator(terminatorid);
		if (macKey == null)
			return false;
		byte[] newMac = DESEncrypt.encrypt(macKey, getAnsi(macdata));
		String sMac = ConvertUtils.bytes2HexStr(mac);
		String sNewMac = ConvertUtils.bytes2HexStr(newMac);
		logger.info("client mac: [{}] generated mac: [{}]" ,sMac , sNewMac);
		return sNewMac.startsWith(sMac);
	}

	/**
	 * ANSI X.919算法
	 * 
	 * @param macdata
	 * @return byte[]
	 */
	private byte[] getAnsi(byte[] macdata) {
		int length = macdata.length;
		byte[] b0 = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		byte[] b1 = new byte[8];
				
		int num = (length%8==0)?length/8:(length/8+1);
		int index = 0;
		for(int i=0;i<num;i++){
			for(int j=0;j<8;j++){
				index = i*8+j;
				b1[j] = (index<length)?macdata[index]:(byte)0x00;
				b0[j] = (byte)(b0[j]^b1[j]);
			}
		}
		
		return b0;
	}

	/**
	 * 检查终端密钥
	 * 
	 * @param terminatorid
	 * @return byte[]
	 */
	private byte[] checkTerminator(String terminatorid) {
		TbBiEncryption encryption = getEncryption(terminatorid);
		if (encryption == null) {
			logger.info("终端:{}尚未生成主密钥密钥！",terminatorid);
			return null;
		}
		if (encryption.getNewMacKey() == null || encryption.getNewMacKey().equals("")) {
			logger.info("终端:{}尚未生成MAC密钥！",terminatorid);
			return null;
		}

		byte[] macKey = DESEncrypt.decrypt(KEK, ConvertUtils.hexStr2Bytes(encryption.getNewMacKey()));
		logger.info("checkMac, kek: [{}] mac key: [{}]",getKEK() , ConvertUtils.bytes2HexStr(macKey).substring(0, 32));

		return macKey;
	}

	/**
	 * 生成密文MAC
	 * 
	 * @param macdata
	 * @param terminatorid
	 * @return byte[]
	 */
	public byte[] encryptMAC(byte[] macdata, String terminatorid) {
		logger.info("开始生成终端:{}encrypt MAC!" ,terminatorid );
		byte[] macKey = checkTerminator(terminatorid);
		if (macKey == null)
			return null;
		byte[] newMac = DESEncrypt.encrypt(macKey,getAnsi(macdata));
//		logger.info("mac data : [{}] encrypt mac : [{}]",ConvertUtils.bytes2HexStr(macdata)	, ConvertUtils.bytes2HexStr(newMac));
		logger.info("encrypt mac : [{}]", ConvertUtils.bytes2HexStr(newMac));

		return newMac;
	}

}
