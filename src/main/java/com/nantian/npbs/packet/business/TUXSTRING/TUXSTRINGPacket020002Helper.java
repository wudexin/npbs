package com.nantian.npbs.packet.business.TUXSTRING;

import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ZJKRQ;

/**
 * 河北燃气缴费查询
 * 
 * @author fyf
 * 
 */
@Component
public class TUXSTRINGPacket020002Helper extends TUXSTRINGPacketxxx002Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket020002Helper.class);

	public String snametemp="";
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		ZJKRQ cardData = (ZJKRQ) bm.getCustomData();
		snametemp=cardData.getUSER_NAME();
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_USER_NO",bm.getUserCode());//用户编号                      
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_SUM_FEE",cardData.getSUM_FEE());//应缴金额                      
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_PAY_AMT",String.valueOf(bm.getAmount()));//实缴金额                      
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_FEE_MON","000000");//缴费月份，与查询交易时保持一致
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_CERT_NO", "000" );//发票号码（空）                
		PacketUtils.addFieldValue(fieldValues, "B05_VOUC_KIND", "000");//发票类型 （空）               
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_CHANNEL_DATE",bm.getTranDate());//接入渠道日期                  
		PacketUtils.addFieldValue(fieldValues, "D13_13_ZJKG_CHANNEL_SEQNO",bm.getPbSeqno());//接入渠道流水号 
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ZJKRQ cash = new ZJKRQ();

		//  第三方流水号
		String SEQ_NO = (String) fieldValues.get("D13_13_ZJKG_SEQ_NO");
		if (SEQ_NO == null)
			throw new PacketOperationException();
		 cash.setSEQ_NO(SEQ_NO);
		 bm.setUserName(snametemp);
		 
		 
			 
			 // 上次余额
			String LAST_BAL = (String) fieldValues.get("D13_13_ZJKG_LAST_BAL");
			if (LAST_BAL == null)
				throw new PacketOperationException();
			 cash.setLAST_BAL(LAST_BAL);
			 
			 // 本次结存  
			String CURR_BAL = (String) fieldValues.get("D13_13_ZJKG_CURR_BAL");
			if (CURR_BAL == null)
				throw new PacketOperationException();
			 cash.setCURR_BAL(CURR_BAL);
 
		bm.setCustomData(cash);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "D13_13_ZJKG_SEQ_NO",   //20  第三方流水号 
				"D13_13_ZJKG_LAST_BAL",// 12  上次余额    
				"D13_13_ZJKG_CURR_BAL",// 8    本次结存   
				 
		};
		return fields;
	}

}
