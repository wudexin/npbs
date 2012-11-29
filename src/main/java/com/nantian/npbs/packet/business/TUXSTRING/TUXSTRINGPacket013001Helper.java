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
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 华电IC卡卡表查询（调用电商华电现金接口）
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket013001Helper  extends TUXSTRINGPacketxxx001Helper {

	private static Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket013001Helper.class);
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		logger.info("向商务平台打包(014001) - 开始");
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_CUSTOMERNO", bm.getUserCode());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		logger.info("解商务平台包(014001) - 开始");
		
		HuaElecCash cash  = new HuaElecCash();
		// 用户编号
		String userCode = (String) fieldValues.get("D13_13_HBE_CUSTOMERNO");
		if (userCode == null) throw new PacketOperationException();
		logger.info("终端上送编号[{}],电商返回编号[{}]",bm.getUserCode(), userCode);
		cash.setUserCode(userCode);
		bm.setUserCode(userCode);
		
		// 用户名称
		String username = (String) fieldValues.get("D13_13_HBE_CUSTOMERNAME");
		if (username == null) throw new PacketOperationException();
		cash.setUserName(username);
		bm.setUserName(username);
		
		// 用户地址
		String add = (String) fieldValues.get("D13_13_HBE_CUSTOMERADDRESS");
		if (add == null) throw new PacketOperationException();
		cash.setAddress(add);
		
		// 账户余额
		String accB = (String)fieldValues.get("D13_13_HBE_USERACCOUNT");
		if (accB == null) throw new PacketOperationException();
		cash.setAccBalance(Double.parseDouble(accB));
		// 计算"欠款，预存款标识" 返回POS
		if((-cash.getAccBalance())>0){  
			bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
		}else{
			bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
		}

		// 供电单位编号
		String orgNo = (String) fieldValues.get("D13_13_HBE_ORGNO");
		if (orgNo == null) throw new PacketOperationException();
		cash.setOrgNo(orgNo);
		
		// 记录条数
		String no = (String) fieldValues.get("D13_13_HBE_RECORDNUM");
		if (no == null) throw new PacketOperationException();
		cash.setRecordNo(no);
		
		// 记录明细
		String re = (String) fieldValues.get("D13_13_HBE_RECORD");
		if (re == null) throw new PacketOperationException();
		cash.setRecord(re);
		
		bm.setCustomData(cash);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HBE_CUSTOMERNO", 			// 用户编号
				"D13_13_HBE_CUSTOMERNAME",			// 用户名称
				"D13_13_HBE_CUSTOMERADDRESS",		// 用户地址
				"D13_13_HBE_USERACCOUNT",			// 账户余额
				"D13_13_HBE_ORGNO",    			    // 供电单位编号
				"D13_13_HBE_RECORDNUM",				// 记录条数
				"D13_13_HBE_RECORD"					// 记录明细
		};
		return fields;
	}
	
}
