package com.nantian.npbs.test.business.pos;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nantian.npbs.common.utils.ConvertUtils;

public class POS903SocketClient {

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	// 服务端地址和端口---------网控器下联卡ip：10.22.17.15,port:8000。tpdu:0054-0057对应10.232.6.210的8884-8888端口，tpdu:0064-0067对应10.22.17.13的8884-8888端口；后四位：0000
	private static final String serverIp = "10.232.6.210";// "10.232.6.210";
	private static final int serverPort = 8885;

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = false;

	public static void main(String args[]) throws Exception {
		// for(int i =0;i<100;i++)
		sendSopBuffer(ConvertUtils.hexStr2BytesOld(packet000903()));
		// System.out.println(bytes2HexStrOld(hexStr2BytesOld(packet000903())));
		System.out.println("send 000903 OK!");
	}

	private static String packet000903() throws Exception {
		return "004b600055000060100000000008000020000000c00011006952313131312020202020202020202020202020202030363635313137382020202020202020202020200009033131313131313131";
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
			System.out.println("刷新缓冲区");
			dataOut.flush();// 刷新缓冲区把数据写往服务器端
			// System.out.println("sleep");
			//
			// Thread.sleep(10000);
			// System.out.println("open instream");

//			InputStream netIn = server.getInputStream();
//			InputStream dataIn = new BufferedInputStream(netIn);
//			System.out.println("read");
//
//			int servernum = dataIn.read(serverbuf);
//			System.out.println("从服务器读取的数据长度：[" + servernum + "]");
//			if (servernum <= 0)
//				throw new Exception("未能从前置读取有效数据！");
//
//			/*
//			 * while (servernum != (-1)) {
//			 * logger.debug("continue reading from socket..."); servernum =
//			 * dataIn.read(serverbuf);// 继续从网络中读取数据 }
//			 */
//
//			// 得到成功标志
//			byte[] tmp = new byte[servernum];
//			for (int i = 0; i < servernum; i++) {
//				tmp[i] = serverbuf[i];
//			}
//			// System.out.println("result:["+new String(tmp)+"]");
//			String ansHexString = bytes2HexStrOld(tmp);
//			System.out.println("result-hex, length:[" + ansHexString.length()
//					+ "]:[" + ansHexString + "]");

		} catch (Exception e) {
			closeSocket();
			throw (e);
		} finally {
			if (!KEEP_CONNECTION) {
				Thread.sleep(10000);
				closeSocket();
			}
		}

		return serverbuf;
	}

	private static Socket getSocket() throws UnknownHostException, IOException {
		Socket s = (Socket) sockPool.get();
		if (s == null || s.isClosed()) {
			System.out.println("s==null is " + (s == null));
			if (s != null)
				System.out.println("is closed:" + s.isClosed());
			s = reconnect();
			sockPool.set(s);
		}
		System.out.println("Socket=" + s.hashCode());
		return s;
	}

	private static Socket reconnect() throws UnknownHostException, IOException {
		System.out.println("recreating socket...");
//		String serverIp = serverIp;
//		int serverPort = serverPort;
		Socket s = new Socket(serverIp, serverPort);
		s.setSoTimeout(10000000);
		System.out.println("socket recreated");
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
