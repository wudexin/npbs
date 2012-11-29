/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * @author TsaiYee
 * 
 */
public class ISO8583PacketUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583PacketUtils.class);
	
	/**
	 * @author jxw
	 * @param  fieldValues
	 * @param  fieldNo
	 * @param  BusinessMessage
	 * @param  indispensable falg
	 * @return void
	 */
	public static void unpackField(Object[] fieldValues, int fieldNo,
			BusinessMessage bm, boolean falg) throws PacketOperationException {
		Object value =  fieldValues[fieldNo];
		if (value == null && falg) {
			logger.info("[{}]域没有上传!!!", fieldNo);
			throw new PacketOperationException("[" + fieldNo + "]域没有上传!!!");
		}
		String fieldName = null;
		switch (fieldNo) {
		case 2:
			fieldName = "卡号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setCustomerAccount(((String)value).trim());
			break;
		case 4:
			fieldName = "交易金额";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			double amount = Integer.parseInt((String)value) / 100.00;
			bm.setAmount(amount);
			break;
		case 11:
			fieldName = "pos流水号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setPosJournalNo(((String)value).trim());
			break;
		case 12:
			fieldName = "交易时间";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setLocalTime(((String)value).trim());
			break;
		case 13:
			fieldName = "交易日期";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setLocalDate(((String)value).trim());
			break;
		case 15:
			fieldName = "标志域";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setFlagField(((String)value).trim());
			break;
		case 25:
			fieldName = "费用类型";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setFeeType(((String)value).trim());
			break;
		case 32:
			fieldName = "用户号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setUserCode(((String)value).trim());
			break;
		case 35:
			fieldName = "二磁道信息";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setTrack2Data((String)value);
			break;
		case 36:
			fieldName = "三磁道信息";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setTrack3Data((String)value);
			break;
		case 37:
			fieldName = "pb流水号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setPbSeqno(((String)value).trim());
			break;
		case 38:
			fieldName = "用户名称";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setUserName(((String)value).trim());
			break;
		case 39:
			fieldName = "响应码";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setResponseCode(((String)value).trim());
			break;
		case 41:
			fieldName = "终端号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setTerminalId(((String)value).trim());
			break;
		case 42:
			fieldName = "商户号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setShopCode(((String)value).trim());
			break;
		case 44:
			fieldName = "响应信息";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setResponseMsg(((String)value).trim());
			break;
		case 49:
			fieldName = "货币代码";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setCurrencyCode(((String)value).trim());
			break;
		case 52:
			fieldName = "密码";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, ConvertUtils.bytes2HexStr((byte[])value) });
			bm.setShopPINData((byte[])value);
			break;
		case 53:
			fieldName = "密码2";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, ConvertUtils.bytes2HexStr((byte[])value) });
			bm.setCustomerPINData((byte[])value);
			break;
		case 55:
			fieldName = "电卡数据";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setCustomData(value);
			break;
		case 58:
			fieldName = "屏幕提示信息";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setAdditionalTip(((String)value).trim());
			break;
		case 60:
			fieldName = "交易码";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setTranCode(((String)value).trim());
			break;
		case 61:    //原pb流水号
			fieldName = "原pb流水号";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setOldPbSeqno(((String)value).trim());
			break;
		case 62:
			fieldName = "工作密钥";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			bm.setWorkKeys((String)value);
			break;
		case 64:
			fieldName = "mac值";
			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, ConvertUtils.bytes2HexStr((byte[])value) });
			bm.setMac((byte[])value);
			break;
		}
//		if("byte[]".equals(value.getClass().getSimpleName()))
//			logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, ConvertUtils.bytes2HexStr((byte[])value) });
//		else if("String".equals(value.getClass().getSimpleName()))
//		logger.info("上传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
	}
	

	public static void packField(Object[] fieldValues, int fieldNo,
			BusinessMessage bm) {
		Object value = null;
		String fieldName = null;
		switch (fieldNo) {
		case 2:
			value = bm.getCustomerAccount();
			fieldName = "卡号";
			break;
		case 4:
//			value = Integer.toString((int)(bm.getAmount()*100));
			value = Integer.toString((int)(DoubleUtils.mul(bm.getAmount(), 100)));
			fieldName = "交易金额";
			break;
		case 11:
			value = bm.getPosJournalNo();
			fieldName = "pos流水号";
			break;
		case 12:
			value = bm.getLocalTime();
			fieldName = "交易时间";
			break;
		case 13:
			value = bm.getLocalDate();
			fieldName = "交易日期";
			break;
		case 15:
			value = bm.getFlagField();
			fieldName = "标志域";
			break;
		case 25:
			value = bm.getFeeType();
			fieldName = "费用类型";
			break;
		case 32:
			value = bm.getUserCode();
			fieldName = "用户号";
			break;
		case 35:
			value = bm.getTrack2Data();
			fieldName = "二磁道信息";
			break;
		case 36:
			value = bm.getTrack3Data();
			fieldName = "三磁道信息";
			break;
		case 37:
			value = bm.getPbSeqno();
			fieldName = "pb流水号";
			break;
		case 38:
			value = bm.getUserName();
			fieldName = "用户名称";
			break;
		case 39:
			value = bm.getResponseCode();
			fieldName = "响应码";
			break;
		case 41:
			value = bm.getTerminalId();
			fieldName = "终端号";
			break;
		case 42:
			value = bm.getShopCode();
			fieldName = "商户号";
			break;
		case 44:
			value = bm.getResponseMsg();
			fieldName = "响应信息";
			break;
		case 49:
			value = bm.getCurrencyCode();
			fieldName = "货币代码";
			break;
		case 52:
			value = bm.getShopPINData();
			fieldName = "密码";
			break;
		case 53:
			value = bm.getCustomerPINData();
			fieldName = "密码2";
			break;
		case 55: //电卡数据在各自报文中打包
			value = bm.getCustomData();
			fieldName = "电卡数据";
			break;
		case 58:
			value = bm.getAdditionalTip();
			fieldName = "屏幕提示信息";
			break;
		case 60:
			value = bm.getTranCode();
			fieldName = "交易码";
			break;
		case 61://String origPosJournalSeqno; //原始交易POS流水号,在签到时存放pos最大流水号
			value = bm.getOrigPosJournalSeqno();
			fieldName = "61域信息";
			break;
		case 62:
			value = bm.getWorkKeys();
			fieldName = "工作密钥";
			break;
		case 64:
			value = bm.getMac();
			fieldName = "mac值";
			break;
		}
		if (value == null) {
			logger.info("[{}]域[{}]设置值出错!值为空。", fieldNo, fieldName);
		}else{
			if("byte[]".equals(value.getClass().getSimpleName()))
				logger.info("下传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, ConvertUtils.bytes2HexStr((byte[])value) });
			else if("String".equals(value.getClass().getSimpleName()))
			logger.info("下传报文[{}]域[{}]:[{}]", new Object[] { fieldNo, fieldName, value });
			fieldValues[fieldNo] = value;
		}
		
	}

	

}