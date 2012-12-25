package com.nantian.npbs.business.service.answer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness001Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness001Service.class);

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("缴费查询交易响应处理开始！无业务处理！");
			
		//add test
		// 业务处理，查询交易，打包前处理
		// 更新系统日期
		//取变量池中的中间业务平台日期，原C中用的是ZHFWPTRQ
//		String szZHFWPTRQ = bm.getMidPlatformDate();
//		String sql = "UPDATE TB_SM_SYSDATA SET SYSTEM_DATE = '"+szZHFWPTRQ+"'";
//		logger.info("sql=["+sql+"]"+ getClass().getSimpleName());
//		try {
//			baseHibernateDao.excuteSQL(sql);
//		} catch (Exception e) {
//			logger.error("修改系统日期表失败！" + getClass().getSimpleName(),e);
//			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
//			cm.setResultMsg("修改系统日期表失败！");
//			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
//			bm.setResponseMsg("修改系统日期表失败！");
//			logger.error("UPDATE TB_SM_SYSDATA err", e);
//		}
//		logger.info("修改系统日期表成功！"+getClass().getSimpleName());
//		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
//		cm.setResultMsg("");
		
		//保存现金缴费临时表数据
		setTempValue(cm,bm);
	}

	/**
	 * 保存现金缴费临时表数据
	 */
	protected void setTempValue(ControlMessage cm,BusinessMessage bm){
		
	}

}
