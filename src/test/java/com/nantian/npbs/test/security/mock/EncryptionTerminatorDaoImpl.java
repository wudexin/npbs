/**
 * 
 */
package com.nantian.npbs.test.security.mock;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.core.orm.impl.GenericHibernateDao;
import com.nantian.npbs.security.dao.EncryptionTerminatorDao;
import com.nantian.npbs.security.model.TbBiEncryptterminator;

/**
 * 终端密钥
 * @author HuangBo
 *
 */
@Transactional
@Repository(value = "terminatorDao")
public class EncryptionTerminatorDaoImpl implements
EncryptionTerminatorDao {
	@Override
	public boolean persistEncryptTerminator(TbBiEncryptterminator terminator) {
		try{
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public TbBiEncryptterminator getTbBiEncryptterminatorById(String terminatorid) {
		try{
			TbBiEncryptterminator e = new TbBiEncryptterminator();
			e.setTerminatorid(terminatorid);
			return e;
		}catch (Exception e) {
			return null;
		}
	}

}
