package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiTradeBusi;
import com.nantian.npbs.business.model.TbBiTradeBusiId;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 备付金密码重置
 * 
 * @author HuBo
 * 
 */
@Component
public class SPLITSTRINGPacket000805Helper implements IPacketSPLITSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		TbBiCompany shop = bm.getShop();
		if (null == shop) {
			bm.setResponseMsg("商户信息有误！");
			return;
		}

		TbBiTradeBusi tbBiTradeBusi = (TbBiTradeBusi) bm.getCustomData();

		if (null == tbBiTradeBusi) {
			bm.setResponseMsg("商户信息不正确！");
			return;
		}

		PacketUtils.addFieldValue(fieldValues, "shopCode", String.valueOf(bm
				.getShopCode()));// 商户号	
		PacketUtils.addFieldValue(fieldValues, "contact", String.valueOf(shop
				.getContact()));// 商户负责人姓名
		PacketUtils.addFieldValue(fieldValues, "auid", String.valueOf(shop
				.getAuid()));// 负责人身份证号码
		PacketUtils.addFieldValue(fieldValues, "agent", String
				.valueOf(tbBiTradeBusi.getAgent()));// 代办人姓名
		PacketUtils.addFieldValue(fieldValues, "agentauid", String
				.valueOf(tbBiTradeBusi.getAgentauid()));// 代办人身份证号码
		PacketUtils.addFieldValue(fieldValues, "companyName", String.valueOf(shop
				.getCompanyName()));//商户名称

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		TbBiTradeBusi tbBiTradeBusi = new TbBiTradeBusi();

		// 查询类型
		String queryType = (String) fieldValues.get("queryType");
		if (queryType == null)
			throw new PacketOperationException();
		bm.setQueryType(queryType);
		// 商户号
		String shopCode = (String) fieldValues.get("shopCode");
		if (shopCode == null)
			throw new PacketOperationException();
		bm.setShopCode(shopCode);
		
		tbBiTradeBusi.setCompanyCode(shopCode);
		
		// 商户负责人姓名
		String contact = (String) fieldValues.get("contact");
		// if (contact == null)
		// throw new PacketOperationException();
		tbBiTradeBusi.setContact(contact);
		// 负责人身份证号码
		String auid = (String) fieldValues.get("auid");
		// if (auid == null)
		// throw new PacketOperationException();
		tbBiTradeBusi.setAuid(auid);
		// 代办人姓名
		String agent = (String) fieldValues.get("agent");
		// if (agent == null)
		// throw new PacketOperationException();
		tbBiTradeBusi.setAgent(agent);
		// 代办人身份证号码
		String agentauid = (String) fieldValues.get("agentauid");
		// if (agentauid == null)
		// throw new PacketOperationException();
		tbBiTradeBusi.setAgentauid(agentauid);
		/** 孟主任：电子商务平台备付金密码重置交易，不用输入新密码，直接重置为初始密码，修改日期20111129 */
		
		tbBiTradeBusi.setTradeTime(bm.getTranTime());
		bm.setCustomData(tbBiTradeBusi);
	}

	@Override
	public String[] hasFields() {
		String[] fields = { "queryType", "shopCode", "contact", "auid",
				"agent", "agentauid" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}