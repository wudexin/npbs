/**
 * 
 */
package com.nantian.npbs.security.dao;

import com.nantian.npbs.security.model.TbBiEncryptterminator;

/**
 * @author HuangBo
 *
 */
public interface EncryptionTerminatorDao {
	
	/**
	 * 更新或新建一个终端密钥
	 * @param terminator
	 * @return
	 */
	public abstract boolean persistEncryptTerminator(TbBiEncryptterminator terminator);
		
	/**
	 * 根据终端ID获得终端密钥
	 * @param terminatorid
	 * @return
	 */
	public abstract TbBiEncryptterminator getTbBiEncryptterminatorById(String terminatorid);

}
