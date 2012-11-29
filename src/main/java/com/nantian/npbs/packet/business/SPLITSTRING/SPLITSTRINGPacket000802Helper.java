package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 备付金缴费
 * 
 * @author hubo
 * 
 */
@Component
public class SPLITSTRINGPacket000802Helper implements IPacketSPLITSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		TbBiCompany shop = bm.getShop();
		if (shop == null) {
			return;
		}
		TbBiPrepay prepay = bm.getPrepay();

		// 商户号
		PacketUtils.addFieldValue(fieldValues, "shopCode", shop
				.getCompanyCode());

		// 商户名称
		PacketUtils.addFieldValue(fieldValues, "companyName", shop
				.getCompanyName());

		// 商户地址
		PacketUtils.addFieldValue(fieldValues, "address", shop.getAddress());

		// 备付金余额
		PacketUtils.addFieldValue(fieldValues, "accBalance", String
				.valueOf(prepay.getAccBalance()));

		// 信用额度
		PacketUtils.addFieldValue(fieldValues, "creditAmt", String
				.valueOf(prepay.getCreditAmt()));

		// 已用信用额度
		PacketUtils.addFieldValue(fieldValues, "useCreAmt", String
				.valueOf(prepay.getUseCreamt()));

		// 缴费金额
		PacketUtils.addFieldValue(fieldValues, "amount", String.valueOf(bm
				.getAmount()));
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// 商户号
		String shopCode = (String) fieldValues.get("shopCode");
		if (shopCode == null)
			throw new PacketOperationException();
		bm.setShopCode(shopCode);
		// 缴费金额
		String amount = (String) fieldValues.get("amount");
		if (amount == null)
			throw new PacketOperationException();
		bm.setAmount(Double.parseDouble(amount));
	}

	@Override
	public String[] hasFields() {
		String[] fields = { "amount", "shopCode" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

}