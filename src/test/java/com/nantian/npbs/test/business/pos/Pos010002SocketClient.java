package com.nantian.npbs.test.business.pos;

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

public class Pos010002SocketClient {

	private static final Logger logger = LoggerFactory
			.getLogger(Pos010002SocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		sendSopBuffer(ConvertUtils.hexStr2Bytes(packet010002()));
		System.out.println("send 010002 OK!");

	}

	private static String packet010002() throws Exception {

		return "027e60001800006022000078300200502200810cc092110000000000010000000231303030303030303030303030303030000016303334303132333437342020202020201201130000908460cbefd3c0c7bf202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202030353030303030322020202020202020202020203135367ec4fc42757e85e70440303030303030303030303736373838393330303845463731463142304544383739413036334333314646433035374130413836363030303030303030303030303030303030303536303030303032323330303030303032313030303030303030303030303133313430313030303030303030303030303030303030303537303136333030303030303030303030303030202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202030333430313233343734202020202020cbefd3c0c7bf202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020bad3b1b1caa1caafbcd2d7afcad0bfaab7a2c7f8d6e9b7e5b4f3bdd6313830bac5323223c2a531b5a5d4aa39303120202020202020202020202020202020202020202020202020202020202020202020303030303030303030303030302e3030b7c7c6d5b2bbc2fa314b5620202020202020202020202020202020202020303030303030303030303030302e3830303030303030303030303030302e30313030302e3030010002b64987ead406f1de";
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
			logger.info("从服务器读取的数据长度：{}" , servernum);
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
			logger.info("result:{}" , new String(tmp));
			String ansHexString = ConvertUtils.bytes2HexStr(tmp);
			logger.info("result-hex, length[:{}]:{}",ansHexString.length() ,ansHexString);

		} catch (Exception e) {
			closeSocket();
			throw (e);
		} finally {
			if (!KEEP_CONNECTION) {
				closeSocket();
			}
		}

		return serverbuf;
	}

	private static Socket getSocket() throws UnknownHostException, IOException {
		Socket s = (Socket) sockPool.get();
		if (s == null || s.isClosed()) {
			logger.debug("s==null is " + (s == null));
			if (s != null)
				logger.debug("is closed:" + s.isClosed());
			s = reconnect();
			sockPool.set(s);
		}
		logger.info("Socket={} " , s.hashCode());
		return s;
	}

	private static Socket reconnect() throws UnknownHostException, IOException {
		logger.debug("recreating socket...");
		String serverIp = "127.0.0.1";
		int serverPort = 7777;
		Socket s = new Socket(serverIp, serverPort);
		s.setSoTimeout(10000000);
		logger.debug("socket recreated");
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
