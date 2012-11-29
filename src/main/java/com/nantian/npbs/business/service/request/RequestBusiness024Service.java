package com.nantian.npbs.business.service.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HeNDElecICCard;

/**
 * 取消写卡
 * @author MDB
 *
 */
@Scope("prototype")
@Component
public class RequestBusiness024Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness024Service.class);
	
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		//进行公共检查
		if(!checkShopState(cm,bm)){   // 检查商户状态
			return false;
		}
		if(!checkSignState(cm,bm)){   // 检查商户签到
			return false;
		}
		if(!checkShopBindBusiness(cm,bm)){ // 检查商户是否有该业务
			return false;
		}
		
		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("补写卡交易请求处理开始!");
		
		//华电IC卡
		if ("013024".equals(bm.getTranCode()) ) {
			
			//检查缴费流水
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			if(oriTrade==null){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,请重新输入!");
				logger.error("取消交易失败！用户录入流水号[{}]" , bm.getOldPbSeqno());
				return;
			}
			if(!GlobalConst.TRADE_TYPE_JF.equals(oriTrade.getTradeType())){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,该流水不是缴费流水!请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,该流水不是缴费流水!请重新输入!");
				logger.info("取消交易失败!用户录入流水号[{}]!该笔流水不是缴费流水!交易时间[{}],PB流水[{}]",bm.getTranDate(),bm.getOldPbSeqno());
				return;
			}
			
			if(!checkOldTradeUserCode(oriTrade,cm,bm) || !checkOldTradeAmount(oriTrade,cm,bm)){
				return;
			}
			
			// 取消授权标志  0-未授权；1-已授权
			logger.info("取消授权标志:[{}]" , oriTrade.getCancelflag());
			if(oriTrade.getCancelflag().equals(GlobalConst.TRADE_CANCEL_FLAG_NO)){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("该取消交易没有授权,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("该取消交易没有授权,请拨打客服电话咨询!");
				logger.error("该取消交易没有授权!用户输入流水号[{}] | 原流水状态[{}]",
						new Object[]{bm.getOldPbSeqno() , oriTrade.getStatus(),});
				return;
			}
			
			//通过缴费流水 查 写卡电子商务平台流水
			StringBuffer sb = new StringBuffer();
			sb.append("select t.system_serial from tb_bi_trade t where t.pb_serial = ");
				sb.append(" (select tc.pb_serial from tb_bi_trade_contrast tc");
					sb.append(" where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"'");
					//流水对照类型为03 - “写卡”
					sb.append(" and tc.trade_type='"+GlobalConst.TRADE_TYPE_XK+"' )");
			//成功状态的缴费交易
			sb.append(" and t.status = '"+GlobalConst.TRADE_STATUS_SUCCESS+"'");
			
			List list = tradeDao.queryBySQL(sb.toString());
			if(null != list && list.size() == 1 && list.get(0)!= null){
				//取写卡电商流水,供Tuxedo打包使用
				bm.setOrigPbSeqno(list.get(0).toString());
				logger.info("上笔写卡流水[{}]",bm.getOrigPbSeqno());
			}else{
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("系统无上笔写卡流水，请到电力营业厅处理!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("系统无上笔写卡流水，请到电力营业厅处理!");
			}
			
			//设置登记流水并修改流水状态
			bm.setSeqnoFlag("1");
			
		}else if("010024".equals(bm.getTranCode())) {  //如果是河电省标卡取消申请写卡数据
			
			//数据必要检查
			if(this.checkNullExceptionString(bm.getTranDate()) ||
					this.checkNullExceptionString(bm.getOldPbSeqno())) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
						GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
						"数据异常！");
				return;
			}
			
			//查询缴费流水		
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			
			//校验是否有该交易
			if(oriTrade==null){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,请重新输入!");
				logger.error("取消交易失败！用户录入流水号[{}]" , bm.getOldPbSeqno());
				return;
			}
			
			//校验该交易是否为缴费交易
			if(!GlobalConst.TRADE_TYPE_JF.equals(oriTrade.getTradeType())){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,该流水不是缴费流水!请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,该流水不是缴费流水!请重新输入!");
				logger.info("取消交易失败!用户录入流水号[{}]!该笔流水不是缴费流水!交易间[{}],PB流水[{}]",bm.getTranDate(),bm.getOldPbSeqno());
				return;
			}
			
			//检查该缴费交易是否为取消中
			if(!GlobalConst.TRADE_CANCEL_ING.equals(oriTrade.getStatus())) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号状态非成功!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号状态非成功!");
				logger.info("取消交易失败!用户录入流水号[{}]!该笔流水状态不为成功!{}",oriTrade.getStatus());
				return;
			}
			
			//设置登记金额及酬金用于保存流水
			bm.setAmount(oriTrade.getAmount());
			bm.setSalary(oriTrade.getSalary());
			bm.setUserName(oriTrade.getCustomername());
						
			// 设置原始电子商务平台缴费流水
			bm.setSysJournalSeqno(oriTrade.getSystemSerial());
			
			TbBiTradeContrast  tbBiTradeContrast = null;
			TbBiTrade  tbBiTradeCancle = null;
			//提取原缴费交易对应取消交易流水对照
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
			
			//设置发送电商取消流水
			if(this.checkNullExceptionString(tbBiTradeCancle.getSystemSerial())) {
				this.setResultMsg(cm, bm, 
						GlobalConst.RESPONSECODE_FAILURE, "数据异常！", 
						GlobalConst.RESULTCODE_FAILURE, "数据异常！", 
						"数据异常！");
				return;
			}
			bm.setOrigSysJournalSeqno(tbBiTradeCancle.getSystemSerial());
			
			//设置登记流水
			bm.setSeqnoFlag("1");
			
			/**
			 * 检查是否重复撤销写卡，否则登记撤销写卡对照，是则更新写卡对照
			 */
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
			}
			
			if(null != list && list.size() == 1){
				Integer number = Integer.valueOf(list.get(0).toString().trim());
				
				if(number == 0){         //首次写卡新增缴费、写卡关联
					TbBiTradeContrast tc = new TbBiTradeContrast();
					TbBiTradeContrastId id = new TbBiTradeContrastId();
					id.setOriPbSerial(bm.getOldPbSeqno());//POS上送缴费流水
					id.setPbSerial(bm.getPbSeqno());//取消写卡流水
					id.setTradeDate(bm.getTranDate());
					tc.setId(id);
					
					logger.info("写卡流水对照类型[{}]" , GlobalConst.TRADE_TYPE_QXXK);
					tc.setTradeType(GlobalConst.TRADE_TYPE_QXXK);
					
					baseHibernateDao.save(tc);				
				} else if(number == 1){            //非首次写卡，更新缴费、写卡关联
					StringBuffer update = new StringBuffer();
					update.append("update tb_bi_trade_contrast tc set tc.pb_serial='"+bm.getPbSeqno()+"' " +
							"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
							"and tc.trade_type='"+GlobalConst.TRADE_TYPE_QXXK+"'");
					try {
						if(tradeDao.updateTradeContrast(update.toString())){							
						}else{
							bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
							bm.setResponseMsg("取消写卡失败!系统错误:更新取消、撤卡关联失败!");
							cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
							cm.setResultMsg("取消写卡失败!系统错误:更新取消、撤卡关联失败!");
						}
					} catch (Exception e) {
						e.printStackTrace();
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("取消写卡失败!系统错误:更新取消、撤卡关联异常!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("取消写卡失败!系统错误:更新取消、撤卡关联异常!");
						logger.error("取消写卡失败!系统错误:更新缴费、写卡关联异常!!SQL[{}]",update.toString());
					}
				}else{      //其它视为异常!
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("取消写卡失败!系统错误:出现多笔撤卡关联!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("取消写卡失败!系统错误:出现多笔缴撤卡关联!");					
				}
			}			
		}
		//add by fengyafang 20120827 农电缴费取消
		else if("018024".equals(bm.getTranCode())){
			logger.info("查询原交易流水！交易日期[{}];原交易流水号[{}]" , 
					new Object[]{bm.getTranDate(),bm.getOldPbSeqno()});
			
			TbBiTrade oriTrade = tradeDao.getTradeById(bm.getTranDate(), bm.getOldPbSeqno());
			if(oriTrade==null){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原流水号输入有误,请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原流水号输入有误,请重新输入!");
				logger.error("取消交易失败！用户录入流水号[{}],用户号[{}],缴费金额[{}]" ,
						new Object[]{bm.getOldPbSeqno(), bm.getUserCode(), bm.getAmount()});
				return;
			}
			
 
			// 取消授权标志  0-未授权；1-已授权
			logger.info("取消授权标志:[{}]" , oriTrade.getCancelflag());
			if(oriTrade.getCancelflag().equals(GlobalConst.TRADE_CANCEL_FLAG_NO)){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("该取消交易没有授权,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("该取消交易没有授权,请拨打客服电话咨询!");
				logger.error("该取消交易没有授权!用户输入流水号[{}],用户号[{}],,缴费金额[{}] | 原流水状态[{}]",
						new Object[]{bm.getOldPbSeqno(),bm.getUserCode(), bm.getAmount(),oriTrade.getStatus(),});
				return;
			}	 	
			//设置一般信息
			bm.setBusinessType(oriTrade.getBusiCode());		
			bm.setUserName(oriTrade.getCustomername());
			
			// 原交易流水商户号
			logger.info("原交易流水商户号:[{}]" , oriTrade.getCompanyCode());
			if(!bm.getShopCode().equals(oriTrade.getCompanyCode())){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("商户号不符！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户号不符！");
				logger.error("商户号不符，原交易流水商户号:[{}]" , oriTrade.getCompanyCode());
				return;
			}
			
			//检查原交易状态、用户编号、缴费金额
			logger.info("原交易状态:[{}];原交易用户编号:[{}];原交易缴费金额:[{}];" ,
					new Object[]{oriTrade.getStatus(),oriTrade.getCustomerno(),oriTrade.getAmount()});
			
			if(!checkOldTradeState(oriTrade,cm,bm)||
					!checkOldTradeUserCode(oriTrade,cm,bm)||
					!checkOldTradeAmount(oriTrade,cm,bm)){
				return;
			}
			
			//登记流水与原流水对照
			logger.info("登记取消流水与原流水对照。交易日期[{}];原流水号[{}];取消交易流水号[{}];" ,
					new Object[]{bm.getTranDate(),bm.getOldPbSeqno(),bm.getPbSeqno()});
			
			TbBiTradeContrast tc = new TbBiTradeContrast();
			TbBiTradeContrastId id = new TbBiTradeContrastId();
			id.setOriPbSerial(bm.getOldPbSeqno());
			id.setPbSerial(bm.getPbSeqno());
			id.setTradeDate(bm.getTranDate());
			tc.setId(id);
			// 01-缴费交易；02-取消交易；03-写卡；04-冲正交易；05-写卡成功确认；06-写卡失败确认
			tc.setTradeType("02");
			bm.setTranType("02");
			baseHibernateDao.save(tc);
			
			// 设置电子商务平台流水
			bm.setSysJournalSeqno(oriTrade.getSystemSerial()); 
			bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
			// 设置取消原交易类型
			cm.setCancelBusinessType(oriTrade.getBusiCode());
			//更新原交易状态,并修改授权标志为未授权
			logger.info("预修改原交易状态为:[{}];预修改原交易授权标志为:[{}];" , GlobalConst.TRADE_CANCEL_ING,GlobalConst.TRADE_CANCEL_FLAG_NO);
			boolean suc = tradeDao.updateTradeStatus(oriTrade.getId().getTradeDate(), oriTrade
					.getId().getPbSerial(),GlobalConst.TRADE_CANCEL_ING,oriTrade.getSystemSerial(),GlobalConst.TRADE_CANCEL_FLAG_NO);
			if (suc != true) {
				logger.info("修改流水状态失败！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("流水状态更新失败！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("流水状态更新失败！");
				return;
			}
			logger.info("流水状态更新成功！");
			//设置用户号
			bm.setUserCode(oriTrade.getCustomerno());
			
			// 设置一些交易信息		
			bm.setAmount(oriTrade.getAmount()); 		//交易金额
			bm.setSalary(oriTrade.getSalary());  		//酬金金额
			bm.setCancelFlag(GlobalConst.TRADE_CANCEL_FLAG_NO);
			bm.setTax(oriTrade.getTax());
			bm.setDepreciation(oriTrade.getDepreciation());
			bm.setOther(oriTrade.getOther());
			bm.setPayType(oriTrade.getPayType());
			bm.setPrePayAccno(oriTrade.getAccno());
			bm.setOrigSysJournalSeqno(oriTrade.getSystemSerial());
			//获得取消前查询的信息
			  
			TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,bm.getNdzhuanyong());
			HeNDElecICCard customData=null;
			if (bm.getCustomData() != null) {
				customData = (HeNDElecICCard) bm.getCustomData();
			} else {
				customData = new HeNDElecICCard();
			}
				String[] split = String.valueOf(cashTemp.getTempValue()).split("\\^");
				 customData.setCHECK_ID(split[0]);    
				  customData.setCONS_NO(split[1]);     
				 customData.setMETER_ID(split[2]);    
				 customData.setMETER_FLAG(split[3]);  
				 customData.setCARD_INFO(split[4]);   
				 customData.setIDDATA(split[5]);      
				 customData.setCONS_NAME(split[6]);   
				 customData.setCONS_ADDR(split[7]) ;  
				 customData.setPAY_ORGNO(split[8]);   
				 customData.setORG_NO(split[9]) ;     
				 customData.setCHARGE_CLASS(split[10]);
				 customData.setFACTOR_VALUE(split[11]);
				 customData.setPURP_PRICE(split[12])  ;
				 customData.setCARD_NO(split[13])  ;
				 customData.setOCS_MODE(split[14])  ;
				 customData.setPRESET_VALUE(split[15])  ;
				bm.setCustomData(customData); 
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("");
			bm.setSeqnoFlag("1");
		}
		else{
			logger.info("无业务逻辑处理!");
			return;
		}
			
		
	}
	
	/**
	 * 检查原交易状态
	 */
	protected boolean checkOldTradeState(TbBiTrade oriTrade,ControlMessage cm,BusinessMessage  bm){
		if(oriTrade.getStatus().trim().equals(GlobalConst.TRADE_STATUS_SUCCESS)){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("取消交易失败,该状态不能取消!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("取消交易失败,该状态不能取消,请拨打客服电话咨询!");
			logger.error("取消交易失败,原状态不能取消!用户录入平台流水号[{}],用户号[{}],缴费金额[{}] | 原流水状态为[{}]",
					new Object[]{ bm.getOldPbSeqno(),bm.getUserCode(), bm.getAmount(), oriTrade.getStatus()});
			return false;
		}
	}

	/**
	 * 09-撤销写卡
	 */
	protected String tradeType(){
		return GlobalConst.TRADE_TYPE_QXXK;
	}
	
	/**
	 * 检查原交易用户号码
	 */
	protected boolean checkOldTradeUserCode(TbBiTrade oriTrade,ControlMessage cm, BusinessMessage bm){
		if(oriTrade.getCustomerno().equals(bm.getUserCode() )){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("用户编号输入有误!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("用户编号输入有误!");
			logger.error("用户号"+oriTrade.getCustomerno()+"bm.getusercode()"+bm.getUserCode());
			logger.error("取消交易失败,用户编号输入有误!流水状态为:" + oriTrade.getStatus() + "用户号:" + bm.getUserCode() 
					+  ",平台流水号:" + bm.getOldPbSeqno() + ",缴费金额:" + bm.getAmount()  );
			return false;
		}
	}
	
	/**
	 * 检查原交易缴费金额
	 */
	protected boolean checkOldTradeAmount(TbBiTrade oriTrade, ControlMessage cm, BusinessMessage bm){
		if(oriTrade.getAmount().equals(bm.getAmount() )){
			return true;
		}else{
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费金额输入有误!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费金额输入有误!");
			logger.error("取消交易失败,缴费金额输入有误!流水状态为[{}]" + oriTrade.getStatus() + "用户号[{}]" + bm.getUserCode() 
					+  ",平台流水号[{}]" + bm.getOldPbSeqno() + ",缴费金额[{}]" + bm.getAmount()  );
			return false;
		}
	}

	@Override
	public boolean needLockProcess() {
		//发送第三方，需要进程控制
		return true;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		//不登记流水
		return;
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag("1");
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
