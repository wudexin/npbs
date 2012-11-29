package com.nantian.npbs.test.performance.pos;

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

public class PosDuplexSocketClient {
	private static final Logger logger = LoggerFactory
			.getLogger(PosDuplexSocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

//	private static final String payHexString = "0052600052000060220000087502000020000100c08011000263000231322020202020202020202020202020202020202020303530303838383920202020202020202020202031353601400133bcecfc08802c1b";

	private static final String payHexString = "0052600052000060220000087502000020000100c08011000263000231322020202020202020202020202020202020202020303530303838383920202020202020202020202031353601400133bcecfc08802c1b";
		//"0043600054800060220000087508000020000000c0001000027220202020202020202020202020202020202020203035303038383839202020202020202020202020000903";
	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = true;

	/** 同步读取还是异步读取 */
	private static final boolean ASYNC_READ = true;

	// 服务端地址和端口
	private static final String serverIp = "10.232.6.210";// "10.232.6.210";
	private static final int serverPort = 8884;
	private static final int timeOut = 60000;

	private static Socket socketHandle = null;

	public static void main(String args[]) throws Exception {
		// 通过Socket连接服务器
		logger.info("获取Socket");
		socketHandle = getSocket();

		if (ASYNC_READ) {
			// 建立异步侦听线程
			new Thread(new SocketReader(socketHandle)).start();
		}

		sendMessageToServer(socketHandle, true);
	}

	public static void sendMessageToServer(Socket server, boolean fromMain)
			throws UnknownHostException, IOException {
		byte[] body = ConvertUtils.hexStr2Bytes(payHexString);

		// 通过Socket连接服务器
		logger.info("获取Socket");

		Socket local = null;

		if (!fromMain) {
			local = getSocket();

			if (ASYNC_READ) {
				// 建立异步侦听线程
				new Thread(new SocketReader(local)).start();
			}
		} else
			local = server;

		for (int i = 0; i < 10; i++) {
			try {
				sendSopBuffer(local, body, 26);

				Thread.sleep((long) (Math.random() * 100));
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		if (fromMain)
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static byte[] sendSopBuffer(Socket server, byte[] buf, int count)
			throws Exception {

		// 创建网络输出流输出内容到服务器上
		OutputStream netOut = server.getOutputStream();
		OutputStream dataOut = new BufferedOutputStream(netOut);

		InputStream netIn = server.getInputStream();
		
		try {
			logger.info("发送数据包");
			for (int i = 0; i < count; i++){
				logger.info("current packet number: " + i);
				dataOut.write(buf, 0, buf.length);// 把文件数据写出网络缓冲区
			}
			dataOut.flush();// 刷新缓冲区把数据写往服务器端

			if (!ASYNC_READ) {
				//直接读取返回结果
				InputStream dataIn = new BufferedInputStream(netIn);
				SocketReader.readSocket(dataIn);
			}
			
		} catch (Exception e) {
			logger.error("发送失败！", e);
		} finally {
			if (!KEEP_CONNECTION) {
				netOut.close();
				dataOut.close();
				closeSocket();
			}
		}
		return null;

	}

	private static Socket getSocket() throws UnknownHostException, IOException {
		Socket s = null;
		s = (Socket) sockPool.get();
		if (s == null || s.isClosed()) {
			logger.debug("s==null is " + (s == null));
			if (s != null)
				logger.info("is closed:" + s.isClosed());
			s = reconnect();
			sockPool.set(s);
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
