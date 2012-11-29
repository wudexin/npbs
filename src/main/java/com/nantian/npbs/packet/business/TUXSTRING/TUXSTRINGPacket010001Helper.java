/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityICCardData;
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 河电省标电卡查询
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket010001Helper extends TUXSTRINGPacketxxx001Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		ElectricityICCardData cardData = (ElectricityICCardData)bm.getCustomData();

		// 地区码
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DQM", " ");   // 不上传
		
		// 电表识别号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DBSBH", cardData.getCardNo());
		
		// 电卡状态
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DKZT", cardData.getCardStatus());
		
		// 购电次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GDCS", cardData.getBuyElecNum());
		
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		DecimalFormat df = new DecimalFormat("0.00");
		ElectricityICCardData customData = (ElectricityICCardData) bm.getCustomData();
		
		//用户编号
		String userCode = (String) fieldValues.get("D13_13_HESB_YHBH");
		if (userCode == null) throw new PacketOperationException();
		customData.setUserCode(userCode);
		bm.setUserCode(userCode);
		
		//用户名称
		String userName = (String) fieldValues.get("D13_13_HESB_YHMC");
		if (userName == null) throw new PacketOperationException();
		customData.setUserName(userName);
		bm.setUserName(userName);
		
		//用电地址
		String address = (String) fieldValues.get("D13_13_HESB_YDDZ");
		if (address == null) throw new PacketOperationException();
		customData.setAddress(address);
		
		// 低保户剩余金额
		String dibaofei = (String) fieldValues.get("D13_13_HESB_DBHSYJE");
		if (dibaofei == null) throw new PacketOperationException();
		customData.setDibaofei(df.format(Double.parseDouble(dibaofei)/100));
		
		// 欠费金额
		String qianfeiJE = (String) fieldValues.get("D13_13_HESB_QFJE");
		if (qianfeiJE == null) throw new PacketOperationException();
		//customData.setFee(qianfeiJE);
		customData.setFee(df.format(Double.parseDouble(qianfeiJE)/100));
		
		//上次余额
		String lastBalance = (String) fieldValues.get("D13_13_HESB_SCYE");
		if (lastBalance == null) throw new PacketOperationException();
		customData.setLastBalance(df.format(Double.parseDouble(lastBalance)/100));
		
		// 电价数量和本条记录，重复开始
		String recNum = (String) fieldValues.get("RECORD_REC");
		if (recNum == null) throw new PacketOperationException();
		customData.setRecNum(recNum);
		
		//扣减金额
		String buckleAmt = (String)fieldValues.get("D13_13_HESB_KJJE");
		if(buckleAmt == null) throw new PacketOperationException();
		customData.setBuckleAmt(Double.valueOf(buckleAmt));
		
		int num = Integer.parseInt(recNum);
		
		if(num>0){
			String[] elecName = new String[num];
			String[] prices = new String[num];
			for(int i =0;i<num;i++){
				//电价名称
				String name = (String) fieldValues.get("D13_13_HESB_DJMC"+i);
				if (name == null) throw new PacketOperationException();
				elecName[i] = name;
				
				// 电价
				String price = (String) fieldValues.get("D13_13_HESB_DJ"+i);
				if (price == null) throw new PacketOperationException();
				prices[i] = price;
				
			}
			
			customData.setElecName(elecName[0]);
			customData.setPrice(df.format(Double.parseDouble(prices[0])/100));
		}
		
		bm.setCustomData(customData);
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HESB_YHBH",    				// 用户编号
				"D13_13_HESB_YHMC",    				// 用户名称
				"D13_13_HESB_YDDZ",    				// 用电地址
				"D13_13_HESB_HSDWBH",    			// 核算单位编号
				"D13_13_HESB_DBHBZ",   				// 低保户标志
				"D13_13_HESB_DBHSYJE",    			// 低保户剩余金额
				"D13_13_HESB_HBQDL",    			// 换表欠电量
				"D13_13_HESB_TZDL",    				// 调整电量
				"D13_13_HESB_QFJE",    				// 欠费金额
				"D13_13_HESB_SCYE",    				// 上次余额
				"D13_13_HESB_KJJE",     			// 扣减金额
				"D13_13_HESB_GDQX",    				// 购电权限
				"D13_13_HESB_SCJCJDL",    			// 上次尖抄见电量
				"D13_13_HESB_SCFCJDL",    			// 上次峰抄见电量
				"D13_13_HESB_SCPCJDL",    			// 上次平抄见电量
				"D13_13_HESB_SCGCJDL",    			// 上次谷抄见电量
				"RECORD_REC"  						// 电价数量
		};
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		String[] addFields = new String[count*2];
		String fieldName = null;
		Field f = null;
		int j = 0;
		for(int i = 0 ; i <count ; i++){

			//电价名称
			fieldName = "D13_13_HESB_DJMC" + i;
			addFields[j] = "D13_13_HESB_DJMC" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 0, 4,0,30, "D13_13_HESB_DJMC");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			//电价
			fieldName = "D13_13_HESB_DJ" + i;
			addFields[j] = "D13_13_HESB_DJ" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE, 0,4,0,16, "D13_13_HESB_DJ");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			
		}
		
		String[] fieldsHelp = new String[fields.length + addFields.length];
		System.arraycopy(fields, 0, fieldsHelp, 0, fields.length);
		System.arraycopy(addFields, 0, fieldsHelp, fields.length, addFields.length);
		return fieldsHelp;
	}
	
}
