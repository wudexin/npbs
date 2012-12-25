/**
 * 
 */
package com.nantian.npbs.security.dao;
import com.nantian.npbs.security.model.TbBiEncryption;

/**
 * 密钥
 * @author HuangBo
 *
 */
public interface EncryptionDao {
	
	/**
	 * 更新或新建一个密钥
	 * @param encryption
	 * @return
	 */
	public abstract boolean persistEncryption(com.nantian.npbs.security.model.TbBiEncryption encryption);
	
	/**
	 * 根据主键获得密钥
	 * @param id
	 * @return
	 */
	public abstract TbBiEncryption getEncryptionById(String id);
	
	
	/**
	 * 取得主鍵
	 * @return String
	 */
	public abstract String getNumber();

}
