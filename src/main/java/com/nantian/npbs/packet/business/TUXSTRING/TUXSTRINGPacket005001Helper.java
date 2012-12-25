package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.HDCashData;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 邯郸燃气查询
 * @author jxw
 *
 */
@Component
public class TUXSTRINGPacket005001Helper extends TUXSTRINGPacketxxx001Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HDG_USERCODE", bm.getUserCode());

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		HDCashData hdCashData = new HDCashData();

		// 用户编号
		String userCode = (String) fieldValues.get("D13_13_HDG_USERCODE");
		if (userCode == null) throw new PacketOperationException();
		hdCashData.setUserCode(userCode);
		bm.setUserCode(userCode);

		//D13_13_HDG_USERNAME	  	用户名称
		String userName = (String) fieldValues.get("D13_13_HDG_USERNAME");
		if (userName == null) throw new PacketOperationException();
		hdCashData.setUserName(userName);
		bm.setUserName(userName);
		
		//D13_13_HDG_TYPENO	     用户类型
		String userType = (String) fieldValues.get("D13_13_HDG_TYPENO");
		if (userType == null) throw new PacketOperationException();
		hdCashData.setUserType(userType);
		
		//D13_13_HDG_USERADDR	  	用户地址
		String userAddr = (String) fieldValues.get("D13_13_HDG_USERADDR");
		if (userAddr == null) throw new PacketOperationException();
		hdCashData.setUserAddr(userAddr);
		
		//D13_13_HDG_LASTAMT	  	上次结余
		String lastAmt = (String) fieldValues.get("D13_13_HDG_LASTAMT");
		if (lastAmt == null) throw new PacketOperationException();
		hdCashData.setLastAmt(lastAmt);
		
		//D13_13_HDG_TOTALREC	   记录总数
		String totalRec = (String) fieldValues.get("D13_13_HDG_TOTALREC");
		if (totalRec == null) throw new PacketOperationException();
		hdCashData.setTotalRec(totalRec);
		
		//D13_13_HDG_CHARGEID_1		第一笔缴费ID
		String chargeId_1 = (String) fieldValues.get("D13_13_HDG_CHARGEID_1");
		if (chargeId_1 == null) throw new PacketOperationException();
		hdCashData.setChargeId_1(chargeId_1);
		
		//D13_13_HDG_OUGHTAMT_1		第一笔应缴金额
		String oughtAmt_1 = (String) fieldValues.get("D13_13_HDG_OUGHTAMT_1");
		if (oughtAmt_1 == null) throw new PacketOperationException();
		hdCashData.setOughtAmt_1(oughtAmt_1);
		
		//D13_13_HDG_TOTALOUGHT		应交合计
		String totalought = (String) fieldValues.get("D13_13_HDG_TOTALOUGHT");
		if (totalought == null) throw new PacketOperationException();
		hdCashData.setTotalought(totalought);
		
		// 重复记录数
		String recNum = (String) fieldValues.get("B13_RECORD_REC1");
		if (recNum == null) throw new PacketOperationException();
		hdCashData.setRecordRec(recNum);
		
		int num = Integer.parseInt(recNum);
		if(num>0){
			//D13_13_HDG_CHARGEID
			String[] chargeIds = new String[num];
			//D13_13_HDG_MONTH
			String[] months = new String[num];
			//D13_13_HDG_GASCOUNT
			String[] gasCounts = new String[num];
			//D13_13_HDG_TOTALAMT
			String[] totalAmts = new String[num];
			//D13_13_HDG_LATEAMT
			String[] lateAmts = new String[num];
			//D13_13_HDG_OUGHTAMT
			String[] oughtAmts = new String[num];
			for(int i =0;i<num;i++){
				// 抄表日期
				String chargeId = (String) fieldValues.get("D13_13_HDG_CHARGEID"+i);
				if (chargeId == null) throw new PacketOperationException();
				chargeIds[i] = chargeId;
				
				//D13_13_HDG_MONTH
				String month = (String) fieldValues.get("D13_13_HDG_MONTH"+i);
				if (month == null) throw new PacketOperationException();
				months[i] = month;
				
				//D13_13_HDG_GASCOUNT
				String gasCount = (String) fieldValues.get("D13_13_HDG_GASCOUNT"+i);
				if (gasCount == null) throw new PacketOperationException();
				gasCounts[i] = gasCount;
				
				//D13_13_HDG_TOTALAMT
				String totalAmt = (String) fieldValues.get("D13_13_HDG_TOTALAMT"+i);
				if (totalAmt == null) throw new PacketOperationException();
				totalAmts[i] = totalAmt;
				
				//D13_13_HDG_LATEAMT
				String lateAmt = (String) fieldValues.get("D13_13_HDG_LATEAMT"+i);
				if (lateAmt == null) throw new PacketOperationException();
				lateAmts[i] = lateAmt;
				
				//D13_13_HDG_OUGHTAMT
				String oughtAmt = (String) fieldValues.get("D13_13_HDG_OUGHTAMT"+i);
				if (oughtAmt == null) throw new PacketOperationException();
				oughtAmts[i] = oughtAmt;
			}
			hdCashData.setChargeId(chargeIds);
			hdCashData.setMonth(months);
			hdCashData.setGasCount(gasCounts);
			hdCashData.setTotalAmt(totalAmts);
			hdCashData.setLateAmt(lateAmts);
			hdCashData.setOughtAmt(oughtAmts);
		}
		
		bm.setCustomData(hdCashData);
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HDG_USERCODE",
				"D13_13_HDG_USERNAME",
				"D13_13_HDG_TYPENO",
				"D13_13_HDG_USERADDR",
				"D13_13_HDG_LASTAMT",
				"D13_13_HDG_TOTALREC",
				"D13_13_HDG_CHARGEID_1",
				"D13_13_HDG_OUGHTAMT_1",
				"D13_13_HDG_TOTALOUGHT",
				"B13_RECORD_REC1"
				};
		return fields;
	}
	
	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		String[] addFields = new String[count*6];
		String[] otherFields = {"D13_13_HDG_CHARGEID",
				"D13_13_HDG_MONTH",
				"D13_13_HDG_GASCOUNT",
				"D13_13_HDG_TOTALAMT",
				"D13_13_HDG_LATEAMT",
				"D13_13_HDG_OUGHTAMT"};
		int[] otherLen = {20,10,5,10,10,10};
		String fieldName = null;
		Field f = null;
		int j = 0;
		for(int i = 0 ; i <count ; i++){
			for(int k = 0; k < 6 ; k++){
				fieldName = otherFields[k] + i;
				addFields[j] = fieldName;
				f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, otherLen[k], fieldName);
				fieldsConfig.addFieldConfig(fieldName, f);
				j++;
			}
		}
		String[] fieldsHelp = new String[fields.length + addFields.length];
		System.arraycopy(fields, 0, fieldsHelp, 0, fields.length);
		System.arraycopy(addFields, 0, fieldsHelp, fields.length, addFields.length);
		return fieldsHelp;
	}
	
}
