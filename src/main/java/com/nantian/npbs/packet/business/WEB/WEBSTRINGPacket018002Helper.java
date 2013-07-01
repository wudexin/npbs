package com.nantian.npbs.packet.business.WEB;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.business.ISO8583.ISO8583FieldICCardUtil;
import com.nantian.npbs.packet.business.ISO8583.ISO8583Packet018001Helper;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 农电卡缴费
 * @author wdx
 *
 */
@Component
public class WEBSTRINGPacket018002Helper extends WEBSTRINGPacketxxx002Helper {
	private static Logger logger = LoggerFactory
			.getLogger(WEBSTRINGPacket018002Helper.class);
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_PREPAY_PWD", 
				"D_FEE_TYPE", 
				"D_PAY_TYPE", 
				"D_CURRENCY_CODE" ,
				"D_AMOUNT", 
				"D_USER_CODE", 
				"D_USER_NAME",
				"D_CONS_NO",
				"D_PB_SEQNO"};
		return fields;
	}
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//备付金密码
		String prePayPwd = (String) fieldValues.get("D_PREPAY_PWD");
		if (prePayPwd == null) throw new PacketOperationException();
		bm.setShopPINData((ConvertUtils.str2Bcd(prePayPwd)));
		
		//欠款，预存款标识
		String feeType = (String) fieldValues.get("D_FEE_TYPE");
		if (feeType == null) throw new PacketOperationException();
		bm.setFeeType(feeType);
		
		//资金归集方式
		String payType = (String) fieldValues.get("D_PAY_TYPE");
		if (payType == null) throw new PacketOperationException();
		bm.setPayType(payType);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//交易金额
		String amount = (String) fieldValues.get("D_AMOUNT");
		if (amount == null) throw new PacketOperationException();
		bm.setAmount(Integer.parseInt((String)amount) / 100.00);
		
		//用户号
		String userCode = (String) fieldValues.get("D_USER_CODE");
		if (userCode == null) throw new PacketOperationException();
		bm.setUserCode(userCode);
		
		//用户名称
		String userName = (String) fieldValues.get("D_USER_NAME");
		if (userName == null) throw new PacketOperationException();
		bm.setUserName(userName);
		
		bm.setOldPbSeqno((String) fieldValues.get("D_PB_SEQNO"));
		//Field 55, custom data, for IC data
		unpackFieldweb55((String) fieldValues.get("D_CONS_NO"), bm);
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//用户号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_CODE", bm.getUserCode());
		
		//用户名称
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_NAME", bm.getUserName());
		
		//商户编号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_CODE", bm.getShop().getCompanyCode());
		
		//商户名称
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_NAME", bm.getShop().getCompanyName());
		
		//货币代码
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//交易金额
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_AMOUNT", Double.toString(bm.getAmount()));
		
		//欠款，预存款标识
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_FEE_TYPE", bm.getFeeType());
		
		//资金归集方式
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_PAY_TYPE", bm.getShop().getFundType());
		
		//附加响应数据
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_ADDITIONAL_TIP", getAddtionalTip(cm,bm));
		
		//响应信息，打印数据
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_web_55", packFieldweb55(bm));
		//响应信息，打印数据
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_web_44", packFieldweb44(bm));

	}
	
	//子类需要重写
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm){
		return null;
	}
	/**
	 * 解包55位元读卡信息解包
	 */
	
	
		protected void unpackFieldweb55(Object field, BusinessMessage bm) {

			logger.info("河北农电IC卡缴费--解包web55位元读卡信息解包--开始");
			HeNDElecICCard customData = new HeNDElecICCard();
				logger.info("河北农电IC卡----web55域信息解包到卡信息实体----开始");
				 String buffer = (String)field;
					customData.setCONS_NO(buffer.toString());// 客户编号
			bm.setUserCode(customData.getCONS_NO().trim());//用户编号
			bm.setCustomData(customData);
			logger.info("河北农电IC卡缴费--解包web55位元读卡信息解包--结束"); 
		}
	/**
	 * 河北农电打包44域
	 */
	
	protected String packFieldweb44(BusinessMessage bm)
			throws PacketOperationException {

		logger.info("开始打包44位元");
		HeNDElecICCard cashData = (HeNDElecICCard) bm.getCustomData();
		if (null == cashData) {
			bm.setResponseMsg("系统错误,请联系管理员!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			return bm.getResponseMsg();
		}
		StringBuffer str = new StringBuffer();
		if (!("".equals(cashData.getLADDER_DIFF()) || cashData.getLADDER_DIFF() == null))
			str.append("阶梯差价:").append(cashData.getLADDER_DIFF()).append("\n");
		if (!("".equals(cashData.getANNUAL_VALUE()) || cashData
				.getANNUAL_VALUE() == null))
			str.append("本年累计用电量:").append(cashData.getANNUAL_VALUE())
					.append("\n");
		if (!("".equals(cashData.getLADDER_SURPLUS()) || cashData
				.getLADDER_SURPLUS() == null))
			str.append(cashData.getLADDER_SURPLUS());
		if (str.toString().length() > 1) {
			bm.setResponseMsg(str.toString());
			return str.toString();
		} else {
			return bm.getResponseMsg();
		}
				
			
	}
	protected String packFieldweb55(BusinessMessage bm) {
		logger.info("河北农电IC卡缴费--打包55位元--开始");
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		} 
	 
		logger.info("河北农电IC卡缴费--打包55位元--结束");
	 
		return customData.getWRITE_INFO();
	}
}
