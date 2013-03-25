package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 卡表信息查询
 * @author
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness022Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness022Service.class);
	
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		try {
			// 进行公共检查
			if(!checkShopState(cm,bm))return false;// 检查商户状态
			if(!checkSignState(cm,bm))return false;// 检查商户签到
			if(!checkShopBindBusiness(cm,bm))return false;// 检查商户是否有该业务
		} catch (Exception e) {
			logger.error("公共校验异常!"+e);
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("公共校验异常!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("公共校验异常!");
			return false;
		}
		logger.info("公共校验成功!");
		return true;
	}


	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("申请写卡数据（补写）请求处理开始！无业务逻辑。");
		//直接发送电子商务平台

	}

	protected String tradeType(){
		//交易类型 01-缴费交易；02-取消交易；04-冲正交易；05-写卡成功确认；06-写卡失败确认；07-查询；08-管理
		//无特殊处理，设置为查询
		return "07";
	}

	@Override
	public boolean needLockProcess() {
		// 需要进程控制
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		// 不登记流水
		bm.setSeqnoFlag(GlobalConst.SEQNO_FLAG_NO);
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag(GlobalConst.SERVICE_CALL_FLAG_YES);
	}
}
