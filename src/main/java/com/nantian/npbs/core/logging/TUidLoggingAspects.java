package com.nantian.npbs.core.logging;

import org.apache.camel.Exchange;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.NtStringUtils;

/**
 * 通过AOP方式实现日志MDC内容设置
 * 
 * @author 王玮
 * @version 创建时间：2011-9-15 下午7:20:13
 * 
 */

//@Aspect
//@Component
public class TUidLoggingAspects {
	private static Logger logger = LoggerFactory
			.getLogger(TUidLoggingAspects.class);

	/**
	 * 定义切入点 第一个*表示方法的返回值,这里使用通配符,只有返回值符合条件的才拦截
	 * 第一个..表示com.nantian.npbs.gateway.camel包及其子包 倒数第二个*表示包下的所有Java类
	 * process表示类的名为process方法 (..)表示方法的参数可以任意多个
	 */
//	@Pointcut("execution(* com.nantian.npbs.gateway.camel..*.process(..))")
	// 定义一个切入点,名称为pointCutMethod(),拦截对应方法
	private void pointCutMethod() {
	}

//	@Before("pointCutMethod() && args(exchange)")
	// 定义前置通知
	public void doBefore(Exchange exchange) {
		String TUID = exchange.getIn().getHeader("TUID", String.class);
		logger.info("TUID：{}" , TUID);

		if (TUID == null) {
			TUID = NtStringUtils.getUuid();
			exchange.getIn().setHeader("TUID", TUID);
		} else {
			// 将TUID放到Exchange的header中，未来任何一个新线程队列的processor即可获得
			exchange.getOut().setHeader("TUID", TUID);
		}
		// 放到MDC中，用于日志显示
		logger.info("TUID：{}" , TUID);
		MDC.put("TUID", TUID);

		String BUID = exchange.getIn().getHeader("BUID", String.class);
		logger.info("BUID：{}" , BUID);

		if (BUID != null) {
			// 将BUID放到Exchange的header中，未来任何一个新线程队列的processor即可获得
			exchange.getOut().setHeader("BUID", BUID);
			// 放到MDC中，用于日志显示
			MDC.put("BUID", BUID);
		}

	}

	// // 配置returning="result", result必须和doAfterReturning的参数一致
	// @AfterReturning(pointcut = "pointCutMethod()", returning = "result")
	// // 定义后置通知
	// public void doAfterReturning(String result) {
	// System.out.println("后置通知" + result);
	// }
	//
	// // 类似returning的配置
	// @AfterThrowing(pointcut = "pointCutMethod()", throwing = "e")
	// // 定义例外通知
	// public void doAfterException(Exception e) {
	// System.out.println("异常通知");
	// }
	//
	// @After("pointCutMethod()")
	// // 定义最终通知
	// public void doAfter() {
	// System.out.println("最终通知");
	// }
	//
	// @Around("pointCutMethod()")
	// // 定义环绕通知
	// public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	// logger.info("进入方法:" + pjp.getSignature().getName());
	// Object object = pjp.proceed(); //
	// 必须执行pjp.proceed()方法,如果不执行此方法,业务bean的方法以及后续通知都不执行
	// System.out.println("退出方法");
	// return object;
	// }
}
