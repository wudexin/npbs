package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PsamCompanyDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiTrade;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.FieldType.VariableType;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.FieldsConfig;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * EPOS报文打包、解包处理帮助类
 * 
 * @author jxw
 * 
 */
@Scope("prototype")
@Component
public class FIXSTRINGPacketHelper implements IPacket {

	private static Logger logger = LoggerFactory
			.getLogger(FIXSTRINGPacketHelper.class);
	protected Map<String, Object> fieldValues = new LinkedHashMap<String, Object>();
	protected FieldsConfig fieldsConfig = FIXSTRINGFieldsConfig.getInstance();
	private int offset = 0;

	@Resource
	public FIXSTRINGPacketHeaderHelper headerHelper;

	@Resource
	public PsamCompanyDao psamCompanyDao;

	@Resource
	EncryptionService encryptionService;

	@Resource
	public TradeDao tradeDao;
	
	private ControlMessage cm;
	private BusinessMessage bm;

	// 报文体
	protected String buffer;

	@Override
	public Map<DATA_TYPE, Object> unpackObject(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {
		logger.info("开始进行FIXSTRING解包....");
		offset = 0;

		cm = PacketUtils.getControlMessage(message);
		bm = PacketUtils.getBusinessMessage(message);
		bm.setSystemChanelCode(GlobalConst.eposChanelCode);
		buffer = (String) PacketUtils.getOrigReqPacket(message);

		logger.info("需要解包的报文内容:[{}]", buffer);

		fieldValues.clear();
		String[] fields = headerHelper.hasFields();
		unpackFIXSTRING(fields, buffer);
		headerHelper.unpack(fieldValues, cm, bm);
		String tranCode = cm.getTranCode();
		logger.info("交易{}解包包头完成.", tranCode);
		offset = 151;

		// pack body
		IPacketFIXSTRING packetHelper;
		try {
			packetHelper = (IPacketFIXSTRING) SpringContextHolder
					.getBean(getPacketBeanName(cm.getTranCode()));

			fields = packetHelper.hasFields();
			fieldValues.clear();
			unpackFIXSTRING(fields, buffer);
			packetHelper.unpack(fieldValues, cm, bm);

			
			String shopCode = null;
			FIXMessageHead  fixMsgHeadData = (FIXMessageHead)bm.getFixMsgHeadData();
			if(bm.getTranCode().equals("000010")&& fixMsgHeadData.getHandleType().equals("7")) {
				bm.setMacFlag(false);
				//如果是web端交易同步调取的末笔交易查询则执行
				TbBiTrade tbBiTrade =  tradeDao.getTradeById(fixMsgHeadData.getTranDate(), bm.getOldPbSeqno());
				if(tbBiTrade != null) {
					shopCode = tbBiTrade.getCompanyCode();
				}
			}else {
				shopCode = psamCompanyDao.findPsamCompanyRef(bm.getPSAMCardNo());
			}
			
			
			if(shopCode == null){
				logger.error("psam卡对应商户号不存在!");
				throw new PacketOperationException("psam卡对应商户号不存在!");
			}
			bm.setShopCode(shopCode);

			if (!checkMac(bm)) {
				throw new PacketOperationException("报文解包失败!报文格式错误");
			}

		} catch (Exception e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}
		logger.info("交易{}解包包体完成.", tranCode);
		logger.info("交易{}FIXSTRING报文解包完成....", tranCode);
		return message;
	}

	// 校验MAC
	private boolean checkMac(BusinessMessage bm) throws Exception {

		// 查询是否需要校验MAC
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle("conf.checkFlag");
			bm.setMacFlag(Boolean.valueOf(rb.getString(bm.getChanelType()+"checkMacFlag")));
			FIXMessageHead  fixMsgHeadData = (FIXMessageHead)bm.getFixMsgHeadData();
			if("000010".equals(bm.getTranCode()) && "7".equals(fixMsgHeadData.getHandleType())) {
				bm.setMacFlag(false);
			}
		}catch(Exception e){
			logger.error("取mac校验标志错!",e);
			//默认为true

			bm.setMacFlag(true);
		}
		logger.info("mac校验标志:[{}],交易码:[{}]!", bm.isMacFlag(), bm.getTranCode());
		if (bm.isMacFlag() == false || "000903".equals(bm.getTranCode())) {
			return true;
		}

		// 无论是否校验MAC，都取出MAB
		// 从报文头（除长度位字段）+报文体（除MAC字段）
		// （MAB），采用ECB算法，加密结果为64位的MAC。
		byte[] macData = getMacData();
		bm.setMacData(macData);

		// MAC
		String fieldName = "D_EPOS_MAC";
		int length = 0;
		String macstr = null;
		try {
			length = fieldsConfig.getFieldAsciiLength(fieldName);
			macstr = FieldUtils.getFixedLengthAsciiField(fieldName, buffer,
					offset, length, fieldsConfig);
			offset += length;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PacketOperationException("mac获取出错");
		}
		if (null == macstr || macstr.length() != 16) {
			logger.error("获取MAC错误！");
			return false;
		}
		// String macstr = buffer.substring(buffer.length()-16);
		byte[] mac = ConvertUtils.str2Bcd(macstr);
		// (byte[])fieldValues.get("D_EPOS_MAC");
		bm.setMac(mac);

		byte[] origMac = bm.getMac();
		if (origMac == null) {
			throw new PacketOperationException("上传mac值为空!");
		}

//		logger.info("mab:[{}]", new String(macData));
//		logger.info("mac:[{}]", ConvertUtils.bytes2HexStr(origMac));

		logger.info("开始进行MAC校验");
		// POS不上送终端号，使用商户号作为密钥管理号
		boolean result = encryptionService.checkMAC(origMac, macData,
				bm.getShopCode());
		if (result)
			logger.info("MAC校验成功!");
		else
			logger.info("MAC校验失败!");
		return result;
	}

	/**
	 * 取上传报文MAB
	 * 
	 * @return MAB
	 */
	@Profiled
	private byte[] getMacData() {
		// MAB长度除去报文头（6+2+2+1位）和MAC（16位）
		int mabLength = buffer.length() - 10 - 16;
		byte[] macData = new byte[mabLength];
		macData = buffer.substring(10, mabLength + 10).getBytes();
		return macData;
	}

	@Override
	public Map<DATA_TYPE, Object> packBuffer(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {
		logger.info("开始进行FIXSTRING打包....");
		offset = 0;
		String packet = null;
		cm = PacketUtils.getControlMessage(message);
		bm = PacketUtils.getBusinessMessage(message);
		String tranCode = cm.getTranCode();

		fieldValues.clear();
		headerHelper.pack(fieldValues, cm, bm);
		String header = packFIXSTRING();
		logger.info("交易{}打包包头完成,报文头[{}],用户号[{}]", new Object[] { tranCode,
				header, bm.getUserCode() });

		// pack body
		IPacketFIXSTRING packetHelper;
		String body = null;
		try {
			fieldValues.clear();

			packetHelper = (IPacketFIXSTRING) SpringContextHolder
					.getBean(getPacketBeanName(cm.getTranCode()));

			packetHelper.pack(fieldValues, cm, bm);
			body = packFIXSTRING();
			packet = header + body;

			// 减去报文头前10位+报文体作为MAB
			byte[] responseMac = getMac(
					packet.substring(10 + 6, packet.length())
							.getBytes("gb2312"), bm.getShopCode());
			logger.info("响应报文MAC值[{}]", responseMac);
			packet += ConvertUtils.bytes2HexStr(responseMac);

			// 报文长度处理
			packet = FieldUtils.leftAddZero4FixedLengthString(
					String.valueOf(packet.getBytes("gb2312").length - 6), 6)
					+ packet.substring(6);
			PacketUtils.setOrigAnsPacket(message, packet);
		} catch (InstantiationException e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		} catch (IllegalAccessException e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		} catch (ClassNotFoundException e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		} catch (Exception e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}

		logger.info("交易{}报文打包完成,报文:[{}]", tranCode, packet);

		return message;
	}

	private void unpackFIXSTRING(String[] fields, String buffer)
			throws PacketOperationException {
		int length = 0;
		String value = null;
		for (String fieldName : fields) {
			try {
				if (fieldsConfig.getFieldVariableType(fieldName) != VariableType.ASCII) {
					logger.warn("不支持的变量类型:"
							+ fieldsConfig.getFieldVariableType(fieldName));
					break;
				}
				length = fieldsConfig.getFieldAsciiLength(fieldName);
				value = FieldUtils.getFixedLengthAsciiField(fieldName, buffer,
						offset, length, fieldsConfig);

				fieldValues.put(fieldName, value.trim());
				offset += length;
				// offset += 1; //spliter
			} catch (Exception e) {
				logger.error("unpack field " + fieldName + "error!", e);
				throw new PacketOperationException();
			}
		}
	}

	private String packFIXSTRING() throws PacketOperationException {
		StringBuffer buffer = new StringBuffer();
		StringBuffer printbf = new StringBuffer();
		int length = 0;
		String value = null;
		for (String fieldName : fieldValues.keySet()) {
			try {
				if (fieldsConfig.getFieldVariableType(fieldName) != VariableType.ASCII) {
					logger.warn("不支持的变量类型:"
							+ fieldsConfig.getFieldVariableType(fieldName));
					break;
				}
				length = fieldsConfig.getFieldAsciiLength(fieldName);
				value = (String) fieldValues.get(fieldName);
				FieldUtils.setFixedLengthAsciiField(fieldName, buffer, length,
						value, fieldsConfig);
				// FieldUtils.setFixedLengthAsciiFieldSpliter(buffer,
				// FIXSTRINGFieldsConfig.FIELD_SPLITER);
				printbf.append(value + "|");
				logger.info("field:{}, value: [{}]", fieldName, value);
			} catch (Exception e) {
				logger.error("field: {} error!", fieldName, e);
				throw new PacketOperationException();
			}
		}
		logger.info("报文打包完成：{}", printbf);
		return buffer.toString();
	}

	/**
	 * @author 生成MAC
	 * @param buffer
	 *            报文体MAB
	 * @param shopCode
	 *            商户号
	 * @throws PacketOperationException
	 */
	private byte[] getMac(byte[] mab, String shopCode)
			throws PacketOperationException {

		byte[] mac = new byte[8];
		// 生成密钥
		logger.info("开始生成MAC");
		byte[] macByte = encryptionService.encryptMAC(mab, shopCode);
		System.arraycopy(macByte, 0, mac, 0, 8);
		logger.info("生成MAC结束");

		return mac;
	}

	// 仅当报文解包处理出错时调用该函数
	@Override
	public Map<DATA_TYPE, Object> packErrorBuffer(Map<DATA_TYPE, Object> message) {
		// 如果解包未完成，直接将请求包返回
		PacketUtils.setOrigAnsPacket(message,
				PacketUtils.getOrigReqPacket(message));
		return null;
	}

	private static String getPacketBeanName(String tranCode) {
		// String BASE_PACKAGE = "com.nantian.npbs.packet.business.FIXSTRING.";
		return "FIXSTRINGPacket" + tranCode + "Helper";
	}
}
