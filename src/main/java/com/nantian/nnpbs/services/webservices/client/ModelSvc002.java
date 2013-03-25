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
 

public class ModelSvc002 extends Client3_TextCXFAPI{
	private static Logger logger = LoggerFactory.getLogger(ModelSvc002.class);
	
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
		modelSvcReq.setCompany_code_fir("05008888");  
		modelSvcReq.setAmount(new String(""+(Double.parseDouble("10") )));
		modelSvcReq.setFlag("1");
		modelSvcReq.setSystem_code("21");
		modelSvcReq.setWeb_date("20130307");
		modelSvcReq.setWeb_serial("9999999999");
		ModelSvcAns modelSvcAns = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns.getStatus()+modelSvcAns.getMessage()
				+modelSvcAns.getPb_serial()+modelSvcAns.getAcc_balance_fir());
	 
	 
	 logger.info("testDemoMethod end!"); }
}

