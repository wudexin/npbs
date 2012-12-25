/**
 * 
 */
package com.nantian.npbs.security.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nantian.npbs.core.orm.impl.GenericHibernateDao;
import com.nantian.npbs.security.model.TbBiEncryptterminator;

/**
 * 终端密钥
 * @author HuangBo
 *
 */
@Transactional
@Repository(value = "terminatorDao")
public class EncryptionTerminatorDaoImpl extends GenericHibernateDao<TbBiEncryptterminator, String> implements
EncryptionTerminatorDao {
	@Override
	public boolean persistEncryptTerminator(TbBiEncryptterminator terminator) {
		try{
			saveOrUpdate(terminator);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public TbBiEncryptterminator getTbBiEncryptterminatorById(String terminatorid) {
		try{
			return (TbBiEncryptterminator)get(terminatorid);
		}catch (Exception e) {
			return null;
		}
	}

}
