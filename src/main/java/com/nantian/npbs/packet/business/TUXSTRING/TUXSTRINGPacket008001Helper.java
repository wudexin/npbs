package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.XAGasCashData;

/**
 * 新奥燃气现金代收 查询
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket008001Helper extends TUXSTRINGPacketxxx001Helper {
	
	private static Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket008001Helper.class);
	
	//打包发电商平台
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// IC卡号
		try {
			PacketUtils.addFieldValue(fieldValues, "D13_13_XAG_USER_NO", bm.getUserCode());
		} catch (Exception e) {
			logger.error("IC卡号左补0出错"+e);
		}
		System.out.println("fieldValues.get()"+fieldValues.get("D13_13_XAG_USER_NO"));
		System.out.println("bm.getUserCode()"+bm.getUserCode());
	}

	//解包到便民
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		XAGasCashData cashData = new XAGasCashData();

		// 用户名称
		String username = (String) fieldValues.get("D13_13_XAG_USER_NAME");
		if (username == null) throw new PacketOperationException();
		cashData.setUserName(username);
		bm.setUserName(username); //返回pos
		
		// 用电地址
		String address = (String) fieldValues.get("D13_13_XAG_USER_ADDR");
		if (address == null) throw new PacketOperationException();
		cashData.setUserAdd(address);
		
		// 帐户余额
		String accBalance = (String) fieldValues.get("D13_13_XAG_LAST_BAL");
		if (accBalance == null) throw new PacketOperationException();
		cashData.setAccBalance(accBalance);
		if(Double.parseDouble(accBalance)>0){  
			//预存
			bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
		}else{
			//欠款
			bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
		}
		
		// 发卡次数
		String fkcs = (String) fieldValues.get("D13_13_XAG_CARD_NUM");
		//if (fkcs == null) throw new PacketOperationException();
		if (fkcs == null) fkcs = "";
		cashData.setFkcs(fkcs);
		
		bm.setCustomData(cashData);
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_XAG_USER_NAME",		// 用户名称
				"D13_13_XAG_USER_ADDR",		// 用电地址
				"D13_13_XAG_LAST_BAL",		// 账户余额
				"D13_13_XAG_CARD_NUM",		// 发卡次数
				};
		return fields;
	}
	
}
