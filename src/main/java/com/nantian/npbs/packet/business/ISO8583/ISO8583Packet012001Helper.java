package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;

/**
 * 河电智能电卡查询(国标卡)
 * @author qxl
 *
 */
@Component
public class ISO8583Packet012001Helper extends ISO8583Packetxxx001Helper {

	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet012001Helper.class);
	
	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河电国标卡IC卡查询--解包55位元读卡信息解包--开始");
		
		// 使用HeGBElecICCard存储解包后的内容，然后将对象放入bm的customData
		HeGBElecICCard icData = new HeGBElecICCard();
		ISO8583FieldICCardUtil.unpackHBGBCardFieldICCard(field,icData);
		bm.setCustomData(icData);
		logger.info("河电国标卡IC卡查询--解包55位元读卡信息解包--结束");
	}
	
	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		logger.info("河电国标卡IC查询--打包44域位元--开始");
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		
		StringBuffer str = new StringBuffer();
		
		if(null != bm.getCustomData() && bm.getCustomData() instanceof HeGBElecICCard) {
			HeGBElecICCard customData = (HeGBElecICCard) bm.getCustomData();	
			if(null != customData.getUserCode()) {
				str.append("用户编号:").append(customData.getUserCode()).append("\n");
			}
			if(null != customData.getUserName()) {
				str.append("用户姓名:").append(customData.getUserName()).append("\n");
			}
			if(null != customData.getFee()) {
				str.append("欠费金额:").append(customData.getFee()).append("\n");
			}							
		}else if(null != bm.getAdditionalTip() && !"".equals(bm.getAdditionalTip())) {
			str.append(bm.getAdditionalTip()).append("\n");			
		}else if(null != bm.getResponseMsg() && !"".equals(bm.getResponseMsg())) {
			str.append(bm.getResponseMsg()).append("\n");			
		}
		bm.setResponseMsg(str.toString());
		logger.info("打包44域完成{}",str.toString());
		return str.toString();
		
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河电国标卡IC卡查询--打包55位元--开始");
		HeGBElecICCard customData = (HeGBElecICCard) bm.getCustomData();
		
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		// 组织需要打包的字段，序号参见ElectricField55Config
		int hasFields[] = {2,3,4,45,31,46,5,6,7,47};
		
		Object[] values = new Object[50];
		
		// 用户编号
		values[2] = null == customData.getUserCode() ? "":customData.getUserCode();
		
		// 用户名称
		values[3] =  null == customData.getUserName() ? "":customData.getUserName();
		
		//用电地址
		values[4] =  null == customData.getAddress() ? "":customData.getAddress();
		
		//购电权限45
		values[45] =  null == customData.getPermissions() ? "":customData.getPermissions();
		
		//欠费金额
		values[31] =  null == customData.getFee() ? "":customData.getFee();

		// 预收金额46
		values[46] =  null == customData.getYsAmount() ? "":customData.getYsAmount();
		
		//核算单位编号5
		values[5] =  null == customData.getCheckUnitCode() ? "":customData.getCheckUnitCode();
		
		//低保户标志6
		values[6] =  null == customData.getDbhFlag() ? "":customData.getDbhFlag();
		
		// 低保户剩余金额
		values[7] = null == customData.getDibaofei() ? "":customData.getDibaofei();
		
		//调整金额47
		values[47] = null == customData.getTzAmount()? "":customData.getTzAmount();
		
		String buffer = null;
		try {
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
		} catch (Exception e) {
			logger.error("打包55位元出错",e);
		}
		logger.info("河电国标卡IC卡查询--打包55位元--结束");
		return buffer;
	}
	
}
