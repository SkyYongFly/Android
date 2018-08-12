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
 * Ӧ�ÿ�ʼҳ�棬��ɳ�ʼ���Ĳ���������ҳ�����ת��������ʾ����
 * @author yzas
 *
 */
public class MainActivity extends Activity {

	protected static final String TAG = "test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//�Ȼ�ȡ���ػ����token
		String token = Config.getCacheToken(this);
		String cachePhone = Config.getCachePhone(this);
		
		//if(!TextUtils.isEmpty(token) && !TextUtils.isEmpty(cachePhone)){
			if(1==1){//��������ʹ��
			//У��token
			//�ϴ���ϵ�����ݵ�������
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
			//��ת����½ҳ��
			Intent intent = new Intent(this,AtyLogin.class);
			startActivity(intent);
		}
		
		//���ٵ�ǰ��ҳ�棬��ֹ���ص�ʱ�򷵻ص��ÿհ�ҳ��
		finish();
				
	}
}
