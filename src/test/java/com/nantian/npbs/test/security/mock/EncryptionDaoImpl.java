/**
 * 
 */
package com.nantian.npbs.test.security.mock;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.security.dao.EncryptionDao;
import com.nantian.npbs.security.model.TbBiEncryption;

/**
 * 密钥
 * 
 * @author HuangBo
 * 
 */
@Transactional
@Repository(value = "encryptionDao")
public class EncryptionDaoImpl implements EncryptionDao {
	@Override
	public boolean persistEncryption(TbBiEncryption encryption) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public TbBiEncryption getEncryptionById(String id) {
		try {
			TbBiEncryption e = new TbBiEncryption();
			e.setKeyID(id);
			e.setKek("EF100EC351BD7CF82A8FB2FF2B5A8E4A");
			e.setNewMacKey("444CFF6B30F2373522340A7184454DA9");
			return e;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getNumber() {
		double num = Math.random()*100000000;

		return String.valueOf(num);
	}

}
