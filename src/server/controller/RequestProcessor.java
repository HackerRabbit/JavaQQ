
package server.controller;

import java.io.*;
import java.net.Socket;
import java.text.*;
import java.util.concurrent.CopyOnWriteArrayList;
import server.*;
import server.model.service.UserService;
import common.model.entity.*;

public class RequestProcessor implements Runnable{
	private Socket currentClientSocket;
	
	public RequestProcessor(Socket currentClientSocket){
		this.currentClientSocket = currentClientSocket;
	}
	
	public void run() {
		boolean flag = true;
		try{
			OnlineClientIOCache currentClientIOCache = new OnlineClientIOCache(
					new ObjectInputStream(currentClientSocket.getInputStream()), 
					new ObjectOutputStream(currentClientSocket.getOutputStream()));
			while(flag){
				Request request = (Request)currentClientIOCache.getOis().readObject();
				System.out.println("Server读取了客户端的请求:" + request.getAction());

				String actionName = request.getAction();
				if(actionName.equals("userRegiste")){      //注册
					registe(currentClientIOCache, request);
				}else if(actionName.equals("userLogin")){  //登录
					login(currentClientIOCache, request);
				}else if("exit".equals(actionName)){       //断开
					flag = logout(currentClientIOCache, request);
				}else if("chat".equals(actionName)){       //聊天
					chat(request);
				}else if("shake".equals(actionName)){      //振动
					shake(request);
				}else if("toSendFile".equals(actionName)){ //发送文件
					toSendFile(request);
				}else if("agreeReceiveFile".equals(actionName)){ //同意接收文件
					agreeReceiveFile(request);
				}else if("refuseReceiveFile".equals(actionName)){ //拒绝接收文件
					refuseReceiveFile(request);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//拒绝接收文件
	private void refuseReceiveFile(Request request) throws IOException {
		FileInfo sendFile = (FileInfo)request.getAttribute("sendFile");
		Response response = new Response();
		response.setType(ResponseType.REFUSERECEIVEFILE);
		response.setData("sendFile", sendFile);
		response.setStatus(ResponseStatus.OK);
		OnlineClientIOCache ocic = DataBuffer.onlineUserIOCacheMap.get(sendFile.getFromUser().getId());
		this.sendResponse(ocic, response);
	}

	// 同意接收文件
	private void agreeReceiveFile(Request request) throws IOException {
		FileInfo sendFile = (FileInfo)request.getAttribute("sendFile");
		Response response = new Response(); 
		response.setType(ResponseType.AGREERECEIVEFILE);
		response.setData("sendFile", sendFile);
		response.setStatus(ResponseStatus.OK);
		OnlineClientIOCache sendIO = DataBuffer.onlineUserIOCacheMap.get(sendFile.getFromUser().getId());
		this.sendResponse(sendIO, response);

		Response response2 = new Response();
		response2.setType(ResponseType.RECEIVEFILE);
		response2.setData("sendFile", sendFile);
		response2.setStatus(ResponseStatus.OK);
		OnlineClientIOCache receiveIO = DataBuffer.onlineUserIOCacheMap.get(sendFile.getToUser().getId());
		this.sendResponse(receiveIO, response2);
	}
	
	//退出
	public boolean logout(OnlineClientIOCache oio, Request request) throws IOException{
		System.out.println(currentClientSocket.getInetAddress().getHostAddress()
				+ ":" + currentClientSocket.getPort() + "走了");

		User user = (User)request.getAttribute("user");

		DataBuffer.onlineUserIOCacheMap.remove(user.getId());

		DataBuffer.onlineUsersMap.remove(user.getId());
			
		Response response = new Response();
		response.setType(ResponseType.LOGOUT);
		response.setData("logoutUser", user);
		oio.getOos().writeObject(response);
		oio.getOos().flush();
		currentClientSocket.close();
		
		DataBuffer.onlineUserTableModel.remove(user.getId());
		//通知所有其它在线客户端
		iteratorResponse(response);
		
		return false;
	}
	//注册
	public void registe(OnlineClientIOCache oio, Request request) throws IOException {
		User user = (User)request.getAttribute("user");
		UserService userService = new UserService();
		userService.addUser(user);
		
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setData("user", user);
		
		oio.getOos().writeObject(response);
		oio.getOos().flush();
		
		//把新注册用户添加到RegistedUserTableModel中
		DataBuffer.registedUserTableModel.add(new String[]{
			String.valueOf(user.getQq()),
			user.getPassword(),
			user.getNickname(),
			String.valueOf(user.getSex())
		});
	}
	
	//登录
	public void login(OnlineClientIOCache currentClientIO, Request request) throws IOException {
		String qqStr = (String)request.getAttribute("qq");
		String password = (String) request.getAttribute("password");
		UserService userService = new UserService();
		User user = userService.login(Long.parseLong(qqStr), password);
		
		Response response = new Response();
		if(null != user){
			if(DataBuffer.onlineUsersMap.containsKey(user.getId())){ 
				//用户已经登录了
				response.setStatus(ResponseStatus.OK);
				response.setData("msg", "该用户已在线!");
				currentClientIO.getOos().writeObject(response);
				currentClientIO.getOos().flush();
			}else { 
				//登录成功
				DataBuffer.onlineUsersMap.put(user.getId(), user);
				response.setData("onlineUsers", 
						new CopyOnWriteArrayList<User>(DataBuffer.onlineUsersMap.values()));
				
				response.setStatus(ResponseStatus.OK);
				response.setData("user", user);
				currentClientIO.getOos().writeObject(response);
				currentClientIO.getOos().flush();
				
				//通知其它用户
				Response response2 = new Response();
				response2.setType(ResponseType.LOGIN);
				response2.setData("loginUser", user);
				iteratorResponse(response2);
				

				DataBuffer.onlineUserIOCacheMap.put(user.getId(),currentClientIO);
				
				DataBuffer.onlineUserTableModel.add(
						new String[]{String.valueOf(user.getQq()), 
										user.getNickname(), 
										String.valueOf(user.getSex())});
			}
		}else{ 
			//登录失败
			response.setStatus(ResponseStatus.OK);
			response.setData("msg", "账号或密码不正确！");
			currentClientIO.getOos().writeObject(response);
			currentClientIO.getOos().flush();
		}
	}
	
	//聊天
	public void chat(Request request) throws IOException {
		Message msg = (Message)request.getAttribute("msg");
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setType(ResponseType.CHAT);
		response.setData("txtMsg", msg);
		
		if(msg.getToUser() != null){ 
			//私聊
			OnlineClientIOCache io = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getId());
			sendResponse(io, response);
		}else{  
			//群发
			for(Long id : DataBuffer.onlineUserIOCacheMap.keySet()){
				if(msg.getFromUser().getId() == id ){	continue; }
				sendResponse(DataBuffer.onlineUserIOCacheMap.get(id), response);
			}
		}
	}
	
	//发送抖动
	public void shake(Request request)throws IOException {
		Message msg = (Message)request.getAttribute("msg");
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(msg.getFromUser().getNickname())
			.append("(").append(msg.getFromUser().getId()).append(") ")
			.append(df.format(msg.getSendTime())).append("\n  给您发送了一个窗口抖动\n");
		msg.setMessage(sb.toString());
		
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setType(ResponseType.SHAKE);
		response.setData("ShakeMsg", msg);
		
		OnlineClientIOCache io = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getId());
		sendResponse(io, response);
	}
	
	//发送文件
	public void toSendFile(Request request)throws IOException{
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setType(ResponseType.TOSENDFILE);
		FileInfo sendFile = (FileInfo)request.getAttribute("file");
		response.setData("sendFile", sendFile);
		OnlineClientIOCache ioCache = DataBuffer.onlineUserIOCacheMap.get(sendFile.getToUser().getId());
		sendResponse(ioCache, response);
	}
	

	private void iteratorResponse(Response response) throws IOException {
		for(OnlineClientIOCache onlineUserIO : DataBuffer.onlineUserIOCacheMap.values()){
			ObjectOutputStream oos = onlineUserIO.getOos();
			oos.writeObject(response);
			oos.flush();
		}
	}
	
	private void sendResponse(OnlineClientIOCache onlineUserIO, Response response)throws IOException {
		ObjectOutputStream oos = onlineUserIO.getOos();
		oos.writeObject(response);
		oos.flush();
	}
}