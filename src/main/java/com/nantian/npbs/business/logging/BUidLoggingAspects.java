package com.nantian.npbs.business.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 通过AOP方式实现日志MDC内容设置
 * 
 * @author 王玮
 * @version 创建时间：2011-9-15 下午7:28:17
 * 
 */

@Aspect
@Component
public class BUidLoggingAspects {
	private static Logger logger = LoggerFactory
			.getLogger(BUidLoggingAspects.class);

	@Pointcut("execution(* com.nantian.npbs.business.service..*.execute(..))")
	// 定义一个切入点,名称为pointCutMethod(),拦截对应方法
	private void pointCutMethod() {
	}

	@Before("pointCutMethod() && args(cm,bm)")
	// 定义前置通知
	public void doBefore(ControlMessage cm, BusinessMessage bm) {
		if (cm != null && bm != null) {

			// 解包完成后，将取到POS流水号
			String BUID = bm.getShopCode() + bm.getPosJournalNo();
			logger.info("BUID：{}" ,BUID);

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
