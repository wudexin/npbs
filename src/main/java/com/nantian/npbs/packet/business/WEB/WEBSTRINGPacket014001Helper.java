package com.nantian.npbs.packet.business.WEB;

import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.business.FIXSTRING.FIXSTRINGPacket014001Helper;
import com.nantian.npbs.packet.internal.HuaElecCash;
import com.nantian.npbs.packet.internal.MobileData;

/**
 * 华电现金查询
 * @author MDB
 *
 */
@Component
public class WEBSTRINGPacket014001Helper extends WEBSTRINGPacketxxx001Helper{

	private static Logger logger = LoggerFactory
			.getLogger(FIXSTRINGPacket014001Helper.class);
	
	@Override
	protected String getAddtionalTip(ControlMessage cm, BusinessMessage bm) {
			
		if(!GlobalConst.RESPONSECODE_SUCCESS.equals(bm.getResponseCode())){
			return bm.getResponseMsg();
		}
		if( null == bm.getCustomData()){
			return "查询异常,返回数据为空!";
		}
		
		HuaElecCash cash ;
		try {
			cash = (HuaElecCash)bm.getCustomData();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常, bm.getCustomData() 程序处理错误");
			return "查询异常,便民程序处理错误!";
		}
		
		StringBuffer str = new StringBuffer();
		str.append("用户编号:").append(cash.getUserCode()).append("\n");
		str.append("用户名称:").append(cash.getUserName()).append("\n");
		str.append("账户余额:").append(new DecimalFormat("0.00").format(-cash.getAccBalance())).append("\n");
		str.append("阶梯欠费:").append(cash.getLevAmt()).append("\n");
		
		return str.toString();
	}
}
