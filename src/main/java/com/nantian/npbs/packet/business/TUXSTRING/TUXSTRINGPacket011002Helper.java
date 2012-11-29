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
 * 新奥燃气IC卡缴费
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket011002Helper extends TUXSTRINGPacketxxx002Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		XAICCardData cardData = (XAICCardData)bm.getCustomData();
			
		
		// 新奥IC卡号
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_ID", cardData.getXAIC_Id());

		// 新奥备注信息
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BZ", cardData.getXAIC_Bz());	

		// 发卡次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_NO", cardData.getXAIC_No());
		
		// 购气量
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BUY", cardData.getXAIC_Buy());
		
		// 缴费金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_COST", String.valueOf(bm.getAmount()));

		// 加密串
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_IFO", cardData.getXAIC_Ifo());	
		
		// 便民服务站流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BM_SEQNO",bm.getPbSeqno());	

		// 便民服务站日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BM_DATE", bm.getTranDate());	
		
	}
	
	

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		XAICCardData cash = (XAICCardData)bm.getCustomData();		
		
		// 加密串
		String xAIC_ifo = (String) fieldValues.get("D13_13_XAIC_IFO");
		if (xAIC_ifo == null) throw new PacketOperationException();
		cash.setXAIC_Ifo(xAIC_ifo);		
		
		//放于bm的customData中
		bm.setCustomData(cash);
		
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
			"D13_13_XAIC_IFO"              //加密串
			
		};
		return fields;
	}

}
