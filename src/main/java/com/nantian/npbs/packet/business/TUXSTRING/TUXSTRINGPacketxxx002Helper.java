/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * @author TsaiYee
 *
 */
@Component
public class TUXSTRINGPacketxxx002Helper implements IPacketTUXSTRING {

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#pack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ElectricityICCardData cardData = (ElectricityICCardData)bm.getCustomData();
		
		//用户编号
		addFieldValue(fieldValues, "D13_13_HESB_YHBH", cardData.getUserCode());
		
		//用户名称
		addFieldValue(fieldValues, "D13_13_HESB_YHMC", cardData.getCustomerName());
		
		//用电地址
		addFieldValue(fieldValues, "D13_13_HESB_YDDZ", cardData.getAddress());
		
		//电价名称
		addFieldValue(fieldValues, "D13_13_HESB_DJMC", cardData.getElecName());
		
		//电价
		addFieldValue(fieldValues, "D13_13_HESB_DJ", String.valueOf(cardData.getPrice()));
		
		//上次余额
		addFieldValue(fieldValues, "D13_13_HESB_SCYE",  String.valueOf(cardData.getLastBalance()));
		
		//低保户剩余金额
		addFieldValue(fieldValues, "D13_13_HESB_DBHSYJE", cardData.getDibaofei());
		
		//电表识别号
		addFieldValue(fieldValues, "D13_13_HESB_DBSBH", cardData.getCardNo());
		
		//卡序列号
		addFieldValue(fieldValues, "D13_13_HESB_YHKFSYZ", cardData.getCardNo());
		
		//随机数
		addFieldValue(fieldValues, "D13_13_HESB_SJS", cardData.getRandomNum());
		
		//购电方式
		addFieldValue(fieldValues, "D13_13_HESB_GDFS", cardData.getBuyElecMode());
		
		//购电值
		addFieldValue(fieldValues, "D13_13_HESB_GDZ", cardData.getCurElectric());
		
		//购电次数
		addFieldValue(fieldValues, "D13_13_HESB_GDCS", cardData.getBuyElecNum());
		
		//电卡总购电字
		addFieldValue(fieldValues, "D13_13_HESB_DKZGDZ", cardData.getBuyElecTotal());
		
		//剩余电字
		addFieldValue(fieldValues, "D13_13_HESB_SYDZ", cardData.getRemainElec());
		
		//过零电字
		addFieldValue(fieldValues, "D13_13_HESB_GLDZ", cardData.getZeroElec());
		
		//总用电字
		addFieldValue(fieldValues, "D13_13_HESB_ZYDZ", cardData.getAllUseElec());
		
		//电卡类型
		addFieldValue(fieldValues, "D13_13_HESB_DKLX", cardData.getElecType());
		
		//尖用电字数
		addFieldValue(fieldValues, "D13_13_HESB_JYDZS", cardData.getJianydzs());
		
		//峰用电字数
		addFieldValue(fieldValues, "D13_13_HESB_FYDZS", cardData.getFengydzs());
		
		//谷用电字数
		addFieldValue(fieldValues, "D13_13_HESB_GYDZS", cardData.getGuydzs());
		
		//平用电字数
		addFieldValue(fieldValues, "D13_13_HESB_PYDZS", cardData.getPingydzs());
		
		//回写时间
		addFieldValue(fieldValues, "D13_13_HESB_HXSJ", cardData.getWritebtime());
		
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#unpack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		
		ElectricityICCardData customData = new ElectricityICCardData();
		
		//外部认证数据
		String outAuthData = (String) fieldValues.get("D13_13_HESB_WBRZSJ");
		if (outAuthData == null) throw new PacketOperationException();
		customData.setOutAuthData(outAuthData);
		
		//购电值
		String curElectric = (String) fieldValues.get("D13_13_HESB_GDZ");
		if (curElectric == null) throw new PacketOperationException();
		customData.setCurElectric(curElectric);
		
		//原交易流水号
		String origSysJournalSeqno = (String) fieldValues.get("D13_13_HESB_WBRZSJ");
		if (origSysJournalSeqno == null) throw new PacketOperationException();
		bm.setOrigSysJournalSeqno(origSysJournalSeqno);
		bm.setCustomData(customData);
	}

	@Override
	public String[] hasFields() {
		String[] fields = {"D13_13_HESB_WBRZSJ", "D13_13_HESB_GDZ", "D13_13_HESB_YJYLSH"};
		return fields;
	}
	
	private void addFieldValue(Map<String, Object> fieldValues, String fieldName, String value) {
		if(null == value) value = "";
		fieldValues.put(fieldName, value.trim());
		
	}

	@Override
	public String[] addFields(String[] fields, int count, TUXSTRINGFieldsConfig fieldsConfig) {
		// TODO Auto-generated method stub
		return null;
	}

}
