package com.nantian.npbs.services.webservices.impl;

import com.nantian.npbs.services.webservices.WebRequestSvc;
import com.nantian.npbs.services.webservices.models.WebSvcAns;
import com.nantian.npbs.services.webservices.models.WebSvcReq;

import java.util.HashMap;
import java.util.Timer;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.services.webservices.ModelRequest025001Svc;
import com.nantian.npbs.services.webservices.models.WebSvcAns;
import com.nantian.npbs.services.webservices.models.WebSvcReq;
import com.nantian.npbs.services.webservices.sendqueues.CreateResponse;
import com.nantian.npbs.services.webservices.sendqueues.PkgSendModel;
import com.nantian.npbs.services.webservices.sendqueues.WebCreateResponse;
import com.nantian.npbs.services.webservices.sendqueues.WebSendModel;

/**
 * @author wdx
 * 
 *         cxf服务实现类
 * 
 */
@Scope("prototype")
@Component(value = "webRequestSvc")
public class WebRequestSvcImpl implements WebRequestSvc {
	private static final Logger logger = LoggerFactory
			.getLogger(WebRequestSvcImpl.class);

	@SuppressWarnings("null")
	@Override
	public String sendToQueue(String webSvcReq) {
		logger.info("DemoMethod begin!");
		/*WebSvcAns webSvcAns = new WebSvcAns();
		WebSvcAns webSvcAns1=new WebSvcAns();
		webSvcAns1.setMessage("test");
		webSvcAns.setWebAnsStr(GlobalConst.RESULTCODE_SUCCESS);*/
		String webSvcAns1="";
		String webSvcAns="";
		if (webSvcReq == null) {
			
			logger.info("Request package is null!");
			return "01";
		} else {
			
			// 发送camel包
			int i = 0;
			WebSendModel pm=(WebSendModel)SpringContextHolder.getBean("webSendModel");
			if (pm.webSendToQueue(webSvcReq, webSvcAns)) {
			} else {
				webSvcAns="02";
				
				return "03";
			}
			WebCreateResponse bean = SpringContextHolder.getBean("webcreateResponse");
			//在返回的数据里找数据，直到找到为止
			while (i < 60) { 
				  String st =(String)	bean.ha.get(webSvcReq.substring(35, 41));
				i++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(null!=st){
					bean.ha.remove(webSvcReq.substring(35, 41));
					webSvcAns1=st;
					break;
				}
			}
		}
	
		logger.info("DemoMethod end!");
		return webSvcAns1;
	}

		
		
		
}


