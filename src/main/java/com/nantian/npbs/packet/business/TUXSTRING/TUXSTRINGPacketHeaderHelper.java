/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
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
public class TUXSTRINGPacketHeaderHelper implements IPacketTUXSTRING {
	private static Logger logger = LoggerFactory
			.getLogger(TUXSTRINGPacketHeaderHelper.class);

	protected FieldsConfig fieldsConfig = TUXSTRINGFieldsConfig.getInstance();
	
	@Resource
	protected BaseHibernateDao baseHibernateDao;
	
	
	
	/**
	 * 报文头打包，需要最后确认并按接口文档修改部分值
	 * 
	 * @param fieldValues
	 * @param cm
	 * @param bm
	 * @throws Exception
	 */
	public String pack(ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {

		StringBuffer buffer = new StringBuffer();
		String value = null;
		
		// 局号，作废！
		addFieldValue("H_BRCH_NO", "", buffer);

		// 柜员号，作废！
		addFieldValue("H_OPER_NO", "", buffer);

		// 交易流水号，与电子商务平台确认后，我们送空！
		if("010003".equals(bm.getTranCode())
				|| "010024".equals(bm.getTranCode())) {
			value = bm.getOrigSysJournalSeqno();
		}else if("010004".equals(bm.getTranCode())) {
			value = bm.getOldElecSeqNo();		
		}else {
			value = "";
		}
		addFieldValue("H_SEQ_NO",value, buffer);

		// 本地IP地址
		addFieldValue("H_IP_ADDR", "10.22.17.6", buffer);

		// 终端号
		addFieldValue("H_TTY", "pts_2", buffer);

		// 授权柜员号
		value = bm.getEcAuthOper();
		if (null == value) {
			value = "";
		}
		
		addFieldValue("H_AUTH_OPER_NO", value.trim(), buffer);

		// 渠道流水号
		// 根据会议纪要需求变更第十条、需要放商户号。
		addFieldValue("H_CHANNEL_TRACE", bm.getShopCode(), buffer); 

		// 渠道标识
		addFieldValue("H_CHANNEL_NO", "02", buffer);

		if("000806".equals(bm.getTranCode())){
			// 网点机构号
			addFieldValue("H_BRCH_NO_NEW", "05000004", buffer);
			// 柜员号
			addFieldValue("H_OPER_NO_NEW", "ZG01", buffer);
		}else{
			// 网点机构号
			value = bm.getEcUnitCode();
			if (null == value) {
				value = "";
			}
			 
			addFieldValue("H_BRCH_NO_NEW",value, buffer);
			// 柜员号
			value = bm.getEcOperCode();
			if (null == value) {
				value = "";
			}
			addFieldValue("H_OPER_NO_NEW", value.trim(), buffer);
		 }
		
		// 上送文件个数
		if(bm.getFileNum() == null){
			bm.setFileNum("0000");
		}
		addFieldValue("H_SFILE_NUM", bm.getFileNum(), buffer);

		return buffer.toString().substring(0, buffer.toString().length() - 1);
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

		TUXMessageHead msgHeadData = new TUXMessageHead();

		// 主机返回处理代码
		String retErr = (String) fieldValues.get("HOST_RET_ERR");
		if (retErr == null) {
			logger.error("主机返回处理代码为空！");
			throw new PacketOperationException();
		}
		msgHeadData.setRetErr(retErr);

		// 主机返回信息
		String retMsg = (String) fieldValues.get("HOST_RET_MSG");
		if (retMsg == null) {
			logger.error("主机返回信息为空！");
			// throw new PacketOperationException();
		}
		msgHeadData.setRetMsg(retMsg);

		// 系统日期
		String sysDate = (String) fieldValues.get("H_SYS_DATE");
		if (sysDate == null) {
			logger.error("系统日期为空！");
			throw new PacketOperationException();
		}
		msgHeadData.setSysDate(sysDate);

		// 交易流水号
		String journalSeqno = (String) fieldValues.get("H_SEQ_NO");
		if (journalSeqno == null) {
			logger.error("交易流水号为空！");
			throw new PacketOperationException();
		}
		msgHeadData.setJournalSeqno(journalSeqno);

		// 下送文件个数
		String rFileNum = (String) fieldValues.get("H_RFILE_NUM");
		if (rFileNum == null) {
			logger.error("下送文件个数为空！");
			throw new PacketOperationException();
		}
		int num = Integer.valueOf(rFileNum).intValue();
		msgHeadData.setrFileNum(num);

		if (num > 0) {
			// 循环取出文件名
			String[] rFileNames = new String[num];
			for (int i = 0; i < num; i++) {
				String rFileName = (String) fieldValues.get("H_RECV_FILE" + i);
				if (rFileName == null) {
					logger.error("不带路径下送文件名为空！");
				}
				rFileNames[i] = rFileName;
			}
			msgHeadData.setrFileName(rFileNames);
		}

		// 交易数据结束标志
		String rendFlag = (String) fieldValues.get("H_REND_FLAG");
		if (rendFlag == null) {
			logger.error("交易数据结束标志为空！");
			throw new PacketOperationException();
		}
		msgHeadData.setRendFlag(rendFlag);

		bm.setMsgHeadData(msgHeadData);
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
						TUXSTRINGFieldsConfig.FIELD_SPLITER);
				break;
			case VARIABLE:
				buffer.append(value + TUXSTRINGFieldsConfig.FIELD_SPLITER);
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
		String[] fields = { "HOST_RET_ERR",// 主机返回处理代码
				"HOST_RET_MSG", // 主机返回信息
				"H_SYS_DATE",// 系统日期
				"H_SEQ_NO",// 交易流水号
				"H_RFILE_NUM",// 下送文件个数
				"H_REND_FLAG"// 交易数据结束标志
		};
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			TUXSTRINGFieldsConfig fieldsConfig) {
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
