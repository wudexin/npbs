package com.nantian.npbs.test.performance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.common.utils.ConvertUtils;

public class Pos8887SocketClient {
	private static final Logger logger = LoggerFactory
			.getLogger(Pos8887SocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	private static final String payHexString = "0051600057000060220000087502000020000100c08011000267000131202020202020202020202020202020202020202030353030383838392020202020202020202020203135360140012e842308897e8f13";

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = true;

	/** 共享socket */
	private static final boolean SHARE_SOCKET = true;

	// 服务端地址和端口
	private static final String serverIp = "10.232.6.210";// "10.232.6.210";
	private static final int serverPort = 8887;
	private static final int timeOut = 10000;

	private static Socket socketHandle = null;

	public static void main(String args[]) throws Exception {
		sendMessageToServer();
	}

	public static void sendMessageToServer() {
		byte[] body = ConvertUtils.hexStr2Bytes(payHexString);

		for (int i = 0; i < 300; i++) {
			try {
				sendSopBuffer(body);
				logger.info("current packet number: " + i);

				Thread.sleep((long) (Math.random() * 100));
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public static byte[] sendSopBuffer(byte[] buf) throws Exception {

		// 通过Socket连接服务器
		logger.info("获取Socket");
		Socket server = getSocket();

		// 创建网络输出流输出内容到服务器上
		OutputStream netOut = server.getOutputStream();
		OutputStream dataOut = new BufferedOutputStream(netOut);

		byte[] serverbuf = new byte[2048];

		try {
			logger.info("发送数据包");
			dataOut.write(buf, 0, buf.length);// 把文件数据写出网络缓冲区
			dataOut.flush();// 刷新缓冲区把数据写往服务器端

			InputStream netIn = server.getInputStream();
			InputStream dataIn = new BufferedInputStream(netIn);

			int servernum = dataIn.read(serverbuf);
			logger.info("从服务器读取的数据长度：{}", servernum);
			if (servernum <= 0)
				throw new Exception("未能从前置读取有效数据！");

			/*
			 * while (servernum != (-1)) {
			 * logger.debug("continue reading from socket..."); servernum =
			 * dataIn.read(serverbuf);// 继续从网络中读取数据 }
			 */

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
			if (!SHARE_SOCKET)
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
		Socket s = null;
		if (!SHARE_SOCKET) {
			s = (Socket) sockPool.get();
			if (s == null || s.isClosed()) {
				logger.debug("s==null is " + (s == null));
				if (s != null)
					logger.info("is closed:" + s.isClosed());
				s = reconnect();
				sockPool.set(s);
			}
		} else {
			if (socketHandle == null) {
				synchronized (logger) {
					socketHandle = new Socket(serverIp, serverPort);
					socketHandle.setSoTimeout(timeOut);
				}
			}
			s = socketHandle;
		}
		logger.info("Socket={}", s.hashCode());
		return s;
	}

	private static Socket reconnect() throws UnknownHostException, IOException {
		logger.debug("recreating socket...");
		Socket s = new Socket(serverIp, serverPort);
		s.setSoTimeout(timeOut);
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
