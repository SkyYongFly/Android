package com.example.daomain;

public class Friend {
	private String phoneNum;
	private String nickName;
	private String sign;
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Friend(String phoneNum, String nickName, String sign) {
		super();
		this.phoneNum = phoneNum;
		this.nickName = nickName;
		this.sign = sign;
	}
	public Friend() {
		super();
	}
	@Override
	public String toString() {
		return "Friend [phoneNum=" + phoneNum + ", nickName=" + nickName + ", sign=" + sign + "]";
	}
	
	
	

}
