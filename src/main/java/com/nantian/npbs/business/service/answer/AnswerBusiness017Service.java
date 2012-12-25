package com.nantian.npbs.business.service.answer;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness017Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness017Service.class);

	@Resource
	private TradeDao tradeDao;
	
	@Resource
	private CommonPrepay commonPrepay;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		// 备付金支取
		logger.info("备付金支取业务开始！");
		boolean suc = false;

//		deleteAuthorizeAmount(cm, bm);
		suc = dealPrepay(cm, bm);
		if (suc == false) {
			return;
		}
		logger.info("备付金支取成功！");
	}
	
	public boolean dealPrepay(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金处理开始！");
		boolean suc = false;
		String flag = cm.getResultCode();
		String accNo = bm.getPrePayAccno();
		Double amt = bm.getAmount();
		String retCode = GlobalConst.RESULTCODE_SUCCESS;

		logger.info("flag=[{}];accNo=[{}];amt=[{}]",new Object[] {flag , accNo});

		if (GlobalConst.RESULTCODE_SUCCESS.equals(flag)) {
			retCode = commonPrepay.withdrawalsPrepay(accNo, amt,bm);
			if (GlobalConst.RESULTCODE_SUCCESS.equals(retCode) != true) {
				String retMsg = null;
				switch (Integer.getInteger(retCode)) {
				case 000002:
					retMsg = "取该商户备付金账户时出错!";
					break;
				case 000003:
					retMsg = "备付金余额不足!";
					break;
				case 000004:
					retMsg = "修改备付金余额出错!";
					break;
				default:
					retMsg = "取款失败！";
					break;
				}
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg(retMsg);
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg(retMsg);
				logger.error(retMsg);
				return suc;
			}

			// 设置备付金明细--在服务端调用好呢还是在这里调用
			// 存取款操作应该联动登记明细，但是这里取不到流水号和商户名称等信息
			TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
			TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
			tbPrepayId.setPbSerial(bm.getPbSeqno());
			tbPrepayId.setTradeDate(bm.getLocalDate());
			prepayInfo.setId(tbPrepayId);
			prepayInfo.setAccno(accNo);
			prepayInfo.setCompanyCode(bm.getShopCode());
			prepayInfo.setBusiCode(bm.getBusinessType());
			prepayInfo.setSystemCode("01");
			prepayInfo.setStatus("00");
			prepayInfo.setSystemSerial(bm.getSysJournalSeqno());
			prepayInfo.setCustomerno(bm.getUserCode());
			prepayInfo.setCustomername(bm.getUserName().trim());
			prepayInfo.setFlag("1");
			prepayInfo.setAmount(amt);
			prepayInfo.setBal(bm.getPreBalance());
			prepayInfo.setSummary("");
			prepayInfo.setRemark("");
			prepayInfo.setTradeTime(bm.getLocalDate() + bm.getLocalTime());

			// 登记备付金明细
			suc = false;
			suc = commonPrepay.addPrepayInfo(prepayInfo);
			if (suc == false) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金明细插入失败！");
				logger.error("备付金明细插入失败！");
				return false;
			}
		}

		return suc;
	}

}
