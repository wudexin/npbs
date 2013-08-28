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
//		  modelSvcReq.setWebReqStr("000145700000090396Z06888            016704                                                                                                                                                      ");
//		  modelSvcReq.setWebReqStr("001183700001800196Z06888            000879                    00000020130409                                                                            0008017458          CNY0008017458|02||FK|||370|0001||||130510093710|1||||370.00||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||                                                                                                                                                                                                                                                                                                                                                                                                                                102                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ");
//		  modelSvcReq.setWebReqStr("000184700000100196Z06888            009129                                                                                                              18203243587         CNYB22FAFFF3FDBEF3C");
//		  modelSvcReq.setWebReqStr("001201700001800105002027            000957                    00000020130409                                                                                                                    0008017458|02||FK|||370|0001||||130510093710|1||||370.00||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||                                                                                                                                                                                                                                                                                                                                                                                                0008017458      0008017458      102                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ");
//		  modelSvcReq.setWebReqStr("000246700001800405002027            000954                    00000020130409                                                                                                                    05497956        130410002596  13041000                      ");
//		  modelSvcReq.setWebReqStr("000246700001800405002027            000962                    00000020130409                                                                                                                    05497956        130410002596  13041000                      ");
//		  modelSvcReq.setWebReqStr("000346700001800205002027            000970                    00000020130409                                                                                                                    f85c21f6dd3b202e001CNY1000        0549795636          刘志强                                                      0549795636      13041000259674                ");
//		  modelSvcReq.setWebReqStr("000246700001800405002027            001016                    00000020130409                                                                                                                    0549787266      1305310025972013053100259721                ");
//		  modelSvcReq.setWebReqStr("000208700000001005002027            001115                    00000020130409                                                                                                                    000549                ");
//		  modelSvcReq.setWebReqStr("000725700000000605002027            001845                    16175620130627                                                                                                                    21                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              CNY20130531                ");
		  modelSvcReq.setWebReqStr("000766700000090605002027            002034                    09511120130628                                                                                                                    00090605002027            002034                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ");
		String modelSvcAns = modelSvc.sendToQueue(modelSvcReq.getWebReqStr());
		System.out.println(modelSvcAns);
		logger.info("testDemoMethod end!"); 
		   
	}

}

