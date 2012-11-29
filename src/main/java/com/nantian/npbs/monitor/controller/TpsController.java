package com.nantian.npbs.monitor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nantian.npbs.monitor.common.MonitorConstants;
import com.nantian.npbs.monitor.service.PerformanceMonitor;

/**
 * @author 7tianle
 * @since 2011-09-20
 */
@Controller
@RequestMapping("/monitor/tps")
public class TpsController {
	
	private static Logger logger = LoggerFactory.getLogger(TpsController.class);
	
	/**
	 * 获取所有的方法名以及x轴、y轴坐标
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/meta")
	public void meta(HttpServletRequest request, HttpServletResponse response)  {
		
		StringBuffer codeBuf = new StringBuffer();
		String objectStr = "";
		try {
					// 初始化
					if(PerformanceMonitor.mbeanServer ==null){
						PerformanceMonitor.init();
					}
					// 获取所有监控属性的数组
					MBeanAttributeInfo[] mi = PerformanceMonitor.mbeanServer
							.getMBeanInfo(PerformanceMonitor.perf4jMBeanON)
							.getAttributes();

					// 获取并显示属性值
					for (int i = 0; i < mi.length; i++) {
						// 监控TPS
						if(mi[i].getName().endsWith(MonitorConstants.MONITOR_TPS)){
							String cnName = "";
							if(mi[i].getName().contains("RequestProcessor")){
								cnName = mi[i].getName().replace("RequestProcessor", "请求处理");
							}
							if(mi[i].getName().contains("AnswerProcessor")){
								cnName = mi[i].getName().replace("AnswerProcessor", "应答处理");
							}
							codeBuf.append("{ \"name\": \"" + cnName + "\",\"code\":\"" + mi[i].getName() + "\"},");
						}
					}

					codeBuf.deleteCharAt(codeBuf.length() - 1);
					objectStr =   "{\"success\":true,\"data\":{\"method\":[{ \"name\": \"全部\",\"code\":\"all\"}," 
							+ codeBuf .toString()+ "],\"y\":\"笔数\",\"x\":\"时间\"}}";
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("jmx mbean 出错(TpsController : meta)!",e);
					objectStr = "{\"success\":false}";
				}
				commonMethod(request,response,objectStr);
	}
	
	/**
	 * 根据方法名获取该方法的值
	 * @param methodName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/data/{methodName}")
	public void data(@PathVariable("methodName") String methodName,HttpServletRequest request, HttpServletResponse response){
		
			DateFormat format = new SimpleDateFormat("HH:mm:ss");
			Calendar now = Calendar.getInstance();
			String objectStr = "";
			try {
				// 初始化
				if(PerformanceMonitor.mbeanServer ==null){
					PerformanceMonitor.init();
				}
				String value = "";
				if(methodName.equals("all")){
					value = getAllValue();
				}else{
					value = PerformanceMonitor.getValue(methodName);
				}
				objectStr =   "{\"success\":true,\"data\":[{ \"name\": \"" + methodName + "\",\"y\":\"" +value + "\","
						+ "\"x\":\"" + format.format(now.getTime()) + "\"}]}";
			} catch (Exception e) {
				logger.error("jmx mbean 出错(TpsController : meta)!");
				objectStr = "{\"success\":false}";
			}
			commonMethod(request,response,objectStr);
				
	}
	
	/**
	 * 获取所有值
	 * @return
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 */
	private String getAllValue() throws InstanceNotFoundException, IntrospectionException, ReflectionException{
		Double num = 0.0;
		MBeanAttributeInfo[] mi = PerformanceMonitor.mbeanServer
				.getMBeanInfo(PerformanceMonitor.perf4jMBeanON).getAttributes();
		// 获取并显示属性值
		for (int i = 0; i < mi.length; i++) {
			// 监控TPS
			if(mi[i].getName().endsWith(MonitorConstants.MONITOR_TPS)){
				if(PerformanceMonitor.getValue(mi[i].getName()) !=null){
					num += Double.parseDouble(PerformanceMonitor.getValue(mi[i].getName()));
				}
			}
		}
		return num.toString();
	}
	/**
	 * 公共方法
	 * @param request
	 * @param response
	 * @param objectStr
	 */
	public void commonMethod(HttpServletRequest request, HttpServletResponse response,String objectStr){
		String jsontest = "";
		boolean flag = false;
		String cbStr = request.getParameter("callback");
		
		if(cbStr != null){
				flag = true;
				response.setContentType("text/javascript;charset=UTF-8");
				jsontest += cbStr+"(";
		}else{
				response.setContentType("application/x-json;charset=UTF-8");
		}
		jsontest += objectStr;
		if(flag){
				jsontest += ");";
		}
		
		try{
				PrintWriter out = response.getWriter();
				out.print(jsontest);
				out.flush();
		} catch (IOException e) {
			logger.error("获取meta出错(TpsController : meta)!");
		}
	}
	
	@RequestMapping(value = "/test/meta")
	public void testMeta(HttpServletRequest request, HttpServletResponse response)  {
		try{
			String jsontest = "";
			boolean flag = false;
				String cbStr = request.getParameter("callback");
				if(cbStr != null){
					flag = true;
					response.setContentType("text/javascript;charset=UTF-8");
					jsontest += cbStr+"(";
				}else{
					response.setContentType("application/x-json;charset=UTF-8");
				}
				jsontest +=  "{\"data\":{\"method\":[{\"name\":\"预判查询1\",\"code\":\"PrejudgeQry1\"},{\"name\":\"预判查询2\",\"code\":\"PrejudgeQry2\"}],\"y\":\"笔数\",\"x\":\"时间\"}}";
				if(flag){
					jsontest += ");";
				}
				System.out.println(cbStr);
				PrintWriter out = response.getWriter();
				out.print(jsontest);
				out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
