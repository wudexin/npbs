package com.nantian.npbs.test.security;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.security.dao.EncryptionDao;
import com.nantian.npbs.security.model.TbBiEncryption;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class EncyptDaoTest {
	@Resource
	EncryptionDao encryptionDao;
	
	@Test
	public void getEncryptionById() {
		TbBiEncryption encryption=encryptionDao.getEncryptionById("1015");
		if(encryption == null)
			System.out.println("encryption is null");
		else
			System.out.println("KEK:"+encryption.getKek());
	}
	
	@Test
	public void persistEncryption(){
		String id="1018";
		TbBiEncryption encryption = encryptionDao.getEncryptionById(id);
		if(encryption == null){
			encryption = new TbBiEncryption();
			encryption.setKeyID(id);
			encryption.setKek("45719EB1D5F0E4652A8FB2FF2B5A8E4A");
			encryptionDao.persistEncryption(encryption);
		}
		encryptionDao.persistEncryption(encryption);
	}
	


}
