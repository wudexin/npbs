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
//		modelSvcReq.setWebReqStr("000145700000090396Z06888           016704                                                                                                              ");
		
//		modelSvcReq.setWebReqStr("000184700000100196Z06888           009129                                                                                                              18203243587         CNYB22FAFFF3FDBEF3C");
		modelSvcReq.setWebReqStr("0002757000001002A7D00861           009130                                  13011131176138                                                              75B1FA4C31CDFBC0  1CNY00000000300018203243587         *庆*                                                        D07547A34AF23B11");
		String modelSvcAns = modelSvc.sendToQueue(modelSvcReq.getWebReqStr());
		System.out.println(modelSvcAns);
		logger.info("testDemoMethod end!"); 
		   
	}

}

