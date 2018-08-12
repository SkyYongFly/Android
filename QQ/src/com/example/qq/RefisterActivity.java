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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RefisterActivity extends Activity {

	protected static final int PHONE_NUM_HAS_EXISTS = 0;
	protected static final int SUCCESS = 2;
	protected static final int FAIL = 1;
	protected static final int REGISTE_FAIL = 3;
	private EditText et_phoneNum;
	private EditText et_password;
	private EditText et_confirmPassword;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

			case PHONE_NUM_HAS_EXISTS:// 手机号已经存在
				Toast.makeText(RefisterActivity.this, R.string.PHONE_NUM_HAS_EXISTS, Toast.LENGTH_SHORT).show();
				break;

			case SUCCESS:// 成功
				Intent intent = new Intent(RefisterActivity.this, LoginActivity.class);
				startActivity(intent);
				Toast.makeText(RefisterActivity.this, R.string.REGIST_SUCCESS, Toast.LENGTH_SHORT).show();
				finish();
				break;

			case FAIL:// 其他情况
				Toast.makeText(RefisterActivity.this, R.string.REGIST_FAIL, Toast.LENGTH_SHORT).show();
				break;
			case REGISTE_FAIL:// 其他情况
				Toast.makeText(RefisterActivity.this, R.string.REGIST_FAIL, Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refister);

		et_phoneNum = (EditText) findViewById(R.id.et_rePhoneNum);
		et_password = (EditText) findViewById(R.id.et_rePassword);
		et_confirmPassword = (EditText) findViewById(R.id.et_reConfirmPassword);
	}

	/**
	 * 注册
	 */
	public void register(View v) {
		final String phoneNum = et_phoneNum.getText().toString();
		final String password = et_password.getText().toString();
		final String confirmPassword = et_confirmPassword.getText().toString();

		// 非空判断
		if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(password)) {
			Toast.makeText(RefisterActivity.this, R.string.NULL_LOGIN_PASSWORD, Toast.LENGTH_SHORT).show();
		} // 密码一致判断
		else if (!password.equals(confirmPassword)) {
			Toast.makeText(RefisterActivity.this, R.string.PASSWORD_NO_SAME, Toast.LENGTH_SHORT).show();
		} // 开始提交数据
		else {

			NetConnection.connection(new SuccessConnection() {

				@Override
				public void onSuccess(String result) {
					Message message = new Message();
					if (result.equals(Config.PHONE_NUM_HAS_EXISTS)) {
						message.what = PHONE_NUM_HAS_EXISTS;
					} else if (result.equals(Config.REGISTER_FAIL)) {
						message.what = REGISTE_FAIL;
					} else {
						message.what = SUCCESS;
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
			}, Config.ACTION, Config.REGISTER, Config.DATA_PHONENUM, phoneNum, Config.DATA_PASSWORD,
					MD5Util.getMd5(password));
		}

	}

	/**
	 * 回到登陆界面
	 * 
	 * @param v
	 */
	public void login(View v) {
		Intent intent = new Intent(RefisterActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
