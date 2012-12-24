package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness004Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness004Service.class);
	// 流水操作
	@Resource
	public TradeDao dao;
	// 备付金
	@Resource
	public CommonPrepay commonPrepay;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("补写卡交易响应处理开始！无业务逻辑。");
	}

	public void dealBusiness_bak(ControlMessage cm, BusinessMessage bm) {
		logger.info("写卡确认交易响应处理!");
		boolean suc = true;
		if ("01".equals(bm.getWriteICResultType())) {
			// 确认类型为01的
			logger.info("写卡确认类型 01-成功确认");
			if ("00".equals(cm.getServiceResultCode())) {
				logger.info("第三方交易成功！修改写卡成功确认流水状态为00");
				suc = dao.updateTradeStatus(bm.getTranDate(), bm.getPbSeqno(),
						"00",bm.getSysJournalSeqno());
				if (suc == false) {
					logger.info("修改原流水状态失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("修改原流水状态失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("修改原流水状态失败！");
					return;
				}
			} else {
				logger.info("第三方交易不成功！");
				return;
			}
		}
		if ("02".equals(bm.getWriteICResultType())) {
			// 确认类型为02的
			logger.info("写卡确认类型 02-失败确认");
			if ("00".equals(cm.getServiceResultCode())) {

				String accNo = bm.getPrePayAccno();
				Double amt = bm.getAmount();
				String retCode = GlobalConst.RESULTCODE_SUCCESS;
				amt = -amt;
				logger.info("第三方交易成功！修改原流水状态为06-写卡失败");
				suc = dao.updateTradeStatus(bm.getOrigLocalDate(), bm
						.getOrigPbSeqno(), "06",bm.getSysJournalSeqno());
				if (suc == false) {
					logger.info("修改原流水状态失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("修改原流水状态失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("修改原流水状态失败！");
					return;
				}
				logger.info("第三方交易成功！修改写卡失败确认流水状态为00");
				suc = dao.updateTradeStatus(bm.getTranDate(), bm.getPbSeqno(),
						"00",bm.getSysJournalSeqno());
				if (suc == false) {
					logger.info("修改流水状态失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("修改流水状态失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("修改流水状态失败！");
					return;
				}

				// 备付金处理
				logger.info("备付金处理！");
				retCode = commonPrepay.withdrawalsPrepay(accNo, amt,bm);
				if (GlobalConst.RESULTCODE_SUCCESS.equals(retCode) != true) {
					logger.info("修改备付金余额失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("修改备付金余额失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("修改备付金余额失败！");
					return;
				}
			} else if ("01".equals(cm.getServiceResultCode())) {
				logger.info("第三方交易失败！修改写卡失败确认流水状态为01");
				suc = dao.updateTradeStatus(bm.getTranDate(), bm.getPbSeqno(),
						"01",bm.getSysJournalSeqno());
				if (suc == false) {
					logger.info("修改流水状态失败！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("修改流水状态失败！");
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("修改流水状态失败！");
					return;
				}
			}
		}

		// 以下根据C代码移植，现均删除
		// 此处用到55位元中的数据做条件判断
		// GetPoolDataByName("POS8583","Reserve055",0,0,szYYSJY,&rellen);
		// memcpy(szLSH, szYYSJY, 14);
		// memcpy(szZHT, szYYSJY + 16, 2);
		// memcpy(szCGBZ, szYYSJY + 14, 2);
		// String szLSH = bm.getCustomData().toString().substring(0, 14);//流水号
		// String szZHT = bm.getCustomData().toString().substring(16, 18);
		// String szCGBZ = bm.getCustomData().toString().substring(14, 16);

		// 取出响应码
		// String resCode = bm.getResponseCode();
		//		
		// //业务处理
		// if(szZHT.equals("00")){
		// if(szCGBZ.equals("01")){
		// if(resCode.equals("00")){
		// String sql =
		// "UPDATE TB_BI_TRADE SET STATUS = '00' WHERE PB_SERIAL = '"+szLSH+"'";
		// logger.info("CGBZ=01");
		// logger.info("sql=["+sql+"]"+getClass().getSimpleName());
		// try{
		// baseHibernateDao.excuteSQL(sql);
		// }catch(Exception e){
		// //PutPoolData("POS8583", 42, 0, "99");设置响应码
		// bm.setResponseCode("99");
		// cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		// cm.setResultMsg("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// logger.error("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// }
		// }
		// }else{
		// if(resCode.equals("00")){
		// String sql =
		// "UPDATE TB_BI_TRADE SET STATUS = '01' WHERE PB_SERIAL = '"+szLSH+"'";
		// logger.info("sql=["+sql+"]"+getClass().getSimpleName());
		// try{
		// baseHibernateDao.excuteSQL(sql);
		// }catch(Exception e){
		// //PutPoolData("POS8583", 42, 0, "99");
		// //PutPoolData("POS8583", 47, 0, "系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// bm.setResponseCode("99");
		// bm.setResponseMsg("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		// cm.setResultMsg("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// logger.error("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// }
		// }
		// }
		// } else {
		// if(szCGBZ.equals("01")){
		// if(resCode.equals("00")){
		// String sql =
		// "UPDATE TB_BI_TRADE SET STATUS = '03' WHERE PB_SERIAL = '"+szLSH+"'";
		// logger.info("sql=["+sql+"]"+getClass().getSimpleName());
		// try{
		// baseHibernateDao.excuteSQL(sql);
		// }catch(Exception e){
		// //PutPoolData("POS8583", 42, 0, "99");
		// //PutPoolData("POS8583", 47, 0, "系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// bm.setResponseCode("99");
		// bm.setResponseMsg("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		// cm.setResultMsg("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// logger.error("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
		// }
		// }
		// }
		// }
		//		
		// bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		// cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		// logger.info("河电IC卡写卡确认打包前处理完成!程序继续执行!");

	}


}
