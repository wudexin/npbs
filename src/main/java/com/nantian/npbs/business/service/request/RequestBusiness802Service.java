package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金续费
 * 
 * @author HuBo
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness802Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness802Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	CommonPrepay commonPrepay;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
//		if (!checkShopState(cm, bm)) { // 检查商户状态
//			return false;
//		}
		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("开始业务处理：");
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
		return "10";
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
		logger.info("备付金续费开始！");
		try {
			String flag = cm.getResultCode();
			String accNo = prepayDao.searchPreAccnoBySA(bm.getShopCode()); // 查询商户备付金账号
			TbBiPrepay tbBiPrepay = commonPrepay.getPrepay(accNo);

			Double amt = bm.getAmount();
			String retCode = GlobalConst.RESULTCODE_SUCCESS;

			logger.debug("flag={}accNo={}amt={}", new Object[] { flag, accNo,
					amt });
			logger.info("备付金帐号:{}存款金额{}", accNo, amt);
			if (GlobalConst.RESULTCODE_SUCCESS.equals(flag)) {
				retCode = commonPrepay.depositPrepay(accNo, amt);
				if (GlobalConst.RESULTCODE_SUCCESS.equals(retCode) != true) {
					String retMsg = null;
					switch (Integer.getInteger(retCode)) {
					case 000002:
						retMsg = "取该商户备付金账户时出错!";
						break;
					// case 000003:
					// retMsg = "备付金余额不足!";
					// break;
					case 000004:
						retMsg = "修改备付金余额出错!";
						break;
					default:
						retMsg = "存款失败！";
						break;
					}
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg(retMsg);
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg(retMsg);
					logger.error(retMsg);
					return false;
				}
				TbBiCompany shop = bm.getShop();

				// 登记备付金明细表tb_bi_prepay_info
				logger.info("登记备付金明细");
				TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
				TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
				tbPrepayId.setPbSerial(bm.getPbSeqno());
				tbPrepayId.setTradeDate(bm.getTranDate());
				prepayInfo.setId(tbPrepayId);
				prepayInfo.setAccno(accNo);
				prepayInfo.setCompanyCode(bm.getShopCode());
				prepayInfo.setBusiCode(bm.getBusinessType());// 业务编码
				prepayInfo.setSystemCode(bm.getSystemChanelCode());
				prepayInfo.setStatus("00");
				prepayInfo.setSystemSerial(bm.getSysJournalSeqno());
				prepayInfo.setCustomerno(shop.getCompanyCode());
				prepayInfo.setCustomername(shop.getCompanyName());
				prepayInfo.setFlag("1");// 存款
				prepayInfo.setAmount(amt);
				if(DoubleUtils.sub(tbBiPrepay.getAccBalance(), 0) > 0.0){
					prepayInfo.setBal(tbBiPrepay.getAccBalance());
				}else{
					prepayInfo.setBal(-tbBiPrepay.getUseCreamt());
				}
				
				prepayInfo.setSummary("备付金续费");
				prepayInfo.setRemark("");
				prepayInfo.setTradeTime(bm.getTranTime());

				// 登记备付金明细
				boolean suc = false;
				suc = commonPrepay.addPrepayInfo(prepayInfo);
				if (suc == false) {
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金明细插入失败！");
					logger.error("备付金明细插入失败！");
					return false;
				}
				bm.setPrepay(tbBiPrepay);
				bm.setCustomData("备付金缴费成功！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
				bm.setResponseMsg("备付金缴费成功！");
				cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
				cm.setResultMsg("备付金缴费成功!");
				logger.info("备付金缴费成功");
			}
		} catch (Exception e) {
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金存款失败！");
			logger.error("备付金存款失败！",e );
		}

		return true;
	}
}