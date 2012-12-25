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

public class Pos010026SocketClient {

	private static final Logger logger = LoggerFactory
			.getLogger(Pos010026SocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		sendSopBuffer(ConvertUtils.hexStr2Bytes(packet010026()));
		System.out.println("send 010026 OK!");

	}

	private static String packet010026() throws Exception {

		return "021c600018000060100000000002000020000008c0021900011312041600222648313131313131312020202020202020202020202030353531303032372020202020202020202020200440343733343234303030303030313930333030303039453544313537324641383532353644303138463942353131393635323544362020202020202020202031313030303030303037343239343936373234323934393637323432393439363732343239343936373230313432393439363732343239343936373234323934393637323432393439363732464646464646202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202030373539323839363337202020202020d0cfcca8bed6202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020bad3b1b1caa1caafbcd2d7afcad0303030202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020303030303030303030303030302e3030b3c7d5f2bed3c3f1b2bbc2fa314b56202020202020202020202020202020303030303030303030303030302e3532303030303030303030303030302e30303030302e303001002600143132303431363030323232363438644f65bf80dba7cf";
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
		int serverPort = 8884;
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
