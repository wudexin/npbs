package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.service.answer.AnswerBusiness010Service;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 * 末笔交易查询
 * @author MDB
 *
 */
@Component
public class FIXSTRINGPacketxxx010Helper implements IPacketFIXSTRING {
	
	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness010Service.class);
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_EPOS_ORI_SEQNO"};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//EPOS原流水号
		String oriEPOSSeqNo = (String) fieldValues.get("D_EPOS_ORI_SEQNO");
		if (oriEPOSSeqNo == null) throw new PacketOperationException();
		bm.setOrigPosJournalSeqno(oriEPOSSeqNo);
		
	}
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		
		//原交易EPOS流水号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_EPOS_ORI_SEQNO", bm.getPosJournalNo());
		
		//用户号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_CODE", bm.getUserCode());
		
		//用户名称
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_NAME", bm.getUserName());
		
		//商户编号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_CODE", bm.getShop().getCompanyCode());
		
		//商户名称
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_NAME", bm.getShop().getCompanyName());
		
		//交易金额
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_AMOUNT", Double.toString(bm.getAmount()));
		
		//原交易PB流水号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_SYS_ORIGSEQNO", bm.getOldPbSeqno());
		
		//交易码（3位）
		logger.info("交易流水[{}]、交易业务码[{}]、交易类别码[{}]",
				new Object[]{bm.getOldPbSeqno(),bm.getBusinessType(),bm.getTranType()});
		if(bm.getTranType() != null){
			if("02".equals(bm.getTranType())){
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_BUSI_CODE", "999");
			}else{
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_BUSI_CODE", bm.getBusinessType());
			}
		}else{
			throw new PacketOperationException("无法判断末笔是“取消交易”或“缴费交易”");
		}
		
		//附加响应数据
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_APP_INFO_FIELD", bm.getAppInfoField());
		
	}

}
