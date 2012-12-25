/**
 * 
 */
package com.nantian.npbs.common.utils;

import java.util.Date;

/**
 * @author TsaiYee
 * 
 */
public class TimeUtils {
	
	/**
	 * 
	 * @param interval (second)
	 * @return
	 */
	public static Date getTimeOut(int interval) {
		Date now = new Date();
		Date timeout = new Date();
		timeout.setTime(now.getTime() + interval * 1000);
		
		return timeout;
	}


}
