package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityCashData;

/**
 * 华电河电无IC卡查询
 * @author 7tianle
 *
 */
@Component
public class TUXSTRINGPacket007001Helper extends TUXSTRINGPacketxxx001Helper {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		//TODU  需要确认查询条件和电费年月
		// 查询条件
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_QUERY", "0");
		
		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CODE", bm.getUserCode());
		
		// 电费年月
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_MONTH", "");
		
	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub
		
		ElectricityCashData cashData = new ElectricityCashData();

		// 用户名称
		String username = (String) fieldValues.get("D13_13_HEE_NAME");
		if (username == null) throw new PacketOperationException();
		cashData.setUsername(username);
		bm.setUserName(username); //返回pos
		
		// 用电地址
		String address = (String) fieldValues.get("D13_13_HEE_ADDR");
		if (address == null) throw new PacketOperationException();
		cashData.setAddress(address);
		
		// 合计金额
		String totalBill = (String) fieldValues.get("D13_13_HEE_TOTAL_BILL");
		if (totalBill == null) throw new PacketOperationException();
		cashData.setTotalBill(totalBill);
		if(Double.parseDouble(totalBill)>0){  //返回pos
			bm.setFeeType(GlobalConst.FEE_TYPE_OWE);
		}else{
			bm.setFeeType(GlobalConst.FEE_TYPE_STORED);
		}
		
		//合计电费
		String totalAmt = (String) fieldValues.get("D13_13_HEE_TOTAL_AMT");
		if (totalAmt == null) throw new PacketOperationException();
		cashData.setTotalAmt(totalAmt);
		
		// 合计违约金
		String penBill = (String) fieldValues.get("D13_13_HEE_PEN_BILL");
		if (penBill == null) throw new PacketOperationException();
		cashData.setPenBill(penBill);

		// 合计预收
		String preAmt = (String) fieldValues.get("D13_13_HEE_PR_AMT");
		if (preAmt == null) throw new PacketOperationException();
		cashData.setPreAmt(preAmt);

		// 金额总笔数
		String amtNum = (String) fieldValues.get("D13_13_HEE_AMT_NUM");
		if (amtNum == null) throw new PacketOperationException();
		cashData.setAmtNum(amtNum);

		// 电费明细数据
		String reg = (String) fieldValues.get("D13_13_HEE_REG");
		if (reg == null) reg="";
		cashData.setReg(reg);
		
		bm.setCustomData(cashData);
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HEE_NAME",   			// 用户名称
				"D13_13_HEE_ADDR",     			// 用电地址
				"D13_13_HEE_TOTAL_BILL",    // 合计金额
				"D13_13_HEE_TOTAL_AMT",    // 合计电费
				"D13_13_HEE_PEN_BILL",    	 // 合计违约金
				"D13_13_HEE_PR_AMT",     		// 合计预收
				"D13_13_HEE_AMT_NUM",    	 // 金额总笔数
				"D13_13_HEE_REG" 				    // 电费明细数据
				};
		return fields;
	}
	
}
