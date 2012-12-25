package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;

/**
 * 河电国标卡IC卡现金撤销
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket012012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HeGBElecICCard cardData = (HeGBElecICCard)bm.getCustomData();
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE", bm.getTranDate());
		
		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO", bm.getPbSeqno());
		
		//流水号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_SEQNO", bm.getSysJournalSeqno());
		
		// 用户IC卡序列号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_ICNO", cardData.getCardSeqNo());
		
		// 电卡类型
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_TYPE", cardData.getCardType());
		
		// 用户卡卡片信息
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_INFO", cardData.getCardInfo());
		
		// 电表出厂编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_GWNO", cardData.getElecFactoryCode());

		// 随机数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_SJ", cardData.getRandomNum());
		
		// 电表识别号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_ID", cardData.getCardCode());
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		HeGBElecICCard cash  = new HeGBElecICCard();

		// 写卡数据
		String walletPacket = (String) fieldValues.get("D13_13_HEGB_PAG");
		if (walletPacket == null) throw new PacketOperationException();
		cash.setWalletPacket(walletPacket);
		
		// 钱包文件的Mac值
		String walletMac1 = (String) fieldValues.get("D13_13_HEGB_MAC1");
		if (walletMac1 == null) throw new PacketOperationException();
		cash.setWalletMac1(walletMac1);

		// 返写区文件的Mac值
		String walletMac2 = (String) fieldValues.get("D13_13_HEGB_MAC2");
		if (walletMac2 == null) throw new PacketOperationException();
		cash.setWalletMac2(walletMac2);

		bm.setCustomData(cash);
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HEGB_PAG",               	//钱包文件/写卡数据
				"D13_13_HEGB_MAC1",                 //钱包文件的Mac值
				"D13_13_HEGB_MAC2",                 //返写区文件的Mac值
				"D13_13_HEGB_NEWSEQ"                //冲正新流水号
			};
		return fields;
	}
	
}
