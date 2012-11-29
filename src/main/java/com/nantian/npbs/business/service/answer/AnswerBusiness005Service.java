package com.nantian.npbs.business.service.answer;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness005Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness005Service.class);
	@Resource
	public PrepayDao prepayDao;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		if (!getPrepayByShopAccount(cm, bm)) {
			logger.error("查询备付金余额失败！");
			return;
		}
		logger.info("查询备付金余额成功！");
	}

	private boolean getPrepayByShopAccount(ControlMessage cm,
			BusinessMessage bm) {
		String shopAccount = bm.getShopCode();// 商户号
		String accNo = null;// 备付金帐号
		TbBiPrepay tbBiPrepay = null;//备付金表对象
		TbBiPrepayInfo tbBiprepayInfo = null;// 备付金明细表对象
		try {
			accNo = prepayDao.searchPreAccnoBySA(shopAccount);
			tbBiPrepay = (TbBiPrepay)baseHibernateDao.get(TbBiPrepay.class, accNo);
			TbBiPrepayInfoId tbBiPrepayInfoId = new TbBiPrepayInfoId();
			tbBiPrepayInfoId.setTradeDate(bm.getTranDate());//交易日期
			tbBiPrepayInfoId.setPbSerial(bm.getPbSeqno());//pb流水号
			tbBiprepayInfo = (TbBiPrepayInfo) baseHibernateDao.get(TbBiPrepayInfo.class, tbBiPrepayInfoId);
			bm.setPrePayAccno(tbBiprepayInfo.getAccno());//设置备付金账户号
			bm.setPrePayName(tbBiprepayInfo.getCustomername());//设置备付金账户名称
			bm.setPrePaySurCreamt(tbBiPrepay.getSurCreamt());//设置备付金剩余信用额度
			bm.setPreBalance(tbBiprepayInfo.getBal());// 设置备付金余额
			
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金出错,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金出错,请拨打客服电话咨询!");
			logger.error("查询备付金出错,查询表：TB_BI_PREPAY , TB_BI_PREPAY_INFO,商户号: " + bm.getShopCode()
					+ "备付金账号: " + accNo);
			return false;
		}
		return true;
	}


}
