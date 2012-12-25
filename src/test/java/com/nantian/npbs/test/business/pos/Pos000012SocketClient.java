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

public class Pos000012SocketClient {

	private static final Logger logger = LoggerFactory
			.getLogger(Pos000012SocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		sendSopBuffer(ConvertUtils.hexStr2Bytes(packet000012()));
		System.out.println("send 000012 OK!");

	}

	private static String packet000012() throws Exception {

		// 007 河电现金取消
		//return "006e600008000060220000085802001000000100c08019000000000100001030323736353339363030202020202020202020202020202020202020202030353030303030312020202020202020202020203135360000120015323031313131313233323933353000162dddc98f2dcb4b";
		// 003 电信取消
		//return "006f600008000060220000085802001000000100c0801900000000030000113135383131343430303738202020202020202020202020202020202020202030353030303030312020202020202020202020203135360000120015323031313131313233323934353233b98d851797118ca3";
	
		// 001 移动取消
//		return "006f600018000060220000783002001020000100c080190000000010000000120009313233343536373839202020202020202020202020202020202020202030353030303030332020202020202020202020203135360000120014313230313033303030303731313730484a84f88f7a47";
		
		//邯郸燃气
//		return "006e600008000060220000783002001020000100c08019000000002000000074000831323331323331322020202020202020202020202020202020202020303530303030303220202020202020202020202031353600001200143132303131333030303937393400d44257f12db4189d";
		
		//保定水费
		return "006d600008000060220000783002001020000100c0801900000000200000001200073233313233313220202020202020202020202020202020202020203035303030303033202020202020202020202020313536000012001431323032303630303030393936305f3e5ff74baee259";
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
			logger.info("result:{}", new String(tmp));
			String ansHexString = ConvertUtils.bytes2HexStr(tmp);
			logger.info("result-hex, length[:{}]:{}", ansHexString.length(),ansHexString);

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
		logger.info("Socket= {}" , s.hashCode());
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
