package com.nantian.npbs.gateway.wtc;

import javax.naming.Context;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weblogic.jndi.Environment;
import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoConnectionFactory;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.TypedCArray;
import weblogic.wtc.jatmi.TypedString;

public class CallWTCSvc {
	private static Logger logger = LoggerFactory.getLogger(CallWTCSvc.class);

	public TypedBuffer callSvc(Object requestPkg, String serviceName)
			throws Exception {
		Context ctx;
		TuxedoConnectionFactory tcf;
		TuxedoConnection myTux;
		byte[] responseData;
		Reply myRtn;// WTC调用Tuxedo时返回的对象类型为Reply
		Environment enviaronment = new Environment();

		try {
			ctx = enviaronment.getInitialContext();// 获得context实例
			tcf = (TuxedoConnectionFactory) ctx
					.lookup("tuxedo/services/TuxedoConnection");
			// 通过jndi查找java应用服务，其中tuxedo.services.TuxedoConnection
			// 是weblogic wtc提供的jndi节点名称
		} catch (NamingException ne) {
			// Could not get the tuxedo object, throw TPENOENT
			throw new TPException(TPException.TPENOENT,
					"Could not get TuxedoConnectionFactory : " + ne);
		}
		// Get TuxedoConnection by TuxedoConnectionFactory
		myTux = tcf.getTuxedoConnection();
		try {
			// Using WTC service receive Reply by TuxedoConnection
			TypedString requestPkgString = (TypedString) requestPkg;
			String requestString = requestPkgString.getStringBuffer()
					.toString();
			logger.info("sendMessage:Object :[{}]" ,requestString);
			// carrayData = requestString.getBytes();
			// TypedCArray requestPkgCArray = new
			// TypedCArray(carrayData.length);
			// requestPkgCArray.carray = carrayData;

			// myRtn = myTux.tpcall(serviceName, (TypedBuffer) requestPkgCArray,
			// ApplicationToMonitorInterface.TPNOTRAN);
			myRtn = myTux.tpcall(serviceName, (TypedBuffer) requestPkg, 0);
		} catch (Exception e) {
			logger.error("WTC调用失败：", e);
			if (e instanceof TPException)
				throw new Exception(TPException.tpstrerror(((TPException) e)
						.gettperrno()));
			else
				throw new Exception(e.getMessage());
		}
		logger.info("tpcall successfull!");

		TypedBuffer retBuffer = myRtn.getReplyBuffer();
		logger.info("电子商务平台返回类型：{}" ,retBuffer.getType());
		if (retBuffer.getType().equals("STRING")) {
			return retBuffer;
		}
		// change TypedCArray to TypedString
		TypedCArray responsePkg = (TypedCArray) retBuffer;
		responseData = responsePkg.carray;
		String responseDataString = new String(responseData);
		logger.info("电子商务平台返回报文类型：{}" , retBuffer.getType());
		logger.info("电子商务平台返回报文：{}" , responseDataString);
		TypedString responsePkgString = new TypedString(responseDataString);

		myTux.tpterm(); // Closing the association with Tuxedo
		return responsePkgString;
	}

}
