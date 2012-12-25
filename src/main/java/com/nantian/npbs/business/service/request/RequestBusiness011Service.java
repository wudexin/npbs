package com.nantian.npbs.business.service.request;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.CompanyDao;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiCompanyMessage;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

import javax.annotation.Resource;

/**
 * 商户信息下载
 * 
 * @author 7tianle
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness011Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness011Service.class);

	/**
	 * 商户状态,签到状态 备付金账户是否存在 密码重置状态检查 是否已用信用额度，如果已用打印小票提示商户。
	 */
	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {

		/*----------add 20111021 by wzd------ start----*/
		// 进行公共检查
		if (!checkShopState(cm, bm)) {// 商户状态检查
			logger.info("商户状态校验失败！");
			return false;
		}
		

		
		  if(!checkSignState(cm,bm)) {//签到状态检查 
			  logger.info("签到状态检查失败！"); 
			  return false; 
		  }

	

		logger.info("公共校验成功!");
		return true;
		/*----------add 20111021 by wzd------ end ----*/

		/*
		 * //before modify: return false;
		 */

	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		/*----------add 20111021 by wzd------ start----*/
		logger.info("商户消息下载交易流程开始：");

		if (cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)) {
			return;
		}
		
		String shopCode = bm.getShopCode();		

		
		//查询并拼接55域商户下载信息
		List<Object[]> msg = companyDao.getCompanyDownLoadMessageByShopCode(shopCode, "0");		
		
		String msgInfo = "";
		if (msg != null && msg.size() > 0) {
			for (int i = 0; i < msg.size(); i++) {
				msgInfo = msgInfo + msg.get(i);
			}			
			bm.setCustomData(msgInfo);
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		
			
			//删除已往已下载过的与本次消息相同的商户消息，2012年5月18日9:13:32添加解决防止系统抛出主键唯一约束报告
			if(companyDao.deleteTbBiCompanyMessage(shopCode,msgInfo,"1")) {
				logger.info("清除相同已下载信息成功！" );
			}else {
				logger.info("清除相同已下载信息失败！");
			}
			
			//置下载消息标志位为已下载
			if(companyDao.updateTbBiCompanyMessage(shopCode, "1")){
				logger.info("商户{}消息下载状态标志位更新失败" ,shopCode );
			}else{
				logger.info("商户{}消息下载状态标志位更新成功",shopCode);
			}	

		} else {
			bm.setCustomData("没有需要下载的商户内容");
			logger.info("没有下载内容，不需要再下载。 ");
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("商户下载消息不存在");
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			bm.setResponseMsg("商户下载消息不存在");
		}

		/*----------add 20111021 by wzd------ end ----*/		
	
	}

	protected String tradeType() {
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
		return false;
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		cm.setServiceCallFlag("0");
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}

}
