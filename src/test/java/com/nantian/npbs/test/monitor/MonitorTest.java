package com.nantian.npbs.test.monitor;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.ReflectionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.monitor.service.PerformanceMonitor;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-9-21 下午5:33:29
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class MonitorTest {
	private static final Logger logger = LoggerFactory
			.getLogger(MonitorTest.class);

	@Test
	public void getMBean() {

		// 初始化
		PerformanceMonitor.init();
		try {

			// 获取所有监控属性的数组
			MBeanAttributeInfo[] mi = PerformanceMonitor.mbeanServer
					.getMBeanInfo(PerformanceMonitor.perf4jMBeanON)
					.getAttributes();

			// 获取并显示属性值
			for (int i = 0; i < mi.length; i++) {
				logger.info(mi[i].getName());
				logger.info(PerformanceMonitor.getValue(mi[i].getName()));
			}

		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
