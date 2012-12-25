package com.nantian.npbs.test.gateway.camel;

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
import com.nantian.npbs.test.business.POSMSG;

public class POSSocketClient {
	private static final Logger logger = LoggerFactory
			.getLogger(POSSocketClient.class);

	private final static ThreadLocal<Socket> sockPool = new ThreadLocal<Socket>();

	/** 决定客户端采用长连接还是短连接的方式 */
	private static final boolean KEEP_CONNECTION = true;

	public static void main(String args[]) throws Exception {
		
//		try {
//			sendSopBuffer(body);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//		
		// 连续发送100次，起到一定的压力、破坏性测试的作用
		for (int i = 0;i<10000 ; i++) {
			try {
				String msg = getRandomMsg();
				logger.info("msg:"+msg);
				if(msg ==null || "".equals(msg)){
					continue;
				}
				byte[] body = ConvertUtils.hexStr2Bytes(getRandomMsg());				
				sendSopBuffer(body);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}

	/**
	 * 随机报文
	 * @return
	 */
	public static String getRandomMsg(){
		String[] str = POSMSG.getMsg();
		return str[getRandomNum(4)];
		
	}
	
	/**
	 * 随机数
	 * @return
	 */
	public static int getRandomNum(int x){
		return 1+new java.util.Random().nextInt(x);
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

			/*InputStream netIn = server.getInputStream();
			InputStream dataIn = new BufferedInputStream(netIn);

			int servernum = dataIn.read(serverbuf);
			logger.info("从服务器读取的数据长度：{}" , servernum);
			if (servernum <= 0)
				throw new Exception("未能从前置读取有效数据！");

			
			 * while (servernum != (-1)) {
			 * logger.debug("continue reading from socket..."); servernum =
			 * dataIn.read(serverbuf);// 继续从网络中读取数据 }
			 

			// 得到成功标志
			byte[] tmp = new byte[servernum];
			for (int i = 0; i < servernum; i++) {
				tmp[i] = serverbuf[i];
			}
			System.out.println(new String(tmp,"UTF-8"));
			logger.info("result:{}" , new String(tmp));
			String ansHexString = ConvertUtils.bytes2HexStr(tmp);
			logger.info("result-hex, length[:{}]:{}" , ansHexString.length(),ansHexString);*/
			

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
		logger.info("Socket={}" , s.hashCode());
		return s;
	}

	private static Socket reconnect() throws UnknownHostException, IOException {
		logger.debug("recreating socket...");
		/*String serverIp = "127.0.0.1";
		int serverPort = 7777;*/
		String serverIp = "10.232.6.210";		
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
