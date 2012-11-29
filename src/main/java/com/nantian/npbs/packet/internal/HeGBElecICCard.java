package com.nantian.npbs.packet.internal;

/**
 * 河电国标卡
 * @author qxl
 *
 */
public class HeGBElecICCard {

	//卡片序列号
	private String cardSeqNo;
	
	//卡片信息
	private String cardInfo;
	
	//卡片状态标志位
	private String cardState;
	
	//随机数
	private String randomNum;
	
	//电表识别号(电卡编号)
	private String cardCode;
	
	//电表出厂编号
	private String elecFactoryCode;
	
	//电卡类型
	private String cardType;
	
	//购电次数
	private String buyElecTimes;
	
	//剩余金额
	private String syAmount;
	
	// 用户编号
	private String userCode;	
	
	// 用户名称
	private String userName;	
	
	//用电地址
	private String address; 	
	
	//购电权限
	private String permissions; 	
	
	//欠费金额
	private String fee; 					
	
	//预收金额
	private String ysAmount;				
	
	// 核算单位编号
	private String checkUnitCode;
	
	// 低保户标志
	private String dbhFlag;
	
	// 低保户剩余金额
	private String dibaofei;
	
	//调整金额
	private String tzAmount;
	
	//钱包文件的Mac值
	private String walletMac1;
	
	//返写区文件的Mac值
	private String walletMac2;
	
	//参数信息文件
	private String walletMac3;
	
	//参数信息文件Mac值
	private String walletMac4;
	
	//写卡数据
	private String walletPacket;
	
	
	// add  start  wzd 2012年5月23日21:47:59
	
	//是否阶梯
	private String levFlag;
	
	//本年一阶用电量
	private String lev1Electric;
	
	//本年二阶用电量
	private String lev2Electric;
	
	//本年三阶用电量
	private String lev3Electric;
	
	//第N档剩余电量
	private String levnElectric;
	
	// add  end  wzd 2012年5月23日21:47:59

	public String getCardSeqNo() {
		return cardSeqNo;
	}

	public String getLevFlag() {
		return levFlag;
	}

	public void setLevFlag(String levFlag) {
		this.levFlag = levFlag;
	}

	public String getLev1Electric() {
		return lev1Electric;
	}

	public void setLev1Electric(String lev1Electric) {
		this.lev1Electric = lev1Electric;
	}

	public String getLev2Electric() {
		return lev2Electric;
	}

	public void setLev2Electric(String lev2Electric) {
		this.lev2Electric = lev2Electric;
	}

	public String getLev3Electric() {
		return lev3Electric;
	}

	public void setLev3Electric(String lev3Electric) {
		this.lev3Electric = lev3Electric;
	}

	public String getLevnElectric() {
		return levnElectric;
	}

	public void setLevnElectric(String levnElectric) {
		this.levnElectric = levnElectric;
	}

	public void setCardSeqNo(String cardSeqNo) {
		this.cardSeqNo = cardSeqNo;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getElecFactoryCode() {
		return elecFactoryCode;
	}

	public void setElecFactoryCode(String elecFactoryCode) {
		this.elecFactoryCode = elecFactoryCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBuyElecTimes() {
		return buyElecTimes;
	}

	public void setBuyElecTimes(String buyElecTimes) {
		this.buyElecTimes = buyElecTimes;
	}

	public String getSyAmount() {
		return syAmount;
	}

	public void setSyAmount(String syAmount) {
		this.syAmount = syAmount;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getYsAmount() {
		return ysAmount;
	}

	public void setYsAmount(String ysAmount) {
		this.ysAmount = ysAmount;
	}

	public String getCheckUnitCode() {
		return checkUnitCode;
	}

	public void setCheckUnitCode(String checkUnitCode) {
		this.checkUnitCode = checkUnitCode;
	}

	public String getDbhFlag() {
		return dbhFlag;
	}

	public void setDbhFlag(String dbhFlag) {
		this.dbhFlag = dbhFlag;
	}

	public String getDibaofei() {
		return dibaofei;
	}

	public void setDibaofei(String dibaofei) {
		this.dibaofei = dibaofei;
	}

	public String getTzAmount() {
		return tzAmount;
	}

	public void setTzAmount(String tzAmount) {
		this.tzAmount = tzAmount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getWalletMac1() {
		return walletMac1;
	}

	public void setWalletMac1(String walletMac1) {
		this.walletMac1 = walletMac1;
	}

	public String getWalletMac2() {
		return walletMac2;
	}

	public void setWalletMac2(String walletMac2) {
		this.walletMac2 = walletMac2;
	}

	public String getWalletMac3() {
		return walletMac3;
	}

	public void setWalletMac3(String walletMac3) {
		this.walletMac3 = walletMac3;
	}

	public String getWalletMac4() {
		return walletMac4;
	}

	public void setWalletMac4(String walletMac4) {
		this.walletMac4 = walletMac4;
	}

	public String getWalletPacket() {
		return walletPacket;
	}

	public void setWalletPacket(String walletPacket) {
		this.walletPacket = walletPacket;
	}				
	
}
