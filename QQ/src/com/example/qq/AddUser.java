package com.example.qq;

import org.json.JSONObject;

import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddUser extends Activity {

	//protected static final int SUCCESS = 0;
	//protected static final int HAS_ARE_FRIENDS = 1;
	protected static final int FAIL = -1;

	private static final String TAG = "test";
	protected static final int GET_SIGN_FAIL = 2;
	protected static final int GET_SIGN_SUCCESS = 3;
	protected static final int SEND_SUCCESS = 0;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
//			case SUCCESS: { // 添加好友成功
//				Toast.makeText(AddUser.this, "添加好友成功", Toast.LENGTH_SHORT).show();
//				// 跳转到好友列表界面
//				Intent intent = new Intent(AddUser.this, MainActivity.class);
//				startActivity(intent);
//			}
//				break;
//			case HAS_ARE_FRIENDS: {// 待添加的好友已经是好友了
//				Toast.makeText(AddUser.this, "你们已经是好友了，无需重复添加", Toast.LENGTH_SHORT).show();
//			}
//				break;
			case SEND_SUCCESS:{
				Toast.makeText(AddUser.this, "请求发送成功", Toast.LENGTH_SHORT).show();
				//跳转到主界面
				Intent intent = new Intent(AddUser.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}break;
			case FAIL: {// 添加失败
				Toast.makeText(AddUser.this, "发送添加好友请求失败", Toast.LENGTH_SHORT).show();
			}
				break;

			case GET_SIGN_SUCCESS: {// 获取昵称和签名成功
				try {
					String dataBack = (String) msg.obj;
					JSONObject jobj = new JSONObject(dataBack);
					String nickName = jobj.getString("nickname");
					String sign = jobj.getString("sign");
					tv_user_nickname.setText(nickName);
					tv_sign.setText(sign);
				} catch (Exception e) {
					e.printStackTrace();
					tv_user_nickname.setText("");
					tv_sign.setText("");
					Toast.makeText(getApplicationContext(), "获取用户昵称和签名失败", Toast.LENGTH_SHORT).show();
				}
			}
				break;

			case GET_SIGN_FAIL: {// 获取昵称和签名失败
				Toast.makeText(getApplicationContext(), "获取用户昵称和签名失败", Toast.LENGTH_SHORT).show();
				tv_sign.setText("");
			}
				break;

			default:
				break;
			}
		}

	};
	private String phoneNum;
	private TextView tv_account;
	private TextView tv_sign;
	private TextView tv_user_nickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);
		// 用户账户
		tv_account = (TextView) findViewById(R.id.tv_user_account_phone);
		// 用户昵称
		tv_user_nickname = (TextView) findViewById(R.id.tv_user_nickname);
		// 用户签名
		tv_sign = (TextView) findViewById(R.id.tv_user_sign);

		// 设置用户账户
		Intent intent = getIntent();
		phoneNum = intent.getExtras().getString("phoneNum");
		Log.d(TAG, "将要添加的好友是" + phoneNum);
		tv_account.setText(phoneNum);
		// 设置用户签名
		setSign();
	}

	/**
	 * 添加好友
	 * 
	 * @param v
	 */
	public void addFriend(View v) {
		// 联网提交数据
		NetConnection.connection(new SuccessConnection() {

			@Override
			public void onSuccess(String result) {
				Message message = new Message();
				message.what =SEND_SUCCESS;//这里没有做更加仔细的处理，服务器应该有消息返回，这里默认发送的请求只要没有异常就是发送成功
				mHandler.sendMessage(message);
			}
		}, new FailConnection() {

			@Override
			public void onFail() {
				Message message = new Message();
				message.what = FAIL;
				mHandler.sendMessage(message);
			}
		}, Config.ACTION, Config.ADD_FRIEND, // 告诉服务器数据是用来添加好友
				Config.DATA_PHONENUM,Config.getCachePhoneNum(getApplicationContext()) ,//要添加好友的用户
				Config.ADD_FRIEND_PHONE,phoneNum); // 待添加好友的手机号
	}

	/**
	 * 设置用户签名
	 */
	public void setSign() {
		// 连接网络获取用户数据
		NetConnection.connection(new SuccessConnection() {

			@Override
			public void onSuccess(String result) {
				Message message = new Message();
				if (result.equals(Config.GET_SIGN_FAIL)) {// 获取签名失败
					message.what = GET_SIGN_FAIL;
				} else {// 获取成功，返回的数据是查询到的昵称和签名数据
					message.what = GET_SIGN_SUCCESS;
					message.obj = result;
				}
				mHandler.sendMessage(message);
			}
		}, new FailConnection() {
			Message message = new Message();

			@Override
			public void onFail() {
				message.what = GET_SIGN_FAIL;
				mHandler.sendMessage(message);
			}
		}, Config.ACTION, Config.GET_SIGN_AND_NICKNAME, // 告诉服务器提交的数据是用来查询指定账户的昵称和签名的
				Config.DATA_PHONENUM, phoneNum); // 需要查询的账户
	}
}
