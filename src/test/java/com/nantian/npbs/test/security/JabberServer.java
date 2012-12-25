package com.nantian.npbs.test.security;

//JabberServer.java
// 一个非常简单的服务器，仅仅把客户端发送给它的消息回传给它
import java.io.*;
import java.net.*;

public class JabberServer {  
  // 选择一个范围在 1-1024之外的端口号
  public static final int PORT = 9999;
  
  public static void main(String[] args) throws IOException {
    ServerSocket s = new ServerSocket(PORT);//创建一个监听SOCKET
    System.out.println("服务器开始监听: " + s);//在控制台输出信息
    try {
      Socket socket = s.accept();//程序阻塞直到连接发生，返回已创建的SOCKET
      try {
        System.out.println("连接被接受: "+ socket);
        
        BufferedReader in = 
          new BufferedReader(
            new InputStreamReader(//把字节流适配成字符流
              socket.getInputStream()));//从SOCKET得到输入流
        
        PrintWriter out = 
          new PrintWriter(
            new BufferedWriter(//为字符流添加缓冲功能
              new OutputStreamWriter(//把字节流适配成字符流
                socket.getOutputStream())),true);//从SOCKET得到输出流，设置输出流为自动刷新输出
        
        while (true) {  
          String str = in.readLine();//从输入流读取数据
          if ("END".equals(str)) break;//如果数据为"END",结束循环
          System.out.println("你输入的是: " + str);//在控制台输出信息
          out.println(str);//向输出流写出信息
        }
      // 总是关闭两个socket...
      } finally {
        System.out.println("closing...");
        socket.close();//关闭socket
      }
    } finally {
      s.close();//关闭监听socket
    }
  } 
} ///:~
