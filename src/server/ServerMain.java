
package server;

import java.io.IOException;
import java.net.*;
import javax.swing.*;
import server.controller.RequestProcessor;
import server.ui.ServerInfoFrame;

public class ServerMain {
	public static void main(String[] args) {
		int port = Integer.parseInt(DataBuffer.configProp.getProperty("port"));
		//初始化服务器
		try {
			DataBuffer.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			//启动新线程进行客户端连接监听
			public void run() {
				try {
					while (true) {
						// 监听
						Socket socket = DataBuffer.serverSocket.accept();
						//每个客户端启动一个线程，在线程中处理每个客户端的请求
						new Thread(new RequestProcessor(socket)).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new ServerInfoFrame(); 
	}
}