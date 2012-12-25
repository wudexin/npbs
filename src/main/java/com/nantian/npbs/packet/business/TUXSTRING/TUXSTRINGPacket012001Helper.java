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

/**
 * 河电国标卡IC卡现金查询
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket012001Helper extends TUXSTRINGPacketxxx001Helper {

	private static Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket012001Helper.class);

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		
		HeGBElecICCard cardData = (HeGBElecICCard)bm.getCustomData();

		// 电表识别号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_ID", cardData.getCardCode());
		
		// 电表出厂编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_GWNO", cardData.getElecFactoryCode());		
		
		
		// 电卡状态标志位
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEGB_UN", cardData.getCardState());

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO", bm.getPbSeqno());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE", bm.getTranDate());

		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {

		DecimalFormat df = new DecimalFormat("0.00");
		
		HeGBElecICCard cash  = new HeGBElecICCard();
		
		// 用户编号
		String userCode = (String) fieldValues.get("D13_13_HEGB_NO");
		if (userCode == null) throw new PacketOperationException();
		logger.info("终端上送编号[{}],电商返回编号[{}]",bm.getUserCode(), userCode);
		cash.setUserCode(userCode);
		bm.setUserCode(userCode);
		
		// 用户名称
		String username = (String) fieldValues.get("D13_13_HEGB_NAME");
		if (username == null) throw new PacketOperationException();
		cash.setUserName(username);
		bm.setUserName(username);
		
		// 用户地址
		String add = (String) fieldValues.get("D13_13_HEGB_ADD");
		if (add == null) throw new PacketOperationException();
		cash.setAddress(add);
		
		// 购电权限
		String permissions = (String) fieldValues.get("D13_13_HEGB_OPP");
		if (permissions == null) throw new PacketOperationException();
		cash.setPermissions(permissions);

		// 欠费金额
		String fee = (String) fieldValues.get("D13_13_HEGB_AMT");
		if (fee == null) throw new PacketOperationException();
		cash.setFee(df.format(Double.parseDouble(fee)/100));
		
		//设置欠款、预存款标志，用于给pos返回
		double amt = Double.valueOf(cash.getFee());
		if(-amt > 0) {
			bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
		}else {
			bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
		}
		
		// 预收金额
		String ysAmount = (String) fieldValues.get("D13_13_HEGB_PAY");
		if (ysAmount == null) throw new PacketOperationException();
		cash.setYsAmount(df.format(Double.parseDouble(ysAmount)/100));
		
		// 核算单位编号
		String checkUnitCode = (String) fieldValues.get("D13_13_HEGB_CODE");
		if (checkUnitCode == null) throw new PacketOperationException();
		cash.setCheckUnitCode(checkUnitCode);

		// 低保户标志
		String dbhFlag = (String) fieldValues.get("D13_13_HEGB_DBCODE");
		if (dbhFlag == null) throw new PacketOperationException();
		cash.setDbhFlag(dbhFlag);

		// 低保户剩余金额
		String dibaofei = (String) fieldValues.get("D13_13_HEGB_DBPAY");
		if (dibaofei == null) throw new PacketOperationException();
		cash.setDibaofei(df.format(Double.parseDouble(dibaofei)/100));

		// 调整金额
		String tzAmount = (String) fieldValues.get("D13_13_HEGB_TZFEE");
		if (tzAmount == null) throw new PacketOperationException();
		cash.setTzAmount(df.format(Double.parseDouble(tzAmount)/100));
		
		bm.setCustomData(cash);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HEGB_NO", 			// 用户编号
				"D13_13_HEGB_NAME",			// 用户名称
				"D13_13_HEGB_ADD",			// 用户地址
				"D13_13_HEGB_OPP",			// 购电权限
				"D13_13_HEGB_AMT",    		// 欠费金额
				"D13_13_HEGB_PAY",			// 预收金额
				"D13_13_HEGB_CODE",			// 核算单位编号
				"D13_13_HEGB_DBCODE",		// 低保户标志
				"D13_13_HEGB_DBPAY",		// 低保户剩余金额
				"D13_13_HEGB_TZFEE"			// 调整金额
		};
		return fields;
	}
	
}
