package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 绿卡余额查询
 * @author
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness020Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness020Service.class);
	@Resource
	public PrepayDao prepayDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		if(!getPrebalanceByShopAccount(cm,bm)){
			logger.error("查询备付金余额失败！");
			return;
		}
		logger.info("查询备付金余额成功！");
	}

	private boolean getPrebalanceByShopAccount(ControlMessage cm,
			BusinessMessage bm) {
		String shopAccount = bm.getShopCode();// 商户号
		String accNo = null;// 备付金帐号
		TbBiPrepay tbBiprepay = null;// 备付金余额
		try {
			accNo = prepayDao.searchPreAccnoBySA(shopAccount);
			tbBiprepay = (TbBiPrepay)baseHibernateDao.get(TbBiPrepay.class, accNo);
			bm.setPreBalance(tbBiprepay.getAccBalance());// 设置备付金余额
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金余额出错,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金余额出错,请拨打客服电话咨询!");
			logger.error("查询备付金余额出错,查询表：TB_BI_PREPAY,商户号: " + bm.getShopCode() + "备付金账号: " + accNo);
			return false;
		}
		return true;
	}



}
