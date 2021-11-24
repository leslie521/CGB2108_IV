package com.jt.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 模拟浏览器
 */
public class Browser {
    public static void main(String[] args) throws IOException {
        //1.构建网络客户端对象Socket，并通过此对象对远端服务进行连接
        Socket socket=new Socket("127.0.0.1", 9000);
        //2.获取流对象
        InputStream in = socket.getInputStream();
        byte[] buf=new byte[1024];
        int len=in.read(buf);
        System.out.println("server say:"+new String(buf,0,len));
        //3.释放资源
        socket.close();
    }
}
