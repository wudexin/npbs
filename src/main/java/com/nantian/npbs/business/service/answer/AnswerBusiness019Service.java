package com.nantian.npbs.business.service.answer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * 备付金余额查询
 * @author
 *
 */
@Scope("prototype")
@Component
public class AnswerBusiness019Service extends AnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness019Service.class);
	@Resource
	public PrepayDao prepayDao;
	
	
	@Resource
	CommonPrepay commonPrepay;
	
	@Resource
	EncryptionService encryptionService;

	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {	
		logger.info("备付金余额查询开始：");
		
		if(cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)) {
			return;
		}
		
		String shopCode = bm.getShopCode(); //查询商户号
		byte[] pwd = bm.getShopPINData(); //得到pos上传备付金密码	
		
		String prepayAcco = prepayDao.searchPreAccnoBySA(shopCode); //查询商户备付金账号
		if(null == prepayAcco || "".equals(prepayAcco)){            //备付金账户是否存在校验
			bm.setAdditionalTip("备付金账户不存在，请与管理员联系。");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金账户不存在！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金账户不存在");
			logger.error("备付金账户不存在，商户号:" + bm.getShopCode());
			return;			
		}
		
		
		TbBiPrepay tbBiPrepay;		
		try {			
			tbBiPrepay = commonPrepay.getPrepay(prepayAcco);
			
			if("2".equals(tbBiPrepay.getState())) {    //备付金账户是否注销检查，对“暂停”状态暂时没有做处理
				bm.setAdditionalTip("备付金账户已注销，查询失败。");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("备付金账户已注销！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("备付金账户已注销！");
				logger.error("备付金账户已注销，商户号:" + bm.getShopCode());
				return;			
			}
			
			//备付金密码检查		
			if (!(encryptionService.checkPIN(pwd, prepayAcco, bm.getShopCode(), tbBiPrepay.getAccpwd()))) {	
				bm.setAdditionalTip("备付金密码错误，查询失败");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("密码不正确！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("密码不正确!");
				logger.error("原密码不正确,商户号:" + bm.getShopCode());
				return;
			}			
			bm.setPrePayAccno(tbBiPrepay.getAccno());
			
			//执行查询
			try {
				bm.setPreBalance(DoubleUtils.sub(tbBiPrepay.getAccBalance(), tbBiPrepay.getUseCreamt()));// 设置备付金余额
				bm.setAdditionalTip("商户号：" + bm.getShopCode() +"\n账号：" + tbBiPrepay.getAccno() + "\n余额：" + bm.getPreBalance() +"\n");
				bm.setCustomData("备付金余额查询成功！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
				bm.setResponseMsg("备付金余额查询成功！");
				cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
				cm.setResultMsg("备付金余额查询成功!");
				logger.info("备付金余额查询成功。备付金余额为：{},已使用信用额度" , tbBiPrepay.getAccBalance(),tbBiPrepay.getUseCreamt() );
			} catch (Exception e) {			
				bm.setAdditionalTip("备付金余额查询出错，请与技术员联系");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询备付余额出错。");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询备付余额出错。");
				logger.info("查询备付余额出错。");			
				e.printStackTrace();
			}	
		} catch (Exception e1) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金密码检查出错！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金密码检查出错!");
			logger.error("备付金密码检查出错,备付金帐号:" + prepayAcco);
			e1.printStackTrace();
		}	
		
	}

}
