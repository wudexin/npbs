package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.WaterCashData;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 现金代收张家口水费（欠费查询）
 * @author hubo
 *
 */
@Component
public class TUXSTRINGPacket006001Helper extends TUXSTRINGPacketxxx001Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CODE", bm.getUserCode());
		
		// 收费月份
		//收费月份一般为000000代表全部欠费
		//缴费同样上送
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_MONTH", "000000");
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		WaterCashData waterCashData = new WaterCashData();

		// 用户名称
		String username = (String) fieldValues.get("D13_13_ZJKW_USER_NAME");
		if (username == null) throw new PacketOperationException();
		waterCashData.setUsername(username);
		bm.setUserName(username);
		// 用户地址
		String userAddr = (String) fieldValues.get("D13_13_ZJKW_USER_ADDR");
		if (userAddr == null) throw new PacketOperationException();
		waterCashData.setUserAddr(userAddr);
		// 应缴金额
		String sumFee = (String) fieldValues.get("D13_13_ZJKW_SUM_FEE");
		if (sumFee == null) throw new PacketOperationException();
		waterCashData.setSumFee(sumFee);
		// 欠费月数数和本条记录，重复开始
		String recNum = (String) fieldValues.get("D13_13_ZJKW_REC_NUM");
		if (recNum == null) throw new PacketOperationException();
		waterCashData.setRecNum(recNum);
		
		int num = Integer.parseInt(recNum);
		
		if(num>0){
			String[] copyDates = new String[num];
			String[] startDatas = new String[num];
			String[] endDatas = new String[num];
			String[] usedNums = new String[num];
			String[] monFees = new String[num];
			String[] feeFlags = new String[num];
			for(int i =0;i<num;i++){
				// 抄表日期
				String copyDate = (String) fieldValues.get("D13_13_ZJKW_COPY_DATE"+i);
				if (copyDate == null) throw new PacketOperationException();
				copyDates[i] = copyDate;
				// 起码
				String startData = (String) fieldValues.get("D13_13_ZJKW_STAR_DATA"+i);
				if (startData == null) throw new PacketOperationException();
				startDatas[i] = startData;
				
				// 止码
				String endData = (String) fieldValues.get("D13_13_ZJKW_END_DATA"+i);
				if (endData == null) throw new PacketOperationException();
				endDatas[i] = endData;
				
				// 使用吨数
				String usedNum = (String) fieldValues.get("D13_13_ZJKW_USED_NUM"+i);
				if (usedNum == null) throw new PacketOperationException();
				usedNums[i] = usedNum;
				
				// 本月欠费
				String monFee = (String) fieldValues.get("D13_13_ZJKW_MON_FEE"+i);
				if (monFee == null) throw new PacketOperationException();
				monFees[i] = monFee;
				
				// 缴费标识
				String feeFlag = (String) fieldValues.get("D13_13_ZJKW_FEE_FLAG"+i);
				if (feeFlag == null) throw new PacketOperationException();
				feeFlags[i] = feeFlag;
				
			}
			waterCashData.setCopyDate(copyDates);
			waterCashData.setStartData(startDatas);
			waterCashData.setEndData(endDatas);
			waterCashData.setUsedNum(usedNums);
			waterCashData.setMonFee(monFees);
			waterCashData.setFeeFlag(feeFlags);
		}
		
		bm.setCustomData(waterCashData);
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_ZJKW_USER_NAME",
				"D13_13_ZJKW_USER_ADDR",
				"D13_13_ZJKW_SUM_FEE",
				"D13_13_ZJKW_REC_NUM"
				};
		return fields;
	}
	
	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		String[] addFields = new String[count*6];
		String fieldName = null;
		Field f = null;
		int j = 0;
		for(int i = 0 ; i <count ; i++){
			fieldName = "D13_13_ZJKW_COPY_DATE" + i;
			addFields[j] = "D13_13_ZJKW_COPY_DATE" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 14, "D13_13_ZJKW_COPY_DATE");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			fieldName = "D13_13_ZJKW_STAR_DATA" + i;
			addFields[j] = "D13_13_ZJKW_STAR_DATA" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 14, "D13_13_ZJKW_STAR_DATA");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			
			fieldName = "D13_13_ZJKW_END_DATA" + i;
			addFields[j] = "D13_13_ZJKW_END_DATA" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 14, "D13_13_ZJKW_END_DATA");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			
			fieldName = "D13_13_ZJKW_USED_NUM" + i;
			addFields[j] = "D13_13_ZJKW_USED_NUM" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 4, "D13_13_ZJKW_USED_NUM");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			
			fieldName = "D13_13_ZJKW_MON_FEE" + i;
			addFields[j] = "D13_13_ZJKW_MON_FEE" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 16, "D13_13_ZJKW_MON_FEE");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			
			fieldName = "D13_13_ZJKW_FEE_FLAG" + i;
			addFields[j] = "D13_13_ZJKW_FEE_FLAG" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 4, "D13_13_ZJKW_FEE_FLAG");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
		}
		String[] fieldsHelp = new String[fields.length + addFields.length];
		System.arraycopy(fields, 0, fieldsHelp, 0, fields.length);
		System.arraycopy(addFields, 0, fieldsHelp, fields.length, addFields.length);
//		System.arraycopy(fields, 5, fieldsHelp, 5 + addFields.length, 1);
		return fieldsHelp;
	}
	
}
