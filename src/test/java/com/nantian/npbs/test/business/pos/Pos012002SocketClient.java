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

public class Pos012002SocketClient {

	private static final Logger logger = LoggerFactory
			.getLogger(Pos012002SocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		sendSopBuffer(ConvertUtils.hexStr2Bytes(packet011002()));
		System.out.println("send 012002 OK!");

	}

	private static String packet011002() throws Exception {

		return "02b260001881c36022000078300200502200810cc092110000000001000000000331303030303030303030303030303030000016303333333834353537342020202020201205253016169906d5c5d6beb8fc202020202020202020202020202020202020202030353030383838382020202020202020202020203135365681d2635d8a01b30546313031313037303030303131384531373131373542323137374346393136393439343842374544444342314432414633303233364443333733344539323236363245363537313442333846373739323631374532344642463338333232334437353633324238413537373344393442334234353334413237374335303044424533443931454345323439344530424135383534443736374534424230434332343930343535344442383145463841413243453831363034313944323030323341383436373639364338383045453043374137424137443641343630383243414138363846334435453145363142343039424233303546384343334234374534364134384544333130384237423734373432313136323045323935383436423739303630303736373935383837343830303130303030373634393930323030303030303034303030313030303030333333383435353734202020202020d5c5d6beb8fc202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020b6abd3aab4e5cef7d0c2d4f63230304b564120202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020203031303030303030303030303030302e303030303030302e30303133343031303230322020202020202030323030302e303030303030302e3030012002eabe5ec65a1da5da";
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
