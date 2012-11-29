package com.nantian.npbs.services.webservices.impl;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.services.webservices.ModelRequest025002Svc;
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
@Component(value = "modelRequest025002Svc")
public class ModelRequest025002SvcImpl implements ModelRequest025002Svc {

	private static final Logger logger = LoggerFactory
			.getLogger(ModelRequest025002SvcImpl.class);

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
			if (modelSvcReq.getCompany_code() == null) {
				modelSvcAns.setMessage("商户号不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			} else if (modelSvcReq.getBusi_code() == null) {
				modelSvcAns.setMessage("交易码不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			} else if (modelSvcReq.getAmount() == null) {
				modelSvcAns.setMessage("金额不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			} else if (modelSvcReq.getWeb_serial() == null) {
				modelSvcAns.setMessage("交易流水不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}else if (modelSvcReq.getWeb_date() == null) {
				modelSvcAns.setMessage("交易日期不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}else if (modelSvcReq.getSystem_code() == null) {
				modelSvcAns.setMessage("交易类型不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}
			if(modelSvcReq.getSystem_code().equals("28")){
				if (modelSvcReq.getCompany_code_sec() == null) {
					modelSvcAns.setMessage("转入商户号不能为空！");
					modelSvcAns.setStatus("01");
					return modelSvcAns;
				}	
			}
			
			// 发送camel包
			int i = 0;
			PkgSendModel pm=(PkgSendModel)SpringContextHolder .getBean("pkgSendModel");
			
			if (pm.sendToQueue(modelSvcReq, modelSvcAns)) {
				Timer t = new Timer();
				while (i < 60) {
					System.out.println(modelSvcAns.getInstatus());
					if (modelSvcAns.getInstatus().equals(
							GlobalConst.TRADE_STATUS_SUCCESS)) {
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
		logger.info("DemoMethod end!");
		return modelSvcAns;
	}

}
