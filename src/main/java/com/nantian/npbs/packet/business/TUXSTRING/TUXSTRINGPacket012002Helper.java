package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;

/**
 * 河电国标卡IC卡现金缴费
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket012002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		HeGBElecICCard cardData = (HeGBElecICCard)bm.getCustomData();
		
		// 购电权限
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_OPP", cardData.getPermissions());
		
		// 用户地址
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_ADD", cardData.getAddress());
		
		// 用户名称
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_NAME", cardData.getUserName());

		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_NO", cardData.getUserCode());

		// 用户IC卡序列号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_ICNO", cardData.getCardSeqNo());

		// 电表识别号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_ID", cardData.getCardCode());

		// 用户卡卡片信息
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_INFO", cardData.getCardInfo());

		// 电表出厂编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_GWNO", cardData.getElecFactoryCode());

		// 随机数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_SJ", cardData.getRandomNum());

		// 购电次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_NUM", cardData.getBuyElecTimes());

		// 欠费金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_AMT", cardData.getFee());

		// 电卡类型
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_TYPE", cardData.getCardType());

		// 购电值(金额)
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_AMT2", String.valueOf(bm.getAmount()));

		// 电卡状态标志位
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_UN", cardData.getCardState());
		
		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO", bm.getPbSeqno());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE", bm.getTranDate());
		

		// 剩余金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_FEE", cardData.getSyAmount());

		// 预收金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_PAY", cardData.getYsAmount());

		// 核算单位编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_CODE", cardData.getCheckUnitCode());

		// 低保户标志
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_DBCODE", cardData.getDbhFlag());

		// 低保户剩余金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_DBPAY", cardData.getDibaofei());
		
		// 调整金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_TZFEE", cardData.getTzAmount());	
		
	}
	
	

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		HeGBElecICCard cash  = new HeGBElecICCard();
		
		// 钱包文件的Mac值
		String walletMac1 = (String) fieldValues.get("D13_13_HEGB_MAC1");
		if (walletMac1 == null) throw new PacketOperationException();
		cash.setWalletMac1(walletMac1);

		// 返写区文件的Mac值
		String walletMac2 = (String) fieldValues.get("D13_13_HEGB_MAC2");
		if (walletMac2 == null) throw new PacketOperationException();
		cash.setWalletMac2(walletMac2);
		
		// 参数信息文件
		String walletMac3 = (String) fieldValues.get("D13_13_HEGB_MAC3");
		if (walletMac3 == null) throw new PacketOperationException();
		cash.setWalletMac3(walletMac3);

		// 参数信息文件Mac值
		String walletMac4 = (String) fieldValues.get("D13_13_HEGB_MAC4");
		if (walletMac4 == null) throw new PacketOperationException();
		cash.setWalletMac4(walletMac4);

		// 写卡数据
		String walletPacket = (String) fieldValues.get("D13_13_HEGB_PAG");
		if (walletPacket == null) throw new PacketOperationException();
		cash.setWalletPacket(walletPacket);
		
		// 是否阶梯
		String levFlag = (String) fieldValues.get("D13_13_HEGB_JT");
		if (levFlag == null) throw new PacketOperationException();
		cash.setLevFlag(levFlag);
		
		// 本年一档用电量
		String lev1Electric = (String) fieldValues.get("D13_13_HEGB_STAGE1");
		if (lev1Electric == null) throw new PacketOperationException();
		if("".equals(lev1Electric.trim())) {
			lev1Electric = "0";
		}
		cash.setLev1Electric(lev1Electric);
		
		
		// 本年二档用电量
		String lev2Electric = (String) fieldValues.get("D13_13_HEGB_STAGE2");
		if (lev2Electric == null) throw new PacketOperationException();
		if("".equals(lev2Electric.trim())) {
			lev2Electric = "0";
		}
		cash.setLev2Electric(lev2Electric);
		
		
		// 本年三档用电量
		String lev3Electric = (String) fieldValues.get("D13_13_HEGB_STAGE3");
		if (lev3Electric == null) throw new PacketOperationException();
		if("".equals(lev3Electric.trim())) {
			lev3Electric = "0";
		}
		cash.setLev3Electric(lev3Electric);
		
		
		// 第N档剩余电量
		String levnElectric = (String) fieldValues.get("D13_13_HEGB_STAGE4");
		if (levnElectric == null) throw new PacketOperationException();
		cash.setLevnElectric(levnElectric);
		
		//复制调整金额
		HeGBElecICCard cardData = (HeGBElecICCard)bm.getCustomData();
		cash.setTzAmount(cardData.getTzAmount());
		bm.setCustomData(cash);
		
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
			"D13_13_HEGB_SEQNO",               //流水号
			"D13_13_HEGB_MAC1",                //钱包文件的Mac值
			"D13_13_HEGB_MAC2",                //返写区文件的Mac值
			"D13_13_HEGB_MAC3",                //参数信息文件
			"D13_13_HEGB_MAC4",                //参数信息文件Mac值
			"D13_13_HEGB_PAG",                  //写卡数据
			"D13_13_HEGB_JT",                   //是否阶梯
			"D13_13_HEGB_STAGE1",                //本年一档用电量
			"D13_13_HEGB_STAGE2",                //本年二档用电量
			"D13_13_HEGB_STAGE3",                //本年三档用电量
			"D13_13_HEGB_STAGE4"                 //第N档剩余电量
		};
		return fields;
	}

}
