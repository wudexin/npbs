package com.nantian.npbs.services.webservices;

import javax.jws.WebService;

import com.nantian.npbs.services.webservices.models.WebSvcAns;
import com.nantian.npbs.services.webservices.models.WebSvcReq;



/**
 * @author HUBO
 * 
 * cxf服务接口类
 *
 */
@WebService
public interface WebRequestSvc {
	/**
	 * demo测试方法
	 * 
	 * @param modelSvcReq
	 * @return
	 */
	public WebSvcAns sendToQueue(WebSvcReq webSvcReq);
}
