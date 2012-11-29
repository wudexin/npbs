package com.nantian.npbs.test.business.mock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiCompanyMessage;
import com.nantian.npbs.business.model.TbBiCompanyMessageId;
import com.nantian.npbs.business.service.internal.CommonVerification;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 基类（抽象类）：所有请求服务的基类
 * 
 * @author wangwei, qiaoxiaolei
 * @since 2011-08-24
 * 
 *        所有子类都是有状态
 * @Scope("prototype") 表示该bean是多例模式，每次调用都是new一个新实例
 * @Component(value="requestBusiness001Service") 
 *                                               //工程启动时，spring扫描该bean，并在RequestBusinessFactory中使用
 */
public abstract class RequestBusinessService implements IRequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusinessService.class);

	/**
	 * 执行入口 1、所有商户统一初始化商户信息
	 * 2、公共检查没有返回状态，该方法中如果出错直接跳出该方法(return)，并记录ResultCode失败状态码
	 * 3、业务处理，首先判断ResultCode,如果为失败状态码，则跳出该方法(return)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void execute(ControlMessage cm, BusinessMessage bm) {

		logger.info("开始执行交易{}" , getClass().getName());
		// 初始化BusinessMessage信息
		if (!initBusinessMessage(cm, bm)) {
			return;
		}

		// 初始化商户,所有的交易都需要初始化商户
		TbBiCompany shop = initShop(cm, bm);
		if (shop == null) {
			return;
		}

		if (!checkCommon(cm, bm)) {
			// 公共检查
			return;
		}
		//检查商户下载信息
		if("003".equals(bm.getTranCode().substring(3)) != true 
				|| "004".equals(bm.getTranCode().substring(3)) != true){
			checkForceDownLoadFlag(cm,bm);
		}
		
		dealBusiness(cm, bm); // 业务处理
		//业务处理完成后，需要判断响应码，若业务处理失败，直接返回，不做流水登记，也不执行后继流程
		if (!cm.getResultCode().equals(GlobalConst.RESULTCODE_SUCCESS)) {
			logger.error("业务处理失败！");
			return;
		}

		setTradeFlag(bm);
		if (hasTrade(bm)) {
			// 加入交易流水(状态为异常状态)
			if (!addTrade(cm, bm)) {
				logger.error("增加交易流水失败!");
				return;
			}
			logger.info("增加交易流水成功!");
		}
	}
	
	/**
	 * 设置是否登记流水
	 * @param bm 交易信息
	 * @return boolean
	 */
	public boolean hasTrade(BusinessMessage bm){
		if ("1".equals(bm.getSeqnoFlag()))
			return true;
		else
			return false;
	}

	/**
	 * 公共检查 抽象方法，子类覆盖时，根据子类业务需要调用不同的方法
	 * 
	 * @param cm
	 * @param bm
	 * @param shop
	 *            传入初始化商户
	 * @return boolean
	 */
	protected abstract boolean checkCommon(ControlMessage cm,BusinessMessage bm);

	/**
	 * 业务处理
	 * 
	 * @param cm
	 * @param bm
	 * @param shop
	 *            传入初始化商户
	 */
	protected abstract void dealBusiness(ControlMessage cm, BusinessMessage bm);

	/**
	 * 返回交易类型 01-缴费交易；02-取消交易；04-冲正交易
	 * 
	 * @return
	 */
	protected abstract String tradeType();

	/**
	 * 初始化BusinessMessage信息
	 */
	public boolean initBusinessMessage(ControlMessage cm, BusinessMessage bm) {
		// 初始化交易类型，目前表中交易busi_code为交易码前三位
		bm.setBusinessType(bm.getTranCode().substring(0,3));

		String tradeDate = "2011-10-10"; // 系统时间
		String pbSerial = "121229988"; // pb流水
		if (tradeDate == null || pbSerial == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("初始化时,生成系统时间和pb流水号失败!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("初始化时,生成系统时间和pb流水号失败!");
			logger.error("初始化时,生成系统时间和pb流水号失败!");
			return false;
		}
		
		// 设置是否发送第三方
		setCallServiceFlag(cm);
		
		//初始化返回报文mac
		bm.setMac(new byte[]{0x00});

		// 设置流水日期
		bm.setTranDate(tradeDate);
		
		// 设置流水号
		bm.setPbSeqno(pbSerial);
		// 设置流水状态
		bm.setPbState("99");

		// 设置取消标志
		bm.setCancelFlag("0");

		Date nowdate = new Date();
		SimpleDateFormat dfdate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dftime = new SimpleDateFormat("hhmmss");
		String date = dfdate.format(nowdate);
		String time = dftime.format(nowdate);
		// System.out.println(date);
		// System.out.println(time);
		
		// 设置时间,6位
		bm.setTranTime(time);
		
		bm.setLocalDate(date);
		bm.setLocalTime(time);

		// 设置交易类型
		bm.setTranType(tradeType());

		// 设置响应码
		bm.setResponseCode("99");

		return true;
	}

	/**
	 * 初始化商户信息 向BusinessMessage设置：商户名称、商户状态、签到状态
	 * 
	 * @param cm
	 * @param bm
	 */
	protected TbBiCompany initShop(ControlMessage cm, BusinessMessage bm) {
		TbBiCompany shop = null;
		// 商户
		try {
			// 方式一：定义Dao接口及类型，用注入的方式进行操作
			shop = new TbBiCompany();
			shop.setCompanyCode(bm.getShopCode());
			shop.setAcountname("hello");
			shop.setCompanyCode("world!");
			shop.setState("0");
			shop.setUnitcode("007002");
			
			if (shop != null) {
				bm.setShop(shop);
				// 设置商户账户、商户名称、商户状态
				bm.setShopAccount(shop.getAcountname());
				bm.setShopName(shop.getCompanyName());
				bm.setShopState(shop.getState());
				bm.setShopInst(shop.getUnitcode());

				bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
				cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			} 

			// Object obj1 = baseDao.getByID(TbBiCompany.class,
			// bm.getShopCode());
			// if (obj1 != null) {
			// shop = (TbBiCompany) obj1;
			// // 设置 //商户账户、商户名称、商户状态 bm.setShopAccount(shop.getAcountname());
			// bm.setShopName(shop.getCompanyName());
			// bm.setShopState(shop.getState());
			//
			// bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			// cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			// } else {
			// bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			// bm.setResponseMsg("无该商户信息!");
			// cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			// cm.setResultMsg("无该商户信息!");
			// logger.error("无该商户信息,查询表:TB_BI_COMPANY!商户号:" + bm.getShopCode());
			// }

		} catch (Exception e) {
			shop = null;
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询您的商户信息失败,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询您的商户信息失败,请联系管理员!");
			logger.error("无该商户信息,查询表:TB_BI_COMPANY!商户号:" + bm.getShopCode(), e);
		}
		return shop;
	}

	/**
	 * 检查进程数，同时增加进程
	 * 
	 * @param bm
	 */
	protected boolean checkProcessNum(ControlMessage cm, BusinessMessage bm) {
		return true;
	}

	/**
	 * 释放进程
	 */
	protected void releaseProcess() {

	}

	/**
	 * 检查商户状态
	 * 
	 * @param cm
	 * @param bm
	 * 
	 */
	protected boolean checkShopState(ControlMessage cm, BusinessMessage bm) {
		TbBiCompany shop = bm.getShop();
		if (shop.getState() == null || shop.getState().equals("")) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户状态不正常,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户状态不正常,请拨打客服电话咨询!");
			logger.error("商户状态不正常,查询表:TB_BI_COMPANY!商户号" + bm.getShopCode());
			return false;
		}
		// 0-正常；1-暂停；2-注销
		if (shop.getState().equals(GlobalConst.SHOP_STATE_NORMAL)) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			return true;
		} else {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户状态不正常,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户状态不正常,请拨打客服电话咨询!");
			logger.error("商户状态不正常,查询表:TB_BI_COMPANY!商户号" + bm.getShopCode());
			return false;
		}

	}

	/**
	 * 检查签到状态
	 * 
	 * @param cm
	 * @param bm
	 */
	protected boolean checkSignState(ControlMessage cm, BusinessMessage bm) {
		TbBiCompany shop = bm.getShop();
		if (shop.getCheckstat() == null || shop.getCheckstat().equals("")) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("您还未签到,请先签到!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("您还未签到,请先签到!");
			logger.error("商户未签到,查询表:TB_BI_COMPANY!商户号" + bm.getShopCode());
			return false;
		}
		// 0-未签到 1-签到
		if (shop.getCheckstat().equals(GlobalConst.SHOP_CHECK_STATE_NO)) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("您还未签到,请先签到!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("您还未签到,请先签到!");
			logger.error("商户未签到,查询表:TB_BI_COMPANY!商户号" + bm.getShopCode());
			return false;
		} else {
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			return true;
		}
	}

	/**
	 * 检查强制下载标志
	 * 
	 * @param cm
	 * @param bm
	 */
	protected void checkForceDownLoadFlag(ControlMessage cm,BusinessMessage bm) {
		TbBiCompany shop = bm.getShop();
		List<TbBiCompanyMessage> shopMsgs = new ArrayList<TbBiCompanyMessage>(); // 商户消息
		TbBiCompanyMessage e = new TbBiCompanyMessage();
		e.setId(new TbBiCompanyMessageId());
		shopMsgs.add(e);
		if(CHANEL_TYPE.POS.equals(bm.getChanelType()))
		cm.getPacketHeader().setHandleType(CommonVerification.checkForceDownLoadFlag(shop, shopMsgs));
	}

	/**
	 * 检查商户与业务是否绑定
	 * 
	 * @param bm
	 */
	protected boolean checkShopBindBusiness(ControlMessage cm,
			BusinessMessage bm) {
		try {

				bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
				cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
				return true;
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询您的套餐信息失败,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询您的套餐信息失败,请联系管理员!");
			logger.error("取该商户该业务的套餐时出错,查询表:TB_BI_COMPANY_BUSINESS,商户号: "
					+ bm.getShopCode() + "业务码: " + bm.getTranCode());
			return false;
		}

	}

	/**
	 * 增加交易流水信息
	 * 
	 * @param bm
	 * @param style
	 *            状态
	 */
	protected boolean addTrade(ControlMessage cm, BusinessMessage bm) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			return true;
	}

	/**
	 * 更新交易流水信息
	 * 
	 * @param bm
	 * @param style
	 *            状态
	 */
	protected boolean updateTrade(ControlMessage cm, BusinessMessage bm) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			return true;
	}
	
	/**
	 * 设置返回信息
	 * 
	 * @param retCode
	 * @param retMsg
	 *            状态
	 */
	public void setRetMsg(ControlMessage cm, BusinessMessage bm, String retCode, String retMsg){
		cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		cm.setResultMsg(retMsg);
		bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
		bm.setResponseMsg(retMsg);
	}

	@Override
	public abstract boolean needLockProcess();

	@Override
	public abstract void setTradeFlag(BusinessMessage bm);
	
	@Override
	public abstract void setCallServiceFlag(ControlMessage cm);

}
