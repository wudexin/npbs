package com.nantian.npbs.test.security;

//: JabberClient.java
// 一个非常简单的客户端，发送行给服务器，并读取服务器发回的信息
import java.net.*;
import java.io.*;

public class JabberClient {
  public static void main(String[] args) throws IOException {
    // 给getByName方法传递参数null，获得本机的IP地址
    InetAddress addr = 
      InetAddress.getByName(null);
    // 或者你可以用下面两种方式之一获得本机的IP地址
//     InetAddress addr = 
//        InetAddress.getByName("127.0.0.1");
//     InetAddress addr = 
//        InetAddress.getByName("localhost");
    
    System.out.println("要连接的地址addr = " + addr);//在控制台输出信息
    Socket socket = new Socket(addr, JabberServer.PORT);//给定IP地址和端口号来创建一个socket
     
    try {
      System.out.println("socket = " + socket);//在控制台输出信息
      
      BufferedReader in =
        new BufferedReader(
          new InputStreamReader(//把字节流适配成字符流
            socket.getInputStream()));//从SOCKET得到输入流
      
      PrintWriter out =
        new PrintWriter(
          new BufferedWriter(
            new OutputStreamWriter(
              socket.getOutputStream())),true);//从SOCKET得到输出流，设置输出流为自动刷新输出
      
      for(int i = 0; i < 10; i ++) {
        out.println("我写的是：howdy " + i);//向输出流中写出数据
        String str = in.readLine();//从输入流中读取数据
        System.out.println(str);//在控制台输出信息
      }
      out.println("END");//向输出流中写出数据"END"，结束程序
    } finally {
      System.out.println("closing...");//在控制台输出信息
      socket.close();
    }
  }
} ///:~
