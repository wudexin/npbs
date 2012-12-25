package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.business.dao.BusinessUnitDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiBusinessUnit;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.core.service.IAnswerBusinessService;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

public abstract class AnswerBusinessService implements IAnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusinessService.class);

	// 基Dao
	@Resource
	protected BaseHibernateDao baseHibernateDao;

	@Resource
	protected BusinessUnitDao businessUnitDao;

	protected TbBiBusinessUnit businessUnit; // 业务对应机构

	// 交易流水Dao
	@Resource
	protected TradeDao tradeDao;

	@Override
	public void execute(ControlMessage cm, BusinessMessage bm) {

		logger.info("开始执行响应处理");
		// 由于流水是一个公共处理，不论失败成功均需要处理
		//先修改流水，再进行账务处理避免非同一事务可能导致业务处理成功流水修改失败
		if ("1".equals(bm.getSeqnoFlag())) {
			editTradeState(cm, bm);
		}

		// 失败不需要进行业务处理
		if(!GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {
			//河电省标卡申请写卡数据交易收到电商明确失败信息时同步更新原缴费交易流失号为失败	
			if(GlobalConst.RESULTCODE_FAILURE.equals(cm.getResultCode()) 
					&& "010003".equals(bm.getTranCode().trim())) {
				tradeDao.updateTradeStatus(bm.getTranDate(), bm.getOldPbSeqno(), 
						GlobalConst.TRADE_STATUS_FAILURE);	
			}			
			return;
		}
			
		dealBusiness(cm, bm);
		
		
	}

	/**
	 * 修改流水表状态
	 */
	protected boolean editTradeState(ControlMessage cm, BusinessMessage bm) {
		logger.info("修改流水状态");
		logger.info("交易日期：[{}],交易流水：[{}]。" , bm.getTranDate(), bm.getPbSeqno());
		// 查询原流水信息
		TbBiTrade trade = tradeDao.findTrade(bm);
		if (trade == null) {
			logger.info("查询流水表出错！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("");
			bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
			bm.setResponseMsg("查询流水表出错！");
			return false;
		}
		// 流水状态根据第三方返回修改
		logger.info("日期：[{}],流水号：[{}],原流水状态：[{}],第三方返回流水状态：[{}]。交易流程控制状态：[{}]",
				new Object[] { trade.getId().getTradeDate(),
						trade.getId().getPbSerial(), trade.getStatus(),
						cm.getServiceResultCode(), cm.getResultCode() });

		// 设置流水状态为第三方状态
		String status = null;
		if (GlobalConst.CHANEL_TYPE.ELEBUSIREQUEST.equals(bm.getChanelType())) {
			if("000806".equals(bm.getTranCode())){
				if (cm.getServiceResultCode() == null) {//调用电商异常
					status = "99";
				} else if (GlobalConst.RESULTCODE_SUCCESS.equals(cm.getServiceResultCode())){
//						&& GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {//只要有电商响应码，这个一定是"000000"
					status = "00";
				}else {
					status ="01";
				}
			}else if (GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {
				status = "00";
			}else {
				status = "01";
			}
		} else {
			if (cm.getServiceResultCode() == null  ){   //调用电商异常
				status = "99";
			} else if (GlobalConst.RESULTCODE_SUCCESS.equals(cm
					.getServiceResultCode())) {
				if( GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())) {
					status = GlobalConst.TRADE_STATUS_SUCCESS;		
				}else{//pos超时后电商才响应
					status = "99";
				}
			} else {
				status = GlobalConst.TRADE_STATUS_FAILURE;
			}
		}

		if (trade.getStatus().equals(status) == true) {
			return true;
		}
		trade.setStatus(status);
		trade.setSystemDate(bm.getMidPlatformDate());
		trade.setSystemSerial(bm.getSysJournalSeqno());

		// 更新流水表
		// boolean suc = dao.updateTrade(trade);
		String sql = "update TbBiTrade set status = '" + trade.getStatus()
				+ "' ,systemDate= '" + trade.getSystemDate()
				+ "' ,systemSerial= '" + trade.getSystemSerial()
				+ "' where id.tradeDate = '" + trade.getId().getTradeDate()
				+ "' and id.pbSerial = '" + trade.getId().getPbSerial() + "' ";
		boolean suc = tradeDao.updateTrade(sql);
		if (suc != true) {
			logger.info("修改流水状态失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("流水状态更新失败！");
			bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
			bm.setResponseMsg("流水状态更新失败！");
			bm.setPbState(GlobalConst.TRADE_STATUS_CARD_ORIG);
			return false;
		}
		bm.setPbState(status);
		logger.info("流水状态更新成功！");
		return true;

	}

	/**
	 * 删除缴费上限授权表
	 */
	public void deleteAuthorizeAmount(ControlMessage cm, BusinessMessage bm) {
		logger.info("删除缴费上限授权表！");
		String sql = null;
		String COMPANY_CODE = null;
		String BUSI_CODE = null;
		String CUSTOMERNO = null;
		Double AMOUNTMAX = 0.00;
		String AUTH_DATE = null;

		COMPANY_CODE = bm.getShopCode();
		BUSI_CODE = bm.getTranCode().substring(0, 2);
		CUSTOMERNO = bm.getUserCode();
		AMOUNTMAX = bm.getAmount();
		AUTH_DATE = bm.getTranDate();

		sql = "delete from TB_BI_COMPANY_AUTHORIZE WHERE COMPANY_CODE = '"
				+ COMPANY_CODE + "' AND BUSI_CODE = '" + BUSI_CODE
				+ "' AND CUSTOMERNO = '" + CUSTOMERNO + "' AND AMOUNTMAX = '"
				+ AMOUNTMAX + "' AND AUTH_DATE = '" + AUTH_DATE + "'";
		logger.info("sql：[{}]" , sql);
		try {
			baseHibernateDao.excuteSQL(sql);
		} catch (Exception e) {
			logger.info("删除缴费上限授权表失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("删除缴费上限授权表失败！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("流水状态更新失败！");
			logger.error("delete TB_BI_COMPANY_AUTHORIZE err", e);
		}
		logger.info("删除缴费上限授权表成功！");
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		cm.setResultMsg("");
	}

	public abstract void dealBusiness(ControlMessage cm, BusinessMessage bm);

}
