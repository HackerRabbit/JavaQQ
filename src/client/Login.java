package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.DataBuffer;
import client.ui.ChatFrame;
import client.ui.RegisterFrame;
import client.util.ClientUtil;
import common.model.entity.Request;
import common.model.entity.Response;
import common.model.entity.ResponseStatus;
import common.model.entity.User;

public class Login {


//略略略
	
	public static BufferedImage backimg;
	public static BufferedImage close;
	public static BufferedImage small;
	public static BufferedImage more;
	public static BufferedImage logo;
	
	JTextField numT;
	JPasswordField passT;
	
	public static Point mouseA = new Point();

	//静态加载图片
	static {
		try {
			backimg = ImageIO.read(Login.class.getResource("../reg/1.png"));
			close = ImageIO.read(Login.class.getResource("../reg/close.png"));
			small = ImageIO.read(Login.class.getResource("../reg/small.png"));
			more = ImageIO.read(Login.class.getResource("../reg/more.png"));
			logo = ImageIO.read(Login.class.getResource("../reg/logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	//登录按钮
	public JLabel loginB;
	//主窗口
	public JFrame jf;
	
	//登陆按钮颜色
	Color c1 = new Color(72, 155, 213);
	//登录按钮点击颜色
	Color c2 = new Color(14, 99, 157);

	public Login () {
		jf = new JFrame();
		JPanel mainP = new JPanel();
		JPanel formP = new JPanel();
		JPanel controlP = new JPanel();
		JPanel logop = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(logo, 0, 0, this);
			}
		};
		
		//右上角按钮图标
		JPanel moreb = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(more, 0, 0, this);
			}
		};
		JPanel smallb = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(small, 0, 0, this);
			}
		};
		JPanel closeb = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(close, 0, 0, this);
			}
		};
		
		numT = new JTextField();
		passT = new JPasswordField();
		
		JLabel qqL = new JLabel("Q Q:");
		JLabel passwordL = new JLabel("密码:");
		JLabel FindPass = new JLabel("<html><a href=''>找回密码</a></html>");
		JLabel registb = new JLabel("<html><a href=''>没有账号?点我注册>></a></html>");
		JLabel northP = new JLabel();
		
		JCheckBox AutoLogin = new JCheckBox("自动登陆");
		JCheckBox RemPass = new JCheckBox("记住密码");
		
		loginB = new JLabel("登陆",JLabel.CENTER);
		
		//背景图片
		ImageIcon imi = new ImageIcon(JButton.class.getResource("/reg/1.png"));
		
		
		
		//配置窗口
		jf.setSize(550, 380);
		jf.setLocationRelativeTo(null);
		jf.setLayout(null);		
		jf.setUndecorated(true);
		jf.add(mainP);
		jf.add(northP);
		
		//更多按钮
		moreb.setBounds(0, 0, 30, 30);
		moreb.setOpaque(false);
		moreb.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				moreb.setCursor(new Cursor(Cursor.HAND_CURSOR));
				moreb.setToolTipText("更多...");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("更多...");
			}
		});
		
		//最小化按钮
		smallb.setBounds(30, 0, 30, 30);
		smallb.setOpaque(false);
		smallb.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				smallb.setCursor(new Cursor(Cursor.HAND_CURSOR));
				smallb.setToolTipText("最小化");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				jf.setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		//关闭按钮
		closeb.setBounds(60, 0, 30, 30);
		closeb.setOpaque(false);
		closeb.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				closeb.setCursor(new Cursor(Cursor.HAND_CURSOR));
				closeb.setToolTipText("关闭");
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				jf.dispose();
			}
		});
		
		//右上角按钮板
		controlP.add(moreb);
		controlP.add(smallb);
		controlP.add(closeb);
		controlP.setBounds(460, 8, 90, 30);
		controlP.setOpaque(false);
		controlP.setLayout(null);
		
		//背景图片面板
		northP.add(controlP);		
		northP.setBounds(0, 0, 549, 150);
		northP.setIcon(imi);
		northP.add(logop);
		
		//logo
		logop.setBounds(20, 20, 128, 128);
		logop.setBackground(null);
	
		//注册超链接
		registb.setBackground(null);
		registb.setBounds(145, 190, 150, 20);
		registb.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//new Regist(500, 250);  //打开注册窗体
				new RegisterFrame();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				registb.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("注册");
			}
		});
		
		//表单面板
		formP.setPreferredSize(new Dimension(300, 250));
		formP.setLayout(null);
		formP.add(qqL);
		formP.add(passwordL);
		formP.add(numT);
		formP.add(passT);		
		formP.add(AutoLogin);
		formP.add(RemPass);
		formP.add(FindPass);
		formP.add(loginB);
		formP.add(registb);
		formP.setBackground(null);
		
		//自动登陆
		AutoLogin.setBounds(20, 120, 80,20);
		AutoLogin.setBackground(null);
		
		//记住密码
		RemPass.setBounds(120, 120, 80, 20);
		RemPass.setBackground(null);
		
		//密码找回
		FindPass.setBounds(215,120,80,20);
		FindPass.setBackground(null);
		FindPass.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				FindPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("找回密码");
			}
		});
		
		//登陆按钮
		loginB.setBounds(20,150,250,30);
		loginB.setOpaque(true);	
		loginB.setBackground(c1);
		loginB.setForeground(Color.WHITE);
		loginB.setFont(new Font("微软雅黑", 0, 20));
		loginB.addMouseListener(new doLogin());
		
		//QQ标签
		qqL.setBounds(22,25,50,50);
		qqL.setFont(new Font("微软雅黑", 0, 20));
		
		//密码标签
		passwordL.setBounds(22,60,50,50);
		passwordL.setFont(new Font("微软雅黑", 0, 20));		
		
		//QQ输入框
		numT.setBounds(80,40,180,30);
		numT.setFont(new Font("微软雅黑", 0, 20));
		
		//密码输入框
		passT.setBounds(80,70,180,30);
		passT.setFont(new Font("微软雅黑", 0, 20));
		
		//主面板
		mainP.setBounds(0, 150, 550, 380);
		mainP.setBorder(BorderFactory.createEtchedBorder());
		mainP.add(formP);
		
		
		
		jf.setVisible(true);
		
		//窗口拖动
		jf.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseA.x = e.getX();
				mouseA.y = e.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				jf.setLocation(jf.getLocation().x+e.getX()-mouseA.x, jf.getLocation().y+e.getY()-mouseA.y);
			}
			
		});
		//关闭窗口
		jf.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				Request req = new Request();
				req.setAction("exit");
				try {
					ClientUtil.sendTextRequest(req);
				} catch (IOException ex) {
					ex.printStackTrace();
				}finally{
					System.exit(0);
				}
			}
		});
		
	}
	
	//登陆
	class doLogin implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			loginB.setCursor(new Cursor(Cursor.HAND_CURSOR));
			loginB.setBackground(new Color(6, 70, 117));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			loginB.setBackground(c1);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			loginB.setBackground(c2);
			login();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			loginB.setBackground(new Color(6, 70, 117));
		}
		
	}
	
	//关闭
	class doclose implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("关闭");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/** 登录 */
	//@SuppressWarnings("unchecked")
	private void login() {
		if (numT.getText().length() == 0 
				|| passT.getPassword().length == 0) {
			JOptionPane.showMessageDialog(jf, 
					"账号和密码是必填的",
					"输入有误",JOptionPane.ERROR_MESSAGE);
			numT.requestFocusInWindow();
			return;
		}
		
		if(!numT.getText().matches("^\\d*$")){
			JOptionPane.showMessageDialog(jf, 
					"账号必须是数字",
					"输入有误",JOptionPane.ERROR_MESSAGE);
			numT.requestFocusInWindow();
			return;
		}
		
		Request req = new Request();
		req.setAction("userLogin");
		req.setAttribute("qq", numT.getText());
		req.setAttribute("password", new String(passT.getPassword()));
		
		//获取响应
		Response response = null;
		try {
			response = ClientUtil.sendTextRequest(req);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(response.getStatus() == ResponseStatus.OK){
			//获取当前用户
			User user2 = (User)response.getData("user");
			if(user2!= null){ //登录成功
				DataBuffer.currentUser = user2;
				//获取当前在线用户列表
				DataBuffer.onlineUsers = (List<User>)response.getData("onlineUsers");
				
				jf.dispose();
				//new ListFrame(350,700);  //打开聊天窗体
				//new QQ(500,500);
				new ChatFrame();
			}else{ //登录失败
				String str = (String)response.getData("msg");
				JOptionPane.showMessageDialog(jf, 
							str,
							"登录失败",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(jf, 
					"服务器内部错误，请稍后再试！！！","登录失败",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//测试类
	public static void main(String[] args) {
		new Login();
	}
}
