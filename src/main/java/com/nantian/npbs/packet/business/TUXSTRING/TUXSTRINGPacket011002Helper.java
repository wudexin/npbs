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
		//000000||20121210|130022940|000120121210130022940|1|B0F86FF2D82AF43D424C25879DF346A95119401B8BF54D5E691E7FC2F7ECC898827A1C37AEBA485A5221214B2CAA2198827A1C37AEBA485A3B9B6CF0B4217DDC|000000022400000001000000022400|
		// 加密串
		String xAIC_ifo = (String) fieldValues.get("D13_13_XAIC_IFO");
		if (xAIC_ifo == null) throw new PacketOperationException();
		cash.setXAIC_Ifo(xAIC_ifo);		
		
		// 阶梯气价信息
		String IC_BM_JT = (String) fieldValues.get("D13_13_XAIC_BM_JT");
		if (IC_BM_JT == null) throw new PacketOperationException();
		cash.setIC_BM_JT(IC_BM_JT);	
		
		//放于bm的customData中
		bm.setCustomData(cash);
		
	}
	
	@Override
	public String[] hasFields() {
		String[] fields = {
			"D13_13_XAIC_IFO",              //加密串
			//add by fengyafnag 
			"D13_13_XAIC_BM_JT"            //阶梯气价信息
		};
		return fields;
	}

}
