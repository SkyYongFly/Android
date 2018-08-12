package com.example.qq;

import org.apache.http.impl.entity.EntitySerializer;

import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFriend extends Activity {
	protected static final int EXIST = 0;

	protected static final int NO_EXIST = -1;

	protected static final int FAIL = -2;

	private EditText et_search_friend;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case EXIST: { //����ӵ��û�����
				//��ת��������û�����ҳ
				Intent intent = new Intent(SearchFriend.this,AddUser.class);
				intent.putExtra("phoneNum",phoneNum);
				startActivity(intent);
			}
				break;
			case NO_EXIST: {//����ӵ��û�������
				Toast.makeText(SearchFriend.this, "���ҵ��û�������", Toast.LENGTH_SHORT).show();
			}
				break;
			case FAIL: {//����ʧ��
				Toast.makeText(SearchFriend.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			}
				break;

			default:
				break;
			}
		}

	};



	private String phoneNum;	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_friend);

		et_search_friend = (EditText) findViewById(R.id.et_search_friend);
	}

	/**
	 * ��������
	 * 
	 * @param v
	 */
	public void searchFriend(View v) {
		phoneNum = et_search_friend.getText().toString();
		if (TextUtils.isEmpty(phoneNum)) {
			Toast.makeText(SearchFriend.this, "����Ϊ�գ�����������", Toast.LENGTH_SHORT).show();
		} else {
			// ������ѯ����
			NetConnection.connection(new SuccessConnection() {

				@Override
				public void onSuccess(String result) {
					Message message = new Message();
					if (result.equals(Config.EXIST)) {
						message.what = EXIST;
						message.obj = phoneNum;
					} else if (result.equals(Config.NOT_EXIST)) {
						message.what = NO_EXIST;
					} else {
						message.what = FAIL;
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
			},Config.ACTION,Config.SEARCH_FRIEND,  //���߷�����������������ѯ�û��Ƿ����
			  Config.DATA_PHONENUM,phoneNum); //����ѯ���ֻ���
		}
	}
}
