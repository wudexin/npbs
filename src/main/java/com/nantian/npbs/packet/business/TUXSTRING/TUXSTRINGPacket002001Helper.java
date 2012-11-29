package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.UnitcomCashData;

/**
 * 联通手机
 * 
 * @author hubo
 * 
 */
@Component
public class TUXSTRINGPacket002001Helper extends TUXSTRINGPacketxxx001Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONETYPE", "G");
		PacketUtils.addFieldValue(fieldValues, "D13_12_UNC_PHONENUM", bm
				.getUserCode());

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
	UnitcomCashData cashData = new UnitcomCashData();
		
		//用户号码
		String phoneNum = (String)fieldValues.get("D13_12_UNC_PHONENUM");
		if(phoneNum == null) throw new PacketOperationException();
		cashData.setPhoneNum(phoneNum);
		bm.setUserCode(phoneNum);
		//用户姓名
		String userName = (String)fieldValues.get("D13_12_UNC_USERNAME");
		if(userName == null) throw new PacketOperationException();
		cashData.setUserName(userName);
		bm.setUserName(userName);
		
		//局编账号
		String accno = (String)fieldValues.get("D13_12_UNC_ACCNO");
		if(accno == null) throw new PacketOperationException();
		cashData.setAccno(accno);
		
		//地市代码
		String areano = (String)fieldValues.get("D13_12_UNC_AREANO");
		if(areano == null) throw new PacketOperationException();
		cashData.setAreano(areano);
		
		//欠费起始年月
		String sMonth = (String)fieldValues.get("D13_12_UNC_SMONTH");
		if(sMonth == null) throw new PacketOperationException();
		cashData.setsMonth(sMonth);
		
		//往月欠费总额
		String sumAmt = (String)fieldValues.get("D13_12_UNC_SUMAMT");
		if(sumAmt == null) throw new PacketOperationException();
		cashData.setSumAmt(sumAmt);
		
		//上次余额
		String lastAmt = (String)fieldValues.get("D13_12_UNC_LASTAMT");
		if(lastAmt == null) throw new PacketOperationException();
		cashData.setLastAmt(lastAmt);
		
		//未出账话费
		String thisAmt = (String)fieldValues.get("D13_12_UNC_THISAMT");
		if(thisAmt == null) throw new PacketOperationException();
		cashData.setThisAmt(thisAmt);
		
		//滞纳金总额
		String lateAmt = (String)fieldValues.get("D13_12_UNC_LATEAMT");
		if(lateAmt == null) throw new PacketOperationException();
		cashData.setLateAmt(lateAmt);
		
		//本期应缴
		String oughtAmt = (String)fieldValues.get("D13_12_UNC_OUGHTAMT");
		if(oughtAmt == null) throw new PacketOperationException();
		cashData.setOughtAmt(oughtAmt);	
		
		bm.setCustomData(cashData);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		 
		String[] fields = { "D13_12_UNC_PHONENUM",// 用户号码
				"D13_12_UNC_USERNAME",// 用户姓名
				"D13_12_UNC_ACCNO", // 局编账号
				"D13_12_UNC_AREANO", // 地市代码
				"D13_12_UNC_SMONTH", // 欠费起始年月
				"D13_12_UNC_SUMAMT", // 往月欠费总额
				"D13_12_UNC_LASTAMT", // 上次余额
				"D13_12_UNC_THISAMT", // 未出账话费
				"D13_12_UNC_LATEAMT", // 滞纳金总额
				"D13_12_UNC_OUGHTAMT",// 本期应缴
		};
		return fields;
	}

}
