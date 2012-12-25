package com.nantian.npbs.packet;

import java.util.ArrayList;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;

/**
 * @author TsaiYee
 *
 */
public class BusinessMessage {
	private CHANEL_TYPE chanelType; 	//渠道类型
	private String systemChanelCode ;   //交易系统编码，渠道编号：01-pos，02-epos，03-电子商务平台
	private String tranType; 			//交易类型 01-缴费交易；02-取消交易；04-冲正交易；05-写卡成功确认；06-写卡失败确认；07-查询；08-管理
	private String tranCode; 			//交易码
	private String tranDate;            //交易日期（sysDate）
	private String tranTime;            //交易时间
	private String businessType; 		//交易类型,交易码前三位
	private double amount;  			//交易金额
	
	//商户
	private TbBiCompany shop;           //商户表
	private String shopCode;  			//商户代码
	private byte[] shopPINData;  		//商户密码（密文）
	private String shopName;            //商户名称
	private String shopState;           //商户状态
	private String checkState;          //签到状态
	private String shopAccount; 		//商户账号
	private String shopInst;			//商户所属机构
	
	private TbBiPrepay prepay;           //备付金表
	private String prePayAccno;         //备付金账户
	private String prePayName;          //备付金名称
	private double preBalance;          //备付金余额
	private double prePaySurCreamt;     //剩余信用额度
	

	private String customerAccount; 	//客户账号
	private byte[] customerPINData;  	//用户密码（密文）
	
	private String userName;			//用户名称
	private String userCode; 			//用户代码
	
	private String posJournalNo; 		//POS流水号
	private String oldPbSeqno;				// 原系统流水号
	private String failureReasonNo;     //  失败原因码
	private String localTime;  			//本地时间 hhmmss
	private String localDate; 			//本地日期 YYYYMMDD
	private String payType; 			//支付类型： 1 - 备付金 
	private String feeType; 			//费用类型： 00 - 预存款  01 - 欠款
	private String track2Data; 			//磁条卡2磁信息
	private String track3Data; 			//磁条卡3磁信息
	private String sysJournalSeqno; 	//系统交易流水号，电子商务平台流水号
	private String oldElecSeqNo;           //电力流水，河北电力省标卡 
	private String responseCode; 		//响应码 
										/* "00"为交易成功。
											"01"为交易失败。
											"02"版本已是最新，不需下载。
			 							 */
	private String terminalId; 			//终端ID
	private String currencyCode;  		//货币代码：only 156 (CNY)
	private ArrayList<Object> journalList; 		//流水列表（查询结果，放入流水对象）
	
	private String additionalTip; 		//附件提示信息
	private Object customData; 			//自定义数据, 55域
	private String origPosJournalSeqno; //原始交易POS流水号,在签到时存放pos最大流水号
	private String origSysJournalSeqno; //原始交易系统流水号
	private String origLocalTime; 		//原始交易本地时间
	private String origLocalDate; 		//原始交易本地日期
	private String origTranAmt;         //原交易金额
	private String queryStartDate; 		//查询开始日期
	private String queryEndDate; 		//查询结束日期	
	private byte[] appDownloadData; 	//应用数据
	private byte[] menuDownloadData; 	//菜单数据
	private String paraDownloadData; 	//参数据
	
	private String cancelFlag;			//取消授权标志
	private String responseMsg; 		//响应信息
	private String summary;				//摘要
	private String remark;				//备注
	
	//流水
	private String seqnoFlag;			//是否登记流水0-不登记，1-登记
	private String pbSeqno; 			//pb流水号
	private String pbState;             //流水状态
	
	//酬金
	private double salary; 				//酬金金额
	private double depreciation;		//设备折旧费用
	private double tax;					//应缴税费
	private double other;				//其他
	
	private String writeICStatus;	   //IC卡写卡状态
	private String writeICResultType;  //IC卡写卡结果类型
	private String OrigPbSeqno;			//IC卡写卡 -- 流水号
	
	/**001 service add*/
	private String midPlatformDate;			//电子商务平台日期
	private String fileNum;			//上传文件个数
	private String ecUnitCode;    //电子商务平台机构
	private String ecOperCode;    //电子商务平台普通柜员
	private String ecAuthOper;    //电子商务平台综合柜员
	/**004 service add*/
	
	private Object msgHeadData; 			//TUXEDO报文头数据对象封装	
	private Object fixMsgHeadData; 			//FIXSTRING报文头数据对象封装
	private Object splitsHeadData; 			//splits报文头数据对象封装
	
	//EPOS报文
	private String PSAMCardNo;				//PSAM卡号
//	private String EPOSSeqNo;				//EPOS流水号
	private String prePayPwd;          		//备付金密码
	private String customField;				//自定义域
	private String queryData;				//查询数据
	private String origDealDate;			//原交易日期
	private String dealDateTime;			//交易日期时间段
	private String appInfoField;			//应用信息域
	private String retData;					//返回数据
	private String pinWorkKeys;				//PIN工作密钥
	
	private String workKeys;				//工作密钥 (包括PIN密钥和MAC密钥)
	private String flagField;				//标志域
	
	private boolean macFlag;           //是否校验mac和生成mac
	private byte[] macData;            //用于生成mac的报文
	private byte[] mac;                //mac值
	
	/**20111027 add 多笔查询用参数*/
	private String packageNum;				//第n个包或者第几页
	private String packageFlag;				//是否有后续数据
	private String numPerPage;				//每页返回笔数
	private String numActual;				//实际实际返回笔数
	
	private Double prePayLowBalance;			//备付金低额提醒额度
	
	private String queryType;					//备付金查询类型:01-续费退费明细查询,02-账户变动全部明细查询
	
	private String lowTipType;//低额提醒类型(ePOS特殊处理)
	private String lowTipAmount;//低额提醒金额(ePOS特殊处理)
	
	//日终
	private String batDate;  //日终日期
	private String batFileName;  //日终对账文件名
	
	private String  ndzhuanyong; //农电专用流水 用来存放取消 补写 撤消补写的 查询流水号
	private String qxMessage;//取消交易的信息
	
	public String getNdzhuanyong() {
		return ndzhuanyong;
	}
	public void setNdzhuanyong(String ndzhuanyong) {
		this.ndzhuanyong = ndzhuanyong;
	}
	public String getFlagField() {
		return flagField;
	}
	public void setFlagField(String flagField) {
		this.flagField = flagField;
	}
	public String getWorkKeys() {
		return workKeys;
	}
	public void setWorkKeys(String workKeys) {
		this.workKeys = workKeys;
	}
	public String getRetData() {
		return retData;
	}
	public void setRetData(String retData) {
		this.retData = retData;
	}
	public String getPinWorkKeys() {
		return pinWorkKeys;
	}
	public void setPinWorkKeys(String pinWorkKeys) {
		this.pinWorkKeys = pinWorkKeys;
	}
	public String getAppInfoField() {
		return appInfoField;
	}
	public void setAppInfoField(String appInfoField) {
		this.appInfoField = appInfoField;
	}
	public String getDealDateTime() {
		return dealDateTime;
	}
	public void setDealDateTime(String dealDateTime) {
		this.dealDateTime = dealDateTime;
	}
	public String getQueryData() {
		return queryData;
	}
	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}
	public String getOrigDealDate() {
		return origDealDate;
	}
	public void setOrigDealDate(String origDealDate) {
		this.origDealDate = origDealDate;
	}
	public String getCustomField() {
		return customField;
	}
	public void setCustomField(String customField) {
		this.customField = customField;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getPSAMCardNo() {
		return PSAMCardNo;
	}
	public void setPSAMCardNo(String pSAMCardNo) {
		PSAMCardNo = pSAMCardNo;
	}
//	public String getEPOSSeqNo() {
//		return EPOSSeqNo;
//	}
//	public void setEPOSSeqNo(String ePOSSeqNo) {
//		EPOSSeqNo = ePOSSeqNo;
//	}
	public String getPrePayPwd() {
		return prePayPwd;
	}
	public void setPrePayPwd(String prePayPwd) {
		this.prePayPwd = prePayPwd;
	}
	public Object getFixMsgHeadData() {
		return fixMsgHeadData;
	}
	public void setFixMsgHeadData(Object fixMsgHeadData) {
		this.fixMsgHeadData = fixMsgHeadData;
	}
	public Object getMsgHeadData() {
		return msgHeadData;
	}
	public void setMsgHeadData(Object msgHeadData) {
		this.msgHeadData = msgHeadData;
	}
	public ArrayList<Object> getJournalList() {
		return journalList;
	}
	public void setJournalList(ArrayList<Object> journalList) {
		this.journalList = journalList;
	}
	
	public String getMidPlatformDate() {
		return midPlatformDate;
	}
	public String getOrigPbSeqno() {
		return OrigPbSeqno;
	}
	public void setOrigPbSeqno(String origPbSeqno) {
		OrigPbSeqno = origPbSeqno;
	}
	public void setMidPlatformDate(String midPlatformDate) {
		this.midPlatformDate = midPlatformDate;
	}
	
	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}
	/**
	 * @return the depreciation
	 */
	public double getDepreciation() {
		return depreciation;
	}
	/**
	 * @param depreciation the depreciation to set
	 */
	public void setDepreciation(double depreciation) {
		this.depreciation = depreciation;
	}
	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}
	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}
	/**
	 * @return the cancelFlag
	 */
	public String getCancelFlag() {
		return cancelFlag;
	}
	/**
	 * @param cancelFlag the cancelFlag to set
	 */
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	/**
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}
	/**
	 * @param responseMsg the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the pbSeqno
	 */
	public String getPbSeqno() {
		return pbSeqno;
	}
	/**
	 * @param pbSeqno the pbSeqno to set
	 */
	public void setPbSeqno(String pbSeqno) {
		this.pbSeqno = pbSeqno;
	}
	/**
	 * @return the tranCode
	 */
	public String getTranCode() {
		return tranCode;
	}
	/**
	 * @param tranCode the tranCode to set
	 */
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	/**
	 * @return the customData
	 */
	public Object getCustomData() {
		return customData;
	}
	/**
	 * @param customData the customData to set
	 */
	public void setCustomData(Object customData) {
		this.customData = customData;
	}
	public CHANEL_TYPE getChanelType() {
		return chanelType;
	}
	public void setChanelType(CHANEL_TYPE chanelType) {
		this.chanelType = chanelType;
	}
	
	public String getCustomerAccount() {
		return customerAccount;
	}
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}
	public String getShopAccount() {
		return shopAccount;
	}
	public void setShopAccount(String shopAccount) {
		this.shopAccount = shopAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPosJournalNo() {
		return posJournalNo;
	}
	public void setPosJournalNo(String posJournalNo) {
		this.posJournalNo = posJournalNo;
	}
	public String getLocalTime() {
		return localTime;
	}
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	public String getLocalDate() {
		return localDate;
	}
	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTrack2Data() {
		return track2Data;
	}
	public void setTrack2Data(String track2Data) {
		this.track2Data = track2Data;
	}
	public String getTrack3Data() {
		return track3Data;
	}
	public void setTrack3Data(String track3Data) {
		this.track3Data = track3Data;
	}
	public String getSysJournalSeqno() {
		return sysJournalSeqno;
	}
	public void setSysJournalSeqno(String sysJournalSeqno) {
		this.sysJournalSeqno = sysJournalSeqno;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public TbBiCompany getShop() {
		return shop;
	}
	public void setShop(TbBiCompany shop) {
		this.shop = shop;
	}
	
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public byte[] getCustomerPINData() {
		return customerPINData;
	}
	public void setCustomerPINData(byte[] pINData) {
		customerPINData = pINData;
	}
	public byte[] getShopPINData() {
		return shopPINData;
	}
	public void setShopPINData(byte[] pINData) {
		shopPINData = pINData;
	}
	
	public String getAdditionalTip() {
		return additionalTip;
	}
	public void setAdditionalTip(String additionalTip) {
		this.additionalTip = additionalTip;
	}
	public String getOrigPosJournalSeqno() {
		return origPosJournalSeqno;
	}
	public void setOrigPosJournalSeqno(String origPosJournalSeqno) {
		this.origPosJournalSeqno = origPosJournalSeqno;
	}
	public String getOrigSysJournalSeqno() {
		return origSysJournalSeqno;
	}
	public void setOrigSysJournalSeqno(String origSysJournalSeqno) {
		this.origSysJournalSeqno = origSysJournalSeqno;
	}
	public String getOrigLocalTime() {
		return origLocalTime;
	}
	public void setOrigLocalTime(String origLocalTime) {
		this.origLocalTime = origLocalTime;
	}
	public String getOrigLocalDate() {
		return origLocalDate;
	}
	public void setOrigLocalDate(String origLocalDate) {
		this.origLocalDate = origLocalDate;
	}
	public String getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public String getQueryEndDate() {
		return queryEndDate;
	}
	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
	public byte[] getAppDownloadData() {
		return appDownloadData;
	}
	public void setAppDownloadData(byte[] appDownloadData) {
		this.appDownloadData = appDownloadData;
	}
	public byte[] getMenuDownloadData() {
		return menuDownloadData;
	}
	public void setMenuDownloadData(byte[] menuDownloadData) {
		this.menuDownloadData = menuDownloadData;
	}
	public String getParaDownloadData() {
		return paraDownloadData;
	}
	public void setParaDownloadData(String paraDownloadData) {
		this.paraDownloadData = paraDownloadData;
	}
	
	public boolean payTypeIsGreenCard() {
		return payType.charAt(0) == '2' ? true : false;
	}
	
	public boolean payTypeIsPrePay() {
		return payType.charAt(0) == '1' ? true : false;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopState() {
		return shopState;
	}
	public void setShopState(String shopState) {
		this.shopState = shopState;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public String getPbState() {
		return pbState;
	}
	public void setPbState(String pbState) {
		this.pbState = pbState;
	}
	public String getPrePayName() {
		return prePayName;
	}
	public void setPrePayName(String prePayName) {
		this.prePayName = prePayName;
	}
	
	public void setPrepay(TbBiPrepay prepay) {
		this.prepay = prepay;
	}
	public TbBiPrepay getPrepay() {
		return prepay;
	}
	
	
	public String getPrePayAccno() {
		return prePayAccno;
	}
	public void setPrePayAccno(String prePayAccno) {
		this.prePayAccno = prePayAccno;
	}
	public double getPreBalance() {
		return preBalance;
	}
	public void setPreBalance(double preBalance) {
		this.preBalance = preBalance;
	}
	public double getPrePaySurCreamt() {
		return prePaySurCreamt;
	}
	public void setPrePaySurCreamt(double prePaySurCreamt) {
		this.prePaySurCreamt = prePaySurCreamt;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public double getOther() {
		return other;
	}
	public void setOther(double other) {
		this.other = other;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	/**
	 * @param shopInst the shopInst to set
	 */
	public void setShopInst(String shopInst) {
		this.shopInst = shopInst;
	}
	/**
	 * @return the shopInst
	 */
	public String getShopInst() {
		return shopInst;
	}
	/**
	 * @param writeICStatus the writeICStatus to set
	 */
	public void setWriteICStatus(String writeICStatus) {
		this.writeICStatus = writeICStatus;
	}
	/**
	 * @return the writeICStatus
	 */
	public String getWriteICStatus() {
		return writeICStatus;
	}
	/**
	 * @param writeICResultType the writeICResultType to set
	 */
	public void setWriteICResultType(String writeICResultType) {
		this.writeICResultType = writeICResultType;
	}
	/**
	 * @return the writeICResultType
	 */
	public String getWriteICResultType() {
		return writeICResultType;
	}
	public String getSeqnoFlag() {
		return seqnoFlag;
	}
	public void setSeqnoFlag(String seqnoFlag) {
		this.seqnoFlag = seqnoFlag;
	}
	public byte[] getMac() {
		return mac;
	}
	public void setMac(byte[] mac) {
		this.mac = mac;
	}
	public boolean isMacFlag() {
		return macFlag;
	}
	public void setMacFlag(boolean macFlag) {
		this.macFlag = macFlag;
	}
	public byte[] getMacData() {
		return macData;
	}
	public void setMacData(byte[] macData) {
		this.macData = macData;
	}
	public String getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}
	public String getPackageFlag() {
		return packageFlag;
	}
	public void setPackageFlag(String packageFlag) {
		this.packageFlag = packageFlag;
	}
	public Double getPrePayLowBalance() {
		return prePayLowBalance;
	}
	public void setPrePayLowBalance(Double prePayLowBalance) {
		this.prePayLowBalance = prePayLowBalance;
	}
	public String getOldPbSeqno() {
		return oldPbSeqno;
	}
	public void setOldPbSeqno(String oldPbSeqno) {
		this.oldPbSeqno = oldPbSeqno;
	}
	public String getFailureReasonNo() {
		return failureReasonNo;
	}
	public void setFailureReasonNo(String failureReasonNo) {
		this.failureReasonNo = failureReasonNo;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}
	public String getNumActual() {
		return numActual;
	}
	public void setNumActual(String numActual) {
		this.numActual = numActual;
	}
	
	public String getOrigTranAmt() {
		return origTranAmt;
	}
	public void setOrigTranAmt(String origTranAmt) {
		this.origTranAmt = origTranAmt;
	}
	public String getSystemChanelCode() {
		return systemChanelCode;
	}
	public void setSystemChanelCode(String systemChanelCode) {
		this.systemChanelCode = systemChanelCode;
	}
	public String getFileNum() {
		return fileNum;
	}
	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}
	public String getEcUnitCode() {
		return ecUnitCode;
	}
	public void setEcUnitCode(String ecUnitCode) {
		this.ecUnitCode = ecUnitCode;
	}
	public String getEcOperCode() {
		return ecOperCode;
	}
	public void setEcOperCode(String ecOperCode) {
		this.ecOperCode = ecOperCode;
	}
	public String getEcAuthOper() {
		return ecAuthOper;
	}
	public void setEcAuthOper(String ecAuthOper) {
		this.ecAuthOper = ecAuthOper;
	}
	public String getBatDate() {
		return batDate;
	}
	public void setBatDate(String batDate) {
		this.batDate = batDate;
	}
	public String getBatFileName() {
		return batFileName;
	}
	public void setBatFileName(String batFileName) {
		this.batFileName = batFileName;
	}
	public Object getSplitsHeadData() {
		return splitsHeadData;
	}
	public void setSplitsHeadData(Object splitsHeadData) {
		this.splitsHeadData = splitsHeadData;
	}
	public String getOldElecSeqNo() {
		return oldElecSeqNo;
	}
	public void setOldElecSeqNo(String oldElecSeqNo) {
		this.oldElecSeqNo = oldElecSeqNo;
	}
	/** 低额提醒类型（ePOS特殊处理）*/
	public void setLowTipType(String lowTipType) {
		this.lowTipType = lowTipType;
	}
	/** 低额提醒类型（ePOS特殊处理）*/
	public String getLowTipType() {
		return lowTipType;
	}
	/** 低额提醒金额（ePOS特殊处理）*/
	public void setLowTipAmount(String lowTipAmount) {
		this.lowTipAmount = lowTipAmount;
	}
	/** 低额提醒金额（ePOS特殊处理）*/
	public String getLowTipAmount() {
		return lowTipAmount;
	}
	public String getQxMessage() {
		return qxMessage;
	}
	public void setQxMessage(String qxMessage) {
		this.qxMessage = qxMessage;
	}
}
