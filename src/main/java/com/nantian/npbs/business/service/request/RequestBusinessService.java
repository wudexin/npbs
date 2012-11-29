package com.nantian.npbs.business.service.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.business.dao.CompanyDao;
import com.nantian.npbs.business.dao.ProgramDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiCompanyMessage;
import com.nantian.npbs.business.model.TbBiEcUnit;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.business.model.TbSmInterouter;
import com.nantian.npbs.business.model.TbSmInterouterId;
import com.nantian.npbs.business.model.TbSmProgram;
import com.nantian.npbs.business.model.TbSmSysdata;
import com.nantian.npbs.business.service.internal.CommonVerification;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.core.service.IRequestBusinessService;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.FieldUtils;

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

	// 基Dao
	@Resource
	protected BaseHibernateDao baseHibernateDao;

	// Company Dao
	@Resource
	protected CompanyDao companyDao;

	// 交易流水Dao
	@Resource
	protected TradeDao tradeDao;

	@Resource
	public ProgramDao programDao;

	// 商户
	// 由于shop在多个方法中使用，并且当前bean为多例模式，故使用类属性的方式。
	// protected TbBiCompany shop = null;

	/**
	 * 执行入口 1、所有商户统一初始化商户信息
	 * 2、公共检查没有返回状态，该方法中如果出错直接跳出该方法(return)，并记录ResultCode失败状态码
	 * 3、业务处理，首先判断ResultCode,如果为失败状态码，则跳出该方法(return)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void execute(ControlMessage cm, BusinessMessage bm) {
		//报文有错误
		if(!GlobalConst.RESULTCODE_SUCCESS.equals(cm.getResultCode())){
			return;
		}

		logger.info("开始执行交易,{}", getClass().getSimpleName());

		// 初始化BusinessMessage信息，检查系统状态
		if (!initBusinessMessage(cm, bm)) {
			return;
		}
		if ("000806".equals(bm.getTranCode())) {
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("交易成功");
			return;
		}
		// 初始化商户,所有的交易都需要初始化商户（备付金缴费撤销除外）
		if (!bm.getTranCode().equals("000803")) {
			TbBiCompany shop = initShop(cm, bm);
			if (shop == null) {
				logger.info("商户初始化失败~！~");
				return;
			}
		}

		if (!checkCommon(cm, bm)) {
			// 公共检查
			return;
		}
		// 检查商户下载信息
		if ("003".equals(bm.getTranCode().substring(3)) != true
				&& "004".equals(bm.getTranCode().substring(3)) != true
				&& GlobalConst.CHANEL_TYPE.ELEBUSIREQUEST.equals(bm
						.getChanelType()) != true) {
			checkForceDownLoadFlag(cm, bm);

		}

		dealBusiness(cm, bm); // 业务处理
		// 业务处理完成后，需要判断响应码，若业务处理失败，直接返回，不做流水登记，也不执行后继流程
		if (!cm.getResultCode().equals(GlobalConst.RESULTCODE_SUCCESS)) {
			logger.error("业务处理失败！");
			return;
		}

		if (!getEcUnit(cm, bm)) {
			return;
		}

		setTradeFlag(bm);
		if (hasTrade(bm)) {
			// 加入交易流水(状态为异常状态)
			try {
				if (!addTrade(cm, bm)) {
					logger.error("增加交易流水失败!");
					return;
				}
			} catch (Exception e) {
				logger.error("增加交易流水失败!", e);
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("增加交易流水失败!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("增加交易流水失败!");
				return;
			}

			logger.info("增加交易流水成功!");
		}
	}

	/**
	 * 设置是否登记流水
	 * 
	 * @param bm
	 *            交易信息
	 * @return boolean
	 */
	public boolean hasTrade(BusinessMessage bm) {
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
	protected abstract boolean checkCommon(ControlMessage cm, BusinessMessage bm);

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
	 * 返回交易类型 01-缴费交易；02-取消交易；03-写卡交易  04-冲正交易；05-写卡成功确认；06-写卡失败确认；07-查询；08-管理
	 * 
	 * @return
	 */
	protected abstract String tradeType();

	/**
	 * 初始化BusinessMessage信息
	 */
	public boolean initBusinessMessage(ControlMessage cm, BusinessMessage bm) {
		// 初始化交易类型，目前表中交易busi_code为交易码前三位
		bm.setBusinessType(bm.getTranCode().substring(0, 3));

		TbSmSysdata sysdata = baseHibernateDao.getSysData();
		if(sysdata == null){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("系统状态查询失败");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("系统状态查询失败");
			logger.error("系统状态查询失败");
			return false;
		}
		String tradeDate = sysdata.getId().getSystemDate();// 系统日期
		String pbSerial = baseHibernateDao.getNumber(); // pb流水
		if (tradeDate == null || pbSerial == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("初始化时,生成系统时间和pb流水号失败!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("初始化时,生成系统时间和pb流水号失败!");
			logger.error("初始化时,生成系统时间和pb流水号失败!");
			return false;
		}
		logger.info("系统日期[{}],pb流水:[{}],状态:[{}]", new Object[]{tradeDate, pbSerial, ""});
		//日终系统修改完毕后放开下面的校验
		if("1".equals(sysdata.getSystemStatus()) && !"000806".equals(bm.getTranCode())){
			if("000010".equals(bm.getTranCode())){
				bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
			}else{
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			}
			bm.setResponseMsg("系统日终，暂停使用，请稍后！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("系统日终，暂停使用，请稍后！");
			logger.info("系统日终，暂停使用，请稍后！");
			return false;
		}

		// 设置是否发送第三方
		setCallServiceFlag(cm);

		// 初始化返回报文mac
		bm.setMac(new byte[] { 0x00 });

		// 设置流水日期
		bm.setTranDate(tradeDate);

		// 设置流水号
		if (bm.getPbSeqno() == null
				|| !"012".equals(bm.getTranCode().substring(3))) { // 取消交易时不为空
			bm.setPbSeqno(pbSerial);
		}

		// 设置流水状态
		bm.setPbState("99");

		// 设置取消标志
		bm.setCancelFlag("0");

		Date nowdate = new Date();
		SimpleDateFormat dfdate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dftime = new SimpleDateFormat("HHmmss");
		String date = dfdate.format(nowdate);
		String time = dftime.format(nowdate);
		// System.out.println(date);
		// System.out.println(time);

		// 设置时间,14位时间戳
		bm.setTranTime(date + time);

		bm.setLocalDate(date);
		// 本地时间6位
		bm.setLocalTime(time);

		// 设置交易类型
		bm.setTranType(tradeType());

		// 设置响应码
//		bm.setResponseCode(GlobalConst.TRADE_STATUS_CARD_ORIG);
//		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);

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
			shop = companyDao.get(bm.getShopCode());

			if (shop != null) {
				bm.setShop(shop);
				// 设置商户账户、商户名称、商户状态
				bm.setShopAccount(shop.getAcountname());
				bm.setPrePayAccno(shop.getResaccno());				
				bm.setShopName(shop.getCompanyName());
				bm.setShopState(shop.getState());
				bm.setShopInst(shop.getUnitcode());
			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("无该商户信息!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("无该商户信息!");
				logger.error("无该商户信息,查询表:TB_BI_COMPANY!商户号:" + bm.getShopCode());
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
		// 怒了，又是一个，一期数据库里全是char，防不胜防呀
		if (shop.getCheckstat().equals(GlobalConst.SHOP_CHECK_STATE_NO) || shop.getCheckstat().equals("0 ")) {
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
	protected void checkForceDownLoadFlag(ControlMessage cm, BusinessMessage bm) {

		List<TbBiCompanyMessage> shopMsgs = null; // 商户消息
		shopMsgs = companyDao.getCompanyMessageByShopCode(bm.getShop()
				.getCompanyCode());

		if (CHANEL_TYPE.POS.equals(bm.getChanelType())) {
//			logger.info("处理前POS报文头标志位[{}]", cm.getPacketHeader().getHandleType());

			// 如果数据库有下载要求,则检测报文头程序版本号.
			if ("1".equals(bm.getShop().getProgramFlag())) {
				// 如果和上送版本一致,则恢复下载标致.
				try {
					TbSmProgram pro = programDao.getProgrameByFilepath(bm
							.getShop().getFilepath());
					if (null != pro) {
						logger.info("数据库版本号[{}]报文头版本号[{}]", FieldUtils
								.leftAddZero4FixedLengthString(pro.getId()
										.toString(), 6), cm.getPacketHeader()
								.getTerminalVersion());

						if (FieldUtils.leftAddZero4FixedLengthString(
								pro.getId().toString(), 6).equals(
								cm.getPacketHeader().getTerminalVersion())) {
							bm.getShop().setProgramFlag("0");
							companyDao.update(bm.getShop());
						}
						// 此处是对异常的特殊处理!!!
						// 如果查询程序版本异常则取消下载
					} else {
						logger.error("根据商户[{}]文件路径[{}]查询程序版本错误!重置数据库标致禁止下载!",
								bm.getShop().getCompanyCode(), bm.getShop()
										.getFilepath());
						bm.getShop().setProgramFlag("0");
						companyDao.update(bm.getShop());
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("校验程序版本错误!" + e);
				}
			}

			cm.getPacketHeader().setHandleType(
					CommonVerification.checkForceDownLoadFlag(bm.getShop(),
							shopMsgs));
//			logger.info("处理后POS报文头标志位[{}]", cm.getPacketHeader().getHandleType());
		}
	}

	/**
	 * 设置标志域
	 * 
	 * @param flagfield
	 * @param bm
	 * @return flag
	 */
	protected boolean setFlagField(BusinessMessage bm) {
		String flag = "";
		String addSign = null;
		if ("0".equals(bm.getShop().getPayType())) {
			addSign = "2";
		}
		if ("1".equals(bm.getShop().getPayType())) {
			addSign = "1";
		}
		flag += addSign;

		// flag += bm.getPrepay().getPwdFlag();

		try {
			flag = FieldUtils.rightAddZero4FixedLengthString(flag, 16);
		} catch (Exception e) {
			logger.error("拼标志域，右补0失败！", e);
			return false;
		}

		bm.setFlagField(flag);

		return true;
	}

	/**
	 * 检查商户与业务是否绑定
	 * 
	 * @param bm
	 */
	protected boolean checkShopBindBusiness(ControlMessage cm,
			BusinessMessage bm) {
		try {
			if (companyDao.checkShopBindBusiness(bm.getShopCode(),
					bm.getBusinessType())) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
				cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
				return true;
			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("您还没有该业务!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("您还没有该业务!");
				logger.error("商户没有该业务,查询表:TB_BI_COMPANY_BUSINESS,商户号: "
						+ bm.getShopCode() + "业务码: " + bm.getTranCode());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询您的商户业务信息,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询您的商户业务信息,请联系管理员!");
			logger.error("取该商户该业务时出错,查询表:TB_BI_COMPANY_BUSINESS,商户号: "
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
		TbBiTrade trade = new TbBiTrade();
		tradeDao.setTrade(bm, trade);
		if (!tradeDao.addTrade(trade)) {
			logger.info("流水表插入失败！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("增加交易流水失败,请联系管理员!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("增加交易流水失败,请联系管理员!");
			return false;
		}
		return true;
	}

	/**
	 * 设置返回信息
	 * 
	 * @param retCode
	 * @param retMsg
	 *            状态
	 */
	public void setRetMsg(ControlMessage cm, BusinessMessage bm,
			String retCode, String retMsg) {
		cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
		cm.setResultMsg(retMsg);
		bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
		bm.setResponseMsg(retMsg);
	}

	/**
	 * 查询商户所属电子商务平台机构及相应柜员、授权柜员
	 * 
	 * @param bm
	 * @throws Exception
	 */
	public boolean getEcUnit(ControlMessage cm, BusinessMessage bm) {
		logger.info(
				"发送电子商务平台标志:{}，渠道号：{},交易码:{}",
				new Object[] {
						cm.getServiceCallFlag() == null ? "" : cm
								.getServiceCallFlag(),
						bm.getSystemChanelCode() == null ? "" : bm
								.getSystemChanelCode(),
						bm.getTranCode() == null ? "" : bm.getTranCode() });
		if ((!"1".equals(cm.getServiceCallFlag()) == true && !GlobalConst.eleChanelCode
				.equals(bm.getSystemChanelCode()))
				|| "000803".equals(bm.getTranCode())) {
			logger.info("不取电子商务平台机构!");
			return true;
		}

		// 电子商务平台机构和柜员
		logger.info("商户所属便民服务站机构号:{}", bm.getShop().getUnitcode());
		String interUnit = bm.getShop().getUnitcode();

		String outUnitCode = null;

		// TbSmOuterUnit outUnit = null;
		// TbSmOuterUnitId outUnitId = null;

		TbBiEcUnit ecUnit = null;

		TbSmInterouter interOut = null;
		TbSmInterouterId interOutId = null;
		logger.info("便民服务站机构号：{},系统编号:{}。", interUnit == null ? "" : interUnit,
				GlobalConst.eleChanelCode);
		interOutId = new TbSmInterouterId(interUnit, "03");
		interOut = (TbSmInterouter) baseHibernateDao.get(TbSmInterouter.class,
				interOutId);
		if (interOut == null) {
			logger.info("机构对应电子商务平台机构表为空!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("机构对应电子商务平台机构表为空!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("机构对应电子商务平台机构表为空!");
			return false;
		}
		outUnitCode = interOut.getOutUnitcode();
		logger.info("电子商务平台机构号：{}", outUnitCode == null ? "" : outUnitCode);
		ecUnit = (TbBiEcUnit) baseHibernateDao.get(TbBiEcUnit.class,
				outUnitCode);
		if (ecUnit == null) {
			logger.info("机构对应电子商务平台柜员表为空!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("机构对应电子商务平台柜员表为空!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("机构对应电子商务平台柜员表为空!");
			return false;
		}
		bm.setEcUnitCode(ecUnit.getEcUnitCode());
		bm.setEcOperCode(ecUnit.getEcOperCode());
		bm.setEcAuthOper(ecUnit.getEcAuthOper());
		return true;
	}

	@Override
	public boolean needLockProcess() {
		//服务队列不再采用通过数据库表进行流量控制的手段，如有特殊情况在子队列中覆盖此方法
		return false;
	}

	@Override
	public abstract void setTradeFlag(BusinessMessage bm);

	@Override
	public abstract void setCallServiceFlag(ControlMessage cm);
}
