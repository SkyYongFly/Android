package com.example.daomain;

/**
 * Created by yzas on 2015/10/4.
 */
public class Phone {
    private String phoneNum ; //手机号码
    private String  contact;//手机号码对应的联系人

    public  void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String  getPhoneNum(){
        return  phoneNum;
    }
    public  void setContact(String contact){
        this.contact = contact;
    }

    public String  getContact(){
        return  contact;
    }




}
