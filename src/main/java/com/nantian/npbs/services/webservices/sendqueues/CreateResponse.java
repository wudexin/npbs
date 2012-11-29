package com.nantian.npbs.services.webservices.sendqueues;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;

import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;

public class CreateResponse {

	public static HashMap<String, ModelSvcAns> ha = new HashMap<String, ModelSvcAns>();

	public void returnFromQueue(Exchange e) {
		ModelSvcAns nodelSvcAns = (ModelSvcAns) e.getIn().getBody();
		if (nodelSvcAns.getStatus().equals("000000")) {
			nodelSvcAns.setTotalStatus("00");
			nodelSvcAns.setStatus("00");
		}else if (nodelSvcAns.getStatus().equals("000001")){
			nodelSvcAns.setTotalStatus("01");	
			nodelSvcAns.setStatus("01");
		}else if(nodelSvcAns.getStatus().equals("99")){
			nodelSvcAns.setTotalStatus("99");	
			nodelSvcAns.setStatus("99");
		}
		ha.put(nodelSvcAns.getStrTest(), nodelSvcAns);
	}
}
