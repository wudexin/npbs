package com.nantian.npbs.services.webservices.impl;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.services.webservices.ModelRequest025001Svc;
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
@Component(value = "modelRequest025001Svc")
public class ModelRequest025001SvcImpl implements ModelRequest025001Svc {

	private static final Logger logger = LoggerFactory
			.getLogger(ModelRequest025001SvcImpl.class);

	@Override
	public ModelSvcAns sendToQueue(  ModelSvcReq modelSvcReq) {
		logger.info("DemoMethod begin!");
		final ModelSvcAns modelSvcAns = new ModelSvcAns();
		modelSvcAns.setStatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
		modelSvcAns.setInstatus(GlobalConst.TRADE_STATUS_CARD_ORIG);
		modelSvcAns.setMessage("初始状态，交易未确定！");
		modelSvcAns.setInmessage("初始状态，交易未确定！");
		if (modelSvcReq == null) {
			logger.info("reqPara1={};reqPara2={}", modelSvcReq.getBusi_code(),
					modelSvcReq.getCompany_code());
			logger.info("Request package is null!");
		} else {
			// 判断必须数据是否为空，如必须数据为空则不发送队列
			if (modelSvcReq.getBusi_code() == null) {
				modelSvcAns.setMessage("商户号不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			} else if (modelSvcReq.getBusi_code() == null) {
				modelSvcAns.setMessage("交易码不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}

			// 发送camel包
			int i = 0;
			PkgSendModel pm=(PkgSendModel)SpringContextHolder.getBean("pkgSendModel");
			if (pm.sendToQueue(modelSvcReq, modelSvcAns)) {
				Timer t = new Timer();
				while (i < 60) {
					System.out.println(modelSvcAns.getStatus());
					if (modelSvcAns.getStatus().equals(
							GlobalConst.TRADE_STATUS_SUCCESS)) {
						System.out.println(modelSvcAns.getAcc_balance()
								+ modelSvcAns.getMessage()
								+ modelSvcAns.getStatus());
						break;
					}
					i++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!modelSvcAns.getStatus().equals(
						GlobalConst.TRADE_STATUS_SUCCESS)) {
					modelSvcAns.setStatus("01");
					modelSvcAns.setMessage("发送超时");
				}
			} else {
				modelSvcAns.setStatus("01");
				modelSvcAns.setMessage("发送队列失败");
			}
		}
		logger.info("DemoMethod end!");
		return modelSvcAns;
	}

}
