package com.example.daomain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.db.SQLiteUtil;
import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;
import com.example.qq.Config;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DataMessageManager {
	private static Context context;
	protected static final String TAG = "test";
	protected static final int SUCCESS = 0;
	protected static final int NULL = 1;
	protected static final int FAIL = 2;
	private static List<DataMessage> listMessage = new ArrayList<DataMessage>();

	private static Handler mHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS: {
				// ����Ϣ���浽�������ݿ�
				SQLiteUtil sqLiteUtil = new SQLiteUtil(context);
				listMessage = (List<DataMessage>) msg.obj;
				if (listMessage != null) {
					for (DataMessage m : listMessage) {
						if (sqLiteUtil.exist(m)) {// ��Ϣ�Ѿ�����
							Log.d(TAG, "��Ϣ�Ѿ�����");
						} else {// ������
							Log.d(TAG, "��Ϣ������");
							sqLiteUtil.addMessage(m);
						}
					}
				}
			}
				break;
			case NULL: {

			}
				break;
			case FAIL: {

			}
			default:
				break;
			}

		}

	};

	public DataMessageManager(Context context) {
		DataMessageManager.context = context;
	}

	/**
	 * ��ȡָ���˺Ż����ڷ������˵�δ������Ϣ
	 * 
	 * @param context
	 * @return
	 */
	public void solveDataMessages() {
		// �������󻺴��ڷ������е�δ������Ϣ
		NetConnection.connection(new SuccessConnection() {

			@Override
			public void onSuccess(String result) {
				Message message = new Message();

				if (Config.NO_UNREAD_DATA.equals(result)) {// û��δ��������
					message.what = NULL;
				} else {
					try {
						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject object = jsonArray.getJSONObject(i);
							DataMessage datamessage = new DataMessage();
							datamessage.setType((String) object.get("type"));
							datamessage.setPersonal((String) object.get("personal"));
							datamessage.setDesphone((String) object.get("desphone"));
							datamessage.setBody((String) object.get("body"));
							datamessage.setHasread((String) object.get("hasread"));
							listMessage.add(datamessage);

						}
						message.what = SUCCESS;
						message.obj = listMessage;

					} catch (JSONException e) {
						e.printStackTrace();
						message.what = FAIL;
					} finally {
						mHandler.sendMessage(message);
					}
				}
			}
		}, new FailConnection() {
			@Override
			public void onFail() {
				Message message = new Message();
				message.what = SUCCESS;
				mHandler.sendMessage(message);
			}
		}, Config.ACTION, Config.GET_NOREAD_MESSAGES, // ���߷�����Ҫ���������ǻ�ȡδ��������
				Config.DATA_PHONENUM, Config.getCachePhoneNum(context));// �˺�

	}

}
