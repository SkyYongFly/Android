package com.example.qq;

import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;
import com.example.util.MD5Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	protected static final String TAG = "test";
	protected static final int NO_EFFECT_PHONE_NUM = -1;
	protected static final int NO_EFFECT_PASSWORD = 0;
	protected static final int FAIL = 1;
	protected static final int SUCCESS = 2;

	protected static final int NO_DATA_BACK = 3;

	private EditText et_phoneNum;
	private EditText et_password;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			switch (msg.what) {

			case NO_EFFECT_PHONE_NUM:// 手机号无效
				Toast.makeText(LoginActivity.this, R.string.NO_EFFECT_PHONE_NUM, Toast.LENGTH_SHORT).show();
				//测试使用****************
				//startActivity(intent);
				//finish();
				//***************************
				break;

			case NO_EFFECT_PASSWORD:// 密码无效
				Toast.makeText(LoginActivity.this, R.string.NO_EFFECT_PASSWORD, Toast.LENGTH_SHORT).show();
				//测试使用****************
				//startActivity(intent);
				//finish();
				//***************************
				break;

			case SUCCESS:// 成功
				// 保存手机号和密码到本地
				String message = (String) msg.obj;
				String[] strings = message.split(":");
				String phoneNum = strings[0];
				String password = strings[1];
				Config.setCachePhoneNum(LoginActivity.this,phoneNum);
				Config.setCachePassword(LoginActivity.this, MD5Util.getMd5(password));

				Toast.makeText(LoginActivity.this, R.string.SUCCESS, Toast.LENGTH_SHORT).show();
				startActivity(intent);
				finish();
				break;

			case NO_DATA_BACK:// 其他情况
				Toast.makeText(LoginActivity.this, R.string.NO_DATA_BACK, Toast.LENGTH_SHORT).show();
				//测试使用****************
				//startActivity(intent);
				//finish();
				//***************************
				break;
			case FAIL:// 其他情况
				Toast.makeText(LoginActivity.this, R.string.FAIL, Toast.LENGTH_SHORT).show();
				//测试使用****************
				//startActivity(intent);
				//finish();
				//***************************
				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
		et_password = (EditText) findViewById(R.id.et_password);

	}

	/**
	 * 登陆
	 * 
	 * @param v
	 */
	public void login(View v) {
		final String phoneNum = et_phoneNum.getText().toString();
		final String password = et_password.getText().toString();

		if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(password)) {
			Toast.makeText(LoginActivity.this, R.string.NULL_LOGIN_PASSWORD, Toast.LENGTH_SHORT).show();
		} else {
			NetConnection.connection(new SuccessConnection() {

				@Override
				public void onSuccess(String result) {
					Log.d(TAG, result);
					Message message = new Message();
					if (result.equals(Config.NO_EFFECT_PHONE_NUM)) {
						message.what = NO_EFFECT_PHONE_NUM;
					} else if (result.equals(Config.NO_EFFECT_PASSWORD)) {
						message.what = NO_EFFECT_PASSWORD;
					} else if (result.equals(Config.NO_BACK)) {
						message.what = NO_DATA_BACK;
					} else if(result.equals(Config.LOGIN_ERROR)){
						message.what = FAIL;
					}else {
						message.what = SUCCESS;
						message.obj = phoneNum + ":" + password;
					}
					mHandler.sendMessage(message);
				}
			}, new FailConnection() {

				@Override
				public void onFail() {
					Message message = new Message();
					message.what = FAIL;
					mHandler.sendMessage(message);
				}
			}, Config.ACTION, Config.LOGIN, Config.DATA_PHONENUM, phoneNum, Config.DATA_PASSWORD,MD5Util.getMd5(password) );
		}

	}

	/**
	 * 跳转到注册页面
	 * 
	 * @param v
	 */
	public void register(View v) {
		Intent intent = new Intent(LoginActivity.this, RefisterActivity.class);
		startActivity(intent);
		//finish();
	}
}
