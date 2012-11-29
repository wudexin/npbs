package com.nantian.npbs.test.performance.pos;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.utils.ConvertUtils;

/**
 * 
 * @author 王玮
 * @version 创建时间：2012-3-17 上午2:47:37
 * 
 */

public class SocketReader implements Runnable {

	private Socket socket = null;

	private static final Logger logger = LoggerFactory
			.getLogger(SocketReader.class);

	private InputStream dataIn  = null;
	
	public SocketReader(Socket socket) {
		this.socket = socket;
		logger.info("socket for reader={}",socket.toString());
		try {
			InputStream netIn = socket.getInputStream();
			dataIn = new BufferedInputStream(netIn);
			logger.info("socket reader stream={}",netIn.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			logger.info("start socket reader...");
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
			try {
				logger.info("read from socket...");
				readSocket(dataIn);
				Thread.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static int readSocket(InputStream is) throws IOException, Exception {

		byte[] serverbuf = new byte[2048];

		int servernum = 0;


		servernum = is.read(serverbuf);
		while (servernum != -1) {
			logger.info("从服务器读取的数据长度： {}", servernum);

			// 得到成功标志
			byte[] tmp = new byte[servernum];
			for (int i = 0; i < servernum; i++) {
				tmp[i] = serverbuf[i];
			}

			String ansHexString = ConvertUtils.bytes2HexStr(tmp);
			logger.info("result-hex, length[:{}]:{}", ansHexString.length(),
					ansHexString);

			servernum = is.read(serverbuf);// 继续从网络中读取数据
		}
		
		logger.info("leave from reader!");
		return servernum;
	}

}
