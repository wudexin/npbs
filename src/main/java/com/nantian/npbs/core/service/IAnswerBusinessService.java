package com.nantian.npbs.core.service;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 
 * @author 7tianle
 *
 */
public interface IAnswerBusinessService {
	public void execute(ControlMessage cm,BusinessMessage  bm);
}
