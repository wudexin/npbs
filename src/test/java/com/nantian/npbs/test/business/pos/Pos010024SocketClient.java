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

public class Pos010024SocketClient {

	private static final Logger logger = LoggerFactory
			.getLogger(Pos010024SocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		sendSopBuffer(ConvertUtils.hexStr2Bytes(packet010024()));
		System.out.println("send 010024 OK!");

	}

	private static String packet010024() throws Exception {

		//return "0154600018000060100000000002000020000100c082190000980010303735393238393633373131313120202020202020202020202020202020303535313030323720202020202020202020202031353602323437333432343030303030303139303330303030353645394246393242443730413732363031384639423531313936353235443620202020202020202020313130303030303030373432393439363732343239343936373234323934393637323432393439363732303134323934393637323432393439363732343239343936373234323934393637324646464646462020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020595a443130323230313230343139313330303139323438202020202020202020010024001431323034313630303232323634358bca4f5b1350fe40";
		//return "0157600018000060220000783002000020000100c002190000230016303334303132333437342020202020202020202020202020202020202020202020202020303530303838383920202020202020202020202002323030303030303030303037363738383933303038463135313044373536333533323433364633364341443638383545453432413830303030303030303030313130303030303130353432393439363732343239343936373234323934393637323432393439363732303134323934393637323432393439363732343239343936373234323934393637324646464646462020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020595a44313032323031323034323331333030313933353320202020202020202001002400143132303432333030323233303536147aee250eba6af1";
		return "0154600018000060100000000002000020000100c082190065000010303334303132333437343131313120202020202020202020202020202020303530303838383920202020202020202020202031353602323030303030303030303037363738383933303038383844413130443436433931343945354633364341443638383545453432413820202020202020202020313130303030303135333432393439363732343239343936373234323934393637323432393439363732303134323934393637323432393439363732343239343936373234323934393637324646464646462020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020595a443130323230313230343234313330303230343038202020202020202020010024001431323034323430303232393935391e014500babf58ac";
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
