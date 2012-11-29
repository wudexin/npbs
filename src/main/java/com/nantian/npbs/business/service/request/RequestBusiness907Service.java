package com.nantian.npbs.business.service.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.ProgramDao;
import com.nantian.npbs.business.model.TbSmProgram;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * POS应用程序更新
 * @author MDB
 */
@Scope("prototype")
@Component
public class RequestBusiness907Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness907Service.class);
	 
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("应用程序更新业务处理开始：");

		//返回报文体
		byte[] retbt = null;
		if((String.valueOf(GlobalConst.HEADER_DEALREQ_NO)).equals(bm.getShop().getProgramFlag())){
			retbt = getError("无下载要求,无需更新!");
			bm.setCustomData(retbt);
			return;
		}
		
		//55域
		String resStartNo = bm.getCustomData().toString();
		//首次请求标致
		Boolean isFirst = false;
		if("0000".equals(resStartNo)){
			isFirst = true;
		}
		
		TbSmProgram pro = null;
		pro = programDao.getProgrameByFilepath(bm.getShop().getFilepath());
		if(null == pro){
			logger.error("商户[{}]程序路径[{}]错误!",
					bm.getShop().getCompanyCode(),bm.getShop().getFilepath());
			retbt = getError("商户程序路径错误!");
			bm.setCustomData(retbt);
			return;
		}
		
		//商户表文件路径字段对应的文件
		File file = new File(bm.getShop().getFilepath());
		logger.info("文件路径{},文件大小{} ",file.getAbsolutePath(),file.length());
 		
		try {
			//第一次请求
			if(isFirst){
				int offset = 0;
				int pakLen = 2 + 4 + 4 + 1 + 32 + 6 + 4;
				int crcDataLen = pakLen - 4;
				retbt = new byte[pakLen];
				byte[] crcbt = new byte[crcDataLen];
				
				//首次响应报文
					//状态
				offset += FieldUtils.setAsciiField(retbt, offset, GlobalConst.RESPONSECODE_SUCCESS, 2);
					// 响应帧号（终端请求帧号）
				offset += FieldUtils.setAsciiField(retbt, offset, resStartNo, 4);
					//文件大小（3位BCD）
				offset += FieldUtils.setBcdField(retbt, offset, getFileBcdLength(file.length()), 4);
					//是否强制下载
				offset += FieldUtils.setAsciiField(retbt, offset, pro.getIsForce(), 1);
					//MD5校验值
				offset += FieldUtils.setAsciiField(retbt, offset, pro.getMd5(), 32);
					//版本号
				offset += FieldUtils.setAsciiField(retbt, offset, FieldUtils.leftAddSpace4FixedLengthString(pro.getId().toString(),6), 6);
					//CRC校验值
				byte[] crcData = new byte[crcDataLen];
				System.arraycopy(retbt, 0, crcData, 0, crcDataLen);
				crcbt = ConvertUtils.str2Bcd(FieldUtils.leftAddZero4FixedLengthString(getCRC32(crcData), 8));
				FieldUtils.setFieldByte(retbt, offset, crcbt);
			}
			//非首次请求
			else{
				//返回数据默认长度
				int blockLength = GlobalConst.DOWN_BLOCK_SIZE;
				//返回数据实际长度
				if(isLastBlock(file.length(),resStartNo))
					blockLength = (int)getLastBlockSize(file.length());
				//读文件起始位：(请求帧号-1)*512
				int readStart = (Integer.valueOf(resStartNo).intValue()-1)*GlobalConst.DOWN_BLOCK_SIZE;
				
				logger.info("判断最后一个报文包:{}",isLastBlock(file.length(),resStartNo));
				logger.info("终端请求帧数（{})",resStartNo);
				logger.info("读取文件起始地址{}读取文件长度{}",readStart,blockLength);
				
				int offset = 0;
				int crcDataLen = 0;
				int alllen = 0;
				
				alllen = 2 + 4 + 3 + blockLength + 4;
				crcDataLen = alllen - 4;
				retbt = new byte[alllen];
				
				byte[] block = new byte[blockLength];
				InputStream stream = new FileInputStream(file);
				stream.skip(readStart);
				try {
					stream.read(block,0,blockLength);
				} finally {
					stream.close();
				}
				
				//非首次响应报文
					//状态
				offset += FieldUtils.setAsciiField(retbt, offset, GlobalConst.RESPONSECODE_SUCCESS, 2);
					// 响应帧号（终端请求帧号）
				offset += FieldUtils.setAsciiField(retbt, offset, resStartNo, 4);
					//实际长度位
				offset += FieldUtils.setAsciiField(retbt, offset, FieldUtils.leftAddZero4FixedLengthString(String.valueOf(blockLength), 3), 3);
					//文件内容
				retbt = FieldUtils.setFieldByte(retbt, offset, block);
				offset += blockLength;
					//CRC校验值
				byte[] crcData = new byte[crcDataLen];
				System.arraycopy(retbt, 0, crcData, 0, crcDataLen);
				byte[] crcbt = ConvertUtils.str2Bcd(FieldUtils.leftAddZero4FixedLengthString(getCRC32(crcData), 8));
				FieldUtils.setFieldByte(retbt, offset, crcbt);
			}
		} catch (IOException e) {
			logger.error("获取文件流失败"+e);
			retbt = getError("获取文件流失败");
			bm.setCustomData(retbt);
			return;
		} catch (Exception e) {
			logger.error("读取文件异常异常"+e);
			retbt = getError("读取文件异常异常");
			bm.setCustomData(retbt);
			return;
		}

		bm.setCustomData(retbt);
		logger.info("报文头处理标志:{}",cm.getPacketHeader().getHandleType());
		logger.info("响应报文串{} ",ConvertUtils.bytes2HexStr(retbt));
		logger.info("应用程序更新业务处理结束。");
	}
	
	/**
	 * 获取错误报文
	 * 
	 * @param errorInfo 错误提示信息
	 * @throws Exception 
	 */
	private byte[] getError(String errorInfo){
		int offset = 0;
		int pakLen = 2+errorInfo.length()*3+1+4;
		int crcDataLen = pakLen - 4;
		byte[] error = new byte[pakLen];
		byte[] crcbt = new byte[crcDataLen];
		try {
			//响应类型"99"
			offset += FieldUtils.setAsciiField(error, offset, "99", 2);
			//消息内容
			offset += FieldUtils.setAsciiField(error, offset, errorInfo, errorInfo.length()*3);
			//是否强制下载
			offset += FieldUtils.setAsciiField(error, offset, "0", 1);
			//CRC校验值
			byte[] crcData = new byte[crcDataLen];
			System.arraycopy(error, 0, crcData, 0, crcDataLen);
			crcbt = ConvertUtils.str2Bcd(getCRC32(crcData));
			FieldUtils.setFieldByte(error, offset, crcbt);
			logger.info("组织错误报文ConvertUtils.bytes2HexStr(error):{}",ConvertUtils.bytes2HexStr(error));
		} catch (Exception e) {
			logger.error("错误:组织错误报文抛错");
		}
		return error;
	}
	
	/**
	 * 计算文件帧总数（帧数从0起始）
	 * 
	 * @param filelength 文件长度
	 */
	private long getBlockNumber(long filelength){
		return 
		filelength % GlobalConst.DOWN_BLOCK_SIZE ==0 ?
				filelength/GlobalConst.DOWN_BLOCK_SIZE : 
				(filelength/GlobalConst.DOWN_BLOCK_SIZE +1);
	}
	
	/**
	 * 根据请求帧号判断是否为最后一个报文
	 * 
	 * @param filelength 文件长度
	 * @param resStartNo 终端请求帧号
	 */
	private boolean isLastBlock(long filelength,String resStartNo){
		if(Integer.valueOf(resStartNo) == getBlockNumber(filelength))
			return true;
		else
			return false;
		
	}
	
	/**
	 * 返回最后一帧实际长度
	 * 
	 * @param filelength 文件长度
	 */
	private long getLastBlockSize(long filelength){
		return (filelength % GlobalConst.DOWN_BLOCK_SIZE);
	}
	
	/**
	 * 根据传入字符串返回CRC32值
	 * 
	 * @param in 传入字符串
	 */
	private String getCRC32(byte[] crcBlock) throws Exception {
		CRC32 crc32 = new CRC32();
		crc32.update(crcBlock);
		return Long.toHexString(crc32.getValue());
	}
	
	/**
	 * 将文件长度转换为3位BCD
	 * 
	 * @param filelength 文件长度
	 */
	private static String getFileBcdLength(long filelength) throws Exception{
		return FieldUtils.leftAddZero4FixedLengthString(String.valueOf(filelength), 8);
	}
	
	@Override
	public boolean needLockProcess() {
		return false;
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		cm.setServiceCallFlag("0");
	}
	
	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}
	
	@Override
	protected String tradeType() {
		// 管理类型的交易
		return "08";
	}
}
