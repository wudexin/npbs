/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 河电省标电卡申请写卡数据（正常）
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket010003Helper extends TUXSTRINGPacketxxx003Helper {

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#pack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		ElectricityICCardData cardData = (ElectricityICCardData)bm.getCustomData();

		// 地区码
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DQM", " ");   // 不上传
		
		//用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YHBH", bm.getUserCode());

		//购电值
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GDZ", cardData.getCurElectric());
		
		//原电力交易流水号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YJYLSH", bm.getOldElecSeqNo());
		
		//电卡类型
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DKLX", cardData.getElecType());

		//卡序列号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YHKFSYZ", cardData.getCardSerno());

		//随机数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_SJS", cardData.getRandomNum());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE", bm.getTranDate());

		// 接入渠道流水号-必输项12011000008850
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO", bm.getPbSeqno());
		
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#unpack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		
		//写卡数据包
		String writeData = (String) fieldValues.get("D13_13_HESB_XKSJB");
		if (writeData == null) throw new PacketOperationException();
		bm.setCustomData(writeData);
		
	}


	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HESB_XKSJB"   // 写卡数据包
				};
		return fields;
	}
	
}
