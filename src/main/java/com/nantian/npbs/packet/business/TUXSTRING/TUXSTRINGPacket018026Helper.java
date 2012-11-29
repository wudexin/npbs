package com.nantian.npbs.packet.business.TUXSTRING;

import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;

/**
 * 河北农电IC卡交易取消
 * 
 * @author fyf
 * 
 */
@Component
public class TUXSTRINGPacket018026Helper extends TUXSTRINGPacketxxx024Helper {

	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacket018026Helper.class);

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HeNDElecICCard cardData = (HeNDElecICCard) bm.getCustomData();
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BM_DATE", String.valueOf(bm.getTranDate()));//便民服务站日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_BM_SEQNO", String.valueOf(bm.getPbSeqno()));//便民服务站流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_SEQNO", String.valueOf(bm.getOrigSysJournalSeqno()));//原交易系统流水号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEND_SERIAL_NUMBER", "");//原交易外部售电系统售电流水号（售电返回）不保存则上送空
		PacketUtils.addFieldValue(fieldValues,"D13_13_HEND_CHECK_ID",cardData.getCHECK_ID());//售电查询出参返回的对账批次
		PacketUtils.addFieldValue(fieldValues,"D13_13_HEND_CONS_NO",cardData.getCONS_NO());	//客户编号
		PacketUtils.addFieldValue(fieldValues,"D13_13_HEND_METER_ID",cardData.getMETER_ID());	//电能表编号
		PacketUtils.addFieldValue(fieldValues,"D13_13_HEND_METER_FLAG",cardData.getMETER_FLAG());	//电能表标识
		PacketUtils.addFieldValue(fieldValues,"D13_13_HEND_CARD_INFO",cardData.getCARD_INFO());	//卡内信息
		PacketUtils.addFieldValue(fieldValues,"D13_13_HEND_IDDATA",cardData.getIDDATA());//卡片信息
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HeNDElecICCard cash = new HeNDElecICCard();

		 //购电次数 
		String PURP_TIMES = (String) fieldValues.get("D13_13_HEND_PURP_TIMES");
		if (PURP_TIMES == null)
			throw new PacketOperationException();
		cash.setPURP_TIMES(PURP_TIMES);
	   
		 //写卡金额     
		String WRITE_VALUE = (String) fieldValues.get("D13_13_HEND_WRITE_VALUE");
		if (WRITE_VALUE == null)
			throw new PacketOperationException();
		cash.setWRITE_VALUE(WRITE_VALUE);
	    
	   
		 //写卡字符串
		String WRITE_INFO = (String) fieldValues.get("D13_13_HEND_WRITE_INFO");
		if (WRITE_INFO == null)
			throw new PacketOperationException();
		cash.setWRITE_INFO(WRITE_INFO.replace("^", "|"));
	   
		 bm.setCustomData(cash);
	   
	 
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = { "D13_13_HEND_PURP_TIMES",//购电次数              
				"D13_13_HEND_WRITE_VALUE",//写卡金额              
				"D13_13_HEND_WRITE_INFO",//写卡字符串            
		};
		return fields;
	}

}
