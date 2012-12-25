package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 *新奥燃气IC卡撤销
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket011012Helper extends TUXSTRINGPacketxxx012Helper{

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {	
		

		XAICCardData icData = (XAICCardData)bm.getCustomData();	
		
		//电商流水号
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_SEQNO", bm.getSysJournalSeqno());
		
		// 新奥IC卡号
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_ID", icData.getXAIC_Id());
		
		// 新奥备注信息
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BZ", icData.getXAIC_Bz());
		
		// 发卡次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_NO", icData.getXAIC_No());
		
		// 加密串
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_IFO", icData.getXAIC_Ifo());
		
		// 便民服务站日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BM_DATE", bm.getTranDate());
		
		// 便民服务站流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BM_SEQNO", bm.getPbSeqno());
		
	}	
	
	
	@Override
	public String[] hasFields() {
		String hasFields[] = {};
		return hasFields;		
	}
	
}
