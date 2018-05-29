
package client.util;

import java.io.IOException;

import client.DataBuffer;
import client.ui.ChatFrame;

import common.model.entity.Request;
import common.model.entity.Response;

// 发送请求的工具
public class ClientUtil {
	
	//发送请求对象,主动接收
	public static Response sendTextRequest(Request request) throws IOException {
		Response response = null;
		try {
			// 发送请求
			DataBuffer.oos.writeObject(request);
			DataBuffer.oos.flush();

			if(!"exit".equals(request.getAction())){
				response = (Response) DataBuffer.ois.readObject();
			}else{
			}
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	//发送请求对象,不主动接收响应 
	public static void sendTextRequest2(Request request) throws IOException {
		try {
			DataBuffer.oos.writeObject(request); // 发送请求
			DataBuffer.oos.flush();
		} catch (IOException e) {
			throw e;
		} 
	}
	
	public static void appendTxt2MsgListArea(String txt) {
		ChatFrame.msgListArea.append(txt);
		//把光标定位到文本域的最后一行
		ChatFrame.msgListArea.setCaretPosition(ChatFrame.msgListArea.getDocument().getLength());
	}
}
