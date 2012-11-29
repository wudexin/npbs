package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 华电现金缴费
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket014002Helper extends TUXSTRINGPacketxxx002Helper{

	private static Logger logger = LoggerFactory.getLogger(TUXSTRINGPacket014002Helper.class);
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		logger.info("向商务平台打包(014002) - 开始");
		
		HuaElecCash cash = (HuaElecCash)bm.getCustomData();
		
		// 用户编号
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_CUSTOMERNO", bm.getUserCode());
		
		// 账户余额
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_USERACCOUNT", String.valueOf(cash.getAccBalance()));
		
		// 缴费金额
		try {
			PacketUtils.addFieldValue(fieldValues, "D13_13_HBE_ BUYACCOUNT", 
					FieldUtils.leftAddZero4FixedLengthString(
							String.valueOf(bm.getAmount()), 16));
		} catch (Exception e) {
			logger.error("向商务平台打包(014002)失败：缴费金额转换失败！");
			throw new PacketOperationException();
		}
		
		// 供电单位编号
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_ORGNO", cash.getOrgNo());
		
		// 记录条数
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_RECORDNUM", cash.getRecordNo());
		
		// 记录明细
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_RECORD", cash.getRecord());
		
		// 便民日期
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_BM_DATE", bm.getTranDate());
		
		// 便民流水
		PacketUtils.addFieldValue(
				fieldValues, "D13_13_HBE_BM_SEQNO", bm.getPbSeqno());
	}

	@Override
	public String[] hasFields() {
		String[] fields = {
			"D13_13_HBE_SEQNO",          //平台流水
			"D13_13_HBE_THIS_BALANCE",   //本次余额
			"D13_13_HBE_SE_NUM",         //起止示数
			"D13_13_HBE_LEV_INCPQ"       //年阶梯累计电量
		};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) 
			throws PacketOperationException {
		logger.info("解商务平台包(014002) - 开始");
		
		// 商务平台返流水
		String sysSerial = (String) fieldValues.get("D13_13_HBE_SEQNO");
		if (sysSerial == null || "".equals(sysSerial) ){
			logger.error("解商务平台包(014002)失败： 电子商务平台返回流水解析错误!");
			throw new PacketOperationException();
		}
		
		// 账户余额
		String thisBal = (String) fieldValues.get("D13_13_HBE_THIS_BALANCE");
		if (thisBal == null || "".equals(thisBal) ){
			logger.error("解商务平台包(014002)失败： 电子商务平台账户余额解析错误!");
			throw new PacketOperationException();
		}
		
		// 起止示数
		String seNum = (String) fieldValues.get("D13_13_HBE_SE_NUM");
		if (seNum == null || "".equals(seNum) ){
			logger.error("解商务平台包(014002)失败： 电子商务平台起止示数解析错误!");
			throw new PacketOperationException();
		}
		
		String levIncpq = (String) fieldValues.get("D13_13_HBE_LEV_INCPQ");
		if(levIncpq == null) throw new PacketOperationException();
		
		
		HuaElecCash cash = (HuaElecCash)bm.getCustomData();
		cash.setSysSerial(sysSerial);
		cash.setThisBalance(thisBal);//本次余额
		cash.setLevIncpq(levIncpq);
		
		//根据“起止示数”判断是否显示“该用户为预交、多月...”
		if(null == seNum || "".equals(seNum.trim()) || "-".equals(seNum.trim())){
			cash.setIsShowDetail(true);	
		}else{
			cash.setIsShowDetail(false);
			cash.setSeNum(seNum);
		}
		
		bm.setCustomData(cash);
		// 商务平台返流水(和报文头流水号重复)
		//bm.setSysJournalSeqno(sysSerial);
	}

}
