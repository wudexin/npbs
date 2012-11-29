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
		modelSvcReq.setOld_pb_serial("12111800244127");
		modelSvcReq.setOld_web_serial("201");
		modelSvcReq.setOld_web_date("20121113");
		modelSvcReq.setOld_trade_date("20121118");
		modelSvcReq.setBusi_code("025012");
		modelSvcReq.setCompany_code_fir("05008888"); 
		modelSvcReq.setAmount("100.01");
		modelSvcReq.setSystem_code("22");
		modelSvcReq.setWeb_serial("208");
		modelSvcReq.setWeb_date("20121118");
		ModelSvcAns modelSvcAns = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns.getStatus()
				+ modelSvcAns.getMessage()+modelSvcAns.getPb_serial()+modelSvcAns.getAmount());		
		logger.info("testDemoMethod end!");
	}

}
