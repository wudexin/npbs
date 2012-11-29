package com.nantian.npbs.test.gateway.camel;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nantian.npbs.common.utils.ConvertUtils;

public class SocketSyncRequestTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		int client_port=7777;
		String host="127.0.0.1";
		
		Socket client = new Socket(host, client_port); 
		System.out.println("client Connected ok: " + client);

		OutputStream netOut = client.getOutputStream();
		OutputStream dataOut = new BufferedOutputStream(netOut);
		
		//send message
//		String msg = "I'm ok\n";
		String hexString = "00a86081cb0003602200000637021000" + 
			"3800810ed08011506615091808201105" + 
			"30010011313531323838383835383020" +
			"110530289085042ad7d32a3030313233" +
			"34353637382020202020202020202020" + 
			"20303536303031363620202020202020" +
			"20202020200053d3c3bba7bac5c2eba3" +
			"ba31353132383838383538300ac7b7b7" +
			"d1bdf0b6eea3ba2d312e35340ad3c3bb" +
			"a7c3fbb3c6a3ba2ad7d32a0a31353600" +
			"1001762072e5e369237c";
		byte[] msg = ConvertUtils.hexStr2Bytes(hexString);

		dataOut.write(msg, 0, msg.length);// 把文件数据写出网络缓冲区
		dataOut.flush();// 刷新缓冲区把数据写往服务器端
		
		System.out.println("send msg ok");
		
		//receive message
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
		System.out.println(in.readLine());
		
		client.close();
	}

}
