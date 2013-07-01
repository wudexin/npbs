package com.nantian.npbs.packet.business.WEB;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.FieldsConfig;

/**
 * 报文头打包、解包帮助类
 * @author jxw
 *
 */
@Scope("prototype")
@Component
public class WEBSTRINGPacketHeaderHelper {
	private static Logger logger = 
				LoggerFactory.getLogger(WEBSTRINGPacketHeaderHelper.class);
	
	protected FieldsConfig fieldsConfig = WEBSTRINGFieldsConfig.getInstance();
	
	public String[] hasFields() {
		String[] fields = {
				"H_PACKET_LENGTH", 
				"H_APPLICATION_TYPE", 
				"H_TERMINAL_STATE", 
				"H_HANDLE_TYPE",
				"D_TRAN_CODE",
				"D_SHOP_CODE",
				"D_EPOS_SEQNO",
				"D_TERMINAL_ID",
				"D_TRAN_TIME",
				"D_TRAN_DATE",
				"D_PB_SEQNO",
				"D_RESPONSE_CODE",
				"D_RET_DATA"};
		return fields;
	}
	
	/**
	 * 报文头解包
	 * @param fieldValues
	 * @param cm
	 * @param bm
	 * @throws PacketOperationException
	 */
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		WEBMessageHead msgHeadData = new WEBMessageHead();
		
		//报文长度
		String packetLength = (String) fieldValues.get("H_PACKET_LENGTH");
		if (packetLength == null) throw new PacketOperationException();
		msgHeadData.setPacketLength(Integer.parseInt(packetLength));
		
		//应用类别定义
		String applicationType = (String) fieldValues.get("H_APPLICATION_TYPE");
		if (applicationType == null) throw new PacketOperationException();
		msgHeadData.setApplicationType(applicationType);
		
		//终端状态
		String terminalState = (String) fieldValues.get("H_TERMINAL_STATE");
		if (terminalState == null) throw new PacketOperationException();
		msgHeadData.setTerminalState(terminalState);
		
		//处理要求
		String handleType = (String) fieldValues.get("H_HANDLE_TYPE");
		if (handleType == null) throw new PacketOperationException();
		msgHeadData.setHandleType(handleType);
		
		//交易码
		String tranCode = (String) fieldValues.get("D_TRAN_CODE");
		if (tranCode == null) throw new PacketOperationException();
		msgHeadData.setTranCode(tranCode);
		cm.setTranCode(tranCode);
		bm.setTranCode(tranCode);
		
		//商户编号
		String shopCode = (String) fieldValues.get("D_SHOP_CODE");
		shopCode = shopCode.toUpperCase();
		if (shopCode == null) throw new PacketOperationException();
		msgHeadData.setShopcode(shopCode);
		bm.setShopCode(shopCode);
		
		//EPOS流水号
		String eposSeqno = (String) fieldValues.get("D_EPOS_SEQNO");
		if (eposSeqno == null) throw new PacketOperationException();
		msgHeadData.setEposSeqno(eposSeqno);
		bm.setPosJournalNo(eposSeqno);
		
		//终端编号
		String terminalId = (String) fieldValues.get("D_TERMINAL_ID");
		if (terminalId == null) throw new PacketOperationException();
		msgHeadData.setTerminalId(terminalId);
		bm.setTerminalId(terminalId);
		
		//交易时间
		String dealTime = (String) fieldValues.get("D_TRAN_TIME");
		if (dealTime == null) throw new PacketOperationException();
		msgHeadData.setTranTime(dealTime);
		logger.info("dealTime[{}]",dealTime);
		
		//交易日期
		String dealDate = (String) fieldValues.get("D_TRAN_DATE");
		if (dealDate == null) throw new PacketOperationException();
		msgHeadData.setTranDate(dealDate);
		logger.info("dealDate[{}]",dealDate);
		
		//PB流水
		String pbSerial = (String) fieldValues.get("D_PB_SEQNO");
		if (pbSerial == null) throw new PacketOperationException();
		bm.setOldPbSeqno(pbSerial);
		logger.info("pbSerial[{}]",pbSerial);
		
		//应答码
		String respCode = (String) fieldValues.get("D_RESPONSE_CODE");
		if (respCode == null) throw new PacketOperationException();
		logger.info("respCode[{}]",respCode);
		
		//返回数据
		String respData = (String) fieldValues.get("D_RET_DATA");
		if (respData == null) throw new PacketOperationException();
		logger.info("respData[{}]",respData);
		
		bm.setWebMsgHeadData(msgHeadData);
	}
	/**
	 * 报文头打包
	 * @param fieldValues
	 * @param cm
	 * @param bm
	 * @throws Exception 
	 */
	public void pack(Map<String, Object> fieldValues, ControlMessage cm, BusinessMessage bm) throws PacketOperationException {
		
		WEBMessageHead msgheadData = (WEBMessageHead)bm.getWebMsgHeadData();
		if(null == msgheadData) return ;
		
		//报文长度，初始设置000000
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "H_PACKET_LENGTH", "000000");
		
		//应用类别定义
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "H_APPLICATION_TYPE", msgheadData.getApplicationType());
		
		//终端状态
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "H_TERMINAL_STATE", msgheadData.getTerminalState());
		
		//处理要求
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "H_HANDLE_TYPE", msgheadData.getHandleType());
		
		//交易码
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_CODE", msgheadData.getTranCode());
		
		//商户号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_SHOP_CODE", bm.getShopCode());
		
		//EPOS流水号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_EPOS_SEQNO", bm.getPosJournalNo());
		
		//终端编号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TERMINAL_ID", bm.getTerminalId());

		//交易时间
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_TIME", bm.getLocalTime());
		
		//交易日期
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_TRAN_DATE", bm.getTranDate());
		
		//PB流水号
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_PB_SEQNO", bm.getPbSeqno());
		
		//应答码
		WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_RESPONSE_CODE", bm.getResponseCode());
		
		//返回响应信息(向终端返回长度不能超过60)
		if(null != bm.getResponseMsg() && bm.getResponseMsg().length() >= 100){
			WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_RET_DATA", bm.getResponseMsg().substring(0,100));
		}else{
			WEBSTRINGPacketUtils.addFieldValue(fieldValues, "D_RET_DATA", bm.getResponseMsg());
		}
		
	}
	
	
}
