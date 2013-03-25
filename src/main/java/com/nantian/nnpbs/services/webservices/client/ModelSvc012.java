package com.nantian.nnpbs.services.webservices.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.nantian.npbs.services.webservices.ModelRequest025012Svc;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

public class ModelSvc012 extends Client3_TextCXFAPI {
	private static Logger logger = LoggerFactory.getLogger(ModelSvc012.class);

	private static ModelRequest025012Svc modelSvc = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelSvc = createService(ModelRequest025012Svc.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDemoMethod() {
		logger.info("testDemoMethod begin!");
		ModelSvcReq modelSvcReq = new ModelSvcReq();
		modelSvcReq.setOld_pb_serial("13010700247714");
		modelSvcReq.setOld_web_serial("9999999999");
		modelSvcReq.setOld_web_date("20130307");
		modelSvcReq.setOld_trade_date("20130107");
		modelSvcReq.setBusi_code("025012");
		modelSvcReq.setCompany_code_fir("05008888"); 
		modelSvcReq.setAmount("10");
		modelSvcReq.setSystem_code("22");
		modelSvcReq.setWeb_serial("88888888");
		modelSvcReq.setWeb_date("20130307");
		ModelSvcAns modelSvcAns = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns.getStatus()
				+ modelSvcAns.getMessage()+modelSvcAns.getPb_serial()+modelSvcAns.getAmount());		
		logger.info("testDemoMethod end!");
	}

}
