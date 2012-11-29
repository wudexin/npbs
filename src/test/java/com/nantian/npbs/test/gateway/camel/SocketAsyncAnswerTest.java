package com.nantian.npbs.test.gateway.camel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketAsyncAnswerTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		int server_port=9987; 
		ServerSocket server = new ServerSocket(server_port);
		Socket client = server.accept();
		
		System.out.println("client Connected ok: " + client);
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
		System.out.println("accept data:" + in.toString());
		PrintWriter out = new PrintWriter(client.getOutputStream());
		String msg = "reply you !";
		out.println(msg);
		out.flush();
		client.close();
	}

}
