package com.nantian.npbs.business.service.request;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 末笔交易查询
 * 
 * @author
 * 
 */

@Scope("prototype")
@Component
public class RequestBusiness010Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness010Service.class);

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		if(CHANEL_TYPE.ELEBUSIREQUEST.equals(cm.getChanelType())){
			bm.setTranDate(bm.getOrigDealDate());
			return true;
		}
		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}
		
		
		 
		if (!checkSignState(cm, bm)) { // 检查商户签到
			return false;
		} 
		
		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		
		//2012年5月23日8:45:05  经业务孟主任确认对于阶梯电价信息在末笔交易查询时不进行打印
		if(cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)) {
			return;
		}
		
		logger.info("末笔查询交易业务处理开始");
		if(checkIsSendToService(cm,bm)){
			cm.setServiceCallFlag("1");
		}else {
			cm.setServiceCallFlag("0");
		}
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
		// 发送第三方		
			cm.setServiceCallFlag("0");	
	}
	

	/**
	 * 检查末笔交易流水是否需要发送至电子商务平台
	 * @param cm
	 * @param bm
	 * @return 
	 */
	public boolean checkIsSendToService(ControlMessage cm,BusinessMessage bm) {		
		
		String hql = "from TbBiTrade t where t.id.tradeDate = '"+ bm.getTranDate() +"' and t.companyCode = '"
			+ bm.getShopCode() + "' and t.posSerial ='" + bm.getOrigPosJournalSeqno() + "'";
		logger.info(hql);
		//日期，商户号，pos流水号为唯一约束
		List<TbBiTrade> tbBiTradeList = tradeDao.findTradeList(hql);		
		if(null == tbBiTradeList || tbBiTradeList.size()<=0) {
			//add by fengyafang公控机有可能出现拥堵的情况，如果交易还没上来。但是末笔查询已经上来了。此时的末笔应返回02原交易不存在，与电商保持一致
			//bm.setResponseCode(GlobalConst.TRADE_STATUS_FAILURE);
			bm.setResponseCode(GlobalConst.TRADE_STATUS_NOEXSIST);
			bm.setResponseMsg("末笔交易查询,便民查询原交易不存在！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("末笔交易查询，便民查询原交易不存在！");
			logger.info("末笔交易查询失败,查询异常,查询表：TB_BI_TRADE,商户号: " + bm.getShopCode());
			return false;
		}else if(tbBiTradeList.size() == 1) {
			TbBiTrade tbBiTrade = tbBiTradeList.get(0);
			String status = tbBiTrade.getStatus();
			//设置返回的原交易信息
		//-Start 2012年1月9日 17:39:58 MDB
			bm.setTranType(tbBiTrade.getTradeType());
			bm.setBusinessType(tbBiTrade.getBusiCode());
		//-End 2012年1月9日 17:39:58 MDB
			bm.setResponseCode(status);
			bm.setShopCode(tbBiTrade.getCompanyCode());
			bm.setUserCode(tbBiTrade.getCustomerno());
			bm.setUserName(tbBiTrade.getCustomername());
//			bm.setPbSeqno(tbBiTrade.getId().getPbSerial());//设置原交易流水，不管是直接返回还是修改都使用
			bm.setOrigDealDate(tbBiTrade.getId().getTradeDate()); //设置需要发送给电子商务平台的末笔交易日期
			bm.setOldPbSeqno(tbBiTrade.getId().getPbSerial());   //设置需要发送给电子商务平台的末笔交易流水
			bm.setAmount(tbBiTrade.getAmount());
			
			if("010".equals(bm.getBusinessType())) { //如果河北省标卡则走专用末笔设置
				if(doHBSbCheckIsSendToService(cm,bm)) {
					return true;
				}else {
					return false;
				}
			}			
			if("99".equals(status) || "02".equals(status)) {
				logger.info("原交易状态为:[{}],发电子商务平台查询。",status);
				 //修改流水不使用公共的修改，anser010中自己修改流水状态和电子商务平台日期、流水
				return true;
			} else if("00".equals(status) && "02".equals(tbBiTrade.getTradeType())){
				//终端无法取到缴费交易和取消交易流水，查询出来后下传
				logger.info("查询原流水与取消流水对照！" );
				TbBiTradeContrast tradeContrast = null;
				String getTradeSql = " from TbBiTradeContrast where id.tradeDate = '"
						+ bm.getTranDate() + "' and id.pbSerial = '"
						+ bm.getOldPbSeqno() + "' and tradeType = '02'";
				List<TbBiTradeContrast> tradeConList = tradeDao.findTradeContrastList(getTradeSql);
				if (tradeConList.size() > 1) {
					logger.info("查询原流水对照失败！" );
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("查询原流水对照失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("查询原流水对照失败！");
					return true;
				}
				tradeContrast = tradeConList.get(0);
				bm.setPbSeqno(bm.getOldPbSeqno());
				if(!"012".equals(bm.getBusinessType())) {				
					bm.setOldPbSeqno(tradeContrast.getId().getOriPbSerial());
				}
				
				logger.info("原交易状态为:[{}],直接返回终端信息。",status);
				bm.setResponseCode(status);
				return false;
				//add by wzd 2012年5月8日14:21:34 当交易被取消时默认返回失败
			}else if(GlobalConst.TRADE_STATUS_CANCEL.equals(status.trim())) {
				logger.info("原交易状态为:[{}]，返回中的失败");
				bm.setResponseCode(GlobalConst.TRADE_STATUS_FAILURE);
				bm.setPbSeqno(tbBiTrade.getId().getPbSerial());				
				return false;
			}else {
				logger.info("原交易状态为:[{}],直接返回终端信息。",status);
				bm.setPbSeqno(tbBiTrade.getId().getPbSerial());
				bm.setResponseCode(status);
				return false;
			}
		}else {
			logger.info("终端流水重复。");
			bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
			bm.setResponseMsg("末笔交易查询,查询交易不存在！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("末笔交易查询，查询交易不存在！");
			return false;
		}
	}
	
	//河北省标卡专用末笔查询判断是否发送电商
	public boolean doHBSbCheckIsSendToService(ControlMessage cm,BusinessMessage bm) {
		
		String hql = "from TbBiTrade t where t.id.tradeDate = '"+ bm.getTranDate() +"' and t.companyCode = '"
		+ bm.getShopCode() + "' and t.posSerial ='" + bm.getOrigPosJournalSeqno() + "'";
	logger.info(hql);
	//日期，商户号，pos流水号为唯一约束
	List<TbBiTrade> tbBiTradeList = tradeDao.findTradeList(hql);		
	if(null == tbBiTradeList || tbBiTradeList.size()<=0) {
		//add by fengyafang公控机有可能出现拥堵的情况，如果交易还没上来。但是末笔查询已经上来了。此时的末笔应返回02原交易不存在，与电商保持一致
		//bm.setResponseCode(GlobalConst.TRADE_STATUS_FAILURE);
		bm.setResponseCode(GlobalConst.TRADE_STATUS_NOEXSIST);
		bm.setResponseMsg("末笔交易查询,便民查询原交易不存在！");
		cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		cm.setResultMsg("末笔交易查询，便民查询原交易不存在！");
		logger.info("末笔交易查询失败,查询异常,查询表：TB_BI_TRADE,商户号: " + bm.getShopCode());
		return false;
	}else if(tbBiTradeList.size() == 1) {
		TbBiTrade tbBiTrade = tbBiTradeList.get(0);
		String status = tbBiTrade.getStatus();
		//设置返回的原交易信息
	//-Start 2012年1月9日 17:39:58 MDB
		bm.setTranType(tbBiTrade.getTradeType());
		bm.setBusinessType(tbBiTrade.getBusiCode());
	//-End 2012年1月9日 17:39:58 MDB
		bm.setResponseCode(status);
		bm.setShopCode(tbBiTrade.getCompanyCode());
		bm.setUserCode(tbBiTrade.getCustomerno());
		bm.setUserName(tbBiTrade.getCustomername());
//		bm.setPbSeqno(tbBiTrade.getId().getPbSerial());//设置原交易流水，不管是直接返回还是修改都使用
		bm.setOrigDealDate(tbBiTrade.getId().getTradeDate()); //设置需要发送给电子商务平台的末笔交易日期
		bm.setOldPbSeqno(tbBiTrade.getId().getPbSerial());   //设置需要发送给电子商务平台的末笔交易流水
		bm.setAmount(tbBiTrade.getAmount());
		logger.info("查询原申请写卡数据流水与缴费流水对照！" );
		TbBiTradeContrast tradeContrastBuy = null;
		String getTradeSqlBuy = null;
		if("03".equals(tbBiTrade.getTradeType())) {
			getTradeSqlBuy= " from TbBiTradeContrast where id.tradeDate = '"
				+ bm.getTranDate() + "' and id.pbSerial = '"
				+ tbBiTrade.getId().getPbSerial() + "' and tradeType = '03'";
		}else if("09".equals(tbBiTrade.getTradeType())) {
			getTradeSqlBuy= " from TbBiTradeContrast where id.tradeDate = '"
				+ bm.getTranDate() + "' and id.pbSerial = '"
				+ tbBiTrade.getId().getPbSerial() + "' and tradeType = '09'";
		}		
		List<TbBiTradeContrast> tradeConListBuy = tradeDao.findTradeContrastList(getTradeSqlBuy);
		if (tradeConListBuy.size() > 1) {
			logger.info("查询原流水对照失败！" );
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询原流水对照失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询原流水对照失败！");
			return false;
		}
		tradeContrastBuy = tradeConListBuy.get(0);
		bm.setOrigPosJournalSeqno(tradeContrastBuy.getId().getOriPbSerial()); //设置发送pos流水
		
		if("99".equals(status) || "02".equals(status)) {
			logger.info("原交易状态为:[{}],发电子商务平台查询。",status);
			 //修改流水不使用公共的修改，anser010中自己修改流水状态和电子商务平台日期、流水
			return true;
		} else if("00".equals(status) && "09".equals(tbBiTrade.getTradeType())){
			//终端无法取到缴费交易和取消交易流水，查询出来后下传			
			TbBiTradeContrast tradeContrastcancle = null;
			String getTradeSqlcancle = " from TbBiTradeContrast where id.tradeDate = '"
					+ bm.getTranDate() + "' and id.oriPbSerial = '"
					+ tradeContrastBuy.getId().getOriPbSerial() + "' and tradeType = '02'";
			List<TbBiTradeContrast> tradeConListcancle = tradeDao.findTradeContrastList(getTradeSqlcancle);
			if (tradeConListcancle.size() > 1) {
				logger.info("查询原流水对照失败！" );
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询原流水对照失败！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询原流水对照失败！");
				return false;
			}
			
			tradeContrastcancle = tradeConListcancle.get(0);
			bm.setOrigPosJournalSeqno(tradeContrastcancle.getId().getPbSerial()); //设置发送给pos打印内容
			bm.setPbSeqno(bm.getOldPbSeqno());
			
			logger.info("原交易状态为:[{}],直接返回终端信息。",status);
			bm.setResponseCode(status);
			return false;
		}else {
			logger.info("原交易状态为:[{}],直接返回终端信息。",status);
			bm.setPbSeqno(tbBiTrade.getId().getPbSerial());
			bm.setResponseCode(status);
			return false;
		}
	}else {
		logger.info("终端流水重复。");
		bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
		bm.setResponseMsg("末笔交易查询,查询交易不存在！");
		cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		cm.setResultMsg("末笔交易查询，查询交易不存在！");
		return false;
	}
		
	}
}
