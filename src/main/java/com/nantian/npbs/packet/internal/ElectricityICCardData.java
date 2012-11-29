/**
 * 
 */
package com.nantian.npbs.packet.internal;

/**
 * @author TsaiYee
 *
 *IC电卡数据信息，参见接口规范电卡信息规定
 */
public class ElectricityICCardData extends ICCardData {
	private String data; 					//IC卡读卡信息(200Bytes)
	private String cardSerno; 		//卡序列号
	private String userCode; 		//用户编号
	
	private String address; 			//地址
	private String dibaofei;			 //低保费	低保户剩余金额
	private String fee; 					//欠费金额
	private String elecName; 		//电价名称
	private String price; 				//电价
	private String lastBalance; 	//上次余额
	private byte[] permissions; 	//购电权限(2Bytes)
	private byte[] remark1; 			//备注1(16Bytes)
	private byte[] remark2; 			//备注2(32Bytes)
	
	private String curRecv;				//本次应收
	private String curElectric;			//购电值		
	private String curBalance;			//本次余额
	private String writeElectric;		//写卡电量
	
	private String outAuthData;		//外部认证数据
	private String writeData;				//写卡数据包
	
	private String electricId;          	// 电表识别号
	private String cardStatus;			//电卡状态
	
	private String randomNum;		//随机数
	private String buyElecMode;		//购电方式
	private String buyElecNum;		//购电次数
	private String buyElecTotal;		//电卡总购电字
	private String remainElec;			//剩余电字
	private String zeroElec;				//过零电字
	private String allUseElec;				//总用电字
	private String ElecType;				//电卡类型
	private String jianydzs;				//尖用电字数
	private String fengydzs;				//峰用电字数
	private String guydzs;					//谷用电字数
	private String pingydzs;				//平用电字数
	private String writebtime;			//回写时间
	private String recNum;			   //电价数量
	
	private String ReadWriteHandle;		//读写器句柄
	
	private String checkUnitCode;			//核算单位编码
	private String dbhFlag;						//低保户标志
	
	/**20111020 add begin*/
	private String userName; 					//用户名称
	private String changeOwdElectric;		//换表欠电量
	private String adjustmentElectric;		//调整电量
	private Double buckleAmt;					//扣减金额
	private String priceAmount;				//电价数量
	private Double electricAmt;				//购电金额
	/**20111020 add end*/
	
	
	/**20111020 add begin*/
	private String  buyElectric;            //本次购电量
	private String  levFlag;		        //阶梯标志
	private String lev1Electric;		    //本年一档用电量
	private String lev2Electric;			//本年二档用电量
	private String lev3Electric;			//本年三档用电量
	private String levnElectric;			//第N档剩余电量
	/**20111020 add end*/
	
	//add by fengyafang 20120718
	private String buyElecValue; //购电量
	
	
	public String getBuyElecValue() {
		return buyElecValue;
	}
	public void setBuyElecValue(String buyElecValue) {
		this.buyElecValue = buyElecValue;
	}
	public String getUserName() {
		return userName;
	}
	public String getChangeOwdElectric() {
		return changeOwdElectric;
	}
	public void setChangeOwdElectric(String changeOwdElectric) {
		this.changeOwdElectric = changeOwdElectric;
	}
	public String getAdjustmentElectric() {
		return adjustmentElectric;
	}
	public void setAdjustmentElectric(String adjustmentElectric) {
		this.adjustmentElectric = adjustmentElectric;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getReadWriteHandle() {
		return ReadWriteHandle;
	}
	public void setReadWriteHandle(String readWriteHandle) {
		ReadWriteHandle = readWriteHandle;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getRandomNum() {
		return randomNum;
	}
	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}
	public String getBuyElecMode() {
		return buyElecMode;
	}
	public void setBuyElecMode(String buyElecMode) {
		this.buyElecMode = buyElecMode;
	}
	public String getBuyElecNum() {
		return buyElecNum;
	}
	public void setBuyElecNum(String buyElecNum) {
		this.buyElecNum = buyElecNum;
	}
	public String getBuyElecTotal() {
		return buyElecTotal;
	}
	public void setBuyElecTotal(String buyElecTotal) {
		this.buyElecTotal = buyElecTotal;
	}
	public String getRemainElec() {
		return remainElec;
	}
	public void setRemainElec(String remainElec) {
		this.remainElec = remainElec;
	}
	public String getZeroElec() {
		return zeroElec;
	}
	public void setZeroElec(String zeroElec) {
		this.zeroElec = zeroElec;
	}
	public String getAllUseElec() {
		return allUseElec;
	}
	public void setAllUseElec(String allUseElec) {
		this.allUseElec = allUseElec;
	}
	public String getElecType() {
		return ElecType;
	}
	public void setElecType(String elecType) {
		ElecType = elecType;
	}
	public String getJianydzs() {
		return jianydzs;
	}
	public void setJianydzs(String jianydzs) {
		this.jianydzs = jianydzs;
	}
	public String getFengydzs() {
		return fengydzs;
	}
	public void setFengydzs(String fengydzs) {
		this.fengydzs = fengydzs;
	}
	public String getGuydzs() {
		return guydzs;
	}
	public void setGuydzs(String guydzs) {
		this.guydzs = guydzs;
	}
	public String getPingydzs() {
		return pingydzs;
	}
	public void setPingydzs(String pingydzs) {
		this.pingydzs = pingydzs;
	}
	public String getWritebtime() {
		return writebtime;
	}
	public void setWritebtime(String writebtime) {
		this.writebtime = writebtime;
	}
	public String getCurRecv() {
		return curRecv;
	}
	public void setCurRecv(String curRecv) {
		this.curRecv = curRecv;
	}
	public String getCurElectric() {
		return curElectric;
	}
	public void setCurElectric(String curElectric) {
		this.curElectric = curElectric;
	}
	public String getCurBalance() {
		return curBalance;
	}
	public void setCurBalance(String curBalance) {
		this.curBalance = curBalance;
	}
	public String getWriteElectric() {
		return writeElectric;
	}
	public void setWriteElectric(String writeElectric) {
		this.writeElectric = writeElectric;
	}
	public String getOutAuthData() {
		return outAuthData;
	}
	public void setOutAuthData(String outAuthData) {
		this.outAuthData = outAuthData;
	}
	public String getWriteData() {
		return writeData;
	}
	public void setWriteData(String writeData) {
		this.writeData = writeData;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the cardSerno
	 */
	public String getCardSerno() {
		return cardSerno;
	}
	/**
	 * @param cardSerno the cardSerno to set
	 */
	public void setCardSerno(String cardSerno) {
		this.cardSerno = cardSerno;
	}
	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the dibaofei
	 */
	public String getDibaofei() {
		return dibaofei;
	}
	/**
	 * @param dibaofei the dibaofei to set
	 */
	public void setDibaofei(String dibaofei) {
		this.dibaofei = dibaofei;
	}
	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}
	/**
	 * @return the elecName
	 */
	public String getElecName() {
		return elecName;
	}
	/**
	 * @param elecName the elecName to set
	 */
	public void setElecName(String elecName) {
		this.elecName = elecName;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the lastBalance
	 */
	public String getLastBalance() {
		return lastBalance;
	}
	/**
	 * @param lastBalance the lastBalance to set
	 */
	public void setLastBalance(String lastBalance) {
		this.lastBalance = lastBalance;
	}
	/**
	 * @return the permissions
	 */
	public byte[] getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(byte[] permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the remark1
	 */
	public byte[] getRemark1() {
		return remark1;
	}
	/**
	 * @param remark1 the remark1 to set
	 */
	public void setRemark1(byte[] remark1) {
		this.remark1 = remark1;
	}
	/**
	 * @return the remark2
	 */
	public byte[] getRemark2() {
		return remark2;
	}
	/**
	 * @param remark2 the remark2 to set
	 */
	public void setRemark2(byte[] remark2) {
		this.remark2 = remark2;
	}
	public Double getBuckleAmt() {
		return buckleAmt;
	}
	public void setBuckleAmt(Double buckleAmt) {
		this.buckleAmt = buckleAmt;
	}
	public String getPriceAmount() {
		return priceAmount;
	}
	public void setPriceAmount(String priceAmount) {
		this.priceAmount = priceAmount;
	}
	public Double getElectricAmt() {
		return electricAmt;
	}
	public void setElectricAmt(Double electricAmt) {
		this.electricAmt = electricAmt;
	}
	public String getElectricId() {
		return electricId;
	}
	public void setElectricId(String electricId) {
		this.electricId = electricId;
	}
	public String getRecNum() {
		return recNum;
	}
	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}
	
	public String getBuyElectric() {
		return buyElectric;
	}
	public void setBuyElectric(String buyElectric) {
		this.buyElectric = buyElectric;
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
}
