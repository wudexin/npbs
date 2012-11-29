package com.nantian.npbs.services.webservices.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.services.webservices.ModelSvc;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
import com.nantian.npbs.services.webservices.sendqueues.PkgSendModel;

/**
 * @author HUBO
 * 
 * cxf服务实现类
 *
 */
@Scope("prototype")
@Component(value = "modelSvc")
public class ModelSvcImpl implements ModelSvc {

	private static final Logger logger = LoggerFactory.getLogger(ModelSvcImpl.class);
	@Override
	public ModelSvcAns demoMethod(ModelSvcReq modelSvcReq) {
		logger.info("DemoMethod begin!");
		 if(modelSvcReq!=null)
			 logger.info("reqPara1={};reqPara2={}",modelSvcReq.getBusi_code(),modelSvcReq.getCompany_code());
		 else
			 logger.info("Request package is null!");
		//发送camel包
		ModelSvcAns demoSvcAns = new ModelSvcAns();
		 if(!new PkgSendModel().sendToQueue(modelSvcReq,demoSvcAns)){
			 
			return demoSvcAns;
		} 
		 
		logger.info("DemoMethod end!");
		return demoSvcAns;
	}
 
}
