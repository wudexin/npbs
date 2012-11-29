/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import java.util.BitSet;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.GlobalConst.DATA_TYPE;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.IPacket;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.FieldsConfig;
import com.nantian.npbs.packet.internal.PacketHeader;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * @author TsaiYee
 * 
 */
@Scope("prototype")
@Component
public class ISO8583PacketHelper implements IPacket {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583PacketHelper.class);

	protected Object fieldValues[] = new Object[64 + 1];
	protected int offset = 0;

	protected byte[] buffer;

	@Resource
	private FieldsConfig fieldsConfig;

	private ControlMessage cm;
	private BusinessMessage bm;

	@Resource
	ISO8583PacketHeaderHelper headerHelper;
	
	@Resource
	EncryptionService encryptionService;

	// @Resource
	// private EncryptionService encryptionService;

	// 仅当报文解包处理出错时调用该函数
	@Override
	@Profiled
	public Map<DATA_TYPE, Object> packErrorBuffer(Map<DATA_TYPE, Object> message) {
		buffer = new byte[2048];
		//解包失败原样数据返回 jxw 2011.12.13
//		for (int i = 0; i < fieldValues.length; i++)
//			fieldValues[i] = null;

		cm = PacketUtils.getControlMessage(message);
		bm = PacketUtils.getBusinessMessage(message);

		logger.info("开始进行错误包打包，报文是否合法: {}", cm.isEnablePack());

		// 如果不是非法报文（通过mac校验），返回错误信息，否则原包信息返回
		if (cm.isEnablePack()) {
			// field 39, response code
			int fieldNo = 39;
			fieldValues[fieldNo] = bm.getResponseCode();
			FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);

			// field 44, response msg
			fieldNo = 44;
			fieldValues[fieldNo] = bm.getResponseMsg();
			FieldUtils.assertFieldNull(fieldNo, fieldValues[fieldNo]);
			
		}
		try {
			pack8583();
			// 报文头打包，包括mac
			buffer = headerHelper.pack(fieldValues, buffer, offset,
					cm, bm);
		} catch (Exception e) {
			logger.error("pack header error!", e);
		}

		PacketUtils.setOrigAnsPacket(message, buffer);
		logger.info("retCode:[{}]; retMsg:[{}];",
				bm.getResponseCode(),
				bm.getResponseMsg());

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nantian.npbs.common.packet.IPacket#packBuffer(java.lang.Object)
	 */
	@Override
	@Profiled
	public Map<DATA_TYPE, Object> packBuffer(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {
		//解包成功只返回可用信息 jxw 2011.12.13
		for (int i = 0; i < fieldValues.length; i++)
			fieldValues[i] = null;
		
		buffer = new byte[2048];

		cm = PacketUtils.getControlMessage(message);
		bm = PacketUtils.getBusinessMessage(message);

		// response code convert
		String resultCode = cm.getResultCode();
		if (GlobalConst.RESULTCODE_SUCCESS.equals(resultCode) && !"000010".equals(bm.getTranCode())) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		} else if (resultCode.equals("999999")){
			 // timeout or unknown result
				bm.setResponseCode("99");
//			else
//				bm.setResponseCode("01");
		}

		IPacketISO8583 packetHelper;
		try {
			// 取交易需要打包到报文中的域
			packetHelper = cm.getTranPacketHelper();//(IPacketISO8583) SpringContextHolder.getBean(getPacketBeanName(cm.getTranCode()));

			logger.info("packFields:8583");
			// 从bm中取值，对fieldValues中响应的bitmap位赋值，未设置的报文将原样返回
			packetHelper.pack(fieldValues, cm, bm);
			// 从fieldValues中取值，打包成buffer报文体，不含mac，mac在报文头中打包。
			if ("000907".equals(bm.getTranCode())) {
				set907HeaderOffset();
				byte[] bytes = (byte[]) bm.getCustomData();
				FieldUtils.setFieldByte(buffer, offset, bytes);
				offset += bytes.length;
			} else {
				pack8583();
			}
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

		// pack header, and trim buffer
		// headerHelper = new PacketHeaderHelper();
		logger.info("packHeader");
		try {
			buffer = headerHelper.pack(fieldValues, buffer, offset, cm, bm);
		} catch (Exception e) {
			logger.error("get packet helper class error!", e);
			throw new PacketOperationException();
		}

		PacketUtils.setOrigAnsPacket(message, buffer);
		logger.info("pack buffer finished, buffer length {}", buffer.length);
		if ("00".equals(bm.getResponseCode())) {
			bm.setResponseMsg("交易成功!");
		}
		logger.info("交易码:[{}];商户号:[{}];retCode:[{}] ;retMsg:[{}]",
				new Object[]{bm.getTranCode(),bm.getShopCode(),bm.getResponseCode(),bm.getResponseMsg()}
				);

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nantian.npbs.common.packet.IPacket#unpackObject(java.lang.Object)
	 */
	@Override
	public Map<DATA_TYPE, Object> unpackObject(Map<DATA_TYPE, Object> message)
			throws PacketOperationException {
		// get object
		// 对于ISO8583报文，netty使用length-decoder进行解码，解码后的类型为BigEndianHeapChannelBuffer
		// 由processor转换为byte[]，此处直接使用byte[]
		buffer = (byte[]) (PacketUtils.getOrigReqPacket(message));
		logger.info("终端报文: {}", ConvertUtils.bytes2HexStr(buffer));

		cm = PacketUtils.getControlMessage(message);
		bm = PacketUtils.getBusinessMessage(message);
		bm.setSystemChanelCode(GlobalConst.posChanelCode);

		// unpack header for ControlMessage
		headerHelper.unpack(buffer, cm, bm);
		
		// unpack buffer to fields[]
		setOffset(cm.getPacketHeader().getHeaderLength());
		unpack8583(cm);
		
		if(cm.getTranCode() == null || "".equals(cm.getTranCode())){
			logger.error("报文解包失败!报文格式错误");
			throw new PacketOperationException("报文解包失败!报文格式错误");
		}
		bm.setTranCode(cm.getTranCode().trim());
		cm.setTranCode(bm.getTranCode());
		if(bm.getShopCode() == null || "".equals(bm.getShopCode())){
			logger.error("商户号不存在");
			throw new PacketOperationException("商户号不存在");
		}
		bm.setShopCode(bm.getShopCode().trim());
		
		
		// check mac
		if (!checkMac(bm)) {
			throw new PacketOperationException("mac 错误!");
		}
		//mac校验通过，设置报文为合法报文
		cm.setEnablePack(true);

		IPacketISO8583 packetHelper;
		packetHelper = (IPacketISO8583) SpringContextHolder.getBean(getPacketBeanName(cm.getTranCode()));
		packetHelper.unpack(fieldValues, cm, bm);
		
		cm.setTranPacketHelper(packetHelper);
		PacketUtils.setBusinessMessage(message, bm);
		PacketUtils.setControlMessage(message, cm);
		

		return message;
	}

	protected boolean isSet(BitSet bitmap, int fieldNo) {
		return bitmap.get(fieldNo);
	}

	protected void resetOffset() {
		offset = 0;
	}

	protected void setOffset(int offset) {
		this.offset = offset;
	}

	protected void setHeaderOffset() {
		this.offset = 23;
	}

	protected void set907HeaderOffset() {
		this.offset = 13;
	}

	@Profiled
	private String getVariableLengthBcdAsciiField(int fieldNo, byte[] buffer)
			throws Exception {
		int headerlength = fieldsConfig.getFieldHeaderBcdLength(fieldNo);
		int maxAsciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);

		String headerString = FieldUtils.getBcdField(buffer, offset,
				headerlength);
		int length = Integer.parseInt(headerString);

		if (length > maxAsciiLength) {
			logger.error("解包错误，域长度大于该域最大长度! fieldNo=[" + fieldNo + "];length=["
					+ length + "];maxAsciiLength=[" + maxAsciiLength + "];");
			throw new PacketOperationException("解包错误，域长度大于该域最大长度! fieldNo=["
					+ fieldNo + "];length=[" + length + "];maxAsciiLength=["
					+ maxAsciiLength + "];");
		}

		offset += headerlength;

		// value
		String value = FieldUtils.getAsciiField(buffer, offset, length);

		offset += length;

		// if length of value is too long, must split
		if (value.length() > length)
			value = value.substring(0, length - 1);

		logger.info(
				"field[{}], offset start[{}], offset end[{}], headerLength[{}]variableASCIILength[{}][{}]:[{}]",
				new Object[] { fieldNo, (offset - headerlength - length),
						offset, headerlength, length,
						fieldsConfig.getDescription(fieldNo), value });

		return value;
	}

	@Profiled
	private String getVariableLengthAsciiField(int fieldNo, byte[] buffer)
			throws Exception {
		int headerlength = fieldsConfig.getFieldHeaderAsciiLength(fieldNo);
		int maxAsciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);

		int length = Integer.parseInt(FieldUtils.getAsciiField(buffer, offset,
				headerlength));

		offset += headerlength;

		if (length > maxAsciiLength) {
			logger.error("解包错误，域长度大于该域最大长度! fieldNo=[" + fieldNo + "];length=["
					+ length + "];maxAsciiLength=[" + maxAsciiLength + "];");
			throw new PacketOperationException("解包错误，域长度大于该域最大长度! fieldNo=["
					+ fieldNo + "];length=[" + length + "];maxAsciiLength=["
					+ maxAsciiLength + "];");
		}

		// value
		String value = FieldUtils.getAsciiField(buffer, offset, length);

		offset += length;

		// if length of value is too long, must split
		if (value.length() > length)
			value = value.substring(0, length - 1);

		logger.debug("field[" + fieldNo + "], offset start["
				+ (offset - headerlength - length) + "]offset end[" + offset
				+ "][" + fieldsConfig.getDescription(fieldNo) + "]");

		logger.debug("field[" + fieldNo + "]headerLength[" + headerlength
				+ "]variableASCIILength[" + length + "]["
				+ fieldsConfig.getDescription(fieldNo) + "]:[" + value + "]");

		return value;
	}

	@Profiled
	private String getVariableLengthBcdField(int fieldNo, byte[] buffer)
			throws Exception {
		int headerlength = fieldsConfig.getFieldHeaderBcdLength(fieldNo);
		int maxAsciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);

		String headerString = FieldUtils.getBcdField(buffer, offset,
				headerlength);

		int length = Integer.parseInt(headerString);
		int bcdLength = (int) Math.ceil(length / 2.0);
		offset += headerlength;

		if (length > maxAsciiLength) {
			logger.error("解包错误，域长度大于该域最大长度! fieldNo=[" + fieldNo + "];length=["
					+ length + "];maxAsciiLength=[" + maxAsciiLength + "];");
			throw new PacketOperationException("解包错误，域长度大于该域最大长度! fieldNo=["
					+ fieldNo + "];length=[" + length + "];maxAsciiLength=["
					+ maxAsciiLength + "];");
		}

		// value
		String value = FieldUtils.getBcdField(buffer, offset, bcdLength);

		offset += bcdLength;

		// if length of value is too long, must split
		if (value.length() > length)
			value = value.substring(0, length - 1);

		int asciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);
		if (value.length() > asciiLength)
			value = value.substring(0, asciiLength - 1);

		logger.debug("field[" + fieldNo + "], offset start["
				+ (offset - headerlength - bcdLength) + "]offset end[" + offset
				+ "][" + fieldsConfig.getDescription(fieldNo) + "]");

		logger.debug("field[" + fieldNo + "]headerLength[" + headerlength
				+ "]variableBCDLength[" + bcdLength + "]variableASCIILength["
				+ length + "][" + fieldsConfig.getDescription(fieldNo) + "]:["
				+ value + "]");

		return value;
	}

	@Profiled
	private void unpack8583(ControlMessage cm) {
		logger.info("开始解8583数据元...");
		BitSet bitmap = cm.getPacketHeader().getBitmap();

		int length = 0;
		// bitmap 0 is second bitmap flag, the no second bitmap
		for (int fieldNo = 1; fieldNo <= 64; fieldNo++) {
			try {
				// BitSet from 0
				if (isSet(bitmap, fieldNo - 1)) {
					switch (fieldsConfig.getFieldLengthType(fieldNo)) {
					case FIXED:
						switch (fieldsConfig.getFieldVariableType(fieldNo)) {
						case BCD:
							length = fieldsConfig.getFieldBcdLength(fieldNo);
							fieldValues[fieldNo] = FieldUtils
									.getFixedLengthBcdField(fieldNo, buffer,
											offset, length, fieldsConfig);
							offset += length;
							break;

						case ASCII:
							length = fieldsConfig.getFieldAsciiLength(fieldNo);
							fieldValues[fieldNo] = FieldUtils
									.getFixedLengthAsciiField(fieldNo, buffer,
											offset, length, fieldsConfig);
							offset += length;
							break;

						case BINARY:
							length = fieldsConfig.getFieldBcdLength(fieldNo);
							fieldValues[fieldNo] = FieldUtils
									.getFixedLengthBinaryField(fieldNo, buffer,
											offset, length, fieldsConfig);
							offset += length;
							break;

						default:
							logger.error("不支持的组合类型!");
							break;
						}
						break;
					case VARIABLE:
						switch (fieldsConfig.getFieldVariableType(fieldNo)) {
						case BCD:
							fieldValues[fieldNo] = getVariableLengthBcdField(
									fieldNo, buffer);
							break;

						case ASCII:
							fieldValues[fieldNo] = getVariableLengthAsciiField(
									fieldNo, buffer);
							break;

						case BCDASCII:
							fieldValues[fieldNo] = getVariableLengthBcdAsciiField(
									fieldNo, buffer);
							break;

						default:
							logger.error("不支持的组合类型!");
							break;
						}
						break;
					}
				}
			} catch (Exception e) {
				logger.error("unpack 8583 error, fieldNo[" + fieldNo + "]!", e);
				break;
			}
		}

		// field 42, shop code
		String shopCode = (String) fieldValues[42];
		bm.setShopCode(shopCode);
		
		// field 60, tranCode
		String tranCode = (String) fieldValues[60];
		cm.setTranCode(tranCode);

		logger.info("8583解包到字段数组完成!");
	}

	@Profiled
	private byte[] setVariableLengthBcdField(int fieldNo, byte[] buffer,
			String value) throws Exception {
		// check length
		int maxAsciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);
		if (value.length() > maxAsciiLength)
			throw new Exception("field[" + fieldNo + "] [" + value
					+ "] length is too long, expect[" + maxAsciiLength
					+ "]actual["
					+ value.getBytes(DynamicConst.PACKETCHARSET).length + "]");

		// complete Header String
		int headerAsciiLength = fieldsConfig.getFieldHeaderAsciiLength(fieldNo);
		String headerString = Integer.toString(value
				.getBytes(DynamicConst.PACKETCHARSET).length);
		FieldUtils.leftAddZero4FixedLengthString(headerString,
				headerAsciiLength);

		int headerBcdLength = fieldsConfig.getFieldHeaderBcdLength(fieldNo);
		int maxBcdLength = fieldsConfig.getFieldMaxBcdLength(fieldNo);

		logger.debug("field[{}], offset[{}], [{}]", new Object[] { fieldNo,
				offset, fieldsConfig.getDescription(fieldNo) });

		logger.debug("field[" + fieldNo + "],headerBcdLength["
				+ headerBcdLength + "]headerAsciiLength[" + headerAsciiLength
				+ "]maxBcdLength[" + maxBcdLength + "]maxAsciiLength["
				+ maxAsciiLength + "]" + "value[" + value + "]");

		// length
		offset += FieldUtils.setBcdField(buffer, offset, headerString,
				headerBcdLength);

		// body
		offset += FieldUtils.setBcdField(buffer, offset, value, maxBcdLength);

		return buffer;
	}

	@Profiled
	private byte[] setVariableLengthAsciiField(int fieldNo, byte[] buffer,
			String value) throws Exception {
		// check length
		int maxAsciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);
		if (value.length() > maxAsciiLength)
			throw new Exception("field[" + fieldNo + "] [" + value
					+ "] length is too long, expect[" + maxAsciiLength
					+ "]actual["
					+ value.getBytes(DynamicConst.PACKETCHARSET).length + "]");

		// complete Header String
		int headerAsciiLength = fieldsConfig.getFieldHeaderAsciiLength(fieldNo);
		String headerString = Integer.toString(value
				.getBytes(DynamicConst.PACKETCHARSET).length);
		FieldUtils.leftAddZero4FixedLengthString(headerString,
				headerAsciiLength);

		int headerlength = fieldsConfig.getFieldHeaderAsciiLength(fieldNo);
		int maxlength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);

		logger.debug("field[{}], offset[{}], [{}]", new Object[] { fieldNo,
				offset, fieldsConfig.getDescription(fieldNo) });

		logger.debug("field[" + fieldNo + "]headerAsciiLength["
				+ headerAsciiLength + "]maxAsciiLength[" + maxAsciiLength + "]"
				+ "value[" + value + "]");

		// length
		offset += FieldUtils.setAsciiField(buffer, offset, headerString,
				headerlength);

		// body
		offset += FieldUtils.setAsciiField(buffer, offset, value, maxlength);

		return buffer;
	}

	@Profiled
	private byte[] setVariableLengthBcdAsciiField(int fieldNo, byte[] buffer,
			String value) throws Exception {
		// check length
		int maxAsciiLength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);
		if (value.length() > maxAsciiLength)
			throw new Exception("field[" + fieldNo + "] [" + value
					+ "] length is too long, expect[" + maxAsciiLength
					+ "]actual[" + value.length() + "]");

		// complete Header String
		int headerAsciiLength = fieldsConfig.getFieldHeaderAsciiLength(fieldNo);

		int headerlength = fieldsConfig.getFieldHeaderBcdLength(fieldNo);
		int maxlength = fieldsConfig.getFieldMaxAsciiLength(fieldNo);

		logger.debug("field[{}], offset[{}], [{}]", new Object[] { fieldNo,
				offset, fieldsConfig.getDescription(fieldNo) });

		logger.debug("field[" + fieldNo + "],headerBcdLength[" + headerlength
				+ "]headerAsciiLength[" + headerAsciiLength
				+ "]maxAsciiLength[" + maxAsciiLength + "]" + "value[" + value
				+ "]");

		int bcdlength = headerlength * 2;
		String headerString = Integer.toString(value
				.getBytes(DynamicConst.PACKETCHARSET).length);
		headerString = FieldUtils.leftAddZero4FixedLengthString(headerString,
				bcdlength);

		// length
		offset += FieldUtils.setBcdField(buffer, offset, headerString,
				bcdlength);

		// body
		offset += FieldUtils.setAsciiField(buffer, offset, value, maxlength);

		return buffer;
	}

	@Profiled
	public byte[] pack8583() throws Exception {
		logger.info("开始打包8583数据元....");
		setHeaderOffset();
		int length = 0;
		for (int fieldNo = 1; fieldNo <= 64; fieldNo++) {
			try {
				if (fieldValues[fieldNo] != null) {
					switch (fieldsConfig.getFieldLengthType(fieldNo)) {
					case FIXED:
						switch (fieldsConfig.getFieldVariableType(fieldNo)) {
						case BCD:
							length = fieldsConfig.getFieldBcdLength(fieldNo);
							FieldUtils
									.setFixedLengthBcdField(fieldNo, buffer,
											offset, length,
											(String) fieldValues[fieldNo],
											fieldsConfig);
							offset += length;
							break;

						case ASCII:
							length = fieldsConfig.getFieldAsciiLength(fieldNo);
							FieldUtils
									.setFixedLengthAsciiField(fieldNo, buffer,
											offset, length,
											(String) fieldValues[fieldNo],
											fieldsConfig);
							offset += length;
							break;

						case BINARY:
							length = fieldsConfig.getFieldAsciiLength(fieldNo);
							FieldUtils
									.setFixedLengthBinaryField(fieldNo, buffer,
											offset, length,
											(byte[]) fieldValues[fieldNo],
											fieldsConfig);
							offset += length;
							break;

						default:
							logger.error("不支持的组合类型!");
							break;
						}
						break;
					case VARIABLE:
						switch (fieldsConfig.getFieldVariableType(fieldNo)) {
						case BCD:
							setVariableLengthBcdField(fieldNo, buffer,
									(String) fieldValues[fieldNo]);
							break;

						case ASCII:
							setVariableLengthAsciiField(fieldNo, buffer,
									(String) fieldValues[fieldNo]);
							break;

						case BCDASCII:
							setVariableLengthBcdAsciiField(fieldNo, buffer,
									(String) fieldValues[fieldNo]);
							break;

						default:
							logger.error("不支持的组合类型!");
							break;
						}
						break;
					}
				}
			} catch (Exception e) {
				logger.error("pack 8583 error, fieldNo[" + fieldNo + "]!");
				throw new Exception(e.getMessage());
			}
		}
		logger.info("打包8583数据元完成....");
		return buffer;
	}

	/**
	 * 取上传报文mab
	 * 
	 * @param
	 * @return mab
	 */
	@Profiled
	private byte[] getMacData() {
		// 从报文消息类型（MTI）到63域之间的部分构成MAC ELEMEMENT BLOCK
		// （MAB），采用ECB算法，加密结果为64位的MAC。
		PacketHeader packetHeader = cm.getPacketHeader();
		int mabLength = packetHeader.getMabLength();
		byte[] macData = new byte[mabLength];
		// 报文长度本身2位，tpdu长度5，报文头长度6
		System.arraycopy(buffer, 13, macData, 0, mabLength);
		// logger.info("上传报文mab:" + ConvertUtils.bytes2HexStr(macData));
		return macData;
	}

	@Profiled
	protected void assertFieldNull(int fieldNo, Object value) {
		if (value == null)
			logger.error("fileds[" + fieldNo + "] value is null!!!");
	}
	
	// check mac
	private boolean checkMac(BusinessMessage bm) throws PacketOperationException  {
		
		// check mac
		//查询是否需要校验mac
		ResourceBundle rb = null;
		try{
			rb = ResourceBundle.getBundle("conf.checkFlag");
			bm.setMacFlag(Boolean.valueOf(rb.getString(bm.getChanelType()+"checkMacFlag")));
		}catch(Exception e){
			logger.error("取mac校验标志错!",e);
			//默认为true
			bm.setMacFlag(true);
		}
		logger.info("mac校验标志:[{}],交易码:[{}]!",bm.isMacFlag(),bm.getTranCode());
		if(bm.isMacFlag() == false || "000903".equals(bm.getTranCode()) || "000907".equals(bm.getTranCode())){
			return true;
		}
		
		// 无论是否校验mac，都取出mab
		// 从报文消息类型（MTI）到63域之间的部分构成MAC ELEMEMENT BLOCK
		// （MAB），采用ECB算法，加密结果为64位的MAC。
		byte[] macData = getMacData();
		bm.setMacData(macData);
		
		// field 64, shop code
		byte[] mac = (byte[]) fieldValues[64];
		bm.setMac(mac);
		
		byte[] origMac = bm.getMac();
		if(origMac == null){
			throw new PacketOperationException("上传mac值为空!");
		}

//		logger.info("mab:{}" , ConvertUtils.bytes2HexStr(macData));
//		logger.info("mac:{}" , ConvertUtils.bytes2HexStr(origMac));

		logger.info("开始进行MAC校验");
		// POS不上送终端号，使用商户号作为密钥管理号
//		encryptionService = new EncryptionService();
		boolean result = encryptionService.checkMAC(origMac, macData, bm.getShopCode());
		if (result)
			logger.error("MAC校验成功!");
		else
			logger.error("MAC校验失败!");
		return result;
	}

	@Profiled
	private static String getPacketBeanName(String tranCode) {

		// String BASE_PACKAGE = "com.nantian.npbs.packet.business.ISO8583.";

		return "ISO8583Packet" + tranCode + "Helper";
	}

}