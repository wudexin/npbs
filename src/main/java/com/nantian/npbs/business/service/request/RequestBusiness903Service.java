package com.nantian.npbs.business.service.request;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * POS签到
 * 
 * @author
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness903Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness903Service.class);

	@Resource
	private CommonPrepay commonPrepay;
	
	@Resource
	private EncryptionService encryptionService;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {

		// 商户状态,签到状态
		// 备付金账户是否存在
		// 密码重置状态检查
		// 是否已用信用额度，如果已用打印小票提示商户。

		// 进行公共检查
		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}

		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		if ("000000".equals(cm.getResultCode()) != true) {
			return;
		}
		
		//检查渠道跟后台所设置渠道是否相同
		String type="";
		if("POS".equalsIgnoreCase(bm.getChanelType().toString())){
		type="01";	
		}else if("EPOS".equalsIgnoreCase(bm.getChanelType().toString())){
			type="03";
		}else if("WEB".equalsIgnoreCase(bm.getChanelType().toString())){
			type="02";
		}
		if(!bm.getShop().getChannelCode().equals(type)){
			bm.setResponseCode(GlobalConst.RESULTCODE_FAILURE);
			bm.setResponseMsg("渠道设置不正确");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("渠道设置不正确");
			bm.setAdditionalTip("渠道设置不正确");
			
			return;
		}
		
//		super.initShop(cm, bm);
		TbBiPrepay prepay = null;
		TbBiPrepayLowamount lowAmountPrepay= null;
		TbBiCompany shop = bm.getShop();
		String shopCode = shop.getCompanyCode();		
		

		String prepayAcc = shop.getResaccno();
		if(prepayAcc == null){
			logger.error("该商户未设置备付金账户!商户号=[" + shopCode + "];");
			setRetMsg(cm,bm,"","该商户未设置备付金账户!商户号=[" + shopCode + "];");
			return;
		}

		// 密码重置状态检查。
		String pwdFlag = "";
		if ("1".equals(shop.getPayType()) && shop.getResaccno() != null
				&& !("".equals(shop.getResaccno()))) {
			try {
				prepay = commonPrepay.getPrepay(shop.getResaccno());
				if(prepay == null){
					logger.error("备付金账户不存在!备付金账号=[" + prepayAcc + "];");
					setRetMsg(cm,bm,"","备付金账户不存在!备付金账号=[" + prepayAcc + "];");
					return;
				}
			} catch (Exception e) {
				logger.error("取该商户备付金账户时出错!备付金账号=[" + prepayAcc + "];", e);
				setRetMsg(cm,bm,"","取该商户备付金账户时出错!备付金账号=[" + prepayAcc + "];");
				return;
			}
			pwdFlag = prepay.getPwdFlag();
		}
		
	
		
		//15域增加标志域处理by jxw at 20111017
		String flagField = getFlagField(shop.getPayType(),pwdFlag);
		bm.setFlagField(flagField);
		
		//62域增加密钥信息处理 by jxw at 20111017
		String workKeys = encryptionService.getWorkKey(bm.getShopCode());
		if(workKeys == null){
			logger.error("生成工作密钥失败!");
			setRetMsg(cm,bm,"","生成工作密钥失败!");
			return;
		}
		bm.setWorkKeys(workKeys);
		
	
		
		  // 提醒已经使用的信用额度和剩余的信用额度。放到58域中返回
//		ArrayList<Object> journalList = new ArrayList<Object>();
//		journalList.add(prepay.getUseCreamt());// 添加已经使用的信用额度到list第一个元素
//		journalList.add(prepay.getSurCreamt());// 添加剩余信用额度到list第二个元素
//		bm.setJournalList(journalList);
		
		try {
			lowAmountPrepay = commonPrepay.getTbBiPrepayLowAmount(prepay.getAccno());
			if(lowAmountPrepay == null) {
				logger.error("备付金低额提醒参数不存在，备付金账户：["+prepay.getAccno() + "]");
				setRetMsg(cm,bm,"","备付金低额提醒参数不存在，备付金账户：["+ prepay.getAccno() + "]");
				return; //added by wangwei
			}
			
		} catch (Exception e1) {
			logger.error("查询备付金低额提醒参数出错，备付金号：["+prepay.getAccno() + "]");
			setRetMsg(cm,bm,"","取备付金低额提醒参数时出错，备付金号：[" + prepay.getAccno() + "]");
			e1.printStackTrace();
			return;
		}
		
		String field58 = "";
		if (prepay.getAccBalance().doubleValue() > lowAmountPrepay.getRemindBalance().doubleValue()) {
			field58= "提醒：您的备付金余额为" + prepay.getAccBalance() + "元。";			
		}else if(0.0 < prepay.getAccBalance().doubleValue() 
				&&	prepay.getAccBalance().doubleValue() <= lowAmountPrepay.getRemindBalance().doubleValue()){
				field58 = "提醒：您的余额已不足"+ lowAmountPrepay.getRemindBalance() + "元，请尽快续费。";
		}else if(prepay.getAccBalance().doubleValue() == 0.0
				&& prepay.getUseCreamt().doubleValue() > 0.0 ) {
				field58 = "提醒：您已欠费：" + prepay.getUseCreamt()	+ "元，请尽快去续费还款。";		
		}else if(prepay.getAccBalance().doubleValue() == 0.0
				&& prepay.getUseCreamt().doubleValue() == 0.0) {
				field58 = "提醒：您的备付金余额为0.00元，请尽快续费。";
		}else {
			field58 = "系统备付金相关金额数据出错，\n请尽快与数据库管理员联系。";
		}
		bm.setAdditionalTip(field58);
		
		String maxPosSeq = tradeDao.selectMaxPosSeq(bm.getTranDate(),shopCode);
		bm.setOrigPosJournalSeqno(maxPosSeq);
		
		
		
		
		//add by fengyafang 农电参数配置检查
		//先检查该商户 是否有农电业务
		// if(checkNdBusiness(bm.getShopCode())){
			 //有农电业务后再检查 是否配置了农电参数，
		 	// if(checkNdPara(bm.getShopCode())){
				 //配置了农电参数 再去修改参数值为1
				 //do 
				 
		//	 }
		// } 

		// 修改状态为签到，修改商户表：tb_bi_company中CheckStat状态为1，0为未签到，1为签到
		if(shop.getCheckstat() != null){
			if ("1".equals(shop.getCheckstat().trim())) {
				// 商户已签到
				//cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
	//			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("该商户已签到!");
				//bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
	//			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("该商户已签到!");
				return;
			}
		}
		
		
		try {
			if(companyDao.updateComCheckStat(bm.getShop(),"1") == false){
				logger.error("修改签到状态失败!商户号=[" + shopCode + "];");
				setRetMsg(cm,bm,"","修改签到状态失败!商户号=[" + shopCode + "];");
				return;
			}
		} catch (Exception e) {
			logger.error("修改签到状态失败!商户号=[" + shopCode + "];",e);
			setRetMsg(cm,bm,"","修改签到状态失败!商户号=[" + shopCode + "];");
			return;
		}
	 
		
		
		 
	}
	/**
	 * 检查是否有农电业务
	 * @param company_code
	 * @return
	 * @throws Exception 
	 */
	private boolean checkNdBusiness(String company_code )  {
		 boolean checkShopBindBusiness =false;
		try {
			checkShopBindBusiness= companyDao.checkShopBindBusiness(company_code, "018");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (checkShopBindBusiness){return checkShopBindBusiness;}else return false;
		 
	}
	
	/**
	 * 检查农电业务是否成功配置
	 * @param company_code
	 * @return
	 */
	private boolean checkNdPara(String company_code ){
		StringBuffer sb=new StringBuffer();
		sb.append(" from TbNdPara TP where tp.id=?");
		List find = baseHibernateDao.find(sb.toString(),new String[]{company_code}  );
		Map map=(Map)find.iterator().next();
		 Iterator iterator = map.keySet().iterator(); 
		 String value="";
		   while(iterator.hasNext()){
			   String next = (String)iterator.next(); 
			  Object object = map.get(next) ;
			  value=object.toString();
		   }
		   
		if(value.isEmpty()){return false;}   
		return true;
	}
	
	/**
	 * 封装标志域
	 * 第一位资金归集方式。  第二位密码修改标志。  后面位置有增加时补充。
	 * 资金归集方式：0 未使用1备付金2.绿卡
	 * 密码修改标志：0 不需要修改1 需要修改如果为未定义都为0
	 * @param payType
	 * @param pwdFlag
	 * author jxw
	 * @return flagFieldString
	 */
	private String getFlagField(String payType, String pwdFlag) {
		String flagFieldString = "";
		flagFieldString = payType + pwdFlag + GlobalConst.POS_FLAGFIELD_FILL_ZERO;
		return flagFieldString;
	}
	
	

	protected String tradeType() {
		// 08-管理
		return "08";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.nantian.npbs.business.service.request.RequestBusinessService#
	 * needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		// 不控制进程
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		// 不登记流水
		bm.setSeqnoFlag("0");
	}
	
	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");
	}
	

}
