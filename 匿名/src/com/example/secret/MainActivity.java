package com.example.secret;

import com.example.secret.daomain.Config;
import com.example.secret.login.AtyLogin;
import com.example.secret.provider.SendContects;
import com.example.secret.provider.SendContects.FailtureSendPhoneNum;
import com.example.secret.provider.SendContects.SuccessSendPhoneNum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
/**
 * 应用开始页面，完成初始化的操作，进行页面的跳转，并不显示内容
 * @author yzas
 *
 */
public class MainActivity extends Activity {

	protected static final String TAG = "test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//先获取本地缓存的token
		String token = Config.getCacheToken(this);
		String cachePhone = Config.getCachePhone(this);
		
		//if(!TextUtils.isEmpty(token) && !TextUtils.isEmpty(cachePhone)){
			if(1==1){//本处测试使用
			//校验token
			//上传联系人数据到服务器
			SendContects.sendContects(this,new SuccessSendPhoneNum() {
				
				@Override
				public void onSuccess(String reString) {
					Log.d(TAG,"success send phoneNum"+reString);
				}
			},
			 new FailtureSendPhoneNum() {
				
				@Override
				public void onFailture() {
					Log.d(TAG,"fail send phoneNum");
				}
			});
			
			Intent intent = new Intent(this,HomeActivity.class);
			startActivity(intent);
		}else{
			//跳转到登陆页面
			Intent intent = new Intent(this,AtyLogin.class);
			startActivity(intent);
		}
		
		//销毁当前的页面，防止返回的时候返回到该空包页面
		finish();
				
	}
}
