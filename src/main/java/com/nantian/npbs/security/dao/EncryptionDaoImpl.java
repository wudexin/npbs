/**
 * 
 */
package com.nantian.npbs.security.dao;

import java.io.Serializable;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nantian.npbs.core.orm.impl.GenericHibernateDao;
import com.nantian.npbs.security.model.TbBiEncryption;
import com.nantian.npbs.security.model.TbBiEncryptterminator;

/**
 * 密钥
 * @author HuangBo
 *
 */
@Transactional
@Repository(value = "encryptionDao")
public class EncryptionDaoImpl extends GenericHibernateDao<TbBiEncryption, String> implements
		EncryptionDao {
	@Override
	public boolean persistEncryption(TbBiEncryption encryption) {
		try{
			saveOrUpdate(encryption);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public TbBiEncryption getEncryptionById(String id) {
		try{
			return (TbBiEncryption)get(id);
		}catch (Exception e) {
			return null;
		}
	}
	
//	@Override
//	public boolean persistEncryptTerminator(TbBiEncryptterminator terminator){
//		try{
//			saveOrUpdate(terminator);
//			return true;
//		}catch(Exception e){
//			return false;
//		}
//	}
	
//	@Override
//	public TbBiEncryptterminator getTbBiEncryptterminatorById(String terminatorid){
//		try{
//			Object o = get(terminatorid);
//			if(o != null) {
//				TbBiEncryptterminator t = (TbBiEncryptterminator)o;
//				System.out.println("ttt:"+t.getKeyid());
//				return t;
//			}else{
//				System.out.println("Object is null");
//				return null;
//			}
//		}catch(Exception e){
//			return null;
//		}
//	}
	
//	@Override
//	public TbBiEncryption getEncryptionByTerminator(String terminatorid){
//		TbBiEncryptterminator terminator = getTbBiEncryptterminatorById(terminatorid);
//		if(terminator != null){
//			System.out.println("keyid="+terminator.getKeyid());
//			return getEncryptionById(terminator.getKeyid());
//		}else{
//			return null;
//		}
//	}
//	
	@Override
	public String getNumber() {
		String num = null;
		final String sql = "select KEYSEQ.nextval from dual";

		num = getHibernateTemplate().execute(new HibernateCallback<String>() {
			public String doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.uniqueResult().toString();
			}
		});

		return num;
	}

}
