package com.nantian.npbs.services.webservices;

import javax.jws.WebService;

import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;



/**
 * @author HUBO
 * 
 * cxf服务接口类
 *
 */
@WebService
public interface ModelSvc {
	/**
	 * demo测试方法
	 * 
	 * @param modelSvcReq
	 * @return
	 */
	public ModelSvcAns demoMethod(ModelSvcReq modelSvcReq);
}
