package com.nantian.npbs.monitor.service;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.monitor.common.MonitorConstants;
//import com.sun.jmx.mbeanserver.JmxMBeanServer;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-9-21 下午5:10:47
 * 
 */

public class PerformanceMonitor {

	private static final Logger logger = LoggerFactory
			.getLogger(PerformanceMonitor.class);

	public static MBeanServer mbeanServer = null;
	public static ObjectName perf4jMBeanON;

	public static void init() {
		try {
			mbeanServer = ManagementFactory.getPlatformMBeanServer();
			perf4jMBeanON = new ObjectName(MonitorConstants.perf4jMBeanName);
		} catch (MalformedObjectNameException e) {
			logger.error("初始化perf4j JMX失败！", e);
		}

	}

	public static String getValue(String property) {
		try {
			return mbeanServer.getAttribute(PerformanceMonitor.perf4jMBeanON,
					property).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
