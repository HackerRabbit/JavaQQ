
package common.model.entity;

//响应类型 
public enum ResponseType {
	TEXT,
	//准备发送文件
	TOSENDFILE,
	//同意接收文件
	AGREERECEIVEFILE,
	//拒绝接收文件
	REFUSERECEIVEFILE,
	//发送文件
	RECEIVEFILE,
	//登录
	LOGIN,
	//退出
	LOGOUT,
	//聊天
	CHAT,
	//振动
	SHAKE,
	//其它
	OTHER
}
