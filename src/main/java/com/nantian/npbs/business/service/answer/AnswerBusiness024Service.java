package com.nantian.npbs.business.service.answer;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 申请补写卡数据
 * @author
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness024Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness024Service.class);
	
	@Resource
	private CommonPrepay commonPrepay;
	
	@Override
	public void execute(ControlMessage cm, BusinessMessage bm) {
		
		logger.info("开始执行响应处理");
		// 由于流水是一个公共处理，不论失败成功均需要处理
		if ("1".equals(bm.getSeqnoFlag())) {
			editTradeState(cm, bm);
		}
		
		dealBusiness(cm, bm);
	}	
	
	@Override
	public void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("取消交易响应处理!");
		
		//如果是河电省标撤销申请写卡脚本 ,则更改撤销流水状态为成功
		if("010024".equals(bm.getTranCode())) {
			if(this.checkNullExceptionString(bm.getTranDate())||			
					this.checkNullExceptionString(bm.getOldPbSeqno())) {
				this.setResultMsg(cm, bm,
						GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
						GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
						"数据异常！");
			}
			//提取缴费交易流水
			TbBiTrade trade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			
			if(trade==null){
				logger.info("查询原交易流水错误,原交易流水号:{},交易日期:{}" , bm.getOldPbSeqno() , bm.getTranDate());
				return;
			}

			//判断缴费交易是否被取消
			if(!GlobalConst.TRADE_CANCEL_ING.equals(trade.getStatus())){
				logger.info("交易状态不正确!pb流水[{}];交易日期[{}],交易状态[{}]" , new Object[]{bm.getPbSeqno() ,bm.getTranDate(),trade.getStatus()});
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				bm.setResponseMsg("取消交易申请写卡失败!请拨打客服电话咨询!");
				cm.setResultMsg("取消交易申请写卡失败!请拨打客服电话咨询!");
				return ;
			}		
			
			bm.setAmount(trade.getAmount());			
			
			if(GlobalConst.RESULTCODE_SUCCESS.equals(cm.getServiceResultCode())) {
				//回退备付金账户
				logger.info("取消交易!回退备付金账户!备付金帐号{};金额{}" , bm.getPrePayAccno(),bm.getAmount());
				if(!commonPrepay.cancelToUpdatePrepay(bm.getPrePayAccno(),bm.getAmount())){
					logger.info("取消交易失败!回退备付金账户金额失败!失败原因:pb流水{};交易日期{}" ,  bm.getPbSeqno() ,bm.getTranDate());
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
					cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
					return;
				}
				
				//查询备付金当前余额
				logger.error("商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
				TbBiPrepay tbBiPrepay = null;
				try {
					Object obj1 = baseHibernateDao.get(TbBiPrepay.class,bm.getPrePayAccno());
					if (obj1 != null) {
						tbBiPrepay = (TbBiPrepay) obj1;
						bm.setPrepay(tbBiPrepay);
						// 设置
						bm.setPreBalance(tbBiPrepay.getAccBalance()); // 备付金余额
					} else {
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("备付金查询出错!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("备付金查询出错!");
						logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"+ bm.getShopCode(),bm.getPrePayAccno());
						return ;
					}
				} catch (Exception e) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("查询您的备付金账户出错,请联系管理员!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("查询您的备付金账户出错,请联系管理员!");
					logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];",bm.getShopCode(),bm.getPrePayAccno());
					return ;
				}
				
				TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
				commonPrepay.setPrepayInfo(prepayInfo, bm, "2");
				if(!commonPrepay.addPrepayInfo(prepayInfo)){
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("备付金明细登记出错,请联系管理员!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金明细登记出错,请联系管理员!");
					logger.error("备付金明细登记出错!商户号: [{}];备付金账号: [{}];", bm.getShopCode(),bm.getPrePayAccno());
					return;
				}
				
				//更新对应当前撤销申请写卡数据流水的缴费交易状态为成功
				this.tradeDao.updateTradeStatus(bm.getTranDate(), trade.getId().getPbSerial(), 
						GlobalConst.TRADE_STATUS_CANCEL);		
				
			}else {			
				
				//更新缴费交易状态为成功
				this.tradeDao.updateTradeStatus(bm.getTranDate(), trade.getId().getPbSerial(), 
						GlobalConst.TRADE_STATUS_SUCCESS);
				
				//更新取消交易流水状态为失败	
				TbBiTradeContrast  tbBiTradeContrast = null;
				TbBiTrade  tbBiTradeCancle = null;
				String sqlCancleConstrast = " from TbBiTradeContrast where id.tradeDate = '"
					+bm.getTranDate() + "' and id.oriPbSerial = '"
					+bm.getOldPbSeqno() + "' and tradeType = '02'";
				List<TbBiTradeContrast>  tbBiTradeContrastList= tradeDao.findTradeContrastList(sqlCancleConstrast);			
				
				if(tbBiTradeContrastList.size() <= 0) {
					this.setResultMsg(cm, bm, 
							GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
							GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
							"数据异常！");
					return;
				}else if(tbBiTradeContrastList.size() == 1) {
					tbBiTradeContrast = tbBiTradeContrastList.get(0);
					
					//提取取消交易流水信息
					if(this.checkNullExceptionString(tbBiTradeContrast.getId().getPbSerial())) {
						this.setResultMsg(cm, bm, 
								GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
								GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
								"数据异常！");
						return;
					}
					
					tbBiTradeCancle = tradeDao.getTradeById(bm.getTranDate(),
							tbBiTradeContrast.getId().getPbSerial());							
				}else {
					for(int i=0; i<tbBiTradeContrastList.size(); i++) {
						tbBiTradeContrast = tbBiTradeContrastList.get(i);
						
						//提取取消交易流水信息
						if(this.checkNullExceptionString(tbBiTradeContrast.getId().getPbSerial())) {
							this.setResultMsg(cm, bm, 
									GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
									GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
									"数据异常！");
							return;
						}
						
						tbBiTradeCancle = tradeDao.getTradeById(bm.getTranDate(),
								tbBiTradeContrast.getId().getPbSerial());
						
						if(null == tbBiTradeCancle) {
							this.setResultMsg(cm, bm, 
									GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
									GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
									"数据异常！");
							return;
						}
						
						if(!GlobalConst.TRADE_STATUS_SUCCESS.equals(tbBiTradeCancle.getStatus())){
							continue;
						}else {
							break;
						}					
					}
				
				}
				
				if(null == tbBiTradeCancle) {
					this.setResultMsg(cm, bm, 
							GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
							GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
							"数据异常！");
					return;
				}
				
				this.tradeDao.updateTradeStatus(bm.getTranDate(), tbBiTradeCancle.getId().getPbSerial(), 
						GlobalConst.TRADE_STATUS_FAILURE);	
				
				return;
			}					
		}
		
		// 如果是华电IC卡则增加写卡流水对照
		if ("013024".equals(bm.getTranCode()) == true) {
			
			//检查是否重复写卡
			StringBuffer sql = new StringBuffer();
			List list = null;
			sql.append(" select count(*) from tb_bi_trade_contrast tc " +
						"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
						"and tc.trade_type='"+GlobalConst.TRADE_TYPE_QXXK+"'");
			try {
				list = tradeDao.queryBySQL(sql.toString());
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("取消写卡失败!系统错误:检测重复撤销写卡错误!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("取消写卡失败!系统错误:检测重复写卡错误!");
				logger.error("取消写卡失败!系统错误:检测重复写卡错误!SQL[{}]",sql.toString());
				e.printStackTrace();
				return;
			}
			if(null != list && list.size() == 1){
				Integer number = Integer.valueOf(list.get(0).toString().trim());
				
				//首次写卡新增缴费、写卡关联
				if(number == 0){
					TbBiTradeContrast tc = new TbBiTradeContrast();
					TbBiTradeContrastId id = new TbBiTradeContrastId();
					id.setOriPbSerial(bm.getOldPbSeqno());//POS上送缴费流水
					id.setPbSerial(bm.getPbSeqno());//取消写卡流水
					id.setTradeDate(bm.getTranDate());
					tc.setId(id);
					
					logger.info("写卡流水对照类型[{}]" , GlobalConst.TRADE_TYPE_QXXK);
					tc.setTradeType(GlobalConst.TRADE_TYPE_QXXK);
					
					baseHibernateDao.save(tc);
					return;
				}
				
				//非首次写卡，更新缴费、写卡关联
				if(number == 1){
					StringBuffer update = new StringBuffer();
					update.append("update tb_bi_trade_contrast tc set tc.pb_serial='"+bm.getPbSeqno()+"' " +
							"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
							"and tc.trade_type='"+GlobalConst.TRADE_TYPE_QXXK+"'");
					try {
						if(tradeDao.updateTradeContrast(update.toString())){
							return;
						}else{
							bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
							bm.setResponseMsg("取消写卡失败!系统错误:更新取消、撤卡关联失败!");
							cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
							cm.setResultMsg("取消写卡失败!系统错误:更新取消、撤卡关联失败!");
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("取消写卡失败!系统错误:更新取消、撤卡关联异常!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("取消写卡失败!系统错误:更新取消、撤卡关联异常!");
						logger.error("取消写卡失败!系统错误:更新缴费、写卡关联异常!!SQL[{}]",update.toString());
						return;
					}
				}
				//其它视为异常!
				else{
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("取消写卡失败!系统错误:出现多笔撤卡关联!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("取消写卡失败!系统错误:出现多笔缴撤卡关联!");
					return;
				}
			}
		}
		//农电取消
		else if ("018024".equals(bm.getTranCode())){
			 
			// 提取缴费交易流水
			TbBiTrade trade = tradeDao.getTradeById(bm.getTranDate(), bm
					.getOldPbSeqno());

			if (trade == null) {
				logger.info("查询原交易流水错误,原交易流水号:{},交易日期:{}", bm.getOldPbSeqno(),
						bm.getTranDate());
				return;
			}

			// 判断缴费交易是否被取消
			if (!GlobalConst.TRADE_CANCEL_ING.equals(trade.getStatus())) {
				logger.info("交易状态不正确!pb流水[{}];交易日期[{}],交易状态[{}]", new Object[] {
						bm.getPbSeqno(), bm.getTranDate(), trade.getStatus() });
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				bm.setResponseMsg("取消交易申请写卡失败!请拨打客服电话咨询!");
				cm.setResultMsg("取消交易申请写卡失败!请拨打客服电话咨询!");
				return;
			}

			bm.setAmount(trade.getAmount());

			if (GlobalConst.RESULTCODE_SUCCESS
					.equals(cm.getServiceResultCode())) {
				// 回退备付金账户
				logger.info("取消交易!回退备付金账户!备付金帐号{};金额{}", bm.getPrePayAccno(),
						bm.getAmount());
				if (!commonPrepay.cancelToUpdatePrepay(bm.getPrePayAccno(), bm
						.getAmount())) {
					logger.info("取消交易失败!回退备付金账户金额失败!失败原因:pb流水{};交易日期{}", bm
							.getPbSeqno(), bm.getTranDate());
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					bm.setResponseMsg("取消交易失败!请拨打客服电话咨询!");
					cm.setResultMsg("取消交易失败!请拨打客服电话咨询!");
					return;
				}

				// 查询备付金当前余额
				logger.error("商户号: [{}];备付金账号: [{}];" + bm.getShopCode(), bm
						.getPrePayAccno());
				TbBiPrepay tbBiPrepay = null;
				try {
					Object obj1 = baseHibernateDao.get(TbBiPrepay.class, bm
							.getPrePayAccno());
					if (obj1 != null) {
						tbBiPrepay = (TbBiPrepay) obj1;
						bm.setPrepay(tbBiPrepay);
						// 设置
						bm.setPreBalance(tbBiPrepay.getAccBalance()); // 备付金余额
					} else {
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("备付金查询出错!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("备付金查询出错!");
						logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];"
								+ bm.getShopCode(), bm.getPrePayAccno());
						return;
					}
				} catch (Exception e) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("查询您的备付金账户出错,请联系管理员!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("查询您的备付金账户出错,请联系管理员!");
					logger.error("备付金查询出错!商户号: [{}];备付金账号: [{}];", bm
							.getShopCode(), bm.getPrePayAccno());
					return;
				}

				TbBiPrepayInfo prepayInfo = new TbBiPrepayInfo();
				commonPrepay.setPrepayInfo(prepayInfo, bm, "2");
				if (!commonPrepay.addPrepayInfo(prepayInfo)) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("备付金明细登记出错,请联系管理员!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金明细登记出错,请联系管理员!");
					logger.error("备付金明细登记出错!商户号: [{}];备付金账号: [{}];", bm
							.getShopCode(), bm.getPrePayAccno());
					return;
				}

				// 更新对应当前撤销申请写卡数据流水的缴费交易状态为成功
				this.tradeDao.updateTradeStatus(bm.getTranDate(), trade.getId()
						.getPbSerial(), GlobalConst.TRADE_STATUS_CANCEL);

			} else {

				// 更新缴费交易状态为成功
				this.tradeDao.updateTradeStatus(bm.getTranDate(), trade.getId()
						.getPbSerial(), GlobalConst.TRADE_STATUS_SUCCESS);

				// 更新取消交易流水状态为失败
				TbBiTradeContrast tbBiTradeContrast = null;
				TbBiTrade tbBiTradeCancle = null;
				String sqlCancleConstrast = " from TbBiTradeContrast where id.tradeDate = '"
						+ bm.getTranDate()
						+ "' and id.pbSerial = '"
						+ bm.getPbSeqno() + "' and tradeType = '02'";
				System.out.println(sqlCancleConstrast.toString());
				List<TbBiTradeContrast> tbBiTradeContrastList = tradeDao
						.findTradeContrastList(sqlCancleConstrast);

				if (tbBiTradeContrastList.isEmpty()) {
					this.setResultMsg(cm, bm, GlobalConst.RESPONSECODE_FAILURE,
							"数据异常！", GlobalConst.RESULTCODE_FAILURE, "数据异常！",
							"数据异常！");
					bm.setResponseMsg(cm.getResultMsg());
					return;
				} else if (tbBiTradeContrastList.size() == 1) {
					tbBiTradeContrast = tbBiTradeContrastList.get(0);

					// 提取取消交易流水信息
					if (this.checkNullExceptionString(tbBiTradeContrast.getId()
							.getPbSerial())) {
						this.setResultMsg(cm, bm,
								GlobalConst.RESPONSECODE_FAILURE, "数据异常！",
								GlobalConst.RESULTCODE_FAILURE, "数据异常！",
								"数据异常！");
						return;
					}

					tbBiTradeCancle = tradeDao.getTradeById(bm.getTranDate(),
							tbBiTradeContrast.getId().getPbSerial());
					bm.setResponseMsg(cm.getResultMsg());
				} else {
					for (int i = 0; i < tbBiTradeContrastList.size(); i++) {
						tbBiTradeContrast = tbBiTradeContrastList.get(i);

						// 提取取消交易流水信息
						if (this.checkNullExceptionString(tbBiTradeContrast
								.getId().getPbSerial())) {
							this.setResultMsg(cm, bm,
									GlobalConst.RESPONSECODE_FAILURE, "数据异常！",
									GlobalConst.RESULTCODE_FAILURE, "数据异常！",
									"数据异常！");
							bm.setResponseMsg(cm.getResultMsg());
							return;
						}

						tbBiTradeCancle = tradeDao.getTradeById(bm
								.getTranDate(), tbBiTradeContrast.getId()
								.getPbSerial());

						if (null == tbBiTradeCancle) {
							this.setResultMsg(cm, bm,
									GlobalConst.RESPONSECODE_FAILURE, "数据异常！",
									GlobalConst.RESULTCODE_FAILURE, "数据异常！",
									"数据异常！");
							bm.setResponseMsg(cm.getResultMsg());
							return;
						}
						if (!GlobalConst.TRADE_STATUS_SUCCESS
								.equals(tbBiTradeCancle.getStatus())) {
							
							continue;
						} else {
							break;
						}
						
					}
					bm.setResponseMsg(cm.getResultMsg());
				}
				if (null == tbBiTradeCancle) {
					this.setResultMsg(cm, bm, GlobalConst.RESPONSECODE_FAILURE,
							"数据异常！", GlobalConst.RESULTCODE_FAILURE, "数据异常！",
							"数据异常！");
					
					return;
				}
				this.tradeDao.updateTradeStatus(bm.getTranDate(),
						tbBiTradeCancle.getId().getPbSerial(),
						GlobalConst.TRADE_STATUS_FAILURE);
				 

				return;
			}
		}

		return;
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
