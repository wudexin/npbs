package com.nantian.npbs.test.security;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.nantian.npbs.security.service.EncryptionService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:appCtx.xml"})
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class EncyptionServiceTest{
	@Resource
	EncryptionService encryptionService;
	
	/*@Test
	public void generateKEK() {
		Assert.assertEquals(true,encryptionService.generateKEK("10010"));
	}
	
	@Test
	public void generatePIN(){
		System.out.println(encryptionService.generatePINKey("10010"));
	}
	
	@Test
	public void generateMAC(){
		Assert.assertEquals(true,encryptionService.generatorMACKey("10010"));
	}
	*/
	/*@Test
	public void generatorAll(){
		Assert.assertEquals(true,encryptionService.generatorAllKey("05000001"));
	}*/
	
	@Test
	public void getWorkKey(){
		Assert.assertEquals(true,encryptionService.getWorkKey("05000001"));
	}

}
