package com.nantian.npbs.packet.business.WEB;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.business.ISO8583.ISO8583FieldICCardUtil;
import com.nantian.npbs.packet.business.ISO8583.ISO8583Packet018001Helper;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 农电卡查询
 * @author wdx
 *
 */
@Component
public class WEBSTRINGPacket018001Helper extends WEBSTRINGPacketxxx001Helper {
	private static Logger logger = LoggerFactory
			.getLogger(WEBSTRINGPacket018001Helper.class);
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_USER_CODE", 
				"D_CURRENCY_CODE",
				"D_web_55"};
		return fields;
	}
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//用户号
		String userCode = (String) fieldValues.get("D_USER_CODE");
		if (userCode == null) throw new PacketOperationException();
		bm.setUserCode(userCode);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		//Field 55, custom data, for IC data
		unpackFieldweb55((String) fieldValues.get("D_web_55"), bm);
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
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_web_44", packFieldweb44(bm));
		//响应信息，打印数据
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_web_55", packFieldweb55(bm));

	}
	
	//子类需要重写
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm){
		return null;
	}
	/**
	 * 解包55位元读卡信息解包
	 */
	
	protected void unpackFieldweb55(Object field, BusinessMessage bm) {

		logger.info("河北农电IC卡查询--解包55位元读卡信息解包--开始");

		// 使用HeGBElecICCard存储解包后的内容，然后将对象放入bm的customData
		HeNDElecICCard icData = new HeNDElecICCard();
		ISO8583FieldICCardUtil.unpackHeNDICCardFieldICCard(field, icData);
		bm.setCustomData(icData);
		logger.info("河北农电IC卡查询--解包55位元读卡信息解包--结束");
	}
	/**
	 * 河北农电打包44域
	 */
	
	protected String packFieldweb44(BusinessMessage bm)
			throws PacketOperationException {

		logger.info("河北农电IC卡查询--打包44域位元--开始");
		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}

		StringBuffer str = new StringBuffer();

		if (null != bm.getCustomData()
				&& bm.getCustomData() instanceof HeNDElecICCard) {
			HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

			if (null != customData.getCARD_NO()) {
				str.append("用户编号:").append(customData.getCONS_NO())
						.append("\n");
			}
			if (null != customData.getCONS_NAME()) {
				str.append("用户名称:").append(customData.getCONS_NAME()).append(
						"\n");
			}
			
			if (null != customData.getOWN_AMT()) {
				str.append("欠费金额:").append(customData.getOWN_AMT())
						.append("\n");
			}
			if (null != bm.getPbSeqno()) {
				str.append("pb流水:").append(bm.getPbSeqno()).append(
						"\n");
			}
			if (null != bm.getUserCode()) {
				str.append("电商返回用户编号:").append(bm.getUserCode()).append(
						"\n");
			}

		} else if (null != bm.getAdditionalTip()
				&& !"".equals(bm.getAdditionalTip())) {
			str.append(bm.getAdditionalTip()).append("\n");
		} else if (null != bm.getResponseMsg()
				&& !"".equals(bm.getResponseMsg())) {
			str.append(bm.getResponseMsg()).append("\n");
			
		}
		bm.setResponseMsg(str.toString());
		logger.info("打包44域完成{}", str.toString());
		return str.toString();
	}
	protected String packFieldweb55(BusinessMessage bm) {

		logger.info("河北农电IC卡查询--打包55位元--开始");
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		StringBuffer sb=new StringBuffer();
		sb.append(customData.getIF_PURP() == null?"":customData.getIF_PURP());//是否允许购电 1为允许
		sb.append(customData.getNOALLOW_MSG() == null?"                                        ":customData.getNOALLOW_MSG()) ;//不允许购电原因
		sb.append(customData.getCONS_ADDR());
		return sb.toString();
	}
}
