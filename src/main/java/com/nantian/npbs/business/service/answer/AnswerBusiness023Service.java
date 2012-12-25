package com.nantian.npbs.business.service.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 补写卡
 * @author
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness023Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness023Service.class);
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		if("010023".equals(bm.getTranCode())) {
			//如果需要补写原交易状态为待写卡状态则更新其状态为成功
			if(!this.setNeedWriteToSuccussStatus(cm, bm)) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "系统更改流水状态出错！", 
						GlobalConst.RESULTCODE_FAILURE, "系统更改流水状态出错！", 
						"流水待写卡更新为成功状态出错!");
			}
			
		}else {
			logger.info("补写卡交易响应处理开始！无业务逻辑。");
		}
		
		

	}
	
	/**
	 * 将一笔缴费或取消流水状态从待写卡状态更新为成功状态
	 * @param cm     控制类
	 * @param bm     业务类  欲更新流水日期：bm.getTrade
	 * @return
	 */
	public boolean setNeedWriteToSuccussStatus(ControlMessage cm,BusinessMessage bm) {
		
		//检查需要更新流水状态的流水日期与流水号是否为空
		if(this.checkNullExceptionString(bm.getTranDate())||
				this.checkNullExceptionString(bm.getOldPbSeqno())) {
			this.setResultMsg(cm, bm, 
					GlobalConst.RESPONSECODE_FAILURE, "系统流水、日期数据异常！", 
					GlobalConst.RESULTCODE_FAILURE, "系统流水、日期数据异常！", 
					"系统流水、日期数据异常！");
			return false;
		}
		
		//提取待更新状态的原交易流水
		TbBiTrade tbBiTrade = this.tradeDao.getTradeById("20"+bm.getOldPbSeqno().trim().substring(0,6),bm.getOldPbSeqno());
		
		if(null != tbBiTrade) {
			if(GlobalConst.TRADE_STATUS_NEED_WRITE.equals(tbBiTrade.getStatus())) {
				this.tradeDao.updateTradeStatus("20"+bm.getOldPbSeqno().trim().substring(0,6), bm.getOldPbSeqno(), 
						GlobalConst.TRADE_STATUS_SUCCESS);				
			}
			return true;
		}else{
			this.setResultMsg(cm, bm, 
					GlobalConst.RESPONSECODE_FAILURE, "流水不存在！", 
					GlobalConst.RESULTCODE_FAILURE, "流水不存在！", 
					"流水不存在！");
			return false;
		}	
	}

	
	/**
	 *判断字符串是否为null或空字符串
	 * @param str   需要判断的字符串
	 * @return 为空为true  否则为false
	 */
	public boolean checkNullExceptionString(String str) {
		if(null == str || "".equals(str)) {
			return true;
		}else {
			return false;
		}			
	}
	
	/**
	 * 设置应答码、应答信息、结果码、结果信息、日志信息
	 * @param cm
	 * @param bm
	 * @param resposeCode  应答码
	 * @param resposeMsg   应答信息
	 * @param resultCode   结果码
	 * @param resultMsg    结果信息
	 * @param logMsg       日志信息
	 */
	public  void setResultMsg(ControlMessage cm, BusinessMessage bm,
			String resposeCode,String resposeMsg,
			String resultCode,String resultMsg,String logMsg) {
		bm.setResponseCode(resposeCode);
		bm.setResponseMsg(resposeMsg);
		cm.setResultCode(resultCode);
		cm.setResultMsg(resultMsg);
		logger.info(logMsg);
		
	}
	


}
