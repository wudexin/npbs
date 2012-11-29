/**
 * 
 */
package com.nantian.npbs.packet.business.ISO8583;

import java.util.BitSet;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.PacketHeader;
import com.nantian.npbs.security.service.EncryptionService;

/**
 * @author TsaiYee
 * 
 */
@Component
public class ISO8583PacketHeaderHelper {

	private static Logger logger = LoggerFactory
			.getLogger(ISO8583PacketHeaderHelper.class);
	
	// 2位：报文长度的长度
	private static int posPacketLen = 2;
	// 5位bcd码：tpdu值(10byte)
	private static int tpduLen = 5;
	// 6位bcd：报文头(12byte)
	private static int headerLen = 6;
	// 2位bcd：消息类型(4byte)
	private static int messageTypeLen = 2;
	// 8位byte：位图表(64bit)
	private static int bitmapLen = 8;
	//mac 8位
	private static int macLen = 8;
	
	private int packetLength = 0;

	@Resource
	EncryptionService encryptionService;

	public byte[] pack(Object fieldValues[], byte[] buffer, int length,
			ControlMessage cm, BusinessMessage bm) throws Exception {

		// 经过和pos终端沟通确认、返回终端报文需要有报文头内容、而目前测试返回没有报文头内容，故需完善返回报文、封装报文头内容。
		// 报文头包括：2位：报文长度 + 5位bcd码：tpdu值(10byte) + 6位bcd：报文头(12byte) +
		// 2位bcd：消息类型(4byte) + 8位byte：位图表(64bit)

		logger.info("报文头打包开始!");

		int offset = 0;

		// 报文长度。因直接接收socket字符串所以上传报文中包含报文长度，但不打包到下传报文中，由socket添加报文长度。
		packetLength = length - posPacketLen; // 减去报文长度本身
		PacketHeader packetHeader = cm.getPacketHeader();
		packetHeader.setPacketLength(packetLength);

		//先拼上长度，后面删掉
		String packLenStr = Integer.toHexString(packetLength);
		packLenStr = FieldUtils.leftAddZero4FixedLengthString(packLenStr, 4);
		FieldUtils.setBcdField(buffer, offset, packLenStr, posPacketLen);
		offset += posPacketLen;

		logger.info("报文长度: {}" , packetLength);

		// 报文网关打包处理TPDU 5位bcd码：tpdu值(10byte)
		String tpdu = packetHeader.getTPDU();
		String tpduNew = tpdu.substring(0, 2) + tpdu.substring(6, 10)
				+ tpdu.substring(2, 6);
		FieldUtils.setBcdField(buffer, offset, tpduNew, tpduLen);
		offset += tpduLen;

		// 报文头处理 6位bcd：报文头(12byte)
		String applicationType = packetHeader.getApplicationType();// 应用类别定义
		String reserveString = packetHeader.getReserveString();// 保留使用
		char terminalState = packetHeader.getTerminalState();// 终端状态
		char handleType = packetHeader.getHandleType();// 处理要求
		String terminalVersion = packetHeader.getTerminalVersion();// 终端软件版本号
		String packetHeadStr = applicationType + reserveString + terminalState
				+ handleType + terminalVersion;
		FieldUtils.setBcdField(buffer, offset, packetHeadStr, headerLen);
		offset += headerLen;

		if ("000907".equals(bm.getTranCode()) != true) {
			// 打包消息类型、bitmap和mac
			packOtherHeader(fieldValues, buffer, offset, cm, bm);
		}
		
		

		// 删除报文长度
		byte[] buf = new byte[packetLength];
		System.arraycopy(buffer, posPacketLen, buf, 0, packetLength);

		logger.info("返回报文：{}" , ConvertUtils.bytes2HexStr(buf));

		return buf;
	}

	public byte[] packOtherHeader(Object fieldValues[], byte[] buffer,
			int offset, ControlMessage cm, BusinessMessage bm) throws Exception {
		// int offset = 0;
		PacketHeader packetHeader = cm.getPacketHeader();
//		int packetLength = packetHeader.getPacketLength();
		// 消息类型处理 例如：0810
		// 打包响应报文消息类型处理 by jxw
		// 处理规则如： xx0x -> xx1x 例如：0800 -> 0810
		String messageType = packetHeader.getMessageType();
		String retMessageType = getRetMessageType(messageType);
		FieldUtils.setBcdField(buffer, offset, retMessageType, messageTypeLen);
		offset += messageTypeLen;

		// 处理bitmap
		BitSet bitmap = new BitSet(64);
		for (int i = 1; i <= 64; i++) {
			if (fieldValues[i] != null) {
				bitmap.set(i - 1);
			}
		}

		// 8位byte：位图表(64bit)
		byte[] bitSetbyte = ConvertUtils.bitset2byteArray(bitmap); // 将BitSet转换为byte[]
		FieldUtils.setBinaryField(buffer, offset, bitSetbyte, bitmapLen);// ISO8583FieldsConfig.getInstance().getFieldAsciiLength(64)
		offset += bitmapLen;

		// 生成mac
		// bm.mac在RequestBusinessService中初始化为0
		if (bm.isMacFlag() == true && "000903".equals(bm.getTranCode()) == false && cm.isEnablePack()) {
			int mabLength = 0;
			if (fieldValues[64] == null) { // 如果packethelper未增加64域取值
				// 设置bitmap
				bitmap.set(64 - 1);
				bitSetbyte = ConvertUtils.bitset2byteArray(bitmap);
				FieldUtils.setBinaryField(buffer, offset - macLen, bitSetbyte, macLen);

				// mablen
				mabLength = packetLength - 11;
				packetHeader.setMabLength(mabLength);
				// packetLength
				packetLength += macLen;
				packetHeader.setPacketLength(packetLength);
			} else {
				// mablen
				// 11 = 5位tpdu + 6位报文头
				// 8位mac
				mabLength = packetLength - macLen - tpduLen - headerLen;
				packetHeader.setMabLength(mabLength);
			}
			// mac macLength = 8;
			byte[] mab = new byte[mabLength];
			// 13 = 2位报文长度 + 5位tupd + 6位报文头
			System.arraycopy(buffer, posPacketLen + tpduLen + headerLen, mab, 0, mabLength);
			logger.info("mab：{}" , ConvertUtils.bytes2HexStr(mab));
			bm.setMacData(mab);
			byte[] mac = getMac(mab, bm.getShopCode());
			logger.info("mac：{}" , ConvertUtils.bytes2HexStr(mac));
			bm.setMac(mac);
			String macStr = ConvertUtils.bytes2HexStr(mac);// "E9070BF5BD59099A";//
			FieldUtils.setBcdField(buffer, packetLength + posPacketLen - macLen, macStr, macLen);
		}

		logger.info("bitmap对应位设置(从0开始,位元需+1): {}" ,bitmap);
		return buffer;
	}

	/**
	 * @author jxw 生成mac
	 * @param buffer
	 *            需要生成mac的报文
	 * @param bm
	 *            bm
	 * @return mac mac值
	 * @throws PacketOperationException
	 */
	private byte[] getMac(byte[] mab, String shopCode)
			throws PacketOperationException {
		// generate mac
		byte[] mac = new byte[8];

		// encryptionService = new EncryptionService();
		// 生成密钥
		logger.info("开始生成MAC");
		byte[] macByte = encryptionService.encryptMAC(mab, shopCode);
		System.arraycopy(macByte, 0, mac, 0, 8);
		logger.info("生成MAC结束");

		return mac;
	}

	/**
	 * 打包响应报文消息类型处理 处理规则如： xx0x -> xx1x 例如：0800 -> 0810
	 * 
	 * @param messageType
	 *            原始请求消息类型
	 * @author jxw
	 * @return retMessageType 响应消息类型
	 */
	private String getRetMessageType(String messageType) {
		String oldChar = "0";
		String newChar = "1";
		String retMessageType = messageType.substring(0, 2)
				+ messageType.substring(2, 3).replace(oldChar, newChar)
				+ messageType.substring(3, 4);
		return retMessageType;
	}

	public String toHexString(int intType) {
		String returnStr = Integer.toHexString(intType);
		if (returnStr.length() == 2) {
			returnStr = "00" + returnStr;
		}
		return returnStr;
	}

	/**
	 * 报文头解包处理 2011- jxw
	 * 
	 * @param buffer
	 * @param cm
	 * @param bm
	 * @throws PacketOperationException
	 */

	public void unpack(Object buffer, ControlMessage cm, BusinessMessage bm)
			throws PacketOperationException {
		byte[] buf = (byte[]) buffer;

		int offset = 0;

		logger.info("开始解报文头...");
		// unpack packet header
		PacketHeader packetHeader = new PacketHeader();

		// 2位：报文长度
		byte[] packetByteLength = FieldUtils.getFieldByte(buf, 0, posPacketLen);
		int packetLength = ConvertUtils.byte2int(packetByteLength);
		packetHeader.setPacketLength(packetLength);
		offset += posPacketLen;
		logger.info("报文长度：{}", packetLength);

		// 5位bcd码：tpdu值(10byte)
		byte[] tPDUbyte = FieldUtils.getFieldByte(buf, offset, tpduLen);
		String tPDU = ConvertUtils.bytes2HexStr(tPDUbyte);
		packetHeader.setTPDU(tPDU);

		offset += tpduLen;

		// 6位bcd：报文头(12byte)
		byte[] packetHeadbyte = FieldUtils.getFieldByte(buf, offset, headerLen);
		String packetHead = ConvertUtils.bytes2HexStr(packetHeadbyte);
		String applicationType = packetHead.substring(0, 2);
		String reserveString = packetHead.substring(2, 4);
		char terminalState = packetHead.substring(4, 5).toCharArray()[0];
		char handleType = packetHead.substring(5, 6).toCharArray()[0];
		String terminalVersion = packetHead.substring(6, 12);

		packetHeader.setApplicationType(applicationType);
		packetHeader.setReserveString(reserveString);
		packetHeader.setTerminalState(terminalState);
		packetHeader.setHandleType(handleType);
		packetHeader.setTerminalVersion(terminalVersion);
		if (terminalState == '0') {
			logger.info("终端为正常交易状态! ");
		} else {
			logger.info("终端为测试交易状态! ");
		}

		offset += headerLen;

		// 2位bcd：消息类型(4byte)
		String messageType = FieldUtils.getBcdField(buf, offset, messageTypeLen);
		packetHeader.setMessageType(messageType);
		offset += messageTypeLen;
		logger.info("messageType: {}", messageType);

		// 8位byte：位图表(64bit)
		BitSet bitmap = ConvertUtils.byteArray2BitSet(FieldUtils.getFieldByte(
				buf, offset, bitmapLen));
		String bitmapString = ConvertUtils.bytes2HexStr(FieldUtils
				.getFieldByte(buf, offset, bitmapLen));
		logger.debug("bitmap hexString[{}]", bitmapString);
		logger.info("bitmap对应位设置(从0开始,位元需+1): {}", bitmap);
		packetHeader.setBitmap(bitmap);
		offset += bitmapLen;

		packetHeader.setHeaderLength(offset);
		// 设置mabLenght，如果上传报文中包含mac，则mabLenght报文长度需减去mac的长度
		if (bitmap.get(64 - 1)) {// 64域,对应bitmap是63位
			// tpdu长度5，报文头长度6，mac长度8
			packetHeader.setMabLength(packetLength - tpduLen - headerLen - macLen);
		} else {
			// tpdu长度5，报文头长度6
			packetHeader.setMabLength(packetLength - tpduLen - headerLen);
		}

		cm.setPacketHeader(packetHeader);

		logger.info("解包报文头完成, offset[{}]", offset);
	}
}