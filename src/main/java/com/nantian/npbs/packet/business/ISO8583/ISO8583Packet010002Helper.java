/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 河电省标卡IC卡缴费
 * @author qxl
 *
 */
@Component
public class ISO8583Packet010002Helper extends ISO8583Packetxxx002Helper {
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet010002Helper.class);
	
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河电省标卡IC卡缴费--开始打包55位元--开始");
		if(bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)){
			return null;
		}
		ElectricityICCardData customData = (ElectricityICCardData) bm.getCustomData();

		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {29,12,44};

		Object[] values = new Object[45];

		// 外部认证数据
		values[29] = null == customData.getOutAuthData() ? "":customData.getOutAuthData(); 
		
		// 购电值
		//values[12] =  String.valueOf(customData.getBuyElectric());
			values[12] =  String.valueOf(customData.getBuyElecValue());
		// 原电力交易流水号
		values[44] = null == bm.getOldElecSeqNo()? "":bm.getOldElecSeqNo();
		
		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("河电省标卡IC卡缴费--开始打包55位元出错",e);
		}

		logger.info("河电省标卡IC卡缴费--开始打包55位元--结束");
		return buffer;
	}
	
	
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电省标卡IC卡缴费--解包55位元--开始");
		
		//使用ElectricityICCardData存储解包后的内容，然后将对象放入bm的customData
		ElectricityICCardData icData = new ElectricityICCardData();
		
		//组织需要解包的字段，序号参见ElectricField55Config
		int hasFields[] = {1,2,3,4,31,26,27,28,7};
		
		String buffer = (String)field;
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(hasFields, buffer);

			// 读卡信息
			ISO8583FieldICCardUtil.unpackHBSBCardFieldICCard(values[1],icData);
			// 用户编号
			String userCode = (String)values[2];
			icData.setUserCode(userCode.trim());
			bm.setUserCode(userCode.trim());
			
			// 用户名称
			String userName = (String)values[3];
			icData.setUserName(userName.trim());
			bm.setUserName(userName.trim());
			
			// 用电地址
			String address = (String)values[4];
			icData.setAddress(address.trim());

			//欠费金额
			String qfje = (String)values[31];
			icData.setFee(String.valueOf(Double.parseDouble(qfje.trim()) * 100.00));
			
			// 电价名称
			icData.setElecName((String)values[26]);
			
			// 电价
			String price = (String)values[27];
			icData.setPrice(String.valueOf(Double.parseDouble(price.trim()) * 100.00 ));
			
			// 上次余额
			String lastBalance = (String)values[28];
			icData.setLastBalance(String.valueOf(Double.parseDouble(lastBalance.trim()) * 100.00));
			
			// 低保户剩余金额
			String dibaofei = (String)values[7];
			icData.setDibaofei(String.valueOf(Double.parseDouble(dibaofei.trim()) * 100.00));

			// 购电值
			icData.setCurElectric(String.valueOf(bm.getAmount()));
			
		} catch (Exception e) {
			logger.error("河电省标卡IC卡缴费--解包55位元出错",e);
		}
		
		//set icData to bm
		bm.setCustomData(icData);
		logger.info("河电省标卡IC卡缴费--解包55位元--结束");
	}
	
	/**
	 * 
	 */
	protected String packField44(BusinessMessage bm) {
		logger.info("开始打包44位元");
		
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return "缴费失败!";
		}
		StringBuffer str = new StringBuffer();
		if(null != bm.getCustomData() && bm.getCustomData() instanceof ElectricityICCardData) {
			ElectricityICCardData icData = (ElectricityICCardData)bm.getCustomData();
			
			if(null != icData.getBuyElectric() && !"".equals(icData.getBuyElectric())) {
				str.append("购电度数:").append(icData.getBuyElectric()).append("度\n");
			}
			
			if(null != icData.getCurBalance() && !"".equals(icData.getCurBalance())) {
				str.append("本次余额:").append(icData.getCurBalance()).append("元\n");
			}
			
			
			if(null != icData.getLevFlag() && !"".equals(icData.getLevFlag())) {
				if("1".equals(icData.getLevFlag().trim())) {   //阶梯电价用户打印
					if(null != icData.getBuckleAmt() && !"".equals(String.valueOf(icData.getBuckleAmt()))) {
						str.append("阶梯差价:").append(icData.getBuckleAmt()).append("\n");
					}
					 
					int n = 0;    //定义n档信息
					
					if(null != icData.getLev1Electric() && !"".equals(icData.getLev1Electric())) {
						str.append("一档电量:").append(icData.getLev1Electric()).append("\n");
						if(Integer.valueOf(icData.getLev1Electric()) != 0) {
							n = 1;
						}						
					}
					
					if(null != icData.getLev2Electric() && !"".equals(icData.getLev2Electric())) {
						str.append("二档电量:").append(icData.getLev2Electric()).append("\n");
						
						if(Integer.valueOf(icData.getLev2Electric()) != 0) {
							n = 2;
						}	
						
					}
					
					if(null != icData.getLev3Electric() && !"".equals(icData.getLev3Electric())) {
						str.append("三档电量:").append(icData.getLev3Electric()).append("\n");
						if(Integer.valueOf(icData.getLev3Electric()) != 0) {
							n = 3;
						}	
					}
					
					BigDecimal totalElectrict = BigDecimal.valueOf(Double.valueOf(icData.getLev1Electric())
							+ Double.valueOf(icData.getLev2Electric()) 
							+ Double.valueOf(icData.getLev3Electric()));
					
				//	str.append("本年累计用电量:").append(totalElectrict).append("\n");
					
					if(n == 0||n == 1) {
						str.append("第1档剩余电量为:").append(icData.getLevnElectric()).append("\n");
					}else if(n == 2) {
						str.append("第2档剩余电量为:").append(icData.getLevnElectric()).append("\n");
					}else {
						str.append("已用到第三档。\n");
					}
					
				}else if("0".equals(icData.getLevFlag().trim())){                                        //非阶梯电价用户打印
					if(null != icData.getBuckleAmt() && !"".equals(String.valueOf(icData.getBuckleAmt()))) {
						str.append("差价电费:").append(icData.getBuckleAmt()).append("\n");
					}
				}else  {
					str.append("数据错误!\n");
				}
			}
			
		}
		
		String lownInfo;
		try {
			lownInfo = super.packField44(bm);
			if(!"".equals(lownInfo)) {
				str.append(lownInfo);
			}
		} catch (PacketOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		//add by yafang 2012-03-21 start  
	 	bm.setResponseMsg(str.toString());
		//add by yafang 2012-03-21 end 
		
		return str.toString();
		
	}
	
}
