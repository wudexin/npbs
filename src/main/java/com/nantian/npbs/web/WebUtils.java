package com.nantian.npbs.web;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getHostName() {
		String hostname = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
		}
		return hostname;
	}

	public static String getAddress() {
		String hostAddress = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostAddress = addr.getHostAddress();
		} catch (UnknownHostException e) {
		}
		return hostAddress;
	}

}
