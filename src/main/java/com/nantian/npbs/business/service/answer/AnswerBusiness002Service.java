package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

@Scope("prototype")
@Component
public class AnswerBusiness002Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness002Service.class);

	@Resource
	private CommonPrepay commonPrepay;

	@Resource
	public PrepayDao prepayDao;	

	TbBiPrepayLowamount tbBiPrepayLowamount;

	boolean flag = false;

	/**
	 * 业务处理
	 */
	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("开始业务处理");
		// 如果是河电省标卡在写卡时处理备付金，并登记备付金明细
		if ("010002".equals(bm.getTranCode()) != true
				&& "000000".equals(cm.getServiceResultCode()) == true) {
			deleteAuthorizeAmount(cm, bm);
			if(!commonPrepay.payPrepay(cm, bm)){
				return;
			}		
		}		
		
		// 检查商户是否有备付金帐号
		String accno = null;
		accno = prepayDao.searchPreAccnoBySA(bm.getShopCode());
		if (accno == null) {
			logger.info("该商户无备付金帐号！商户号：{}" , bm.getShopCode());
			return;
		} else {
			bm.setPrePayAccno(accno);
		}

		// 检查商户是否需要备付金低额提醒
		if (ifLowAmount(cm, bm)) {
			// 修改备付金低额提醒次数为当前次数减1
			tbBiPrepayLowamount.setRemainNum((byte) (tbBiPrepayLowamount
					.getRemainNum() - 1));
			try {
				prepayDao.updateTbBiPrepayLowamount(tbBiPrepayLowamount);
			} catch (Exception e) {
				logger.info("修改备付金参数表出错！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("修改备付金参数表出错！请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("修改备付金参数表出错！");
			}
		}

	}

	/**
	 * 是否需要备付金低额提醒
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	public boolean ifLowAmount(ControlMessage cm, BusinessMessage bm) {
		// 获取当前商户备付金余额
		Double preBalance = getPrebalanceByShopAccount(cm, bm);
		
		  
		//根据商户好提取备付金实体，为了区分终端提示信息而添加
		TbBiPrepay tbBiPrepay = null;
		
		String accNo = prepayDao.searchPreAccnoBySA(bm.getShopCode()); //提取备付金账户
		
		if("".equals(accNo) || accNo == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("获取备付金失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("获取备付金失败！");
			logger.error("获取备付金账号失败，商户号：[{}]",bm.getShopCode());
			return false;
		}		
		
		
		tbBiPrepay = (TbBiPrepay) baseHibernateDao.get(TbBiPrepay.class,
				accNo);
		
		if(tbBiPrepay == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("获取备付金失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("获取备付金失败！");
			logger.error("获取备付金账号失败，备付金号：[{}]",accNo);
			return false;
		}

		
	
		// 查询备付金参数表获取备付金参数
		try {
			tbBiPrepayLowamount = prepayDao.getTbBiPrepayLowamount(bm
					.getPrePayAccno());
			if (null == tbBiPrepayLowamount) {
				logger.error("备付金参数未配置！备付金帐号[{}]" , bm.getPrePayAccno());
				return false; //added by wangwei
			}
			Double remindBalance = tbBiPrepayLowamount.getRemindBalance();// 提醒金额
			int remainNum = tbBiPrepayLowamount.getRemainNum();// 剩余提醒次数
			if(remainNum>0){
				String tishiStr = "";
				//modifyStart MDB 2012年1月12日 18:18:16
				if(0.0 < preBalance.doubleValue() && preBalance.doubleValue() <= remindBalance.doubleValue()){
					tishiStr = "备付金余额已不足"+ remindBalance + "元，请尽快续费。";
					bm.setAdditionalTip(tishiStr);
					bm.setLowTipAmount(String.valueOf(remindBalance));
					bm.setLowTipType("0");
					return true;
				}
				if(preBalance.doubleValue() == 0.0 && tbBiPrepay.getUseCreamt().doubleValue() > 0.0 ) {
					tishiStr = "备付金已欠费" + tbBiPrepay.getUseCreamt() + "元，请尽快续费还款。";
					bm.setAdditionalTip(tishiStr);
					bm.setLowTipAmount(String.valueOf(tbBiPrepay.getUseCreamt()));
					bm.setLowTipType("1");
					return true;
				}
				if(preBalance.doubleValue() == 0.0 && tbBiPrepay.getUseCreamt().doubleValue() == 0.0) {
					tishiStr = "备付金余额为0.00元，请尽快续费。";
					bm.setAdditionalTip(tishiStr);
					bm.setLowTipAmount(String.valueOf(preBalance));
					bm.setLowTipType("2");
					return true;
				}
				
				return false;
				//modifyEnd MDB 2012年1月12日 18:18:16
			}else{
				return false;
			}
			
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("获取备付金参数失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("获取备付金参数失败！");
		}
		return false;
	}

	/**
	 * 备付金余额查询
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private Double getPrebalanceByShopAccount(ControlMessage cm,
			BusinessMessage bm) {
		String shopAccount = bm.getShopCode();// 商户号
		String accNo = null;// 备付金帐号
		TbBiPrepay tbBiprepay = null;// 备付金
		Double preBalance = 0.00;// 备付金余额
		try {
			accNo = prepayDao.searchPreAccnoBySA(shopAccount);
			tbBiprepay = (TbBiPrepay) baseHibernateDao.get(TbBiPrepay.class,
					accNo);
			preBalance = tbBiprepay.getAccBalance();// 设置备付金余额
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金余额出错,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金余额出错,请拨打客服电话咨询!");
			logger.error("查询备付金余额出错,查询表：TB_BI_PREPAY,商户号: " + bm.getShopCode()
					+ "备付金账号: " + accNo);
		}
		return preBalance;
	}

}
