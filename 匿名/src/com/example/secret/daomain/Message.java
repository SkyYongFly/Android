package com.example.secret.daomain;

public class Message {
	private String msg_id;//��Ϣ�����
	private String msg_phone;//��Ϣ�Ĺ�����
	private String msg_body;//��Ϣ������
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_phone() {
		return msg_phone;
	}
	public void setMsg_phone(String msg_phone) {
		this.msg_phone = msg_phone;
	}
	public String getMsg_body() {
		return msg_body;
	}
	public void setMsg_body(String msg_body) {
		this.msg_body = msg_body;
	}
	public Message(String msg_phone,String msg_id, String msg_body) {
		super();
		this.msg_id = msg_id;
		this.msg_phone = msg_phone;
		this.msg_body = msg_body;
	}
	
	
	
	

}
