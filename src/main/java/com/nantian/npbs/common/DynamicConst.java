package com.nantian.npbs.common;

public class DynamicConst {

	public static final String SERVER_TYPE_CACHE = "CACHE";

	public static final String SERVER_TYPE_CENTRAL = "CENTRAL";

	public static String SERVER_TYPE = SERVER_TYPE_CACHE; // 真实值从配置文件中读取，默认为缓存

	public static String AppHome = null;

	public static String ServiceAddress = null;

	public static String PostServiceUrl = null;

	public static String GetServiceUrl = null;

	public static String ConfigDir = null;

	// upper center server
	public static String UpperPostServiceUrl = null;
	public static String UpperGetServiceUrl = null;
	public static String UpperPostFileServiceUrl = null;
	public static String UpperGetFileServiceUrl = null;
	// local cached server
	public static String LocalPostServiceUrl = null;
	public static String LocalGetServiceUrl = null;
	public static String LocalGetFileServiceUrl = null;
	public static String LocalPostFileServiceUrl = null;
	public static String LocalGetPath = null;
	public static String LocalPostPath = null;

	// 使用独立队列的服务，配置中的交易将使用独立队列，现为缺省值，实际运行时将从配置文件中读取，
	// 不在此列表中的交易将使用缺省路由
	public static String[] SERVICESEDALIST = { "014" };

	public static final String PACKETCHARSET = "GB2312";
}
