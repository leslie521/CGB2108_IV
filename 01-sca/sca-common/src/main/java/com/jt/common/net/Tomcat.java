package com.jt.common.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过java代码实现一个简易Tomcat对象
 */
public class Tomcat {
    public static void main(String[] args) throws IOException {
        //1.构建一个ServerSocket对象(java网络编程中的服务对象),并在指定端口进行监听
        ServerSocket server=new ServerSocket(9000);
        System.out.println("server start ...");
        //2.等待客户端连接(可以循环处理多个客户端的请求)
        while(true){
            Socket client = server.accept();//阻塞式方法
            OutputStream out = client.getOutputStream();
            byte[] data=("HTTP/1.1 200 OK \n\r" +
                    "Content-Type: text/html;charset=utf-8 \n\r" +
                    "\n\r" +
                    "hello client").getBytes();
            out.write(data);
            out.flush();
        }//http
    }
}
//sca-consumer (tomcat->ServerSocket)
//sca-provider (tomcat->ServerSocket)
