package com.example.qq;

import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResponseAddFriend extends Activity {

	private String phoneNum ;
	private TextView  tv_account;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response_add_friend);
		//获取请求添加的账号
		phoneNum = getIntent().getExtras().getString("account");
		//
		tv_account  = (TextView) findViewById(R.id.tv_account_phone);
		tv_account.setText(phoneNum);
	}
	
	/**
	 * 同意添加为好友
	 * @param v
	 */
	public void  agree(View v){
		NetConnection.connection(new SuccessConnection() {
			
			@Override
			public void onSuccess(String result) {
				
			}
		}, new FailConnection() {
			
			@Override
			public void onFail() {
				
			}
		}, Config.ACTION,Config.RESPONSE_ADD_FRIEND,
			Config.DATA_PHONENUM,Config.getCachePhoneNum(getApplicationContext()),
			Config.DESPHONE,phoneNum,
			Config.RESPONSE,"agree");
	}
	
	/**
	 * 不同意添加为好友
	 * @param v
	 */
	public void disagree(View v){
		
	}
}
