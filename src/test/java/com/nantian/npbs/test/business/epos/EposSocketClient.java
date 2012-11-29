package com.nantian.npbs.test.business.epos;

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

public class EposSocketClient {
	private static final Logger logger = LoggerFactory.getLogger(EposSocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		String payHexString = "02a96000038277602200000901020050"
				+ "20008114c09211196221881210017188"
				+ "56100000000200009009040000103035"
				+ "34363137323335370104996221881210"
				+ "017188561d1561560000000000000003"
				+ "000000214141449121d000000000000d"
				+ "000000000000d00000007090600060d1"
				+ "a6b7e520202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "20202020202020202020202020202030"
				+ "35303031303232202020202020202020"
				+ "202020313536999eae8722341ffd0448"
				+ "30303030303030303030373637393137"
				+ "31383730303030303030303030303736"
				+ "37393137313837304339334131463642"
				+ "38323933304538434433363043383339"
				+ "31443835464631323030303030303030"
				+ "30303030303030303030303330303030"
				+ "31313533303030303033383430303030"
				+ "30303030303030303438353630313030"
				+ "30303030303030303030303030303030"
				+ "30303030303030303030303030303030"
				+ "30303030202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "20202020202020202020202030353436"
				+ "31373233353720202020202020202020"
				+ "d1a6b7e5202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "20202020202020202020202020202020"
				+ "202020202020202020202020bad3b1b1"
				+ "caa1caafbcd2d7afcad0d0c2bbaac7f8"
				+ "b1b1b6febbb7c3f7d6e9b9abd4b0b1b1"
				+ "b2e0a3acbaecd0c7d0a1c7f8cef7b2e0"
				+ "3323c2a531b5a5d4aa33303220202020"
				+ "202020202020202020202020b3c7d5f2"
				+ "bed3c3f1b2bbc2fa314b562020202020"
				+ "20202020202020202020353220202020"
				+ "20202020202020202020302020202020"
				+ "34342020202020202020202020202020" + "010002b43755519fa390b4";
		byte[] body = ConvertUtils.hexStr2Bytes(payHexString);
		sendSopBuffer(body);
		System.out.println("发送结束!");

		// 连续发送100次，起到一定的压力、破坏性测试的作用
//		for (int i = 0; i < 100; i++) {
//			try {
//				sendSopBuffer(body);
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			}
//		}

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
			 * logger.infor("continue reading from socket..."); servernum =
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
			logger.info("s==null is " + (s == null));
			if (s != null)
				logger.info("is closed:{}" , s.isClosed());
			s = reconnect();
			sockPool.set(s);
		}
		logger.info("Socket={}" , s.hashCode());
		return s;
	}

	private static Socket reconnect() throws UnknownHostException, IOException {
		logger.info("recreating socket...");
		String serverIp = "127.0.0.1";
		int serverPort = 8888;
		Socket s = new Socket(serverIp, serverPort);
		s.setSoTimeout(10000000);
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
