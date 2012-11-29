package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金查询
 * 
 * @author
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness801Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness801Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	CommonPrepay commonPrepay;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}
		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金查询开始：");

		String shopCode = bm.getShopCode(); // 查询商户号

		String prepayAcco = prepayDao.searchPreAccnoBySA(shopCode); // 查询商户备付金账号

		// 执行查询
		try {
			TbBiPrepay tbBiPrepay = commonPrepay.getPrepay(prepayAcco);
			bm.setPrepay(tbBiPrepay);// 设置备付金信息
			bm.setCustomData("备付金查询成功！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			bm.setResponseMsg("备付金查询成功！");
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("备付金查询成功!");
			logger.info("备付金查询成功");
		} catch (Exception e) {
			bm.setAdditionalTip("备付金查询出错，请与技术员联系");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金出错。");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金出错。");
			logger.info("查询备付金出错。");
			e.printStackTrace();
		}
		logger.info("查询备付金成功！");

	}

	protected String tradeType() {
		return "07";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.nantian.npbs.business.service.request.RequestBusinessService#
	 * needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
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