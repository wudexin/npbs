package com.nantian.nnpbs.services.webservices.client;
 


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.nantian.npbs.services.webservices.WebRequestSvc;
import com.nantian.npbs.services.webservices.models.WebSvcAns;
import com.nantian.npbs.services.webservices.models.WebSvcReq;
 

public class WebTestClientSvc extends Client3_TextCXFAPI{
	private static Logger logger = LoggerFactory.getLogger(WebTestClientSvc.class);
	
	private static WebRequestSvc modelSvc = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelSvc = createService(WebRequestSvc.class);
		replaceClientTimeOut(modelSvc);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testDemoMethod() {
		 logger.info("testDemoMethod begin!");
		 
		WebSvcReq modelSvcReq = new WebSvcReq();
		modelSvcReq.setBusi_code("000001");
		 
		WebSvcAns modelSvcAns = modelSvc.sendToQueue(modelSvcReq);
		System.out.println(modelSvcAns.getMessage());
		logger.info("testDemoMethod end!"); 
		   
	}

}

