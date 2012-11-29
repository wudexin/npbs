package com.nantian.npbs.packet.business.ISO8583;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.utils.TransferUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 用户交易明细查询
 * @author jxw
 *
 */
public class ISO8583Packetxxx008Helper implements IPacketISO8583{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx008Helper.class);
	
	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		logger.info("开始解包到BusinessMessage, chanleType:{}",cm.getChanelType());
		
		// field 11 pbSeqno
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);
		
		//field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);
		
		//field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);
		
		//field 49, currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);

		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		//field 61, orig data, for queryStartDate data queryEndDate
		String queryDate = (String)fieldValues[61];
		if(queryDate == null) throw new PacketOperationException();
		bm.setQueryStartDate(queryDate.substring(0, 8));
		bm.setQueryEndDate(queryDate.substring(8));
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}

	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		int fieldNo = 0;
		
		//field 11, pos journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);
		
		//field 12, local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);
		
		//field 13, local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);
		
		//field 37, pb journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);
	
		//field 39, response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);
		
		//field 41, terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);
		
		//field 42, shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);
		
		//field 44, additional response data
//	    add by fengyafang 2012-03-21 start
//		fieldNo = 44;
//		fieldValues[fieldNo] =
		packField44(bm);
//		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		ISO8583PacketUtils.packField(fieldValues,44,bm);
//		add by fengyafang 2012-03-21 end
		
		//field 49, currency code
		ISO8583PacketUtils.packField(fieldValues,49,bm);
		
		//field 60, tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//field 64, mac
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}
	
	//TODO: complete pack field 44
	@SuppressWarnings("unchecked")
	private String packField44(BusinessMessage bm) {
		StringBuffer stringBuffer = new StringBuffer();
		ResourceBundle  rb = TransferUtils.getTransferResources();		
		ArrayList<Object> res = bm.getJournalList();
		if(null!= res && res.size()>0) {
			ArrayList<Object[]> tranRes = (ArrayList) res.get(0);// 交易统计
			//add by fengyafang 20120830 与孟主任沟通。不需要备付金的金额。
			//ArrayList<Object[]> preRes = (ArrayList) res.get(1);// 备付金统计
			BigDecimal countsum=new BigDecimal("0");
			BigDecimal amsum=new BigDecimal("0");
			for (Object[] o : tranRes) {
				String companyCode = (String) o[0];// 商户号
				String busiCode = (String) o[1];// 业务类型
				BigDecimal pbSerialCount = (BigDecimal) o[2];// 交易笔数
				BigDecimal amountSum = (BigDecimal) o[3];// 交易总金额
				logger.info("商户号[" + companyCode + "]业务类型[" + busiCode + "]交易笔数["
						+ pbSerialCount + "]交易金额[" + amountSum + "]");
				stringBuffer.append("业务类型:"+rb.getString("busicode."+busiCode))
						.append("\n交易笔数:"+pbSerialCount)
							.append("\n交易金额:"+amountSum+"\n");
				countsum=countsum.add(pbSerialCount);
				amsum=amsum.add(amountSum);
			}
			stringBuffer.append("交易总笔数:"+countsum+"\n");
			stringBuffer.append("交易总金额:"+amsum+"\n");
			
		/*
		 * //add by fengyafang 20120830 与孟主任沟通。不需要备付金的金额。
			for (Object[] o : preRes) {
				String accNo = (String) o[0];// 帐户号
				BigDecimal prepayPbCount = (BigDecimal) o[1];// 备付金交易笔数
				BigDecimal prepayAmountSum = (BigDecimal) o[2];// 备付金交易总金额
				logger.info("备付金帐号[" + accNo + "]备付金交易笔数[" + prepayPbCount
						+ "]备付金交易总金额[" + prepayAmountSum + "]");
				stringBuffer.append("备付金帐号:"+accNo)
					.append("\n备付金交易笔数:"+prepayPbCount)
						.append("\n备付金交易总金额:"+prepayAmountSum+"\n");
			}*/ 
			logger.info("-------"+stringBuffer.toString());
		 	//add by fengyafang 2012-03-21 start  
		 	bm.setResponseMsg(stringBuffer.toString());
		}else{
			stringBuffer.append(bm.getResponseMsg());
		}	
		//add by fengyafang 2012-03-21 end   
		return stringBuffer.toString();
	}
	
}
