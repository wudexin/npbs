package com.nantian.npbs.core.service;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 请求服务接口类
 * @author qiaoxiaolei
 * 入口方法 execute
 */
public interface IRequestBusinessService {

	/**
	 * 入口方法
	 * @param cm 控制信息
	 * @param bm 交易信息
	 */
	public void execute(ControlMessage cm,BusinessMessage  bm);
	
	/**
	 * 进程控制标志判断
	 */
	public boolean needLockProcess();
	
	/**
	 * 设置是否登记流水
	 * @param bm 交易信息
	 */
	public void setTradeFlag(BusinessMessage bm);
	
	/**
	 * 设置是否发送第三方
	 * @param cm 控制信息
	 */
	public void setCallServiceFlag(ControlMessage cm);
	
	
	
}
