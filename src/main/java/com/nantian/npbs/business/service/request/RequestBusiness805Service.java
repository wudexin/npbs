package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiTradeBusi;
import com.nantian.npbs.business.model.TbBiTradeBusiId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金密码重置
 * 
 * @author HuBo
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness805Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness805Service.class);

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
		logger.info("开始业务处理：");
		boolean suc = false;

		suc = resetPasswd(cm, bm);
		if (suc == false) {
			return;
		}
	}

	/**
	 * 返回交易类型 01-缴费交易；02-取消交易；04-冲正交易
	 * 
	 * @return
	 */
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
		// bm.setSeqnoFlag("1");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");

	}

	public boolean resetPasswd(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金密码重置开始！");

		String queryType = bm.getQueryType();
		if (null == queryType) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("请上送查询类型！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("请上送查询类型！");
			logger.info("请上送查询类型！");
			return false;
		}

		if (queryType.equals("0")) {
			// 商户信息校验
			if (!checkShopInfo(cm, bm)) {
				logger.info("商户信息校验错！");
			}
		} else if (queryType.equals("1")) {
			// 商户密码修改
			if (!resetPsw(cm, bm)) {
				logger.info("商户密码重置错！");
			}
		} else {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询类型不正确！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询类型不正确！");
			logger.info("查询类型不正确！");
			return false;
		}

		logger.info("备付金密码重置成功！");

		return true;
	}

	/**
	 * 商户信息校验
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean checkShopInfo(ControlMessage cm, BusinessMessage bm) {
		TbBiTradeBusi tbBiTradeBusi = (TbBiTradeBusi) bm.getCustomData();
		/** 校验负责人姓名 */
		if (null != tbBiTradeBusi.getContact()) {
			// 负责人姓名不为空
			if (null == bm.getShop().getContact()) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户负责人姓名验证失败");
				logger.info("该商户负责人姓名为空,商户号:{}", bm.getShopCode());
				return false;
			}
			if (!((bm.getShop().getContact().trim()).equals(tbBiTradeBusi
					.getContact()))) {
				// 校验不通过
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户负责人姓名验证失败");
				logger.info("商户负责人姓名不正确,商户号:{}", bm.getShopCode());
				return false;
			}
		} else {
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户负责人姓名验证失败");
			logger.info("商户负责人姓名不能为空,商户号:{}", bm.getShopCode());
			return false;
		}

		/** 校验负责人身份证号码 */
		if (null != tbBiTradeBusi.getAuid()) {
			// 负责人身份证号码不为空
			if (null == bm.getShop().getAuid()) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("该商户未登记负责人身份证号码,商户号:" + bm.getShopCode());
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户负责人证件号码验证失败");
				logger.info("商户未登记负责人身份证号码,商户号:{}", bm.getShopCode());
				return false;
			}
			if (!((bm.getShop().getAuid().trim()).equals(tbBiTradeBusi
					.getAuid()))) {
				// 校验不通过
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("商户负责人身份证号码不正确");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户负责人证件号码验证失败");
				logger.info("商户负责人身份证号码不正确,商户号:{}", bm.getShopCode());
				return false;
			}
		} else {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户负责人身份证号码不能为空,商户号:" + bm.getShopCode());			
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户负责人证件号码验证失败");
			logger.info("商户负责人身份证号码不能为空,商户号:{}", bm.getShopCode());
			return false;
		}
		// 查询不登记流水
		bm.setSeqnoFlag("0");
		cm.setResultMsg("查询校验通过！");
		logger.info("校验通过,商户号:{}", bm.getShopCode());
		return true;
	}

	/**
	 * 商户密码重置
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private boolean resetPsw(ControlMessage cm, BusinessMessage bm) {
		if (!checkShopInfo(cm, bm)) {
			return false;
		}

		// 获取备付金帐号
		String accNo = bm.getShop().getResaccno();
		if (null == accNo) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("该商户备付金帐号为空,商户号:" + bm.getShopCode());
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("该商户备付金帐号为空");
			logger.info("该商户备付金帐号为空,商户号:{}", bm.getShopCode());
			return false;
		}

		try {
			if (!prepayDao.resetPSW(accNo)) {
				// 重置密码不成功
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("商户密码重置失败,商户号:" + bm.getShopCode());
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户密码重置失败");
				logger.info("商户密码重置失败,商户号:{}", bm.getShopCode());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户密码重置失败,商户号:" + bm.getShopCode());
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户密码重置失败");
			logger.info("商户密码重置失败,商户号:{}", bm.getShopCode());
			return false;
		}

		// 登记备付金账户密码修改记录表tb_bi_trade_busi
		TbBiTradeBusi tbBiTradeBusi = (TbBiTradeBusi) bm.getCustomData();
		tbBiTradeBusi.setCompanyName(bm.getShop().getCompanyName());
		tbBiTradeBusi.setRemark("备付金密码重置");
		TbBiTradeBusiId tbBiTradeBusiId = new TbBiTradeBusiId();
		tbBiTradeBusiId.setPbSerial(bm.getPbSeqno());
		tbBiTradeBusiId.setTradeDate(bm.getTranDate());
		tbBiTradeBusi.setId(tbBiTradeBusiId);
		tbBiTradeBusi.setTradeTime(bm.getTranTime());
		tradeDao.addTbBiTradeBusi(tbBiTradeBusi);

		// 登记流水
		bm.setSeqnoFlag("1");
		cm.setResultMsg("密码重置成功！");
		logger.info("商户密码重置成功！");

		return true;
	}
}