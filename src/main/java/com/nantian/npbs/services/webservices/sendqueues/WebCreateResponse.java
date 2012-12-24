package com.nantian.npbs.services.webservices.sendqueues;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;

import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.services.webservices.models.WebSvcAns;

public class WebCreateResponse {

	public static HashMap<String, WebSvcAns> ha = new HashMap<String, WebSvcAns>();

	public void returnFromQueue(Exchange e) {
		WebSvcAns webnodelSvcAns = (WebSvcAns) e.getIn().getBody();
		if (webnodelSvcAns.getStatus().equals("000000")) {
			webnodelSvcAns.setTotalStatus("00");
			webnodelSvcAns.setStatus("00");
		}else if (webnodelSvcAns.getStatus().equals("000001")){
			webnodelSvcAns.setTotalStatus("01");	
			webnodelSvcAns.setStatus("01");
		}else if(webnodelSvcAns.getStatus().equals("99")){
			webnodelSvcAns.setTotalStatus("99");	
			webnodelSvcAns.setStatus("99");
		}
		ha.put(webnodelSvcAns.getStrTest(), webnodelSvcAns);
	}
}
