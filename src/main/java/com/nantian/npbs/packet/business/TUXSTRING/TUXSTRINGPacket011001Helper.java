package com.nantian.npbs.packet.business.TUXSTRING;

import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 *新奥燃气IC卡查询
 * @author wzd
 *
 */
@Component
public class TUXSTRINGPacket011001Helper extends TUXSTRINGPacketxxx001Helper {

	private static Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket011001Helper.class);

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

		// 客户预购气量
		PacketUtils.addFieldValue(fieldValues, "D13_13_XAIC_BUY", cardData.getXAIC_Buy());
		
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

		//DecimalFormat df = new DecimalFormat("0.00");
		XAICCardData cash = null;
		if(null != bm.getCustomData() && bm.getCustomData() instanceof XAICCardData) {
			cash = (XAICCardData)bm.getCustomData();
		}
		
		if(cash != null) {
			// 发卡次数
			String xaic_no = (String) fieldValues.get("D13_13_XAIC_NO");
			if (xaic_no == null) throw new PacketOperationException();		
			cash.setXAIC_No(xaic_no);
			
			
			// 用户姓名
			String xaic_name = (String) fieldValues.get("D13_13_XAIC_NAME");
			if (xaic_name == null) throw new PacketOperationException();
			cash.setXAIC_Name(xaic_name);
			bm.setUserName(xaic_name);
			
			// 用户地址
			String add = (String) fieldValues.get("D13_13_XAIC_ADD");
			if (add == null) throw new PacketOperationException();
			cash.setXAIC_Add(add);
			
			// 最大购气量
			String xaic_maxgas = (String) fieldValues.get("D13_13_XAIC_MAXGAS");
			if (xaic_maxgas == null) throw new PacketOperationException();
			cash.setXAIC_MaxGas(xaic_maxgas);

			// 购气单价
			String xaic_amt1 = (String) fieldValues.get("D13_13_XAIC_AMT1");
			if (xaic_amt1 == null) throw new PacketOperationException();
			cash.setXAIC_Amt1(xaic_amt1);
			
			//账户余额		
			String xaic_amt = (String) fieldValues.get("D13_13_XAIC_AMT");
			if (xaic_amt == null) throw new PacketOperationException();
			cash.setXAIC_Amt(xaic_amt);
			
			//预存标志
			if(Double.valueOf(xaic_amt)>0) {
				bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
			}else {
				bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
			}
			
			//购气量
			String xaic_buy = (String) fieldValues.get("D13_13_XAIC_BUY");
			if (xaic_buy == null) throw new PacketOperationException();
			cash.setXAIC_Buy(xaic_buy);
			
			// 购气金额
			String xaic_cost = (String) fieldValues.get("D13_13_XAIC_COST");
			if (xaic_cost == null) throw new PacketOperationException();			
			cash.setXAIC_Cost(xaic_cost.replace(".", ""));		
			
		}else {
			logger.info("数据错误！");
		}	
		
		bm.setCustomData(cash);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_XAIC_NO", 			// 发卡次数
				"D13_13_XAIC_NAME",			// 用户姓名
				"D13_13_XAIC_ADD",			// 用户地址
				"D13_13_XAIC_MAXGAS",		// 最大购气电量
				"D13_13_XAIC_AMT1",    		// 购气单价
				"D13_13_XAIC_AMT",			// 帐户余额
				"D13_13_XAIC_BUY",			// 购气量
				"D13_13_XAIC_COST",		    // 购气金额				
		};
		return fields;
	}
	
}
