package com.nantian.npbs.web.listener;

import static com.nantian.npbs.common.GlobalConst.HOST_ADDRESS;
import static com.nantian.npbs.common.GlobalConst.WEB_INF_PATH;

import java.io.IOException;

import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.PropertyUtils;
import com.nantian.npbs.monitor.service.PerformanceMonitor;
import com.nantian.npbs.web.WebUtils;

public class NpbsContextLoaderListener extends ContextLoaderListener{
	private static Logger logger = LoggerFactory.getLogger(NpbsContextLoaderListener.class);
	@Override
	public void contextInitialized(ServletContextEvent event) {
		loadServerConfig(event);
		super.contextInitialized(event);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

	/**
	 * 系统初始化方法
	 * 
	 * @throws Exception
	 */
	private void loadServerConfig(ServletContextEvent event){
		logger.info("Loading system configuration...");
		String appHome = System.getenv("ECFSVC_APP_HOME");
		if(StringUtils.isEmpty(appHome)){
			appHome = event.getServletContext().getInitParameter("ECFSVC_APP_HOME");
		}
		if(StringUtils.isEmpty(appHome))
//			throw new ECFSVCHomeNotFoundException("can not found the ECFSVC_APP_HOME!");
		try {
			DynamicConst.ConfigDir = appHome+java.io.File.separator+"Config";//new File("../../Config").getCanonicalPath();
			PropertyUtils.loadProperties(DynamicConst.ConfigDir+java.io.File.separator+"config.properties");
			
			DynamicConst.AppHome = appHome;
			DynamicConst.SERVER_TYPE = PropertyUtils.getString("ServerType");
			DynamicConst.ServiceAddress = PropertyUtils.getString("ServiceAddress");

			logger.info("服务器类型：" + DynamicConst.SERVER_TYPE);
			logger.info("服务器运行环境地址：" + DynamicConst.AppHome);

			//获取本地服务配置
			DynamicConst.LocalPostServiceUrl = PropertyUtils.getString("LocalPostServiceUrl");
			DynamicConst.LocalGetServiceUrl	= PropertyUtils.getString("LocalGetServiceUrl");
			DynamicConst.LocalGetFileServiceUrl  = PropertyUtils.getString("LocalGetFileServiceUrl");
			DynamicConst.LocalPostFileServiceUrl = PropertyUtils.getString("LocalPostFileServiceUrl");
			DynamicConst.LocalGetPath = PropertyUtils.getString("LocalGetPath");;
			DynamicConst.LocalPostPath = PropertyUtils.getString("LocalPostPath");;
			
			logger.info("服务器类型：" + DynamicConst.SERVER_TYPE);
			logger.info("本地提交File POST服务地址：" + DynamicConst.LocalPostFileServiceUrl);
			logger.info("本地获取File Get服务地址：" + DynamicConst.LocalGetFileServiceUrl);
			logger.info("本地提交POST服务地址：" + DynamicConst.LocalPostServiceUrl);
			logger.info("本地获取Get服务地址：" + DynamicConst.LocalGetServiceUrl);
			
			if (DynamicConst.SERVER_TYPE.equals(DynamicConst.SERVER_TYPE_CENTRAL)) {
				//logger.info("创建NantianDFC实例...");
				//initNantianDFC();
			}else{
				//获取总行前置服务URL
				DynamicConst.UpperPostServiceUrl = PropertyUtils.getString("UpperPostServiceUrl");
				DynamicConst.UpperGetServiceUrl  = PropertyUtils.getString("UpperGetServiceUrl");
				DynamicConst.UpperPostFileServiceUrl = PropertyUtils.getString("UpperPostFileServiceUrl");
				DynamicConst.UpperGetFileServiceUrl  = PropertyUtils.getString("UpperGetFileServiceUrl");
				

				logger.info("上级提交File POST服务地址：" + DynamicConst.UpperPostFileServiceUrl);
				logger.info("上级获取File Get服务地址：" + DynamicConst.UpperGetFileServiceUrl);
				logger.info("上级提交POST服务地址：" + DynamicConst.UpperPostServiceUrl);
				logger.info("上级获取Get服务地址：" + DynamicConst.UpperGetServiceUrl);
			}
		} catch (IOException e) {
//			throw new LoadPropertiesFileException("加载配置文件失败",e);
		} catch (Exception e) {
//			throw new InitNantianDFCException("初始化nantian DFC失败",e);
		}
		WEB_INF_PATH = event.getServletContext().getRealPath("/WEB-INF");
		HOST_ADDRESS = WebUtils.getAddress();
		logger.info("本机地址：" + GlobalConst.HOST_ADDRESS);
		//AppName = "NTSECF";

		//初始化性能监控
		PerformanceMonitor.init();

		logger.info("系统初始化成功。");
	}
}
