/**
 * 
 */
package com.nantian.npbs.common;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.business.dao.BusinessUnitDao;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * @author TsaiYee
 *
 */
@Scope("prototype")
@Component
public class ProcessManager {
	private static Logger logger = LoggerFactory.getLogger(ProcessManager.class);
	
	@Resource
	public BusinessUnitDao businessUnitDao;
	
	public static ProcessManager getProcessManager() {
		return SpringContextHolder.getBean("processManager");
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void unlockProcess(ControlMessage cm, BusinessMessage bm) {
		if (!cm.isLockProcess())
			return;
		
		logger.info("开始释放进程 ");
		// businessUnit =
		// businessUnitDao.getTbBiBusinessUnitByCondition(bm.getTranCode(),
		// bm.getOrigPosJournalSeqno());
		try {
//			businessUnitDao.releaseProcess(bm.getTranCode(), bm
//					.getOrigPosJournalSeqno());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("释放进程出现错误！", e);
		} // 释放进程
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean lockProcess(ControlMessage cm, BusinessMessage bm) {
		//TODO:  检查进程数是否已经分配完成
		
		//TODO: 记录占用进程
		
		return true;
	}
	
}
