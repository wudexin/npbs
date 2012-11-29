package com.nantian.npbs.packet.business.ISO8583;

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
 * 用户交易流水列表查询
 * 
 * @author jxw
 * 
 */
public class ISO8583Packetxxx006Helper implements IPacketISO8583 {

	private static final Logger logger = LoggerFactory
			.getLogger(ISO8583Packetxxx006Helper.class);

	@Override
	public void unpack(Object[] fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		logger.info("开始解包到BusinessMessage, chanleType:{}" , cm.getChanelType());


		// field 11, system trace number (pos journal no)
		ISO8583PacketUtils.unpackField(fieldValues,11,bm,true);

		// field 32, user code
		ISO8583PacketUtils.unpackField(fieldValues,32,bm,true);

		// field 41, terminalId
		ISO8583PacketUtils.unpackField(fieldValues,41,bm,true);

		// field 42, shop code (for pos)
		ISO8583PacketUtils.unpackField(fieldValues,42,bm,true);

		// field 49, currency code
		ISO8583PacketUtils.unpackField(fieldValues,49,bm,true);
		
		// field 60 tranCode
		ISO8583PacketUtils.unpackField(fieldValues,60,bm,true);
		
		// field 61, orig data, for dateAndPkgNum
		String dateAndPkgNum = (String) fieldValues[61];
		if (dateAndPkgNum == null)
			throw new PacketOperationException();
		bm.setOrigLocalDate(dateAndPkgNum.substring(0, 8));
		bm.setPackageNum(dateAndPkgNum.substring(8));
		
		//field 64, mac
		ISO8583PacketUtils.unpackField(fieldValues,64,bm,true);

	}
	
	
	@Override
	public void pack(Object[] fieldValues, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		int fieldNo = 0;

		//不需要绿卡信息
		/*// field 2, main account
		ISO8583PacketUtils.packField(fieldValues,2,bm);*/

		// field 11, pos journal no
		ISO8583PacketUtils.packField(fieldValues,11,bm);

		// field 12, local time
		ISO8583PacketUtils.packField(fieldValues,12,bm);

		// field 13, local date
		ISO8583PacketUtils.packField(fieldValues,13,bm);

		// feild 32, user code
		ISO8583PacketUtils.packField(fieldValues,32,bm);

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
		ISO8583PacketUtils.packField(fieldValues,44,bm);
//		FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
//		add by fengyafang 2012

		// field 49, currency code
		ISO8583PacketUtils.packField(fieldValues,49,bm);

		// field 60, tran code
		ISO8583PacketUtils.packField(fieldValues,60,bm);
		
		//field 64, mac
		ISO8583PacketUtils.packField(fieldValues,64,bm);
	}

	// complete pack field 44
	@SuppressWarnings("unchecked")
	private String packField44(BusinessMessage bm) {
		StringBuffer stringBuffer = new StringBuffer();
		ArrayList<Object[]> tbList = (ArrayList) bm.getJournalList();
		ResourceBundle rb = TransferUtils.getTransferResources();
		int i = 0;
		String num = null;
		String flag = bm.getPackageFlag();
		if (null != tbList) {
			for (Object[] tb : tbList) {
				stringBuffer.append(rb.getString("busicode." + tb[2])).append(
						"|" + tb[8]).append("|" + tb[0]).append("\n");
				
				if (i == 11 && flag.equals("1")) {
					stringBuffer.append("121\n");
					break;
				}
				if (i == tbList.size() && flag.equals("0")) {
					if (i < 10) {
						num = "0" + i;
						stringBuffer.append(num + "0\n");
						break;
					} else {
						num = i + "";
						stringBuffer.append(num + "0\n");
						break;
					}
				}
				i++;
			}
			logger.info(stringBuffer.toString());
		}
		
	 	//add by fengyafang 2012-03-21 start  
	 	bm.setResponseMsg(stringBuffer.toString());
		//add by fengyafang 2012-03-21 end   
		return stringBuffer.toString();
	}

}
