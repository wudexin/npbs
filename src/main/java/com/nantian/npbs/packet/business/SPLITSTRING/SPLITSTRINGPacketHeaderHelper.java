/**
 * 
 */
package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.FieldsConfig;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 报文头打包、解包帮助类
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class SPLITSTRINGPacketHeaderHelper implements IPacketSPLITSTRING {
	private static Logger logger = LoggerFactory
			.getLogger(SPLITSTRINGPacketHeaderHelper.class);

	protected FieldsConfig fieldsConfig = SPLITSTRINGFieldsConfig.getInstance();

	/**
	 * 报文头打包
	 * 
	 * @param fieldValues
	 * @param cm
	 * @param bm
	 * @throws Exception
	 */
	public String pack(ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {

		SPLITSTRINGMessageHead splitHeadData = (SPLITSTRINGMessageHead) bm.getSplitsHeadData();
		StringBuffer buffer = new StringBuffer();

		if (splitHeadData == null) {
			// 响应码
			addFieldValue("retCode", "000001", buffer);

			// 响应信息
			addFieldValue("retMsg", "打包报文错误!", buffer);
			return buffer.toString();
		}

		// 响应码
		addFieldValue("retCode", cm.getResultCode(), buffer);

		// 响应信息
		addFieldValue("retMsg", cm.getResultMsg(), buffer);

		// 交易码
		addFieldValue("tranCode", cm.getTranCode(), buffer);

		if ("000806".equals(bm.getTranCode())) {
			// 对账日期
			addFieldValue("thirdDate", bm.getBatDate(), buffer);

			// 便民服务站交易日期
			addFieldValue("pbDate", bm.getTranDate(), buffer);

			// 
			addFieldValue("operCode", "", buffer);

			// 
			addFieldValue("unitCode", "", buffer);

			// 
			addFieldValue("thirdSerial", "", buffer);

			// 便民服务站流水号
			addFieldValue("pbSerial", bm.getPbSeqno(), buffer);
		} else {
			// 电子商务平台交易日期
			addFieldValue("thirdDate", splitHeadData.getThirdDate(), buffer);

			// 便民服务站交易日期
			addFieldValue("pbDate", bm.getTranDate(), buffer);

			// 柜员号
			addFieldValue("operCode", bm.getEcOperCode(), buffer);

			// 机构号
			addFieldValue("unitCode", bm.getEcUnitCode(), buffer);

			// 电子商务平台流水号
			addFieldValue("thirdSerial", splitHeadData.getThirdSerial(), buffer);

			// 便民服务站流水号
			addFieldValue("pbSerial", bm.getPbSeqno(), buffer);
		}

		return buffer.toString();

	}

	/**
	 * 报文头解包
	 * 
	 * @param fieldValues
	 * @param cm
	 * @param bm
	 * @throws PacketOperationException
	 */
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub

		SPLITSTRINGMessageHead splitHeadData = new SPLITSTRINGMessageHead();

		// 交易码
		String tranCode = (String) fieldValues.get("tranCode");
		if (tranCode == null) {
			logger.error("交易码为空！");
			throw new PacketOperationException();
		}
		splitHeadData.setTranCode(tranCode);

		// 交易请求日期
		String requstDate = (String) fieldValues.get("thirdDate");
		if (requstDate == null) {
			logger.error("交易请求日期为空！");
			throw new PacketOperationException();
		}
		if ("000806".equals(splitHeadData.getTranCode())) {
			bm.setBatDate(requstDate);
			bm.setSplitsHeadData(splitHeadData);
			return;
		} else {
			splitHeadData.setThirdDate(requstDate);
			bm.setMidPlatformDate(requstDate);
		}

		// 电子商务平台流水号
		String thirdSerial = (String) fieldValues.get("thirdSerial");
		if (thirdSerial == null) {
			logger.error("电子商务平台流水号为空！");
			throw new PacketOperationException();
		}
		splitHeadData.setThirdSerial(thirdSerial);
		bm.setSysJournalSeqno(thirdSerial);

		// 渠道号
		String channelCode = (String) fieldValues.get("channelCode");
		if (channelCode == null) {
			logger.error("渠道号为空！");
			throw new PacketOperationException();
		}
		splitHeadData.setChannelCode(channelCode);

		// 柜员号
		String operCode = (String) fieldValues.get("operCode");
		if (operCode == null) {
			logger.error("柜员号为空！");
			throw new PacketOperationException();
		}
		splitHeadData.setOperCode(operCode);

		// 机构号
		String unitCode = (String) fieldValues.get("unitCode");
		if (unitCode == null) {
			logger.error("机构号为空！");
			throw new PacketOperationException();
		}
		splitHeadData.setUnitCode(unitCode);

		bm.setSplitsHeadData(splitHeadData);
	}

	/**
	 * 加域值通用方法
	 * 
	 * @param fieldValues
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	private void addFieldValue(String fieldName, String value,
			StringBuffer buffer) {
		if (null == value)
			value = "";
		int length;
		try {
			if (fieldsConfig.getFieldVariableType(fieldName) != VariableType.ASCII) {
				logger.warn("不支持的变量类型:"
						+ fieldsConfig.getFieldVariableType(fieldName));
			}
			switch (fieldsConfig.getFieldLengthType(fieldName)) {
			case FIXED:
				length = fieldsConfig.getFieldAsciiLength(fieldName);
				FieldUtils.setFixedLengthAsciiField(fieldName, buffer, length,
						value, fieldsConfig);
				FieldUtils.setFixedLengthAsciiFieldSpliter(buffer,
						SPLITSTRINGFieldsConfig.FIELD_SPLITER);
				break;
			case VARIABLE:
				buffer.append(value + SPLITSTRINGFieldsConfig.FIELD_SPLITER);
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 报文头名称数组
	 * 
	 * @return
	 * @throws PacketOperationException
	 */
	public String[] getFieldHeaderNames(int length) {
		// TODO
		String[] fieldsAdd = new String[length];
		for (int i = 0; i < length; i++) {
			fieldsAdd[i] = "H_RECV_FILE" + i;// 不带路径下送文件名
		}
		return fieldsAdd;
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] hasFields() {
		String[] fields = { "tranCode",// 交易码
				"thirdDate", // 交易日期
				"thirdSerial",// 电子商务平台流水号
				"channelCode",// 渠道号
				"operCode",// 柜员号
				"unitCode"// 机构号
		};
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		String[] addFields = new String[count];
		String fieldName = null;
		for (int i = 0; i < count; i++) {
			fieldName = "H_RECV_FILE" + i;
			addFields[i] = "H_RECV_FILE" + i;// 不带路径下送文件名
			Field f = new Field(fieldName, VariableType.ASCII,
					LengthType.VARIABLE, 0, 4, 0, 4, "H_RECV_FILE");
			fieldsConfig.addFieldConfig(fieldName, f);
		}
		String[] fieldsHelp = new String[fields.length + addFields.length];
		System.arraycopy(fields, 0, fieldsHelp, 0, 5);
		System.arraycopy(addFields, 0, fieldsHelp, 5, count);
		System.arraycopy(fields, 5, fieldsHelp, 5 + addFields.length, 1);
		return fieldsHelp;
	}

}
