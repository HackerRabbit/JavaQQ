
package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;
import java.util.*;
import client.model.entity.OnlineUserListModel;
import common.model.entity.User;

public class DataBuffer {
	
	public static User currentUser;
	
	public static List<User> onlineUsers;
	
	public static Socket clientSeocket;
	
	public static ObjectOutputStream oos;
	
	public static ObjectInputStream ois;
	
	public static Properties configProp;
	
	public static Dimension screenSize;
	
	public static String ip ;
	
	public static final int RECEIVE_FILE_PORT = 6666;
	
	public static OnlineUserListModel onlineUserListModel;

	static{
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//加载服务器配置文件
		configProp = new Properties();
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			configProp.load(Thread.currentThread()
									.getContextClassLoader()
									.getResourceAsStream("serverconfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private DataBuffer(){}
}
