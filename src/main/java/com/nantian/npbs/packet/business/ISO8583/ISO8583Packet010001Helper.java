package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 河电省标卡IC卡查询
 * @author jxw, qxl
 *
 */
@Component
public class ISO8583Packet010001Helper extends ISO8583Packetxxx001Helper {
	
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet010001Helper.class);
	
	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电省标卡IC卡查询--解包55位元读卡信息解包--开始");
		
		// 使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
		ElectricityICCardData icData = new ElectricityICCardData();
		ISO8583FieldICCardUtil.unpackHBSBCardFieldICCard(field,icData);
		bm.setCustomData(icData);
		logger.info("河电省标卡IC卡查询--解包55位元读卡信息解包--结束");
	}
	
	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河电省标卡IC卡查询--打包55位元--开始");
		ElectricityICCardData customData = (ElectricityICCardData) bm.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {2,3,4,31,26,27,28,7};
		Object[] values = new Object[32];
		// 用户编号
		values[2] = null == customData.getUserCode() ? "":customData.getUserCode();
		// 用户名称
		values[3] =  null == customData.getUserName() ? "":customData.getUserName();
		//用电地址
		values[4] =  null == customData.getAddress() ? "":customData.getAddress();
		//欠费金额
		values[31] =  null == customData.getFee() ? "":customData.getFee();
		// 电价名称
		values[26] = null == customData.getElecName() ? "":customData.getElecName();
		// 电价
		values[27] = null ==  customData.getPrice() ? "":customData.getPrice();
		//上次余额
		values[28] = null == customData.getLastBalance() ? "":customData.getLastBalance();
		// 低保户剩余金额
		values[7] = null == customData.getDibaofei() ? "":customData.getDibaofei();
		
		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("打包55位元出错",e);
		}
		logger.info("河电省标卡IC卡查询--打包55位元--结束");
		return buffer;
	}

	
	/**
	 * 
	 * 河电IC卡缴费44域：本次购电量:XX度\n本次余额:XX\n交易时间:XX\n"
	 */
	protected String packField44(BusinessMessage bm) {
		logger.info("开始打包44位元");
		
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return "查询失败!";
		}
		
		ElectricityICCardData customData = (ElectricityICCardData) bm.getCustomData();
		if(null == customData){
			return "查询失败!";
		}
		
		StringBuffer str = new StringBuffer();
		str.append("用户号码:").append(customData.getUserCode()).append("\n");
		str.append("用户姓名:").append(customData.getUserName()).append("\n");
		str.append("欠费金额:").append(customData.getFee()).append("\n");
			
		
		//add by yafang 2012-03-21 start  
		bm.setResponseMsg(str.toString());
		//add by yafang 2012-03-21 end  
		return str.toString();
	}
	
	
	
	/**
	 * 解包55位元读卡信息解包
	 */
//	@Override
//	protected void unpackField55(Object field, BusinessMessage bm) {
//
//		logger.info("河电省标卡IC卡查询--解包55位元读卡信息解包--开始");
//		
//		// 使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
//		ElectricityICCardData icData = new ElectricityICCardData();
//
//		// 组织需要解包的字段，序号参见ElectricFieldReadCardInfoConfig
//		int hasFields[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
//
//		String buffer = (String) field;
//		try {
//			Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, buffer);
//			icData.setCardNo(values[1].toString());                 //卡号
//			icData.setElectricId(values[1].toString());           	//卡号-电表识别号
//			icData.setRandomNum(values[2].toString());   	 		//随机数
//			icData.setCardSerno(values[3].toString());              //卡序列号
//			icData.setReadWriteHandle(values[4].toString());        //读写器句柄
//			icData.setCardStatus(values[5].toString());        		//电卡状态
//			icData.setBuyElecNum(values[6].toString());    			//购电次数
//			icData.setBuyElecTotal(values[7].toString());           //电卡总购电字
//			icData.setRemainElec(values[8].toString());           	//剩余电字
//			icData.setZeroElec(values[9].toString());           	//过零电字
//			icData.setAllUseElec(values[10].toString());           	//总用电字
//			icData.setElecType(values[11].toString());          	//电卡类型
//			icData.setJianydzs(values[12].toString());           	//尖用电字数
//			icData.setFengydzs(values[13].toString());           	//峰用电字数
//			icData.setGuydzs(values[14].toString());           		//谷用电字数
//			icData.setPingydzs(values[15].toString());           	//平用电字数
//			icData.setWritebtime(values[16].toString());           	//回写时间
//		} catch (Exception e) {
//			logger.error("读卡信息解包出错",e);
//		}
//		// set icData to bm
//		bm.setCustomData(icData);
//		logger.info("河电省标卡IC卡查询--解包55位元读卡信息解包--结束");
//	}
}
