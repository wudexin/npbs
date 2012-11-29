package com.nantian.nnpbs.services.webservices.client;
 


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.services.webservices.ModelRequest025001Svc;
import com.nantian.npbs.services.webservices.ModelRequest025002Svc;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
 

public class ModelSvcTest extends Client3_TextCXFAPI{
	private static Logger logger = LoggerFactory.getLogger(ModelSvcTest.class);
	
	private static ModelRequest025002Svc modelSvc = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelSvc = createService(ModelRequest025002Svc.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testDemoMethod() {
		 logger.info("testDemoMethod begin!");
		ModelSvcReq modelSvcReq = new ModelSvcReq();
		modelSvcReq.setBusi_code("025002");
		modelSvcReq.setCompany_code("05008889"); 
		modelSvcReq.setAmount("100");
		modelSvcReq.setFlag("1");
		modelSvcReq.setSystem_code("21");
		modelSvcReq.setWeb_date("20121015");
		modelSvcReq.setWeb_serial("898989");
		ModelSvcAns modelSvcAns = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns.getAcc_balance()+modelSvcAns.getMessage());
		logger.info("testDemoMethod end!"); 
	}

}

