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
//			case SUCCESS: { // ��Ӻ��ѳɹ�
//				Toast.makeText(AddUser.this, "��Ӻ��ѳɹ�", Toast.LENGTH_SHORT).show();
//				// ��ת�������б����
//				Intent intent = new Intent(AddUser.this, MainActivity.class);
//				startActivity(intent);
//			}
//				break;
//			case HAS_ARE_FRIENDS: {// ����ӵĺ����Ѿ��Ǻ�����
//				Toast.makeText(AddUser.this, "�����Ѿ��Ǻ����ˣ������ظ����", Toast.LENGTH_SHORT).show();
//			}
//				break;
			case SEND_SUCCESS:{
				Toast.makeText(AddUser.this, "�����ͳɹ�", Toast.LENGTH_SHORT).show();
				//��ת��������
				Intent intent = new Intent(AddUser.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}break;
			case FAIL: {// ���ʧ��
				Toast.makeText(AddUser.this, "������Ӻ�������ʧ��", Toast.LENGTH_SHORT).show();
			}
				break;

			case GET_SIGN_SUCCESS: {// ��ȡ�ǳƺ�ǩ���ɹ�
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
					Toast.makeText(getApplicationContext(), "��ȡ�û��ǳƺ�ǩ��ʧ��", Toast.LENGTH_SHORT).show();
				}
			}
				break;

			case GET_SIGN_FAIL: {// ��ȡ�ǳƺ�ǩ��ʧ��
				Toast.makeText(getApplicationContext(), "��ȡ�û��ǳƺ�ǩ��ʧ��", Toast.LENGTH_SHORT).show();
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
		// �û��˻�
		tv_account = (TextView) findViewById(R.id.tv_user_account_phone);
		// �û��ǳ�
		tv_user_nickname = (TextView) findViewById(R.id.tv_user_nickname);
		// �û�ǩ��
		tv_sign = (TextView) findViewById(R.id.tv_user_sign);

		// �����û��˻�
		Intent intent = getIntent();
		phoneNum = intent.getExtras().getString("phoneNum");
		Log.d(TAG, "��Ҫ��ӵĺ�����" + phoneNum);
		tv_account.setText(phoneNum);
		// �����û�ǩ��
		setSign();
	}

	/**
	 * ��Ӻ���
	 * 
	 * @param v
	 */
	public void addFriend(View v) {
		// �����ύ����
		NetConnection.connection(new SuccessConnection() {

			@Override
			public void onSuccess(String result) {
				Message message = new Message();
				message.what =SEND_SUCCESS;//����û����������ϸ�Ĵ���������Ӧ������Ϣ���أ�����Ĭ�Ϸ��͵�����ֻҪû���쳣���Ƿ��ͳɹ�
				mHandler.sendMessage(message);
			}
		}, new FailConnection() {

			@Override
			public void onFail() {
				Message message = new Message();
				message.what = FAIL;
				mHandler.sendMessage(message);
			}
		}, Config.ACTION, Config.ADD_FRIEND, // ���߷�����������������Ӻ���
				Config.DATA_PHONENUM,Config.getCachePhoneNum(getApplicationContext()) ,//Ҫ��Ӻ��ѵ��û�
				Config.ADD_FRIEND_PHONE,phoneNum); // ����Ӻ��ѵ��ֻ���
	}

	/**
	 * �����û�ǩ��
	 */
	public void setSign() {
		// ���������ȡ�û�����
		NetConnection.connection(new SuccessConnection() {

			@Override
			public void onSuccess(String result) {
				Message message = new Message();
				if (result.equals(Config.GET_SIGN_FAIL)) {// ��ȡǩ��ʧ��
					message.what = GET_SIGN_FAIL;
				} else {// ��ȡ�ɹ������ص������ǲ�ѯ�����ǳƺ�ǩ������
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
		}, Config.ACTION, Config.GET_SIGN_AND_NICKNAME, // ���߷������ύ��������������ѯָ���˻����ǳƺ�ǩ����
				Config.DATA_PHONENUM, phoneNum); // ��Ҫ��ѯ���˻�
	}
}
