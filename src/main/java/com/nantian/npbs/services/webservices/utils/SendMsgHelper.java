package com.nantian.npbs.services.webservices.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

 	public class SendMsgHelper {
		public static void packPkgHeader(ModelSvcReq demoSvcReq, Map<String, Object> headers) {
			headers.put("system_date", new SimpleDateFormat("yyyyMMdd24HHmmss").format(new Date()));
			headers.put("pb_serial", "0000000000");
		}
	}

 
