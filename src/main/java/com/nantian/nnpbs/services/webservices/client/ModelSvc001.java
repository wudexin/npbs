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
 

public class ModelSvc001 extends Client3_TextCXFAPI{
	private static Logger logger = LoggerFactory.getLogger(ModelSvc001.class);
	
	private static ModelRequest025001Svc modelSvc = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelSvc = createService(ModelRequest025001Svc.class);
		replaceClientTimeOut(modelSvc);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testDemoMethod() {
		 logger.info("testDemoMethod begin!");
		 
		ModelSvcReq modelSvcReq = new ModelSvcReq();
		modelSvcReq.setBusi_code("025001");
		modelSvcReq.setCompany_code_fir("05008895"); 
		ModelSvcAns modelSvcAns = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns.getAcc_balance_fir()+modelSvcAns.getMessage());
		logger.info("testDemoMethod end!"); 
		
		ModelSvcReq modelSvcReq1 = new ModelSvcReq();
		modelSvcReq1.setBusi_code("025001");
		modelSvcReq1.setCompany_code_fir("05008895"); 
		ModelSvcAns modelSvcAns1 = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns1.getAcc_balance_fir()+modelSvcAns1.getMessage());
		logger.info("testDemoMethod end!"); 
	}

}

