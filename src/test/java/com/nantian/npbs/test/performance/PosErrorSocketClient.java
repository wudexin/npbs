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

public class PosErrorSocketClient {
	private static final Logger logger = LoggerFactory
			.getLogger(PosErrorSocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	private static final String payHexString1 = "008160000800006022000078300200502200810cc0901100000000000001001699313030303030303030303030303030300000033133391112080000270706d5c5b6abd1c720202020202020202020202020202020202020203035303030303031202020202020202020202020313536514afa";
	private static final String payHexString2 = "0077aaaaaaaa0800006022000078300200502200810cc090110000000000000100";

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = true;

	/** 共享socket */
	private static final boolean SHARE_SOCKET = false;

	// 服务端地址和端口
	private static final String serverIp = "127.0.0.1";// "10.10.58.107";
	private static final int serverPort1 = 8884;
	private static final int serverPort2 = 8885;
	private static final int timeOut = 60000;

	public static void main(String args[]) throws Exception {
		sendMessageToServer();
	}

	public static void sendMessageToServer() throws UnknownHostException, IOException {
		byte[] body1 = ConvertUtils.hexStr2Bytes(payHexString1);
		byte[] body2 = ConvertUtils.hexStr2Bytes(payHexString2);

		Socket server1 = getSocket(serverPort1);
		Socket server2 = getSocket(serverPort2);
		
			try {
				sendSopBuffer(body1,server1, serverPort1);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				sendSopBuffer(body2,server2, serverPort2);
				Thread.sleep((1000));
				sendSopBuffer(body1,server1, serverPort1);
				
				readResult(server2);

				Thread.sleep((long) (Math.random() * 100));
			} catch (Exception e) {
				logger.error("", e);
			}
	}

	public static Socket sendSopBuffer(byte[] buf, Socket server, int port) throws Exception {

		// 通过Socket连接服务器
		logger.info("获取Socket");

		// 创建网络输出流输出内容到服务器上
		OutputStream netOut = server.getOutputStream();
		OutputStream dataOut = new BufferedOutputStream(netOut);

		try {
			logger.info("发送数据包");
			dataOut.write(buf, 0, buf.length);// 把文件数据写出网络缓冲区
			dataOut.flush();// 刷新缓冲区把数据写往服务器端

			return server;
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
	}

	private static byte[] readResult(Socket server) throws IOException,
			Exception {
		InputStream netIn = server.getInputStream();
		InputStream dataIn = new BufferedInputStream(netIn);

		byte[] serverbuf = new byte[2048];

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


		return serverbuf;
	}

	private static Socket getSocket(int port) throws UnknownHostException, IOException {
		Socket s = reconnect(port);
		logger.info("Socket={}", s.hashCode());
		return s;
	}

	private static Socket reconnect(int port) throws UnknownHostException, IOException {
		logger.debug("recreating socket...");
		Socket s = new Socket(serverIp, port);
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
