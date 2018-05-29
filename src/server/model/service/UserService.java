
package server.model.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import server.DataBuffer;

import common.model.entity.User;
import common.util.IOUtil;

public class UserService {
	private static int idCount = 3; //id
	
	//添加
	public void addUser(User u){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaqq", "root", "root");
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO `user` (`id`, `qq`, `password`, `nickname`, `sex`, `head`) VALUES ('0', '"+u.getQq()+"', '"+u.getPassword()+"', '"+u.getNickname()+"', '"+u.getSex()+"', '"+u.getHead()+"')";
			stmt.execute(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//登陆
	public User login(long qq, String password){
		User result = null;
		List<User> users = loadAllUser();
		for (User user : users) {
			if(qq == user.getQq() && password.equals(user.getPassword())){
				result = user;
				break;
			}
		}
		return result;
	}
	
	//根据ID获取用户登陆信息
	public User loadUser(long id){
		User result = null;
		List<User> users = loadAllUser();
		for (User user : users) {
			if(id == user.getId()){
				result = user;
				break;
			}
		}
		return result;
	}
	
	
	//加载所有用户
	public List<User> loadAllUser() {
		ArrayList<User> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaqq", "root", "root");
			Statement stmt = conn.createStatement();
			String sql = "select * from user";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User u = new User(rs.getString("password"), rs.getString("nickname"), rs.getString("sex"), rs.getInt("head"));
				u.setId(rs.getLong("id"));
				u.setQq(rs.getLong("qq"));
				list.add(u);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	public static void main(String[] args){
		List<User> users = new UserService().loadAllUser();
		for (User user : users) {
			System.out.println(user);
		}
	}
}
