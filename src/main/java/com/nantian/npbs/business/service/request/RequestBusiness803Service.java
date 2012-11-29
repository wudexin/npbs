package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金续费撤销
 * 
 * @author HuBo
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness803Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness803Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	CommonPrepay commonPrepay;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("开始业务处理：{}", getClass().getName());
		boolean suc = false;

		suc = dealPrepay(cm, bm);
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
		return "11";
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
		bm.setSeqnoFlag("1");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");

	}

	public boolean dealPrepay(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金续费撤销开始！查询原交易流水！交易日期[{}];原交易流水号[{}]", new Object[] {
				bm.getTranDate(), bm.getOldPbSeqno() });
		// try {
		TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm
				.getOldPbSeqno());
		if (oriTrade == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水号输入有误,请重新输入!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("原流水号输入有误,请重新输入!");
			logger.error("取消交易失败！用户录入流水号[{}],用户号[{}],缴费金额[{}]", new Object[] {
					bm.getOldPbSeqno(), bm.getUserCode(), bm.getAmount() });
			return false;
		}
		logger.info("原交易状态:[{}];原交易用户编号:[{}];原交易缴费金额:[{}];", new Object[] {
				oriTrade.getStatus(), oriTrade.getCustomerno(),
				oriTrade.getAmount() });

		// 登记流水与原流水对照
		logger.info("登记取消流水与原流水对照。交易日期[{}];原流水号[{}];取消交易流水号[{}];",
				new Object[] { bm.getTranDate(), bm.getOldPbSeqno(),
						bm.getPbSeqno() });

		TbBiTradeContrast tc = new TbBiTradeContrast();
		TbBiTradeContrastId id = new TbBiTradeContrastId();
		id.setOriPbSerial(oriTrade.getId().getPbSerial());
		id.setPbSerial(bm.getPbSeqno());
		id.setTradeDate(bm.getTranDate());
		tc.setId(id);
		// 01-缴费交易；02-取消交易；03-写卡；04-冲正交易；05-写卡成功确认；06-写卡失败确认
		logger.info("写卡流水对照类型[{}]", bm.getTranType());
		tc.setTradeType(bm.getTranType());
		baseHibernateDao.save(tc);

		if (!oriTrade.getStatus().equals(GlobalConst.TRADE_STATUS_SUCCESS)) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水状态不正确!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("原流水状态不正确!");
			return false;
		}

		// 设置电子商务平台流水
		bm.setSysJournalSeqno(bm.getSysJournalSeqno());
		bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
		// 设置取消原交易类型
		cm.setCancelBusinessType(oriTrade.getBusiCode());

		bm.setPrePayAccno(oriTrade.getAccno());
		String shopCode = oriTrade.getCompanyCode();
		bm.setAmount(oriTrade.getAmount());

		bm.setShopCode(shopCode);
		TbBiCompany shop = initShop(cm, bm);
		if (shop == null) {
			return false;
		}

		logger.info("备付金帐号:{}撤销金额:{}", bm.getPrePayAccno(), bm.getAmount());

		if (GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {

			// 修改原流水状态为取消
			logger.info("修改原流水状态,原流水:[{}],状态:[{}]", oriTrade.getId()
					.getPbSerial(), GlobalConst.TRADE_CANCEL_ING);
			boolean suc = tradeDao
					.updateTradeStatus(oriTrade.getId().getTradeDate(),
							oriTrade.getId().getPbSerial(),
							GlobalConst.TRADE_CANCEL_ING, oriTrade
									.getSystemSerial());

			if (suc != true) {
				logger.info("修改流水状态失败！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("流水状态更新失败！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("流水状态更新失败！");
				return false;
			}
			logger.info("流水状态更新成功！{}", getClass().getName());

		}

		return true;
	}

}