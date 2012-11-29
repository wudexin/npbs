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
 * 备付金查询
 * 
 * @author
 * 
 */
@Component
public class SPLITSTRINGPacket000801Helper implements IPacketSPLITSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		TbBiCompany shop = bm.getShop();
		if (shop == null) {
			return;
		}
		TbBiPrepay prepay = bm.getPrepay();
		if (prepay == null) {
			return;
		}

		// 商户号
		PacketUtils.addFieldValue(fieldValues, "shopCode", shop
				.getCompanyCode());

		// 商户名称
		PacketUtils.addFieldValue(fieldValues, "companyName", shop
				.getCompanyName());

		// 机构编码
		PacketUtils.addFieldValue(fieldValues, "unitCode", bm.getEcUnitCode());

		// 状态
		PacketUtils.addFieldValue(fieldValues, "state", shop.getState());

		// 商户地址
		PacketUtils.addFieldValue(fieldValues, "address", shop.getAddress());
		
		//联系人
		PacketUtils.addFieldValue(fieldValues, "contact", shop.getContact());

		// 联系电话
		PacketUtils.addFieldValue(fieldValues, "contactNum", shop
				.getContactnum());

		// 创建日期
		PacketUtils
				.addFieldValue(fieldValues, "creatDate", shop.getCreatDate());

		// 备注
		PacketUtils.addFieldValue(fieldValues, "remark", shop.getRemark());

		// 身份证号
		PacketUtils.addFieldValue(fieldValues, "auid", shop.getAuid());

		// 资金归集方式
		PacketUtils.addFieldValue(fieldValues, "payType", shop.getPayType());

		// 备付金帐号
		PacketUtils.addFieldValue(fieldValues, "resAccNo", shop.getResaccno());

		// 备付金余额
		PacketUtils.addFieldValue(fieldValues, "accBalance", String
				.valueOf(prepay.getAccBalance()));

		// 信用额度
		PacketUtils.addFieldValue(fieldValues, "creditAmt", String
				.valueOf(prepay.getCreditAmt()));

		// 已用信用额度
		PacketUtils.addFieldValue(fieldValues, "useCreAmt", String
				.valueOf(prepay.getUseCreamt()));

		// 剩余信用额度
		PacketUtils.addFieldValue(fieldValues, "surCreAmt", String
				.valueOf(prepay.getSurCreamt()));

		// 欠费日期
		PacketUtils.addFieldValue(fieldValues, "arrearsDate", prepay
				.getArrearsDate());

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// 商户号
		String shopCode = (String) fieldValues.get("shopCode");
		if (shopCode == null)
			throw new PacketOperationException();
		bm.setShopCode(shopCode);
	}

	@Override
	public String[] hasFields() {
		String[] fields = { "shopCode" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

}