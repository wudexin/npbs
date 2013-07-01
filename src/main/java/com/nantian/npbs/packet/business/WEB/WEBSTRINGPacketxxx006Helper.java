package com.nantian.npbs.packet.business.WEB;

import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.TransferUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 *  用户交易明细查询
 * @author jxw
 *
 */
@Component
public class WEBSTRINGPacketxxx006Helper implements IPacketWEBSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		
		packField44(bm);
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_QUERY_DATA", bm.getResponseMsg());
	}

	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_QUERY_DATA", 
				"D_CURRENCY_CODE", 
				"D_ORIG_DEAL_DATE"};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//查询数据
		String queryData = (String) fieldValues.get("D_QUERY_DATA");
		if (queryData == null) throw new PacketOperationException();
		//bm.setQueryData(queryData);	
		bm.setPackageNum(queryData);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//原系统riqi
		String origdate = (String) fieldValues.get("D_ORIG_DEAL_DATE");
		if (origdate == null) throw new PacketOperationException();
		bm.setOrigLocalDate(origdate);
	}
	private String packField44(BusinessMessage bm) {
		StringBuffer stringBuffer = new StringBuffer();
		ArrayList<Object[]> tbList = (ArrayList) bm.getJournalList();
		ResourceBundle rb = TransferUtils.getTransferResources();
		int i = 1;
		String num = null;
		String flag = bm.getPackageFlag();
		if (null != tbList) {
			for (Object[] tb : tbList) {
				stringBuffer.append(rb.getString("busicode." + tb[2])).append(
						"|" + tb[9]).append("|" + tb[10]).append("|" + tb[12]).append("|" + tb[4]).append("\n");
				
				if (i == 6 && flag.equals("1")) {
					stringBuffer.append("061\n");
					break;
				}
				if (i == tbList.size() && flag.equals("0")) {
					/*if (i < 6) {
						num = "0" + i;
						stringBuffer.append(num + "0\n");
						break;
					} else {
						num = i + "";
						stringBuffer.append(num + "0\n");
						break;
					}*/
					num = "0" + i;
					stringBuffer.append(num + "0\n");
				}
				i++;
			}
			
		}
		
	 	//add by fengyafang 2012-03-21 start  
	 	bm.setResponseMsg(stringBuffer.toString());
		//add by fengyafang 2012-03-21 end   
		return stringBuffer.toString();
	}

}
