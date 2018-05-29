
package server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import server.model.entity.*;
import common.model.entity.User;

//服务器数据缓存
public class DataBuffer {

	public static ServerSocket serverSocket;

	public static Map<Long, OnlineClientIOCache> onlineUserIOCacheMap;

	public static Map<Long, User> onlineUsersMap;

	public static Properties configProp;

	public static RegistedUserTableModel registedUserTableModel;

	public static OnlineUserTableModel onlineUserTableModel;

	public static Dimension screenSize;
	
	static{
		// 初始化
		onlineUserIOCacheMap = new ConcurrentSkipListMap<Long,OnlineClientIOCache>();
		onlineUsersMap = new ConcurrentSkipListMap<Long, User>();
		configProp = new Properties();
		registedUserTableModel = new RegistedUserTableModel();
		onlineUserTableModel = new OnlineUserTableModel();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// 加载服务器配置文件
		try {
			configProp.load(Thread.currentThread()
								  .getContextClassLoader()
								  .getResourceAsStream("serverconfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
