
package common.model.entity;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class User implements Serializable {
	private static final long serialVersionUID = -427838794924380991L;
	private long id;
	private String password;
	private String nickname;
	private String sex;
	private int head;
	
	private long qq;

	public User(String password, String nickname, String sex, int head) {
		this.password = password;
		this.sex = sex;
		this.head = head;
		if (nickname.equals("") || nickname == null) {
			this.nickname = "无名";
		} else {
			this.nickname = nickname;
		}
	}
	
	public User(long id, String password) {
		this.id = id;
		this.password = password;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void setHead(int head) {
		this.head = head;
	}
	
	public int getHead() {
		return head;
	}
	
	public ImageIcon getHeadIcon() {
		ImageIcon image = new ImageIcon("images/" + head + ".png");
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + head;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + sex.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (head != other.head)
			return false;
		if (id != other.id)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName()
				+ "[id=" + this.id
				+ ",pwd=" + this.password
				+ ",nickname=" + this.nickname
				+ ",head=" + this.head
				+ ",sex=" + this.sex
				+ "]";
	}

	public long getQq() {
		return qq;
	}

	public void setQq(long qq) {
		this.qq = qq;
	}

}