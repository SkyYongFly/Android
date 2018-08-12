package com.example.secret.login;

import com.example.secret.BaseActivity;
import com.example.secret.R;
import com.example.secret.daomain.Config;
import com.example.secret.net.HttpMethod;
import com.example.secret.net.Login;
import com.example.secret.net.Login.SuccessLoginCallback;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登陆页面
 * 
 * @author yzas
 *
 */
public class AtyLogin extends BaseActivity {

	private static final String TAG = "test";
	private String strToken;
	private boolean fail = false;
	private EditText et_phoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);

	}

	/**
	 * 获取验证码
	 * @param v
	 */
	public void getCode(View v) {
		String phoneNum = et_phoneNum.getText().toString();
		Log.d(TAG,phoneNum);

		if (TextUtils.isEmpty(phoneNum)) {
			Toast.makeText(this, R.string.NULL_PHONE_NUMBER, Toast.LENGTH_SHORT).show();
		} else {
			new Login(Config.URI, HttpMethod.POST,

					new SuccessLoginCallback() {
						@Override
						public void onSuccess(String token) {
							strToken = token;
							Log.d(TAG,"token="+token);
						}
					}, new Login.FailtureLoginCallback() {

						@Override
						public void onFail() {
							Log.d(TAG,"fail to get token");
						}
					}, 
					Config.CONNECT_ACTION, Config.CONNECT_ACTION,
					Config.Phone_NUM,phoneNum);

			if (TextUtils.isEmpty(strToken)) {
				Toast.makeText(getApplicationContext(), R.string.LOGIN_FAIL, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), R.string.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
			}
		}

	}

	/**
	 * 登陆
	 * @param v
	 */
	public void login(View v) {

	}

}
