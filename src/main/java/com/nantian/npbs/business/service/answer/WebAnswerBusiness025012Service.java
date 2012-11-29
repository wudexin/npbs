package com.nantian.npbs.business.service.answer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Scope("prototype")
@Component(value = "WebAnswerBusiness025012Service")
public class WebAnswerBusiness025012Service extends WebAnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(WebAnswerBusiness025012Service.class);

	@Resource
	private CommonPrepay commonPrepay;

	@Resource
	public PrepayDao prepayDao;

	boolean flag = false;

	/**
	 * 业务处理
	 */
	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		// 如果交易成功，备付金处理，加一条备付金流水
	

		logger.info("修改原交易流水状态！");
		// 更新原交易状态
		boolean suc = tradeDao
				.updateTradeStatus(modelSvcReq.getOld_trade_date(),modelSvcReq.getOld_pb_serial(),GlobalConst.TRADE_STATUS_CANCEL,
						modelSvcReq.getOld_web_serial());
		if (suc != true) {
			logger
					.info("更新原交易状态错误,原交易流水号:{},交易日期:{}", new Object[] {
							modelSvcReq.getOld_pb_serial(),
							modelSvcReq.getWeb_date() });
			// 更新原交易状态。
			boolean suc1 = tradeDao.updateTradeStatus(modelSvcReq.getOld_trade_date(),modelSvcReq.getOld_pb_serial(),
					modelSvcReq.getOriTrade().getStatus(),modelSvcReq.getOld_web_serial() );
			if (suc1 != true) {
				logger.info("更新原交易状态错误,原交易流水号:{},交易日期:{},原状态为:{}",
						new Object[] { modelSvcReq.getOld_pb_serial(),
								modelSvcReq.getWeb_date(),
								modelSvcReq.getOriTrade().getStatus() });
				modelSvcAns.setMessage("取消交易失败!请拨打客服电话咨询");
				modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
				return;
			}
			modelSvcAns.setMessage("取消交易失败!请拨打客服电话咨询");
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
			return;
		} else {

			 
			// 回退备付金账户
			logger.info("取消交易!回退备付金账户!备付金帐号{};金额{}", modelSvcReq
					.getCompany_code_fir(), modelSvcReq.getAmount());
			if (!cancelToUpdatePrepay(modelSvcReq.getCompany_code_fir(), Double
					.parseDouble(modelSvcReq.getAmount()),modelSvcAns)) {
				logger.info("取消交易失败!回退备付金账户金额失败!失败原因:pb流水{};交易日期{}", modelSvcReq
						.getPb_serial(), modelSvcReq.getTrade_date());
				modelSvcAns.setMessage("取消交易失败!请拨打客服电话咨询");
				modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
				return;
			}

			TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
			TbBiPrepayInfoId tbPrepayId = new TbBiPrepayInfoId();
			tbPrepayId.setPbSerial(modelSvcReq.getPb_serial());
			tbPrepayId.setTradeDate(modelSvcReq.getTrade_date());
			setPrepayInfo(prepayInfo, tbPrepayId, modelSvcReq, modelSvcReq
					.getCompany_code_fir());
			prepayInfo.setFlag("1");
			prepayInfo.setAmount(Double.parseDouble(modelSvcReq.getAmount()));
			prepayInfo.setBal(Double.parseDouble(modelSvcAns.getAcc_balance_fir()));
			prepayInfo.setSummary("存款");
			prepayInfo.setRemark("交易取消回退");

			if (!commonPrepay.addPrepayInfo(prepayInfo)) {
				modelSvcAns.setMessage("备付金明细登记出错,请联系管理员!");
				modelSvcAns.setStatus(GlobalConst.RESULTCODE_FAILURE);
				logger.error("备付金明细登记出错!商户号: [{}];", modelSvcReq
						.getCompany_code_fir());
				return;
			}
			
			modelSvcAns.setAmount(modelSvcReq.getAmount());
			modelSvcAns.setPb_serial(modelSvcReq.getPb_serial());
			modelSvcAns.setTrade_date(modelSvcReq.getTrade_date());
			modelSvcAns.setCompany_code_fir(modelSvcReq
					.getCompany_code_fir());
			modelSvcAns.setMessage("交易成功!");
			modelSvcAns.setStatus(GlobalConst.RESULTCODE_SUCCESS);
		}
		
		
		
	}
}
