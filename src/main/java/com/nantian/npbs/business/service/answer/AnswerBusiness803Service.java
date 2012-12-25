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
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
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
public class AnswerBusiness803Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness803Service.class);
	@Resource
	public PrepayDao prepayDao;

	@Resource
	CommonPrepay commonPrepay;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金续费撤销Answer开始！查询原交易流水！交易日期[{}];原交易流水号[{}]",
				new Object[] { bm.getTranDate(), bm.getOldPbSeqno() });
		TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm
				.getOldPbSeqno());
		if (oriTrade == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水号输入有误,请重新输入!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("原流水号输入有误,请重新输入!");
			logger.error("取消交易失败！用户录入流水号[{}],用户号[{}],缴费金额[{}]", new Object[] {
					bm.getOldPbSeqno(), bm.getUserCode(), bm.getAmount() });
			return;
		}
		logger.info("原交易状态:[{}];原交易用户编号:[{}];原交易缴费金额:[{}];", new Object[] {
				oriTrade.getStatus(), oriTrade.getCustomerno(),
				oriTrade.getAmount() });

		if (!GlobalConst.TRADE_CANCEL_ING.equals(oriTrade.getStatus())) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水状态不正确!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("原流水状态不正确!");
			return;
		}

		logger.info("备付金帐号:{}撤销金额:{}", bm.getPrePayAccno(), bm.getAmount());

		if (GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {
			boolean suc = cancelPrepay(cm, bm);
			if (suc) {
				// 修改原流水状态为取消
				logger.info("修改原流水状态,原流水:[{}],状态:[{}]", oriTrade.getId()
						.getPbSerial(), GlobalConst.TRADE_STATUS_CANCEL);
				suc = tradeDao.updateTradeStatus(oriTrade.getId()
						.getTradeDate(), oriTrade.getId().getPbSerial(),
						GlobalConst.TRADE_STATUS_CANCEL, oriTrade
								.getSystemSerial());

				if (suc != true) {
					logger.info("修改流水状态失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("流水状态更新失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("流水状态更新失败！");
					return;
				}
				logger.info("流水状态更新成功！{}", getClass().getName());
			} else {
				// 修改原流水状态为成功
				logger.info("回退原流水状态,原流水:[{}],状态:[{}]", oriTrade.getId()
						.getPbSerial(), GlobalConst.TRADE_STATUS_SUCCESS);
				suc = tradeDao.updateTradeStatus(oriTrade.getId()
						.getTradeDate(), oriTrade.getId().getPbSerial(),
						GlobalConst.TRADE_STATUS_SUCCESS, oriTrade
								.getSystemSerial());
				if (suc != true) {
					logger.info("修改流水状态失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("流水状态更新失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("流水状态更新失败！");
					return;
				}
			}
		}
	}

	/**
	 * 撤销备付金
	 */
	private boolean cancelPrepay(ControlMessage cm, BusinessMessage bm) {
		TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm
				.getOldPbSeqno());
		boolean suc = false;
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

		if (!GlobalConst.TRADE_CANCEL_ING.equals(oriTrade.getStatus())) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("原流水状态不正确!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("原流水状态不正确!");
			return false;
		}

		logger.info("备付金帐号:{}撤销金额:{}", bm.getPrePayAccno(), bm.getAmount());

		if (GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {

			// 修改备付金表
			boolean ret = false;
			ret = commonPrepay.cancelPrepayIn(bm.getPrePayAccno(), bm
					.getAmount(), bm.getTranDate());
			if (ret == false) {

				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("备付金缴费不能撤销！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金缴费不能撤销！");
				return false;
			}
			TbBiPrepay tbBiPrepay = null;
			try {
				tbBiPrepay = commonPrepay.getPrepay(bm.getPrePayAccno());
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询备付金账户失败！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询备付金账户失败！");
				return false;
			}

			// 登记备付金明细表tb_bi_prepay_info
			logger.info("登记备付金明细");
			TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
			TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
			tbPrepayId.setPbSerial(bm.getPbSeqno());
			tbPrepayId.setTradeDate(bm.getTranDate());
			prepayInfo.setId(tbPrepayId);
			prepayInfo.setAccno(bm.getPrePayAccno());
			prepayInfo.setCompanyCode(oriTrade.getCompanyCode());
			prepayInfo.setBusiCode(bm.getBusinessType());// 业务编码
			prepayInfo.setSystemCode(bm.getSystemChanelCode());
			prepayInfo.setStatus("01");
			prepayInfo.setSystemSerial(bm.getSysJournalSeqno());
			prepayInfo.setCustomerno(oriTrade.getCustomerno());
			prepayInfo.setCustomername(oriTrade.getCustomername());
			prepayInfo.setFlag("1");// 存款
			prepayInfo.setAmount(bm.getAmount());
			if (DoubleUtils.sub(tbBiPrepay.getAccBalance(), 0) > 0.0) {
				prepayInfo.setBal(tbBiPrepay.getAccBalance());
			} else {
				prepayInfo.setBal(-tbBiPrepay.getUseCreamt());
			}
			prepayInfo.setSummary("备付金续费取消");
			prepayInfo.setRemark(bm.getRemark());
			prepayInfo.setTradeTime(bm.getTranTime());

			// 登记备付金明细
			suc = commonPrepay.addPrepayInfo(prepayInfo);
			if (suc == false) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金明细插入失败！");
				logger.error("备付金明细插入失败！");
				return false;
			}

			bm.setPrepay(tbBiPrepay);
			bm.setCustomData(prepayInfo);
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			bm.setResponseMsg("备付金缴费撤销成功！");
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("备付金缴费撤销成功!");
			logger.info("备付金缴费撤销成功");
		}
		return true;
	}

}
