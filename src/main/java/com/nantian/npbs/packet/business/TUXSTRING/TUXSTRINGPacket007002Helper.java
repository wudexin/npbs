package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityCashData;

/**
 * 河电无IC卡缴费
 * @author qiaoxl
 *
 */
@Component
public class TUXSTRINGPacket007002Helper extends TUXSTRINGPacketxxx002Helper {

	private static Logger logger = LoggerFactory.getLogger(TUXSTRINGPacketxxx002Helper.class);
	
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ElectricityCashData cashData = (ElectricityCashData)bm.getCustomData();
		
		//缴费金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_PAY_AMT", String.valueOf(cashData.getPayAmt()));
		
		// 电费明细数据
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_REG", cashData.getReg());

		// 用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_CODE",cashData.getCode());

		// 用户名称
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_NAME", cashData.getUsername());

		// 金额总笔数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_AMT_NUM", cashData.getAmtNum());

		// 合计金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HEE_TOTAL_BILL", cashData.getTotalBill());
		
		// 接入渠道日期-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_DATE",  bm.getTranDate());

		// 接入渠道流水号-必输项
		PacketUtils.addFieldValue(fieldValues, "CHANNEL_SEQNO",  bm.getPbSeqno());
		
		
	}
	
	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
			"D13_13_HEE_THIS_BALANCE",
			"D13_13_HEE_SE_NUM",
			"D13_13_HEE_JTDJXX"
		};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		ElectricityCashData cash = (ElectricityCashData)bm.getCustomData();
	//addStart MDB 2012年1月12日 18:47:31
		//000000||20120725|130022097|000199131100_PG01_1313060|1|821.11| |0;| 
		// 本次余额
		String thisBal = (String) fieldValues.get("D13_13_HEE_THIS_BALANCE");
		if (thisBal == null || "".equals(thisBal) ){
			logger.error("解商务平台包(014002)失败： 电子商务平台起止示数解析错误!");
			throw new PacketOperationException();
		}
		
		// 起止示数
		String seNum = (String) fieldValues.get("D13_13_HEE_SE_NUM");
		if (seNum == null || "".equals(seNum) ){
			logger.error("解商务平台包(014002)失败： 电子商务平台起止示数解析错误!");
			throw new PacketOperationException();
		}
		
		
		//-------------add by wzd 2012年5月17日11:27:18 ---start
		//阶梯电价信息
		String jtdjxx = (String)fieldValues.get("D13_13_HEE_JTDJXX");
		if (jtdjxx == null || "".equals(jtdjxx) ){
			logger.error("解商务平台包(014002)失败： 电子商务平台起止示数解析错误!");
			throw new PacketOperationException();
		}
		
		String[] firstSplitStr = jtdjxx.split(";");
		
		if(firstSplitStr.length<1) {
			logger.error("电子商务平台阶梯电价数据信息解析错误！");
			throw new PacketOperationException();
		}
		
			 		
		cash.setJtdjxx(firstSplitStr);   //阶梯电价信息
		//-------------add by wzd 2012年5月17日11:27:18 ---end	
		cash.setThisBalance(thisBal);//本次余额
		
		//根据“起止示数”判断是否显示“该用户为预交、多月...”
		if(null == seNum || "".equals(seNum.trim()) || "-".equals(seNum.trim())){
			cash.setIsShowDetail(true);	
		}else{
			cash.setIsShowDetail(false);
			cash.setSeNum(seNum);
		}
		
		bm.setCustomData(cash);
	//addEnd MDB 2012年1月12日 18:47:31
	}
	
}
