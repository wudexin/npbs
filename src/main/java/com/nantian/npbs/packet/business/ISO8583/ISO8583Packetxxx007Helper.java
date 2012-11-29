package com.nantian.npbs.packet.business.ISO8583;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bea.common.ldap.DateUtil;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.common.utils.TransferUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 用户交易明细查询
 * 
 * @author jxw
 * 
 */
public class ISO8583Packetxxx007Helper implements IPacketISO8583 {

	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx007Helper.class);

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());

		// field 11 pbSeqno
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);

		// field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);

		// field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);

		// field 49, currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);

		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		// field 61, orig data, for pbSeqno
		ISO8583PacketUtils.unpackField(fieldValues,61,bm,true);
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);
	}
	
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		int fieldNo = 0;

		// field 11, pos journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);

		// field 12, local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);

		// field 13, local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);

		// field 37, pb journal no
		ISO8583PacketUtils.packField(fieldValues,37,bm);

		// field 39, response code
		ISO8583PacketUtils.packField(fieldValues,39,bm);

		// field 41, terminal id
		ISO8583PacketUtils.packField(fieldValues,41,bm);

		// field 42, shop code
		ISO8583PacketUtils.packField(fieldValues,42,bm);

		// field 44, additional response data
//		add by fengyafang 2012-03-21 start
//		fieldNo = 44;
//		fieldValues[fieldNo] = 
		packField44(bm);
//		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
		ISO8583PacketUtils.packField(fieldValues,44,bm);
//		add by fengyafang 2012-03-21 end
		
		// field 49, currency code
		ISO8583PacketUtils.packField(fieldValues,49,bm);

		// field 60, tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//field 64, mac
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}

	// TODO: complete pack field 44
	@SuppressWarnings("unchecked")
	private String packField44(BusinessMessage bm) {
		StringBuffer stringBuffer = new StringBuffer();
		ArrayList<TbBiTrade> tbList = (ArrayList) bm.getJournalList();
		ResourceBundle rb = TransferUtils.getTransferResources();
		if (null != tbList) {
			for (TbBiTrade tb : tbList) {
				stringBuffer.append("业务类型："
					+ rb.getString("busicode." + tb.getBusiCode())).append(
								"\n用户名称：" + tb.getCustomername()).append(
								"\n用户号码：" + tb.getCustomerno()).append(
								"\n中心流水：" + tb.getId().getPbSerial()).append(
								"\n交易金额(元)：" + tb.getAmount()).append(
								"\n交易时间：" + tb.getTradeTime());
			}
			logger.info(stringBuffer.toString());
		} else {
			stringBuffer.append("无此交易流水！");
		}
	 	//add by fengyafang 2012-03-21 start  
	 	bm.setResponseMsg(stringBuffer.toString());
		//add by fengyafang 2012-03-21 end   
		return stringBuffer.toString();
	}

	
}
