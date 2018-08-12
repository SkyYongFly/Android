package com.example.daomain;

/**
 * ��Ϣ
 * @author yzas
 *
 */
public class DataMessage {
	private String type;   //��Ϣ���� ����/����/��������
	private String personal; //������Ϣ����Ⱥ
	private String desphone; //��Դ����Ŀ���˻�
	private String body;     //��Ϣ����
	private String hasread; //�Ƿ��Ѷ�
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
