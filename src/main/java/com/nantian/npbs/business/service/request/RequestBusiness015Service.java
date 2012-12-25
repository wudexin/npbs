package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 终端查询交易明细（指定用户号）
 * @author 
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness015Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness015Service.class);
	
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		// 终端查询交易明细（指定用户号）无公共校验
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		//校验日期是否合法
		if(!checkDate(cm,bm))
			return;
		logger.info("终端查询交易明细（指定业务种类）request日期校验成功！");
		logger.info("终端查询交易明细（指定业务种类）request业务处理结束！");
	}
	
	/**
	 * 校验日期是否合法
	 * @param cm 
	 * @param bm
	 * @return
	 */
	private boolean checkDate(ControlMessage cm,BusinessMessage bm){
		
		long lTime1 = Long.parseLong(bm.getQueryStartDate());
		long lTime2 = Long.parseLong(bm.getQueryEndDate());
		if(lTime2<lTime1){
			bm.setResponseCode("99");
			bm.setResponseMsg("输入日期有误，请修改后重试!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("输入日期有误，请修改后重试!");
			logger.info("输入日期有误，请修改后重试!");
			return false;
		}
		return true;
	}
	
	//TODO: 
	protected String tradeType(){
		return "07";
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.service.request.RequestBusinessService#needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");
	}
	

}
