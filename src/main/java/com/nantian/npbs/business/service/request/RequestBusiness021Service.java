package com.nantian.npbs.business.service.request;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * 备付金密码修改
 * 
 * @author
 * 
 */
@Scope("prototype")
@Component
//TODO 需要重新整理
public class RequestBusiness021Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness021Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	public CommonPrepay commonPrepay;
	
	@Resource
	EncryptionService encryptionService;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}
		if (!checkSignState(cm, bm)) { // 检查商户签到
			return false;
		}
	
		logger.info("公共校验成功!");
		return true;
	}

	// TODO:
	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金密码修改业务流程开始:");
		
		if(cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)) {
		    return;	
		}
		
		// 备付金密码修改业务流程		
		String accNo = prepayDao.searchPreAccnoBySA(bm.getShopCode());// 备付金帐号		
		byte[] shopPINData = bm.getShopPINData();// 商户原密码
		byte[] newPsw = bm.getCustomerPINData();//取pos终端新密码密文

		if(null == accNo || "".equals(accNo)) {                      //检验商户对应背负金账户是否存在
			bm.setCustomData("备付金账户不存在，请与管理员联系。");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金账户不存在！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金账户不存在");
			logger.error("备付金账户不存在，商户号:" + bm.getShopCode());
			return;			
		}
		
		TbBiPrepay tbBiPrepay;
		try {
			tbBiPrepay = commonPrepay.getPrepay(accNo);  //查询备付金实体
			
			if("2".equals(tbBiPrepay.getState())) {      //检查备付金账户是否注销
				bm.setCustomData("备付金账户已注销。");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("备付金账户已注销！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金账户已注销");
				logger.error("备付金账户已注销，商户号:" + bm.getShopCode());
				return;	
			}
			
			// 检查原密码是否正确
			if (!(encryptionService.checkPIN(shopPINData, accNo, bm.getShopCode(), tbBiPrepay.getAccpwd()))) {
				bm.setCustomData("密码修改失败.\n原密码不正确,请重新输入.");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("原密码不正确！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("原密码不正确!");
				logger.error("原密码不正确,商户号:" + bm.getShopCode());
				return;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金查询数据库异常！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金查询数据库异常!");
			logger.error("备付金密码检查出错。备付金帐号:" + accNo);
			e.printStackTrace();
		}     
		
		String psw = DigestUtils.md5Hex(GlobalConst.PASSWD_INIT + accNo);
		String newPswMD5Pin = encryptionService.getMD5Pin(newPsw, accNo, bm.getShopCode());  //得到新密码密文
		
		if(psw.equals(newPswMD5Pin)){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金密码不能为初始密码,请重新输入!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金密码修改出错!");
			return;
		}
		
		if(commonPrepay.updatePSW(newPswMD5Pin, accNo)){
			bm.setCustomData("备付金密码修改成功!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			bm.setResponseMsg("备付金密码修改成功!");
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("备付金密码修改成功!");
			logger.info("备付金密码修改成功");
		}else {	
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金密码修改出错！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金密码修改出错!");
			logger.error("备付金密码修改出错。备付金帐号:" + accNo);
			return;			
		}
			
		logger.info("备付金密码修改业务流程结束！");

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");
	}
	

}
