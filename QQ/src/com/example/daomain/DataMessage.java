package com.example.daomain;

/**
 * 消息
 * @author yzas
 *
 */
public class DataMessage {
	private String type;   //消息类型 接收/发送/好友申请
	private String personal; //个人消息还是群
	private String desphone; //来源或者目的账户
	private String body;     //消息内容
	private String hasread; //是否已读
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPersonal() {
		return personal;
	}
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	public String getDesphone() {
		return desphone;
	}
	public void setDesphone(String desphone) {
		this.desphone = desphone;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getHasread() {
		return hasread;
	}
	public void setHasread(String hasread) {
		this.hasread = hasread;
	}
	public DataMessage(String type, String personal, String desphone, String body,
			String hasread) {
		super();
		this.type = type;
		this.personal = personal;
		this.desphone = desphone;
		this.body = body;
		this.hasread = hasread;
	}
	public DataMessage() {
		super();
	}
	@Override
	public String toString() {
		return "Message [type=" + type + ", personal=" + personal
				+ ", desphone=" + desphone + ", body=" + body + ", hasread="
				+ hasread + "]";
	}
	
	

}
