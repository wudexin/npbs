package com.nantian.npbs.test.performance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.utils.ConvertUtils;

public class EPosSocketClient {
	private static final Logger logger = LoggerFactory
			.getLogger(EPosSocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = true;

	private static final String payHexString = "000275700000100296Z07033           001571                                  12022100407629                                                              E2957399ACA7AD90  1CNY00000000200018733243127         *永*                                                        5D245C11F55FD34C";

	public static void main(String args[]) throws Exception {

		sendMessageToServer();

	}

	public static void sendMessageToServer() throws UnsupportedEncodingException {
		byte[] body = payHexString.getBytes(DynamicConst.PACKETCHARSET);

		for (int i = 0; i < 10; i++) {
			try {
				sendSopBuffer(body);

				Thread.sleep((long) (Math.random() * 100));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	public static byte[] sendSopBuffer(byte[] buf) throws Exception {

		// 通过Socket连接服务器
		Socket server = getSocket();

		// 创建网络输出流输出内容到服务器上
		OutputStream netOut = server.getOutputStream();
		OutputStream dataOut = new BufferedOutputStream(netOut);

		byte[] serverbuf = new byte[2048];

		try {
			dataOut.write(buf, 0, buf.length);// 把文件数据写出网络缓冲区
			dataOut.flush();// 刷新缓冲区把数据写往服务器端

			InputStream netIn = server.getInputStream();
			InputStream dataIn = new BufferedInputStream(netIn);

			int servernum = dataIn.read(serverbuf);
			logger.info("从服务器读取的数据长度：{}", servernum);
			if (servernum <= 0)
				throw new Exception("未能从前置读取有效数据！");

			// 得到成功标志
			byte[] tmp = new byte[servernum];
			for (int i = 0; i < servernum; i++) {
				tmp[i] = serverbuf[i];
			}
			logger.info("result:{}", new String(tmp));
			String ansHexString = ConvertUtils.bytes2HexStr(tmp);
			logger.info("result-hex, length[:{}]:{}", ansHexString.length(),
					ansHexString);

		} catch (Exception e) {
			closeSocket();
			throw (e);
		} finally {
			if (!KEEP_CONNECTION) {
				netOut.close();
				dataOut.close();
				closeSocket();
			}
		}

		return serverbuf;
	}

	private static Socket getSocket() throws UnknownHostException, IOException {
		Socket s = (Socket) sockPool.get();
		if (s == null || s.isClosed()) {
			logger.info("s==null is " + (s == null));
			if (s != null)
				logger.info("is closed:{}", s.isClosed());
			s = reconnect();
			sockPool.set(s);
		}
		logger.info("Socket={}", s.hashCode());
		return s;
	}

	private static Socket reconnect() throws UnknownHostException, IOException {
		logger.info("recreating socket...");
		String serverIp = "127.0.0.1";
		int serverPort = 8888;
		Socket s = new Socket(serverIp, serverPort);
		s.setSoTimeout(60000);
		logger.info("socket recreated");
		return s;
	}

	public static void closeSocket() throws IOException {
		Socket s = (Socket) sockPool.get();
		sockPool.set(null);
		if (s != null && !s.isClosed()) {
			s.close();
		}
	}
}
