package com.nantian.npbs.packet.business.ISO8583;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityICCardData;
import com.nantian.npbs.packet.internal.HeGBElecICCard;

/**
 * 河电智能电卡补写卡交易
 * @author qxl
 *
 */
@Component
public class ISO8583Packet012002Helper extends ISO8583Packetxxx002Helper{

	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet012002Helper.class);
	
	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电国标卡IC卡缴费--解包55位元读卡信息解包--开始");
		
		// 使用HeGBElecICCard存储解包后的内容，然后将对象放入bm的customData
		HeGBElecICCard icData = new HeGBElecICCard();
		
		//组织需要解包的字段，序号参见ElectricField55Config
		int hasFields[] = {48,2,3,4,45,31,46,5,6,7,47};
		
		String buffer = (String)field;
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(hasFields, buffer);

			// 读卡信息
			ISO8583FieldICCardUtil.unpackHBGBCardFieldICCard(values[48],icData);

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
			
			// 购电权限
			String permissions = (String)values[45];
			icData.setPermissions(permissions);
			
			// 欠费金额
			String qfje = (String)values[31];
			icData.setFee(String.valueOf(Double.parseDouble(qfje.trim()) * 100.00));
			
			// 预收金额
			String ysje = (String)values[46];
			icData.setYsAmount(String.valueOf(Double.parseDouble(ysje.trim()) * 100.00));
			
			// 核算单位编号
			String checkUnitCode = (String)values[5];
			icData.setCheckUnitCode(checkUnitCode);
			
			// 低保户标志
			String dbhFlag = (String)values[6];
			icData.setDbhFlag(dbhFlag);
			
			// 低保户剩余金额
			String dibaofei = (String)values[7];
			icData.setDibaofei(String.valueOf(Double.parseDouble(dibaofei.trim()) * 100.00));
			
			// 调整金额
			String tzAmount = (String)values[47];
			icData.setTzAmount(String.valueOf(Double.parseDouble(tzAmount.trim()) * 100.00));

		} catch (Exception e) {
			logger.error("河电省标卡IC卡缴费--解包55位元出错",e);
		}
		
		//set icData to bm
		bm.setCustomData(icData);
		

		logger.info("河电国标卡IC卡缴费--解包55位元读卡信息解包--结束");
	}
	
	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {
		logger.info("河电国标卡IC卡缴费--打包55位元--开始");

		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}		
		HeGBElecICCard customData =  (HeGBElecICCard)bm.getCustomData();
		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {49,50,51,52,53};
		
		Object[] values = new Object[60];
		
		// 钱包文件的Mac值
		values[49] = null == customData.getWalletMac1() ? "":customData.getWalletMac1();
		
		// 返写区文件的Mac值
		values[50] =  null == customData.getWalletMac2() ? "":customData.getWalletMac2();

		// 参数信息文件
		values[51] =  null == customData.getWalletMac3() ? "":customData.getWalletMac3();

		// 参数信息文件Mac值
		values[52] =  null == customData.getWalletMac4() ? "":customData.getWalletMac4();

		// 写卡数据
		values[53] =  null == customData.getWalletPacket() ? "":customData.getWalletPacket();

		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("打包55位元出错",e);
		}
		logger.info("河电国标卡IC卡缴费--打包55位元--结束");
		return buffer;
	}

	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		logger.info("开始打包44位元");
		
		if(!bm.getResponseCode().equals(GlobalConst.RESPONSECODE_SUCCESS)){
			return "缴费失败!";
		}
		StringBuffer str = new StringBuffer();
		if(null != bm.getCustomData() && bm.getCustomData() instanceof HeGBElecICCard) {
			HeGBElecICCard icData = (HeGBElecICCard)bm.getCustomData();
			
			
			
			if(null != icData.getLevFlag() && !"".equals(icData.getLevFlag())) {
				if("1".equals(icData.getLevFlag().trim())) {   //阶梯电价用户打印
					
					if(null != icData.getTzAmount() && !"".equals(String.valueOf(icData.getTzAmount()))) {
						str.append("阶梯差价:").append(icData.getTzAmount().trim()).append("\n");
					}
					 
					int n = 0;    //定义n档信息
					
					if(null != icData.getLev1Electric() && !"".equals(icData.getLev1Electric())) {
					//modify by fengyafang 20120116	 start
					//	str.append("一档电量:").append(icData.getLev1Electric()).append("\n");
						str.append("截止至抄表日期:").append(icData.getLev1Electric()).append("\n");
					 //modify by fengyafang 20120116  end	 	 
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
					
				//	BigDecimal totalElectrict = BigDecimal.valueOf(Double.valueOf(icData.getLev1Electric())
				//			+ Double.valueOf(icData.getLev2Electric()) 
				//			+ Double.valueOf(icData.getLev3Electric()));
					
				//	str.append("本年累计用电量:").append(totalElectrict).append("\n");
					
					if(n == 0||n == 1) {
						str.append("第1档剩余电量为:").append(icData.getLevnElectric()).append("\n");
					}else if(n == 2) {
						str.append("第2档剩余电量为:").append(icData.getLevnElectric()).append("\n");
					}else {
						str.append("已用到第三档。\n");
					}
					
				}else if("0".equals(icData.getLevFlag().trim())){                                        //非阶梯电价用户打印
					if(null != icData.getTzAmount() && !"".equals(String.valueOf(icData.getTzAmount()))) {
						str.append("差价电费:").append(icData.getTzAmount().trim()).append("\n");
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
			e.printStackTrace();
		}	
		
		
	 	bm.setResponseMsg(str.toString());
		
		
		return str.toString();
	}
	
}
