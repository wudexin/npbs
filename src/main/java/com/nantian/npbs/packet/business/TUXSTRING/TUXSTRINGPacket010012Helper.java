package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 河电省标电卡撤销
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket010012Helper extends TUXSTRINGPacketxxx012Helper{

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#pack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ElectricityICCardData cardData = (ElectricityICCardData)bm.getCustomData();
		
		// 地区码
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DQM", " ");
		
		// 交易流水号
		PacketUtils.addFieldValue(fieldValues, "OLD_SEQ_NO", bm.getSysJournalSeqno()); 
		
		// 电表识别号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DBSBH", cardData.getCardNo());

		// 电卡类型
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DKLX", cardData.getElecType());

		// 卡序列号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YHKFSYZ", cardData.getCardSerno());
		
		// 随机数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_SJS", cardData.getRandomNum());
		
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#unpack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		ElectricityICCardData customData = new ElectricityICCardData();
		
		// 外部认证数据
		String curElectric = (String) fieldValues.get("D13_13_HESB_WBRZSJ");
		if (curElectric == null) throw new PacketOperationException();
		customData.setOutAuthData(curElectric);

		//原电力交易流水号
		String seqNo = (String) fieldValues.get("D13_13_HESB_YJYLSH");
		if (seqNo == null) throw new PacketOperationException();
		bm.setOldElecSeqNo(seqNo);
		bm.setCustomData(customData);
		
	}


	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HESB_WBRZSJ",   // 外部认证数据
				"D13_13_HESB_YJYLSH"   // 原电力交易流水号
				};

		return fields;
	}
	
}
