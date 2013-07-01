package com.nantian.npbs.packet.business.WEB;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.business.ISO8583.ISO8583FieldICCardUtil;
import com.nantian.npbs.packet.business.ISO8583.ISO8583Packet018001Helper;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 农电补写卡
 * @author wdx
 *
 */
@Component
public class WEBSTRINGPacket018004Helper extends WEBSTRINGPacketxxx002Helper {
	private static Logger logger = LoggerFactory
			.getLogger(WEBSTRINGPacket018004Helper.class);
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_CONS_NO",
				"D_PB_SEQNO",
				"D_SYS_ORIGSEQNO"};
		return fields;
	}
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		
		bm.setNdzhuanyong((String) fieldValues.get("D_PB_SEQNO"));
		bm.setOldPbSeqno((String) fieldValues.get("D_SYS_ORIGSEQNO"));
		//Field 55, custom data, for IC data
		unpackFieldweb55((String) fieldValues.get("D_CONS_NO"), bm);
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		
		
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_web_55", packFieldweb55(bm));

	}
	
	//子类需要重写
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm){
		return null;
	}
	/**
	 * 解包55位元读卡信息解包
	 */
	
	
		protected void unpackFieldweb55(Object field, BusinessMessage bm) {

			logger.info("河北农电IC卡缴费--解包web55位元读卡信息解包--开始");
			HeNDElecICCard customData = new HeNDElecICCard();
				logger.info("河北农电IC卡----web55域信息解包到卡信息实体----开始");
				 String buffer = (String)field;
					customData.setCONS_NO(buffer.toString());// 客户编号
			bm.setUserCode(customData.getCONS_NO().trim());//用户编号
			bm.setCustomData(customData);
			logger.info("河北农电IC卡缴费--解包web55位元读卡信息解包--结束"); 
		}
	
	protected String packFieldweb55(BusinessMessage bm) {
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();
		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		return customData.getWRITE_INFO();
	}
}
