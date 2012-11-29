package com.nantian.npbs.common.utils;

import java.util.ResourceBundle;

public class TransferUtils {
	/**
	 * 取配置文件信息
	 * @return
	 */
	public static ResourceBundle getTransferResources(){
		ResourceBundle rb = ResourceBundle.getBundle("conf.transfer");
		return rb;
	}
}
