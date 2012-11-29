package com.nantian.npbs.business.service.request;

import java.math.BigDecimal;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.model.TbBiBusiness;
import com.nantian.npbs.business.model.TbBiBusinessUnit;
import com.nantian.npbs.business.model.TbBiBusinessUnitId;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiFormulaDetail;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiSallaryPackage;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * 业务缴费
 * 
 * @author qiaoxiaolei
 * @since 2011-08-24
 */
@Scope("prototype")
@Component
public class RequestBusiness002Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness002Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	EncryptionService encryptionService;

	/**
	 * 检查
	 */
	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {

		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}
		if (!checkSignState(cm, bm)) { // 检查商户签到
			return false;
		}
		if (!checkShopBindBusiness(cm, bm)) { // 检查商户是否有该业务
			return false;
		}

		// 检查备付金
		if (!checkReserve(cm, bm)) {
			return false;
		}
		logger.info("检查备付金成功!");

		// 检查商户酬金套餐中是否包含此业务,并计算酬金
		if (!checkBusinessRemuneration(cm, bm)) {
			return false;
		}
		logger.info("缴费校验,佣金计算成功!");

		// 检查电话号码
		checkPhoneNum(bm);
		
		logger.info("公共校验成功!");
		return true;
	}

	/**
	 * 业务处理
	 */
	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {

		// 获取现金缴费临时表数据
		if (!getTempValue(cm, bm)) {
			return;
		}

		// 当交易码为失败时,退出
		if (cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)) {
			return;
		}

	}

	/**
	 * 检查备付金 设置bm 备付金账户、备付金余额、剩余信用额度
	 * 
	 * @param tbBiRescash
	 *            备付金账户
	 * @param amount
	 *            交易金额
	 */
	protected boolean checkReserve(ControlMessage cm, BusinessMessage bm) {
		TbBiCompany shop = bm.getShop();
		// 检查是否支付方式是否为备付金，非备付金方式不需要检查备付金
		if (!bm.payTypeIsPrePay()) {
			logger.info("商户:{} 使用非备付金方式, 支付方式: {}", bm.getShopCode(),
					bm.getPayType());
			return true;
		}

		// 备付金 账户
		TbBiPrepay tbBiPrepay = null;
		try {
			Object obj1 = baseHibernateDao.get(TbBiPrepay.class,
					shop.getResaccno());
			if (obj1 != null) {
				tbBiPrepay = (TbBiPrepay) obj1;
				if(!(GlobalConst.PREPAY_STATE_ZC).equals(tbBiPrepay.getState())){
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("您的备付金账户暂停或已注销,不能进行缴费交易!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("您的备付金账户暂停或已注销,不能进行缴费交易!");
					logger.info("该备付金账户暂停或注销,商户号[{}],备付金账号[{}]",
							bm.getShopCode(),shop.getResaccno());
					return false;
				}
				bm.setPrepay(tbBiPrepay);
				// 设置
				bm.setPrePayAccno(tbBiPrepay.getAccno()); // 备付金账户
				bm.setPreBalance(tbBiPrepay.getAccBalance()); // 备付金余额
				bm.setPrePaySurCreamt(tbBiPrepay.getSurCreamt());// 剩余信用额度

			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("您的备付金账户不存在,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("您的备付金账户不存在,请拨打客服电话咨询!");
				logger.info("该商户备付金账户不存在,查询表：TB_BI_PREPAY,商户号: "
						+ bm.getShopCode() + "备付金账号: " + shop.getResaccno());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询您的备付金账户出错,请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询您的备付金账户出错,请联系管理员!");
			logger.info("取该商户备付金账户时出错,查询表：TB_BI_PREPAY,商户号: "
					+ bm.getShopCode() + "备付金账号: " + shop.getResaccno());
			return false;
		}
		
		// 1、 检查备付金密码
		if ("1".equals(tbBiPrepay.getPwdFlag())) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("备付金密码已过期或已重置，请修改密码!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("备付金密码已过期或已重置，请修改密码!");
			logger.info("备付金密码已过期或已重置，请修改密码!");
			return false;
		}
		boolean pinCheckFlag = true;
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle("conf.checkFlag");
			pinCheckFlag = Boolean.valueOf(rb.getString("pinCheckFlag"));
		} catch (Exception e) {
			logger.info("取密码校验标志错!");
		}
		if (pinCheckFlag) {
			byte[] pindata = bm.getShopPINData();
			if (pindata == null) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("密码不能为空,请重新输入!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("终端上传备付金密码为空!");
				logger.info("终端上传备付金密码为空!");
				return false;
			} else {
				if (encryptionService.checkPIN(pindata, bm.getPrepay()
						.getAccno(), bm.getShopCode(), bm.getPrepay()
						.getAccpwd()) != true) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("密码错误,请重新输入!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("备付金密码错误!");
					logger.info("备付金密码错误!");
					return false;
				}
				String psw = DigestUtils.md5Hex(GlobalConst.PASSWD_INIT + bm.getPrepay().getAccno());
				if(psw.equals(bm.getPrepay().getAccpwd())){
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("密码为初始密码，请修改密码！");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("密码为初始密码，请修改密码！");
					logger.info("密码为初始密码，请修改密码！");
					return false;
				}
			}
		}

		// 2、检查备付金余额
		// 备付金余额 + 信用余额 > 交易金额
		if (bm.getAmount()
				- (tbBiPrepay.getAccBalance() + tbBiPrepay.getSurCreamt()) > 0) {

			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("您的备付金余额不足,请尽快充值!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("您的备付金余额不足,请尽快充值!");
			logger.info("备付金余额不足!账户余额:" + tbBiPrepay.getAccBalance()
					+ "信用余额:" +tbBiPrepay.getSurCreamt() + ",缴费金额:" + bm.getAmount());
			return false;
		}

		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		return true;

	}

	/**
	 * 按照C写的 1、检查商户酬金套餐中是否包含此业务 2、缴费金额校验 3、计算酬金
	 * 
	 * @param cm
	 * @param bm
	 */
	protected boolean checkBusinessRemuneration(ControlMessage cm,
			BusinessMessage bm) {

		BigDecimal packageId = null;
		BigDecimal formulaId = null;

		TbBiCompany shop = bm.getShop();
		// 商户表，酬金套餐
		try {
			packageId = prepayDao.getShopPackageId(bm.getShopCode());
			if (packageId == null) {

				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("您尚无开通此套餐,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("您尚无开通此套餐,请拨打客服电话咨询!");
				logger.info("该商户没有酬金套餐查询表TB_BI_COMPANY,商户号: "
						+ bm.getShopCode());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询商户套餐信息失败, 请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询商户套餐信息失败, 请联系管理员!");
			logger.info("查询商户套餐信息失败,查询表TB_BI_COMPANY,商户号: " + bm.getShopCode());
			return false;
		}

		try {
			formulaId = prepayDao.getFormulaId(packageId, bm.getBusinessType());
			if (formulaId == null) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("您尚无开通此套餐,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("您尚无开通此套餐,请拨打客服电话咨询!");
				logger.info("该商户无该套餐信息,查询表TB_BI_SALLARY_PACKAGE_FORMULA!商户号: "
						+ bm.getShopCode() + ",packageId: " + packageId
						+ "业务号: " + bm.getBusinessType());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询商户套餐信息失败, 请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询商户套餐信息失败, 请联系管理员!");
			logger.info("查询商户套餐信息失败,查询表TB_BI_SALLARY_PACKAGE_FORMULA!商户号: "
					+ bm.getShopCode() + ",packageId: " + packageId + "业务号: "
					+ bm.getBusinessType());
			return false;
		}

		// 校验
		try {
			if (!prepayDao.checkRemunerationByShopBusiCode(formulaId,
					bm.getBusinessType())) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("商户没有该业务的套餐, 请设置业务套餐重新发送!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户没有该业务的套餐, 请设置业务套餐重新发送!");
				logger.info("商户没有该业务的套餐,查询表：TB_BI_FORMULA,商户号: "
						+ bm.getShopCode() + ",formulaId: " + formulaId
						+ "业务号：" + bm.getBusinessType());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户没有该业务的套餐, 请设置业务套餐重新发送!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户没有该业务的套餐, 请设置业务套餐重新发送!");
			logger.info("校验商户是否该业务的套餐出错,查询表：TB_BI_FORMULA,商户号: "
					+ bm.getShopCode() + ",formulaId: " + formulaId + "业务号："
					+ bm.getBusinessType());
			return false;
		}

		// 校验金额,是否小于最小金额
		if (!checkPaymentBalance(cm, bm, shop)) {
			return false;
		}

		// 计算酬金
		if (!calculateRemuneration(cm, bm, formulaId, packageId)) {
			return false;
		}

		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		return true;
	}

	/**
	 * 缴费金额校验
	 * 
	 * 业务：当支付类型是备付金，并且缴费方式是预存款时，如果缴费金额小于该商户所在机构的该交易最小金额时则退出缴费交易，
	 * 如果缴费金额大于最大金额时，检查商户是否有授权额度信息，并且该授权额度和缴费额度相等。 缴费成功后则删除此授权信息，日终也删除此授权信息。
	 * 
	 * @param bm
	 * @param amount
	 *            交易金额
	 */
	protected boolean checkPaymentBalance(ControlMessage cm,
			BusinessMessage bm, TbBiCompany shop) {

		// 业务机构
		TbBiBusinessUnit businessUnit = null;
		try {
			Object obj1 = baseHibernateDao.get(
					TbBiBusinessUnit.class,
					new TbBiBusinessUnitId(bm.getBusinessType(), shop
							.getUnitcode()));
			if (obj1 != null) {
				businessUnit = (TbBiBusinessUnit) obj1;
			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("取默认缴费额度有错, 请联系管理员!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("取默认缴费额度,查询表:TB_BI_BUSINESS_UNIT,商户号: "
						+ bm.getShopCode() + ",业务号: " + bm.getBusinessType()
						+ "机构号：" + shop.getUnitcode());
				logger.error("取默认缴费额度,查询表:TB_BI_BUSINESS_UNIT,商户号: "
						+ bm.getShopCode() + ",业务号: " + bm.getBusinessType()
						+ "机构号：" + shop.getUnitcode());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("取默认缴费额度有错, 请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("取默认缴费额度有错, 请联系管理员!");
			logger.error("取默认缴费额度有错,查询表:TB_BI_BUSINESS_UNIT,商户号: "
					+ bm.getShopCode() + ",业务号: " + bm.getBusinessType()
					+ "机构号：" + shop.getUnitcode(),e);
			return false;
		}

		// 业务类
		TbBiBusiness tbBiBusiness = null;
		try {
			Object obj1 = baseHibernateDao.get(TbBiBusiness.class,
					bm.getBusinessType());
			if (obj1 != null) {
				tbBiBusiness = (TbBiBusiness) obj1;
			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("取默认缴费额度有错, 请联系管理员!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("取默认缴费额度有错, 请联系管理员!");
				logger.error("取默认缴费额度有错,查询表：TB_BI_BUSINESS,商户号: "
						+ bm.getShopCode() + ",业务号: " + bm.getBusinessType());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("取默认缴费额度有错, 请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("取默认缴费额度有错, 请联系管理员!");
			logger.info("取默认缴费额度有错,查询表：TB_BI_BUSINESS,商户号: "
					+ bm.getShopCode() + ",业务号: " + bm.getBusinessType());
			return false;
		}

		// 备付金
		logger.info("资金归集方式[{}],预存标志:[{}]",bm.getPayType(),bm.getFeeType());
		if (bm.getPayType().equals(GlobalConst.PAYMENT_TYPE_RESCASH)) {
			// 预存款
			if (!bm.getFeeType().equals(GlobalConst.FEE_TYPE_OWE)) {

				// 小于该商户所在机构的该交易最小缴费金额
				if (bm.getAmount() < businessUnit.getFeemix()) {

					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("金额小于该业务最小缴费金额, 请重新查询缴费!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("金额小于该业务最小缴费金额, 请重新查询缴费!");
					logger.info("该笔缴费小于该业务最小金额,正常退出!");
					return false;
				}
			

			}

			// 大于该交易的最大金额时,如果有正确的授权信息，则通过
			if (bm.getAmount() - tbBiBusiness.getDefaultMax() > 0) {

				// 校验授权(商户号、交易码、用户号、缴费金额、授权日期)
				logger.info("company："
				+bm.getShopCode()+"busiCode:" 
				+ bm.getBusinessType()+"costomer:"
				+bm.getUserCode()+"date:"
				+bm.getTranDate()+ "amount:"
				+bm.getAmount());
				
				try {
					if (!companyDao.checkCompanyAuthorize(bm.getShopCode(),
							bm.getBusinessType(), bm.getUserCode(),
							bm.getAmount(), bm.getTranDate())) {
							bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
							bm.setResponseMsg("缴费金额超限，请先授权!");
							cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
							cm.setResultMsg("缴费金额超限，请先授权!");
							logger.info("缴费金额超限,正常退出!");
							return false;
					}
				} catch (Exception e) {
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("后台取商户缴费授权信息出错!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("后台取商户缴费授权信息出错!");
					logger.info("取商户授权信息有错,查询表：TB_BI_COMPANY_AUTHORIZE,商户号:"
							+ bm.getShopCode()
							+ ",业务号:"
							+ bm.getBusinessType()
							+ ",客户号:"
							+ bm.getUserCode() + ",缴费金额:" + bm.getAmount());
					return false;
				}

			}
		}

		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		return true;
	}

	/**
	 * 计算酬金计算酬金 设置bm 酬金金额、应缴税费、设备折旧费用
	 * 
	 * @param cm
	 * @param bm
	 * @param packageId
	 * @param formulaId
	 * @param tbBiSallaryPackage
	 */
	protected boolean calculateRemuneration(ControlMessage cm,
			BusinessMessage bm, BigDecimal formulaId, BigDecimal packageId) {

		double dSumMoney = 0.000; /* 酬金金额 */
		double dTAX = 0.000;
		double dDEPRECIATION = 0.000;
		double dOTHER = 0.000;

		Object obj = null;
		TbBiSallaryPackage tbBiSallaryPackage = null;

		// 根据id获取酬金套餐表
		try {
			obj = baseHibernateDao.get(TbBiSallaryPackage.class, packageId);
			if (obj != null) {
				tbBiSallaryPackage = (TbBiSallaryPackage) obj;
			} else {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("您尚无开通此套餐,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("您尚无开通此套餐,请拨打客服电话咨询!");
				logger.info("该商户无该套餐信息,查询表TB_BI_SALLARY_PACKAGE!商户号: "
						+ bm.getShopCode() + ",packageId: " + packageId);
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询商户套餐信息失败, 请联系管理员!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询商户套餐信息失败, 请联系管理员!");
			logger.info("查询商户套餐信息失败,查询表TB_BI_SALLARY_PACKAGE!商户号: "
					+ bm.getShopCode() + "packageId: " + packageId);
			return false;
		}

		TbBiFormulaDetail tbBiFormulaDetail = null;
		try {
			tbBiFormulaDetail = prepayDao.getTbBiFormulaDetail(formulaId,
					bm.getAmount());
			if (tbBiFormulaDetail == null) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("系统内部错, 请拨打客服中心查询该笔交易!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("系统内部错, 请拨打客服中心查询该笔交易!");
				logger.info("查询酬金公式明细表出错,查询表:TB_BI_FORMULA_DETAIL,商户号: "
						+ bm.getShopCode() + ",formulaId: " + formulaId
						+ ",金额:" + bm.getAmount());
				return false;
			}
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("系统内部错, 请拨打客服中心查询该笔交易!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("系统内部错, 请拨打客服中心查询该笔交易!");
			logger.info("查询酬金公式明细表出错,查询表:TB_BI_FORMULA_DETAIL,商户号: "
					+ bm.getShopCode() + ",formulaId: " + formulaId + ",金额:"
					+ bm.getAmount());
			return false;
		}

		dTAX = tbBiSallaryPackage.getTax();
		dDEPRECIATION = tbBiSallaryPackage.getDepreciation();
		dOTHER = tbBiSallaryPackage.getOther();

		// 1-按笔数
		if (tbBiFormulaDetail.getCalculateType().equals(
				GlobalConst.Formula_CALCULATE_TYPE_TIME)) {
			dSumMoney = tbBiFormulaDetail.getCalculateNum();

			// 2-按金额
		} else if (tbBiFormulaDetail.getCalculateType().equals(
				GlobalConst.Formula_CALCULATE_TYPE_AMOUNT)) {
			dSumMoney = bm.getAmount() * tbBiFormulaDetail.getCalculateNum()
					/ 100.000;
		}
		dTAX = dSumMoney * dTAX / 100.000;
		dSumMoney = dSumMoney - dTAX - dOTHER;

		// 设置
		bm.setSalary(dSumMoney); // 酬金金额
		bm.setTax(dTAX); // 应缴税费
		bm.setDepreciation(dDEPRECIATION); // 设备折旧费用
		bm.setOther(dOTHER);

		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);

		logger.info(
				"佣金计算成功!商户号:{},酬金金额:{},应缴税费:,设备折旧费用{},其他:" + dOTHER,
				new Object[] { bm.getShopCode(), dSumMoney, dTAX, dDEPRECIATION });
		return true;
	}

	/**
	 * 返回交易类型 
	 * 01-缴费交易；
	 * @return
	 */
	@Override
	protected String tradeType() {
		return "01";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.business.service.request.RequestBusinessService#
	 * needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		return true;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("1");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 发送第三方
		cm.setServiceCallFlag("1");
	}
	
	/**
	 * 检查电话号码
	 * @param bm
	 * @return
	 */
	protected void checkPhoneNum(BusinessMessage bm){
	}
	
	/**
	 * 获取现金缴费临时表数据
	 * 
	 * @param cm
	 * @param bm
	 */
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		return true;
	}
}
