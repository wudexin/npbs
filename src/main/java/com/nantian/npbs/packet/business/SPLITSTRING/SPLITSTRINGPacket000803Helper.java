package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 备付金续费撤销
 * 
 * @author hubo
 * 
 */
@Component
public class SPLITSTRINGPacket000803Helper implements IPacketSPLITSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		TbBiPrepayInfo tbBiPrepayInfo = (TbBiPrepayInfo) bm.getCustomData();
		if (null == tbBiPrepayInfo) {
			return;
		}
		TbBiPrepay tbBiPrepay = bm.getPrepay();
		if(null == tbBiPrepay){
			return;
		}

		// 撤销金额
		PacketUtils.addFieldValue(fieldValues, "amount", String
				.valueOf(tbBiPrepayInfo.getAmount()));
		// 商户号
		PacketUtils.addFieldValue(fieldValues, "shopCode", tbBiPrepayInfo
				.getCompanyCode());
		// 商户名
		PacketUtils.addFieldValue(fieldValues, "companyName", tbBiPrepayInfo
				.getCustomername());
		// 帐户余额
		PacketUtils.addFieldValue(fieldValues, "accBalance", String
				.valueOf(tbBiPrepay.getAccBalance()));
		// 信用额度
		PacketUtils.addFieldValue(fieldValues, "creditAmt", String
				.valueOf(tbBiPrepay.getCreditAmt()));
		// 已使用额度
		PacketUtils.addFieldValue(fieldValues, "useCreAmt", String
				.valueOf(tbBiPrepay.getUseCreamt()));
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// 原流水号
		String oldPbSeqno = (String) fieldValues.get("oldPbSeqno");
		if (oldPbSeqno == null)
			throw new PacketOperationException();
		bm.setOldPbSeqno(oldPbSeqno);

	}

	@Override
	public String[] hasFields() {
		String[] fields = { "oldPbSeqno" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}