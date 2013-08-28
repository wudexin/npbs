package com.nantian.npbs.services.webservices.sendqueues;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;

import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.services.webservices.models.WebSvcAns;

public class WebCreateResponse {

	public static HashMap<String, String> ha = new HashMap<String, String>();

	public void returnFromQueue(Exchange e) {
		Object body = e.getIn().getBody();
		int i = (body.toString()).indexOf("ORIGREQPACKET=");
		String mapid = body.toString().substring(i + 50, i + 50 + 6);
		ha.put(mapid, body.toString());
	}
}
