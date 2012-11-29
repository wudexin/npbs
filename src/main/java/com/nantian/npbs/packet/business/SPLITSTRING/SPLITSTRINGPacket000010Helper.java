package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.service.request.RequestBusiness010Service;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;

/**
 * 交易状态查询
 * 
 * @author hubo
 * 
 */
@Component
public class SPLITSTRINGPacket000010Helper implements IPacketSPLITSTRING {

	private static Logger logger = LoggerFactory
	.getLogger(SPLITSTRINGPacket000010Helper.class);
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 原流水日期
		String oldTradeDate = (String) fieldValues.get("oldTradeDate");
		if (oldTradeDate == null)
			throw new PacketOperationException();
		bm.setOrigDealDate(oldTradeDate);
		
		// 商户号
		String shopCode = (String) fieldValues.get("shopCode");
		if (shopCode == null)
			throw new PacketOperationException();
		bm.setShopCode(shopCode);
		
		// 原pos流水
		String oldPosSeqno = (String) fieldValues.get("oldPosSeqno");
		if (oldPosSeqno == null)
			throw new PacketOperationException();
		bm.setOrigPosJournalSeqno(oldPosSeqno);
		
		checkPack(fieldValues,cm,bm);
	}
	
	private boolean checkPack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm){
		String[] fields = hasFields();
		for (String fieldName : fields) {
			if("".equals((String) fieldValues.get(fieldName))){
				String msg = "["+ fieldName +"]域为空" ;
				bm.setResponseCode(GlobalConst.TRADE_STATUS_FAILURE);
				bm.setResponseMsg(msg);
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg(msg);
				logger.info("[{}]域为空" ,fieldName);
				return false;
			}
		}
		return true;
		
	}

	@Override
	public String[] hasFields() {
		String[] fields = { "oldTradeDate", "shopCode", "oldPosSeqno" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		return null;
	}

}