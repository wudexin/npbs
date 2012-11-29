package com.nantian.npbs.services.webservices.impl;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.services.webservices.ModelRequest025012Svc;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;
import com.nantian.npbs.services.webservices.sendqueues.PkgSendModel;

/**
 * @author HUBO
 * 
 *         cxf服务实现类
 * 
 */
@Scope("prototype")
@Component(value = "modelRequest025012Svc")
public class ModelRequest025012SvcImpl implements ModelRequest025012Svc {

	private static final Logger logger = LoggerFactory
			.getLogger(ModelRequest025012SvcImpl.class);

	@Override
	public ModelSvcAns sendToQueue(  ModelSvcReq modelSvcReq) {
		logger.info("DemoMethod begin!");
		final ModelSvcAns modelSvcAns = new ModelSvcAns();
		modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
		modelSvcAns.setInstatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
		modelSvcAns.setMessage("初始状态，交易未确定！");
		modelSvcAns.setInmessage("初始状态，交易未确定！");
		if (modelSvcReq == null)
			logger.info("reqPara1={};reqPara2={}", modelSvcReq.getBusi_code(),
					modelSvcReq.getCompany_code());
		else {
			logger.info("Request package is null!");
			// 发送camel包
			
			int i = 0;
			PkgSendModel pm=(PkgSendModel)SpringContextHolder
			 .getBean("pkgSendModel");
			if (pm.sendToQueue(modelSvcReq, modelSvcAns)) {
				Timer t = new Timer();
				while (i < 60) {
					System.out.println(modelSvcAns.getStatus());
					if (modelSvcAns.getStatus().equals(GlobalConst.TRADE_STATUS_SUCCESS)) {
						System.out.println(modelSvcAns.getAcc_balance()
								+ modelSvcAns.getMessage()
								+ modelSvcAns.getStatus());
						break;
					}
					i++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				modelSvcAns.setStatus("01");
				modelSvcAns.setMessage("发送队列失败");
			}
		}
		return modelSvcAns;
	}

}
