package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 业务查询
 * 
 * @author 7tianle
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness001Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness001Service.class);

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {

		// 检查商户状态
		if (!checkShopState(cm, bm)) {
			return false;
		}
		// 检查商户签到
		if (!checkSignState(cm, bm)) {
			return false;
		}
		// 检查商户是否有该业务
		if (!checkShopBindBusiness(cm, bm)) {
			return false;
		}
		// 检查强制下载信息
		checkForceDownLoadFlag(cm, bm);

		// 检查电话号码
		if(!checkPhoneNum(cm,bm)){
			return false;
		}
		
		//设置标志域
		if(!setFlagField(bm)){
			return false;
		}
		
		return true;

	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("缴费查询交易请求处理开始！无业务处理！");
		// 当交易码为失败时,退出
//		if (cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)) {
//			return;
//		}

		// 加入交易流水(状态为异常状态)
//		logger.info("增加交易流水!");
//		if (!addTrade(cm, bm)) {
//			logger.error("增加交易流水失败!");
//			return;
//		}
//		logger.info("增加交易流水成功!");
	}

	protected String tradeType() {
		return "07";
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag("1");
	}
	
	/**
	 * 检查电话号码
	 * @param bm
	 * @return
	 */
	protected boolean checkPhoneNum(ControlMessage cm,BusinessMessage bm){
		return true;
	}

}
