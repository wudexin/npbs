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
import com.nantian.npbs.services.webservices.sendqueues.CreateResponse;
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
	public ModelSvcAns sendToQueue(ModelSvcReq modelSvcReq) {
		logger.info("DemoMethod begin!");
		final ModelSvcAns modelSvcAns = new ModelSvcAns();
		 ModelSvcAns modelSvcAns1=null;
		modelSvcAns.setStatus(GlobalConst.RESULTCODE_SUCCESS); 
		modelSvcAns.setTotalStatus("99"); 
		modelSvcAns.setMessage("初始状态，交易未确定！"); 
		if (modelSvcReq == null){
			logger.info("reqPara1={};reqPara2={}", modelSvcReq.getBusi_code(),
					modelSvcReq.getCompany_code_fir());
		return modelSvcAns;}
		else {
			// 发送camel包
			if (modelSvcReq.getCompany_code_fir() == null) {
				modelSvcAns.setMessage("商户号不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			} 
			else if (modelSvcReq.getOld_pb_serial() == null) {
				modelSvcAns.setMessage("原冲正流水号不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}
			else if (modelSvcReq.getOld_web_date() == null) {
				modelSvcAns.setMessage("原冲正日期不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}	else if (modelSvcReq.getOld_trade_date() == null) {
				modelSvcAns.setMessage("原冲正日期不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}
			else if (modelSvcReq.getOld_web_serial() == null) {
				modelSvcAns.setMessage("原冲正流水号不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}
			else if (modelSvcReq.getAmount() == null) {
				modelSvcAns.setMessage("冲正金额不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}
			else if (modelSvcReq.getAmount() == null) {
				modelSvcAns.setMessage("冲正金额不能为空！");
				modelSvcAns.setStatus("01");
				return modelSvcAns;
			}else if (modelSvcReq.getWeb_serial() == null) {
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
			}else 
			
			if(modelSvcReq.getSystem_code().equals("28")){
				if (modelSvcReq.getCompany_code_sec() == null) {
					modelSvcAns.setMessage("转入商户不能为空！");
					modelSvcAns.setStatus("01");
					return modelSvcAns;
				}
			}
			 
			int i = 0;
			PkgSendModel pm = (PkgSendModel) SpringContextHolder
					.getBean("pkgSendModel");
			if (pm.sendToQueue(modelSvcReq, modelSvcAns)) {
			} else {
				modelSvcAns.setStatus("01");
				modelSvcAns.setMessage("发送队列失败");
				return modelSvcAns;
			}
			CreateResponse bean = SpringContextHolder.getBean("createResponse");
			//在返回的数据里找数据，直到找到为止
			while (i < 60) { 
				  modelSvcAns1 =(ModelSvcAns)	bean.ha.get(modelSvcReq.getWeb_date()+""+modelSvcReq.getWeb_serial());
				i++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(null!=modelSvcAns1){
					bean.ha.remove(modelSvcReq.getWeb_date()+""+modelSvcReq.getWeb_serial());
					break;
				}
			}
		}
		return modelSvcAns1;
	}
}
