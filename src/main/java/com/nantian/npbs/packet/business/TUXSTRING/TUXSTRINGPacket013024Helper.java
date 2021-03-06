package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.HuaElecICCard;

/**
 * 华电IC卡写卡取消
 * @author MDB
 *
 */
@Component
public class TUXSTRINGPacket013024Helper extends TUXSTRINGPacketxxx024Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		HuaElecICCard sc = (HuaElecICCard)bm.getCustomData();
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_CUSTOMERNO", sc.getUserCode());
		
		// 电表编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_METERNO", sc.getAmmeterCode());

		// 卡序列号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_CARDSEQNO",sc.getCardSeqNo());

		// 卡分散数据
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_CARDMSG", sc.getCardMsg());

		// 随机数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_ROMNO", sc.getRomNo());
		
		// 平台流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_SEQNO", bm.getOrigPbSeqno());
		
		// 返回购电次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_REBUYTIMES", String.valueOf(Integer.parseInt(sc.getBuyElecNum())));
		
		// 参数信息文件
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_PARATYPE", sc.getParaType());

		// 便民日期
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_BM_DATE", bm.getTranDate());
		
		// 便民流水
		PacketUtils.addFieldValue(fieldValues, "D13_13_HBGB_BM_SEQNO", bm.getPbSeqno());
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		HuaElecICCard sc  = new HuaElecICCard();
		
		// 用户编号
		String userCode = (String) fieldValues.get("D13_13_HBGB_CUSTOMERNO");
		if (userCode == null) throw new PacketOperationException();
		sc.setUserCode(userCode);
		
		// 电表编号
		String ammeterCode = (String) fieldValues.get("D13_13_HBGB_METERNO");
		if (ammeterCode == null) throw new PacketOperationException();
		sc.setAmmeterCode(ammeterCode);
		
		// 认证数据一
		String authdata1 = (String) fieldValues.get("D13_13_HBGB_AUTHDATA1");
		if (authdata1 == null) throw new PacketOperationException();
		sc.setAuthdata1(authdata1);

		// 认证数据二
		String authdata2 = (String) fieldValues.get("D13_13_HBGB_AUTHDATA2");
		if (authdata2 == null) throw new PacketOperationException();
		sc.setAuthdata2(authdata2);
		
		// 认证数据三
		String authdata3 = (String) fieldValues.get("D13_13_HBGB_AUTHDATA3");
		if (authdata3 == null) throw new PacketOperationException();
		sc.setAuthdata3(authdata3);
		
		// 写卡数据
		String writeParam = (String) fieldValues.get("D13_13_HBGB_WRITEPARAM");
		if (writeParam == null) throw new PacketOperationException();
		sc.setWriteParam(writeParam);
		
		// 账户余额
		String accountBalance = (String) fieldValues.get("D13_13_HBGB_USERACCOUNT");
		if (accountBalance == null) throw new PacketOperationException();
		sc.setAccountBalance(accountBalance);
		
		// 购电次数
		String buyElecNum = (String) fieldValues.get("D13_13_HBGB_REBUYTIMES");
		if (buyElecNum == null) throw new PacketOperationException();
		sc.setBuyElecNum(buyElecNum);
		
		bm.setCustomData(sc);
		
	}

	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HBGB_CUSTOMERNO",   				// 用户编号
				"D13_13_HBGB_METERNO",     					// 电表编号
				"D13_13_HBGB_AUTHDATA1",    			    // 认证数据一
				"D13_13_HBGB_AUTHDATA2",    			    // 认证数据二
				"D13_13_HBGB_AUTHDATA3",    			    // 认证数据三
				"D13_13_HBGB_USERACCOUNT",    				// 账户余额
				"D13_13_HBGB_REBUYTIMES",    			    // 返回购电次数
				"D13_13_HBGB_WRITEPARAM",    			    // 写卡数据
		};
		return fields;
	}
	
}
